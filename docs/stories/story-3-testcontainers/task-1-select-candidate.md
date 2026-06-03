# T3.1 — Select candidate dependency/test

**Story:** [Story 3 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.1 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Testcontainers Pilot |
| **Estimate** | S |
| **Priority** | Must |
| **Labels** | `testcontainers`, `integration-test`, `selection` |
| **Sprint** | Week 2 |
| **Depends on** | T1.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The first Testcontainers pilot should de-risk the idea, not stress-test it. Picking a manageable dependency that is already exercised by existing tests gives a fair, low-cost signal about whether the approach is worth expanding.

## Goal
Choose one integration dependency/test for the Testcontainers pilot.

## Scope
Assess candidates such as Redis, Kafka, Schema Registry, LocalStack. Prefer one that:
- is already used by existing integration tests
- has manageable setup complexity
- provides useful validation value
- does not require large-scale refactoring for a first pilot

## Acceptance criteria
- [ ] One candidate dependency/test is selected
- [ ] Selection rationale is documented
- [ ] Pilot scope is agreed before implementation
