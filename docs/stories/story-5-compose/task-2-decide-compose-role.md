# T5.2 — Decide the Target Compose Role

**Story:** [Story 5 — Docker Compose Rationalisation](./README.md)

| Field | Value |
|---|---|
| **ID** | T5.2 |
| **Type** | Decision recommendation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Depends on** | T5.1 and current Story 4 evidence |
| **Owner** | _TBD_ |
| **Status** | Done — recommendation prepared; adoption not approved |
| **Primary output** | [T5.2 — Decide the Target Compose Role](../../../solution/story-5/T5.2-decide-compose-role.md) |

## Why

The pilot needs a clear Compose boundary that preserves useful workflows and makes unproven reductions visible as candidates, not completed changes.

## Goal

Recommend the target role of Compose and state the evidence and ownership required before any adoption.

## Scope

Define what should remain for full E2E and local debugging, which changes remain candidates, the preferred orchestration boundary, and the next validation/ownership route.

## Boundaries / non-goals

- No reduced Compose implementation or default pipeline change.
- No CI benefit, reliability improvement or stakeholder approval is assumed.
- RepoSync/platform ownership remains separate from technical feasibility.

## Acceptance criteria

- [x] Keep, candidate and stop/not-now decisions are explicit.
- [x] Full E2E and local-debug safeguards are retained.
- [x] Missing functional, timing and CI evidence is identified.
- [x] RepoSync/platform-owned changes have a durable adoption route.
- [x] The recommendation does not present a prototype as production-approved.
