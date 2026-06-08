# T1.2 — Identify local vs RepoSync-controlled change boundaries

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.2 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | 1 |
| **Priority** | Must |
| **Labels** | `drone`, `reposync`, `ownership`, `boundaries` |
| **Sprint** | Week 1 |
| **Depends on** | T1.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
RepoSync owns the central pipeline source of truth. The pilot must know exactly which files/changes are repo-local vs centrally controlled so local work stays realistic and reusable pipeline changes can be shaped for ACP/RepoSync.

## Goal
Produce a clear table of what the pilot team can change locally and what should become an ACP/RepoSync change request or recommendation.

## Scope
Classify:
- **Repo-local (safe to change):** Dockerfile, `.dockerignore`, Maven profiles, `pom.xml` dependencies, test source code, `application-*.yml`, docker-compose files used by Maven plugin.
- **RepoSync-controlled (requires coordination):** `.drone.star`, pipeline steps/ordering, DIND image, Drone secrets, service definitions.
- **Unclear / confirm:** docker-compose files invoked by the pipeline (is the compose file in the repo or generated?), Maven step environment variables.

## Acceptance criteria
- [ ] A clear "local vs central" classification exists
- [ ] The RepoSync source repo and change request process are identified
- [ ] The pilot team knows who to contact for central changes
- [ ] Any centrally controlled files that appear local are flagged
