# T5.2 — Classify CST-local vs platform/ETO items

**Story:** [Story 5 — Findings, Ownership & Recommendations](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T5.2 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 5 — Findings, Ownership & Recommendations |
| **Estimate** | S |
| **Priority** | Must |
| **Labels** | `ownership`, `cst-vs-eto`, `classification` |
| **Sprint** | Week 4 |
| **Depends on** | T5.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Some improvements are safe to own and validate inside CST; others touch shared infrastructure and need platform/ETO involvement. Classifying them prevents progressing wider-impact changes without the right ownership.

## Goal
Classify each optimisation item as CST-local or platform/ETO, with a short rationale.

## Scope
Likely **CST-local**: baseline measurement, Dockerfile review, `.dockerignore`, local layering experiment, small Testcontainers pilot, Compose review.

Likely **platform/ETO**: organisation-maintained base images, shared CI/CD templates, BuildKit remote cache infrastructure, shared Testcontainers helper libraries, security-scanning standards, ephemeral-environment platform capability.

## Acceptance criteria
- [ ] Each item is classified CST-local vs platform/ETO
- [ ] Each classification has a short rationale
- [ ] Assumptions are documented
