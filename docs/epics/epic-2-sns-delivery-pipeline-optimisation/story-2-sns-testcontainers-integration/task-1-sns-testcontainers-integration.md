# E2-S2.1 — Implement and Validate the SNS Testcontainers Integration Path

**Story:** [E2-S2 — Productise the SNS Testcontainers Integration Path](./README.md)

| Field | Value |
|---|---|
| **ID** | E2-S2.1 |
| **Type** | Testcontainers implementation and CI validation |
| **Estimate** | 5 |
| **Priority** | Must |
| **Depends on** | Validated Redis Testcontainers pilot, repository access and CI Docker availability |
| **Status** | Proposed / New |
| **Primary output** | A maintained opt-in Testcontainers path covering Redis, Kafka, Schema Registry and one real SNS application flow, validated locally and in branch CI |

## Why

The pilot established that Redis can be started and validated through a narrow Testcontainers
workflow using dynamic endpoints, isolated data and explicit cleanup.

The next step should not be split into several analysis or reporting tasks. Kafka, Schema
Registry, one representative SNS application flow and opt-in CI validation form one coherent
technical outcome and should be implemented and validated together.

## Goal

Extend the validated Redis Testcontainers foundation into a maintained, repeatable SNS
integration path that:

- starts Redis, Kafka and Schema Registry through Testcontainers
- runs one representative `cmd-adaptor-sns` application scenario
- validates the expected application output or state
- runs locally and through an explicit opt-in branch-CI path
- preserves the existing Compose/full-E2E workflow

## Scope

### 1. Retain the Redis foundation

- Retain or appropriately refactor the existing `MinimalRedisTest`.
- Preserve dynamic Redis endpoint resolution.
- Preserve `PING` and `SET`/`GET` assertions.
- Preserve isolated UUID-based data.
- Preserve explicit client and test-data cleanup.
- Do not duplicate the existing Redis lifecycle unnecessarily.

### 2. Add Kafka Testcontainers support

- Add a Kafka container using a repository-compatible image and version.
- Do not use fixed host ports.
- Resolve the Kafka bootstrap endpoint dynamically.
- Add explicit readiness handling.
- Create isolated topic names for each run.
- Demonstrate a basic produce-and-consume path.
- Make container logs available when startup or assertions fail.

### 3. Add Schema Registry container support

- Add a Schema Registry container compatible with the selected Kafka setup.
- Run Kafka and Schema Registry on the same isolated Testcontainers network.
- Configure Schema Registry through Kafka's network alias rather than a host port assumption.
- Resolve the externally accessible Schema Registry URL dynamically.
- Register and retrieve a representative schema.
- Confirm that the configuration is usable by the selected SNS application scenario.

### 4. Implement one real SNS application flow

Select one representative existing integration scenario that exercises real `cmd-adaptor-sns`
behaviour.

The scenario must:

- start Redis, Kafka and Schema Registry through Testcontainers
- configure the application with dynamically resolved endpoints
- publish a representative input message
- allow `cmd-adaptor-sns` to process the message
- assert one meaningful application outcome, such as:
  - output Kafka message
  - Redis state
  - persisted command state
  - another existing observable side effect
- use isolated topic names and Redis data
- avoid dependency on state from previous runs
- provide useful diagnostics on failure

The test must validate application behaviour, not only container connectivity.

### 5. Provide a maintained opt-in invocation

- Keep the Testcontainers path explicitly opt-in.
- Use the existing JUnit tag, Maven profile or repository-standard equivalent.
- Document the exact local command.
- Avoid creating multiple overlapping invocation mechanisms.
- Do not change the default developer or CI path unless separately approved.

### 6. Validate in branch CI

Add or use an explicitly approved opt-in branch-CI execution path.

Validate:

- Docker access
- container startup
- dynamic networking
- Redis connectivity
- Kafka produce/consume
- Schema Registry registration/retrieval
- application startup
- the selected real SNS scenario
- cleanup
- failure log capture

Record the exact CI run reference and result.

If CI enablement cannot be approved within the task window, preserve the implementation as
local opt-in and explicitly record the approval dependency. Do not silently add it to the
default pipeline.

### 7. Repeated validation

Run the complete path at least twice locally.

Where the CI path is approved, run it at least once in a real branch pipeline.

Record:

- exact command
- environment
- image versions
- execution result
- failures
- retries
- cleanup behaviour
- relevant diagnostics

## Acceptance criteria

- [ ] The existing Redis Testcontainers workflow is retained or cleanly refactored.
- [ ] Redis uses dynamic endpoint resolution and isolated test data.
- [ ] Kafka starts through Testcontainers without fixed host ports.
- [ ] Kafka readiness is explicitly validated.
- [ ] A unique topic is created and a produce/consume assertion succeeds.
- [ ] Schema Registry starts on the same isolated network as Kafka.
- [ ] Schema Registry connects to Kafka through a network alias.
- [ ] A representative schema is registered and retrieved successfully.
- [ ] One real `cmd-adaptor-sns` application scenario runs using the Testcontainers dependencies.
- [ ] The application test validates a meaningful output, state or side effect.
- [ ] Topic names and Redis data are isolated between runs.
- [ ] Failure diagnostics include relevant application and container logs.
- [ ] The complete path succeeds in at least two consecutive local runs.
- [ ] Exact local invocation and prerequisites are documented.
- [ ] An opt-in branch-CI path is validated where approval is available.
- [ ] The existing Compose/full-E2E workflow remains available and unchanged.
- [ ] No existing integration service is removed as part of this task.
- [ ] No full-suite migration or Compose replacement claim is made.
- [ ] No speed or reliability improvement is claimed without equivalent measurements.

## Boundaries / non-goals

- No migration of the complete integration-test suite.
- No removal of the existing Compose environment.
- No claim that all current Compose services are unnecessary.
- No default-CI enablement without explicit approval.
- No unrelated Docker image-build work.
- No CD pipeline, Helm, `kd`, PVC or deployment review.
- No production runtime or deployment change.
- No broad architecture report beyond implementation evidence required to maintain and
  validate this Testcontainers path.

## Expected application flow

```text
Redis Testcontainer
Kafka Testcontainer
Schema Registry container
        |
        v
cmd-adaptor-sns
        |
        v
Representative input message
        |
        v
Application processing
        |
        v
Expected output / Redis state / observable side effect
```

## Required final result

The result must state:

- files changed
- selected container images and versions
- Redis, Kafka and Schema Registry lifecycle
- dynamic endpoint and network configuration
- selected SNS application scenario
- exact assertions
- exact local invocation
- repeated local-run evidence
- branch-CI evidence or explicit approval blocker
- failures and retries
- cleanup and diagnostic behaviour
- confirmation that the current Compose/full-E2E path remains unchanged
- final disposition: `adopt as opt-in` / `revise` / `retain local pending CI approval` / `stop`
