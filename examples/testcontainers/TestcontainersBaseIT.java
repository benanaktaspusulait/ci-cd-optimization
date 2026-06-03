package com.example.pilot.integration;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Entry point for Cucumber integration tests running with Testcontainers.
 *
 * This class:
 * - Uses JUnit 5 Platform Suite to discover Cucumber features.
 * - Points Cucumber at the glue package where step definitions + Spring config live.
 * - Triggers Spring Boot test context (via CucumberSpringConfig) which starts Testcontainers.
 *
 * How it works:
 * 1. JUnit 5 picks up this @Suite class.
 * 2. Cucumber engine scans "features/" on the classpath for .feature files.
 * 3. Step definitions in the glue package are instantiated via Spring.
 * 4. CucumberSpringConfig starts Testcontainers before the first step runs.
 * 5. Each scenario gets an isolated, deterministic environment.
 *
 * Related: ADR-0002, Story 3, CucumberSpringConfig.java
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.pilot.integration")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-report.json")
public class TestcontainersBaseIT {
    // No body — configuration only. Cucumber discovers features and steps via annotations above.
}
