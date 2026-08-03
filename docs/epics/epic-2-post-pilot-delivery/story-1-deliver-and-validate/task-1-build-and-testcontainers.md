# E2-S1.1 — Implement SNS Build and Testcontainers Infrastructure

| Field | Value |
|---|---|
| **Type** | Production repository implementation and local validation |
| **Status** | Proposed / New — not started |
| **Depends on** | [Story 3 build evidence](../../../../solution/story-3/T3.4-impact-summary.md), [Story 4 Redis evidence](../../../../solution/story-4/T4.4-document-findings.md), inspected SNS snapshot `6bec7c579c9a244503850fad3072859fa809e41b` and repository/RepoSync owner approval |

## Goal

Implement the production SNS image-build changes and a reusable Testcontainers-managed infrastructure layer for Redis, Kafka and Schema Registry.

## Scope

- Apply the reviewed Dockerfile layer ordering and retain or finalise the targeted `.dockerignore`.
- Validate cold, no-change warm and real JAR-content-change builds, required artefacts and image startup behaviour.
- Productise the Redis container lifecycle and existing smoke/wiring path.
- Add supported Kafka and compatible Schema Registry containers, including any required Kafka-mode prerequisite.
- Resolve Redis, Kafka and Schema Registry endpoints dynamically; the new path must not depend on fixed host ports or Compose DNS names.
- Reproduce required readiness, topic creation, schema registration/lookup and cleanup/isolation behaviour.
- Exercise a representative Java producer-to-consumer Avro scenario repeatedly with unique test data.
- Document exact commands, image and dependency versions, Docker/Testcontainers environment, failures, retries and rollback.

## Acceptance criteria

- [ ] Production Dockerfile and `.dockerignore` changes are reviewable and preserve runtime artefacts.
- [ ] Cold, warm no-change and real JAR-change builds succeed; only captured measurements are reported.
- [ ] Redis, Kafka and Schema Registry start through a documented repository workflow.
- [ ] Tests and selected system-under-test paths receive dynamically resolved endpoints.
- [ ] Required topics and schemas are prepared without the existing Compose readiness helper.
- [ ] Redis functional checks and a representative Kafka/Avro producer-consumer scenario pass repeatedly with isolated data.
- [ ] Failure diagnostics and container cleanup are demonstrated.
- [ ] No image-size, cold-build, CI, speed, reliability or full-E2E benefit is claimed unless newly measured.
- [ ] Existing Compose/full-E2E behaviour remains available until E2-S1.2 and E2-S1.3 pass.

