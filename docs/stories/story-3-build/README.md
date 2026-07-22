# Story 3 — Docker Build Optimisation

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** T2.3 Docker build & image baseline · **Parallel with:** Story 4

## Goal
Select and apply one safe Dockerfile or build-context optimisation for the SNS pilot repository, using the measured T2.3 before-state and proving the impact with before/after numbers.

> **Drone / ownership constraint:** Local Dockerfile and `.dockerignore` prototypes are in scope for Story 3. Durable production Dockerfile changes are expected to require RepoSync; `.dockerignore` ownership still needs confirmation. BuildKit cache mounts can be tested locally, but Drone/DIND CI support is unproven and cache is ephemeral between CI runs unless ACP/ETO provide registry cache support (see T1.5).

## Why
Build time and image size are recurring sources of friction. Story 2 provides the measured before-state for SNS: final image size `906MB`, cold local Docker build `real 1m17.855s`, warm cached build `real 0m0.851s`, full Docker build context `191.27MB`, and N=10 CI elapsed average `13:35`. Story 3 uses these measured baselines and keeps claim boundaries explicit.

## Outcome

Story 3 produced one keep-now change and one carry-forward prototype:

- **Keep now:** targeted `.dockerignore`, reducing Docker build context from `191.27MB` to `189B` while preserving required runtime artefacts. The targeted `.dockerignore` was validated successfully in the SNS repository. For durable reuse and consistency across command-adaptor repositories, `.dockerignore` ownership and distribution should be considered through RepoSync. RepoSync adoption is a recommended follow-up.
- **Carry forward:** layer-order Dockerfile prototype, improving local same-daemon warm-cache rebuild after a real JAR content change from `75.82-77.90s` to `4.62-5.08s`.

No image-size reduction, cold-build improvement, CI saving, production Dockerfile change, RepoSync approval or broad adaptor-family rollout is claimed.

## Acceptance criteria
- [x] T2.3 Docker/image baseline is reviewed as the before-state
- [x] Dockerfile and `.dockerignore` ownership route is confirmed or documented
- [x] One safe Docker/build-context optimisation candidate is selected
- [x] `.dockerignore`, if added or changed, preserves the runtime artefacts required by the Dockerfile
- [x] At least one focused Dockerfile/build-context improvement is applied locally or prepared as a RepoSync-ready change
- [x] Build time, build context and image size are compared before/after where the change is applied, with a keep/adjust recommendation
- [x] No production/base-image change is recommended without RepoSync, ownership, approved image-source and security validation

## Implementation sequencing

T3.2 may satisfy the first implementation candidate where `.dockerignore` / build-context reduction is selected. T3.3 is used for the selected Dockerfile/build optimisation where a non-`.dockerignore` candidate is chosen, or where a second focused candidate is explicitly selected after T3.1. Story 3 should apply one focused change at a time so before/after impact can be attributed.

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T3.1 | [Confirm Dockerfile ownership route and select optimisation candidate](./task-1-review-dockerfile.md) | 2 | Must | Completed |
| T3.2 | [Add or validate .dockerignore while preserving runtime artefacts](./task-2-dockerignore.md) | 1 | Must | Completed - targeted `.dockerignore` validated locally |
| T3.3 | [Apply one safe Docker build optimisation](./task-3-layering-improvement.md) | 2 | Must | Completed - layer-order prototype measured locally; carry forward only |
| T3.4 | [Measure local Docker build and image impact](./task-4-measure-impact.md) | 2 | Should | Completed - impact summary published |
