# ADR-0006: Use a layered base-image hierarchy

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** CST (proposal), platform/ETO (ownership decision)
- **Related:** [tech-notes.md — Base image strategy](../stories/tech-notes.md), [SECURITY.md](../../SECURITY.md), [Story 5 — ownership classification](../stories/story-5-findings/README.md)

## Context

Application Dockerfiles currently inherit from arbitrary upstream images (e.g. `openjdk:17`, `eclipse-temurin:17-jre`). There is no shared base-image governance:

- Each repository pins a different upstream tag or uses `latest`, making centrally patching CVEs impossible.
- Runtime images include build tooling (Maven, compilers) that belongs only in build stages.
- There is no organisation-wide scanning gate that checks base images before they are used.
- Coordinating a base-image bump across many repositories requires per-repo manual work.

A four-layer hierarchy is documented in `tech-notes.md`:

```
base-os        ← patched OS layer (e.g. UBI minimal, Debian slim)
  └── base-runtime  ← JRE + core runtime dependencies
        └── base-build   ← JDK + build tools (Maven, Gradle) — build stages only
              └── application  ← application code, built by the team
```

## Decision

We will adopt the layered base-image hierarchy described in `tech-notes.md` as the target pattern for this pilot and any subsequent rollout:

1. Application Dockerfiles will use **versioned, digest-pinned** `base-runtime` and `base-build` images rather than direct upstream references.
2. The `base-os` and `base-runtime` layers will be owned and published by **platform/ETO** on a scheduled rebuild cadence.
3. CST will validate the pattern on the pilot repository; the final ownership decision is made in Story 5.

## Consequences

- **Positive:**
  - CVE patches applied once to `base-os`/`base-runtime` propagate to all applications on next rebuild.
  - Smaller runtime images — build tooling is confined to `base-build`, not shipped in the final image.
  - Simpler application Dockerfiles — teams inherit a known-good runtime without repeating OS-level setup.
  - Central compliance (SBOM, scanning, signing) can be applied at the base layer.

- **Negative / trade-offs:**
  - Requires platform/ETO to maintain and publish the shared layers — additional operational burden.
  - Application teams lose direct control of the runtime environment; changes require a base-image update request.
  - Bootstrapping cost: defining, building, scanning, and publishing the base images is not a CST-local task.

- **Follow-ups:**
  - Classify base-image ownership in T5.2 (CST-local vs platform/ETO).
  - If platform/ETO takes ownership: define rebuild cadence, deprecation policy, and notification process.
  - If platform/ETO cannot own it yet: document that digest-pinning to a known-good upstream is the interim approach.

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Direct upstream images (`eclipse-temurin:17-jre`) | No infra to build/maintain | No central patch control; tag drift; no org-level scanning | Does not meet compliance or patching needs |
| Single monolithic base image (OS + JDK + app) | Simpler hierarchy | Ships build tools in production image; fat images; slow CI | Violates security and image-size goals |
| No shared base images (per-team Dockerfiles) | Full team autonomy | CVE patching requires per-repo work; inconsistency | Repeats current pain point at scale |
