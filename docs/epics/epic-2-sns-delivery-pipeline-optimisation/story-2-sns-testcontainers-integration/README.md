# E2-S2 — Productise the SNS Testcontainers Integration Path

**Epic:** [Epic 2 — SNS Delivery Pipeline Optimisation](../README.md)

| Field | Value |
|---|---|
| **ID** | E2-S2 |
| **Status** | Proposed / New |
| **Primary output** | A maintained opt-in Testcontainers path covering Redis, Kafka, Schema Registry and one real SNS application flow, validated locally and, where approved, in branch CI |

## Why

The Redis pilot established a narrow Testcontainers workflow. Kafka, Schema Registry and
one real SNS application flow form one coherent technical outcome and should be
implemented and validated together.

## Goal

Extend the validated Redis Testcontainers foundation into a maintained, repeatable SNS
integration path.

## Tasks

| ID | Task | Estimate | Priority | Status |
|---|---|---:|---|---|
| E2-S2.1 | [Implement and Validate the SNS Testcontainers Integration Path](./task-1-sns-testcontainers-integration.md) | 5 | Must | Proposed / New |

## Story acceptance criteria

- [ ] E2-S2.1 delivers a working opt-in Testcontainers application path.
- [ ] Existing Compose/full-E2E behaviour remains unchanged.
