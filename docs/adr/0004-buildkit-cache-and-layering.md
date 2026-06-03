# ADR-0004: Use BuildKit cache + layered multi-stage builds

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [ADR-0001 — Pilot approach](0001-pilot-not-rollout.md) · [ADR-0003 — Compose role](0003-reduce-compose-in-ci.md) · [ADR-0005 — CI runner mode](0005-ci-runner-docker-mode.md) · [Story 2](../stories/story-2-build/README.md) · [tech notes](../stories/tech-notes.md#buildkit-remote-cache)

## Context

The pilot repository's Dockerfile currently follows a pattern that causes unnecessary rebuilds:

1. **Single-stage or unordered COPY:** source code and dependency metadata are copied together, so any source change invalidates the dependency-download layer. Maven downloads ~200 MB of dependencies on every build.
2. **No cache mounts:** even locally, there's no persistent Maven cache between builds — each `docker build` re-downloads the `.m2` repository.
3. **Large runtime image:** the same image that builds the application is shipped to production, including the JDK, Maven, build tools, and intermediate artefacts — resulting in ~450 MB images.
4. **No remote cache:** GitLab CI runners are ephemeral. Without registry-backed cache, every CI build starts cold — no layer reuse from previous runs.

The consequence: builds are slow (~5 min CI), images are unnecessarily large, and the team waits for dependency downloads that haven't changed.

BuildKit (Docker's modern build backend) supports:
- **Cache mounts** (`--mount=type=cache`): persist the Maven repository across local builds without baking it into a layer.
- **Multi-stage builds:** separate "resolve dependencies" → "compile" → "runtime" into distinct stages. Only the final runtime stage ships to production.
- **Registry remote cache** (`--cache-from`/`--cache-to`): store cache layers in the container registry so CI runners can reuse them across jobs (requires platform/ETO infrastructure).

## Decision

We will restructure the pilot Dockerfile using BuildKit features:

1. **Multi-stage build** with three stages:
   - `deps` stage: copy only `pom.xml` / `.mvn` / `mvnw`, resolve dependencies with a cache mount. This layer only rebuilds when dependency metadata changes.
   - `build` stage: copy source, compile the application. Only rebuilds when source changes.
   - `runtime` stage: JRE-only base image + the built JAR. No JDK, no Maven, no source — minimal attack surface and image size.

2. **BuildKit cache mounts** for the Maven local repository (`/root/.m2`), enabled for local builds immediately.

3. **Registry remote cache** (branch-aware: `--cache-from` main + current branch): documented and templated in `.gitlab-ci.yml`, but **commented out** until platform/ETO provisions the cache namespace. This is a post-pilot item (see [FUTURE-CONSIDERATIONS](../stories/FUTURE-CONSIDERATIONS.md)).

4. **Clean build must always work:** a `--no-cache` build must succeed, so cache is an optimisation, never a hard dependency (guards risk R6).

Target Dockerfile pattern:
```dockerfile
# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy AS deps
WORKDIR /app
COPY pom.xml .mvn mvnw ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B dependency:go-offline

FROM deps AS build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B package -DskipTests

FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
USER 1001
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

## Consequences

- **Positive:**
  - Faster rebuilds: dependency layer cached separately — source-only changes rebuild in seconds locally.
  - Smaller runtime image: JRE-only, no build tools → target < 380 MB (from ~450 MB).
  - Predictable CI build time: once remote cache is available, branch builds reuse the main branch cache.
  - Improved security posture: runtime image has reduced attack surface (no compiler, no Maven).

- **Negative / trade-offs:**
  - Registry remote cache requires platform/ETO infrastructure (storage, permissions, retention policy) — deferred to post-pilot.
  - Multi-stage Dockerfiles are slightly more complex to read for developers unfamiliar with the pattern.
  - Cache mounts are BuildKit-specific — if BuildKit is disabled, the Dockerfile still works but without the cache benefit.
  - Risk R6: a corrupt or stale cache could theoretically produce an incorrect image — mitigated by the "clean build must work" rule and T2.4 verification.

- **Follow-ups:**
  - T2.3: apply one layering change at a time and measure (not a full rewrite at once).
  - T2.4: compare before/after locally and in CI; verify a `--no-cache` build still succeeds.
  - Story 5 / T5.2: route remote-cache infra requirement to platform/ETO.
  - Post-pilot: platform/ETO provisions cache namespace; uncomment `--cache-from`/`--cache-to` in `.gitlab-ci.yml`.

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Single-stage, copy-all Dockerfile (status quo) | Simple; everyone understands it | No layer caching; large images; every change triggers full rebuild | This is the current pain point |
| Cache mounts only, no multi-stage | Partial speedup for dep downloads | Runtime image still ships JDK + build tools; no size reduction | Leaves the image-size problem unsolved |
| Pre-built dependency image (build deps baked into a base) | Very fast builds; no dep resolution at all | Governance overhead; must rebuild when deps change; another image to maintain | Heavier than pilot scope — possible future platform item |
| Kaniko (daemonless build) | No Docker daemon needed in CI | Less mature BuildKit features; no cache mounts; limited multi-stage support | BuildKit is the standard; Kaniko is a workaround for environments without Docker |
