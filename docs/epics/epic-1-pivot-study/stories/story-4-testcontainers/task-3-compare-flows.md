# T4.3 — Compare Redis pilot with docker-compose support flow

**Story:** [Story 4 — Testcontainers Pilot](README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.3 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 2 |
| **Priority** | Should |
| **Labels** | `testcontainers`, `redis`, `docker-compose`, `comparison` |
| **Sprint** | Week 3 |
| **Depends on** | T4.2 |
| **Owner** | _TBD_ |
| **Status** | In progress — T4.2 evidence available; target-machine Compose runtime measurement pending |

## Why
The Redis smoke test and the full integration-test suite exercise different scopes. A fair comparison must isolate the Redis/support-dependency workflow and avoid presenting a minimal wiring test as a replacement for the Kafka-driven E2E path.

## Goal
Compare the Phase 1 Redis Testcontainers smoke/wiring pilot with the existing docker-compose Redis/support-dependency flow and decide whether the evidence justifies a follow-up.

## Scope
Distinguish clearly between:

- the existing docker-compose Redis/support-dependency path
- the local, opt-in Redis Testcontainers smoke/wiring path
- the full E2E Kafka and Schema Registry input/assertion path, which remains out of scope for Phase 1

Compare the two Redis-related paths across:

- startup/setup time, only where measured with a documented method
- local command complexity
- developer feedback loop
- isolation and determinism
- dependency wiring complexity
- repeated-run behaviour, where practical, to distinguish one-off startup cost from stable local behaviour
- CI feasibility, recorded as not measured when no CI run was attempted
- benefits, drawbacks and limitations
- whether the evidence is sufficient to attempt Redis Option B or prioritise a Kafka and Schema Registry follow-up

## Comparison boundary

Do not compare the minimal Redis smoke test with the full integration-test suite as though they provide equivalent coverage or as though the pilot replaces the suite. Do not infer speed, CI or reliability improvements that were not measured.

The existing `redis_kafka` pre-integration readiness stage combines Redis readiness with Kafka, Schema Registry and Kafdrop readiness plus Kafka topic creation. If Redis startup time cannot be isolated safely from that combined flow, record docker-compose Redis startup time as not separately measured rather than assigning an estimate or treating the combined stage as a Redis-only timing.

Full E2E compose timing may be captured as contextual evidence, but it must not be compared directly with the Redis-only Testcontainers smoke test.

## Acceptance criteria
- [ ] Redis Testcontainers and the docker-compose Redis/support-dependency flow are compared fairly across the defined dimensions
- [ ] Full E2E and docker-compose replacement are explicitly marked out of scope
- [ ] Benefits, drawbacks and limitations are documented
- [ ] CI suitability is not claimed unless it was attempted and measured
- [ ] Existing docker-compose Redis startup time is reported only if it can be isolated with a documented measurement method; otherwise it is explicitly recorded as not separately measured
- [ ] If a Redis-only docker-compose comparison cannot be isolated safely, the limitation and reason are documented, and no like-for-like performance conclusion is made
- [ ] The comparison records the exact commands, environment and measurement method used for each measured value
- [ ] The recommendation states whether to continue to Redis Option B and/or a Kafka and Schema Registry follow-up
