# Story 1 — Drone/RepoSync Pipeline Assessment

**Epic:** [Container & CI/CD Optimisation Pilot](../../../../../README.md)
**Depends on:** — (gate for the entire pilot) · **Parallel with:** —

## Goal
Understand the centrally managed Drone pipeline structure, establish what can be changed locally vs what requires RepoSync/platform coordination, and assess feasibility of Testcontainers and BuildKit in the current CI setup.

> **Scope boundary:** this story assesses the **CI pipeline** (per-adaptor repo, `.drone.star`). The **deploy pipeline** (MMA service repo → Helm → Kubernetes) is a separate concern and is documented as context but not optimised by this pilot.

## Why
The FDP adaptor pipelines are generated from a `.drone.star` file managed via RepoSync. Local pipeline changes are not durable, so the pilot must separate repo-local proof points from reusable changes that should be proposed through ACP/RepoSync.

## Acceptance criteria
- [ ] `.drone.star` pipeline structure is documented (steps, services, DIND usage)
- [ ] Local vs RepoSync-controlled change boundaries are clearly defined
- [ ] CI pipeline steps and Docker Compose usage are mapped
- [ ] Testcontainers feasibility in Drone is assessed (DIND access, Ryuk, DOCKER_HOST)
- [ ] BuildKit feasibility in current DIND setup is assessed
- [ ] Findings inform which later stories can proceed locally vs need central coordination

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T1.1 | [Review .drone.star pipeline structure](task-1-review-drone-star.md) | 2 | Must | Not started |
| T1.2 | [Identify local vs RepoSync boundaries](task-2-local-vs-central.md) | 1 | Must | Not started |
| T1.3 | [Map CI steps, DIND and Compose usage](task-3-map-ci-steps.md) | 2 | Must | Not started |
| T1.4 | [Assess Testcontainers feasibility in Drone](task-4-testcontainers-feasibility.md) | 2 | Must | Not started |
| T1.5 | [Assess BuildKit/cache feasibility](task-5-buildkit-feasibility.md) | 1 | Should | Not started |
