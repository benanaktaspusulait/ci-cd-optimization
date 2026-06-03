# Story 3 — Testcontainers Pilot

**Epic:** [Container & CI/CD Optimisation Pilot](../../README.md)
**Depends on:** Story 1 · **Parallel with:** Story 2

## Goal
Prove whether Testcontainers can replace part of the docker-compose integration setup for one dependency, with better isolation and determinism.

## Why
Full docker-compose setups can be slow to start, share hidden state, and cause flaky, environment-dependent failures. Testcontainers offers isolated, deterministic, per-test environments. The stronger value here is reliability and local/CI consistency — not only speed.

## Acceptance criteria
- [ ] One candidate dependency/test selected with rationale
- [ ] Testcontainers setup implemented or prototyped and connecting successfully
- [ ] Flow compared with the existing docker-compose flow
- [ ] Findings, constraints and a continue/stop recommendation documented

## Tasks
| Task | Title | Est | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T3.1 | [Select candidate dependency/test](./task-1-select-candidate.md) | S | Must | Not started |
| T3.2 | [Implement Testcontainers setup](./task-2-implement-setup.md) | L | Must | Not started |
| T3.3 | [Compare with docker-compose flow](./task-3-compare-flows.md) | M | Should | Not started |
| T3.4 | [Document findings & constraints](./task-4-document-findings.md) | S | Should | Not started |
