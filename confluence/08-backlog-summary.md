# Backlog Summary — Pilot Closure and Shared Adoption

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Original pilot complete in substance; RepoSync adoption pending |
| **Last updated** | 2026-09-18 |

## Historical Pilot Backlog

Stories 1–6 remain useful as the history of how the work was initiated. They should not be treated as an accurate live delivery board now that the implementation has progressed through three repositories.

| Story | Original purpose | Current outcome |
|---|---|---|
| S1 Pipeline Assessment | Understand Drone/RepoSync/DIND boundaries | Completed through implementation evidence |
| S2 Baseline & Pilot Scope | Select repo and measure current state | SNS baseline established; later repos used for portability/scale validation |
| S3 Docker Build Optimisation | Improve build/context/cache behaviour | Validated pattern retained |
| S4 Testcontainers Pilot | Prove one dependency locally | Expanded to mandatory CI Testcontainers lifecycle |
| S5 Compose Rationalisation | Decide CI/local role | CI role substantially reduced in validated path; local use may remain |
| S6 Outcome / Ownership | Route follow-up | Cross-repo validation complete; RepoSync adoption is the remaining route |

## Cross-Repository Validation Work

| Item | Repository | Status |
|---|---|---|
| Reference implementation | SNS | Reviewed / technically accepted |
| Portability validation | PNR | Reviewed / technically accepted |
| Large-pipeline validation | PCDP | Reviewed / technically accepted |

All three are intentionally unmerged while shared RepoSync-managed pieces are prepared for the durable common implementation.

## Current Adoption Backlog

### A1 — Extract common RepoSync pattern

**Goal:** identify the pipeline elements proven common across SNS, PNR and PCDP.

Acceptance:

- common vs repo-specific changes enumerated;
- no business-test assumptions embedded in common pipeline code;
- rollback route documented.

### A2 — Implement common elements in RepoSync

**Goal:** move centrally owned pipeline logic to the durable source.

Acceptance:

- shared `.drone.star` pattern updated through normal ownership/review route;
- generated repository result matches validated dependency graph;
- no repo-local permanent fork of shared pipeline logic required.

### A3 — Verify representative repositories after RepoSync adoption

**Goal:** confirm centralisation preserves coverage and performance characteristics.

Acceptance:

- mandatory Docker/test/scenario guards pass;
- exact built-image runtime validation passes;
- Trivy scan/reporting remains present;
- representative timing captured with the same evidence rules;
- any repo-specific exception documented explicitly.

### A4 — Publish final engineering standard / reuse guide

**Goal:** replace the old pilot framing with the validated reusable pattern and boundaries.

