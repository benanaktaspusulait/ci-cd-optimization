package com.example.pilot.integration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcontainers configuration for Kafka + Schema Registry.
 *
 * This is a more complex example — two containers that need to communicate.
 * Demonstrates Testcontainers networking, wait strategies, and multi-container coordination.
 *
 * Why this matters:
 * - In docker-compose, Kafka + Schema Registry are started together with a shared network.
 * - With Testcontainers, we replicate the same topology programmatically.
 * - The test code controls the entire lifecycle — no external file needed.
 *
 * How it works:
 * 1. A shared Testcontainers Network is created (like a docker-compose network).
 * 2. Kafka starts first, exposing a bootstrap server on a random port.
 * 3. Schema Registry starts second, pointed at the Kafka container via the shared network.
 * 4. Wait strategies ensure each container is healthy before proceeding.
 * 5. CucumberSpringConfig reads the dynamic URLs and injects them into Spring properties.
 *
 * Comparison to docker-compose (for T3.3):
 * - docker-compose: services defined in YAML, ports hardcoded or range-mapped, startup order via depends_on (no health check by default).
 * - Testcontainers: services defined in code, ports always randomised (no conflicts), startup order enforced by explicit wait strategies.
 *
 * Related: ADR-0002, CucumberSpringConfig.java, T3.2, T3.3
 */
public class KafkaContainerConfig {

    private static final DockerImageName KAFKA_IMAGE =
            DockerImageName.parse("confluentinc/cp-kafka:7.6.1");

    private static final DockerImageName SCHEMA_REGISTRY_IMAGE =
            DockerImageName.parse("confluentinc/cp-schema-registry:7.6.1");

    // Shared network — Kafka and Schema Registry communicate over this.
    private static final Network NETWORK = Network.newNetwork();

    /**
     * Kafka container — single broker, KRaft mode (no Zookeeper needed in cp-kafka 7.x).
     */
    public static final KafkaContainer KAFKA = new KafkaContainer(KAFKA_IMAGE)
            .withNetwork(NETWORK)
            .withNetworkAliases("kafka");

    /**
     * Schema Registry container — connects to Kafka via the shared network alias.
     */
    private static final GenericContainer<?> SCHEMA_REGISTRY = new GenericContainer<>(SCHEMA_REGISTRY_IMAGE)
            .withNetwork(NETWORK)
            .withExposedPorts(8081)
            .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "kafka:9092")
            .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
            .dependsOn(KAFKA)
            .waitingFor(Wait.forHttp("/subjects").forStatusCode(200));

    /** Schema Registry URL for Spring property injection. */
    public static final String SCHEMA_REGISTRY_URL;

    static {
        // Start in order: Kafka first, then Schema Registry.
        KAFKA.start();
        SCHEMA_REGISTRY.start();
        SCHEMA_REGISTRY_URL = "http://" + SCHEMA_REGISTRY.getHost() + ":" + SCHEMA_REGISTRY.getMappedPort(8081);
    }
}
