# ADR-0004: Reduce Docker Compose role in CI, keep it for local

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [Story 4](../stories/story-4-compose/README.md), [ADR-0003](0003-testcontainers-for-integration-tests.md)

## Context
The same Docker Compose file often serves both CI integration tests and local debugging, pulling extra services into every CI run. Some services may not be needed for CI at all.

## Decision
We will reduce Docker Compose's role in **CI** (preferring Testcontainers where suitable) while **keeping Compose for local debugging**. We will not remove Compose. The reduced set is recommended in Story 4, informed by the Testcontainers findings.

## Consequences
- **Positive:** leaner, faster CI runs; clearer separation of CI vs local concerns; developers keep familiar local tooling.
- **Negative / trade-offs:** risk of breaking a hidden local workflow (R4); requires accurate service mapping first.
- **Follow-ups:** map and classify services (T4.1–T4.2) before changing anything; change CI usage only; document any dependency discovered.

## Alternatives considered
| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Remove Compose entirely | Simplest mental model | Breaks local debugging; high risk | Out of scope; not the goal |
| Keep Compose everywhere | No change | Slow CI; mixed-purpose file | Doesn't address the friction |
| Split into separate compose files (CI vs local) | Clear separation | More files to maintain | Possible follow-up; heavier than pilot needs |
