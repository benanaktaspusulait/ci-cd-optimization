# Story 3 — Docker Build Optimisation

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** Story 2 · **Parallel with:** Story 4

## Goal
Plan, prototype or apply one or more practical Dockerfile / build-context improvements for the SNS pilot repository and prove their impact with before/after numbers.

> **Drone / ownership constraint:** Local Dockerfile and `.dockerignore` prototypes are in scope for Story 3. Durable production Dockerfile changes are expected to require RepoSync; `.dockerignore` ownership still needs confirmation. BuildKit cache mounts can be tested locally, but Drone/DIND CI support is unproven and cache is ephemeral between CI runs unless ACP/ETO provide registry cache support (see T1.5).

## Why
Build time and image size are recurring sources of friction. Story 2 provides the measured before-state for SNS: final image size `906MB`, cold local Docker build `real 1m17.855s`, warm cached build `real 0m0.851s`, full Docker build context `191.27MB`, and N=10 CI elapsed average `13:35`. Story 3 should use these measured baselines and avoid fixed saving claims until after-values are captured.

## Acceptance criteria
- [ ] Current Dockerfile and build context reviewed against the Story 2 measured baseline; cache-invalidation and image-size drivers identified
- [ ] `.dockerignore` ownership confirmed and an appropriate candidate added or proposed without excluding the runtime artefacts required by the Dockerfile
- [ ] At least one focused Dockerfile/build-context improvement is prototyped locally or prepared as a RepoSync-ready change
- [ ] Build time, build context and image size are compared before/after where the change is applied, with a keep/adjust recommendation
- [ ] Production rollout route is documented, including RepoSync, ACP and approved image-source constraints where relevant

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T3.1 | [Review current Dockerfile & build context](./task-1-review-dockerfile.md) | 2 | Must | Not started |
| T3.2 | [Add or validate .dockerignore](./task-2-dockerignore.md) | 1 | Must | Not started |
| T3.3 | [Apply Dockerfile layering / cache improvement](./task-3-layering-improvement.md) | 2 | Must | Not started |
| T3.4 | [Measure local & CI build impact](./task-4-measure-impact.md) | 2 | Should | Not started |
