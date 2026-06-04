# Story 3 — Docker Build Optimisation

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** Story 2 · **Parallel with:** Story 4

## Goal
Apply one or more practical Dockerfile / build-context improvements and prove their impact with before/after numbers.

> **Drone constraint:** Multi-stage builds and `.dockerignore` work locally and in any Docker environment. BuildKit cache mounts work locally but are ephemeral in CI (DIND resets per build). Remote registry cache requires platform/ETO action (see T1.5). Focus local-first.

## Why
Build time and image size are recurring sources of friction. Small, well-targeted changes (layer ordering, `.dockerignore`, cache mounts, multi-stage) often deliver disproportionate gains without changing application behaviour.

## Acceptance criteria
- [ ] Current Dockerfile and build context reviewed; cache-invalidation risks identified
- [ ] `.dockerignore` present and appropriate
- [ ] At least one layering/cache improvement applied
- [ ] Build time and image size compared before/after, with a keep/adjust recommendation

## Tasks
| Task | Title | Est | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T3.1 | [Review current Dockerfile & build context](./task-1-review-dockerfile.md) | M | Must | Not started |
| T3.2 | [Add or validate .dockerignore](./task-2-dockerignore.md) | S | Must | Not started |
| T3.3 | [Apply Dockerfile layering / cache improvement](./task-3-layering-improvement.md) | M | Must | Not started |
| T3.4 | [Measure local & CI build impact](./task-4-measure-impact.md) | M | Should | Not started |
