# Project Plan and Governance — Current State

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Cross-repository validation complete; central adoption pending |
| **Last updated** | 2026-09-18 |

## Delivery Model

The original four-week pilot plan is complete in substance. Governance now focuses on the handoff from validated repository implementations to the centrally managed RepoSync pipeline.

## Milestones

| Milestone | Outcome | Status |
|---|---|---|
| M1 | Baseline / pipeline boundaries understood | Complete |
| M2 | SNS reference implementation validated | Complete |
| M3 | PNR portability validation | Complete |
| M4 | PCDP large-pipeline validation | Complete |
| M5 | Common RepoSync pattern extracted and reviewed | Pending |
| M6 | Representative repos verified after central adoption | Pending |

## Governance Principle

The central pipeline source and repository-owned application/test code have different ownership boundaries.

### RepoSync/common ownership

- shared Drone step/dependency graph;
- common Docker/Testcontainers CI environment settings;
- shared image build/cache orchestration;
- common runtime-smoke/Trivy step sequencing where applicable.

### Repository ownership

- topic catalogue and suffix rules;
- aggregate selection;
- feature/scenario inventory;
- application startup and health properties;
- test fixtures and business assertions.

## Change Controls

A common change is ready for adoption only when:

- it has evidence from the validated repositories;
- it does not reduce business/test coverage;
- exact built-image validation remains present;
- security scan/reporting remains present;
- rollback is possible;
- repository-specific assumptions are parameterised locally or excluded from the common code.

## Measurement Governance

- Compare like-for-like workloads.
- Prefer multiple successful runs for stable baselines where available.
- Label single-run values as observed, not representative averages.
- Do not sum overlapping Drone step timings.
- Distinguish controlled Docker experiments from CI measurements.
- Record rejected experiments so they are not accidentally reintroduced.

## Current Review / Merge State

SNS, PNR and PCDP have received technical review/acceptance. Merge is intentionally deferred while shared RepoSync-managed elements are prepared through the correct common ownership route.

