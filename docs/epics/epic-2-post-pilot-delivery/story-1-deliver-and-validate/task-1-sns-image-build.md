# E2-S1.1 — Implement Validated SNS Image-Build Changes

| Field | Value |
|---|---|
| **Type** | Production image-build implementation and validation |
| **Status** | Proposed / New — not started |
| **Depends on** | [Story 3 final evidence](../../../../solution/story-3/T3.4-impact-summary.md) and confirmed repository/RepoSync ownership |

## Scope

- Apply the reviewed Dockerfile layer ordering to the production SNS Dockerfile.
- Retain or finalise the validated targeted `.dockerignore`.
- Capture cold, no-change warm and real JAR-content-change warm build evidence.
- Validate image startup/runtime behaviour and required artefacts.
- Record exact commands, image state, environment, failures and retries.
- Document durable ownership, review and executable rollback routes.

## Acceptance criteria

- [ ] The production Dockerfile and targeted `.dockerignore` changes are reviewable.
- [ ] Cold and warm no-change builds succeed.
- [ ] A real JAR-content-change rebuild is measured.
- [ ] Runtime/image smoke validation succeeds and required artefacts remain present.
- [ ] Local results are labelled as local; real-CI measurement and adoption are deferred to E2-S1.4.
- [ ] No image-size, cold-build or CI benefit is claimed unless newly measured.
- [ ] A tested or executable rollback path is documented.
