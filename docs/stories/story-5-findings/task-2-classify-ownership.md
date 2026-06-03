# T5.2 — Classify ownership & recommend target board

**Story:** [Story 5 — Findings, Ownership & Recommendations](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T5.2 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 5 — Findings, Ownership & Recommendations |
| **Estimate** | M |
| **Priority** | Must |
| **Labels** | `ownership`, `cst-vs-eto`, `classification`, `target-board` |
| **Sprint** | Week 4 |
| **Depends on** | T5.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Some improvements are safe to own inside CST; others touch shared infrastructure and need platform/ETO. Classification without a board/owner recommendation is incomplete — both must happen together so follow-up work lands in the right place immediately.

## Goal
Classify each optimisation item as CST-local or platform/ETO, and for each item recommend which board/owner should carry it forward.

## Scope
**Classify** each item:
- Likely **CST-local**: baseline measurement, Dockerfile review, `.dockerignore`, local layering experiment, Testcontainers pilot, Compose review.
- Likely **platform/ETO**: org base images, shared CI/CD templates, BuildKit remote cache infrastructure, shared Testcontainers helpers, security-scanning standards, ephemeral environments.

**Recommend** for each item one of:
- CST board
- ETO / platform board
- shared visibility only
- further discussion needed

## Acceptance criteria
- [ ] Each item is classified CST-local vs platform/ETO with short rationale
- [ ] Each item is mapped to a suggested owner/board
- [ ] No wider-impact item is progressed without appropriate visibility
- [ ] Assumptions are documented
