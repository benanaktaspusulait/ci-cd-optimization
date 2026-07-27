# T5.2 — Decide the Target Compose Role

**Story:** [Story 5 — Docker Compose Rationalisation](./README.md)

| Field | Value |
|---|---|
| **ID** | T5.2 |
| **Type** | Decision recommendation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Depends on** | [T5.1 evidence](../../../solution/story-5/T5.1-validate-compose-scope.md), [T4.4 findings](../../../solution/story-4/T4.4-document-findings.md) and [Story 4 Summary](../../../solution/story-4/SUMMARY.md) |
| **Owner** | _TBD_ |
| **Status** | Done — target-role recommendation prepared; implementation and adoption not approved |
| **Primary output** | [T5.2 — Decide the Target Compose Role](../../../solution/story-5/T5.2-decide-compose-role.md) |

## Why

The pilot needs a clear Compose boundary that preserves useful workflows and makes unproven reductions visible as candidates, not completed changes.

## Goal

Recommend the target role of Compose and state the evidence and ownership required before any adoption.

## Scope

Define explicit keep, candidate, stop/not-now, local-utility and architectural decisions for every material T5.1 area, plus the evidence and ownership route required before change.

## Boundaries / non-goals

- No reduced Compose implementation or default pipeline change.
- No CI benefit, reliability improvement or stakeholder approval is assumed.
- RepoSync/platform ownership remains separate from technical feasibility.

## Acceptance criteria

- [x] Every material T5.1 rationalisation candidate has an explicit target-role decision.
- [x] `KEEP`, `CANDIDATE`, `STOP / NOT NOW`, local-utility and architectural decisions are distinguishable.
- [x] Current validated behaviour is separated from future target-state candidates.
- [x] Full-E2E and local-debug safeguards are retained.
- [x] Missing functional, timing, CI and ownership evidence is explicit.
- [x] RepoSync/platform-owned changes have a durable decision route.
- [x] Story 6 handoff candidates are explicit and are not presented as approved work.
- [x] No prototype or repository analysis is presented as production approval.
- [x] All references point to one canonical T5.2 solution document.
