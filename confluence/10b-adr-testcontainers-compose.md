# ADR-0002 & ADR-0003: Testcontainers and Compose Decisions

| Field | Value |
|---|---|
| **Status** | Accepted for validated CI pattern |
| **Last updated** | 2026-09-18 |

## ADR-0002: Use Testcontainers for the CI Integration-Test Lifecycle

### Context

The original design used Docker Compose orchestration across multiple CI steps for Kafka/Redis/Schema Registry, aggregate services, the command adaptor and integration-test helpers. Lifecycle ownership was distributed and the pipeline paid for repeated orchestration transitions.

The original ADR proposed a one-dependency local prototype. Implementation evidence has now moved beyond that assumption.

### Decision

Use Testcontainers as the owner of suitable integration infrastructure in the CI Maven/Failsafe/Cucumber lifecycle.

The validated pattern includes:

- Redis, Kafka/ZooKeeper and Schema Registry;
- repository-required aggregate containers;
- dynamic topics/endpoints;
- application startup for in-JVM integration scenarios;
- readiness, diagnostics and deterministic cleanup;
- mandatory Docker/test/scenario completeness guards.

The exact built Docker image is still validated separately after the in-JVM suite.

### Evidence

- SNS: 7 feature files / 14 business scenarios protected.
- PNR: 15 feature files / 15 declared non-ignored scenarios / 21 expanded executable cases protected by inventory logic.
- PCDP: 131 feature files / 480 declared / 1382 executable cases; snapshot 125 declared / 229 executable.

### Consequences

**Positive:**

- lifecycle ownership moves beside the tests that require the infrastructure;
- fewer CI orchestration boundaries;
- dynamic isolation is easier to express;
- test completeness can be enforced explicitly;
- infrastructure startup can be parallelised according to real dependencies.

**Trade-offs:**

- Testcontainers requires a reachable Docker daemon in CI;
- repository-specific topics, aggregate sets and application wiring remain local concerns;
- DIND CI runs use explicit lifecycle cleanup with Ryuk disabled.

## ADR-0003: Reduce Compose Role in CI; Retain Where Useful Locally

### Decision

For the validated CI path, Compose-heavy orchestration is no longer the primary business/integration-test lifecycle.

Docker Compose may remain for:

- local full-stack exploration;
- debugging workflows;
- legacy or auxiliary paths not yet migrated;
- compatibility where a repository-specific use case requires it.

The goal is not to delete Compose everywhere. The goal is to avoid using a mixed-purpose Compose stack as the default CI lifecycle when test-owned infrastructure provides a clearer and faster dependency graph.

### Adoption Rule

A repository moves to the common CI pattern only after equivalent business-test coverage and exact-image runtime validation are demonstrated. Compose is not removed simply to make the pipeline look simpler.

