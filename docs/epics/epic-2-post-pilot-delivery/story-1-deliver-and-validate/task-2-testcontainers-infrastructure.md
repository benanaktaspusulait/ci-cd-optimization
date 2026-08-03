# E2-S1.2 — Implement Testcontainers Infrastructure

| Field | Value |
|---|---|
| **Type** | Repository infrastructure implementation and local functional validation |
| **Status** | Proposed / New — not started |
| **Depends on** | [Story 4 Redis evidence](../../../../solution/story-4/T4.4-document-findings.md), inspected SNS snapshot `6bec7c579c9a244503850fad3072859fa809e41b` and repository-owner approval |

## Goal

Build a reusable Testcontainers-managed infrastructure layer for Redis, Kafka and Schema Registry without claiming full-E2E or CI adoption.

## Scope

- Productise the Redis container lifecycle and existing smoke/wiring path.
- Refactor `MinimalRedisTest` into a maintained infrastructure smoke test or replace it with reusable fixtures and remove it only after equivalent Redis checks exist; record the chosen disposition.
- Add supported Kafka and compatible Schema Registry containers, including any required Kafka-mode prerequisite.
- Resolve Redis, Kafka and Schema Registry endpoints dynamically for dependency-level tests; do not depend on fixed host ports or Compose DNS names in the new path.
- Reproduce required readiness, topic creation, schema registration/lookup and cleanup/isolation behaviour.
- Exercise a representative Java producer-to-consumer Avro scenario repeatedly with unique test data.
- Provide reusable lifecycle/configuration fixtures for Task 3 rather than duplicating container startup across individual test classes.
- Keep existing Compose command/snapshot paths available until E2-S1.3 and E2-S1.4 establish equivalence and adoption.
- Document exact commands, image and dependency versions, Docker/Testcontainers environment, failures and retries.

## Acceptance criteria

- [ ] Redis, Kafka and Schema Registry start through a documented repository workflow.
- [ ] `MinimalRedisTest` has an explicit keep/refactor/replace disposition and no validated Redis assertion is lost.
- [ ] Dependency-level test clients receive dynamically resolved endpoints; application/full-E2E wiring is completed in E2-S1.3.
- [ ] Required topics and schemas are prepared without the existing Compose readiness helper.
- [ ] Redis functional checks and a representative Kafka/Avro producer-consumer scenario pass repeatedly with isolated data.
- [ ] Reusable fixtures expose lifecycle and endpoint information to the existing integration-test suite without per-test-class infrastructure duplication unless explicitly justified.
- [ ] Failure diagnostics and container cleanup are demonstrated.
- [ ] Existing Compose/full-E2E behaviour remains available until later equivalence and CI gates pass.
- [ ] No CI, speed, reliability, full-E2E replacement or Compose-reduction benefit is claimed without new evidence.
