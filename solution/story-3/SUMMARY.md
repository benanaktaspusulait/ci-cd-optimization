# Story 3 - Docker Build Optimisation: Summary

| Field | Value |
|-------|-------|
| **Status** | Story 3 complete - one keep-now change and one carry-forward prototype recorded |
| **Date updated** | 2026-07-14 |

---

## Deliverables

| Task | File | Current state |
|------|------|---------------|
| T3.1 - Review Dockerfile & build context | [T3.1-review-dockerfile.md](./T3.1-review-dockerfile.md) | Completed analysis; first candidate confirmed as targeted `.dockerignore` / build-context reduction |
| T3.2 - Add or validate .dockerignore | [T3.2-dockerignore.md](./T3.2-dockerignore.md) | Completed locally; context transfer measured at `189B`; durable ownership route still pending |
| T3.3 - Layering / cache improvement | [T3.3-layering-improvement.md](./T3.3-layering-improvement.md) | Measured and confirmed on the Home Office development machine (2 independent runs): same-daemon warm-cache rebuild after real JAR content change is ~15-16x faster with layer-order prototype (77.90s→5.08s, 75.82s→4.62s); no cold-build/image-size change; classified by T3.4 as carry-forward, with production change still pending RepoSync ownership route |
| T3.4 - Measure impact | [T3.4-impact-summary.md](./T3.4-impact-summary.md) | Completed; T3.2/T3.3 measurements consolidated with claim boundaries and keep/carry-forward decisions |

---

## Consolidated Outcome

Story 3 produced one validated keep-now change and one measured carry-forward prototype:

| Area | Before | After / Candidate | Story 3 decision | Claim boundary |
|---|---:|---:|---|---|
| Targeted `.dockerignore` / build context | `191.27MB` | `189B` | **Keep now** | Build-context reduction claimed |
| Final image size | `906MB` | `906MB`; `875MB` vs `875MB` in T3.3 same-session comparison | No image-size action | No image-size saving claimed |
| Cold/no-cache build | `real 1m17.855s` | `real 78.14s` / broadly similar | No cold-build action | No cold-build saving claimed |
| No-change warm build | `real 0m0.851s` | Separate from T3.3 JAR-change scenario | Context only | Do not compare directly |
| JAR-change warm rebuild | `75.82-77.90s` current Dockerfile | `4.62-5.08s` layer-order prototype | **Carry forward** | Claimed only as local same-daemon warm-cache benefit |
| CI impact | Not measured | Not measured | No CI recommendation | No CI saving claimed |

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
| T3.2 validated build context transfer | `189B` | T3.2 |
| Base/rootfs layers visible in history | `165MB + 300MB` | T2.3 |
| Executable JAR layer | `173MB` | T2.3 |
| `yum install/update` layer | `249MB` | T2.3 |
| Docker ignore status | No Docker ignore file found in SNS local checkout | T2.3 |

The `Command Adaptor` CI step is a cost-concentration signal, not an isolated Docker build timer. Step durations may overlap and must not be summed or converted directly into exact CI elapsed-time savings.

---

## Story 3 Direction

Recorded Story 3 decision from T3.4:

1. Keep the targeted `.dockerignore` candidate because local validation reduced build context while preserving required runtime artefacts.
2. Confirm `.dockerignore` durable ownership before claiming CI benefit or wider adoption.
3. Carry forward the measured layer-order prototype for RepoSync/platform ownership discussion; do not apply it to the production Dockerfile yet.
4. Measure CI applicability only if the ownership route is confirmed and a representative Drone run is available.
5. Route any durable Dockerfile change through RepoSync, with ACP/image-source confirmation for any future runtime base-image change.

---

## Not Claimed

- No production Dockerfile, `.drone.star` or Docker Compose change is claimed as applied.
- No image-size reduction, CI saving or cold-build-time improvement is claimed from Story 3.
- No CI benefit is claimed from the T3.3 layer-order prototype; the measured benefit is local same-daemon warm-cache rebuild after a real JAR content change.
- No broad adaptor-family `.dockerignore` rollout is claimed.
- No broad adaptor-family layer-order rollout is claimed.
- No direct public image switch is recommended without approved image-source / Artifactory validation.
- DVLA and RoRo TSV support structural portability only; they do not provide measured SNS after-values.

---

## Open Items

| # | Item | Why it matters |
|---|------|----------------|
| 1 | Confirm whether `.dockerignore` is repo-local or RepoSync-managed | Determines whether durable adoption can be a repo MR or must go through the template route |
| 2 | Confirm production Dockerfile change route with RepoSync | Current Dockerfile appears centrally controlled |
| 3 | Confirm approved smaller Java runtime image path | Required before recommending any base-image replacement |
| 4 | Measure layer-order behaviour in representative CI only if selected by RepoSync/platform | Required before claiming CI benefit or production applicability |
| 5 | Complete approved security scanning if the prototype advances | Local smoke checks passed; Trivy was blocked by the local Docker-socket environment |
