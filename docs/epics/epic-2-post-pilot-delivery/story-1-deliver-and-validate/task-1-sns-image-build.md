# E2-S1.1 — Implement Validated SNS Image-Build Changes

| Field | Value |
|---|---|
| **Type** | Production implementation and validation |
| **Status** | Proposed / New — not started |
| **Depends on** | [Story 3 final evidence](../../../../solution/story-3/T3.4-impact-summary.md) and confirmed repository/RepoSync ownership |

## Scope

- Apply the reviewed layer ordering to the production SNS Dockerfile.
- Retain or finalise the validated targeted `.dockerignore`.
- Capture cold, no-change warm and real JAR-change warm build evidence.
- Validate image startup/runtime behaviour and required artefacts.
- Document ownership, review and rollback routes.

## Acceptance criteria

- [ ] The production Dockerfile change is reviewable.
- [ ] The targeted `.dockerignore` is present and validated.
- [ ] Cold and warm no-change builds succeed.
- [ ] A real JAR-content-change rebuild is measured.
- [ ] Runtime/image smoke validation succeeds and required artefacts remain present.
- [ ] Local and CI measurements are reported separately.
- [ ] No image-size, cold-build or CI benefit is claimed unless newly measured.
- [ ] A tested or executable rollback path is documented.
