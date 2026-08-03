# Container & CI/CD Optimisation Pilot — Closure Record

| Field | Value |
|---|---|
| **Status** | Closure pending — evidence prepared; T6.2 owner review/share-out not recorded |
| **Reviewed** | 2026-08-03 |
| **Scope** | Pilot Stories 1–6 only |

## Closure statement

The optimisation pilot has established current-state evidence, validated bounded local experiments, recorded limitations and non-claims, and identified ownership and adoption routes.

No additional implementation will be added to this epic. Approved SNS productionisation and the separately gated CD pipeline review are tracked under a [new delivery epic](epics/INDEX.md), not as Story 7.

The pilot cannot yet be marked `Done` because the [T6.2 publishing record](../solution/story-6/T6.2-decide-adoption-route.md#required-publishing-record) contains no stakeholder/owner review, share-out date, agreed dispositions or routed next actions.

## Evidence-based outcomes

| Outcome | Pilot result | Boundary | Evidence |
|---|---|---|---|
| Targeted `.dockerignore` | Local build context reduced from `191.27MB` to `189B`; required runtime/build artefacts preserved | Image size unchanged; no cold-build or CI saving demonstrated; durable RepoSync/platform ownership may still be required | [Story 3 final evidence](../solution/story-3/T3.4-impact-summary.md) |
| Dockerfile layer ordering | Same-daemon warm-cache rebuild after a real JAR change improved from `75.82–77.90s` to `4.62–5.08s` (approximately 15–16x for that local scenario) | No image-size, cold-build or CI improvement demonstrated; production adoption was outside the pilot | [Story 3 final evidence](../solution/story-3/T3.4-impact-summary.md) |
| Redis Testcontainers Option A | Two successful local opt-in functional runs demonstrated a narrow Redis smoke/wiring path | No CI saving, flaky-test improvement, Kafka/Schema Registry migration or full-E2E replacement; Option B is stop/not-now without a separate objective | [Story 4 final evidence](../solution/story-4/T4.4-document-findings.md) |
| Compose | Inspected Compose defines 20 services; mapped Drone configuration directly or transitively references 17; Compose is the currently validated full-E2E/custom-application mechanism | Immediate reduction unsupported; Kafdrop, Jaeger, LocalStack, aggregator divergence and orchestration ownership remain separately scoped candidates | [Story 5 current state](../solution/story-5/T5.1-validate-compose-scope.md) and [target-role decision](../solution/story-5/T5.2-decide-compose-role.md) |
| Ownership | Temporary target-repository experiments do not establish durable ownership | RepoSync/platform owns centrally managed pipeline, Compose and readiness routes where applicable; no prototype represents production or CI approval | [Story 6 classification](../solution/story-6/T6.1-classify-outcomes-and-ownership.md) |

## Missing closure evidence

The following T6.2 fields are not recorded:

- stakeholders / owners consulted
- date shared
- adopt decisions
- retain-as-candidate decisions
- stop decisions
- RepoSync/platform next actions
- wider-owner next actions
- unresolved questions after review

Until that evidence exists:

- T6.2 remains `Not completed`.
- Story 6 remains `In progress`.
- The pilot epic remains `In progress` and is not represented as approved or fully closed.

## Follow-up destinations

| Area | Destination | Status |
|---|---|---|
| Approved SNS image-build, Redis/Kafka/Schema Registry integration topology and CI-validation delivery | [Epic 2 — Post-Pilot Container and CD Delivery](epics/epic-2-post-pilot-delivery/README.md), SNS tasks | Proposed / New; non-Redis scope requires new evidence |
| Evidence-gated `kd`/Helm, PVC and legacy CD target transition | [Epic 2 — Post-Pilot Container and CD Delivery](epics/epic-2-post-pilot-delivery/README.md), CD transition task | Proposed / New; implementation requires target approval |
| Kafdrop, Jaeger and LocalStack | Separately scoped and approved validation work | Not approved |
| Build-once-promote | Related release/platform work where ownership overlaps | Candidate only |
