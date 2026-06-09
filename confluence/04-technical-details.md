# Technical Details

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

> This page contains deep technical content for engineers. Non-technical readers should refer to the parent overview and proposal matrix.

---

## 1. Dockerfile Optimisation

### Current State

The existing Dockerfile (`amazoncorretto:17` base) is single-stage:

```dockerfile
FROM amazoncorretto:17

COPY ./target/cmd-adaptor-dvla-exec.jar /local
COPY ./target/dependencies/opentelemetry-javaagent.jar /local/opentelemetry-javaagent.jar

WORKDIR /tmp

RUN yum install -y shadow-utils unzip \
    && yum update -y ca-certificates ... \
    && curl --silent --output /tmp/envconsul.zip https://releases.hashicorp.com/envconsul/0.13.1/envconsul_0.13.1_linux_amd64.zip \
    && unzip envconsul.zip && mv envconsul /usr/local/bin/envconsul \
    && adduser -u 1000 -U -m -s /bin/bash fdpuser \
    && chmod 0755 /local/cmd-adaptor-dvla-exec.jar \
    && chown fdpuser:fdpuser /local/cmd-adaptor-dvla-exec.jar

USER fdpuser
WORKDIR /home/fdpuser

CMD ["java", "-javaagent:/local/opentelemetry-javaagent.jar", ... "-jar", "/local/cmd-adaptor-dvla-exec.jar"]
```

**Problems:**
- Ships full JDK and OS/runtime tools in production image (~450 MB).
- No layer separation — depending on the build path, source changes may trigger full dependency re-resolution.
- No BuildKit cache mounts — Maven `.m2` not persisted between builds.
- `yum update` in same layer as app code — invalidates frequently.

### Proposed Multi-Stage Dockerfile

```dockerfile
# syntax=docker/dockerfile:1

# ══════════════════════════════════════════════════════════════════════════════
# Stage 1: Resolve dependencies (cached independently of source changes)
# ══════════════════════════════════════════════════════════════════════════════
FROM amazoncorretto:17 AS deps
WORKDIR /app

# Copy only dependency metadata — rebuilds ONLY when pom.xml changes.
COPY pom.xml ./
COPY cmd-adaptor-dvla/pom.xml cmd-adaptor-dvla/
COPY cmd-adaptor-dvla-common/pom.xml cmd-adaptor-dvla-common/
COPY cmd-adaptor-dvla-test-common/pom.xml cmd-adaptor-dvla-test-common/
COPY cmd-adaptor-dvla-integration-tests/pom.xml cmd-adaptor-dvla-integration-tests/

COPY .mvn .mvn
COPY mvnw ./
RUN chmod +x mvnw

# Cache mount: persists /root/.m2 across builds locally (ephemeral in CI DIND).
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B dependency:go-offline -pl cmd-adaptor-dvla -am -DskipTests

# ══════════════════════════════════════════════════════════════════════════════
# Stage 2: Build the application
# ══════════════════════════════════════════════════════════════════════════════
FROM deps AS build

COPY cmd-adaptor-dvla/src cmd-adaptor-dvla/src
COPY cmd-adaptor-dvla-common/src cmd-adaptor-dvla-common/src

RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B package -pl cmd-adaptor-dvla -am -DskipTests \
    && cp cmd-adaptor-dvla/target/cmd-adaptor-dvla-exec.jar /app/app.jar

# Download OpenTelemetry agent
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B dependency:copy \
      -Dartifact=io.opentelemetry.javaagent:opentelemetry-javaagent:1.30.0:jar \
      -DoutputDirectory=/app/agent

# ══════════════════════════════════════════════════════════════════════════════
# Stage 3: Runtime (minimal — no JDK, no Maven, no source)
# ══════════════════════════════════════════════════════════════════════════════
FROM amazoncorretto:17 AS runtime

# Install envconsul (HashiCorp Vault integration)
RUN yum install -y shadow-utils unzip \
    && curl --silent --output /tmp/envconsul.zip \
       https://releases.hashicorp.com/envconsul/0.13.1/envconsul_0.13.1_linux_amd64.zip \
    && unzip /tmp/envconsul.zip -d /usr/local/bin/ \
    && rm -f /tmp/envconsul.zip \
    && yum clean all && rm -rf /var/cache/yum

RUN adduser -u 1000 -U -m -s /bin/bash fdpuser

WORKDIR /home/fdpuser
COPY --from=build --chown=fdpuser:fdpuser /app/app.jar ./cmd-adaptor-dvla-exec.jar
COPY --from=build --chown=fdpuser:fdpuser /app/agent/opentelemetry-javaagent-1.30.0.jar ./opentelemetry-javaagent.jar

USER fdpuser
EXPOSE 7112 8077

HEALTHCHECK --interval=30s --timeout=5s --start-period=15s --retries=3 \
    CMD ["sh", "-c", "curl -sf http://localhost:7112/actuator/health || exit 1"]

CMD ["java", \
     "-javaagent:/home/fdpuser/opentelemetry-javaagent.jar", \
     "-jar", "/home/fdpuser/cmd-adaptor-dvla-exec.jar"]
```

