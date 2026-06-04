# T5.1 — Consolidate pilot findings

**Story:** [Story 5 — Findings, Ownership & Recommendations](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T5.1 |
| **Type** | Documentation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 5 — Findings, Ownership & Recommendations |
| **Estimate** | M |
| **Priority** | Must |
| **Labels** | `findings`, `summary`, `consolidation` |
| **Sprint** | Week 4 |
| **Depends on** | T2.4, T3.4, T4.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Evidence spread across stories is hard to act on. A single consolidated summary turns the pilot into something stakeholders can review and decide on quickly.

## Goal
Bring all pilot evidence into one shareable findings summary.

## Scope
Consolidate:
- baseline (Story 1)
- build optimisation results (Story 2)
- Testcontainers comparison (Story 3)
- Docker Compose review (Story 4)
- Pipeline assessment findings (Story 0) — local vs RepoSync boundaries

Present as a clear before → after / observations narrative. Explicitly classify each item as:
- CST-local (repo changes, no RepoSync involvement)
- RepoSync/platform (`.drone.star` changes, DIND config, registry)
- ETO/wider (base images, shared templates, org-wide standards)

## Acceptance criteria
- [ ] A single consolidated findings summary exists
- [ ] It links back to the supporting story evidence
- [ ] It is in a form suitable for sharing with stakeholders
