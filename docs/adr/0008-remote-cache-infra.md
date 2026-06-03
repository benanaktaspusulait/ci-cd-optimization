# ADR-0008: BuildKit remote cache requires platform/ETO infrastructure

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** CST (proposal), platform/ETO (ownership and provisioning)
- **Related:** [ADR-0005 — BuildKit cache and layering](0005-buildkit-cache-and-layering.md), [tech-notes.md — BuildKit remote cache](../stories/tech-notes.md), [T2.3](../stories/story-2-build/task-3-layering-improvement.md), [Story 5](../stories/story-5-findings/README.md)

## Context

GitLab CI runners are typically **ephemeral** — each job starts with a clean environment and no persistent local disk. Without a remote cache, every CI build resolves dependencies and rebuilds layers from scratch, even when nothing has changed.

BuildKit supports a registry-backed remote cache:

```bash
docker buildx build \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:main \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:$CI_COMMIT_REF_SLUG \
  --cache-to=type=registry,ref=$REGISTRY_IMAGE/cache:$CI_COMMIT_REF_SLUG,mode=max \
  --tag $REGISTRY_IMAGE:$CI_COMMIT_SHA --push .
```

This approach stores build cache layers in the container registry alongside application images. The benefits are significant (faster CI, fewer dependency downloads), but the prerequisites are non-trivial:

- **Registry storage:** cache layers consume registry space; a retention/eviction policy is needed.
- **Write permissions:** CI jobs need push access to a cache namespace in the registry.
- **Runner BuildKit support:** `docker buildx` must be available; runner executor must support it.
- **Security:** cache images must be scanned or excluded from production promotion paths.

Provisioning and maintaining this infrastructure is a **platform/ETO responsibility**, not CST-local.

## Decision

We will document the remote-cache pattern and validate its technical feasibility on the pilot repository (T2.3), but will **not implement it as a CST-local change** without explicit platform/ETO involvement:

1. T2.3 will apply CST-local optimisations (layer ordering, `.dockerignore`, cache mounts for local builds) and measure their impact.
2. Remote cache (registry-backed) will be **flagged in Story 5** as a platform/ETO item with this ADR attached.
3. If platform/ETO can provision the cache namespace before the pilot ends, the pilot will include a remote-cache measurement. Otherwise, it is a post-pilot recommendation.

## Consequences

- **Positive:**
  - CST does not block on platform/ETO to deliver Story 2 improvements.
  - The pattern is documented and evidence-ready for the platform team.
  - Local-only cache mounts (`--mount=type=cache`) still provide meaningful speedup for local builds.

- **Negative / trade-offs:**
  - Full CI cache benefit is deferred until platform/ETO acts.
  - Pipeline duration improvement target (≥ 20% reduction) may not be achievable without remote cache — this should be noted in the Story 5 findings.

- **Follow-ups:**
  - T2.3: implement and measure CST-local improvements (layer ordering, cache mounts). Note if remote cache is not yet available.
  - T5.2: classify remote cache as platform/ETO item; attach this ADR.
  - Post-pilot: platform/ETO to provision registry cache namespace, set retention policy, and update the shared CI/CD template.

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| No remote cache | No infra needed | Full rebuild every job; defeats cache-optimisation goal | Unacceptable for the pipeline duration target |
| CST-managed cache namespace | Pilot can proceed independently | Unauthorised registry usage; no retention policy; security gap | Requires platform/ETO approval and ownership |
| Local-only cache mounts | No infra; immediate benefit | Only helps local builds; CI runners still rebuild from scratch | Valid partial improvement; not a substitute for remote cache |
| GHA/Azure cache action (external) | Mature tooling | Not applicable — this project uses GitLab CI | Not relevant |
