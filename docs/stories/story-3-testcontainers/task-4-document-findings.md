# T3.4 — Document findings & constraints

**Story:** [Story 3 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.4 |
| **Type** | Documentation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Testcontainers Pilot |
| **Estimate** | S |
| **Priority** | Should |
| **Labels** | `testcontainers`, `documentation`, `findings` |
| **Sprint** | Week 3 |
| **Depends on** | T3.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A pilot only pays off if its lessons are captured. Clear findings and constraints let stakeholders decide on wider adoption without repeating the experiment.

## Goal
Document what the Testcontainers pilot showed, including limits and a recommendation.

## Scope
Document:
- what was tested
- what worked / what did not
- performance observations
- reliability / isolation observations
- limitations
- recommended next steps

Apply the reuse policy: container reuse may be enabled locally for faster feedback, but disabled in CI for clean, deterministic runs with no hidden shared state.

## Acceptance criteria
- [ ] Findings are documented and shared
- [ ] Constraints are clearly identified
- [ ] A recommendation is available for stakeholders
