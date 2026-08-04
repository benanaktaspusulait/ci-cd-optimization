# Deferred Post-Pilot Candidates

| Field | Value |
|---|---|
| **Status** | Candidate register — not approved implementation |
| **Evidence source** | [T5.1 current-state evidence](../../solution/story-5/T5.1-validate-compose-scope.md), [T5.2 target-role decision](../../solution/story-5/T5.2-decide-compose-role.md), [T6.1 ownership classification](../../solution/story-6/T6.1-classify-outcomes-and-ownership.md) and [T6.2 prepared decision record](../../solution/story-6/T6.2-decide-adoption-route.md) |

## Purpose

Preserve unvalidated SNS integration-topology and orchestration candidates without
presenting them as Epic 2 committed work. A candidate is a recommendation for separately
scoped and approved investigation, not permission to implement or adopt.

## Promoted to active scope

The following candidates have been promoted out of this register into active story scope:

| Candidate | Promoted to | Notes |
|---|---|---|
| Kafka Testcontainers | [E2-S2.1](./epic-2-sns-delivery-pipeline-optimisation/story-2-sns-testcontainers-integration/task-1-sns-testcontainers-integration.md) | Approved as part of the SNS Testcontainers integration path |
| Schema Registry Testcontainers | [E2-S2.1](./epic-2-sns-delivery-pipeline-optimisation/story-2-sns-testcontainers-integration/task-1-sns-testcontainers-integration.md) | Approved as part of the SNS Testcontainers integration path |

## Candidate register

| Candidate | Current evidence boundary | Required before implementation |
|---|---|---|
| Complete command/snapshot equivalence | Existing Compose path is the validated mechanism | Scenario/assertion inventory, target design and equivalent local/CI validation |
| Application-container boundary | Compose, Testcontainers-generic and hybrid options were not compared | Separately approved architecture analysis and representative implementation evidence |
| Kafdrop readiness decoupling | Current readiness dependency is mapped; functional necessity is unresolved | Equivalent readiness/topic-setup validation and readiness-owner decision |
| Jaeger automated-startup role | Current configured role is mapped; no automated trace assertion was established | Observability-owner decision and equivalent validation |
| LocalStack and `wait4localstack` | Current CI startup/wait path is mapped; SNS functional necessity is unresolved | Workflow trace, owner confirmation and equivalent validation |
| `aggregate-v1id-v2id` divergence | Local/CI topology divergence is mapped | Scenario coverage and target-role decision |
| `aggregate-matching` readiness | Current mapped path is confirmed; isolated necessity/readiness is unmeasured | Scenario mapping and equivalent validation |
| `kafka-rest` ownership/use | No mapped invocation was found; undocumented use remains possible | Owner/workflow confirmation before any removal decision |
| Single integration-test/orchestration owner | Drone and Maven have overlapping responsibilities | Target design, local/CI validation and RepoSync/platform decision |

## Boundaries / non-goals

- No remaining candidate is part of Epic 2 implementation scope.
- No service is approved for removal.
- No full-E2E, CI saving or reliability result is inferred from the Redis pilot.
- A future epic or task may be created only after the objective, owner, evidence plan
  and approval route are explicit.
