# T2.2 — Capture CI/CD pipeline baseline

**Story:** [Story 2 — Baseline & Pilot Scope](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T2.2 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 2 — Baseline & Pilot Scope |
| **Estimate** | M |
| **Priority** | Must |
| **Labels** | `baseline`, `pipeline`, `metrics` |
| **Sprint** | Week 1 |
| **Depends on** | T2.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Pipeline duration is the headline metric stakeholders care about. Capturing it now, with a documented method, makes any later improvement provable rather than anecdotal.

## Goal
Record current CI/CD pipeline timings for the selected repository.

## Scope
- Capture average pipeline duration.
- Break down by stage: build, unit test, integration test.
- Capture failed-pipeline frequency if available.
- Note the data source and measurement method (e.g. last N runs from CI history).

## Acceptance criteria
- [ ] Baseline pipeline metrics are documented
- [ ] Data source / measurement method is recorded
- [ ] Metrics are in a form that can be re-measured later for before/after comparison
