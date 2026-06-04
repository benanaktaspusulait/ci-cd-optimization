# Optimised Dockerfile for FDP Command Adaptor (Spring Boot / Maven)
#
# CURRENT STATE (from live repo):
#   - Single stage: amazoncorretto:17
#   - yum install + update in one RUN
#   - Copies pre-built JAR + OpenTelemetry agent
#   - No layer separation for dependencies
#   - Includes envconsul (HashiCorp Vault integration)
#   - Runs as non-root (fdpuser) ✓
#
# OPTIMISED VERSION (below):
#   - Multi-stage: separate dependency resolution from build from runtime
#   - BuildKit cache mounts for Maven local repo
#   - Smaller runtime payload: Maven, source and build artefacts stay out of the final image
#   - Dependencies cached independently of source changes
#   - OpenTelemetry agent downloaded once, cached in its own layer
#   - Envconsul stays (required for Vault secret injection at runtime)
#
# Reference: ADR-0004, Story 3, T3.3
#
# ══════════════════════════════════════════════════════════════════════════════
# Stage 1: Resolve dependencies
# ══════════════════════════════════════════════════════════════════════════════
FROM amazoncorretto:17 AS deps

WORKDIR /app

# Copy only dependency metadata — this layer rebuilds ONLY when pom.xml changes.
COPY pom.xml ./
COPY cmd-adaptor-dvla/pom.xml cmd-adaptor-dvla/
COPY cmd-adaptor-dvla-common/pom.xml cmd-adaptor-dvla-common/
COPY cmd-adaptor-dvla-test-common/pom.xml cmd-adaptor-dvla-test-common/
COPY cmd-adaptor-dvla-integration-tests/pom.xml cmd-adaptor-dvla-integration-tests/

# Maven wrapper
COPY .mvn .mvn
COPY mvnw ./
RUN chmod +x mvnw

# Resolve all dependencies. Cache mount keeps .m2 across builds.
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B dependency:go-offline -pl cmd-adaptor-dvla -am -DskipTests

# ══════════════════════════════════════════════════════════════════════════════
# Stage 2: Build the application
# ══════════════════════════════════════════════════════════════════════════════
FROM deps AS build

# Copy source code (only now — after deps are resolved).
COPY cmd-adaptor-dvla/src cmd-adaptor-dvla/src
COPY cmd-adaptor-dvla-common/src cmd-adaptor-dvla-common/src

# Build the application JAR. Tests skipped — they run in a separate CI stage.
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B package -pl cmd-adaptor-dvla -am -DskipTests \
    && cp cmd-adaptor-dvla/target/cmd-adaptor-dvla-exec.jar /app/app.jar

# Download OpenTelemetry agent (pinned version from fdp-bom).
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B dependency:copy \
      -Dartifact=io.opentelemetry.javaagent:opentelemetry-javaagent:1.30.0:jar \
      -DoutputDirectory=/app/agent

# ══════════════════════════════════════════════════════════════════════════════
# Stage 3: Runtime image
# ══════════════════════════════════════════════════════════════════════════════
FROM amazoncorretto:17 AS runtime

# Install envconsul (HashiCorp Vault integration — required for secret injection)
RUN yum install -y shadow-utils unzip \
    && curl --silent --output /tmp/envconsul.zip \
       https://releases.hashicorp.com/envconsul/0.13.1/envconsul_0.13.1_linux_amd64.zip \
    && unzip /tmp/envconsul.zip -d /usr/local/bin/ \
    && rm -f /tmp/envconsul.zip \
    && chmod 0755 /usr/local/bin/envconsul \
    && yum clean all && rm -rf /var/cache/yum

# Create non-root user
RUN adduser -u 1000 -U -m -s /bin/bash fdpuser

WORKDIR /home/fdpuser

# Copy built JAR and OTel agent from build stage
COPY --from=build --chown=fdpuser:fdpuser /app/app.jar ./cmd-adaptor-dvla-exec.jar
COPY --from=build --chown=fdpuser:fdpuser /app/agent/opentelemetry-javaagent-1.30.0.jar ./opentelemetry-javaagent.jar

USER fdpuser

EXPOSE 7112 8077

HEALTHCHECK --interval=30s --timeout=5s --start-period=15s --retries=3 \
    CMD ["sh", "-c", "curl -sf http://localhost:7112/actuator/health || exit 1"]

CMD ["java", \
     "-javaagent:/home/fdpuser/opentelemetry-javaagent.jar", \
     "-Dcom.sun.management.jmxremote", \
     "-Dcom.sun.management.jmxremote.port=9012", \
     "-Dcom.sun.management.jmxremote.rmi.port=9012", \
     "-Dcom.sun.management.jmxremote.authenticate=false", \
     "-Dcom.sun.management.jmxremote.local.only=false", \
     "-Dcom.sun.management.jmxremote.ssl=false", \
     "-Djava.rmi.server.hostname=localhost", \
     "-jar", "/home/fdpuser/cmd-adaptor-dvla-exec.jar"]
