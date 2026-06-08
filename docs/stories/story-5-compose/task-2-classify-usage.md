# T5.2 — Classify services & usage

**Story:** [Story 5 — Docker Compose Rationalisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T5.2 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 5 — Docker Compose Rationalisation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `docker-compose`, `classification`, `ci-vs-local` |
| **Sprint** | Week 3 |
| **Depends on** | T5.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Not every service in the compose file is needed for CI tests — some exist only for local debugging or are leftovers. Classifying them is what makes a safe reduction possible.

## Goal
Classify each Compose service by necessity and by where it is actually used.

## Scope
For each service, mark:
- required for integration tests
- required only for local debugging
- optional / unclear
- potentially removable from the CI flow

Also note where Compose is invoked (CI vs local) and whether one file serves multiple purposes.

## Acceptance criteria
- [ ] Required test dependencies are identified
- [ ] Non-essential services are identified
- [ ] CI vs local usage is documented; mixed-purpose usage flagged
- [ ] Any uncertainty is recorded for follow-up
