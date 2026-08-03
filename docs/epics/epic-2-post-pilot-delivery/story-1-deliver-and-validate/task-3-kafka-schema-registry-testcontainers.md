# E2-S1.3 — Implement Kafka and Schema Registry Testcontainers Coverage

| Field | Value |
|---|---|
| **Type** | Repository implementation and functional-equivalence validation |
| **Status** | Proposed / New — not started or measured |
| **Depends on** | Inspected SNS snapshot `6bec7c579c9a244503850fad3072859fa809e41b`, E2-S1.2 reusable lifecycle approach and repository-owner approval |

## Evidence basis

The inspected integration-test module uses Kafka producers/consumers and Avro deserialisation against a configured Schema Registry. Its local and CI Maven profiles point to Kafka and Schema Registry endpoints, while the current Compose readiness path creates Kafka topics and waits for Schema Registry. This establishes functional scope, not Testcontainers feasibility, speed or CI compatibility.

## Scope

- Add supported Testcontainers dependencies and lifecycle management for Kafka and a compatible Schema Registry, including any Kafka-mode prerequisite selected by the implementation.
- Expose mapped Kafka and Schema Registry endpoints dynamically; do not rely on fixed `localhost:9092`, `kafka:29092` or `schema-registry:8081` addresses for the new path.
- Reproduce topic creation, schema registration/lookup and readiness behaviour required by the selected command scenarios.
- Exercise the existing Java producer/consumer and Avro encode/decode path, including unique test data and cleanup/isolation behaviour.
- Keep command and snapshot scenario requirements separate; aggregator-dependent snapshot coverage is completed in E2-S1.4.
- Record exact images, versions, Docker/Testcontainers environment, commands, failures, retries and timings actually captured.

## Acceptance criteria

- [ ] Kafka and Schema Registry containers start through a documented opt-in repository workflow.
- [ ] Tests and the selected system-under-test path consume dynamically resolved endpoints.
- [ ] Required topics and schemas are prepared without dependence on the existing Compose readiness helper.
- [ ] At least one representative producer-to-consumer Avro scenario passes repeatedly with isolated test data.
- [ ] Failure diagnostics and container cleanup are demonstrated and documented.
- [ ] Existing Compose command/snapshot paths remain available until E2-S1.4/E2-S1.5 equivalence and adoption gates pass.
- [ ] No CI, performance, reliability, full-E2E replacement or Compose-reduction benefit is claimed without new evidence.

