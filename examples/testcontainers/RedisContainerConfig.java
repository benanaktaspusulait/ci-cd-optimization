package com.example.pilot.integration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcontainers configuration for Redis.
 *
 * This is the simplest possible Testcontainers example — a single container,
 * one exposed port, no special configuration needed.
 *
 * Why Redis as a first pilot candidate (T3.1):
 * - Already used by integration tests (existing docker-compose service).
 * - Simple — no multi-node setup, no authentication in test mode.
 * - Fast to start (~2 seconds).
 * - Provides immediate value: if this works, the pattern is proven.
 *
 * How it works:
 * - The container is declared static — it starts once and is shared across all tests.
 * - Testcontainers assigns a random host port mapped to container port 6379.
 * - CucumberSpringConfig reads the dynamic port and injects it into Spring properties.
 * - The application connects to Redis using the injected host:port — no hardcoded ports.
 *
 * Reuse policy (ADR-0002):
 * - Local: reuse enabled via .testcontainers.properties → faster feedback loops.
 * - CI: reuse disabled (default) → clean, deterministic environment per pipeline run.
 *
 * Related: ADR-0002, CucumberSpringConfig.java, T3.2
 */
public class RedisContainerConfig {

    // Pin the version — never use :latest in tests. This ensures deterministic behaviour.
    private static final DockerImageName REDIS_IMAGE = DockerImageName.parse("redis:7.2-alpine");

    /**
     * Shared Redis container — started once, reused across all scenarios.
     * Testcontainers Ryuk will clean it up when the JVM exits.
     */
    public static final GenericContainer<?> REDIS = new GenericContainer<>(REDIS_IMAGE)
            .withExposedPorts(6379)
            .withCommand("redis-server", "--maxmemory", "64mb", "--maxmemory-policy", "allkeys-lru");

    static {
        REDIS.start();  // Start eagerly so it's ready before Spring context initialises.
    }
}
