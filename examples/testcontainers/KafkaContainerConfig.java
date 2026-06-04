package uk.gov.ho.dacc.fdp.integration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcontainers configuration for Kafka + Zookeeper + Schema Registry.
 *
 * Mirrors the real FDP docker-compose infrastructure:
 * - Zookeeper: confluentinc/cp-zookeeper:7.5.5
 * - Kafka: confluentinc/cp-kafka:7.5.5 (matches MSK 3.5.1 in production)
 * - Schema Registry: confluentinc/cp-schema-registry:7.5.5
 *
 * Why these versions:
 * PM-71719 states MSK deployed 3.5.1 equates to cp-kafka 7.5.5.
 * Using the same versions in tests ensures parity with production.
 *
 * How the FDP integration tests currently work (docker-compose):
 * 1. docker-compose-maven-plugin starts zookeeper → kafka → schema-registry → redis
 * 2. pre-integration-test service waits for all to be healthy
 * 3. Aggregator services start (aggregate-party, aggregate-object, etc.)
 * 4. Command adaptor builds from source and starts
 * 5. Cucumber tests run against the full stack
 *
 * How this would work with Testcontainers:
 * 1. This class starts zookeeper → kafka → schema-registry programmatically
 * 2. No pre-integration-test wait container needed (wait strategies handle it)
 * 3. Tests connect via dynamic ports (no conflicts with other services)
 * 4. Cleanup is automatic in local runs (Ryuk); Drone may rely on ephemeral pod cleanup
 *
 * Comparison table (for T4.3):
 * ┌──────────────────────────────┬──────────────────────────────────────────────┐
 * │ Current (docker-compose)     │ Proposed (Testcontainers)                    │
 * ├──────────────────────────────┼──────────────────────────────────────────────┤
 * │ Fixed ports (9092, 8081)     │ Random ports (no conflicts)                  │
 * │ KAFKA_AUTO_CREATE=false      │ Same config, set via withEnv()               │
 * │ Separate wait container      │ Built-in wait strategies                     │
 * │ Shared state across tests    │ Isolated per test run                        │
 * │ `mvn docker-compose:up`      │ Started from test code (same JVM)            │
 * │ Manual cleanup (compose down)│ Ryuk locally; Drone pod cleanup if disabled  │
 * │ CI needs docker-compose CLI  │ CI needs Docker daemon only                  │
 * └──────────────────────────────┴──────────────────────────────────────────────┘
 *
 * Related: ADR-0002, ADR-0003, Story 4, T4.2
 */
public class KafkaContainerConfig {

    // Pin to exact versions matching production MSK (PM-71719)
    private static final DockerImageName ZOOKEEPER_IMAGE =
            DockerImageName.parse("confluentinc/cp-zookeeper:7.5.5");
    private static final DockerImageName KAFKA_IMAGE =
            DockerImageName.parse("confluentinc/cp-kafka:7.5.5");
    private static final DockerImageName SCHEMA_REGISTRY_IMAGE =
            DockerImageName.parse("confluentinc/cp-schema-registry:7.5.5");

    // Shared network (equivalent to docker-compose default network)
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
    public static final KafkaContainer KAFKA = new KafkaContainer(KAFKA_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("kafka")
            .withExternalZookeeper("zookeeper:2181")
            .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", "1")
            .withEnv("KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS", "100")
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "false")
            .dependsOn(ZOOKEEPER);

    // ── Schema Registry ──────────────────────────────────────────────────────
    private static final GenericContainer<?> SCHEMA_REGISTRY = new GenericContainer<>(SCHEMA_REGISTRY_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("schema-registry")
            .withExposedPorts(8081)
            .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://kafka:9092")
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_SSL_ENABLED_PROTOCOLS", "PLAINTEXT")
            .dependsOn(KAFKA)
            .waitingFor(Wait.forHttp("/subjects").forStatusCode(200));

    // ── Exposed URLs for Spring property injection ───────────────────────────
    public static final String BOOTSTRAP_SERVERS;
    public static final String SCHEMA_REGISTRY_URL;

    static {
        ZOOKEEPER.start();
        KAFKA.start();
        SCHEMA_REGISTRY.start();

        BOOTSTRAP_SERVERS = KAFKA.getBootstrapServers();
        SCHEMA_REGISTRY_URL = "http://" + SCHEMA_REGISTRY.getHost()
                + ":" + SCHEMA_REGISTRY.getMappedPort(8081);
    }
}
