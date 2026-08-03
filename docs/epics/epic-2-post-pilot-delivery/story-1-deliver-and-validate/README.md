# E2-S1 — Deliver and Validate Post-Pilot Outcomes

**Epic:** [Post-Pilot Container and CD Delivery](../README.md)  
**Status:** Proposed / New  
**Depends on:** Pilot evidence plus task-specific repository, RepoSync/platform and release-owner approval

## Goal

Deliver the approved SNS build, integration-test, CI and CD transition through five outcome-focused tasks while preserving independent evidence, ownership and completion gates.

## Workstream boundaries

| Workstream | Tasks | Completion boundary |
|---|---|---|
| SNS repository implementation | E2-S1.1–E2-S1.3 | Image build, Redis/Kafka/Schema Registry implementation and equivalent command/snapshot full-E2E coverage |
| CI adoption | E2-S1.4 | Real branch/MR evidence, rollback and an owner-approved disposition |
| CD transition | E2-S1.5 | Current-state evidence and target design may start earlier; migration, rollout and deployment validation wait for E2-S1.4 adoption evidence |

Completion of any workstream does not imply completion or approval of another.

## Tasks

| ID | Task | Status | Depends on |
|---|---|---|---|
| E2-S1.1 | [Implement validated SNS image-build changes](./task-1-sns-image-build.md) | Proposed / New | Story 3 evidence and repository/RepoSync owner approval |
| E2-S1.2 | [Implement Testcontainers infrastructure](./task-2-testcontainers-infrastructure.md) | Proposed / New | Story 4 evidence, inspected SNS code and repository-owner approval |
| E2-S1.3 | [Implement and validate the full SNS integration topology](./task-3-e2e-compose-topology.md) | Proposed / New | E2-S1.2 plus Story 5 service-role evidence |
| E2-S1.4 | [Validate CI and decide adoption](./task-4-ci-validation-adoption.md) | Proposed / New | Approved E2-S1.1–E2-S1.3 output and required platform route |
| E2-S1.5 | [Deliver the CD target transition](./task-5-cd-transition.md) | Proposed / New | E2-S1.4 before migration; current CD evidence, target owners and implementation approval |

## Responsibility balance

| Task | Owns | Does not own |
|---|---|---|
| E2-S1.1 | Dockerfile, `.dockerignore`, build/runtime image evidence | Testcontainers, full-E2E topology, CI adoption or CD |
| E2-S1.2 | Redis/Kafka/Schema Registry lifecycle, reusable test fixtures, `MinimalRedisTest` disposition and dependency-level functional checks | Full command/snapshot equivalence, default CI or Compose removal |
| E2-S1.3 | Existing Cucumber/`SnsSteps`/Maven-profile migration, command/snapshot equivalence, application-container boundary and Compose service dispositions | Real-CI adoption or CD migration |
| E2-S1.4 | Updated existing-suite execution in real branch/MR CI, CI rollout/rollback and adoption decision | Deployment/CD architecture or environment rollout |
| E2-S1.5 | CD current state, target approval, implementation and deployment validation | Reopening Testcontainers or CI-topology decisions without new evidence |

## Recommended execution order

1. E2-S1.1 and E2-S1.2 may start independently once their respective owners approve them.
2. E2-S1.3 starts after E2-S1.2 supplies the dependency layer.
3. E2-S1.4 starts after E2-S1.1 and E2-S1.3 produce approved candidates.
4. E2-S1.5 discovery and target-design work may run in parallel, but its migration stage cannot start until E2-S1.4 records the adopted SNS delivery path and the CD target is separately approved.

## Story acceptance criteria

- [ ] Each task preserves its evidence and non-claim boundaries.
- [ ] The implemented SNS path covers Redis, Kafka, Schema Registry and the command/snapshot scenarios that require them.
- [ ] Existing integration tests are migrated without silently dropping scenarios, tags or assertions.
- [ ] Optional/supporting Compose services receive evidence-based dispositions rather than assumed migration or removal.
- [ ] SNS and CD workstreams retain independent owners, dependencies and approval states.
- [ ] Implementation evidence and review/design evidence are not conflated.
- [ ] Failures, retries, unresolved questions and unapproved candidates remain explicit.
- [ ] No production, CI or CD adoption is claimed before the relevant owner decision.
