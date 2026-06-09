# Security Plan

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

This page defines the security approach for container builds and CI/CD pipelines within the pilot scope. It covers secret management, image scanning, policy enforcement, and supply-chain hardening.

> **Scope:** practices the pilot will apply or assess. The pilot operates in **report-only mode** — scanning and policy checks produce warnings but do not gate builds. The target state (post-pilot, with ACP/ETO support) introduces hard gates on critical findings. Items needing org-wide infrastructure (shared scanners, signing infrastructure) are flagged as **ACP/ETO** and routed via Story 6.

---

## 1. Secret Management

### Secret Handling by Context

| Context | Mechanism | Details |
|---------|-----------|---------|
| **CI pipeline secrets** | Drone secrets | Registry credentials and tokens are injected as environment variables into pipeline steps. Managed via Drone's secret store (per-repo or organisation-level). Never written to build logs. |
| **Build-time secrets** | BuildKit secret mounts | Secrets needed during build (e.g., Maven `settings.xml`, Artifactory credentials for dependency download) are mounted ephemerally — never baked into image layers. Not passed via `ARG`, `ENV`, or `COPY`. |
| **Runtime secrets** | Vault / envconsul | Application secrets (database credentials, API keys) are injected at container startup via Vault agent or envconsul sidecar. Never present in the image. Out of pilot scope to implement, in scope to document. |
| **Prevention** | `.dockerignore` | Excludes sensitive files (`.env`, credentials, SSH keys) from the Docker build context entirely. Secret scanning runs in CI. |

### Secret-Safe Build Pattern (BuildKit)

