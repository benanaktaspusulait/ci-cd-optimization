# Epic 2 — SNS Delivery Pipeline Optimisation

| Field | Value |
|---|---|
| **Status** | Proposed / New — work not started or approved by this document |
| **Type** | Post-pilot delivery epic |
| **Depends on** | [Pilot closure record](../../PILOT-CLOSURE.md), task-specific pilot evidence and the relevant repository/platform/release owners |

## Why

Pilot follow-up work has one shared lifecycle boundary but two distinct outcomes: productionising validated SNS changes and independently reviewing the broader CD path. A single epic keeps the post-pilot phase coherent while separate stories prevent scope, ownership and completion from being conflated.

## Goal

Deliver validated SNS outcomes and produce an evidence-backed CD target plan through independently owned stories with separate acceptance and approval gates.

## Stories

| ID | Story | Status | Dependency boundary |
|---|---|---|---|
| E2-S1 | [Productionise Validated SNS Outcomes](./story-1-productionise-validated-outcomes/README.md) | Proposed / New | Validated Story 3/4 evidence and task-specific implementation approval |
| E2-S2 | [Review the CD Pipeline and Define the Target](./story-2-review-cd-and-plan/README.md) | Proposed / New | CD repository/pipeline access and owner input; independent from E2-S1 completion |

## Evidence and ownership boundaries

- E2-S1 implements only the validated image-build and local opt-in Redis outcomes, then validates explicitly approved components in CI.
- E2-S2 performs current-state review and target delivery planning only; it does not implement CD migration.
- E2-S1 completion is not a hard prerequisite for E2-S2 review or target design.
- Coordinate the stories only where artefact flow, pipeline ownership or RepoSync changes overlap.
- Kafka/Schema Registry and broad SNS integration-topology work remain in the [deferred candidate register](../DEFERRED-CANDIDATES.md), not E2-S1 committed scope.

## Boundaries / non-goals

- No Story 7 is added to the pilot.
- No Kafka/Schema Registry, command/snapshot or full-E2E Compose migration.
- No `kd`-to-Helm migration, umbrella-chart adoption, PVC removal or legacy-component deletion.
- No CD implementation story is created before E2-S2 evidence, target approval, ownership, validation and rollback gates exist.
- No local result is represented as CI evidence.
- No production, CI or CD adoption is claimed without the responsible owner’s decision.

## Epic acceptance criteria

- [ ] E2-S1 and E2-S2 retain independent scope, dependencies, owners and completion states.
- [ ] Only validated SNS outcomes are implemented in E2-S1.
- [ ] E2-S2 produces current-state evidence and target planning without migration implementation.
- [ ] Local and CI evidence remain distinguishable.
- [ ] Deferred candidates are not described as committed work.
- [ ] Any approved CD implementation and rollout are created later as separate stories.
