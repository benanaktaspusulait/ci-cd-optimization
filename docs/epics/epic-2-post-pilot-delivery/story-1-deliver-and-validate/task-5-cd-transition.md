# E2-S1.5 — Deliver the CD Target Transition

| Field | Value |
|---|---|
| **Type** | Current-state review, target decision, implementation and deployment validation |
| **Status** | Proposed / New — not started or approved |
| **Depends on** | Current CD pipeline/configuration access and target owners for discovery/design; E2-S1.4 adoption evidence plus explicit platform/release approval before migration changes |

## Goal

Move the SNS CD path from its evidenced current state to an owner-approved target, including implementation, rollout, rollback and real deployment validation.

## Staged scope and gates

1. **Current state:** map CD stages, triggers, artefacts, environments, `kd`, Helm/chart ownership, PVC lifecycle and legacy components using repository, pipeline and owner evidence.
2. **Target decision:** compare retained `kd`, Helm and umbrella-chart options; decide PVC and legacy-component roles; document alternatives, risks, ownership and stop conditions.
3. **Implementation:** only after E2-S1.4 records the adopted SNS delivery path and the CD target is separately approved, implement the agreed pipeline/chart/configuration changes through their durable owner routes.
4. **Validation and rollout:** validate artefact identity, environment configuration, deployment health, PVC behaviour and rollback in the agreed non-production path before wider promotion.
5. **Closure:** record deployment evidence, failures, retries, unresolved risks, owner approval and final adoption disposition.

Build-once-promote remains separately owned release/platform work. Coordinate it where ownership overlaps, but do not silently make it a prerequisite or claim it was delivered here.

## Acceptance criteria

- [ ] Every confirmed CD path and component links to repository, pipeline, runtime or owner evidence.
- [ ] `kd`/Helm/chart, PVC and legacy-component decisions record alternatives and rationale.
- [ ] No migration change begins before E2-S1.4 adoption evidence and CD target, owner, rollout and rollback approvals are recorded.
- [ ] Approved changes are implemented through the correct repository/RepoSync/platform route.
- [ ] The same intended artefact and configuration are traceable through the target environments.
- [ ] Deployment health, PVC lifecycle and rollback are validated in the approved environment.
- [ ] Failures, retries, residual risks and manual steps remain explicit.
- [ ] Production or wider rollout is not claimed without deployment evidence and owner approval.
