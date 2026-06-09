# Technical Details — Testcontainers

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

> This page contains deep technical content for engineers. Non-technical readers should refer to the parent overview and proposal matrix.

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


---

*Feedback or questions? Contact the page owner or comment below.*
