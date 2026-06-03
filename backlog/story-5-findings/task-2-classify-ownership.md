# T5.2 — Classify CST-local vs platform/ETO items

**Story:** [Story 5 — Findings, Ownership & Recommendations](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T5.2 | S | Must | _TBD_ | Not started | T5.1 |

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
