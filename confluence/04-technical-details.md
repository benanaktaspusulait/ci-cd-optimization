# Technical Details

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

> This page contains deep technical content for engineers. Non-technical readers should refer to the [parent overview](00-parent-overview.md) and [proposal matrix](01-proposal-matrix.md) instead.

---

## 1. Dockerfile Optimisation

### Current state

The existing Dockerfile is single-stage:
- Base image: `amazoncorretto:17`
- Copies pre-built JAR + OpenTelemetry agent
- Includes `yum install` for system packages + envconsul (Vault integration)
- Ships JDK + build tools in the runtime image (~450 MB)
- No layer separation between dependencies and source

### Proposed multi-stage approach

```dockerfile
# Stage 1: Resolve dependencies (cached separately from source)
FROM amazoncorretto:17 AS deps
WORKDIR /app
COPY pom.xml cmd-adaptor-dvla/pom.xml cmd-adaptor-dvla-common/pom.xml ./
COPY .mvn mvnw ./
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B dependency:go-offline -pl cmd-adaptor-dvla -am -DskipTests

# Stage 2: Build (only rebuilds on source change)
FROM deps AS build
COPY cmd-adaptor-dvla/src cmd-adaptor-dvla/src
COPY cmd-adaptor-dvla-common/src cmd-adaptor-dvla-common/src
RUN --mount=type=cache,target=/root/.m2/repository \
    ./mvnw -B package -pl cmd-adaptor-dvla -am -DskipTests

# Stage 3: Runtime (JDK removed — smaller, more secure)
FROM amazoncorretto:17 AS runtime
# ... envconsul + non-root user + JAR only
```

### Expected gains

| Metric | Before | After (estimated) |
|--------|--------|-------------------|
| Image size | ~450 MB | ~300 MB (JDK + Maven removed from runtime) |
| Local rebuild (source change only) | ~5 min | ~1.5 min (deps layer cached) |
| Build context | ~200 MB | ~50 MB (.dockerignore excludes .git, target, docs) |

---

## 2. .dockerignore

### Proposed content

```gitignore
.git/
.gitignore
.idea/
*.iml
target/
build/
out/
docs/
*.md
*.log
scripts/
docker-compose*.yml
src/test/
```

This keeps only files needed for the build in the Docker context.

---

## 3. Testcontainers

### Approach

Replace one Docker Compose dependency (Redis recommended) with Testcontainers in integration tests. The existing Cucumber + JUnit 4 (vintage) test structure is preserved.

### Key components

| Component | Role |
|-----------|------|
| `RedisContainerConfig.java` | Starts Redis 5.0.6 container, exposes dynamic port |
| `KafkaContainerConfig.java` | Starts Zookeeper + Kafka (cp-7.5.5) + Schema Registry with shared network |
| `CucumberSpringConfig.java` | Bridges Cucumber ↔ Spring Boot ↔ Testcontainers via `@DynamicPropertySource` |
| `TestcontainersBaseIT.java` | Cucumber runner (`@RunWith(Cucumber.class)`) with `@CucumberOptions` |

### FDP-specific properties wired

```java
@DynamicPropertySource
static void kafkaProperties(DynamicPropertyRegistry registry) {
    registry.add("fdp.kafka.broker", () -> KafkaContainerConfig.BOOTSTRAP_SERVERS);
    registry.add("fdp.kafka.schema-registry-url", () -> KafkaContainerConfig.SCHEMA_REGISTRY_URL);
    registry.add("fdp.app.kafka.stream.replication-factor", () -> "1");
    registry.add("fdp.app.kafka.topic.suffix", () -> "0");
}
```

### Maven dependencies to add

Only 3 new dependencies (Testcontainers BOM + core + kafka module). All Cucumber/Spring/JUnit dependencies already exist.

### Reuse policy

- **Local:** reuse enabled (`testcontainers.reuse.enable=true`) for faster iteration.
- **CI:** reuse disabled — clean containers per pipeline run (deterministic).

### Maven profile

```xml
<profile>
    <id>testcontainers</id>
    <properties>
        <skip.containers>true</skip.containers>    <!-- skip docker-compose-maven-plugin -->
        <skip.aggregators>true</skip.aggregators>
        <skip.integration.tests>false</skip.integration.tests>
    </properties>
</profile>
```

Usage: `./mvnw verify -pl cmd-adaptor-dvla-integration-tests -P testcontainers`

---

