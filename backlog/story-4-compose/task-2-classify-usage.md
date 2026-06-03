# T4.2 — Classify services & usage

**Story:** [Story 4 — Docker Compose Rationalisation](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T4.2 | M | Must | _TBD_ | Not started | T4.1 |

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
