# T4.3 — Compare with docker-compose flow

**Story:** [Story 4 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.3 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 2 |
| **Priority** | Should |
| **Labels** | `testcontainers`, `docker-compose`, `comparison` |
| **Sprint** | Week 3 |
| **Depends on** | T4.2 |
| **Owner** | _TBD_ |
| **Status** | Not started |

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
