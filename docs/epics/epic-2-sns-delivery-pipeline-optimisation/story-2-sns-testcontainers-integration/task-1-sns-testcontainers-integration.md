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
| **Primary output** | A maintained opt-in Testcontainers path covering Redis, Kafka, Schema Registry and one real SNS application flow, validated locally and, where approved, in branch CI |

## Why

The pilot established that Redis can be started and validated through a narrow Testcontainers
workflow using dynamic endpoints, isolated data and explicit cleanup. Kafka, Schema Registry
and one real SNS application flow form one coherent technical outcome and should be
implemented and validated together.

## Goal

Extend the validated Redis Testcontainers foundation into a maintained, repeatable SNS
integration path that starts Redis, Kafka and Schema Registry through Testcontainers,
runs one representative `cmd-adaptor-sns` application scenario and preserves the existing
Compose/full-E2E workflow.

## Scope

### 1. Retain the Redis foundation

- Retain or appropriately refactor the existing `MinimalRedisTest`.
- Preserve dynamic endpoint resolution, `PING`/`SET`/`GET` assertions, UUID-based
  isolated data and explicit cleanup.
- Do not duplicate the existing Redis lifecycle unnecessarily.

### 2. Add Kafka Testcontainers support

- Add a Kafka container using a repository-compatible image and version.
- Resolve the bootstrap endpoint dynamically — no fixed host ports.
- Add explicit readiness handling.
- Create isolated topic names per run.
- Demonstrate a basic produce-and-consume path.
- Make container logs available on startup or assertion failure.

### 3. Add Schema Registry container support

- Add a Schema Registry container compatible with the selected Kafka setup.
- Run Kafka and Schema Registry on the same isolated Testcontainers network.
- Configure Schema Registry through Kafka's network alias, not a host port assumption.
- Resolve the externally accessible Schema Registry URL dynamically.
- Register and retrieve a representative schema.

### 4. Implement one real SNS application flow

Select one representative existing integration scenario that exercises real
`cmd-adaptor-sns` behaviour.

The scenario must:

- configure the application with dynamically resolved endpoints
- publish a representative input message
- allow `cmd-adaptor-sns` to process the message
- assert one meaningful output, state or observable side effect
- use isolated topic names and Redis data
- provide useful diagnostics on failure

The test must validate application behaviour, not only container connectivity.

### 5. Opt-in invocation

- Keep the Testcontainers path explicitly opt-in via the existing Maven profile or
  repository-standard equivalent.
- Document the exact local command.
- Do not change the default developer or CI path unless separately approved.

### 6. Branch CI validation

Where CI Docker access is approved, run the complete path in a real branch pipeline
and record the run reference and result. If approval is not available within the task
window, record the explicit blocker and preserve the implementation as local opt-in.

### 7. Validation

- Run the complete path twice locally.
- Record the exact command, pass/fail result and duration for each run.
- Where approved, record one opt-in branch-CI run reference.
- Record any failure, retry or CI approval blocker.

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
- [ ] No full-suite migration or Compose replacement claim is made.
- [ ] No speed or reliability improvement is claimed without equivalent measurements.

## Required final result

The result must state:

- files changed
- selected real SNS scenario and meaningful assertion
- exact local invocation and two run results
- branch-CI run reference or approval blocker
- confirmation that Compose/full-E2E remains unchanged
- final disposition: `adopt as opt-in` / `revise` / `retain local pending CI approval` / `stop`

## Boundaries / non-goals

- No migration of the complete integration-test suite.
- No removal of the existing Compose environment.
- No default-CI enablement without explicit approval.
- No unrelated Docker image-build work.
- No CD pipeline, Helm, `kd`, PVC or deployment review.
- No production runtime or deployment change.
- No broad architecture report beyond implementation evidence required to
  maintain and validate this Testcontainers path.
