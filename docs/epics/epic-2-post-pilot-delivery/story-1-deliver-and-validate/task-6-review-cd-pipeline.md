# E2-S1.6 — Review the Current CD Pipeline

| Field | Value |
|---|---|
| **Type** | Current-state evidence review |
| **Status** | Proposed / New — not started |
| **Depends on** | Current CD pipeline/configuration access and platform/release-owner input |

## Scope

- Map CD stages, triggers, artefacts, environments and shared dependencies.
- Map `kd` invocation and responsibilities.
- Record Helm and umbrella-chart evidence without assuming a target.
- Trace PVC creation, attachment, reuse and deletion behaviour for ephemeral environments.
- Inventory legacy components and identify owners or ownership gaps.

## Acceptance criteria

- [ ] Every confirmed path and component links to repository, pipeline or owner evidence.
- [ ] PVC lifecycle and attachment risks distinguish configuration from runtime evidence.
- [ ] Confirmed facts, structural observations, inferences and unresolved questions are separated.
- [ ] Undocumented use and missing ownership remain explicit.
- [ ] No `kd`, PVC or legacy-component change is implemented from static analysis.
- [ ] The output is sufficient input for E2-S1.7.

