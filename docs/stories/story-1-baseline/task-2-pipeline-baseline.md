# T1.2 — Capture CI/CD pipeline baseline

**Story:** [Story 1 — Baseline & Pilot Scope](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T1.2 | M | Must | _TBD_ | Not started | T1.1 |

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
