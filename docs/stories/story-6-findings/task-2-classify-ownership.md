# T6.2 — Classify ownership & recommend target board

**Story:** [Story 6 — Findings, Ownership & Recommendations](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T6.2 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 6 — Findings, Ownership & Recommendations |
| **Estimate** | M |
| **Priority** | Must |
| **Labels** | `ownership`, `cst-vs-eto`, `classification`, `target-board` |
| **Sprint** | Week 4 |
| **Depends on** | T6.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Some improvements are safe to own inside CST; others touch shared infrastructure and need platform/ETO. Classification without a board/owner recommendation is incomplete — both must happen together so follow-up work lands in the right place immediately.

For this pilot, split platform/ETO follow-ups into **RepoSync/platform** items (`.drone.star`, DIND, central Drone templates) and **wider ETO/platform** items (org standards, shared base images, remote cache infrastructure).

## Goal
Classify each optimisation item as CST-local or platform/ETO, and for each item recommend which board/owner should carry it forward. Where an item is not CST-local, distinguish whether it belongs to RepoSync/platform or wider ETO/platform.

## Scope
**Classify** each item:
- Likely **CST-local**: baseline measurement, Dockerfile review, `.dockerignore`, local layering experiment, Testcontainers pilot, Compose review.
- Likely **RepoSync/platform**: `.drone.star` step changes, DIND environment, BuildKit enablement, Testcontainers CI environment variables, shared Drone templates.
- Likely **wider platform/ETO**: org base images, BuildKit remote cache infrastructure, shared Testcontainers helpers, security-scanning standards, ephemeral environments.

**Recommend** for each item one of:
- CST board
- RepoSync / platform board
- ETO / platform board
- shared visibility only
- further discussion needed

## Acceptance criteria
- [ ] Each item is classified CST-local vs platform/ETO with short rationale
- [ ] Platform/ETO items distinguish RepoSync/platform ownership from wider ETO/platform ownership
- [ ] Each item is mapped to a suggested owner/board
- [ ] No wider-impact item is progressed without appropriate visibility
- [ ] Assumptions are documented
