# E2-S2.1 — Review the Current CD Pipeline

**Story:** [E2-S2 — Review the CD Pipeline and Define the Target](./README.md)

| Field | Value |
|---|---|
| **ID** | E2-S2.1 |
| **Type** | Current-state evidence and architecture review |
| **Estimate** | 3 |
| **Priority** | Must |
| **Depends on** | Current CD repository/pipeline access and platform/release-owner input |
| **Status** | Proposed / New — not started |
| **Primary output** | Evidence-linked CD stage, artefact, environment, ownership, PVC and legacy-component map |

## Why

The current CD mechanism cannot be changed safely until its triggers, artefact flow, environment promotion, shared ownership and operational state are understood.

## Goal

Create a reliable current-state CD map that separates repository structure from runtime and owner evidence.

## Scope

- Map current CD stages, triggers and environment promotion paths.
- Trace artefact identity and flow across build, publish and deployment stages.
- Identify every confirmed `kd` invocation and responsibility.
- Map Helm, umbrella-chart and service-chart use and ownership.
- Map PVC creation, attachment, reuse and deletion lifecycle.
- Identify legacy components using repository, pipeline and available operational evidence.
- Map RepoSync, platform, release and service-owner boundaries.
- Record failures or operational examples where evidence is available.
- Classify each statement as confirmed fact, runtime evidence, structural observation, inference or unresolved question.

## Boundaries / non-goals

- No `kd`-to-Helm migration or umbrella-chart adoption.
- No PVC removal or legacy-component deletion.
- No rollout, deployment or production change.
- No performance, reliability or operational benefit inferred from static analysis.
- E2-S2.1 may proceed independently of E2-S1; coordination is required only where ownership or artefact flow overlaps.

## Acceptance criteria

- [ ] Every confirmed path and component links to repository, pipeline, runtime or owner evidence.
- [ ] Stages, triggers, artefact flow and environment promotion are mapped.
- [ ] `kd`, Helm/chart and ownership responsibilities are explicit.
- [ ] PVC creation, attachment, reuse and deletion are distinguished.
- [ ] Legacy components and ownership gaps are recorded without premature removal decisions.
- [ ] Evidence types and unresolved questions are clearly separated.
- [ ] No configuration or runtime change is made.
- [ ] The output is sufficient input for E2-S2.2.
