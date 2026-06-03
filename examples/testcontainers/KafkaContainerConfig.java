package com.example.pilot.integration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcontainers configuration for Kafka + Zookeeper + Schema Registry.
 *
 * This example mirrors the existing docker-compose.yml setup where:
 * - Zookeeper runs as a separate service (Kafka depends on it)
 * - Kafka runs a single broker
 * - Schema Registry connects to Kafka for Avro/JSON schema management
 *
 * With Testcontainers, we replicate the same topology programmatically.
 * The test code controls the entire lifecycle — no external YAML needed.
 *
 * NOTE on Zookeeper:
 * - cp-kafka 7.x supports KRaft mode (no Zookeeper needed).
 * - Testcontainers' KafkaContainer uses KRaft by default since tc-kafka module 1.19+.
 * - However, if your Kafka version or config requires Zookeeper, use the explicit setup below.
 * - We show BOTH approaches: KRaft (simple) and Zookeeper (explicit).
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * APPROACH 1: KRaft mode (recommended for cp-kafka 7.4+)
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * If you don't need Zookeeper compatibility, this is simpler:
 *
 *   public static final KafkaContainer KAFKA = new KafkaContainer(
 *       DockerImageName.parse("confluentinc/cp-kafka:7.6.1")
 *   );
 *
 * That's it — KafkaContainer handles everything internally (no Zookeeper).
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * APPROACH 2: Explicit Zookeeper + Kafka + Schema Registry (below)
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Use this when:
 * - Your Kafka version requires Zookeeper (pre-7.4 or specific config)
 * - You want to mirror the exact docker-compose topology for comparison (T3.3)
 * - You need Schema Registry connected to Kafka
 *
 * How it works:
 * 1. A shared Testcontainers Network is created (like docker-compose's default network).
 * 2. Zookeeper starts first with a health check.
 * 3. Kafka starts after Zookeeper is healthy, configured to connect via network alias.
 * 4. Schema Registry starts after Kafka, pointed at Kafka's internal listener.
 * 5. CucumberSpringConfig reads the dynamic URLs and injects them into Spring properties.
 *
 * Comparison to docker-compose (for T3.3):
 * ┌────────────────────────────┬────────────────────────────────────────────────┐
 * │ docker-compose             │ Testcontainers                                 │
 * ├────────────────────────────┼────────────────────────────────────────────────┤
 * │ Ports hardcoded (9092)     │ Ports randomised (no conflicts ever)           │
 * │ depends_on (no healthcheck)│ Explicit wait strategies (truly ready)         │
 * │ Shared across all tests    │ Isolated per test run (no hidden state)        │
 * │ Started via CLI before test│ Started from code (same JVM, same lifecycle)   │
 * │ Cleanup: compose down -v   │ Cleanup: automatic (Ryuk) on JVM exit         │
 * └────────────────────────────┴────────────────────────────────────────────────┘
 *
 * Related: ADR-0002, ADR-0003, CucumberSpringConfig.java, T3.2, T3.3
 */
public class KafkaContainerConfig {

    private static final DockerImageName ZOOKEEPER_IMAGE =
            DockerImageName.parse("confluentinc/cp-zookeeper:7.6.1");

    private static final DockerImageName KAFKA_IMAGE =
            DockerImageName.parse("confluentinc/cp-kafka:7.6.1");

    private static final DockerImageName SCHEMA_REGISTRY_IMAGE =
            DockerImageName.parse("confluentinc/cp-schema-registry:7.6.1");

    // Shared network — all containers communicate over this (like docker-compose default network).
    private static final Network NETWORK = Network.newNetwork();

    // ── Zookeeper ────────────────────────────────────────────────────────────
    private static final GenericContainer<?> ZOOKEEPER = new GenericContainer<>(ZOOKEEPER_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("zookeeper")
            .withEnv("ZOOKEEPER_CLIENT_PORT", "2181")
            .withEnv("ZOOKEEPER_TICK_TIME", "2000")
            .withExposedPorts(2181)
            .waitingFor(Wait.forListeningPort());

    // ── Kafka ────────────────────────────────────────────────────────────────
    /**
     * Kafka broker connected to Zookeeper.
     * Two listeners: PLAINTEXT (internal, for Schema Registry) and PLAINTEXT_HOST (external, for tests).
     */
    public static final KafkaContainer KAFKA = new KafkaContainer(KAFKA_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("kafka")
            .withExternalZookeeper("zookeeper:2181")
            .dependsOn(ZOOKEEPER);

    // ── Schema Registry ──────────────────────────────────────────────────────
    private static final GenericContainer<?> SCHEMA_REGISTRY = new GenericContainer<>(SCHEMA_REGISTRY_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("schema-registry")
            .withExposedPorts(8081)
            .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "kafka:9092")
            .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
            .dependsOn(KAFKA)
            .waitingFor(Wait.forHttp("/subjects").forStatusCode(200));

    // ── Exposed URLs for Spring property injection ───────────────────────────
    /** Kafka bootstrap servers URL (for spring.kafka.bootstrap-servers). */
    public static final String BOOTSTRAP_SERVERS;

    /** Schema Registry URL (for spring.kafka.properties.schema.registry.url). */
    public static final String SCHEMA_REGISTRY_URL;

    static {
        // Start in dependency order: Zookeeper → Kafka → Schema Registry.
        ZOOKEEPER.start();
        KAFKA.start();
        SCHEMA_REGISTRY.start();

        BOOTSTRAP_SERVERS = KAFKA.getBootstrapServers();
        SCHEMA_REGISTRY_URL = "http://" + SCHEMA_REGISTRY.getHost()
                + ":" + SCHEMA_REGISTRY.getMappedPort(8081);
    }
}
