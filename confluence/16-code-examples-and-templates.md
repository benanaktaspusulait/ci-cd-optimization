# Code Examples and Templates

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

This page provides the copy/adapt examples and the key snippets needed to understand them. All code is inline below — no other files are needed.

> **Classification note:** This page contains internal project details (package names, registry domains, infrastructure versions). It is intended for internal Confluence only. Do not share externally without security/classification review.

---

## Real Project Context

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
| Registry | `docker.digital.homeoffice.gov.uk` |
| CI runner image | `quay.io/ukhomeofficedigital/ileap-java17-mvn:1.3` |
| Test framework | Cucumber + JUnit 4 (vintage) + JUnit 5 Platform |
| Integration test orchestration | `docker-compose-maven-plugin` currently |

---

## Example Catalogue

### Testcontainers + Cucumber + Spring Boot

| Example | What it shows |
|---------|---------------|
| Testcontainers `pom.xml` dependencies | Minimal dependencies to add to the existing `pom.xml`; most FDP dependencies already exist |
| `TestcontainersBaseIT` Cucumber runner | Cucumber runner using JUnit 4 `@RunWith`, matching existing FDP pattern |
| `CucumberSpringConfig` | Spring Boot <-> Testcontainers glue with FDP-specific properties (`fdp.kafka.broker`, `fdp.app.redis.nodes`, etc.) |
| `RedisContainerConfig` | Redis 5.0.6 container; simplest first candidate for T4.1 |
| `KafkaContainerConfig` | Zookeeper + Kafka + Schema Registry, cp-7.5.5, matching production MSK |
| `LocalStackContainerConfig` | LocalStack IAM example if chosen as candidate |

### Docker Build Optimisation

| Example | What it shows |
|---------|---------------|
| Optimised `Dockerfile` | Multi-stage build; current single-stage `amazoncorretto:17` becomes deps/build/runtime stages with cache mounts |
| Infrastructure `docker-compose.yml` | Infrastructure services only, mirroring RepoSync-controlled compose without FDP application services |
| `.dockerignore` | Lean build context for Java/Maven multi-module project |

### CI/CD

| Example | What it shows |
|---------|---------------|
| Drone considerations | How Testcontainers/BuildKit would work in the real Drone pipeline |
| GitLab CI integration-test job | Illustrative GitLab CI equivalent with Testcontainers mode and Compose fallback |

> The real FDP CI uses **Drone** (`.drone.star` via RepoSync), not GitLab CI. GitLab CI snippets are illustrative only.

---

## How to Apply the Examples

1. Add Testcontainers BOM to parent `pom.xml` `<dependencyManagement>`.
2. Add three dependencies to `cmd-adaptor-dvla-integration-tests/pom.xml`: Testcontainers core, JUnit Jupiter, Kafka.
3. Add `testcontainers` Maven profile to skip `docker-compose-maven-plugin`.
4. Copy container configs (`RedisContainerConfig`, `KafkaContainerConfig`) to `src/test/java/`.
5. Copy or merge `CucumberSpringConfig` into the existing Spring test configuration.
6. Run locally: `./mvnw verify -pl cmd-adaptor-dvla-integration-tests -P testcontainers`.
7. Compare the same tests in Testcontainers vs `-P ci-cmd` for T4.3.

---

## Drone CI Considerations

### Current Pipeline Structure

```text
Pipeline type: Kubernetes
DIND service: docker (tcp://docker:2375)

CI pipeline steps:
1. RepoSync Version
2. Retrieve Artifactory Secrets
3. Wait for Docker
4. Extract Adaptor Information
5. Kafka & Redis (docker-compose up)
6. Aggregators (docker-compose up -d)
7. mvn clean install
8. Command Adaptor (docker-compose up --build)
9. Pre-Integration Tests (docker-compose up, wait checks)
10. Integration Tests (docker-compose up --exit-code-from)
11. Sonar Scan
12. Scan with Trivy
13. Slack notifications
```

### Testcontainers in Drone

For Testcontainers to work in the `mvn clean install` step or a new Maven step:

```yaml
environment:
  DOCKER_HOST: tcp://docker:2375
  TESTCONTAINERS_RYUK_DISABLED: "true"
  TESTCONTAINERS_CHECKS_DISABLE: "true"
```

