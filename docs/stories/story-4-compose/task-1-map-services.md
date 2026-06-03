# T4.1 — Map services started by docker-compose

**Story:** [Story 4 — Docker Compose Rationalisation](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T4.1 | S | Must | _TBD_ | Not started | T1.4 |

## Why
You cannot rationalise what you have not mapped. A clear inventory of compose services is the foundation for deciding what is genuinely needed in CI.

## Goal
Produce a complete inventory of the services the pilot repository starts via docker-compose.

## Scope
For each service capture:
- service name
- image / build source
- dependency relationships
- exposed ports
- purpose, if known

## Acceptance criteria
- [ ] All Compose services are listed
- [ ] Dependencies between services are understood
- [ ] Services with unclear purpose are flagged for review
