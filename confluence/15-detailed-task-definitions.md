# Detailed Task Definitions — Historical Pilot and Current Adoption Work

| Field | Value |
|---|---|
| **Status** | Historical pilot tasks complete in substance; RepoSync adoption tasks active |
| **Last updated** | 2026-09-21 |

The original T1.1–T6.2 task set is retained as the history of the initial pilot. It should no longer be used as the live status source for the current cross-repository/adoption phase.

## Historical Task Pages

| Story | Tasks | Current interpretation |
|---|---|---|
| Story 1 — Pipeline Assessment | T1.1–T1.5 | Superseded by implementation evidence from three repos |
| Story 2 — Baseline & Pilot Scope | T2.1–T2.4 | SNS baseline established; later repos used for portability/scale validation |
| Story 3 — Docker Build Optimisation | T3.1–T3.4 | Validated context/layer/cache pattern |
| Story 4 — Testcontainers Pilot | T4.1–T4.4 | Expanded from prototype to validated CI lifecycle |
| Story 5 — Compose Rationalisation | T5.1–T5.2 | Validated reduced CI role / local retention as needed |
| Story 6 — Outcome / Adoption | T6.1–T6.2 | Cross-repo validation completed; central RepoSync adoption remains |

## Current Adoption Tasks

### A1 — Extract common RepoSync-managed elements

**Goal:** derive the shared pipeline pattern from SNS, PNR and PCDP without embedding repository-specific test knowledge.

### A2 — Implement shared pattern in RepoSync

**Goal:** make the common CI behaviour durable in the centrally managed source.

### A3 — Verify generated pipelines

**Goal:** run representative repositories after RepoSync adoption and confirm coverage, exact-image validation, scanning and performance.

### A4 — Publish final reuse standard

**Goal:** replace pilot/proposal language with the accepted common pattern, evidence boundaries and repository adaptation rules.

