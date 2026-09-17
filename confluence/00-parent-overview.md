# FDP Container & CI/CD Optimisation — Validated Reference Implementation and Cross-Repository Validation

| Field | Value |
|---|---|
| **Owner** | CST / Cerberus Delivery — shared pipeline adoption via RepoSync ownership |
| **Created by** | Benan Aktas |
| **Status** | Validated across SNS, PNR and PCDP — common RepoSync implementation pending |
| **Last updated** | 2026-09-17 |
| **Labels** | `ci-cd`, `testcontainers`, `docker`, `reposync`, `cross-repository-validation` |

## Executive Summary

The original one-repository pilot has progressed into a validated reference implementation and cross-repository portability exercise.

- **SNS** established the reference implementation and measurement method.
- **PNR** validated that the pattern can be adapted rather than copied mechanically.
- **PCDP** validated the approach against a substantially larger test surface and pipeline.

All three repository implementations have been technically reviewed and accepted. They have not been merged because the durable implementation of the shared pipeline elements belongs in the **RepoSync-managed source**, rather than as repository-specific copies.

The reusable pattern combines:

- targeted Docker build-context reduction and layer ordering;
- registry-backed BuildKit cache reuse where available;
- a consolidated Maven-owned Testcontainers test lifecycle;
- mandatory Docker/test-discovery/scenario-completeness guards;
- exact built-image runtime validation after in-JVM integration testing;
- reuse of verified Maven reactor artefacts;
- early/parallel Trivy database preparation while retaining the existing scan policy;
- deterministic shutdown, bounded polling, readiness checks and failure diagnostics.

The work does **not** claim that every repository should have identical implementation details. Topic catalogues, aggregate dependencies, scenario inventory, application startup and health/readiness wiring are repository-specific and must be reassessed.

## Measured Evidence

### SNS — reference implementation

Successful CI baseline: **13m35s average across 10 successful runs**.

Measured optimised runs: **4m57s** and **4m44s**.

Best observed comparison: **8m51s reduction**, approximately **65.2%**, or approximately **2.87x faster**. The 4m44s result is an observed run, not a guaranteed future duration.

### PNR — portability validation

The final retained implementation includes the same core architecture with PNR-specific adaptations, including feature/scenario inventory protection, selective aggregate startup and exact built-image runtime validation.

No end-to-end timing claim is recorded here because a comparable baseline/final timing set has not been supplied for this page.

### PCDP — large-pipeline validation

Observed reference comparison during validation: approximately **16m30s → 7m38s**. This is an observed before/after comparison, not an N-run statistical baseline.

PCDP protects a much larger feature inventory: **131 feature files**, **480 declared scenarios**, **1382 executable cases**; the snapshot suite contains **125 declared / 229 executable** cases.

## Delivery Status

| Repository | Role | Technical status | Merge status |
|---|---|---|---|
| SNS | Reference implementation | Reviewed / technically accepted | Deferred pending shared RepoSync-managed implementation |
| PNR | Portability validation | Reviewed / technically accepted | Deferred pending shared RepoSync-managed implementation |
| PCDP | Large / structurally different validation | Reviewed / technically accepted | Deferred pending shared RepoSync-managed implementation |

This status is intentional. The remaining delivery work is not another repository-local optimisation cycle; it is to identify the common `.drone.star` / centrally managed elements and implement them through the durable RepoSync route.

## What Was Proven

1. Testcontainers can run in the Drone Kubernetes + DIND model used by these pipelines.
2. The integration-test path can be consolidated without silently reducing business-test coverage.
3. Exact packaged-image validation remains necessary because in-JVM tests do not validate Docker packaging/runtime behaviour.
4. Build/cache improvements must be measured in the target repository; useful structural patterns transfer, but timings do not.
5. False-green protection is part of the optimisation: Docker availability, test discovery, scenario inventory and completed-scenario counts must fail loudly.
6. Trivy scanning/reporting remains in the flow; database preparation can move off the final critical path without changing the existing vulnerability policy.
7. RepoSync-managed pipeline changes should be centralised only after cross-repository validation identifies the genuinely reusable subset.

## Scope Now

### Completed / validated

- SNS reference implementation.
- PNR portability validation.
- PCDP large-pipeline validation.
- Docker build-context/layer/cache patterns.
- Testcontainers CI execution through DIND.
- Exact built-image runtime validation.
- Test completeness and Docker-required guards.
- Trivy critical-path optimisation with existing scan policy retained.

### Remaining

- Extract the common RepoSync-managed pipeline pattern.
- Review the common pattern with the appropriate shared-pipeline owners.
- Implement the accepted common elements in RepoSync.
- Keep repository-specific test/application adaptations in their owning repositories.
- Re-run representative repositories after RepoSync adoption to confirm no regression.

### Out of scope for this work

- CD/deploy pipeline redesign.
- Helm/release-flow migration.
- Organisation-wide base-image redesign.
- Changing vulnerability severity or Trivy exit-code policy.
- Unrelated application/business behaviour changes.

## Documentation Map

| Page | Purpose |
|---|---|
| Proposal Matrix | Original proposals mapped to actual validated outcome |
| Phased Plan | Actual SNS → PNR → PCDP → RepoSync adoption progression |
| Risks and DACI | Current adoption risks and ownership decisions |
| Technical Details | Reusable technical pattern and evidence boundaries |
| Docker Build & Infrastructure | Final Docker/context/cache approach |
| Testcontainers | Final test-owned lifecycle and repository-specific adaptations |
| Pipeline & Drone Context | Before/after CI dependency graph and RepoSync boundary |
| Backlog Summary | Historical pilot backlog plus current adoption work |
| Architecture Decisions | Updated ADR status and new RepoSync adoption ADR |
| Working Agreements and Metrics | Measurement rules and cross-repository evidence |
| Security Plan | Security controls actually retained/validated |
| Code Examples and Templates | Validated patterns, not speculative copy/paste templates |

