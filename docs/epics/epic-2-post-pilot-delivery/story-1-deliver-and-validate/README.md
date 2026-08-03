# E2-S1 — Deliver and Validate Post-Pilot Outcomes

**Epic:** [Post-Pilot Container and CD Delivery](../README.md)  
**Status:** Proposed / New  
**Depends on:** Pilot evidence plus task-specific repository, RepoSync/platform and release-owner approval

## Goal

Deliver the approved SNS build, integration-test, CI and CD transition through four outcome-focused tasks while preserving independent evidence, ownership and completion gates.

## Workstream boundaries

| Workstream | Tasks | Completion boundary |
|---|---|---|
| SNS repository implementation | E2-S1.1–E2-S1.2 | Image build, Redis/Kafka/Schema Registry implementation and equivalent command/snapshot full-E2E coverage |
| CI adoption | E2-S1.3 | Real branch/MR evidence, rollback and an owner-approved disposition |
| CD transition | E2-S1.4 | Current-state evidence, target decision, approved migration, rollout and deployment validation |

Completion of one workstream does not imply completion or approval of the other.

## Tasks

| ID | Task | Status | Depends on |
|---|---|---|---|
| E2-S1.1 | [Implement SNS build and Testcontainers infrastructure](./task-1-build-and-testcontainers.md) | Proposed / New | Story 3/4 evidence, inspected SNS code and owner approval |
| E2-S1.2 | [Implement and validate the full SNS integration topology](./task-2-e2e-compose-topology.md) | Proposed / New | E2-S1.1 plus Story 5 service-role evidence |
| E2-S1.3 | [Validate CI and decide adoption](./task-3-ci-validation-adoption.md) | Proposed / New | Approved E2-S1.1/E2-S1.2 output and required platform route |
| E2-S1.4 | [Deliver the CD target transition](./task-4-cd-transition.md) | Proposed / New | Current CD evidence, target owners and implementation approval |

## Story acceptance criteria

- [ ] Each task preserves its evidence and non-claim boundaries.
- [ ] The implemented SNS path covers Redis, Kafka, Schema Registry and the command/snapshot scenarios that require them.
- [ ] Optional/supporting Compose services receive evidence-based dispositions rather than assumed migration or removal.
- [ ] SNS and CD workstreams retain independent owners, dependencies and approval states.
- [ ] Implementation evidence and review/design evidence are not conflated.
- [ ] Failures, retries, unresolved questions and unapproved candidates remain explicit.
- [ ] No production, CI or CD adoption is claimed before the relevant owner decision.
