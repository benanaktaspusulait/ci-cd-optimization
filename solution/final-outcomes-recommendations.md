# Pilot Final Outcomes and Recommendations

> **Closure status:** Evidence and recommendations are prepared. The pilot remains open only for the unrecorded T6.2 owner review, stakeholder share-out, dispositions and routed next actions. No additional implementation story will be added to the pilot.

> **Delivery routing:** Approved SNS work and the separately gated CD pipeline review are task workstreams in [Epic 2](../docs/epics/epic-2-post-pilot-delivery/README.md).

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
- **SNS productionisation:** Route approved `.dockerignore`, Dockerfile and Redis workflow work through the SNS tasks in Epic 2 rather than extending the pilot. A later inspection of SNS snapshot `6bec7c579c9a244503850fad3072859fa809e41b` also places Kafka/Schema Registry implementation, full integration-topology equivalence and real-CI validation in that delivery epic; those additions are proposed work and do not broaden the Redis pilot evidence.
- **CD review:** Route `kd`/Helm, PVC lifecycle and legacy CD component analysis through the separately gated CD tasks in Epic 2.

## Not Claimed

- No CI saving.
- No production Dockerfile adoption.
- No broad adaptor rollout.
- No RepoSync approval yet.
