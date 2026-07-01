# Story 3 - Docker Build Optimisation: Summary

| Field | Value |
|-------|-------|
| **Status** | Draft Story 3 preparation - task definitions aligned; implementation and after-measurements not started |
| **Date updated** | 2026-07-01 |

---

## Deliverables

| Task | File | Current state |
|------|------|---------------|
| T3.1 - Review Dockerfile & build context | [T3.1-review-dockerfile.md](./T3.1-review-dockerfile.md) | Draft analysis aligned to Story 1/2 evidence |
| T3.2 - Add or validate .dockerignore | [T3.2-dockerignore.md](./T3.2-dockerignore.md) | Candidate proposal only; ownership/apply step pending |
| T3.3 - Layering / cache improvement | [T3.3-layering-improvement.md](./T3.3-layering-improvement.md) | Prototype plan only; production change pending RepoSync route |
| T3.4 - Measure impact | [T3.4-measure-impact.md](./T3.4-measure-impact.md) | Measurement plan updated; after-values pending |

---

## Baseline To Use

Story 3 should use measured Story 2 values, not the older static estimates.

| Metric | Measured before-state | Source |
|--------|-----------------------|--------|
| CI elapsed duration | Average `13:35`, median `13:31` from N=10 successful SNS CI sample | T2.2 |
| `Command Adaptor` visible CI step | Average `11:01`, range `10:49-11:18` | T2.2 |
| Local Docker cold/no-cache build | `real 1m17.855s` | T2.3 |
| Local Docker warm cached build | `real 0m0.851s` | T2.3 |
| Final image size | `906MB` | T2.3 |
| Full Docker build context transferred | `191.27MB` | T2.3 |
| Base/rootfs layers visible in history | `165MB + 300MB` | T2.3 |
| Executable JAR layer | `173MB` | T2.3 |
| `yum install/update` layer | `249MB` | T2.3 |
| Docker ignore status | No Docker ignore file found in SNS local checkout | T2.3 |

The `Command Adaptor` CI step is a cost-concentration signal, not an isolated Docker build timer. Step durations may overlap and must not be summed or converted directly into exact CI elapsed-time savings.

---

## Story 3 Direction

Recommended first Story 3 path:

1. Confirm `.dockerignore` ownership.
2. Apply or propose a `.dockerignore` that keeps only the current Dockerfile's required runtime artefacts.
3. Prototype the lowest-risk Dockerfile layer-order change locally: move expensive package/envconsul/user setup before frequently changing application artefact copies while preserving the current packaging lifecycle.
4. Measure before/after using the T2.3 baseline.
5. Route durable Dockerfile changes through RepoSync, with ACP/image-source confirmation for any runtime base-image change.

---

## Not Claimed

- No Story 3 Jira task is marked done here.
- No production Dockerfile, `.dockerignore`, `.drone.star` or Docker Compose change is claimed as applied.
- No image-size or build-time reduction is claimed until after-values are measured.
- No direct public image switch is recommended without approved image-source / Artifactory validation.
- DVLA and RoRo TSV support structural portability only; they do not provide measured SNS after-values.

---

## Open Items

| # | Item | Why it matters |
|---|------|----------------|
| 1 | Confirm whether `.dockerignore` is repo-local or RepoSync-managed | Determines whether it can be added directly or must go through the template route |
| 2 | Confirm production Dockerfile change route with RepoSync | Current Dockerfile appears centrally controlled |
| 3 | Confirm approved smaller Java runtime image path | Required before recommending any base-image replacement |
| 4 | Build and measure a local candidate image | Required before keep/adjust recommendation |
| 5 | Run Trivy/smoke checks on candidate image if built | Prevents trading build improvement for runtime/security regression |
