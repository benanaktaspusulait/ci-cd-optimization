# T6.1 — Consolidate pilot findings

**Story:** [Story 6 — Findings, Ownership & Recommendations](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T6.1 |
| **Type** | Documentation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 6 — Findings, Ownership & Recommendations |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `findings`, `summary`, `consolidation` |
| **Sprint** | Week 4 |
| **Depends on** | T3.4, T4.4, T5.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Evidence spread across stories is hard to act on. A single consolidated summary turns the pilot into something stakeholders can review and decide on quickly.

## Goal
Bring all pilot evidence into one shareable findings summary.

## Scope
Consolidate:
- baseline (Story 2)
- build optimisation results (Story 3)
- Testcontainers comparison (Story 4)
- Docker Compose review (Story 5)
- Pipeline assessment findings (Story 1) — local vs RepoSync boundaries

Present as a clear before → after / observations narrative. Explicitly classify each item as:
- CST-local (repo changes, no RepoSync involvement)
- RepoSync/platform (`.drone.star` changes, DIND config, registry)
- ETO/wider (base images, shared templates, org-wide standards)

Include a short target operating model recommendation: which parts should stay local to the pilot repo, which should be proposed for ACP/RepoSync distribution, and which are wider DSA ETO/Enabling considerations.

## Acceptance criteria
- [ ] A single consolidated findings summary exists
- [ ] It links back to the supporting story evidence
- [ ] It includes a target operating model / RepoSync distribution recommendation
- [ ] It is in a form suitable for sharing with stakeholders
