# Delivery Epic Index

This index separates the bounded optimisation pilot from subsequent delivery work. The follow-up epic uses pilot evidence as input; it is not an additional pilot story.

| Epic | Purpose | Status | Primary record |
|---|---|---|---|
| Epic 1 — Container & CI/CD Optimisation Pilot | Baseline, bounded local experiments, evidence, limitations, Compose mapping and ownership/adoption recommendations | In progress — evidence prepared; T6.2 owner review, share-out and decisions not evidenced | [Pilot closure record](../PILOT-CLOSURE.md) |
| Epic 2 — Post-Pilot Container and CD Delivery | Two independently owned stories: validated SNS productionisation and CD current-state review/target planning | Proposed / New | [Epic 2](./epic-2-post-pilot-delivery/README.md) |

## Separation rule

- No Story 7 or implementation story will be added to the pilot.
- Epic 2 contains two independent stories: E2-S1 for SNS and E2-S2 for CD review/planning.
- Each task retains its own dependency, owner and approval gate.
- A proposed epic or candidate is not approved implementation work merely because it appears here.

## Transition

| Pilot outcome | Follow-up destination |
|---|---|
| `.dockerignore` and layer ordering | [E2-S1 — SNS image-build task](./epic-2-post-pilot-delivery/story-1-productionise-validated-outcomes/task-1-sns-image-build.md) |
| Redis Option A local workflow | [E2-S1 — opt-in Redis task](./epic-2-post-pilot-delivery/story-1-productionise-validated-outcomes/task-2-redis-testcontainers.md) |
| CI validation and adoption of approved SNS changes | [E2-S1 — component-level CI/adoption task](./epic-2-post-pilot-delivery/story-1-productionise-validated-outcomes/task-3-ci-validation-adoption.md) |
| Kafka/Schema Registry and broad SNS topology questions | [Deferred candidate register](./DEFERRED-CANDIDATES.md); not Epic 2 implementation |
| `kd`/Helm, PVC lifecycle and legacy CD components | [E2-S2 — CD review and target planning](./epic-2-post-pilot-delivery/story-2-review-cd-and-plan/README.md) |
| Build-once-promote | Related release/platform work; coordinate only where ownership overlaps |

## Pilot closure dependency

The pilot evidence and recommendation work is prepared, but the pilot cannot truthfully be marked `Done` until [T6.2](../../solution/story-6/T6.2-decide-adoption-route.md) records the owner review, stakeholder share-out, dispositions and routed next actions. No names, dates or approvals are inferred.
