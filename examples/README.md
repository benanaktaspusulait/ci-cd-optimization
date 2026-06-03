# Examples

Reference code samples for the pilot. These are **not production code** — they are annotated examples to illustrate the patterns proposed in the ADRs and stories.

Copy, adapt, and rename as needed when applying to the pilot repository.

## Contents

### Testcontainers + Cucumber + Spring Boot

| File | What it shows | Related |
|------|---------------|---------|
| [pom-dependencies.xml](testcontainers/pom-dependencies.xml) | All Maven dependencies needed (Testcontainers, Cucumber, Spring Boot Test, AWS SDK) | T3.2 |
| [TestcontainersBaseIT.java](testcontainers/TestcontainersBaseIT.java) | JUnit 5 + Cucumber entry point that triggers the full integration test suite | ADR-0002, Story 3 |
| [CucumberSpringConfig.java](testcontainers/CucumberSpringConfig.java) | The glue: Cucumber ↔ Spring Boot ↔ Testcontainers, dynamic property injection | ADR-0002, T3.2 |
| [RedisContainerConfig.java](testcontainers/RedisContainerConfig.java) | Simplest example: single Redis container with GenericContainer | ADR-0002, T3.2 |
| [KafkaContainerConfig.java](testcontainers/KafkaContainerConfig.java) | Complex example: Zookeeper + Kafka + Schema Registry with shared network | ADR-0002, T3.2 |
| [LocalStackContainerConfig.java](testcontainers/LocalStackContainerConfig.java) | AWS emulation: S3, SQS, SNS, DynamoDB via LocalStack + SDK client helpers | ADR-0002, T3.2 |

### Docker (build optimisation)

| File | What it shows | Related |
|------|---------------|---------|
| [Dockerfile](docker/Dockerfile) | Optimised multi-stage build with BuildKit cache mounts (heavily annotated) | ADR-0004, Story 2 |
| [.dockerignore](docker/.dockerignore) | Lean build context for Java/Maven (excludes IDE, docs, tests, secrets) | T2.2 |
| [docker-compose.yml](docker/docker-compose.yml) | Base Compose file with all typical FDP services (Redis, Zookeeper, Kafka, Schema Registry, LocalStack) | Story 4, ADR-0003 |

### CI/CD (GitLab)

| File | What it shows | Related |
|------|---------------|---------|
| [gitlab-ci-integration-test.yml](ci/gitlab-ci-integration-test.yml) | Integration test stage: Testcontainers DinD mode + Compose fallback (both jobs) | ADR-0005, T3.2 |

## How to use these examples

1. **Dependencies first** — add entries from `pom-dependencies.xml` to your `pom.xml`.
2. **Container configs** — copy `*ContainerConfig.java` files, adjust image versions if needed.
3. **Spring glue** — copy `CucumberSpringConfig.java`, add/remove `@DynamicPropertySource` blocks for the services you actually use.
4. **Entry point** — copy `TestcontainersBaseIT.java`, adjust the `GLUE_PROPERTY_NAME` package.
5. **Run locally** — `./mvnw verify -Dskip.unit.tests=true` should start containers and run Cucumber features.
6. **Run in CI** — use the `gitlab-ci-integration-test.yml` snippet, confirm runner tag with platform/ETO.
