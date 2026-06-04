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
Some improvements are safe to own inside CST/Cerberus Delivery; others touch CI tooling (ACP) or wider platform patterns (DSA ETO/Enabling/CIT). Classification without a board/owner recommendation is incomplete — both must happen together.

## Goal
Classify each optimisation item into the three ownership categories and recommend which board/owner should carry it forward.

## Scope
**Classify** each item:
- **CST / Cerberus Delivery**: baseline measurement, Dockerfile review, `.dockerignore`, local layering experiment, Testcontainers local prototype, Compose review. Subject to agreement with Thomas Reddy.
- **ACP (CI/CD tooling)**: `.drone.star` / RepoSync changes, DIND environment, BuildKit enablement, Testcontainers CI environment variables, CI cache infrastructure. Requires ACP prioritisation.
- **DSA ETO / Enabling / CIT**: org base images, shared engineering templates, reusable Testcontainers libraries, cross-project adoption model, remote cache infrastructure. Subject to DSA Tech Strategy alignment.

**Recommend** for each item one of:
- CST / Cerberus Delivery board
- ACP board
- DSA ETO / Enabling board
- Shared visibility only
- Further discussion needed

## Acceptance criteria
- [ ] Each item is classified into CST, ACP, or DSA ETO/Enabling with short rationale
- [ ] Each item is mapped to a suggested owner/board
- [ ] No wider-impact item is progressed without appropriate visibility
- [ ] Assumptions are documented
