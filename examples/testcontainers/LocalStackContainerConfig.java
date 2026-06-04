package uk.gov.ho.dacc.fdp.integration;

import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.IAM;

/**
 * Testcontainers configuration for LocalStack.
 *
 * Matches the real FDP docker-compose: localstack/localstack:0.12.18
 * Currently only IAM is enabled in the real setup (LOCALSTACK_SERVICES=iam).
 *
 * NOTE: The FDP docker-compose uses a very old LocalStack version (0.12.18).
 * Testcontainers' LocalStackContainer module works best with LocalStack 1.x+.
 * For the pilot, two options:
 *   1. Use GenericContainer with the exact old image (0.12.18) — matches prod exactly
 *   2. Use LocalStackContainer with a newer version — better API, same IAM behaviour
 *
 * This example shows option 2 (recommended for the pilot) with a note about option 1.
 *
 * Related: ADR-0002, T4.1 (if LocalStack is chosen as the Testcontainers candidate)
 */
public class LocalStackContainerConfig {

    // Option 2: Use a modern LocalStack version (better Testcontainers integration)
    private static final DockerImageName LOCALSTACK_IMAGE =
            DockerImageName.parse("localstack/localstack:3.5");

    public static final LocalStackContainer LOCALSTACK = new LocalStackContainer(LOCALSTACK_IMAGE)
            .withServices(IAM);

    static {
        LOCALSTACK.start();
    }

    /**
     * Returns the LocalStack endpoint URL for AWS SDK client configuration.
     */
    public static String getEndpoint() {
        return LOCALSTACK.getEndpoint().toString();
    }

    /*
     * Option 1 (exact version match with production):
     * If the team requires exact version parity, use GenericContainer instead:
     *
     *   private static final GenericContainer<?> LOCALSTACK = new GenericContainer<>(
     *       DockerImageName.parse("localstack/localstack:0.12.18"))
     *       .withExposedPorts(4566)
     *       .withEnv("LOCALSTACK_SERVICES", "iam")
     *       .withEnv("LOCALSTACK_DEBUG", "1")
     *       .waitingFor(Wait.forHttp("/health").forStatusCode(200));
     *
     * Then access via: "http://" + LOCALSTACK.getHost() + ":" + LOCALSTACK.getMappedPort(4566)
     */
}
