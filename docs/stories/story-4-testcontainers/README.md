# Story 4 — Testcontainers Pilot

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** Story 2 · **Parallel with:** Story 3

## Goal
Validate the Redis-first Testcontainers pilot route identified by the T2.4/T2.5 evidence. Phase 1 is a local, opt-in Redis smoke/wiring pilot that tests feasibility, dependency wiring, isolation and the developer workflow without changing the full integration-test flow.

> **Drone constraint:** CI feasibility depends on Story 1 findings (T1.4) and the centrally managed Drone/DIND environment. The Drone pipeline uses DIND with `DOCKER_HOST=tcp://docker:2375`, and Testcontainers may need a Ryuk constraint such as `TESTCONTAINERS_RYUK_DISABLED=true`. CI remains a follow-up unless it is explicitly attempted and measured; production or default CI adoption is not part of Phase 1.

## Why
Redis was selected as the lower-complexity first candidate because it can validate Testcontainers startup, connectivity and local workflow with limited scope. Redis is a support dependency in the current docker-compose/application/pre-integration setup; it is not the central Kafka-driven input and assertion path observed in the integration tests.

Kafka and Schema Registry remain higher-value follow-up candidates because they are central to that path and carry topic, message and offset isolation risks. Their additional value and complexity make them unsuitable for the first wiring pilot.

Phase 1 does not replace docker-compose Redis or the full docker-compose integration stack. It does not claim faster execution, CI savings, fewer flaky tests, improved Kafka isolation or full-stack replacement. Any such outcome requires separate implementation and measurement.

## Acceptance criteria
- [x] Redis is confirmed as the Phase 1 candidate and its scope and non-goals are documented
- [ ] A local, opt-in Redis Testcontainers smoke/wiring test is implemented or prototyped and connects successfully
- [ ] The Redis pilot is compared fairly with the existing docker-compose Redis/support-dependency flow
- [ ] Findings, limits and a continue/stop recommendation are documented without unmeasured claims

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T4.1 | [Confirm Redis pilot candidate and scope](./task-1-select-candidate.md) | 1 | Must | Completed |
| T4.2 | [Implement Redis Testcontainers smoke/wiring pilot](./task-2-implement-setup.md) | 3 | Must | Ready to start |
| T4.3 | [Compare Redis pilot with docker-compose support flow](./task-3-compare-flows.md) | 2 | Should | Not started |
| T4.4 | [Document Redis pilot findings, limits and recommendation](./task-4-document-findings.md) | 1 | Should | Not started |
