package com.example.pilot.integration;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Cucumber ↔ Spring Boot ↔ Testcontainers glue configuration.
 *
 * This class is the bridge between three frameworks:
 * 1. Cucumber — discovers this via @CucumberContextConfiguration in the glue package.
 * 2. Spring Boot — @SpringBootTest starts the full application context.
 * 3. Testcontainers — container configs (Redis, Kafka) register their dynamic properties here.
 *
 * How it fits together:
 * - Cucumber finds this config because it's in the glue package (see TestcontainersBaseIT).
 * - Spring starts the app context once per test run (not per scenario — reuses context).
 * - @DynamicPropertySource injects container connection details (host, port) into Spring properties.
 * - Step definitions are Spring beans — they can @Autowired any service/repository as normal.
 *
 * Lifecycle:
 * - Containers start BEFORE the Spring context initialises (static initialiser in container configs).
 * - Containers are shared across all scenarios in the same test run (fast).
 * - Containers are destroyed when the JVM exits (Testcontainers Ryuk handles cleanup).
 * - In CI, container reuse is DISABLED — every pipeline run gets fresh containers (deterministic).
 *
 * Related: ADR-0002, RedisContainerConfig.java, KafkaContainerConfig.java
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class CucumberSpringConfig {

    // ── Redis ────────────────────────────────────────────────────────────────
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", RedisContainerConfig.REDIS::getHost);
        registry.add("spring.data.redis.port", () -> RedisContainerConfig.REDIS.getMappedPort(6379));
    }

    // ── Kafka ────────────────────────────────────────────────────────────────
    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KafkaContainerConfig.KAFKA::getBootstrapServers);
        registry.add("spring.kafka.properties.schema.registry.url",
                () -> KafkaContainerConfig.SCHEMA_REGISTRY_URL);
    }
}
