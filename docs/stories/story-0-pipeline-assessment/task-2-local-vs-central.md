# T0.2 — Identify local vs RepoSync-controlled change boundaries

**Story:** [Story 0 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T0.2 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 0 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | S |
| **Priority** | Must |
| **Labels** | `drone`, `reposync`, `ownership`, `boundaries` |
| **Sprint** | Week 1 |
| **Depends on** | T0.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
RepoSync overwrites local pipeline changes. The pilot must know exactly which files/changes survive vs which are centrally controlled — otherwise effort is wasted on changes that get overwritten.

## Goal
Produce a clear table of what the pilot team can change locally and what requires a RepoSync/platform change request.

## Scope
Classify:
- **Repo-local (safe to change):** Dockerfile, `.dockerignore`, Maven profiles, `pom.xml` dependencies, test source code, `application-*.yml`, docker-compose files used by Maven plugin.
- **RepoSync-controlled (requires coordination):** `.drone.star`, pipeline steps/ordering, DIND image, Drone secrets, service definitions.
- **Unclear / confirm:** docker-compose files invoked by the pipeline (is the compose file in the repo or generated?), Maven step environment variables.

## Acceptance criteria
- [ ] A clear "local vs central" classification exists
- [ ] The RepoSync source repo and change request process are identified
- [ ] The pilot team knows who to contact for central changes
- [ ] Any files that appear local but are overwritten are flagged
