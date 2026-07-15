# T4.2 — Implement Redis Testcontainers smoke/wiring pilot

**Story:** [Story 4 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.2 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 3 |
| **Priority** | Must |
| **Labels** | `testcontainers`, `redis`, `integration-test`, `implementation` |
| **Sprint** | Week 2 |
| **Depends on** | T4.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A small, runnable Redis test provides evidence about Testcontainers feasibility, wiring and local developer workflow before any higher-risk work on the Kafka-driven assertion path.

## Goal
Implement a Redis Testcontainers smoke/wiring pilot that can be run locally and explicitly, independently of the full docker-compose E2E flow.

## Scope
In the relevant integration-test module:

- use the implementation-readiness decisions recorded by T4.1 for the target module, test framework, dependency-management route, Redis image/source, connectivity approach and opt-in execution mechanism; do not reopen those choices in T4.2 unless repository evidence shows that the selected route is not implementable
- add a small, clearly named Redis Testcontainers test
- prefer opt-in local execution through a Maven profile, test include pattern or otherwise clearly isolated test class
- use the Redis image, tag and approved/local image-source route confirmed by T4.1, aligned with the compose baseline where available; do not assume direct public-registry access
- start Redis through Testcontainers and verify that the container becomes ready
- verify connectivity with a minimal Redis interaction, such as `PING` or `SET`/`GET`, according to existing client dependencies
- define cleanup and isolation behaviour appropriate to the smoke test
- verify that the test does not depend on Redis state from a previous run and that repeated local executions start from a clean or explicitly reset state
- keep the pilot separate from the full docker-compose E2E flow
- do not modify the RepoSync-managed `pre-integration-test/app.py`
- do not change `local-int-cmd` or `local-int-snapshot`
- do not replace compose Redis in Phase 1
- do not wire the pilot into CI by default
- document the exact local execution command and, if the test is run, the measurement method and observed local smoke-test time
- if execution time is recorded, identify whether the value is Maven elapsed time, test-framework-reported time or container startup time; do not present one as another

## Environment and CI note

Docker availability is required for local execution. Drone/DIND feasibility is follow-up work unless CI is explicitly attempted. If it is attempted, record the relevant `DOCKER_HOST` and Ryuk constraints, execution method and result. Otherwise record CI suitability as **not measured**.

The pilot must not claim CI savings, flaky-test improvement or full-stack replacement from a successful Redis smoke test.

## Execution boundary

If local implementation or execution is blocked by missing Docker access, dependency resolution or repository prerequisites, record the environment blocker explicitly and leave the implementation acceptance criteria incomplete. A prepared plan alone does not complete T4.2.

## Acceptance criteria
- [ ] A Redis Testcontainers smoke/wiring test is implemented in the relevant integration-test module and is runnable locally through the documented opt-in mechanism
- [ ] Redis starts locally through Testcontainers when the pilot is executed
- [ ] The test connects successfully and completes its minimal Redis interaction
- [ ] Repeated local executions do not depend on state left by a previous run
- [ ] The test is opt-in or otherwise isolated from the full E2E flow
- [ ] The existing docker-compose E2E flow is not replaced
- [ ] RepoSync-managed files, `local-int-cmd` and `local-int-snapshot` are not modified
- [ ] The local execution command and any measured smoke-test time are documented
- [ ] CI suitability is assessed or explicitly recorded as follow-up/not measured
- [ ] No CI saving, flaky-test improvement or full-stack replacement is claimed
