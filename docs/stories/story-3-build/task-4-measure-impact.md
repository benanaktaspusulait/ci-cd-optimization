# T3.4 — Measure local & CI build impact

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
| **Depends on** | T3.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A change is only worth keeping if it measurably helps. Comparing against the Story 2 baseline turns the optimisation into evidence stakeholders can trust.

## Goal
Quantify the effect of the build changes on build time, build context and image size using the measured SNS before-state from Story 2.

## Scope
Compare against the baseline:
- local build time before/after
- build context before/after
- final image size before/after
- CI build time before/after only if the CI change is actually run and the metric can be interpreted safely

Use these before-state metrics from T2.3 unless a newer measured baseline is captured:

| Metric | Before-state baseline |
|--------|-----------------------|
| Final image size | `906MB` |
| Cold local Docker build | `real 1m17.855s` |
| Warm cached local Docker build | `real 0m0.851s` |
| Full Docker build context transferred | `191.27MB` |

The T2.2 `Command Adaptor` Drone step is useful as CI cost-concentration evidence, but it is not an isolated Docker build timer. Do not subtract local Docker savings directly from total CI elapsed time unless CI after-measurements support that conclusion.

## Acceptance criteria
- [ ] Before/after local Docker build metrics are captured for the applied/prototyped change
- [ ] Before/after build-context and image-size metrics are captured
- [ ] CI impact is measured only where CI evidence exists; otherwise it is recorded as pending
- [ ] Any improvement or regression is documented
- [ ] A keep/adjust recommendation is made
