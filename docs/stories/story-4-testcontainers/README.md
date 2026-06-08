# Story 4 — Testcontainers Pilot

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** Story 2 · **Parallel with:** Story 3

## Goal
Prove whether Testcontainers can replace part of the docker-compose integration setup for one dependency, with better isolation and determinism.

> **Drone constraint:** CI feasibility depends on Story 1 findings (T1.4). The Drone pipeline uses DIND with `DOCKER_HOST=tcp://docker:2375`. Testcontainers may need `TESTCONTAINERS_RYUK_DISABLED=true` (already present in ECR pipeline). If CI is not feasible, this story stays **local-only** — still valuable for proving the pattern.

## Why
Full docker-compose setups can be slow to start, share hidden state, and cause flaky, environment-dependent failures. Testcontainers offers isolated, deterministic, per-test environments. The stronger value here is reliability and local/CI consistency — not only speed.

## Acceptance criteria
- [ ] One candidate dependency/test selected with rationale
- [ ] Testcontainers setup implemented or prototyped and connecting successfully
- [ ] Flow compared with the existing docker-compose flow
- [ ] Findings, constraints and a continue/stop recommendation documented

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T4.1 | [Select candidate dependency/test](./task-1-select-candidate.md) | 1 | Must | Not started |
| T4.2 | [Implement Testcontainers setup](./task-2-implement-setup.md) | 3 | Must | Not started |
| T4.3 | [Compare with docker-compose flow](./task-3-compare-flows.md) | 2 | Should | Not started |
| T4.4 | [Document findings & constraints](./task-4-document-findings.md) | 1 | Should | Not started |
