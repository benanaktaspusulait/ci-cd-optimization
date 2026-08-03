# E2-S1.1 — Implement Validated SNS Image-Build Changes

**Story:** [E2-S1 — Productionise Validated SNS Outcomes](./README.md)

| Field | Value |
|---|---|
| **ID** | E2-S1.1 |
| **Type** | Production image-build implementation and validation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Depends on** | [Story 3 consolidated evidence](../../../../solution/story-3/T3.4-impact-summary.md) and confirmed repository/RepoSync ownership |
| **Status** | Proposed / New — not started |
| **Primary output** | Reviewable Dockerfile/`.dockerignore` diff, local measurement record, runtime validation and rollback instructions |

## Why

The local pilot found a targeted build-context change and a layer-ordering candidate, but neither is a durable SNS repository outcome until the intended diff, runtime behaviour and owner route are verified.

## Goal

Apply only the validated image-build changes and produce reviewable local evidence without implying CI or production benefit.

## Scope

- Apply only the reviewed Dockerfile layer-ordering change.
- Retain or finalise the targeted `.dockerignore`.
- Preserve base-image intent, runtime command and required artefact content unless a separate approved change exists.
- Capture cold, warm no-change and real JAR-content-change builds.
- Validate image startup and required runtime artefacts.
- Record exact commands, environment, Docker/image state, failures and retries.
- Document an executable rollback and durable RepoSync/owner route.

## Boundaries / non-goals

- No base-image strategy, runtime-command or unrelated Dockerfile functional change.
- No Testcontainers, Compose, Maven or Drone change.
- Local evidence is not CI evidence.
- No durable RepoSync adoption before owner approval.

## Acceptance criteria

- [ ] Exact changed files and intended diff are reviewable.
- [ ] No unrelated Dockerfile functional change is included.
- [ ] Cold and warm no-change builds succeed.
- [ ] A real JAR-content-change rebuild is measured.
- [ ] Runtime smoke validation succeeds and required artefacts remain present.
- [ ] Environment, image state, commands, failures and retries are recorded.
- [ ] Local evidence is not represented as CI evidence.
- [ ] No image-size, cold-build or CI benefit is claimed unless newly measured.
- [ ] Rollback is executable and documented.
- [ ] The durable repository/RepoSync review route is recorded, and no owner-controlled change is merged outside that route.
