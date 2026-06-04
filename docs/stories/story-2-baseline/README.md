# Story 2 — Baseline & Pilot Scope

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** Story 1 · **Parallel with:** —

## Goal
Select the pilot repository and capture a trustworthy "before" state, so every later change can be measured and proven.

## Why
Without a baseline there is no way to prove whether an optimisation actually helped. This story fixes the pilot scope and records the starting numbers before any change is made.

## Acceptance criteria
- [ ] Pilot repository selected with documented rationale
- [ ] Pipeline, build, image-size and integration-test baselines captured
- [ ] Measurement method recorded so it can be repeated for the "after" comparison
- [ ] Baseline reviewed and agreed with stakeholders

## Tasks
| Task | Title | Est | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T2.1 | [Select pilot repository/service](./task-1-select-repo.md) | S | Must | Not started |
| T2.2 | [Capture CI/CD pipeline baseline](./task-2-pipeline-baseline.md) | M | Must | Not started |
| T2.3 | [Capture Docker build & image-size baseline](./task-3-build-image-baseline.md) | S | Must | Not started |
| T2.4 | [Capture integration-test baseline](./task-4-integration-test-baseline.md) | M | Must | Not started |
