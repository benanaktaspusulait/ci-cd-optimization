# Technical Details — Testcontainers and Integration-Test Lifecycle

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Validated in CI across SNS, PNR and PCDP |
| **Last updated** | 2026-09-17 |

## Final Decision

Testcontainers is no longer a one-dependency local prototype. The validated CI pattern moves suitable integration infrastructure into test ownership and executes it from the Maven/Failsafe/Cucumber lifecycle against the Drone DIND Docker daemon.

The application can run in the test JVM for business/integration scenarios, followed by a separate exact-Docker-image runtime validation step.

## CI Docker Model

Validated CI configuration uses the existing Drone Kubernetes + DIND model. The common configuration includes the DIND endpoint and the Testcontainers host/network settings required by the runner.

Representative settings used by the validated implementations include:

```text
DOCKER_HOST=tcp://docker:2375
DOCKER_API_VERSION=1.41
TESTCONTAINERS_HOST_OVERRIDE=docker
TESTCONTAINERS_RYUK_DISABLED=true
```

Ryuk is disabled for this DIND CI setup. Explicit Java lifecycle ownership, root-scope cleanup and bounded shutdown remain responsible for releasing resources during the test run; the ephemeral CI pod remains an additional isolation boundary.

Mandatory CI suites must fail if Docker is unavailable. Local developer profiles may choose a softer behaviour, but that must not leak into the mandatory CI path.

## Infrastructure Lifecycle

The exact dependency set is repository-specific, but the validated architecture uses:

- Redis;
- ZooKeeper/Kafka;
- Schema Registry;
- the aggregate services required by the selected business suite;
- dynamic topic suffixes / isolated topic naming;
- bounded readiness checks;
- failure diagnostics;
- deterministic shutdown.

Independent containers/readiness checks should start concurrently only where the dependency graph allows it.

## Repository Adaptations

### SNS

- Redis, ZooKeeper, Kafka, Schema Registry.
- Five aggregates: party, object, location, event and service.
- Application starts in the test JVM.
- Current business coverage guard: at least **7 feature files / 14 business scenarios**.
- Dynamic topic suffixes and topic catalogue are created by the test environment.

### PNR

- Same high-level lifecycle, adapted for PNR topics and test inventory.
- Inventory logic distinguishes declared scenarios from scenario-outline expansion.
- Current protected inventory includes **15 feature files**, **15 declared non-ignored scenarios** and **21 expanded executable cases**.
- Aggregate startup is selected from the effective test scope rather than assumed globally.
- Exact built-image runtime validation is separate from the in-JVM test suite.

### PCDP

- Larger infrastructure/test surface with repository-specific aggregate requirements.
- Feature inventory guard protects **131 feature files / 480 declared / 1382 executable cases**.
- Snapshot inventory protects **125 declared / 229 executable cases**.
- Non-snapshot inventory is **355 declared / 1153 executable cases**.
- The mandatory CI Testcontainers profile must not silently skip because Docker is unavailable.

## False-Green Protection

The speed-up is not valid if the new pipeline runs less validation. The reusable guardrail set therefore includes:

- Maven Failsafe `failIfNoTests` / specified-test protections;
- Docker-required hard failure for mandatory CI;
- feature/scenario inventory guards;
- completed-scenario counters;
- exact test runner/profile selection;
- readiness timeouts;
- failure diagnostics and container logs;
- no hidden `disabledWithoutDocker=true`-style bypass for the mandatory suite.

## Exact Built-Image Runtime Validation

In-JVM integration tests validate application behaviour but not the packaged Docker image. The final pipeline therefore:

1. completes the Maven/Testcontainers business suite;
2. builds the command-adaptor image;
3. starts the **exact image built by that pipeline**;
4. verifies runtime readiness against compatible test infrastructure;
5. runs the final vulnerability scan against the built image.

This boundary is intentionally retained even when the application also ran successfully inside the test JVM.

## Polling, Consumers and Shutdown

Reusable optimisation rules include:

- correct time units for poll durations;
- bounded polling rather than unbounded waits;
- larger poll batches where behaviour permits;
- stop polling once the expected count is reached;
- bounded producer/consumer close;
- deterministic Kafka Streams shutdown;
- shared/reused HTTP clients where safe;
- no readiness-result caching that changes validation semantics.

## What Not to Standardise Blindly

Do not centralise repository-specific:

- topic names/catalogues;
- scenario counts;
- aggregate lists;
- Spring/application properties;
- health paths/ports;
- stream topology assumptions.

RepoSync should centralise the pipeline/lifecycle pattern, not business-test knowledge.

