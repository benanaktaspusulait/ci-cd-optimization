# Pilot Final Outcomes and Recommendations

> **Closure status:** Evidence and recommendations are prepared. The pilot remains open only for the unrecorded T6.2 owner review, stakeholder share-out, dispositions and routed next actions. No additional implementation story will be added to the pilot.

> **Delivery routing:** Validated SNS work is in [Epic 2 Story E2-S1](../docs/epics/epic-2-sns-delivery-pipeline-optimisation/story-1-productionise-validated-outcomes/README.md); independent CD review and target planning is in [Epic 2 Story E2-S2](../docs/epics/epic-2-sns-delivery-pipeline-optimisation/story-2-review-cd-and-plan/README.md).

## Validated Changes

| Change | Evidence | Current status | Durable route |
|---|---|---|---|
| Targeted `.dockerignore` | Context reduced from `191.27MB` to observed `189B`; build and runtime artefacts validated | Keep locally validated change | RepoSync follow-up |
| Dockerfile layer ordering | Same-daemon JAR-change rebuild improved from `75–78s` to `4.6–5.1s` | Prototype only | RepoSync/platform discussion |
| Redis Testcontainers pilot | Two successful local runs; PING and SET/GET validated | Local opt-in pilot | CI feasibility follow-up |

## RepoSync Follow-ups

- Add/manage the validated `.dockerignore` through RepoSync.
- Review the layer-order Dockerfile candidate through RepoSync/platform ownership.
- Record any temporary target-repo experiments that should become durable centrally managed changes.

## Future Recommendations

- **Taskfile Workflow:** Evaluate a Taskfile-based developer workflow to encapsulate prerequisite builds, pilot execution and measurement commands, reducing reliance on lengthy handover instructions. This is a recommended follow-up for developer experience improvement.
- **SNS productionisation:** Route only approved `.dockerignore`, Dockerfile, opt-in Redis and component-level CI-evaluation work through Epic 2 rather than extending the pilot.
- **Broader SNS topology:** Keep Kafka/Schema Registry, command/snapshot migration and service-role questions in the deferred candidate register until separately scoped and approved.
- **CD review and planning:** Route `kd`/Helm, PVC lifecycle and legacy CD current-state analysis and target planning through E2-S2. Migration implementation follows only as separate stories after evidence and owner approval.

## Not Claimed

- No CI saving.
- No production Dockerfile adoption.
- No broad adaptor rollout.
- No RepoSync approval yet.
