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
| **Depends on** | T3.2 or T3.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A change is only worth keeping if it measurably helps. Comparing against the Story 2 baseline turns the optimisation into evidence stakeholders can trust.

## Goal
Compare before/after local Docker build time, build context and image size using the measured SNS before-state from T2.3.

## Scope
Compare against the baseline:
- local build time before/after
- build context before/after
- final image size before/after
- CI build time before/after only if the CI change is actually run and the metric can be interpreted safely

Before-state from T2.3:

| Metric | Before-state baseline |
|--------|-----------------------|
| Final image size | `906MB` |
| Cold local Docker build | `real 1m17.855s` |
| Warm cached local Docker build | `real 0m0.851s` |
| Full Docker build context transferred | `191.27MB` |

The T2.2 `Command Adaptor` Drone step is useful as CI cost-concentration evidence, but it is not an isolated Docker build timer. Do not subtract local Docker savings directly from total CI elapsed time unless CI after-measurements support that conclusion.

## Acceptance criteria
- [ ] Before/after local Docker build metrics are captured.
- [ ] Before/after build-context and image-size metrics are captured.
- [ ] Improvement or regression is documented.
- [ ] CI impact is measured only if available.
- [ ] No CI saving is claimed without CI measurement.
- [ ] A keep/adjust recommendation is made.
