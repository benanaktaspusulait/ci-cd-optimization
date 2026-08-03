# E2-S1 — Productionise Validated SNS Outcomes

**Epic:** [Epic 2 — SNS Delivery Pipeline Optimisation](../README.md)

| Field | Value |
|---|---|
| **ID** | E2-S1 |
| **Status** | Proposed / New |
| **Primary output** | Reviewable SNS image-build and opt-in Redis changes plus component-level real-CI/adoption evidence |

## Why

Validated local outcomes need production-quality packaging and evidence without broadening their original scope.

## Goal

Implement the image-build and Redis outcomes independently, then validate only explicitly approved candidates in real CI.

## Tasks

| ID | Task | Estimate | Priority | Status | Depends on |
|---|---|---:|---|---|---|
| E2-S1.1 | [Implement validated SNS image-build changes](./task-1-sns-image-build.md) | 2 | Must | Proposed / New | Story 3 evidence and ownership confirmation |
| E2-S1.2 | [Productise the opt-in Redis Testcontainers workflow](./task-2-redis-testcontainers.md) | 2 | Must | Proposed / New | Story 4 evidence and repository-owner approval |
| E2-S1.3 | [Validate approved SNS changes in CI and decide adoption](./task-3-ci-validation-adoption.md) | 2 | Must | Proposed / New | One or more approved E2-S1.1/E2-S1.2 outputs and platform route |

## Boundaries / non-goals

- E2-S1.1 and E2-S1.2 may proceed and be accepted independently.
- E2-S1.3 evaluates components independently; it does not require Redis default-CI enablement.
- Kafka, Schema Registry and full-topology candidates are outside this story.
- No production or CI adoption is claimed before the responsible owner records a disposition.

## Story acceptance criteria

- [ ] Task boundaries remain aligned with the pilot evidence.
- [ ] Image-build and Redis outcomes have separate evidence and decisions.
- [ ] Local and CI evidence remain distinguishable.
- [ ] Unapproved candidates are not implemented or described as committed scope.
