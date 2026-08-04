# E2-S1.1 — Implement, Validate and Prepare Adoption of the SNS Image-Build Optimisation

**Story:** [E2-S1 — Productionise the SNS Image-Build Optimisation](./README.md)

| Field | Value |
|---|---|
| **ID** | E2-S1.1 |
| **Type** | Image-build implementation, CI validation and adoption preparation |
| **Estimate** | 5 |
| **Priority** | Must |
| **Depends on** | [Story 3 consolidated evidence](../../../../solution/story-3/T3.4-impact-summary.md), confirmed repository/RepoSync ownership and CI Docker availability |
| **Status** | In progress — local implementation validated; feature-branch CI complete; develop shared-cache validation pending merge; RepoSync/owner adoption route pending |
| **Primary output** | [E2-S1.1 implementation result](../../../../solution/epic-2/E2-S1.1-sns-image-build-result.md) |

## Why

The pilot validated a targeted layer-ordering change and a build-context improvement.
Neither is a durable outcome until CI behaviour and the owner adoption route are
confirmed end to end.

## Goal

Implement the validated image-build changes, validate them in real branch CI and
prepare a complete adoption route including rollback and RepoSync/owner approval.

## Scope

### 1. Dockerfile layer ordering

- Apply the reviewed layer-ordering change from the T3.3 prototype.
- Preserve base-image intent, runtime command and required artefact content.
- Retain the existing RepoSync warning header unchanged.

### 2. Targeted `.dockerignore`

- Retain or finalise the targeted `.dockerignore`.
- Confirm that only the two required JAR inputs are exposed to the build context.

### 3. Local build validation

- Validate cold, warm no-change and real JAR-content-change builds.
- Confirm the produced image starts successfully and contains the required runtime
  artefacts, user and command.

### 4. Registry-backed cache validation

- Validate feature-branch cache seed and reuse in real branch CI.
- Confirm that changed application JARs rebuild while stable setup layers remain cached.
- After merge, validate the shared `develop` cache and its reuse by a later branch.
- Keep post-merge validation explicitly pending until it can be executed.

### 5. Validation evidence

Record only:

- exact validation commands
- relevant CI run references
- observed cache behaviour (BuildKit cache-hit and rebuilt-layer output)
- runtime startup result
- failures or retries
- post-merge and owner blockers

### 6. Rollback

- Document an executable rollback for the local change.
- Document a rollback route for any RepoSync-managed commit.

### 7. RepoSync/owner adoption route

- Record the durable source repository and platform owner for the Dockerfile.
- Confirm or identify the responsible reviewer for `.dockerignore`.
- Record the accepted review and merge route.
- No owner-controlled change is merged outside the agreed route.

## Acceptance criteria

- [x] Reviewed Dockerfile and `.dockerignore` changes are implemented locally.
- [x] Cold, warm no-change and changed-JAR paths are validated locally.
- [x] Required runtime behaviour and artefacts are preserved.
- [x] Feature-branch registry cache seed and reuse are validated in CI.
- [x] Changed JAR rebuilds while stable setup layers remain cached.
- [ ] Shared `develop` cache is validated after merge.
- [ ] RepoSync/owner review and adoption route is recorded.
- [x] Rollback is executable.
- [x] No unrelated Docker, Maven, Compose or Testcontainers change is included.

## Boundaries / non-goals

- No base-image strategy, runtime-command or unrelated Dockerfile functional change.
- No Testcontainers, Compose or Maven change.
- Local evidence is not CI evidence.
- No durable RepoSync adoption before owner approval.
- No CI saving or reliability claim without equivalent measurements.
