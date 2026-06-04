# ADR-0002: Use Testcontainers for selected integration tests

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [ADR-0001 — Pilot approach](0001-pilot-not-rollout.md) · [ADR-0003 — Compose role](0003-reduce-compose-in-ci.md) · [ADR-0005 — CI runner mode](0005-ci-runner-docker-mode.md) · [Story 4](../stories/story-4-testcontainers/README.md)

## Context

Integration tests in the pilot repository currently rely on a full Docker Compose stack (`docker-compose.yml`) that starts all dependent services (Redis, Kafka, Schema Registry, etc.) before any test runs. This creates several problems:

1. **Slow startup:** the full stack takes ~90 seconds before the first test can execute, regardless of whether the test needs all services.
2. **Shared state:** services persist across test runs, so one test can pollute another. This causes intermittent, hard-to-reproduce failures.
3. **Environment drift:** the Compose setup behaves differently on developer machines vs CI runners (port conflicts, resource limits, network differences), leading to "works on my machine" problems.
4. **All-or-nothing:** you start the entire stack even if your test only needs Redis. This wastes CI minutes and complicates debugging.

Testcontainers (a Java library) offers a different model: each test (or test class) programmatically starts only the containers it needs, with isolated networks and randomised ports. The container lifecycle is managed from the test code — start before test, tear down after.

The stronger value proposition for Testcontainers is **determinism and isolation**, not just speed. A test that manages its own dependencies is reproducible by definition.

## Decision

We will pilot Testcontainers for **one selected integration dependency** (e.g. Redis or Kafka), managing its lifecycle from the test code, and compare it against the existing Compose flow before any wider adoption.

Specifics:
- The candidate dependency is selected in T4.1 based on simplicity and validation value.
- The Testcontainers setup is implemented in T4.2 (container definition, property wiring, wait strategy, cleanup).
- Container **reuse** is allowed locally (`testcontainers.reuse.enable=true`) for faster feedback loops.
- Container **reuse is disabled in CI** — every run gets a clean, isolated environment with no hidden shared state.
- The comparison (T4.3) covers: startup time, test runtime, complexity, local developer experience, CI suitability, and determinism.
- A continue/stop recommendation is documented in T4.4.

## Consequences

- **Positive:**
  - Isolated, deterministic, per-test environments — no shared state between tests.
  - Better local/CI consistency — same container version, same config, same behaviour everywhere.
  - Reduced reliance on Compose in CI (feeds into [ADR-0003](0003-reduce-compose-in-ci.md)).
  - Simpler debugging — test logs include container startup; failure is localised.
  - Selective startup — tests only start what they need, so CI minutes are spent on relevant dependencies.

- **Negative / trade-offs:**
  - Requires a working Docker runtime in CI — this is not trivial (see [ADR-0005](0005-ci-runner-docker-mode.md) for runner mode options).
  - Docker-in-Docker may be slow or restricted on shared runners (risk R3).
  - Adds a library dependency (`org.testcontainers`) to the project's test classpath.
  - First-run cold pull of container images can be slow (mitigated by image caching in CI if available).

- **Follow-ups:**
  - T4.2: assess CI suitability early — if the runner cannot provide Docker, document it and treat Testcontainers as local-only.
  - If CI is unsuitable: Compose remains in CI for integration tests ([ADR-0003](0003-reduce-compose-in-ci.md) fallback).
  - If successful: expand to more dependencies in post-pilot phase.
  - Route runner-mode decision to ACP/ETO via [ADR-0005](0005-ci-runner-docker-mode.md).

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Keep full Docker Compose for all tests | No change; familiar | Slow startup; shared state; flaky; all-or-nothing | This is the problem we're solving |
| Shared long-lived test environment (always-on containers) | Fast per-test (no startup wait) | Hidden shared state; requires coordination; not isolated | Reintroduces the determinism problem |
| Mock all external dependencies | Very fast; no Docker needed | Lower fidelity; misses real integration bugs (serialisation, timeouts, version drift) | Defeats the purpose of integration testing |
| Testcontainers for all dependencies at once | Full isolation immediately | Large refactor; higher pilot risk; hard to attribute improvements | Too much for a first pilot — start with one and expand |
