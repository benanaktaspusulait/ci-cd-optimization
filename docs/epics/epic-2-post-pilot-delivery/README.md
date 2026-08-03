# Epic 2 — Post-Pilot Container and CD Delivery

| Field | Value |
|---|---|
| **Status** | Proposed / New — work not started or approved by this document |
| **Type** | Post-pilot delivery epic |
| **Depends on** | [Pilot closure record](../../PILOT-CLOSURE.md), [Story 3 Docker evidence](../../../solution/story-3/T3.4-impact-summary.md), [Story 4 Redis evidence](../../../solution/story-4/T4.4-document-findings.md), [Story 5 Compose decision](../../../solution/story-5/T5.2-decide-compose-role.md) and [Story 6 ownership/adoption record](../../../solution/story-6/SUMMARY.md) |

## Goal

Deliver and validate approved post-pilot outcomes through two explicitly separated workstreams:

1. SNS repository productionisation, full integration-infrastructure coverage and real delivery-path validation.
2. CD pipeline current-state review and target-design planning.

The shared epic simplifies tracking. Task-level dependencies, owners and approval gates keep the two workstreams independent.

## SNS repository evidence basis

The SNS scope was refreshed from the inspected `main` snapshot at commit `6bec7c579c9a244503850fad3072859fa809e41b` on 3 August 2026. The inspection covered the integration-test Maven profiles and Java/Cucumber Kafka/Avro path, application Kafka/Schema Registry/Redis configuration, the 20-service Compose file, readiness/topic setup and mapped Drone orchestration. This is repository/configuration evidence; no new Kafka, Schema Registry, full-E2E or CI runtime result was produced by the inspection.

The inspected working tree already contained a modification to `redis-only-compose-script.sh`; the planning update did not alter the SNS repository.

## Boundaries

- This is not a continuation of the pilot and does not create Story 7.
- Local warm-cache results are not assumed to reproduce in CI.
- The Redis pilot is evidence only for the narrow Redis smoke/wiring path. Kafka, Schema Registry and full-E2E work must produce their own implementation and equivalence evidence.
- Full scope means evaluating and implementing the target SNS integration-test path; it does not mean blindly moving all 20 Compose services into Testcontainers.
- Kafka and Schema Registry are in implementation scope because the inspected SNS integration tests use real Kafka producer/consumer and Avro Schema Registry paths.
- Compose-hosted application containers and helper services remain until their scenario role is traced and an equivalent approved target is validated.
- Kafdrop, Jaeger, LocalStack, `wait4localstack`, aggregator variants, `kafka-rest` and `kafka-topic-extract` are explicit validation/decision scope, not pre-approved removals.
- RepoSync-controlled files require the durable owner route.
- The CD tasks do not implement a `kd`/Helm migration, remove PVCs or delete legacy components.
- An umbrella-chart direction is not assumed to be approved.
- Build-once-promote remains related release/platform work and is coordinated only where ownership overlaps.

## Story

| ID | Story | Status |
|---|---|---|
| E2-S1 | [Deliver and Validate Post-Pilot Outcomes](./story-1-deliver-and-validate/README.md) | Proposed / New |

## Epic acceptance criteria

- [ ] Approved SNS changes are implemented and validated without unsupported claims.
- [ ] Redis, Kafka and Schema Registry dependency workflows are implemented and validated at the scope assigned to them.
- [ ] Command and snapshot integration scenarios retain equivalent functional coverage on the approved target path.
- [ ] Every retained, changed or removed Compose service has scenario evidence, an owner-approved disposition and rollback coverage.
- [ ] Real CI evidence is recorded separately from local evidence.
- [ ] The current CD pipeline, `kd`/Helm, PVC and legacy-component paths are evidence-mapped.
- [ ] CD target direction, risks, validation, rollback and ownership gates are documented before implementation stories are proposed.
- [ ] Each task records its own owner, approval and adoption state.
- [ ] No candidate is described as implemented or adopted without evidence.
