# E2-S2 — Productise the SNS Testcontainers Integration Path

**Epic:** [Epic 2 — SNS Delivery Pipeline Optimisation](../README.md)

| Field | Value |
|---|---|
| **ID** | E2-S2 |
| **Status** | Proposed / New |
| **Primary output** | A maintained opt-in Testcontainers path covering Redis, Kafka, Schema Registry and one real SNS application flow, validated locally and in branch CI |

## Why

The Redis pilot established a narrow Testcontainers workflow. Kafka, Schema Registry and
one real SNS application flow form one coherent technical outcome and should be
implemented and validated together rather than split across analysis or reporting tasks.

## Goal

Extend the validated Redis Testcontainers foundation into a maintained, repeatable SNS
integration path.

## Tasks

| ID | Task | Estimate | Priority | Status |
|---|---|---:|---|---|
| E2-S2.1 | [Implement and Validate the SNS Testcontainers Integration Path](./task-1-sns-testcontainers-integration.md) | 5 | Must | Proposed / New |

## Boundaries / non-goals

- No migration of the complete integration-test suite.
- No removal of the existing Compose environment.
- No default-CI enablement without explicit approval.
- No CD pipeline, Helm, `kd`, PVC or deployment review.
- No production runtime or deployment change.

## Story acceptance criteria

- [ ] Task boundaries remain aligned with the pilot evidence.
- [ ] Redis, Kafka and Schema Registry have separate lifecycle evidence.
- [ ] Local and CI evidence remain distinguishable.
- [ ] Existing Compose/full-E2E path remains unchanged.
