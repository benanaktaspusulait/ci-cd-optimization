# Security Plan

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

Concrete security practices for the pilot. This page carries the original security plan into the Confluence set.

> **Scope:** practices the pilot will apply or assess. Items needing org-wide infrastructure (shared scanners, signing infrastructure) are flagged as **ACP/ETO** and routed via Story 6.

---

## 1. Secret Management

| Concern | Approach |
|---------|----------|
| CI secrets (registry creds, tokens) | Store in **Drone secrets** (per-repo or organisation-level). Never store them in the repo. Drone encrypts secrets and injects them as environment variables at runtime. |
| Build-time secrets, for example Maven `settings.xml` | Pass via **BuildKit secret mounts**, not `ARG`, `ENV`, or `COPY`. |
| App runtime secrets | Injected at deploy time via the platform secret manager, for example **HashiCorp Vault** / cloud secret manager. Out of pilot scope to implement, in scope to document. |
| Preventing leaks | `.dockerignore` excludes `.env` and key files; secret scanning runs in CI. |

### Secret-Safe Build Pattern (BuildKit)

```dockerfile
# secret is mounted only for this RUN, never baked into a layer
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    ./mvnw -B package
```

```bash
docker buildx build --secret id=maven_settings,src=$HOME/.m2/settings.xml .
```

### Rules

- No secrets in image layers, build args, logs, or the repository.
- No real credentials in examples or fixtures; use placeholders.
- Rotate any credential that is suspected to have leaked.

---

## 2. Scanning Policy

| What | Tool (candidate) | When | Pilot mode | Target gate |
|------|------------------|------|------------|-------------|
| Image vulnerabilities | **Trivy** or **Snyk** | Every pilot build | Report-only / warn | Fail on **Critical**; review High |
| Dependency vulnerabilities | Trivy / Snyk / `mvn` audit | On MR + weekly schedule | Report-only / warn | Fail on Critical |
| Secret scanning | **gitleaks** / **trufflehog** | On MR | Report-only until tool is chosen | Fail on any verified secret |
| SBOM generation | **Syft** (SPDX/CycloneDX) | On image build | Artefact attached to build | Required artefact |
| Base image freshness | Scheduled rebuild + scan | Weekly | Report-only / warn | Flag outdated/EOL base images |

Tool **choice** is CST-local for the pilot. A shared, org-wide scanning **standard / gate** is **ACP/ETO** and should be classified in Story 6.

The template CI starts in report-only mode to avoid blocking before baseline data exists. Promote the target gates only after Story 2 captures the baseline and stakeholders agree the thresholds.

### Severity Policy (target gate, after promotion)

- **Critical:** block merge/build.
- **High:** review and decide; waiver with expiry if accepted.
- **Medium/Low:** track; do not block.

---

## 3. Policy as Code

Container/image rules should be enforced automatically rather than by review.

| Policy | Rule | How |
|--------|------|-----|
| No `root` runtime | Container must run as non-root `USER` | Dockerfile lint (**hadolint**) + image policy check |
| No unpinned base/job images | Base images and CI job images pinned to a version; digest for critical | hadolint rule + CI grep/lint |
| No secrets in image | Built image contains no secret material | Secret scan of built image |
| Healthcheck present | Long-running images define a healthcheck | hadolint / policy check |
| Approved base images | Use sanctioned base images only | Policy check against allowlist (ACP/ETO) |

### Enforcement Approach

- Start with **hadolint** for Dockerfile rules; it is fast and can run locally + CI.
- Express image/admission policies as code (**OPA/Conftest** or equivalent) where a gate is wanted.
- For the pilot, run policies in **warn** mode first; promote to **block** once stable.
- Release alias tags such as `:main` are allowed only when the immutable SHA tag is also pushed.
- Avoid `:latest` in pilot templates.

---

## 4. Supply-Chain Hardening

These are assessed mostly as ACP/ETO or platform follow-ups:

- **Digest pinning** for critical base images, for example `FROM image@sha256:...`.
- **Image signing / provenance**, for example cosign; assess feasibility, likely ACP/ETO.
- **Scheduled base-image rebuilds** to pick up patches.
- **Deprecated-image policy** so EOL bases are flagged and removed.

---

## Responsibilities

| Area | Owner |
|------|-------|
| Secret-safe builds, `.dockerignore`, hadolint | **CST (pilot)** |
| Scanning tool trial in pilot CI | **CST (pilot)** |
| Org-wide scanning gate, signing infrastructure, base-image allowlist | **Platform / ETO**; route via Story 6 |

## Reporting a Vulnerability

This is a planning/pilot repository with docs plus executable templates/config. If a security issue is found in pilot **code, config, or templates**, raise it privately with the pilot lead rather than opening a public issue.

---

*Feedback or questions? Contact the page owner or comment below.*