```dockerfile
# syntax=docker/dockerfile:1
FROM amazoncorretto:17 AS build

# Secret is mounted only for this RUN, never baked into a layer
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    ./mvnw -B package

# Runtime stage — no secrets present
FROM amazoncorretto:17-alpine AS runtime
COPY --from=build /app/target/app.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

```bash
docker buildx build --secret id=maven_settings,src=$HOME/.m2/settings.xml .
```

### Rules

- **No secrets in image layers** — never use `COPY` or `ADD` for secret files. Never use `ARG` for secret values (ARGs are visible in image history).
- **No secrets in build arguments** — `docker build --build-arg SECRET=value` exposes the value in `docker history`. Use `--secret` instead.
- **No secrets in build logs** — ensure CI logging does not echo secret values. Drone masks declared secrets automatically.
- **No secrets in source repository** — credentials, tokens, and keys must never be committed. Use `.gitignore` and pre-commit hooks to prevent accidental commits.
- **No real credentials in examples or fixtures** — use placeholders.
- **Rotate any credential** that is suspected to have leaked.

---

## 2. Scanning Policy

### Scan Types

| Scan Type | Tool (candidate) | What It Checks | When | Pilot Mode | Target Gate (Post-Pilot) |
|-----------|------------------|----------------|------|------------|--------------------------|
| **Container image scan** | Trivy or Snyk | OS packages and application dependencies in the built image for known CVEs. | Every pilot build | Report-only — log findings, do not fail build. | Fail on Critical severity. Review High. |
| **Dependency scan** | Trivy (fs mode), Snyk, or `mvn` audit | Source-level dependency files (`pom.xml`, lockfiles) for known vulnerabilities. | On MR + weekly schedule | Report-only. | Fail on Critical. |
| **Secret scan** | gitleaks or trufflehog | Source code and git history for accidentally committed secrets, tokens, or credentials. | On MR | Report-only until tool is chosen. | Fail on any verified secret detection. |
| **SBOM generation** | Syft (SPDX/CycloneDX) | Produce a Software Bill of Materials for each built image. Machine-readable inventory of all components. | On image build | Artefact attached to build — no gating. | Required artefact; attach to release artifacts. |
| **Base image freshness** | Trivy or scheduled rebuild + scan | Detect when the base image has not been rebuilt/updated beyond a defined threshold (e.g., 30 days). | Weekly | Report-only. | Flag outdated/EOL base images. Warn if older than 30 days, fail if older than 90 days. |

Tool **choice** is CST-local for the pilot. A shared, org-wide scanning **standard / gate** is **ACP/ETO** and should be classified in Story 6. The template CI starts in report-only mode to avoid blocking before baseline data exists. Promote the target gates only after Story 2 captures the baseline and stakeholders agree the thresholds.

### Severity Policy

| Severity | Pilot Action | Target Action (Post-Pilot) |
|----------|-------------|---------------------------|
| **Critical** | Log and alert. Investigate. | Block merge/build until resolved. |
| **High** | Log and alert. | Review and decide; waiver with expiry if accepted. |
| **Medium** | Log. | Track for periodic review; do not block. |
| **Low / Negligible** | Log. | Informational only; do not block. |

> During the pilot, all scanning is advisory. Hard gates require ACP/ETO to provide organisational policy and shared infrastructure for consistent enforcement across repositories.

---

## 3. Policy as Code

Container/image rules should be enforced automatically rather than by review.

### Container Policies

| Policy | Rule | Enforcement Tool | Pilot Mode | Target (Post-Pilot) |
|--------|------|-----------------|------------|---------------------|
| **No root user** | Container must not run as root. Dockerfile must include `USER` directive with non-root UID. | hadolint (DL3002) + OPA/Conftest | Warn | Fail |
| **No unpinned base images** | Base and CI job images must use a specific tag or digest, not `latest`. Digest for critical images. | hadolint (DL3006) + CI grep/lint | Warn | Fail |
| **No secrets in image** | No `ARG` containing secret-like values. No `COPY` of `.env`, credentials, or key files. Built image contains no secret material. | hadolint + custom Conftest policy + secret scan of built image | Warn | Fail |
| **Healthcheck defined** | Long-running images define a `HEALTHCHECK` instruction, or the Helm chart defines liveness/readiness probes. | hadolint (DL3009) + OPA/Conftest | Warn | Warn |
| **Approved base images only** | Base image must come from an approved registry and image list (TBC — requires ACP/ETO to define the allowlist). | OPA/Conftest | Inform | Fail |

### Enforcement Approach and Tools

| Tool | Purpose | Where It Runs |
|------|---------|---------------|
| **hadolint** | Dockerfile linting — catches common mistakes, security anti-patterns, and best-practice violations. Fast; runs locally and in CI. | Locally (developer workstation) + CI pipeline step. |
| **OPA / Conftest** | Policy-as-code engine — evaluates Dockerfiles, Kubernetes manifests, and Helm values against Rego policies. | CI pipeline step. Policies stored alongside code or centrally via RepoSync. |

- Start with **hadolint** for Dockerfile rules; it is fast and can run locally + CI.
- Express image/admission policies as code (**OPA/Conftest** or equivalent) where a gate is wanted.
- For the pilot, run policies in **warn** mode first; promote to **block** once stable.
- Release alias tags such as `:main` are allowed only when the immutable SHA tag is also pushed.
- Avoid `:latest` in pilot templates.

---

## 4. Supply-Chain Hardening

These are assessed mostly as ACP/ETO or platform follow-ups.

| Measure | Description | Pilot Status | Post-Pilot Status | Requires |
|---------|-------------|-------------|-------------------|----------|
| **Digest pinning** | Pin base images by SHA256 digest rather than mutable tag (`FROM image@sha256:...`). Ensures reproducible builds. | Assess feasibility. Document digest for pilot base image. | Enforce for all production Dockerfiles. | CST: implementation. ACP/ETO: approved digest list. |
| **Image signing (cosign)** | Sign built images with a cryptographic key (Sigstore cosign). Consumers verify image provenance before deployment. | Out of scope — requires signing infrastructure. | Implement with ACP-provided signing keys and verification in deployment pipeline. | ACP/ETO: signing infrastructure, key management, verification policies. |
| **Scheduled rebuilds** | Rebuild and re-push images on a schedule (e.g., weekly) even without code changes, so base image security patches are incorporated promptly. | Out of scope. | Implement as scheduled Drone pipeline or external trigger. | ACP: scheduled pipeline support. CST: rebuild pipeline definition. |
| **Deprecated-image policy** | Detect and alert when a base image or dependency has been deprecated or marked end-of-life by its maintainer. | Out of scope. | Implement as scanning policy. | ACP/ETO: policy definition and scanning infrastructure. |

### Notes

- Most supply-chain hardening measures require organisational infrastructure that is beyond the pilot scope.
- The pilot contributes by structuring Dockerfiles correctly (multi-stage, minimal runtime) so that these measures can be applied cleanly post-pilot.
- Digest pinning is the most accessible measure for CST to implement during or shortly after the pilot.

---

## Responsibilities

| Area | CST (Pilot Owner) | ACP / ETO (Platform) |
|------|-------------------|---------------------|
| **Secret-safe builds** | Implement BuildKit secret mounts. Ensure no secrets in layers, args, or logs. Maintain `.dockerignore`. Run hadolint. | Provide Drone secret store. Maintain Vault/envconsul infrastructure. |
| **Scanning trial** | Run Trivy/Snyk in report-only mode during pilot. Evaluate findings. Document false-positive rate and actionability. | Provide org-wide scanning gates (hard fail on Critical). Maintain scanner infrastructure and vulnerability databases. |
| **SBOM generation** | Generate SBOM for pilot images using Syft. Validate output format and completeness. | Define org-wide SBOM requirements. Provide storage and attestation infrastructure. |
| **Policy enforcement** | Implement hadolint locally and in CI (warn mode). Write initial Conftest policies for pilot Dockerfile. | Define and maintain org-wide OPA/Conftest policies. Provide policy distribution mechanism. |
| **Image signing** | Out of scope for pilot. Prepare Dockerfile structure to be signing-compatible. | Provide signing infrastructure (cosign, key management). Define verification policies for deployment pipeline. |
| **Base image ownership** | Use `amazoncorretto:17` for pilot. Document requirements for approved base image. | Own and maintain approved base images with timely security patches. Define approved image list. |
| **Deprecated-image detection** | Out of scope for pilot. | Define and implement deprecated-image scanning policy. |

---

## Reporting a Vulnerability

This is a planning/pilot repository with docs plus executable templates/config. If a security issue is found in pilot **code, config, or templates**, raise it privately with the pilot lead rather than opening a public issue.

---

## Summary

The pilot security approach is **observe and learn**:

1. Implement secret-safe practices (BuildKit mounts, `.dockerignore`) — these are zero-cost and immediately beneficial.
2. Run scanning in report-only mode — understand the current vulnerability landscape without blocking development.
3. Document findings — provide evidence to ACP/ETO for the case to implement hard gates.
4. Structure Dockerfiles correctly — multi-stage builds, non-root users, pinned bases — so that future policy enforcement is straightforward.

Hard gates, image signing, and org-wide policies require ACP/ETO ownership and are post-pilot activities.

---

*Feedback or questions? Contact the page owner or comment below.*
