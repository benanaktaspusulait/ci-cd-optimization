package uk.gov.ho.dacc.fdp.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Entry point for Cucumber integration tests running with Testcontainers.
 *
 * NOTE: The existing FDP project uses cucumber-junit (JUnit 4 @RunWith style).
 * This is maintained here for compatibility with the existing test infrastructure.
 * Migration to JUnit 5 Platform Suite (@Suite) is a separate, optional step.
 *
 * How to run:
 *   ./mvnw verify -pl cmd-adaptor-dvla-integration-tests -P testcontainers
 *
 * What happens:
 * 1. Maven Failsafe picks up this class (matches *E2ETest / *IT pattern)
 * 2. @RunWith(Cucumber.class) launches the Cucumber engine
 * 3. Cucumber scans 'features' on the classpath for .feature files
 * 4. Step definitions in 'uk.gov.ho.dacc.fdp.integration' are loaded via Spring
 * 5. CucumberSpringConfig starts Testcontainers (Kafka, Redis, SchemaRegistry)
 * 6. @DynamicPropertySource injects dynamic ports into Spring properties
 * 7. Tests run against isolated, deterministic containers
 * 8. Containers are cleaned up by Testcontainers/Ryuk in local runs
 *    (Drone may disable Ryuk and rely on ephemeral pod cleanup)
 *
 * Differences from existing RunCucumberIntegrationTest:
 * - No docker-compose-maven-plugin needed (skip.containers=true in profile)
 * - No pre-integration-test wait container
 * - No fixed ports (9092, 8081, 6379) — all dynamic
 * - Same Cucumber features, same step definitions, different infrastructure
 *
 * Related: ADR-0002, CucumberSpringConfig.java, Story 4
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "uk.gov.ho.dacc.fdp.integration",
        plugin = {
                "pretty",
                "json:target/cucumber-report.json",
                "junit:target/cucumber-junit-report.xml"
        },
        tags = "not @snapshot"  // Same exclusion as ci-cmd profile
)
public class TestcontainersBaseIT {
    // No body — Cucumber discovers features and steps via annotations above.
}
