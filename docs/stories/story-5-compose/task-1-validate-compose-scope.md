# T5.1 — Validate Current Compose Scope

**Story:** [Story 5 — Docker Compose Rationalisation](./README.md)

| Field | Value |
|---|---|
| **ID** | T5.1 |
| **Type** | Analysis |
| **Estimate** | 3 |
| **Priority** | Must |
| **Depends on** | T1.3, T1.4 and Story 4 final evidence ([T4.4](../../../solution/story-4/T4.4-document-findings.md) / [Story 4 Summary](../../../solution/story-4/SUMMARY.md)) |
| **Owner** | _TBD_ |
| **Status** | Done — evidence prepared |
| **Primary output** | [T5.1 — Validate Current Compose Scope](../../../solution/story-5/T5.1-validate-compose-scope.md) |

## Why

A safe Compose decision needs one trusted view of service topology and actual CI, full E2E and local usage.

## Goal

Validate the current Compose scope and identify which roles are confirmed, unclear or only candidates for change.

## Scope

Map services, dependencies and invocation paths; classify their CI, full E2E and local-debug roles; record uncertainties and relevant Story 1–4 evidence.

## Boundaries / non-goals

- No Compose, Maven-profile or CI change.
- No service removal based only on static analysis or a Redis-only pilot.
- Kafka/Schema Registry and full E2E behaviour remain outside the Redis-only evidence.

## Acceptance criteria

- [x] All defined services and material dependencies are represented.
- [x] CI, full E2E and local-debug use are distinguished.
- [x] Confirmed facts, structural observations and inferences are labelled.
- [x] Unclear or unmeasured behaviour is recorded without an optimisation claim.
- [x] The evidence needed for a safe target-role decision is linked.
- [x] Each confirmed invocation path and service-role classification is linked to repository or pipeline evidence.
