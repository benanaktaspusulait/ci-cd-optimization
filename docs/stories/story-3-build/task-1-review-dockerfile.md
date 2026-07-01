# T3.1 — Review current Dockerfile & build context

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.1 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `docker`, `dockerfile`, `build-context` |
| **Sprint** | Week 2 |
| **Depends on** | T2.1, T2.3, T1.5 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Optimisation should be evidence-led, not guesswork. Reviewing the current Dockerfile reveals where the cache breaks and which layers are rebuilt unnecessarily, so effort goes where it actually helps.

## Goal
Understand and document the current Dockerfile structure and build context against the measured Story 2 baseline, then identify concrete optimisation opportunities without claiming savings before measurement.

## Scope
Review:
- current base image
- layer ordering
- dependency installation steps
- COPY instructions
- build-context size
- unnecessary files pulled into the Docker context
- Dockerfile and `.dockerignore` ownership route
- approved image-source / Artifactory constraints for any runtime base-image change

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
| Docker ignore status | Not found in SNS local checkout |

DVLA and RoRo TSV may be used as structural portability evidence only unless direct measurements are captured for them.

## Acceptance criteria
- [ ] Current Dockerfile structure is documented
- [ ] Measured image-size, layer and build-context baseline values from T2.3 are referenced
- [ ] Cache-invalidation risks and repeatability concerns are identified
- [ ] RepoSync, `.dockerignore` ownership and approved image-source constraints are captured
- [ ] A prioritised list of optimisation opportunities is produced without unmeasured saving claims
