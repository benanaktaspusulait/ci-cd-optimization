# E2-S1.2 — Productise the Opt-In Redis Testcontainers Workflow

**Story:** [E2-S1 — Productionise Validated SNS Outcomes](./README.md)

| Field | Value |
|---|---|
| **ID** | E2-S1.2 |
| **Type** | Repository workflow implementation and local validation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Depends on** | [Story 4 final evidence](../../../../solution/story-4/T4.4-document-findings.md), [Story 4 Summary](../../../../solution/story-4/SUMMARY.md) and repository-owner approval |
| **Status** | Proposed / New — not started |
| **Primary output** | Maintained local opt-in Redis workflow, exact invocation, repeated-run evidence and documented limitations |

## Why

The pilot validated a narrow Redis smoke/wiring path locally. It needs a maintainable invocation and explicit lifecycle without being expanded into a broader infrastructure migration.

## Goal

Productise Redis Option A as a repeatable local opt-in workflow while preserving all current default Compose/full-E2E behaviour.

## Scope

- Retain or refactor the existing Redis Testcontainers lifecycle.
- Give `MinimalRedisTest` an explicit keep, refactor or replace disposition.
- Preserve the validated `PING` and `SET`/`GET` behaviour.
- Use dynamically resolved Redis endpoints and isolated run-specific data.
- Document prerequisites, exact invocation, intended use and limitations.
- Use a Taskfile or equivalent wrapper only if it matches repository standards.
- Run repeated local functional checks and record failures, retries, cleanup and diagnostics.

## Boundaries / non-goals

- No Kafka or Schema Registry Testcontainers implementation.
- No Avro producer/consumer, command/snapshot or application-topology migration.
- No Compose reduction or service removal.
- No default CI enablement; CI evaluation is optional and separately approved in E2-S1.3.
- Current Compose/full-E2E behaviour remains unchanged.

## Acceptance criteria

- [ ] Redis starts through a documented opt-in workflow.
- [ ] Dynamic endpoint resolution is used.
- [ ] `MinimalRedisTest` has an explicit disposition and validated Redis assertions are retained.
- [ ] Repeated local runs succeed with isolated data.
- [ ] Cleanup and failure diagnostics are demonstrated.
- [ ] Prerequisites and exact invocation are documented.
- [ ] Default CI and current Compose/full-E2E behaviour are unchanged.
- [ ] No speed, flaky-test, CI or replacement claim is made.
