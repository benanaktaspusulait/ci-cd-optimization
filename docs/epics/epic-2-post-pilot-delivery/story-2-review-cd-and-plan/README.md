# E2-S2 — Review the CD Pipeline and Define the Target

**Epic:** [Epic 2 — Post-Pilot Container and CD Delivery](../README.md)

| Field | Value |
|---|---|
| **ID** | E2-S2 |
| **Status** | Proposed / New |
| **Primary output** | Current-state CD evidence map plus an owner-reviewable target recommendation and phased delivery plan |

## Why

A target recommendation is defensible only after the current CD flow, ownership and operational constraints are mapped.

## Goal

Separate evidence gathering from target recommendation while keeping both outcomes under one independently owned CD story.

## Tasks

| ID | Task | Estimate | Priority | Status | Depends on |
|---|---|---:|---|---|---|
| E2-S2.1 | [Review the current CD pipeline](./task-1-review-current-cd.md) | 3 | Must | Proposed / New | CD repository/pipeline access and owner input |
| E2-S2.2 | [Define the CD target design and delivery plan](./task-2-define-cd-target.md) | 3 | Must | Proposed / New | Completed E2-S2.1 evidence and owner input |

## Boundaries / non-goals

- No migration implementation belongs to this story.
- E2-S2.1 does not recommend a target before evidence is mapped.
- E2-S2.2 does not describe a recommendation as approved adoption.
- E2-S1 CI adoption is a coordination point only where ownership overlaps, not a hard prerequisite.

## Story acceptance criteria

- [ ] E2-S2.1 produces sufficient evidence for target comparison.
- [ ] E2-S2.2 cites E2-S2.1 evidence for every material recommendation.
- [ ] Missing ownership and runtime evidence remain explicit.
- [ ] No CD implementation or deployment result is claimed.
