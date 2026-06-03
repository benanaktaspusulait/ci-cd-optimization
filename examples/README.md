# Examples

Reference code samples for the pilot. These are **not production code** — they are annotated examples to illustrate the patterns proposed in the ADRs and stories.

Copy, adapt, and rename as needed when applying to the pilot repository.

## Contents

| File | Illustrates | Related |
|------|-------------|---------|
| [TestcontainersBaseIT.java](testcontainers/TestcontainersBaseIT.java) | Base class for Cucumber + Testcontainers + Spring Boot integration | ADR-0002, Story 3 |
| [RedisContainerConfig.java](testcontainers/RedisContainerConfig.java) | Single-container example (Redis) with Spring Boot property wiring | ADR-0002, T3.2 |
| [KafkaContainerConfig.java](testcontainers/KafkaContainerConfig.java) | Kafka + Schema Registry multi-container example | ADR-0002, T3.2 |
| [CucumberSpringConfig.java](testcontainers/CucumberSpringConfig.java) | Cucumber-Spring glue that wires Testcontainers into step definitions | ADR-0002, Story 3 |
| [Dockerfile](docker/Dockerfile) | Optimised multi-stage build with cache mounts (annotated) | ADR-0004, Story 2 |
| [.dockerignore](docker/.dockerignore) | Lean build context for a Java/Maven project | T2.2 |
| [gitlab-ci-integration-test.yml](ci/gitlab-ci-integration-test.yml) | Integration test stage with Testcontainers (DinD) | ADR-0005, T3.2 |
