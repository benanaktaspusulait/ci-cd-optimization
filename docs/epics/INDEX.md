# Delivery Epic Index

This index separates the bounded optimisation pilot from subsequent delivery work. The follow-up epic uses pilot evidence as input; it is not an additional pilot story.

| Epic | Purpose | Status | Primary record |
|---|---|---|---|
| Epic 1 — Container & CI/CD Optimisation Pilot | Baseline, bounded local experiments, evidence, limitations, Compose mapping and ownership/adoption recommendations | In progress — evidence prepared; T6.2 owner review, share-out and decisions not evidenced | [Pilot closure record](../PILOT-CLOSURE.md) |
| Epic 2 — Post-Pilot Container and CD Delivery | Track full SNS integration-infrastructure delivery and CD review/design as independently gated task workstreams | Proposed / New | [Epic 2](./epic-2-post-pilot-delivery/README.md) |

## Separation rule

- No Story 7 or implementation story will be added to the pilot.
- Epic 2 contains one story with separate SNS delivery and CD review/design tasks.
- Each task retains its own dependency, owner and approval gate.
- The proposed epic is not approved implementation work merely because it appears here.

## Transition

| Pilot outcome | Follow-up destination |
|---|---|
| `.dockerignore` and layer ordering | [Epic 2 — SNS image-build task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-1-sns-image-build.md) |
| Redis Option A local workflow | [Epic 2 — Redis task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-2-redis-testcontainers.md) |
| Kafka/Schema Registry and Avro integration coverage | [Epic 2 — Kafka/Schema Registry task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-3-kafka-schema-registry-testcontainers.md); new implementation evidence required |
| Compose application/helper topology and Kafdrop/Jaeger/LocalStack candidates | [Epic 2 — SNS topology task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-4-compose-e2e-topology.md); no automatic migration or removal |
| CI validation of approved SNS changes | [Epic 2 — SNS delivery-validation task](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-5-sns-delivery-validation.md) |
| `kd`/Helm, PVC lifecycle and legacy CD components | [Epic 2 — CD review/design tasks](./epic-2-post-pilot-delivery/story-1-deliver-and-validate/README.md) |
| Build-once-promote | Related release/platform work; coordinate only where ownership overlaps |

## Pilot closure dependency

The pilot evidence and recommendation work is prepared, but the epic cannot truthfully be marked `Done` until [T6.2](../../solution/story-6/T6.2-decide-adoption-route.md) records the owner review, stakeholder share-out, dispositions and routed next actions. No names, dates or approvals are inferred.
