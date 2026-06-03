# T2.4 — Measure local & CI build impact

**Story:** [Story 2 — Docker Build Optimisation](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T2.4 | M | Should | _TBD_ | Not started | T2.3 |

## Why
A change is only worth keeping if it measurably helps. Comparing against the Story 1 baseline turns the optimisation into evidence stakeholders can trust.

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
