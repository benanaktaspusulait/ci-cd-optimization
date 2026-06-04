# T5.3 — Recommend reduced Compose role

**Story:** [Story 5 — Docker Compose Rationalisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T5.3 |
| **Type** | Documentation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 5 — Docker Compose Rationalisation |
| **Estimate** | M |
| **Priority** | Should |
| **Labels** | `docker-compose`, `recommendation`, `rationalisation` |
| **Sprint** | Week 4 |
| **Depends on** | T4.4, T5.2 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The pilot's aim is not to remove Docker Compose, but to right-size its role: lean in CI, still useful locally. A clear recommendation prevents accidental over-reach and preserves valuable local workflows.

## Goal
Recommend a reduced/clarified Compose role for the pilot scope, informed by the Testcontainers findings (Story 4).

## Scope
Recommend:
- what should remain in Docker Compose
- what could move to Testcontainers
- what should remain for local debugging
- what should **not** be changed during the pilot

Target model:
```text
CI integration tests   → prefer Testcontainers where suitable
Local manual debugging → keep Docker Compose where useful
E2E / exploratory      → consider ephemeral environments selectively
```

## Acceptance criteria
- [ ] Recommendation is documented
- [ ] Risk / impact is noted
- [ ] Recommendation is reviewed with relevant stakeholders
