# Risks, Ownership and Decision Areas

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Cross-repository validation complete; adoption decisions active |
| **Last updated** | 2026-09-17 |

## Current Risk Register

| # | Risk | Status | Impact | Mitigation / next action |
|---|---|---|---|---|
| R1 | Optimisation is valid only for one repository | **Reduced** | High | SNS, PNR and PCDP provide three validation points with different scale/structure. |
| R2 | Faster pipeline silently runs fewer tests | **Controlled** | High | Feature/scenario inventory, completed-scenario, zero-test and Docker-required guards are part of the pattern. |
| R3 | Testcontainers cannot run in Drone DIND | **Resolved for validated repos** | High | Explicit Docker/Testcontainers CI configuration has been validated. |
| R4 | Repo-local `.drone.star` changes are overwritten by RepoSync | **Active** | High | Do not merge durable shared pipeline logic as repo-specific copies; implement common pieces in RepoSync. |
| R5 | A common pattern accidentally embeds SNS/PNR/PCDP-specific assumptions | **Active** | High | Centralise only lifecycle/dependency-graph patterns; keep topics, counts, aggregates and application wiring repo-specific. |
| R6 | Cache use makes clean builds unreliable | **Controlled** | High | Cache is an optimisation, not a dependency; clean build/runtime validation remains required. |
| R7 | Exact Docker packaging/runtime behaviour is no longer tested after moving tests in-JVM | **Controlled** | High | Built image is started and health/readiness validated after the Maven/Testcontainers phase. |
| R8 | Security work is weakened for speed | **Controlled** | High | Existing Trivy policy and scanning/reporting are retained; only DB preparation timing/dependency placement changed. |
| R9 | More performance tuning creates scope creep or business-test changes | **Controlled** | Medium | Final repository implementations were frozen after evidence-based cleanup; rejected experiments are not retained. |
| R10 | Central RepoSync adoption regresses validated timings or coverage | **Active** | High | Re-run representative repositories after centralisation and compare like-for-like workload/coverage. |

## Decision / Ownership Areas

| Area | Current decision | Durable owner / route | Status |
|---|---|---|---|
| Testcontainers CI execution | Validated in Drone Kubernetes + DIND | RepoSync/shared pipeline for common env/step wiring; repo code for test lifecycle | Validated; centralisation pending |
| Docker/BuildKit cache strategy | Validated pattern; measure per repo | RepoSync/shared pipeline for common build commands | Centralisation pending |
| Exact-image runtime validation | Required correctness gate | Common pipeline step + repo-specific health/runtime properties | Validated |
| Compose role | Reduced in validated CI path; retain where useful for local/exploratory workflows | Repository owners for local Compose; shared pipeline for CI orchestration | Validated |
| Trivy policy | Existing policy unchanged | Existing security/platform ownership | No policy change in this work |
| RepoSync adoption | Common reusable pipeline elements must move to central source | RepoSync/shared pipeline ownership | Next action |

## When a New Decision Record Is Required

Create/update an ADR when a change:

- alters the shared RepoSync-generated pipeline;
- changes the mandatory validation surface;
- changes security-gating policy rather than execution order;
- introduces a common abstraction used by multiple repos;
- requires an exception for a repository that cannot follow the common pattern.

