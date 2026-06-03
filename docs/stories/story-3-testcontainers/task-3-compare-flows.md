# T3.3 — Compare with docker-compose flow

**Story:** [Story 3 — Testcontainers Pilot](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T3.3 | M | Should | _TBD_ | Not started | T3.2 |

## Why
The decision to adopt Testcontainers should rest on a like-for-like comparison, not impressions. Comparing both flows on the same dependency makes the trade-offs explicit.

## Goal
Compare the Testcontainers-based flow against the existing docker-compose flow for the selected dependency.

## Scope
Compare:
- setup / startup time
- test runtime
- complexity
- local developer experience
- CI suitability
- isolation / determinism

## Acceptance criteria
- [ ] Comparison is documented across the dimensions above
- [ ] Benefits and drawbacks are identified
- [ ] A recommendation is made on whether to continue with Testcontainers for further tests
