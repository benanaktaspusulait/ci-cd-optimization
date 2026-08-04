# Epic 2 — SNS Delivery Pipeline Optimisation

| Field | Value |
|---|---|
| **Status** | Proposed / New — work not started or approved by this document |
| **Type** | Post-pilot delivery epic |
| **Depends on** | [Pilot closure record](../../PILOT-CLOSURE.md), task-specific pilot evidence and the relevant repository/platform/release owners |

## Why

Pilot follow-up work has two distinct outcomes: productionising validated SNS changes,
and extending the Testcontainers foundation into a full SNS integration path. A single
epic keeps the post-pilot phase coherent while separate stories prevent scope, ownership
and completion from being conflated.

## Goal

Deliver validated SNS outcomes and extend the Testcontainers path into a maintainable
SNS integration workflow through independently owned stories with separate acceptance
and approval gates.

## Stories

| ID | Story | Status | Dependency boundary |
|---|---|---|---|
| E2-S1 | [Productionise Validated SNS Outcomes](./story-1-productionise-validated-outcomes/README.md) | Proposed / New | Validated Story 3/4 evidence and task-specific implementation approval |
| E2-S2 | [Productise the SNS Testcontainers Integration Path](./story-2-sns-testcontainers-integration/README.md) | Proposed / New | Validated Redis Testcontainers pilot, repository access and CI Docker availability |

## Evidence and ownership boundaries

- E2-S1 implements only the validated image-build and local opt-in Redis outcomes, then
  validates explicitly approved components in CI.
- E2-S2 extends the Redis Testcontainers foundation to cover Kafka, Schema Registry and
  one real SNS application flow, validated locally and in branch CI.
- E2-S1 completion is not a hard prerequisite for E2-S2.
- Coordinate the stories only where artefact flow, pipeline ownership or RepoSync changes
  overlap.

## Boundaries / non-goals

- No Compose migration or service removal.
- No `kd`-to-Helm migration, umbrella-chart adoption, PVC removal or legacy-component
  deletion.
- No CD implementation story before evidence, target approval, ownership, validation
  and rollback gates exist.
- No local result is represented as CI evidence.
- No production, CI or CD adoption is claimed without the responsible owner's decision.

## CD review backlog

CD pipeline review and target definition tasks have been preserved in
[future-backlog/](./future-backlog/) and are not part of active story scope.

## Epic acceptance criteria

- [ ] E2-S1 and E2-S2 retain independent scope, dependencies, owners and completion states.
- [ ] Only validated SNS outcomes are implemented in E2-S1.
- [ ] E2-S2 produces a maintained, repeatable SNS integration path with local and CI evidence.
- [ ] Local and CI evidence remain distinguishable.
- [ ] Existing Compose/full-E2E path remains unchanged.
- [ ] Any approved CD implementation and rollout are created later as separate stories.
