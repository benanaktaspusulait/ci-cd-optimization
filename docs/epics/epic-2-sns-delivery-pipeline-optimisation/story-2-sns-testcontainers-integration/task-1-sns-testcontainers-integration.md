# E2-S2.1 — Implement and Validate the SNS Testcontainers Integration Path

**Story:** [E2-S2 — Productise the SNS Testcontainers Integration Path](./README.md)

| Field | Value |
|---|---|
| **ID** | E2-S2.1 |
| **Type** | Testcontainers implementation and optional CI validation |
| **Estimate** | 5 |
| **Priority** | Must |
| **Depends on** | Validated Redis Testcontainers pilot and repository access; CI Docker availability only for optional branch-CI validation |
| **Status** | Proposed / New |
| **Primary output** | A maintained opt-in Testcontainers path covering Redis, Kafka and Schema Registry with all technically suitable SNS integration tests migrated, validated locally and, where approved, in branch CI |

## Why

The pilot established that Redis can be started and validated through Testcontainers
using dynamic endpoints, isolated data and explicit cleanup. The next step is to build
the shared Testcontainers environment and migrate all technically suitable existing
SNS integration tests to it — not just one scenario.

## Goal

Create a shared Testcontainers environment for Redis, Kafka and Schema Registry, migrate
all technically suitable existing `cmd-adaptor-sns` integration tests to it, and validate
the complete migrated suite locally and in opt-in branch CI.

## Scope

### 1. Retain the Redis foundation

- Retain or appropriately refactor the existing `MinimalRedisTest`.
- Preserve dynamic endpoint resolution, `PING`/`SET`/`GET` assertions, UUID-based
  isolated data and explicit cleanup.

### 2. Shared Testcontainers environment

- Create one shared Testcontainers fixture for Redis, Kafka and Schema Registry.
- Run all three on the same isolated Testcontainers network.
- Resolve all endpoints dynamically — no fixed host ports.
- Configure Schema Registry through Kafka's network alias.
- Add explicit readiness handling for each container.
- Make container logs available on startup or assertion failure.

### 3. Integration test inventory and migration

- Inventory all existing `cmd-adaptor-sns` integration tests.
- Migrate all technically suitable tests to the shared Testcontainers path.
- Preserve every existing meaningful assertion and application behaviour.
- Do not leave tests on Compose merely to reduce implementation scope.
- Record only tests that genuinely cannot be migrated, with the exact external
  dependency or technical blocker.

### 4. Isolated test data

- Use isolated topic names and Redis keys per run.
- Avoid dependency on state from previous runs throughout the migrated suite.

### 5. Opt-in invocation

- Keep the Testcontainers path explicitly opt-in via the existing Maven profile or
  repository-standard equivalent.
- Document the exact local command for the complete migrated suite.
- Do not change the default developer or CI path unless separately approved.

### 6. Compose preservation

- Keep the existing Compose path available until migration parity is demonstrated.
- Do not remove Compose in this task unless every dependent test has migrated
  and explicit approval is provided.

### 7. Branch CI validation

Where CI Docker access is approved, run the complete migrated suite in a real branch
pipeline and record the run reference and result. If approval is not available within
the task window, record the explicit blocker and preserve the implementation as local
opt-in.

### 8. Validation

- Run the complete migrated suite twice locally.
- Record the exact command, pass/fail result and duration for each run.
- Where approved, record one opt-in branch-CI run reference.
- Record any failure, retry or CI approval blocker.

## Acceptance criteria

- [ ] Redis, Kafka and Schema Registry use one maintained Testcontainers fixture.
- [ ] All existing SNS integration tests are inventoried.
- [ ] All technically suitable SNS integration tests are migrated.
- [ ] Existing meaningful assertions and behaviours are preserved.
- [ ] Any non-migrated test has a concrete documented technical blocker.
- [ ] Dynamic endpoints and isolated topics/data are used throughout.
- [ ] The complete migrated suite passes twice consecutively locally.
- [ ] The complete migrated suite passes in opt-in branch CI where approved.
- [ ] Existing Compose tests remain available until parity is demonstrated.
- [ ] No test is excluded merely to keep the task small.

## Required final result

The result must state:

- files changed
- inventory of existing tests and migration disposition for each
- exact local invocation and two run results
- branch-CI run reference or approval blocker
- confirmation that Compose path remains available until parity is demonstrated
- final disposition: `adopt as opt-in` / `revise` / `retain local pending CI approval` / `stop`

## Boundaries / non-goals

- No removal of the existing Compose environment before migration parity is demonstrated.
- No default-CI enablement without explicit approval.
- No unrelated Docker image-build work.
- No CD pipeline, Helm, `kd`, PVC or deployment review.
- No production runtime or deployment change.
- No broad architecture report beyond implementation evidence required to
  maintain and validate this Testcontainers path.
