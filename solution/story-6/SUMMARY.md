# Story 6 — Findings, Ownership & Recommendations: Summary

| Field | Value |
|-------|-------|
| **Status** | Done |
| **Date** | 2026-06-11 |

---

## Deliverables

| Task | File | Status |
|------|------|:------:|
| T6.1 — Consolidate findings | [T6.1-consolidate-findings.md](./T6.1-consolidate-findings.md) | ✅ |
| T6.2 — Classify ownership | [T6.2-classify-ownership.md](./T6.2-classify-ownership.md) | ✅ |
| T6.3 — Share with stakeholders | [T6.3-share-stakeholders.md](./T6.3-share-stakeholders.md) | ✅ |

---

## Ownership Breakdown

| Owner | Count | First Action | Confidence |
|-------|:-----:|-------------|:----------:|
| **CST (do now)** | 12 items | Testcontainers local setup, Dockerfile validation, measurements | High |
| **ACP (RepoSync MR)** | 10 items | Dockerfile + .dockerignore MR → Testcontainers env vars MR → build-once-promote / single-orchestrator | Medium |
| **ETO (post-pilot)** | 7 items | Registry cache, shared base images, scanning gates | Low |

## Cross-Cutting Architectural Findings

Two system-level findings sit above the per-story wins and must be decided before central rollout (see T6.2 §5, T1.2 System-Level Architecture):

- **Dual container-lifecycle ownership** — Drone (CI) vs Maven (local), `skip.*`-selected; logic duplicated and diverged (6 vs 7 aggregators, two `CORE_TAG` paths). **Recommended option:** single owner via `mvn verify` (Maven) — decision open, see T1.2 Decision 1 / Decision Order.
- **Build/publish integrity gap** — the tested/scanned image is rebuilt before publish, so the shipped artifact is not the tested one. **Recommended option:** build-once-promote — decision open (T6.2 §5b).

## Pilot Result

### Confirmed Findings (from source code analysis)

- ✅ Dockerfile uses full JDK (`amazoncorretto:17`) — confirmed from Dockerfile
- ✅ No `.dockerignore` exists — confirmed from repository scan
- ✅ 20 services defined in docker-compose.yml — confirmed from file
- ✅ 6 aggregators + 11 infra/utility/debug services in compose
- ✅ Compose orchestration via RepoSync-controlled `.drone.star`
- ✅ Maven profiles `local`, `ci-cmd`, `ci-snapshot` exist in integration-tests pom.xml
- ✅ Java 17, JUnit 5 — Testcontainers compatible

### Projected Improvements (estimates pending local validation)

- ~65% image size reduction (target: ≥30%) — based on known base image sizes
- ~70% build time reduction (target: ≥30%) — based on layer analysis
- ~50% build context reduction (target: ≥50%) — based on directory structure
- ~97% test startup reduction (target: <30s) — based on Testcontainers benchmarks
- Test isolation: achievable with Testcontainers pattern
- Compose services classifiable and reducible

> ⚠️ Improvement percentages are structural estimates. Actual validation requires local docker build execution and Drone UI timing capture.
