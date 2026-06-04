package uk.gov.ho.dacc.fdp.integration;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Cucumber ↔ Spring Boot ↔ Testcontainers glue configuration for FDP.
 *
 * This replaces the docker-compose-maven-plugin approach where:
 *   1. Maven starts docker-compose (pre-integration-test phase)
 *   2. A wait container checks health of kafka/redis/schema-registry
 *   3. Cucumber tests run against fixed ports (localhost:9092, localhost:8081, localhost:6379)
 *
 * With Testcontainers:
 *   1. This class starts containers programmatically (static initialisers in *ContainerConfig)
 *   2. Wait strategies are built into each container definition (no separate wait container)
 *   3. @DynamicPropertySource injects randomised ports into Spring properties
 *   4. No port conflicts, no shared state, deterministic
 *
 * FDP-specific Spring properties wired here:
 *   - FDP_KAFKA_BROKER / spring.kafka.bootstrap-servers
 *   - FDP_KAFKA_SCHEMA_REGISTRY_URL
 *   - FDP_APP_REDIS_NODES / spring.data.redis.host + port
 *   - FDP_APP_KAFKA_TOPIC_SUFFIX (set to "0" as in docker-compose)
 *   - FDP_APP_KAFKA_STREAM_REPLICATION_FACTOR (1 for single-node test Kafka)
 *   - FDP_APP_KAFKA_STREAM_MIN_INSYNC_REPLICAS (1 for single-node)
 *
 * Profiles:
 *   - Uses "integration-test" profile (similar to existing "docker" profile but for Testcontainers)
 *   - Application-specific properties can be overridden in application-integration-test.yml
 *
 * Related: ADR-0002, Story 3, T3.2
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class CucumberSpringConfig {

    // ── Kafka + Schema Registry ──────────────────────────────────────────────
    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", () -> KafkaContainerConfig.BOOTSTRAP_SERVERS);
        registry.add("fdp.kafka.broker", () -> KafkaContainerConfig.BOOTSTRAP_SERVERS);
        registry.add("fdp.kafka.schema-registry-url", () -> KafkaContainerConfig.SCHEMA_REGISTRY_URL);
        registry.add("fdp.app.kafka.stream.replication-factor", () -> "1");
        registry.add("fdp.app.kafka.stream.min-insync-replicas", () -> "1");
        registry.add("fdp.app.kafka.topic.suffix", () -> "0");
    }

    // ── Redis ────────────────────────────────────────────────────────────────
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", RedisContainerConfig.REDIS::getHost);
        registry.add("spring.data.redis.port", () -> RedisContainerConfig.REDIS.getMappedPort(6379));
        registry.add("fdp.app.redis.nodes", RedisContainerConfig::getRedisNodes);
    }

    // ── OpenTelemetry (disable in tests — no Jaeger needed) ──────────────────
    @DynamicPropertySource
    static void otelProperties(DynamicPropertyRegistry registry) {
        registry.add("otel.traces.exporter", () -> "none");
        registry.add("otel.metrics.exporter", () -> "none");
        registry.add("otel.logs.exporter", () -> "none");
    }
}
