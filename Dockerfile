# syntax=docker/dockerfile:1
# Dockerfile — Baseline template for the CI/CD Optimisation Pilot
#
# BEFORE state (Story 1 baseline): replace with the actual pilot repo Dockerfile in T1.3.
# This file demonstrates the target pattern post-optimisation (Stories 2 & 3).
#
# Pattern: multi-stage build — separate dependency resolution from source compilation.
# References: ADR-0004, post-pilot (base image), docs/stories/tech-notes.md
#
# TODOs before use:
#   - Replace base image tags with org-approved images once post-pilot (base image) is implemented.
#   - Confirm Maven wrapper path (./mvnw) matches pilot repo.
#   - Adjust EXPOSE port to match the application.
#   - Confirm non-root user UID matches org policy (SECURITY.md).
#   - Add a HEALTHCHECK once the pilot app's health endpoint and runtime tooling are known.

# ── Stage 1: dependency resolution ──────────────────────────────────────────
# Copy only dependency metadata first so this layer is cached independently of source changes.
# Cache mount keeps the Maven local repo across builds (local only; remote cache = post-pilot (remote cache)).
FROM eclipse-temurin:17-jdk-jammy AS deps
# TODO: replace with org base-build image once post-pilot (base image) base images are provisioned.

WORKDIR /app

# Copy only files needed to resolve dependencies.
COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw ./

RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B dependency:go-offline -q

# ── Stage 2: build ────────────────────────────────────────────────────────────
FROM deps AS build

# Copy source only after dependencies are cached.
COPY src/ src/

RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B package -DskipTests -q

# ── Stage 3: runtime image ────────────────────────────────────────────────────
# Use a JRE-only image — no JDK, no build tools shipped to production.
FROM eclipse-temurin:17-jre-jammy AS runtime
# TODO: replace with org base-runtime image once post-pilot (base image) base images are provisioned.

# Run as a non-root user (SECURITY.md requirement).
RUN groupadd --gid 1001 appgroup && \
    useradd --uid 1001 --gid appgroup --shell /bin/sh --no-create-home appuser

WORKDIR /app

# Copy only the built artefact from the build stage.
COPY --from=build /app/target/*.jar app.jar

# Ensure the non-root user owns the artefact.
RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

# TODO: add a real application healthcheck here once the pilot service exposes one.

ENTRYPOINT ["java", "-jar", "app.jar"]