**Why Ryuk must be disabled:** Ryuk is a helper container that cleans up other containers. In Drone's Kubernetes pipeline model, Ryuk cannot reliably connect to the DIND daemon. The ECR pipeline already has this workaround.

**Implication:** Without Ryuk, cleanup is the responsibility of the pipeline. Because Drone pods are ephemeral, this is acceptable; containers die with the pod.

**Where this change lives:** RepoSync-controlled `.drone.star`; it cannot be done durably in the adaptor repo.

**Fallback:** If Drone CI execution is not feasible or the RepoSync change is not approved, Testcontainers runs locally only and Docker Compose remains the CI integration test mechanism.

### BuildKit in Drone

**Multi-stage builds:** work today; `docker build` with multi-stage Dockerfiles is standard Docker behaviour.

**BuildKit cache mounts:** work per-build with `DOCKER_BUILDKIT=1`, but DIND is ephemeral per pipeline so cache is lost between builds.

```yaml
environment:
  DOCKER_BUILDKIT: "1"
```

This is a RepoSync-controlled change.

**Remote registry cache requires ACP/ETO:**

- Registry namespace for cache layers.
- Write permissions for the Drone pipeline.
- Retention/eviction policy.
- `.drone.star` change to add `--cache-from` / `--cache-to`.

### CST-Local vs CI Feasibility

| Action | Works locally | Works in CI |
|--------|:-------------:|:-----------:|
| Multi-stage Dockerfile | Yes | Yes (standard Docker) |
| `.dockerignore` | Yes | Yes |
| BuildKit cache mounts | Yes (persistent) | TBC (ephemeral per build) |
| Testcontainers tests | Yes | TBC (needs T1.4 confirmation) |
| Maven `-P testcontainers` profile | Yes | TBC (needs RepoSync change to skip compose) |
| Remote registry cache | No | No (needs ACP/ETO) |

### Recommended Pilot Approach

1. **Story 1:** confirm T1.4 (Testcontainers) and T1.5 (BuildKit) feasibility.
2. **Story 3:** apply Dockerfile optimisation locally; measure local before/after; CI benefit comes from multi-stage and `.dockerignore`.
3. **Story 4:** prototype Testcontainers locally; if T1.4 confirms CI feasibility, request RepoSync change for `DOCKER_HOST` + Ryuk env vars.
4. **Story 6:** document what was local vs what needs RepoSync/platform action.

---

## Dockerfile Pattern

The optimised Dockerfile example is based on the real FDP command adaptor.

Current state:

- Single stage: `amazoncorretto:17`.
- `yum install` + update in one `RUN`.
- Copies pre-built JAR + OpenTelemetry agent.
- No layer separation for dependencies.
- Includes `envconsul` for HashiCorp Vault integration.
- Runs as non-root `fdpuser`.

Optimised version:

- Multi-stage: dependency resolution -> build -> runtime.
- BuildKit cache mounts for Maven local repo.
- Smaller runtime payload: Maven, source and build artefacts stay out of the final image.
- Dependencies cached independently of source changes.
- OpenTelemetry agent downloaded once and cached in its own layer.
- `envconsul` remains because it is required for Vault secret injection at runtime.

```dockerfile
FROM amazoncorretto:17 AS deps
WORKDIR /app
COPY pom.xml ./
COPY cmd-adaptor-dvla/pom.xml cmd-adaptor-dvla/
COPY cmd-adaptor-dvla-common/pom.xml cmd-adaptor-dvla-common/
COPY cmd-adaptor-dvla-test-common/pom.xml cmd-adaptor-dvla-test-common/
COPY cmd-adaptor-dvla-integration-tests/pom.xml cmd-adaptor-dvla-integration-tests/
COPY .mvn .mvn
COPY mvnw ./
RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B dependency:go-offline -pl cmd-adaptor-dvla -am -DskipTests

FROM deps AS build
COPY cmd-adaptor-dvla/src cmd-adaptor-dvla/src
COPY cmd-adaptor-dvla-common/src cmd-adaptor-dvla-common/src
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B package -pl cmd-adaptor-dvla -am -DskipTests \
    && cp cmd-adaptor-dvla/target/cmd-adaptor-dvla-exec.jar /app/app.jar

FROM amazoncorretto:17 AS runtime
# envconsul + non-root user + app JAR copied from build stage
```

