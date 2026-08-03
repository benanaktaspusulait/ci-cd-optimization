# E2-S1.2 — Implement and Validate the Full SNS Integration Topology

| Field | Value |
|---|---|
| **Type** | Full integration-path implementation and equivalence validation |
| **Status** | Proposed / New — not started or approved |
| **Depends on** | E2-S1.1, [T5.1 current-scope evidence](../../../../solution/story-5/T5.1-validate-compose-scope.md), [T5.2 target-role decision](../../../../solution/story-5/T5.2-decide-compose-role.md) and required service owners |

## Goal

Implement a complete, supportable SNS command and snapshot integration-test topology. “Complete” means equivalent required scenario coverage, not converting every Compose service into Testcontainers.

## Scope

- Build and retain a scenario-to-service matrix for command and snapshot profiles.
- Integrate E2-S1.1 Redis/Kafka/Schema Registry infrastructure with the command adaptor and required aggregator application containers.
- Select and implement the approved application-container boundary: Compose, Testcontainers generic containers or a justified hybrid.
- Validate Kafdrop readiness coupling, Jaeger automated-startup role, LocalStack/`wait4localstack` functional necessity, `aggregate-v1id-v2id` local/CI divergence, `aggregate-matching` necessity/readiness and `kafka-rest`/`kafka-topic-extract` workflows independently.
- Give every supporting service an evidence-backed `retain`, `change`, `remove` or `unresolved` disposition.
- Retain services when evidence is incomplete. Removal requires owner confirmation, equivalent scenario validation and an executable rollback.
- Update RepoSync-controlled Compose/Drone configuration only through the durable platform route.
- Run equivalent command and snapshot scenario sets on current and candidate paths; record missing measurements rather than estimates.

## Acceptance criteria

- [ ] Each scenario maps to its required infrastructure, application and helper services.
- [ ] Command and snapshot suites pass on the implemented target with equivalent assertions and data.
- [ ] Required command-adaptor and aggregator startup/readiness paths are validated explicitly.
- [ ] Every optional/helper service has an evidence-backed disposition and owner route.
- [ ] Removed services have equivalent-path tests and executable rollback.
- [ ] The current Compose route remains recoverable until E2-S1.3 CI validation and adoption approval complete.
- [ ] No speed, CI saving, flaky-test, reliability or full Compose replacement claim is made without measurement.

