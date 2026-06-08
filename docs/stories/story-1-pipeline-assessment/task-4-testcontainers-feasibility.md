# T1.4 — Assess Testcontainers feasibility in Drone/DIND

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.4 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `testcontainers`, `drone`, `dind`, `feasibility` |
| **Sprint** | Week 1 |
| **Depends on** | T1.1, T1.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Testcontainers requires Docker daemon access from within the test JVM. The current Drone pipeline provides DIND but it's unclear whether the Maven step can reach it. The ECR pipeline already sets `TESTCONTAINERS_RYUK_DISABLED=true` — this suggests prior exploration but also a known compatibility issue. Without confirming feasibility, Story 4 cannot determine whether Testcontainers will work in CI or only locally.

## Goal
Determine whether Testcontainers can run in the Drone CI pipeline and document any constraints.

## Scope
Investigate:
- Can the `mvn clean install` step access `DOCKER_HOST=tcp://docker:2375`? (currently it may not have this env var)
- Does `TESTCONTAINERS_RYUK_DISABLED=true` need to be set? What are the cleanup implications?
- Is `TESTCONTAINERS_CHECKS_DISABLE=true` needed to skip pre-flight checks?
- Can Testcontainers pull images through the DIND service (registry access, network)?
- Is there a Drone step timeout that would kill long-running container startups?
- Would Testcontainers conflict with the existing docker-compose usage in the same pipeline?

Known findings from `.drone.star`:
- ECR pipeline Maven step already has `TESTCONTAINERS_RYUK_DISABLED=true`
- DIND service is named `docker` and exposes port 2375
- The Maven step image is `quay.io/ukhomeofficedigital/ileap-java17-mvn`

## Decision outcomes (fill in after investigation)
- [ ] **CI feasible:** Testcontainers can run in CI with these env vars: _TBD_
- [ ] **CI feasible with constraints:** works but with limitations: _TBD_
- [ ] **Local only:** Testcontainers cannot run in CI; pilot stays local-only (fallback per ADR-0002)

## Acceptance criteria
- [ ] Docker daemon accessibility from Maven step is confirmed or denied
- [ ] Required environment variables for Testcontainers in Drone are documented
- [ ] Ryuk disabled implications are understood and documented
- [ ] A clear "feasible / feasible with constraints / local only" decision is made
- [ ] Finding informs Story 4 scope (CI or local-only)
