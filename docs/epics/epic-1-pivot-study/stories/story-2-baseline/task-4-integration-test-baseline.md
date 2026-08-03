# T2.4 — Capture integration-test baseline

**Story:** [Story 2 — Baseline & Pilot Scope](README.md)

| Field | Value |
|-------|-------|
| **ID** | T2.4 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 2 — Baseline & Pilot Scope |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `baseline`, `integration-test`, `docker-compose` |
| **Sprint** | Week 1 |
| **Depends on** | T2.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The Testcontainers (Story 4) and Compose (Story 5) work both depend on understanding how integration tests run today, what they depend on, and where the pain is. This task captures that starting picture.

## Goal
Document how integration tests currently start and behave for the selected repository.

## Scope
- How integration tests are currently started (command / pipeline step).
- Docker Compose dependencies involved.
- Startup / wait time before tests can run.
- Known flaky or environment-related issues, if any.

## Acceptance criteria
- [ ] Current integration-test setup is documented
- [ ] Required dependencies are listed
- [ ] Known pain points / flaky behaviours are captured
