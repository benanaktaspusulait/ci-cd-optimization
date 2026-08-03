# T4.2 — Implement Redis Testcontainers smoke/wiring pilot

**Story:** [Story 4 — Testcontainers Pilot](README.md)

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

## Expected implementation changes
T4.2 should produce only the minimum repo-local changes required for the Redis pilot, following the repository's existing Maven structure and the T4.1 implementation-readiness decision.

Expected changes are limited to:

- Testcontainers dependency/version configuration in the appropriate Maven dependency-management location
- test-scoped Testcontainers and Redis client dependencies in `cmd-adaptor-sns-integration-tests`
- one isolated JUnit Jupiter test class, such as `MinimalRedisTest`
- an opt-in Maven profile, test include pattern or documented explicit `-Dtest=MinimalRedisTest` route
- a short execution/result note recording the command, dependency versions, image source, timing method and observed outcome

T4.2 must not change these files or routes unless temporary isolated pilot experimentation is needed:

- RepoSync-managed docker-compose resources (temporary experimentation allowed if clearly documented; durable changes via RepoSync)
- `pre-integration-test/app.py` (temporary experimentation allowed if clearly documented; durable changes via RepoSync)
- `local-int-cmd`
- `local-int-snapshot`
- `ci-cmd`
- `ci-snapshot`
- Drone/CI configuration
- the full Cucumber E2E flow

## Scope
In the relevant integration-test module:

- use the implementation-readiness decisions recorded by T4.1 for the target module, test framework, dependency-management route, Redis image/source, connectivity approach and opt-in execution mechanism; do not reopen those choices in T4.2 unless repository evidence shows that the selected route is not implementable
- add a small, clearly named Redis Testcontainers test
- prefer opt-in local execution through a Maven profile, test include pattern or otherwise clearly isolated test class
- use the Redis image, tag and approved/local image-source route confirmed by T4.1, aligned with the compose baseline where available; do not assume direct public-registry access
- record the exact resolved Testcontainers version, Redis client version, dependency source/repository route and Redis image source used by the successful local run
- keep all new implementation dependencies test-scoped unless repository evidence requires a different route and that exception is documented
- start Redis through Testcontainers and verify that the container becomes ready
- ensure the test fails clearly if the Redis container does not become ready, mapped-port connectivity fails, or the expected Redis interaction does not return the required result
- verify connectivity with a minimal Redis interaction, such as `PING` or `SET`/`GET`, using an existing Redis client dependency if one exists or the minimal test-scoped Redis client route selected from T4.1
- define cleanup and isolation behaviour appropriate to the smoke test
- verify that the test does not depend on Redis state from a previous run and that repeated local executions start from a clean or explicitly reset state
- record how state isolation is achieved, for example unique keys, explicit cleanup, container lifecycle isolation or database reset
- keep the pilot separate from the full docker-compose E2E flow
- RepoSync-controlled files may be changed temporarily in the target repository for isolated pilot experimentation when needed, provided the change is clearly documented and not presented as the durable ownership route. Long-term adoption should still be handled through the relevant RepoSync/MR process.
- do not change `local-int-cmd` or `local-int-snapshot` as the durable route
- do not replace compose Redis in Phase 1
- do not wire the pilot into CI by default
- document the exact local execution command and, if the test is run, the measurement method and observed local smoke-test time
- if execution time is recorded, identify whether the value is Maven elapsed time, test-framework-reported time or container startup time; do not present one as another
- if more than one timing type is available, report them separately; do not derive docker-compose, CI or full-suite savings from the Redis smoke-test measurement

## Environment and CI note

Docker availability is required for local execution. Drone/DIND feasibility is follow-up work unless CI is explicitly attempted. If it is attempted, record the relevant `DOCKER_HOST` and Ryuk constraints, execution method and result. Otherwise record CI suitability as **not measured**.

The pilot must not claim CI savings, flaky-test improvement or full-stack replacement from a successful Redis smoke test.

## Execution boundary

If local implementation or execution is blocked by missing Docker access, dependency resolution or repository prerequisites, record the environment blocker explicitly and leave the implementation acceptance criteria incomplete. A prepared plan alone does not complete T4.2.

## Completion validation

Before marking T4.2 complete:

- review the final Git diff
- confirm only intended repo-local pilot files changed
- confirm production and RepoSync-managed files were not modified
- confirm existing Maven profiles and E2E commands retain their prior semantics
- confirm the exact opt-in command runs only the Redis smoke/wiring pilot
- confirm the full E2E flow is not required for the pilot execution

## Acceptance criteria
- [ ] A Redis Testcontainers smoke/wiring test is implemented in the relevant integration-test module and is runnable locally through the documented opt-in mechanism
- [ ] Expected implementation changes are limited to the Maven dependency/version route, test-scoped pilot dependencies, one isolated Redis smoke test, the opt-in execution route and the execution/result note
- [ ] Exact Testcontainers version, Redis client version, Redis image/tag and dependency/image-source routes used by the local pilot are documented
- [ ] New pilot dependencies are test-scoped or any exception is explicitly justified
- [ ] Redis starts locally through Testcontainers when the pilot is executed
- [ ] The test connects successfully and completes its minimal Redis interaction
- [ ] Container-startup, connectivity and Redis-interaction failures produce a clear failing test result rather than a silent skip or false pass
- [ ] Repeated local executions pass without depending on state left by a previous run, and the isolation/cleanup mechanism is documented
- [ ] The test is opt-in or otherwise isolated from the full E2E flow
- [ ] The existing docker-compose E2E flow is not replaced
- [ ] RepoSync-managed files may only be modified for isolated pilot experimentation; durable changes must go through RepoSync
- [ ] The local execution command and any measured smoke-test time are documented
- [ ] Final Git diff confirms only intended repo-local pilot files changed
- [ ] Existing Maven profiles and full E2E execution semantics remain unchanged
- [ ] The documented opt-in command runs the Redis pilot without requiring the full docker-compose E2E stack
- [ ] CI suitability is assessed or explicitly recorded as follow-up/not measured
- [ ] No CI saving, flaky-test improvement or full-stack replacement is claimed
