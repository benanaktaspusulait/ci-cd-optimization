# Delivery Epic Index

This index separates the bounded optimisation pilot from subsequent delivery work. The follow-up epic uses pilot evidence as input; it is not an additional pilot story.

| Epic | Purpose | Status | Primary record |
|---|---|---|---|
| Epic 1 — Container & CI/CD Optimisation Pilot | Baseline, bounded local experiments, evidence, limitations, Compose mapping and ownership/adoption recommendations | In progress — evidence prepared; T6.2 owner review, share-out and decisions not evidenced | [Pilot closure record](../PILOT-CLOSURE.md) |
| Epic 2 — Post-Pilot Container and CD Delivery | Track full SNS build/integration/CI delivery and evidence-gated CD implementation | Proposed / New | [Epic 2](./epic-2-post-pilot-delivery/README.md) |

## Separation rule

- No Story 7 or implementation story will be added to the pilot.
- Epic 2 contains one story with four outcome-focused SNS and CD transition tasks.
- Each task retains its own dependency, owner and approval gate.
- The proposed epic is not approved implementation work merely because it appears here.

## Transition

| Pilot outcome | Follow-up destination |
|---|---|
| `.dockerignore`, layer ordering, Redis, Kafka and Schema Registry | [Epic 2 — build and Testcontainers task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-1-build-and-testcontainers.md); non-Redis implementation requires new evidence |
| Compose application/helper topology and Kafdrop/Jaeger/LocalStack candidates | [Epic 2 — full integration-topology task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-2-e2e-compose-topology.md); no automatic migration or removal |
| CI validation and adoption of approved SNS changes | [Epic 2 — CI validation/adoption task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-3-ci-validation-adoption.md) |
| `kd`/Helm, PVC lifecycle and legacy CD components | [Epic 2 — CD transition task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-4-cd-transition.md) |
| Build-once-promote | Related release/platform work; coordinate only where ownership overlaps |

## Pilot closure dependency

The pilot evidence and recommendation work is prepared, but the epic cannot truthfully be marked `Done` until [T6.2](../../solution/story-6/T6.2-decide-adoption-route.md) records the owner review, stakeholder share-out, dispositions and routed next actions. No names, dates or approvals are inferred.
