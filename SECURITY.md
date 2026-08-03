# Security Plan

Concrete security practices for the pilot. Turns the high-level notes in [tech-notes](docs/epics/epic-1-pivot-study/stories/tech-notes.md#security--compliance) into an actionable plan. [← Back to overview](README.md)

> **Scope:** practices the pilot will apply or assess. Items needing org-wide infrastructure (shared scanners, signing infra) are flagged as **ACP/ETO** and routed via Story 6.

---

## 1. Secret management

| Concern | Approach |
|---------|----------|
| CI secrets (registry creds, tokens) | Store in **Drone secrets** (per-repo or organisation-level) — never in the repo. Drone encrypts secrets and injects them as environment variables at runtime. |
| Build-time secrets (e.g. Maven `settings.xml`) | Pass via **BuildKit secret mounts**, not `ARG`/`ENV` or `COPY` (see pattern below) |
| App runtime secrets | Injected at deploy time via the platform secret manager (e.g. **HashiCorp Vault** / cloud secret manager) — out of pilot scope to implement, in scope to document |
| Preventing leaks | `.dockerignore` excludes `.env`, key files; secret scanning in CI (see §2) |

**Secret-safe build pattern (BuildKit):**
```dockerfile
# secret is mounted only for this RUN, never baked into a layer
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    ./mvnw -B package
```
```bash
docker buildx build --secret id=maven_settings,src=$HOME/.m2/settings.xml .
```

**Rules**
- No secrets in image layers, build args, logs, or the repository.
- No real credentials in examples or fixtures — use placeholders.
- Rotate any credential that is suspected to have leaked.

---

## 2. Scanning policy

| What | Tool (candidate) | When | Pilot mode | Target gate |
|------|------------------|------|------------|-------------|
| Image vulnerabilities | **Trivy** or **Snyk** | Every pilot build | Report-only / warn | Fail on **Critical**; review High |
| Dependency vulnerabilities | Trivy / Snyk / `mvn` audit | On MR + weekly schedule | Report-only / warn | Fail on Critical |
| Secret scanning | **gitleaks** / **trufflehog** | On MR | Report-only until tool is chosen | Fail on any verified secret |
| SBOM generation | **Syft** (SPDX/CycloneDX) | On image build | Artefact attached to build | Required artefact |
| Base image freshness | scheduled rebuild + scan | Weekly | Report-only / warn | Flag outdated/EOL base images |

> Tool **choice** is CST-local for the pilot. A shared, org-wide scanning **standard / gate** is **ACP/ETO** — classify in Story 6.
> The template CI starts in report-only mode to avoid blocking before baseline data exists. Promote the target gates only after Story 2 captures the baseline and stakeholders agree the thresholds.

**Severity policy (target gate, after promotion)**
- **Critical:** block merge/build.
- **High:** review and decide (waiver with expiry if accepted).
- **Medium/Low:** track, don't block.

---

## 3. Policy as code

Container/image rules to enforce automatically rather than by review.

| Policy | Rule | How |
|--------|------|-----|
| No `root` runtime | Container must run as non-root `USER` | Dockerfile lint (**hadolint**) + image policy check |
| No unpinned base/job images | Base images and CI job images pinned to a version (digest for critical) | hadolint rule + CI grep/lint |
| No secrets in image | Built image contains no secret material | secret scan of built image |
| Healthcheck present | Long-running images define a healthcheck | hadolint / policy check |
| Approved base images | Use sanctioned base images only | policy check against allowlist *(ACP/ETO)* |

**Enforcement approach**
- Start with **hadolint** for Dockerfile rules (fast, local + CI).
- Express image/admission policies as code (**OPA/Conftest** or equivalent) where a gate is wanted.
- For the pilot, run policies in **warn** mode first; promote to **block** once stable.
- Release alias tags such as `:main` are allowed only when the immutable SHA tag is also pushed; avoid `:latest` in pilot templates.

---

## 4. Supply-chain hardening (assessed, mostly ACP/ETO)

- **Digest pinning** for critical base images (`FROM image@sha256:…`).
- **Image signing / provenance** (e.g. cosign) — assess feasibility, likely ACP/ETO.
- **Scheduled base-image rebuilds** to pick up patches.
- **Deprecated-image policy** so EOL bases are flagged and removed.

---

## Responsibilities

| Area | Owner |
|------|-------|
| Secret-safe builds, `.dockerignore`, hadolint | **CST (pilot)** |
| Scanning tool trial in pilot CI | **CST (pilot)** |
| Org-wide scanning gate, signing infra, base-image allowlist | **Platform / ETO** (route via Story 6) |

## Reporting a vulnerability

This is a planning/pilot repository with docs plus executable templates/config. If a security issue is found in pilot **code, config, or templates**, raise it privately with the pilot lead rather than opening a public issue.
