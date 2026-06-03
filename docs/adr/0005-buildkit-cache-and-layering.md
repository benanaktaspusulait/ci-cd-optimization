# ADR-0005: Use BuildKit cache + layered multi-stage builds

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [Story 2](../stories/story-2-build/README.md), [tech notes](../stories/tech-notes.md#buildkit-remote-cache)

## Context
Builds repeatedly download dependencies and rebuild layers that haven't changed, because dependency resolution isn't separated from source build and CI runners don't keep local cache between jobs.

## Decision
We will restructure the pilot Dockerfile to **copy dependency metadata before source**, use **multi-stage builds**, and use **BuildKit cache mounts** for the dependency cache. Where CI cache infra exists, we will use a **branch-aware registry cache**. A clean, cache-less build must always still succeed.

## Consequences
- **Positive:** faster rebuilds via cache reuse; smaller runtime image; predictable CI build time.
- **Negative / trade-offs:** registry remote cache needs infrastructure that may be **platform/ETO-owned**; cache misuse could in theory produce stale images (risk R6).
- **Follow-ups:** apply one change at a time and measure (T2.3–T2.4); verify a no-cache build still works; route remote-cache infra to platform/ETO via Story 5.

## Alternatives considered
| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Single-stage, copy-all Dockerfile | Simple | No layer caching; large images; slow | Current pain point |
| Cache mounts only (no remote cache) | No infra needed | Lost between CI jobs | Good local win; insufficient for CI alone |
| Pre-built dependency images | Fast | Maintenance + governance overhead | Heavier than a first pilot needs (future platform item) |
