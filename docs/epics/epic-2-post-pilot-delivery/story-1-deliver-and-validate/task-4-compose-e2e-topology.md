# E2-S1.4 — Implement and Validate the Target SNS Integration Topology

| Field | Value |
|---|---|
| **Type** | Full integration-path implementation and equivalence validation |
| **Status** | Proposed / New — not started or approved |
| **Depends on** | E2-S1.2, E2-S1.3, [T5.1 current-scope evidence](../../../../solution/story-5/T5.1-validate-compose-scope.md), [T5.2 target-role decision](../../../../solution/story-5/T5.2-decide-compose-role.md) and required owners |

## Goal

Implement a complete, supportable SNS integration-test topology for the existing command and snapshot scenarios. “Complete” means equivalent required coverage, not converting every Compose service to Testcontainers.

## Scope

- Build a scenario-to-service matrix for command and snapshot profiles before changing orchestration.
- Integrate the Redis, Kafka and Schema Registry workflows from E2-S1.2/E2-S1.3 with the command adaptor and required aggregator application containers.
- Decide and implement the orchestration boundary for application containers: Compose, Testcontainers generic containers or a justified hybrid.
- Validate Kafdrop readiness coupling, Jaeger automated-startup role, LocalStack/`wait4localstack` functional necessity, `aggregate-v1id-v2id` local/CI divergence, `aggregate-matching` necessity/readiness, and `kafka-rest`/`kafka-topic-extract` workflows independently.
- Retain a service when evidence is incomplete; removal requires owner confirmation, equivalent scenario validation and rollback.
- Update RepoSync-controlled Compose/Drone configuration only through the durable platform route.
- Run equivalent command and snapshot scenario sets on the current and candidate paths; record missing measurements rather than estimating them.

## Acceptance criteria

- [ ] Each scenario maps to its required infrastructure, application and helper services.
- [ ] Command and snapshot suites pass on the implemented target with equivalent assertions and test data.
- [ ] Required aggregators and command-adaptor readiness are validated explicitly.
- [ ] Every optional/helper service has a documented `retain`, `change`, `remove` or `unresolved` disposition with evidence and owner route.
- [ ] Any removed service is covered by an equivalent-path test and executable rollback.
- [ ] Current Compose behaviour remains recoverable until real CI validation and adoption approval complete.
- [ ] No speed, CI saving, flaky-test improvement, reliability improvement or full Compose replacement is claimed unless measured.

