package uk.gov.ho.dacc.fdp.integration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcontainers configuration for Redis.
 *
 * Matches the real FDP docker-compose: redis:5.0.6
 * Used by: aggregate-party, aggregate-object, aggregate-location, aggregate-event,
 *          aggregate-service, aggregate-matching (FDP_APP_REDIS_NODES=redis:6379)
 *
 * Why Redis is the best first Testcontainers candidate (T3.1):
 * - Simplest dependency — single container, no multi-node setup
 * - Already used by multiple FDP services
 * - Fast to start (~2 seconds)
 * - No authentication in test mode
 * - Proves the Testcontainers pattern with minimal risk
 *
 * FDP-specific notes:
 * - The FDP apps connect to Redis via FDP_APP_REDIS_NODES environment variable
 * - In Testcontainers mode, Spring @DynamicPropertySource injects the dynamic host:port
 * - No change to application code needed — only test configuration
 *
 * Related: ADR-0002, CucumberSpringConfig.java, T3.1, T3.2
 */
public class RedisContainerConfig {

    // Pin to the same version as production docker-compose
    private static final DockerImageName REDIS_IMAGE = DockerImageName.parse("redis:5.0.6");

    /**
     * Shared Redis container — started once, reused across all scenarios.
     * Testcontainers Ryuk will clean it up when the JVM exits.
     */
    public static final GenericContainer<?> REDIS = new GenericContainer<>(REDIS_IMAGE)
            .withExposedPorts(6379);

    static {
        REDIS.start();
    }

    /**
     * Returns the connection string in the format FDP apps expect (host:port).
     * Use this for FDP_APP_REDIS_NODES or spring.data.redis.host/port injection.
     */
    public static String getRedisNodes() {
        return REDIS.getHost() + ":" + REDIS.getMappedPort(6379);
    }
}
