# E2-S1.3 — Implement and Validate the Full SNS Integration Topology

| Field | Value |
|---|---|
| **Type** | Full integration-path implementation and equivalence validation |
| **Status** | Proposed / New — not started or approved |
| **Depends on** | E2-S1.2, [T5.1 current-scope evidence](../../../../solution/story-5/T5.1-validate-compose-scope.md), [T5.2 target-role decision](../../../../solution/story-5/T5.2-decide-compose-role.md) and required service owners |

## Goal

Implement a complete, supportable SNS command and snapshot integration-test topology. “Complete” means equivalent required scenario coverage, not converting every Compose service into Testcontainers.

## Scope

- Inventory the existing Cucumber feature files, runners, tags, step definitions, assertions and Maven execution profiles before changing them.
- Build and retain a scenario-to-service matrix for command and snapshot profiles.
- Integrate E2-S1.2 Redis/Kafka/Schema Registry infrastructure with the command adaptor and required aggregator application containers.
- Update `SnsSteps` and related test configuration to consume dynamically supplied Kafka bootstrap and Schema Registry endpoints; wire Redis endpoints into each included application/test path that requires Redis.
- Update `local-testcontainers`, `local-int-cmd`, `local-int-snapshot`, `ci-cmd` and `ci-snapshot` only as required by the approved target, documenting the retained purpose and invocation of every profile.
- Preserve existing scenario tags, assertions and command/snapshot coverage. Any intentional retirement or replacement requires a one-to-one mapping, rationale and owner approval.
- Select and implement the approved application-container boundary: Compose, Testcontainers generic containers or a justified hybrid.
- Validate Kafdrop readiness coupling, Jaeger automated-startup role, LocalStack/`wait4localstack` functional necessity, `aggregate-v1id-v2id` local/CI divergence, `aggregate-matching` necessity/readiness and `kafka-rest`/`kafka-topic-extract` workflows independently.
- Give every supporting service an evidence-backed `retain`, `change`, `remove` or `unresolved` disposition.
- Retain services when evidence is incomplete. Removal requires owner confirmation, equivalent scenario validation and executable rollback.
- Move topic/schema/readiness responsibilities away from `pre-integration-test` only where E2-S1.2 or another retained owner provides equivalent behaviour; remove obsolete fixed-port configuration and helpers only after equivalence passes.
- Keep test names, runner/tag selection and Maven Surefire/Failsafe execution explicit so local and CI suite membership can be audited.
- Update RepoSync-controlled Compose/Drone configuration only through the durable platform route.
- Run equivalent command and snapshot scenario sets on current and candidate paths; record missing measurements rather than estimates.

## Acceptance criteria

- [ ] Each scenario maps to its required infrastructure, application and helper services.
- [ ] A before/after inventory maps every existing feature, runner, tag and material assertion to the retained, updated or explicitly approved retired path.
- [ ] `SnsSteps` and related configuration use the approved dynamic endpoints without embedding candidate-path fixed ports or Compose-only DNS names.
- [ ] The retained Maven profiles have documented purposes and exact invocations; redundant profiles are removed only after equivalent local and CI routes exist.
- [ ] Command and snapshot suites pass on the implemented target with equivalent assertions and data.
- [ ] Existing tests are updated rather than replaced by a narrower smoke-only suite.
- [ ] Required command-adaptor and aggregator startup/readiness paths are validated explicitly.
- [ ] Every optional/helper service has an evidence-backed disposition and owner route.
- [ ] Removed services have equivalent-path tests and executable rollback.
- [ ] Obsolete readiness helpers and fixed endpoint configuration are removed only after their responsibilities are mapped and covered elsewhere.
- [ ] The current Compose route remains recoverable until E2-S1.4 CI validation and adoption approval complete.
- [ ] No speed, CI saving, flaky-test, reliability or full Compose replacement claim is made without measurement.
