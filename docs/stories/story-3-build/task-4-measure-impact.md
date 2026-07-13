# T3.4 — Measure local Docker build and image impact

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.4 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 2 |
| **Priority** | Should |
| **Labels** | `docker`, `metrics`, `before-after` |
| **Sprint** | Week 3 |
| **Depends on** | T3.2 and T3.3 |
| **Owner** | _TBD_ |
| **Status** | Completed - T3.2 and T3.3 measurements consolidated into the impact summary |
| **Execution result** | [T3.4 impact summary](../../../solution/story-3/T3.4-impact-summary.md) |

## Why
A change is only worth keeping if it measurably helps. Comparing against the Story 2 baseline turns the optimisation into evidence stakeholders can trust.

## Goal
Compare before/after local Docker build impact using the measured SNS before-state from T2.3 and the measured Story 3 results from T3.2 and T3.3. Keep build-context reduction, image-size impact, cold/no-cache build behaviour, and warm-cache rebuild behaviour separate.

## Scope
Compare against the baseline:
- local build time before/after
- build context before/after
- final image size before/after
- runtime artefact preservation after `.dockerignore`
- no-change warm-cache rebuild vs real JAR-change warm-cache rebuild
- same-daemon local warm-cache benefit vs CI benefit
- CI build time before/after only if the CI change is actually run and the metric can be interpreted safely

T3.4 should treat T3.2 and T3.3 as separate optimisation effects:

- T3.2 measures targeted `.dockerignore` impact on Docker build context, image size and cold build behaviour.
- T3.3 measures local same-daemon warm-cache rebuild behaviour after a real application JAR content change.

Before-state from T2.3:

| Metric | Before-state baseline |
|--------|-----------------------|
| Final image size | `906MB` |
| Cold local Docker build | `real 1m17.855s` |
| Warm cached local Docker build | `real 0m0.851s` |
| Full Docker build context transferred | `191.27MB` |

T3.2 measured after-state:

| Metric | T3.2 after-state |
|--------|------------------|
| Build context transfer | `189B` |
| Cold build | `real 78.14s` |
| Final image size | `906MB` |
| Runtime artefacts | Confirmed present |

T3.3 measured after-state:

| Scenario | Current Dockerfile | Layer-order prototype |
|----------|--------------------|-----------------------|
| Warm rebuild after real JAR content change | `77.90s` / `75.82s` | `5.08s` / `4.62s` |

Result: the layer-order prototype is approximately `15-16x` faster for a same-daemon local warm-cache rebuild after a real application JAR content change.

Important measurement boundary:

The T2.3 warm cached local Docker build value (`real 0m0.851s`) represents a no-change warm cached rebuild. It should not be compared directly with the T3.3 warm-cache rebuild after a real application JAR content change. T3.3 measures a different and more meaningful scenario: whether the expensive setup layer is reused when the application artefact changes.

The T2.2 `Command Adaptor` Drone step is useful as CI cost-concentration evidence, but it is not an isolated Docker build timer. Do not subtract local Docker savings directly from total CI elapsed time unless CI after-measurements support that conclusion.

## Claim boundaries

- No CI saving is claimed.
- No cold-build improvement is claimed.
- No image-size reduction is claimed.
- No production Dockerfile change is claimed.
- T3.2 recommendation: keep `.dockerignore` as the proven build-context reduction, subject to repository vs RepoSync/platform ownership confirmation.
- T3.3 recommendation: carry forward the layer-order prototype to the ownership discussion; do not recommend production adoption without RepoSync/platform ownership confirmation.

## Acceptance criteria
- [x] Before/after local Docker build metrics are captured.
- [x] Before/after build-context and image-size metrics are captured.
- [x] T3.2 `.dockerignore` impact is summarised separately from T3.3 layer-order impact.
- [x] No-change warm-cache rebuild and real JAR-change warm-cache rebuild are not mixed.
- [x] Same-daemon local warm-cache benefit is scoped separately from CI benefit.
- [x] Production adoption is not recommended without RepoSync/platform ownership confirmation.
- [x] Production adoption recommendation distinguishes between "keep locally validated change" and "carry forward prototype for RepoSync/platform discussion".
- [x] Improvement or regression is documented.
- [x] CI impact is measured only if available; otherwise it is explicitly recorded as not measured.
- [x] No CI saving is claimed without CI measurement.
- [x] A keep/adjust recommendation is made.