### Expected Gains

| Metric | Before (estimated) | After (estimated) | Improvement |
|--------|--------------------|--------------------|-------------|
| Image size | ~450 MB | ~300 MB | ≥ 30% ↓ |
| Local rebuild (source change) | ~5 min | ~1.5 min | ≥ 70% ↓ |
| Local rebuild (dep change) | ~5 min | ~3 min | ~40% ↓ |
| Build context | ~200 MB | ~50 MB | ≥ 75% ↓ |

---

## 2. .dockerignore

```gitignore
.git/
.gitignore
.idea/
*.iml
target/
build/
out/
docs/
*.md
*.log
scripts/
docker-compose*.yml
src/test/
```

---

## 3. Docker Compose — Current CI Services

The integration test `docker-compose.yml` (RepoSync-controlled) starts these services:

| Service | Image | Version | Purpose | CI-required? |
|---------|-------|---------|---------|:------------:|
| zookeeper | confluentinc/cp-zookeeper | 7.5.5 | Kafka dependency | Yes |
| kafka | confluentinc/cp-kafka | 7.5.5 | Event streaming (matches MSK 3.5.1 prod) | Yes |
| schema-registry | confluentinc/cp-schema-registry | 7.5.5 | Avro schema management | Yes |
| redis | redis | 5.0.6 | Cache / state store | Yes |
| localstack | localstack/localstack | 0.12.18 | AWS IAM emulation | Maybe |
| jaeger | jaegertracing/all-in-one | 1.65.0 | OpenTelemetry trace UI | No (local debug) |
| kafdrop | obsidiandynamics/kafdrop | 3.30.0 | Kafka UI | No (local debug) |
| kafka-rest | confluentinc/cp-kafka-rest | 7.5.5 | REST API for Kafka | Maybe |
| aggregate-party | Internal FDP image | CORE_TAG | Stream processor | Snapshot tests only |
| aggregate-object | Internal FDP image | CORE_TAG | Stream processor | Snapshot tests only |
| aggregate-location | Internal FDP image | CORE_TAG | Stream processor | Snapshot tests only |
| aggregate-event | Internal FDP image | CORE_TAG | Stream processor | Snapshot tests only |
| aggregate-service | Internal FDP image | CORE_TAG | Stream processor | Snapshot tests only |
| aggregate-matching | Internal FDP image | CORE_TAG | Stream processor | Snapshot tests only |
| aggregate-v1id-v2id | Internal FDP image | CORE_TAG | Stream processor | Snapshot tests only |
| command-adaptor | Built from source | — | The service under test | Yes |
| pre-integration-test | Custom build | — | Wait/health-check orchestrator | Yes (startup only) |
| integration-tests | ileap-java17-mvn:1.3 | — | Runs Maven integration tests | Yes |

Story 5 maps and classifies these to determine what can be reduced in CI.

---

## 4. Testcontainers

### Approach

Replace one Docker Compose dependency with Testcontainers. The existing Cucumber + JUnit 4 (vintage) test structure is preserved.

### RedisContainerConfig.java

