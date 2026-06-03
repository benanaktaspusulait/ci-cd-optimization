package com.example.pilot.integration;

import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.*;

/**
 * Testcontainers configuration for LocalStack (AWS service emulation).
 *
 * LocalStack emulates AWS services locally — the application talks to it using the
 * real AWS SDK, just pointed at a different endpoint. This means integration tests
 * exercise the actual AWS SDK code paths without needing a real AWS account.
 *
 * Services enabled in this example:
 * - S3 (object storage)
 * - SQS (message queues)
 * - SNS (pub/sub notifications)
 * - DynamoDB (NoSQL database)
 *
 * How it works:
 * - LocalStackContainer starts a single container exposing all enabled services on one port (4566).
 * - The container provides endpoint override URLs and dummy credentials.
 * - Spring properties (or AWS SDK client config) are pointed at the container endpoint.
 * - Tests use the real SDK — same code as production, different endpoint.
 *
 * Why LocalStack over mocking:
 * - Mocking the SDK hides bugs in serialisation, pagination, error handling, and IAM logic.
 * - LocalStack exercises the real SDK + HTTP stack — catches issues mocking would miss.
 * - Startup is fast (~5 seconds for the services above).
 *
 * Related: ADR-0002, CucumberSpringConfig.java, T3.1, T3.2
 */
public class LocalStackContainerConfig {

    private static final DockerImageName LOCALSTACK_IMAGE =
            DockerImageName.parse("localstack/localstack:3.5");

    /**
     * LocalStack container with S3, SQS, SNS, and DynamoDB enabled.
     * Add more services as needed: KINESIS, LAMBDA, etc.
     */
    public static final LocalStackContainer LOCALSTACK = new LocalStackContainer(LOCALSTACK_IMAGE)
            .withServices(S3, SQS, SNS, DYNAMODB)
            .withEnv("DEFAULT_REGION", "eu-west-2");

    static {
        LOCALSTACK.start();
    }

    // ── Helper methods for creating AWS SDK clients pointed at LocalStack ────

    /**
     * Returns AWS credentials for LocalStack (dummy — LocalStack accepts anything).
     */
    public static StaticCredentialsProvider localStackCredentials() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                        LOCALSTACK.getAccessKey(),
                        LOCALSTACK.getSecretKey()
                )
        );
    }

    /**
     * Example: create an S3 client pointed at LocalStack.
     * Use this pattern for any AWS service client in tests.
     */
    public static S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(LOCALSTACK.getEndpointOverride(S3))
                .region(Region.EU_WEST_2)
                .credentialsProvider(localStackCredentials())
                .forcePathStyle(true)   // Required for LocalStack S3
                .build();
    }

    /**
     * Example: create an SQS client pointed at LocalStack.
     */
    public static SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(LOCALSTACK.getEndpointOverride(SQS))
                .region(Region.EU_WEST_2)
                .credentialsProvider(localStackCredentials())
                .build();
    }
}
