# syntax=docker/dockerfile:1
#
# Optimised multi-stage Dockerfile for a Spring Boot / Maven application.
# Reference: ADR-0004, Story 2, docs/stories/tech-notes.md
#
# Structure:
#   Stage 1 (deps)    — resolve Maven dependencies (cached separately from source)
#   Stage 2 (build)   — compile the application
#   Stage 3 (runtime) — minimal JRE-only image for production
#
# Key optimisations:
#   1. Dependency metadata (pom.xml) is copied BEFORE source code.
#      → Source changes don't invalidate the dependency layer.
#   2. BuildKit cache mount persists the Maven local repo across builds.
#      → Local rebuilds skip dependency downloads entirely.
#   3. Runtime stage uses JRE-only image — no JDK, no Maven, no source.
#      → Smaller image (~180 MB vs ~450 MB), reduced attack surface.
#   4. Non-root user in runtime stage (SECURITY.md compliance).
#
# Usage:
#   Local (with cache):
#     DOCKER_BUILDKIT=1 docker build -t my-app .
#
#   CI (without remote cache — until platform/ETO provisions it):
#     docker buildx build --tag $REGISTRY_IMAGE:$CI_COMMIT_SHA --push .
#
#   CI (with remote cache — post-pilot):
#     docker buildx build \
#       --cache-from=type=registry,ref=$CACHE_IMAGE:main \
#       --cache-from=type=registry,ref=$CACHE_IMAGE:$CI_COMMIT_REF_SLUG \
#       --cache-to=type=registry,ref=$CACHE_IMAGE:$CI_COMMIT_REF_SLUG,mode=max \
#       --tag $REGISTRY_IMAGE:$CI_COMMIT_SHA --push .

# ══════════════════════════════════════════════════════════════════════════════
# Stage 1: Resolve dependencies
# ══════════════════════════════════════════════════════════════════════════════
# Only pom.xml and Maven wrapper are copied here. This layer ONLY rebuilds
# when dependency metadata changes — not when you edit a .java file.
FROM eclipse-temurin:17-jdk-jammy AS deps

WORKDIR /app

# Copy dependency metadata only.
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
RUN chmod +x mvnw

# Resolve all dependencies (download to local repo).
# --mount=type=cache: persists /root/.m2 across builds (local only; lost in CI without remote cache).
# -B: batch mode (no interactive prompts, cleaner logs).
# dependency:go-offline: downloads everything without compiling.
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B dependency:go-offline -DskipTests

# ══════════════════════════════════════════════════════════════════════════════
# Stage 2: Compile the application
# ══════════════════════════════════════════════════════════════════════════════
# Source code is copied here. This layer rebuilds on source changes,
# but dependencies are already cached from Stage 1.
FROM deps AS build

# Copy source code (only now — after deps are resolved).
COPY src ./src

# Build the application JAR. Tests are skipped here — they run in a separate CI stage.
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B package -DskipTests

# ══════════════════════════════════════════════════════════════════════════════
# Stage 3: Runtime image
# ══════════════════════════════════════════════════════════════════════════════
# JRE-only — no JDK, no Maven, no source code, no build artefacts.
# This is what ships to production.
FROM eclipse-temurin:17-jre-jammy AS runtime

WORKDIR /app

# Copy only the built JAR from the build stage.
COPY --from=build /app/target/*.jar app.jar

# Run as non-root (SECURITY.md: "Container must run as non-root USER").
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

# Expose the application port (adjust to match your app's server.port).
EXPOSE 8080

# Health check for orchestrators (Docker, Kubernetes).
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD ["sh", "-c", "wget -qO- http://localhost:8080/actuator/health || exit 1"]

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
