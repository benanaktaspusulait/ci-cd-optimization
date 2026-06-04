# T1.5 — Assess BuildKit/cache feasibility in current Drone/DIND setup

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.5 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | S |
| **Priority** | Should |
| **Labels** | `buildkit`, `drone`, `dind`, `cache`, `feasibility` |
| **Sprint** | Week 1 |
| **Depends on** | T1.1, T1.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
ADR-0004 proposes BuildKit multi-stage builds with cache mounts and potentially remote registry cache. But the current pipeline runs `docker build` inside DIND — it's unclear whether BuildKit is enabled, whether `docker buildx` is available, and whether registry cache writes are permitted.

## Goal
Determine what level of BuildKit optimisation is feasible in the current Drone/DIND setup.

## Scope
Investigate:
- Is `DOCKER_BUILDKIT=1` set or settable in the current DIND image?
- Does the DIND image include `docker buildx`?
- Can `--mount=type=cache` work inside the DIND daemon (ephemeral — lost between builds)?
- Can `--cache-from=type=registry` read from the internal registry?
- Can `--cache-to=type=registry` write to the internal registry (permissions, namespace)?
- Would any of these require a `.drone.star` change (RepoSync)?

Likely outcomes:
- **Multi-stage builds:** almost certainly work (standard Docker feature, no special DIND requirement)
- **Local cache mounts:** work per-build but lost between CI runs (still useful for local dev)
- **Remote registry cache:** likely requires platform/ETO (registry namespace + permissions + .drone.star env vars)

## Acceptance criteria
- [ ] BuildKit availability in DIND is confirmed or denied
- [ ] `docker buildx` availability is confirmed or denied
- [ ] Cache mount behaviour in CI is documented (ephemeral vs persistent)
- [ ] Remote cache feasibility is assessed (registry permissions, .drone.star changes needed)
- [ ] Finding informs Story 3 scope (what can be done locally vs what needs platform)
