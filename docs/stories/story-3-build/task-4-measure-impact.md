# T3.4 — Measure local & CI build impact

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.4 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | M |
| **Priority** | Should |
| **Labels** | `docker`, `metrics`, `before-after` |
| **Sprint** | Week 3 |
| **Depends on** | T3.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A change is only worth keeping if it measurably helps. Comparing against the Story 2 baseline turns the optimisation into evidence stakeholders can trust.

## Goal
Quantify the effect of the build changes on build time and image size.

## Scope
Compare against the baseline:
- local build time before/after
- CI build time before/after (if available)
- final image size before/after

## Acceptance criteria
- [ ] Before/after build metrics are captured
- [ ] Any improvement or regression is documented
- [ ] A keep/adjust recommendation is made
