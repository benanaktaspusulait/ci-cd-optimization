# ADR-0003: Reduce Docker Compose role in CI, keep it for local

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [ADR-0002 — Testcontainers](0002-testcontainers-for-integration-tests.md) · [ADR-0004 — BuildKit](0004-buildkit-cache-and-layering.md) · [ADR-0005 — CI runner mode](0005-ci-runner-docker-mode.md) · [Story 5](../stories/story-5-compose/README.md)

## Context

The pilot repository uses a single `docker-compose.yml` that serves multiple purposes:

1. **CI integration tests:** the Drone pipeline runs `docker compose up` (via `.drone.star` steps) before integration tests, starting all services regardless of what the test actually needs.
2. **Local debugging:** developers use the same file to spin up services while developing and debugging locally.
3. **Exploratory testing:** occasionally used to stand up the full stack for manual testing.

This mixed usage causes problems:
- **Unnecessary services in CI:** the Compose file may start services (e.g. a debug UI, monitoring tools) that integration tests don't touch — wasting CI minutes and adding failure surface.
- **Mixed-purpose file:** changes to support local debugging (e.g. adding a pgAdmin container) affect CI without anyone intending it.
- **Opacity:** it's unclear which services are truly required for the test suite and which are convenience tools.

[ADR-0002](0002-testcontainers-for-integration-tests.md) introduces Testcontainers as a way for tests to manage their own dependencies. Once a dependency is managed by Testcontainers, the corresponding Compose service is no longer needed in CI — but may still be useful locally.

## Decision

We will reduce Docker Compose's role in **CI** while **keeping it for local debugging**. Specifically:

1. **Map** all services currently in `docker-compose.yml` (T5.1).
2. **Classify** each service: required for CI tests / local-debug only / optional / removable (T5.2).
3. **Recommend** which services to remove from the CI flow, which to keep, and which to move to Testcontainers (T5.3).

We will **not remove Compose entirely**. Docker Compose remains valuable for:
- Spinning up the full stack for local manual testing.
- Debugging scenarios that require multiple services interacting.
- Onboarding new developers who need a quick local environment.

The target model:
```
CI integration tests   → Testcontainers (isolated, deterministic)
Local manual debugging → Docker Compose (convenient, full-stack)
E2E / exploratory      → Compose or ephemeral environments (future)
```

## Consequences

- **Positive:**
  - Leaner, faster CI runs — only services the tests actually need are started.
  - Clearer separation of CI vs local concerns — changes to the local Compose file don't accidentally break CI.
  - Developers keep a familiar tool for local debugging — no workflow disruption.
  - Forces the team to document which services are actually test dependencies vs convenience tools.

- **Negative / trade-offs:**
  - Risk of breaking a hidden local workflow (risk R4) — a service assumed "not needed" turns out to be required.
  - Requires accurate service mapping first (T5.1–T5.2) — cannot skip straight to removal.
  - Two ways to start dependencies (Testcontainers in code, Compose on CLI) adds mental overhead until the team internalises the split.

- **Follow-ups:**
  - T5.1–T5.2: map and classify before changing anything.
  - Change **CI usage only** in the pilot — do not change local Compose usage.
  - Document any hidden dependency discovered during mapping.
  - If a service is borderline, keep it in CI during the pilot and flag for review.

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Remove Docker Compose entirely | Simplest mental model; one way to do things | Breaks local debugging; destroys familiar workflow; high developer disruption | Too aggressive; not the goal |
| Keep Compose for everything (status quo) | No change; no disruption | Slow CI; shared state; mixed-purpose file; no isolation | This is the problem we're addressing |
| Split into two compose files (CI vs local) | Clear separation without Testcontainers | More files to maintain; still shared-state in CI; doesn't improve test isolation | Possible follow-up, but heavier than what the pilot needs |
| Use Testcontainers for everything, drop Compose | Full isolation in CI and locally | Large refactor; loss of the "full stack" local convenience | Over-rotation — Compose has legitimate local value |
