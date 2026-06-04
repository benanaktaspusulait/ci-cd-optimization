# Examples

Reference code samples based on the **real FDP project structure**. These match the actual canlı ortam (fdp-cmd-adaptor-dvla, docker-compose via RepoSync, Maven multi-module).

Copy, adapt, and rename as needed when applying to the pilot repository.

## Real project context

| Property | Value |
|----------|-------|
| Group ID | `uk.gov.ho.dacc.fdp` |
| Artifact | `fdp-cmd-adaptor-dvla` |
| Java | 17 (Amazon Corretto) |
| Build | Maven multi-module (parent + 4 modules) |
| Kafka | Confluent cp-kafka 7.5.5 (matches MSK 3.5.1 prod) |
| Schema Registry | cp-schema-registry 7.5.5 |
| Redis | 5.0.6 |
| LocalStack | 0.12.18 (IAM only) |
| Tracing | OpenTelemetry + Jaeger |
| Registry | docker.digital.homeoffice.gov.uk |
| CI runner image | quay.io/ukhomeofficedigital/ileap-java17-mvn:1.3 |
| Test framework | Cucumber + JUnit 4 (vintage) + JUnit 5 Platform |
| Integration test orchestration | docker-compose-maven-plugin (currently) |

## Contents

### Testcontainers + Cucumber + Spring Boot

| File | What it shows |
|------|---------------|
| [pom-dependencies.xml](testcontainers/pom-dependencies.xml) | What to add to the existing pom.xml (minimal — most deps already exist) |
| [TestcontainersBaseIT.java](testcontainers/TestcontainersBaseIT.java) | Cucumber runner (JUnit 4 @RunWith style, matching existing FDP pattern) |
| [CucumberSpringConfig.java](testcontainers/CucumberSpringConfig.java) | Spring Boot ↔ Testcontainers glue with FDP-specific properties (fdp.kafka.broker, fdp.app.redis.nodes, etc.) |
| [RedisContainerConfig.java](testcontainers/RedisContainerConfig.java) | Redis 5.0.6 container (simplest candidate for T3.1) |
| [KafkaContainerConfig.java](testcontainers/KafkaContainerConfig.java) | Zookeeper + Kafka + Schema Registry (cp-7.5.5, matching production MSK) |
| [LocalStackContainerConfig.java](testcontainers/LocalStackContainerConfig.java) | LocalStack (IAM) — if chosen as candidate |

### Docker (build optimisation)

| File | What it shows |
|------|---------------|
| [Dockerfile](docker/Dockerfile) | Optimised multi-stage build (current single-stage amazoncorretto:17 → 3 stages with cache mounts) |
| [docker-compose.yml](docker/docker-compose.yml) | Infrastructure services only (mirrors real RepoSync-controlled compose, without FDP app services) |
| [.dockerignore](docker/.dockerignore) | Lean build context for Java/Maven multi-module project |

### CI/CD (GitLab)

| File | What it shows |
|------|---------------|
| [gitlab-ci-integration-test.yml](ci/gitlab-ci-integration-test.yml) | Two jobs: Testcontainers mode (`-P testcontainers`) + Compose fallback (`-P ci-cmd`) for comparison |

## How to apply

1. **Add Testcontainers BOM** to parent pom.xml `<dependencyManagement>` (see pom-dependencies.xml)
2. **Add 3 dependencies** to `cmd-adaptor-dvla-integration-tests/pom.xml` (testcontainers, junit-jupiter, kafka)
3. **Add `testcontainers` Maven profile** (skips docker-compose-maven-plugin)
4. **Copy container configs** (RedisContainerConfig, KafkaContainerConfig) to `src/test/java/`
5. **Copy CucumberSpringConfig** (or merge into existing Spring test config)
6. **Run locally:** `./mvnw verify -pl cmd-adaptor-dvla-integration-tests -P testcontainers`
7. **Compare (T3.3):** same tests, Testcontainers vs `-P ci-cmd`, measure timing
