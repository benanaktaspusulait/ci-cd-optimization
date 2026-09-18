# Architecture Decisions (ADR)

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Updated after cross-repository validation |
| **Last updated** | 2026-09-18 |

## ADR Index

| ID | Title | Status | Page |
|---|---|---|---|
| ADR-0001 | Run a measured pilot before wider adoption | **Accepted / fulfilled** | ADR — Pilot Approach |
| ADR-0002 | Use Testcontainers for CI integration-test lifecycle | **Accepted for validated pattern** | ADR — Testcontainers & Compose |
| ADR-0003 | Reduce Compose role in CI; retain where useful locally | **Accepted for validated pattern** | ADR — Testcontainers & Compose |
| ADR-0004 | Use measured Docker context/layer/cache optimisation rather than blanket multi-stage assumptions | **Accepted / revised after evidence** | ADR — BuildKit & CI Runner |
| ADR-0005 | Use existing Drone Kubernetes + DIND execution model for Testcontainers | **Accepted for validated pattern** | ADR — BuildKit & CI Runner |
| ADR-0006 | Validate across representative repositories before RepoSync centralisation | **Accepted; central implementation pending** | ADR — Cross-Repository Validation & RepoSync Adoption |

## Status Meaning

- **Accepted / fulfilled** — the decision was used and its intended validation has been completed.
- **Accepted for validated pattern** — demonstrated in SNS/PNR/PCDP branch implementations; durable shared adoption is still pending where RepoSync owns the generated pipeline.
- **Accepted; central implementation pending** — direction agreed by the current delivery evidence, but shared RepoSync implementation remains outstanding.

## Decision Principle

A technically successful repository branch is not the end-state when the same pipeline content is centrally generated. The architecture decision and the ownership route are therefore tracked separately:

1. prove the pattern;
2. validate portability;
3. identify the common subset;
4. centralise shared pipeline logic in RepoSync;
5. verify representative repositories again.

