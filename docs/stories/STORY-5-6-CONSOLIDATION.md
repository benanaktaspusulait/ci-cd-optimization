# Story 5 and Story 6 Consolidation

This inventory records the task structure reviewed before the Story 5 and Story 6 definitions were changed. It is the traceability record for the documentation-only consolidation; no technical implementation or stakeholder approval is implied.

## Existing-task inventory

| Story | Existing task | Purpose | Proposed consolidated goal |
|---|---|---|---|
| Story 5 | T5.1 — Map services started by docker-compose | Inventory services, dependencies, ports and known purpose | T5.1 — Validate current Compose scope |
| Story 5 | T5.2 — Classify services and usage | Separate CI, local-debug, optional and unclear use | T5.1 — Validate current Compose scope |
| Story 5 | T5.3 — Recommend reduced Compose role | Define what Compose should retain, what may move, and what must not change | T5.2 — Decide the target Compose role |
| Story 6 | T6.1 — Consolidate pilot findings | Bring Story 1–5 evidence into a reviewable summary | T6.1 — Classify pilot outcomes and ownership routes |
| Story 6 | T6.2 — Classify ownership and recommend target board | Separate CST-local, RepoSync/platform and wider ETO work | T6.1 — Classify pilot outcomes and ownership routes |
| Story 6 | T6.3 — Share findings with stakeholders | Publish the outcome, capture feedback and record next steps | T6.2 — Decide the adoption route and publish the pilot outcome |

## Old-to-new task mapping

| Old task | New consolidated task | Reason |
|---|---|---|
| T5.1 | T5.1 | Mapping and usage classification are one evidence-gathering outcome. |
| T5.2 | T5.1 | Classification has no independent stakeholder outcome without the service map. |
| T5.3 | T5.2 | The Compose-role recommendation remains a distinct decision outcome. |
| T6.1 | T6.1 | Evidence consolidation is an input to ownership classification, not a separate delivery. |
| T6.2 | T6.1 | Ownership and routing complete the same classification outcome. |
| T6.3 | T6.2 | Sharing, feedback and the adopt/candidate/stop decision form one pilot-close outcome. |

## Status interpretation

- Story 5 evidence has been prepared, so both consolidated documentation outcomes are complete. This does not mean a reduced Compose configuration was implemented or adopted.
- Story 6 classification material has been prepared. Stakeholder sharing, feedback, approval and adoption are not evidenced, so the final adoption/publishing outcome remains not completed.
- Existing Story 1–4 technical evidence remains unchanged. Documentation references to retired task identifiers point directly to the consolidated evidence.

## Post-pilot epic transition

This mapping separates recommendations from delivery. It does not approve any candidate or add another pilot story.

| Pilot outcome | Follow-up destination |
|---|---|
| `.dockerignore` and layer ordering | [Epic 2 — SNS image-build task](../epics/epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-1-sns-image-build.md) |
| Redis Option A local workflow | [Epic 2 — Redis task](../epics/epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-2-redis-testcontainers.md) |
| Kafka/Schema Registry and Avro integration coverage | [Epic 2 — Kafka/Schema Registry task](../epics/epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-3-kafka-schema-registry-testcontainers.md); new evidence required |
| Compose application/helper topology and Kafdrop/Jaeger/LocalStack candidates | [Epic 2 — SNS topology task](../epics/epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-4-compose-e2e-topology.md); no automatic migration or removal |
| CI validation of approved SNS changes | [Epic 2 — SNS delivery-validation task](../epics/epic-2-post-pilot-delivery/story-1-deliver-and-validate/task-5-sns-delivery-validation.md) |
| `kd`/Helm, PVC lifecycle and legacy CD components | [Epic 2 — CD review/design tasks](../epics/epic-2-post-pilot-delivery/story-1-deliver-and-validate/README.md) |
| Build-once-promote | Related release/platform work; coordinate only where ownership overlaps |

The pilot retains Stories 1–6 only. T6.2 remains incomplete until owner review, stakeholder sharing, dispositions and next actions are recorded.
