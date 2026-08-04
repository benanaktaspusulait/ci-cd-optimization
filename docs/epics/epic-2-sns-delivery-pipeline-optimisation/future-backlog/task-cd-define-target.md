# E2-S2.2 — Define the CD Target Design and Delivery Plan

**Story:** [E2-S2 — Review the CD Pipeline and Define the Target](./README.md)

| Field | Value |
|---|---|
| **ID** | E2-S2.2 |
| **Type** | Target-state recommendation and delivery planning |
| **Estimate** | 3 |
| **Priority** | Must |
| **Depends on** | Completed E2-S2.1 current-state evidence and confirmed platform/release-owner input |
| **Status** | Proposed / New — not started |
| **Primary output** | Target recommendation, alternatives, validation/rollback gates, ownership routes and phased implementation backlog |

## Why

Owners need a target recommendation that is grounded in actual CD evidence and can be delivered incrementally without presupposing an umbrella-chart, PVC or legacy-component outcome.

## Goal

Recommend the CD target and define separately approvable implementation phases, validation and rollback without making migration changes.

## Scope

- Compare retained `kd`, Helm and umbrella-chart options.
- Recommend the PVC target role using E2-S2.1 lifecycle evidence.
- Classify each legacy component as `keep`, `candidate`, `stop / not now` or `unresolved`.
- Define functional and operational validation, rollout, rollback and stop conditions.
- Define ownership, RepoSync/platform routes and approval gates.
- Produce phased implementation stories only where evidence and owner decisions support them.
- Coordinate build-once-promote only where release or pipeline ownership overlaps.

## Boundaries / non-goals

- No migration, chart, PVC, script or pipeline implementation.
- No PVC or legacy-component removal.
- No assumption that an umbrella-chart approach is approved.
- No deployment improvement claim without measurement.
- No hard dependency on E2-S1.3 CI adoption; record coordination only where ownership or artefact flow overlaps.

## Acceptance criteria

- [ ] Alternatives, recommendation, rationale and unresolved assumptions are documented.
- [ ] PVC and legacy-component recommendations cite E2-S2.1 evidence.
- [ ] Every material recommendation has an ownership and approval route.
- [ ] Validation, rollout, rollback and stop conditions are explicit.
- [ ] Implementation phases are independently reviewable and identify dependencies.
- [ ] Build-once-promote coordination does not create an unsupported delivery dependency.
- [ ] No recommendation is represented as implemented or adopted.
