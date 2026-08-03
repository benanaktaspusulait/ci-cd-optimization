# T3.1 — Confirm Dockerfile ownership route and select optimisation candidate

**Story:** [Story 3 — Docker Build Optimisation](README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.1 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `docker`, `dockerfile`, `build-context`, `ownership` |
| **Sprint** | Week 2 |
| **Depends on** | T2.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Optimisation should start from the measured T2.3 Docker/image baseline rather than repeating broad discovery. This task turns the T2.3 findings into an ownership route and one safe implementation candidate for Story 3.

## Goal
Use the T2.3 Docker/image baseline to confirm the RepoSync/platform ownership route and select one safe Docker/build-context optimisation candidate.

## Scope
Review the T2.3 evidence and decide:
- which Dockerfile or build-context candidate is safest to attempt first
- whether Dockerfile changes must go through RepoSync/platform ownership
- whether `.dockerignore` is repo-local or centrally managed
- whether any base-image option has an approved image-source / Artifactory / security route

Use the T2.3 measured SNS baseline as the starting point:

| Metric | Baseline |
|--------|----------|
| Final image size | `906MB` |
| Cold local Docker build | `real 1m17.855s` |
| Warm cached local Docker build | `real 0m0.851s` |
| Full Docker build context transferred | `191.27MB` |
| Base/rootfs layers visible in Docker history | `165MB + 300MB` |
| Executable JAR layer | `173MB` |
| `yum install/update` layer | `249MB` |
| Docker ignore status | Not found in SNS local checkout during T2.3 local file search |

DVLA and RoRo TSV may be used as structural portability evidence only unless direct measurements are captured for them.

Candidate options include:
- `.dockerignore` / build-context reduction while preserving runtime artefacts
- `yum install/update` layer repeatability review
- layer-order improvement for the current packaging-only Dockerfile
- runtime base-image review only after approved image-source / Artifactory validation

## Acceptance criteria
- [ ] T2.3 baseline is reviewed as the before-state.
- [ ] Dockerfile and `.dockerignore` ownership route is confirmed or documented.
- [ ] One safe optimisation candidate is selected.
- [ ] RepoSync/platform route is captured where required.
- [ ] No production/base-image change is recommended without image-source, Artifactory and security validation.
- [ ] No optimisation saving is claimed without before/after measurement.
