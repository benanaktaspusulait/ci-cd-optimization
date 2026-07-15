# T4.1 — Confirm Redis pilot candidate and scope

**Story:** [Story 4 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.1 |
| **Type** | Scope confirmation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 1 |
| **Priority** | Must |
| **Labels** | `testcontainers`, `redis`, `integration-test`, `scope` |
| **Sprint** | Week 2 |
| **Depends on** | T2.4, T2.5 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
T2.4/T2.5 already identified Redis as the lower-complexity first Testcontainers candidate. This task confirms that decision and fixes the implementation boundary before T4.2; it does not restart open-ended candidate selection.

T4.1 should reuse the published T2.4/T2.5 decision and perform only the repository-level prerequisite checks needed to make T4.2 implementation-ready. It should not repeat the candidate analysis. Its purpose is to formalise the existing Redis-first decision, verify implementation prerequisites against the actual repository and record the selected opt-in execution route for T4.2—not to reconsider Redis, Kafka, Schema Registry and LocalStack from scratch.

## Goal
Confirm Redis as the first low-complexity Testcontainers pilot candidate, using the T2.4/T2.5 evidence, and define a safe Phase 1 scope for implementation.

## Expected output

T4.1 should produce a short implementation-readiness record containing:

- confirmed target integration-test module and test framework
- selected Testcontainers dependency/version-management route
- selected Redis image, tag and image-source route
- selected Redis connectivity approach
- selected opt-in Maven profile, include pattern or isolated test-class approach
- confirmed non-goals and RepoSync constraints
- a T4.2 ready / blocked decision with any unresolved prerequisites listed

## Scope
Confirm and document that:

- Redis is selected first because its lower setup complexity provides a small test of Testcontainers startup, connectivity, wiring, isolation and local workflow.
- Redis is an infrastructure/support dependency in the current flow, not the central Kafka-driven integration-test input and assertion path.
- Kafka and Schema Registry remain higher-value follow-up candidates because they are central to that path and carry higher setup complexity plus topic, message and offset isolation risk.
- Phase 1 is a local, opt-in Redis smoke/wiring pilot.
- Phase 1 does not replace docker-compose Redis or the full docker-compose stack, change the full E2E flow, change `local-int-cmd` or `local-int-snapshot`, modify RepoSync-managed pre-integration files, or run in CI by default.

Before T4.2, check and record these implementation prerequisites:

- the relevant integration-test module POM and test framework
- the Testcontainers dependency and version-management approach
- an existing Redis client dependency or a simple alternative connectivity approach
- an opt-in Maven profile, test include pattern or clearly isolated test-class approach
- the exact Redis image/tag and image-source route to use, aligned with the compose baseline where available; do not assume direct public-registry access without local or approved registry validation
- confirmation that no local-only edit to `pre-integration-test/app.py` is required or permitted

## Acceptance criteria
- [ ] Redis is confirmed as the first candidate and the rationale is documented
- [ ] The Kafka and Schema Registry follow-up rationale is documented
- [ ] Phase 1 scope and non-goals are documented
- [ ] Repository-level implementation prerequisites are checked and recorded, including the confirmed target module, test framework, dependency-management route, Redis image/source, Redis connectivity approach and intended opt-in execution mechanism
- [ ] A T4.2 implementation-ready or blocked decision is recorded; any unresolved prerequisite is identified explicitly as a blocker or follow-up
- [ ] No CI saving or flaky-test improvement is claimed