---

## Testcontainers Patterns

### Redis Container

Redis is the recommended first Testcontainers candidate:

- Simplest dependency; single container, no multi-node setup.
- Already used by multiple FDP services.
- Fast to start.
- No authentication in test mode.
- Proves pattern with minimal risk.

```java
public class RedisContainerConfig {
    private static final DockerImageName REDIS_IMAGE = DockerImageName.parse("redis:5.0.6");

    public static final GenericContainer<?> REDIS = new GenericContainer<>(REDIS_IMAGE)
            .withExposedPorts(6379);

    static {
        REDIS.start();
    }

    public static String getRedisNodes() {
        return REDIS.getHost() + ":" + REDIS.getMappedPort(6379);
    }
}
```

### Kafka + Schema Registry

Kafka pattern mirrors the real FDP docker-compose infrastructure:

- Zookeeper: `confluentinc/cp-zookeeper:7.5.5`.
- Kafka: `confluentinc/cp-kafka:7.5.5`, matching MSK 3.5.1 in production.
- Schema Registry: `confluentinc/cp-schema-registry:7.5.5`.

Key Testcontainers benefits:

- Random ports avoid conflicts.
- Wait strategies replace separate wait containers.
- Test code owns dependency lifecycle.
- Cleanup is Ryuk locally; Drone may rely on pod cleanup if Ryuk is disabled.

### Cucumber Spring Configuration

FDP integration tests currently use Cucumber + Spring. Dynamic properties wire Testcontainers values into Spring:

```java
@DynamicPropertySource
static void redisProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", RedisContainerConfig.REDIS::getHost);
    registry.add("spring.data.redis.port", () -> RedisContainerConfig.REDIS.getMappedPort(6379));
    registry.add("fdp.app.redis.nodes", RedisContainerConfig::getRedisNodes);
}
```

Kafka properties include `spring.kafka.bootstrap-servers`, `fdp.kafka.broker`, `fdp.kafka.schema-registry-url`, replication factor, min in-sync replicas, and topic suffix.

OpenTelemetry is disabled in tests by setting trace/metrics/log exporters to `none`.

### Cucumber Runner

The example preserves the current FDP JUnit 4 Cucumber pattern:

```java
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "uk.gov.ho.dacc.fdp.integration",
        plugin = {
                "pretty",
                "json:target/cucumber-report.json",
                "junit:target/cucumber-junit-report.xml"
        },
        tags = "not @snapshot"
)
public class TestcontainersBaseIT {
}
```

---

## Maven Dependencies and Profile

Add Testcontainers BOM to parent `dependencyManagement`:

```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers-bom</artifactId>
    <version>1.19.8</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

Add to `cmd-adaptor-dvla-integration-tests/pom.xml`:

```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>kafka</artifactId>
    <scope>test</scope>
</dependency>
```

Redis does not need a dedicated module; use `GenericContainer` with `redis:5.0.6`.

LocalStack is optional. Use `org.testcontainers:localstack` only if tests need AWS service emulation.

Add `testcontainers` Maven profile:

```xml
<profile>
    <id>testcontainers</id>
    <properties>
        <skip.containers>true</skip.containers>
        <skip.aggregators>true</skip.aggregators>
        <skip.integration.tests>false</skip.integration.tests>
    </properties>
</profile>
```

Usage:

```bash
./mvnw verify -pl cmd-adaptor-dvla-integration-tests -P testcontainers
```

---

## GitLab CI Illustration

The GitLab CI example shows the same idea in a non-Drone form:

- `integration-test-testcontainers`: runs `./mvnw -B verify -pl cmd-adaptor-dvla-integration-tests -P testcontainers`.
- `integration-test-compose`: fallback job using `-P ci-cmd`.
- Both capture simple duration metrics into artefacts.
- Docker access is provided by `docker:24-dind`.
- Runner tag and privileged Docker posture must be confirmed with ACP/ETO if used anywhere outside illustration.

This is illustrative only. Real implementation path for FDP is Drone/RepoSync.

---

*Feedback or questions? Contact the page owner or comment below.*
