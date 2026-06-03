# T1.4 — Capture integration-test baseline

**Story:** [Story 1 — Baseline & Pilot Scope](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T1.4 | M | Must | _TBD_ | Not started | T1.1 |

## Why
The Testcontainers (Story 3) and Compose (Story 4) work both depend on understanding how integration tests run today, what they depend on, and where the pain is. This task captures that starting picture.

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
