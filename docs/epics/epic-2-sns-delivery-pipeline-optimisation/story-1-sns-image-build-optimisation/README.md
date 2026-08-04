# E2-S1 — Productionise the SNS Image-Build Optimisation

**Epic:** [Epic 2 — SNS Delivery Pipeline Optimisation](../README.md)

| Field | Value |
|---|---|
| **ID** | E2-S1 |
| **Status** | In progress |
| **Primary output** | Validated, adopted SNS image-build optimisation with CI evidence and RepoSync/owner approval |

## Why

The pilot validated a targeted layer-ordering change and a build-context improvement.
Neither is a durable outcome until CI behaviour and the owner adoption route are
confirmed end to end.

## Goal

Implement the image-build changes, validate them in real branch CI and complete
the RepoSync/owner adoption route as a single coherent outcome.

## Tasks

| ID | Task | Estimate | Priority | Status |
|---|---|---:|---|---|
| E2-S1.1 | [Implement, Validate and Prepare Adoption of the SNS Image-Build Optimisation](./task-1-sns-image-build-optimisation.md) | 5 | Must | In progress |

## Boundaries / non-goals

- No Testcontainers, Compose, Maven or unrelated Dockerfile change.
- No durable adoption before owner approval.
- Local evidence is not CI evidence.

## Story acceptance criteria

- [ ] Local implementation, CI validation and adoption route are covered in a single task.
- [ ] Local and CI evidence remain distinguishable.
- [ ] Adoption is not claimed until the responsible owner records the decision.
- [ ] Existing Compose/full-E2E path remains unchanged.
