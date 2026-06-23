# Story 1 — Pipeline Assessment: Summary & Conclusions

| Field | Value |
|-------|-------|
| **Jira** | CST-2000 |
| **Status** | Analysis complete - ACP confirmations and CI smoke validations recorded as follow-up items |
| **Date completed** | 2026-06-11 |
| **Last updated** | 2026-06-23 (T1.5 ownership wording aligned with T1.2; smoke validation, decision and risk sections retained) |
| **Source analysed** | `.drone.star` (RepoSync v7.1.0), `docker-compose.yml`, `Dockerfile`, `.drone/slack-functions.sh`, `bin/*.sh` |

---

## Deliverables

| Task | File | Status |
|------|------|:------:|
| T1.1 — Review .drone.star pipeline structure | [T1.1-review-drone-star.md](./T1.1-review-drone-star.md) | ✅ |
| T1.2 — Local vs RepoSync boundaries | [T1.2-local-vs-central.md](./T1.2-local-vs-central.md) | ✅ |
| T1.3 — Map CI steps, DIND & Compose | [T1.3-map-ci-steps.md](./T1.3-map-ci-steps.md) | ✅ |
| T1.4 — Testcontainers feasibility | [T1.4-testcontainers-feasibility.md](./T1.4-testcontainers-feasibility.md) | ✅ |
| T1.5 — BuildKit/cache feasibility | [T1.5-buildkit-feasibility.md](./T1.5-buildkit-feasibility.md) | ✅ |

---

## Story 1 Acceptance Criteria

| # | Criterion | Status | Evidence |
|---|-----------|:------:|----------|
| 1 | `.drone.star` structure documented (steps, services, DIND) | ✅ | T1.1 — pipeline types, step ordering, DIND config |
| 2 | Local vs RepoSync-controlled boundaries defined | ✅ | T1.2 — classification table + ownership map |
| 3 | CI steps and Docker Compose usage mapped | ✅ | T1.3 — step-by-step map, DIND interactions |
| 4 | Testcontainers feasibility assessed (DIND, Ryuk, DOCKER_HOST) | ✅ | T1.4 — feasible with constraints, pending smoke validation |
| 5 | BuildKit feasibility in current DIND assessed | ✅ | T1.5 — tiered feasibility decision, pending smoke validation |
| 6 | Findings inform which later stories are local vs central | ✅ | This summary — "What Later Stories Can Do" table |

**Note:** Two feasibility items (Testcontainers CI, BuildKit CI) are assessed as *likely feasible* but carry an open smoke-validation step that needs a Drone run plus ACP confirmation of the DIND image. These are recorded as follow-up items, not blockers for local pilot work.

---

## Key Findings

### 1. Almost everything is RepoSync-controlled

The Dockerfile, docker-compose.yml, .drone.star, and bin scripts are all overwritten by RepoSync. The pilot's local scope is limited to:
- Java source/test code
- Maven config (pom.xml, profiles)
- Spring config
- Local hook scripts (`*-local.sh`)
- Potentially `.dockerignore` (not currently present — ownership needs confirmation)

### 2. MR CI feedback comes from branch pushes, not the `pull_request` event

**Confirmed from source:** Pushes to feature branches run the **full CI pipeline** (`ci_pipeline()` — build, integration tests, Trivy) via the `push` event (`else` branch in `main(ctx)`). GitLab shows these branch-push pipelines against the open MR, so developers **do** get CI feedback before merge.

The `pull_request` event itself generates only `blank_pipeline('GitLab MR')` (a single version-echo step), separate from the branch-push CI. Its purpose is unclear — possibly to avoid duplicate runs, possibly vestigial. **Needs confirmation with ACP.** Note: Sonar runs only on `develop`, so static analysis is not part of feature-branch CI.

### 3. Testcontainers is feasible-looking in CI (with RepoSync + ACP validation)

**Confirmed:** The ECR/Artifactory pipeline already sets `DOCKER_HOST=tcp://docker:2375` and `TESTCONTAINERS_RYUK_DISABLED=true` on its Maven step, so the pattern exists in this Drone/DIND setup. The CI pipeline's `mvn clean install` step lacks these env vars. Adding them requires a RepoSync MR, and the final CI claim still needs an ACP/DIND validation run. Local execution has no barriers.

### 4. BuildKit is likely available but unconfirmed

The ACP DIND image has no explicit version tag, so BuildKit support is **likely but unproven** until a smoke test runs in Drone. Multi-stage Dockerfile is expected to be the biggest local win (image-size reduction to be **measured** against the approved runtime base — no fixed percentage claimed before baseline). Cache mounts help within a single build but are ephemeral between CI runs. Remote registry cache requires ACP infrastructure that does not currently exist. See T1.5 for the tiered feasibility decision and required `.drone.star` changes.

### 5. Pipeline has measurable inefficiencies

- **Double Maven build:** CI step `mvn clean install` + `integration-tests` compose container `mvn -Pci-snapshot install`
- **Repeated `apk add docker-compose`:** in 5 DIND steps (~25-50s wasted)
- **Sequential wait checks:** two blocking compose-up calls in the Integration Tests step
- **Trivy report-only:** `--exit-code 0` means CRITICAL/HIGH vulnerabilities never fail the pipeline

### 6. Slack failure notification has a suspected issue

