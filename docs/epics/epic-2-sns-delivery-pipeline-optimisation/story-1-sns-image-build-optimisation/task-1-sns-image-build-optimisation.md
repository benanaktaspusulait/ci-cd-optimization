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

The local pilot found a targeted build-context change and a layer-ordering candidate.
Neither is a durable SNS repository outcome until the intended diff, CI behaviour and
owner adoption route are verified end to end.

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
- No startup scripts, certificates or configuration files are hidden.

### 3. Default BuildKit builder

- Use the default BuildKit builder present in the target CI environment.
- Do not configure a custom builder or registry mirror unless required.

### 4. Local build validation

- Capture cold, warm no-change and real JAR-content-change builds.
- Validate image startup and required runtime artefacts.
- Record exact commands, environment, Docker/image state, failures and retries.

### 5. Registry-backed branch cache (feature branch)

- Run a real branch pipeline against the feature branch.
- Confirm that the registry-backed layer cache is populated on first run.
- Confirm cache reuse on a no-change second run.
- Confirm that a real JAR-content-change triggers only the expected layer rebuild.
- Record the exact CI run reference, environment, image state and timing.

### 6. Develop shared-cache policy

- After the feature branch is merged to `develop`, validate that the shared cache
  is seeded correctly and available to subsequent branch builds.
- Record the exact `develop` pipeline run reference and cache behaviour.
- This step completes after merge; it does not block the feature-branch acceptance
  criteria.

### 7. Feature-branch cache seed/reuse

- Confirm that a fresh feature branch can reuse the `develop` shared cache.
- Record the run reference and layer-hit evidence.

### 8. Changed-JAR rebuild behaviour

- Confirm that a changed-JAR rebuild on a feature branch reuses the `yum`/`envconsul`
  setup layers from cache and rebuilds only the application-JAR copy and following layers.
- Record the exact before/after layer hash evidence.

### 9. Image inspect and runtime startup

- Run `docker inspect` on the produced image and confirm digest, labels and layer count.
- Start the image and confirm Java, `envconsul`, both required runtime JARs, `fdpuser`
  and the preserved Java/JMX/OpenTelemetry command.

### 10. Rollback

- Document an executable rollback for the local change.
- Document a rollback route for any RepoSync-managed commit.

### 11. RepoSync/owner adoption route

- Record the durable source repository and platform owner for the Dockerfile.
- Confirm or identify the responsible reviewer for `.dockerignore`.
- Record the accepted review and merge route.
- No owner-controlled change is merged outside the agreed route.

## Acceptance criteria

### Local implementation
- [x] Exact changed files and intended diff are reviewable.
- [x] No unrelated Dockerfile functional change is included.
- [x] Cold and warm no-change builds succeed.
- [x] A real JAR-content-change rebuild is measured.
- [x] Runtime smoke validation succeeds and required artefacts remain present.
- [x] Environment, image state, commands, failures and retries are recorded.
- [x] Local evidence is not represented as CI evidence.
- [x] No image-size, cold-build or CI benefit is claimed unless newly measured.
- [x] Rollback is executable and documented.

### Feature-branch CI
- [ ] Exact CI run reference, environment and image state are recorded.
- [ ] Registry-backed cache is populated on first run and reused on no-change second run.
- [ ] Changed-JAR rebuild reuses setup layers and rebuilds only expected layers.
- [ ] Image artefacts and runtime behaviour are validated in CI.
- [ ] Local and CI evidence remain separated; failures and retries are not omitted.
- [ ] No CI saving or reliability claim is made without equivalent measurements.

### Develop shared-cache (post-merge)
- [ ] Develop pipeline run reference is recorded.
- [ ] Shared cache is seeded correctly and available to subsequent branch builds.

### Adoption
- [ ] The durable repository/RepoSync review route is recorded.
- [ ] The responsible reviewer for `.dockerignore` is confirmed.
- [ ] No owner-controlled change is merged outside the agreed route.
- [ ] Rollout and rollback are documented for any default-path adoption.
- [ ] Adoption is not claimed until the responsible owner records the decision.

## Boundaries / non-goals

- No base-image strategy, runtime-command or unrelated Dockerfile functional change.
- No Testcontainers, Compose or Maven change.
- Local evidence is not CI evidence.
- No durable RepoSync adoption before owner approval.
- No CI saving or reliability claim without equivalent measurements.
