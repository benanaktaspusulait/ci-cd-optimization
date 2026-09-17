# Phased Plan — Actual Delivery Progression

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Cross-repository validation complete; RepoSync adoption pending |
| **Last updated** | 2026-09-17 |

The original four-week pilot plan is now historical. The work evolved through evidence gates rather than a fixed calendar plan.

## Actual Phases

| Phase | Repository / focus | Outcome | Status |
|---|---|---|---|
| 1 | SNS reference implementation | Established the end-to-end pattern and measurement discipline | Complete |
| 2 | PNR portability validation | Confirmed that the pattern can be adapted to a different test inventory/topology | Complete |
| 3 | PCDP large-pipeline validation | Confirmed the architecture against a substantially larger suite and more complex pipeline | Complete |
| 4 | Shared pipeline adoption | Move common RepoSync-managed pieces to their durable central source | Pending |
| 5 | Post-adoption verification | Re-run representative repos and confirm timing/coverage/reliability after centralisation | Pending |

## Phase 1 — SNS Reference Implementation

Key outcomes:

- baseline captured using successful CI runs;
- Compose-heavy integration path consolidated into Testcontainers-owned lifecycle;
- exact Docker image validated separately;
- Docker context/layer/cache strategy improved;
- Maven reuse reduced duplicate work;
- Trivy DB preparation moved off the final critical path;
- false-green protections added.

Measured result: **13m35s average baseline (N=10) → 4m57s / 4m44s observed optimised runs**.

## Phase 2 — PNR Portability Validation

Key outcomes:

- transferred the architecture without copying SNS-specific assumptions;
- added PNR feature/scenario inventory handling;
- retained exact built-image validation;
- made aggregate startup dependent on the selected test scope;
- preserved PNR-specific Kafka/test lifecycle behaviour.

The supplied evidence is sufficient for architectural portability; no comparable end-to-end timing set is recorded on this page.

## Phase 3 — PCDP Large-Pipeline Validation

Key outcomes:

- applied the pattern to a significantly larger feature/test inventory;
- protected 131 feature files / 480 declared / 1382 executable cases;
- mandatory snapshot path protects 125 declared / 229 executable cases;
- consolidated infrastructure/application lifecycle into Testcontainers-owned execution;
- retained exact built-image runtime validation and final Trivy scan;
- kept broad cleanup work scoped so business/test semantics were not changed merely to improve speed.

Observed reference comparison: approximately **16m30s → 7m38s**.

## Phase 4 — RepoSync Centralisation

This is the current delivery gate.

1. Identify the `.drone.star` and other centrally managed parts common to SNS, PNR and PCDP.
2. Separate common logic from repository-specific configuration/test code.
3. Implement the accepted common pattern in the RepoSync-managed source.
4. Avoid per-repository permanent copies of centrally owned pipeline logic.
5. Review centrally managed changes with the appropriate shared-pipeline owners.

## Phase 5 — Post-Adoption Verification

After RepoSync adoption:

- run representative repositories through the centrally generated pipeline;
- verify business-test inventory and exact-image runtime checks still execute;
- compare critical-path timing with the validated branch implementations;
- record any repository-specific exception explicitly rather than adding blanket switches.

