# E2-S1.3 — Validate Approved SNS Changes in CI and Decide Adoption

**Story:** [E2-S1 — Productionise Validated SNS Outcomes](./README.md)

| Field | Value |
|---|---|
| **ID** | E2-S1.3 |
| **Type** | Real-CI validation and component-level adoption decision |
| **Estimate** | 2 |
| **Priority** | Must |
| **Depends on** | One or more approved E2-S1.1/E2-S1.2 outputs, [pilot CI baseline](../../../../solution/story-2/T2.2-pipeline-baseline.md) and required RepoSync/platform route |
| **Status** | Proposed / New — not started |
| **Primary output** | Exact CI run evidence and independent component-level adopt/revise/retain/stop dispositions |

## Why

Local results do not establish CI feasibility or benefit, and the image-build and Redis outcomes may warrant different adoption decisions.

## Goal

Validate only explicitly approved SNS candidates in real branch/MR pipelines and record an independent disposition for each component.

## Scope

- Run approved image-build and `.dockerignore` changes through real branch/MR pipelines.
- Use the existing pilot baseline only where metric definitions, execution scope and relevant environment are equivalent. Otherwise capture a new current-path baseline before making a comparison.
- Validate image artefacts and runtime behaviour.
- Evaluate the Redis workflow in CI only when separately approved for CI evaluation; retaining it local is a valid outcome.
- Capture exact run references, environment, Docker/image state, failures and retries.
- Preserve local-versus-CI evidence separation.
- Record component-level `adopt`, `revise`, `retain local`, `retain-as-candidate` or `stop` decisions.
- Document rollout and rollback for each default-path adoption.

## Boundaries / non-goals

- No forced Redis default-CI enablement.
- No Kafka, Schema Registry, command/snapshot or full-topology migration.
- Image-build and Redis changes are not accepted or rejected as one package.
- No CI saving or reliability claim without equivalent evidence.

## Acceptance criteria

- [ ] Exact CI run references, environment, image state and comparison method are recorded.
- [ ] Any before/after comparison uses equivalent scope and metric definitions; otherwise the result is reported as a new baseline rather than an improvement.
- [ ] Image-build, `.dockerignore` and Redis candidates receive independent dispositions.
- [ ] Redis CI evaluation occurs only when explicitly approved.
- [ ] Local and CI evidence remain separated; failures and retries are not omitted.
- [ ] Image artefacts and runtime behaviour are validated for adopted image changes.
- [ ] Rollout and rollback are documented for every default-path adoption.
- [ ] No broad integration-suite migration is required by this task.
- [ ] Adoption is not claimed until the responsible owner records the decision.
