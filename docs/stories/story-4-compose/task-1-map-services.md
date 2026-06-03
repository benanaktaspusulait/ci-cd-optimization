# T4.1 — Map services started by docker-compose

**Story:** [Story 4 — Docker Compose Rationalisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.1 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Docker Compose Rationalisation |
| **Estimate** | S |
| **Priority** | Must |
| **Labels** | `docker-compose`, `mapping`, `inventory` |
| **Sprint** | Week 3 |
| **Depends on** | T1.4 |
| **Owner** | _TBD_ |
| **Status** | Not started |

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
