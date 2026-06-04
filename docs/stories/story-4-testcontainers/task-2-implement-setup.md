# T4.2 — Implement Testcontainers setup

**Story:** [Story 4 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.2 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | L |
| **Priority** | Must |
| **Labels** | `testcontainers`, `integration-test`, `implementation` |
| **Sprint** | Week 2 |
| **Depends on** | T4.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A working, runnable setup is the only way to get real numbers and a real developer-experience signal. Prototyping it for one dependency proves feasibility before any wider commitment.

## Goal
Implement or prototype a Testcontainers setup for the selected dependency so an integration test can run against it.

## Scope
Implement:
- container definition
- required environment / property wiring
- readiness / wait strategy
- cleanup / isolation approach

Reference pattern:
```java
@Container
static KafkaContainer kafka = new KafkaContainer(
    DockerImageName.parse("confluentinc/cp-kafka:7.6.0"));

@DynamicPropertySource
static void props(DynamicPropertyRegistry r) {
    r.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
}
```

Use the same dependency image/tag as the Compose baseline unless T4.1 explicitly documents a reason to change it.

## Acceptance criteria
- [ ] The dependency starts via Testcontainers
- [ ] The test connects to the dependency successfully
- [ ] The setup runs locally
- [ ] CI suitability is assessed or noted
