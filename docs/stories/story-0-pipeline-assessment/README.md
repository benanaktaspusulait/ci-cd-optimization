# Story 0 — Drone/RepoSync Pipeline Assessment

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** — (gate for the entire pilot) · **Parallel with:** —

## Goal
Understand the centrally managed Drone pipeline structure, establish what can be changed locally vs what requires RepoSync/platform coordination, and assess feasibility of Testcontainers and BuildKit in the current CI setup.

## Why
The FDP adaptor pipelines are generated from a `.drone.star` file managed via RepoSync. Local changes are overwritten. Without understanding these boundaries first, the pilot risks proposing changes that cannot be implemented locally or that conflict with the central pipeline governance.

## Acceptance criteria
- [ ] `.drone.star` pipeline structure is documented (steps, services, DIND usage)
- [ ] Local vs RepoSync-controlled change boundaries are clearly defined
- [ ] CI pipeline steps and Docker Compose usage are mapped
- [ ] Testcontainers feasibility in Drone is assessed (DIND access, Ryuk, DOCKER_HOST)
- [ ] BuildKit feasibility in current DIND setup is assessed
- [ ] Findings inform which later stories can proceed locally vs need central coordination

## Tasks
| Task | Title | Est | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T0.1 | [Review .drone.star pipeline structure](./task-1-review-drone-star.md) | M | Must | Not started |
| T0.2 | [Identify local vs RepoSync boundaries](./task-2-local-vs-central.md) | S | Must | Not started |
| T0.3 | [Map CI steps, DIND and Compose usage](./task-3-map-ci-steps.md) | M | Must | Not started |
| T0.4 | [Assess Testcontainers feasibility in Drone](./task-4-testcontainers-feasibility.md) | M | Must | Not started |
| T0.5 | [Assess BuildKit/cache feasibility](./task-5-buildkit-feasibility.md) | S | Should | Not started |