```java
package uk.gov.ho.dacc.fdp.integration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class RedisContainerConfig {

    // Pin to same version as production docker-compose
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

### KafkaContainerConfig.java (Zookeeper + Kafka + Schema Registry)

```java
package uk.gov.ho.dacc.fdp.integration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class KafkaContainerConfig {

    // Pin to versions matching production MSK (PM-71719: MSK 3.5.1 = cp-kafka 7.5.5)
    private static final DockerImageName ZOOKEEPER_IMAGE = DockerImageName.parse("confluentinc/cp-zookeeper:7.5.5");
    private static final DockerImageName KAFKA_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:7.5.5");
    private static final DockerImageName SCHEMA_REGISTRY_IMAGE = DockerImageName.parse("confluentinc/cp-schema-registry:7.5.5");

    private static final Network NETWORK = Network.newNetwork();

    private static final GenericContainer<?> ZOOKEEPER = new GenericContainer<>(ZOOKEEPER_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("zookeeper")
            .withEnv("ZOOKEEPER_CLIENT_PORT", "2181")
            .withEnv("ZOOKEEPER_TICK_TIME", "2000")
            .withExposedPorts(2181)
            .waitingFor(Wait.forListeningPort());

    public static final KafkaContainer KAFKA = new KafkaContainer(KAFKA_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("kafka")
            .withExternalZookeeper("zookeeper:2181")
            .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", "1")
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "false")
            .dependsOn(ZOOKEEPER);

    private static final GenericContainer<?> SCHEMA_REGISTRY = new GenericContainer<>(SCHEMA_REGISTRY_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("schema-registry")
            .withExposedPorts(8081)
            .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://kafka:9092")
            .dependsOn(KAFKA)
            .waitingFor(Wait.forHttp("/subjects").forStatusCode(200));

    public static final String BOOTSTRAP_SERVERS;
    public static final String SCHEMA_REGISTRY_URL;

    static {
        ZOOKEEPER.start();
        KAFKA.start();
        SCHEMA_REGISTRY.start();
        BOOTSTRAP_SERVERS = KAFKA.getBootstrapServers();
        SCHEMA_REGISTRY_URL = "http://" + SCHEMA_REGISTRY.getHost() + ":" + SCHEMA_REGISTRY.getMappedPort(8081);
    }
}
```

### CucumberSpringConfig.java

```java
package uk.gov.ho.dacc.fdp.integration;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class CucumberSpringConfig {

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", () -> KafkaContainerConfig.BOOTSTRAP_SERVERS);
        registry.add("fdp.kafka.broker", () -> KafkaContainerConfig.BOOTSTRAP_SERVERS);
        registry.add("fdp.kafka.schema-registry-url", () -> KafkaContainerConfig.SCHEMA_REGISTRY_URL);
        registry.add("fdp.app.kafka.stream.replication-factor", () -> "1");
        registry.add("fdp.app.kafka.stream.min-insync-replicas", () -> "1");
        registry.add("fdp.app.kafka.topic.suffix", () -> "0");
    }

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", RedisContainerConfig.REDIS::getHost);
        registry.add("spring.data.redis.port", () -> RedisContainerConfig.REDIS.getMappedPort(6379));
        registry.add("fdp.app.redis.nodes", RedisContainerConfig::getRedisNodes);
    }

    @DynamicPropertySource
    static void otelProperties(DynamicPropertyRegistry registry) {
        registry.add("otel.traces.exporter", () -> "none");
        registry.add("otel.metrics.exporter", () -> "none");
    }
}
```

### TestcontainersBaseIT.java (Cucumber runner)

```java
package uk.gov.ho.dacc.fdp.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "uk.gov.ho.dacc.fdp.integration",
        plugin = {"pretty", "json:target/cucumber-report.json"},
        tags = "not @snapshot"
)
public class TestcontainersBaseIT {
}
```

### Maven Dependencies to Add

Add to parent `pom.xml` `<dependencyManagement>`:

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

All other dependencies (Cucumber, Spring Boot Test, JUnit) already exist in the project.

### Maven Profile

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

Usage: `./mvnw verify -pl cmd-adaptor-dvla-integration-tests -P testcontainers`

### Reuse Policy

- **Local:** reuse enabled (`testcontainers.reuse.enable=true` in `~/.testcontainers.properties`) for faster feedback.
- **CI:** reuse disabled (default) — clean containers per pipeline run (deterministic).

---

## 5. BuildKit

### Local Cache Mounts

`DOCKER_BUILDKIT=1 docker build .` enables `--mount=type=cache`. The Maven `.m2` repository persists across local builds — deps are not re-downloaded unless `pom.xml` changes.

### Remote Cache (post-pilot, requires ACP)

```bash
docker buildx build \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:main \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:$BRANCH \
  --cache-to=type=registry,ref=$REGISTRY_IMAGE/cache:$BRANCH,mode=max \
  --tag $REGISTRY_IMAGE:$COMMIT_SHA --push .
```

**Not implementable without ACP:** requires registry namespace (`docker.digital.homeoffice.gov.uk/dacc-aws/fdp-cache`), write permissions, DIND BuildKit support, and RepoSync `.drone.star` change.

### Build Measurement

Local measurement approach (before/after):

```bash
# Warm build (with cache)
time DOCKER_BUILDKIT=1 docker build -t pilot:test .

# Cold build (no cache)
time DOCKER_BUILDKIT=1 docker build --no-cache -t pilot:nocache .

# Image size
docker images pilot:test --format '{{.Size}}'
```

---

## 6. Base Image Strategy (Post-Pilot, DSA ETO)

Target hierarchy:

```text
base-os (patched OS — e.g. Amazon Linux)
  └── base-runtime (JRE + core runtime deps)
        └── base-build (JDK + Maven — build stages only)
              └── application (team-built)
```

> The initial pilot may identify where shared base images would help, but creating and maintaining organisation-level base images would require DSA ETO ownership, lifecycle management, rebuild cadence, and compatibility guarantees.

---

*Feedback or questions? Contact the page owner or comment below.*
