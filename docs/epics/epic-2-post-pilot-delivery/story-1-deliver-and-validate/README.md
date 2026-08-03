# E2-S1 — Deliver and Validate Post-Pilot Outcomes

**Epic:** [Post-Pilot Container and CD Delivery](../README.md)  
**Status:** Proposed / New  
**Depends on:** Pilot evidence plus task-specific repository, RepoSync/platform and release-owner approval

## Goal

Manage the approved SNS productionisation work and the separate CD review through a small task set while preserving independent evidence, ownership and completion gates.

## Workstream boundaries

| Workstream | Tasks | Completion boundary |
|---|---|---|
| SNS repository delivery | E2-S1.1–E2-S1.5 | Image build, Redis/Kafka/Schema Registry implementation, full-E2E topology, local validation, real CI evidence and an approved disposition |
| CD pipeline review/design | E2-S1.6–E2-S1.7 | Current-state evidence, owner decisions and a target delivery plan; no migration implementation |

Completion of one workstream does not imply completion or approval of the other.

## Tasks

| ID | Task | Status | Depends on |
|---|---|---|---|
| E2-S1.1 | [Implement validated SNS image-build changes](./task-1-sns-image-build.md) | Proposed / New | Story 3 evidence and owner approval |
| E2-S1.2 | [Productise the opt-in Redis Testcontainers workflow](./task-2-redis-testcontainers.md) | Proposed / New | Story 4 evidence |
| E2-S1.3 | [Implement Kafka and Schema Registry Testcontainers coverage](./task-3-kafka-schema-registry-testcontainers.md) | Proposed / New | SNS integration-test code evidence and owner approval |
| E2-S1.4 | [Implement and validate the target SNS integration topology](./task-4-compose-e2e-topology.md) | Proposed / New | E2-S1.2/E2-S1.3 plus Story 5 service-role evidence |
| E2-S1.5 | [Validate approved SNS changes in the real delivery path](./task-5-sns-delivery-validation.md) | Proposed / New | Approved E2-S1.1–E2-S1.4 output and required platform route |
| E2-S1.6 | [Review the current CD pipeline](./task-6-review-cd-pipeline.md) | Proposed / New | Current pipeline/configuration access and owners |
| E2-S1.7 | [Define the CD target design and delivery plan](./task-7-define-cd-target.md) | Proposed / New | E2-S1.6 evidence and owner decisions |

## Story acceptance criteria

- [ ] Each task preserves its evidence and non-claim boundaries.
- [ ] The implemented SNS path covers Redis, Kafka, Schema Registry and the command/snapshot scenarios that require them.
- [ ] Optional/supporting Compose services receive evidence-based dispositions rather than assumed migration or removal.
- [ ] SNS and CD workstreams retain independent owners, dependencies and approval states.
- [ ] Implementation evidence and review/design evidence are not conflated.
- [ ] Failures, retries, unresolved questions and unapproved candidates remain explicit.
- [ ] No production, CI or CD adoption is claimed before the relevant owner decision.