The `send_slack_failure` function in `.drone/slack-functions.sh` makes a second `send_slack_text` call where a channel name (`fdp-alarm-nonprod`) appears to be passed as the message text parameter rather than as a target channel. This needs confirmation with the team — it may be benign but unintended.

### 7. Docker Compose is orchestrated in two layers

Integration tests are driven by **two distinct Compose mechanisms**: the Drone pipeline's own `docker-compose up` steps (Kafka, Redis, aggregators, command-adaptor) and the Maven `docker-compose-maven-plugin` invoked inside the integration-tests container. The two layers are mutually exclusive by environment (Drone orchestrates in CI; the Maven plugin is used for local/other paths), but they overlap in intent and are a source of the double Maven build and coupling. This is the primary input for Story 5 (Compose rationalisation) — see T1.4 §7 for the full lifecycle map and which layer should own the lifecycle.

---

## What Later Stories Can Do Locally vs Centrally

| Story | Can do locally | Needs RepoSync/ACP |
|-------|---------------|-------------------|
| **S2 — Baseline** | Local build timing, image size measurement | Drone UI pipeline timing (read-only, no change needed) |
| **S3 — Build Optimisation** | Prototype multi-stage Dockerfile, test `.dockerignore`, local cache mounts, local measurements | Apply Dockerfile changes through RepoSync; apply `.dockerignore` through repo or RepoSync once ownership is confirmed; add `DOCKER_BUILDKIT=1` on the publish step |
| **S4 — Testcontainers** | Full local prototype with any dependency | CI execution (RepoSync MR for env vars) |
| **S5 — Compose Rationalisation** | Document and classify services | Modify docker-compose.yml (RepoSync MR) |
| **S6 — Findings** | Consolidate, classify ownership | Share with ACP/ETO stakeholders |

---

## Recommended Next Actions

1. **Confirm `.dockerignore` ownership** — if not synced, create one immediately (quick win)
2. **Capture Drone UI timings** — validate estimated durations against real pipeline data (T2.2)
3. **Ask ACP:** DIND Docker version, buildx availability, and the purpose of the separate `pull_request` pipeline
4. **Start Testcontainers locally** — no blockers for Phase 1 (T4.1/T4.2)
5. **Prototype multi-stage Dockerfile locally** — measure before/after image size (T3.1/T3.3)
6. **Confirm Slack failure behaviour** — is the second `send_slack_text` call intentional? (Low priority)
7. **Clarify Trivy governance** — is there an external process that reviews findings even though the pipeline doesn't gate?

---

## Security and Release Assurance Notes

### Vulnerability Scanning

**Trivy image scanning exists** in the CI pipeline. It scans the built command-adaptor image for CRITICAL and HIGH severity vulnerabilities (ignoring unfixed ones). However, it uses `--exit-code 0`, meaning the step **always succeeds** regardless of findings. Vulnerabilities are reported in Drone build logs but do not block the pipeline or prevent image publication.

**The gap is in release gating, not in the absence of scanning.** The scanning tool is present and running; what is missing is enforcement — a policy that says "fail the build if CRITICAL findings exist."

### What Is Not Visible in the Pipeline

The following security practices are **not visible** in `.drone.star` or associated pipeline files:

| Practice | Observation |
|----------|-------------|
| Dependency scanning (Maven/library CVEs) | Not configured as a pipeline step |
| Secret scanning (gitleaks, trufflehog) | Not configured as a pipeline step |
| SAST quality gate | Sonar runs on `develop` only; gating behaviour not visible |
| Image signing / attestation | Not configured |
| SBOM generation | Not configured |
| Vulnerability waiver / exception process | Not visible in pipeline config |
| Security threshold ownership | Not documented |

> **Important:** These practices may exist outside the pipeline (e.g. as Artifactory policies, separate governance workflows, or manual review processes). This assessment is limited to what is observable in the pipeline source files.

### MR Pipeline and Pre-Merge Assurance

**Confirmed:** Pre-merge CI feedback **does** exist — feature-branch pushes run the full CI pipeline (build, integration tests, Trivy), and GitLab displays these against the open MR. The separate `pull_request` event produces only a near-empty pipeline (version echo).

Nuances worth noting:
- **Sonar runs only on `develop`**, so static analysis is not part of feature-branch / MR CI.
- The dedicated `pull_request` pipeline does no real work; its purpose (deliberate vs vestigial) needs confirmation with ACP.
- Because Trivy is report-only (`--exit-code 0`), the feature-branch scan reports but does not gate.

### Image Pinning and Supply-Chain

Three CI/CD images use `latest` or no explicit tag:
- `acp/dind` — no tag visible (ACP-managed)
- `acp/trivy/client:latest` — unpinned
- `fdp-deploy:latest` — unpinned

This creates non-reproducible builds and theoretical supply-chain risk. Pinning to specific versions or digests would improve reproducibility.

### Recommendations

1. **Short-term:** Clarify whether a vulnerability governance process exists outside the pipeline
2. **Short-term:** Propose `--exit-code 1` for Trivy (or at minimum for CRITICAL) as a RepoSync MR — with a `.trivyignore` waiver mechanism
3. **Medium-term:** Clarify the role of the `pull_request` pipeline and consider adding Sonar to feature-branch CI (currently Sonar runs only on `develop`)
4. **Medium-term:** Investigate adding dependency scanning (Trivy fs or `mvn` audit)
5. **Long-term:** Pin all CI/CD images to specific tags or digests
