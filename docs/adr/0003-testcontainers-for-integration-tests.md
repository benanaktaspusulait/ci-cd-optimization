# ADR-0003: Use Testcontainers for selected integration tests

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [Story 3](../stories/story-3-testcontainers/README.md)

## Context
Integration tests rely on a full Docker Compose stack that is slow to start and can share hidden state between runs, contributing to flaky, environment-dependent failures. We want more isolated, deterministic tests.

## Decision
We will pilot **Testcontainers** for one selected integration dependency, managing its lifecycle from the test code, and compare it against the existing Compose flow before any wider adoption. Container **reuse** is allowed locally for speed but **disabled in CI** for clean, deterministic runs.

## Consequences
- **Positive:** isolated, deterministic, per-test environments; better local/CI consistency; reduced reliance on Compose in CI.
- **Negative / trade-offs:** requires a working Docker runtime in CI; Docker-in-Docker may be slow or restricted (risk R3); a code dependency on Testcontainers.
- **Follow-ups:** assess CI suitability early (T3.2); if CI is unsuitable, keep Compose in CI and use Testcontainers locally (see ADR-0004).

## Alternatives considered
| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Keep full Docker Compose | No change | Slow, shared state, flaky | The problem we're trying to fix |
| Shared long-lived test env | Fast per-test | Hidden state; coordination; not isolated | Reintroduces determinism problems |
| Mock the dependency | Fast, no Docker | Lower fidelity; misses integration bugs | Defeats the purpose of integration tests |
