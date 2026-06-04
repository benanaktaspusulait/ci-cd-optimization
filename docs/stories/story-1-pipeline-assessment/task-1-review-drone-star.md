# T1.1 — Review .drone.star pipeline structure

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.1 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | M |
| **Priority** | Must |
| **Labels** | `drone`, `pipeline`, `reposync`, `assessment` |
| **Sprint** | Week 1 |
| **Depends on** | — |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The `.drone.star` file defines the entire CI pipeline. Understanding its structure is the prerequisite for every other pilot task — without it, we cannot know what is feasible locally.

## Goal
Document the current Drone pipeline structure: what runs, in what order, with what services.

## Scope
- Obtain and review the `.drone.star` source (from the RepoSync source repo)
- Document pipeline types (CI, ECR, Artifactory, etc.)
- Document steps within each pipeline (order, images, commands)
- Document services (DIND, Kafka, Redis, etc.)
- Note any existing Testcontainers-related configuration (e.g. `TESTCONTAINERS_RYUK_DISABLED`)
- Note how MR/pull_request events are handled

## Acceptance criteria
- [ ] Pipeline types and their purposes are documented
- [ ] Step ordering and dependencies are mapped
- [ ] DIND service configuration is documented
- [ ] Existing Testcontainers workarounds are noted
- [ ] MR pipeline behaviour is confirmed (blank or full)