## 4. BuildKit Cache

### Local cache mounts

`--mount=type=cache,target=/root/.m2/repository` persists Maven downloads across local builds. Immediate benefit, no infrastructure needed.

### Remote cache (post-pilot, ACP dependent)

```bash
docker buildx build \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:main \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:$BRANCH \
  --cache-to=type=registry,ref=$REGISTRY_IMAGE/cache:$BRANCH,mode=max \
  --tag $REGISTRY_IMAGE:$COMMIT_SHA --push .
```

**Not implementable without ACP:** requires registry namespace, write permissions, DIND BuildKit support, and RepoSync `.drone.star` change.

---

## 5. Docker Compose — Current CI Usage

The integration test docker-compose starts:

| Service | Image | Purpose | CI-required? |
|---------|-------|---------|:------------:|
| zookeeper | cp-zookeeper:7.5.5 | Kafka dependency | Yes |
| kafka | cp-kafka:7.5.5 | Event streaming | Yes |
| schema-registry | cp-schema-registry:7.5.5 | Avro schema management | Yes |
| redis | redis:5.0.6 | Cache / state store | Yes |
| localstack | localstack:0.12.18 | IAM emulation | Maybe |
| jaeger | jaegertracing/all-in-one:1.65.0 | Trace UI | No (local debug) |
| kafdrop | obsidiandynamics/kafdrop:3.30.0 | Kafka UI | No (local debug) |
| aggregate-party/object/location/event/service/matching/v1id-v2id | Internal FDP images | Stream processors | For snapshot tests only |
| command-adaptor | Built from source | The service under test | Yes |

Story 5 maps and classifies these to determine what can be removed from CI.

---

## 6. Security

### Secret management

| Concern | Approach |
|---------|----------|
| CI secrets (registry creds, tokens) | **Drone secrets** (per-repo or org-level) — encrypted, injected at runtime |
| Build-time secrets (Maven `settings.xml`) | **BuildKit secret mounts** (`--mount=type=secret`) — never baked into layers |
| App runtime secrets | **HashiCorp Vault** via envconsul — out of pilot scope to implement, in scope to document |
| Preventing leaks | `.dockerignore` excludes `.env`, key files; secret scanning in CI |

**Rules:** No secrets in image layers, build args, logs, or repo. No real credentials in examples. Rotate any suspected leak.

### Scanning policy

| What | Tool (candidate) | When | Pilot mode | Target gate |
|------|------------------|------|------------|-------------|
| Image vulnerabilities | Trivy or Snyk | Every pilot build | Report-only | Fail on Critical |
| Dependency vulnerabilities | Trivy / `mvn` audit | On MR + weekly | Report-only | Fail on Critical |
| Secret scanning | gitleaks / trufflehog | On MR | Report-only | Fail on any secret |
| SBOM generation | Syft (SPDX/CycloneDX) | On image build | Artefact attached | Required |
| Base image freshness | Scheduled scan | Weekly | Report-only | Flag outdated/EOL |

Tool choice is CST-local for the pilot. Org-wide scanning standard/gate is ACP/ETO (classify in Story 6).

### Policy as code

| Policy | Rule | Enforcement |
|--------|------|-------------|
| No `root` runtime | Non-root USER in Dockerfile | hadolint + image policy check |
| No unpinned images | Version (or digest for critical) pinned | hadolint + CI lint |
| No secrets in image | No secret material in layers | Secret scan of built image |
| Healthcheck present | Long-running images define HEALTHCHECK | hadolint |
| Approved base images | Use sanctioned images only | Policy check against allowlist (ACP/ETO) |

Enforcement: start with hadolint (fast, local + CI). OPA/Conftest for admission policies. Pilot runs in **warn** mode; promote to **block** after baseline.

### Supply-chain hardening (ACP/ETO, post-pilot)

- Digest pinning for critical base images.
- Image signing/provenance (cosign) — assess feasibility.
- Scheduled base-image rebuilds.
- Deprecated-image policy.

---

## 7. Code Examples

Working examples exist in the repository under `examples/`:
- `examples/testcontainers/` — RedisContainerConfig, KafkaContainerConfig, CucumberSpringConfig, pom-dependencies
- `examples/docker/` — optimised Dockerfile, .dockerignore, docker-compose
- `examples/ci/` — Drone considerations, GitLab CI illustrative snippet

---

*Feedback or questions? Contact the page owner or comment below.*
