# Code Examples and Templates — Validated Patterns

| Field | Value |
|---|---|
| **Status** | Reference patterns derived from SNS/PNR/PCDP |
| **Last updated** | 2026-09-21 |

> These are patterns, not copy/paste contracts. Topic names, feature counts, aggregate sets, image references, health paths and application properties must be adapted per repository.

## 1. CI Dependency Graph Pattern

```text
Retrieve secrets
   |-----------------------> Prepare Trivy DB --------------------+
   |-----------------------> Extract adaptor information ----+    |
   +--> Wait for Docker ------------------------------------+|    |
                                                            vv    |
                                             Build/Test with Testcontainers
                                                        |
                                                   Build image
                                                        |
                                             Validate built image runtime
                                                        |
                                                        +----------+
                                                                   |
                                                               Trivy scan
```

Do not copy exact step names blindly; preserve the dependency intent.

## 2. Drone / Testcontainers Environment Pattern

```text
DOCKER_HOST=tcp://docker:2375
DOCKER_API_VERSION=1.41
TESTCONTAINERS_HOST_OVERRIDE=docker
TESTCONTAINERS_RYUK_DISABLED=true
```

Private registry authentication must be prepared before internal test images are resolved.

## 3. Maven Local Repository Reuse

```sh
export MAVEN_REPO_LOCAL="$PWD/.m2/repository"
mkdir -p "$MAVEN_REPO_LOCAL"
mvn -ntp -Dmaven.repo.local="$MAVEN_REPO_LOCAL" clean verify <profiles>
```

A later focused runtime-smoke Maven invocation can reuse the already populated repository/reactor outputs rather than rebuilding upstream modules.

## 4. Docker Context Pattern

```gitignore
**
!Dockerfile
!target/
target/*
!target/<adaptor>-exec.jar
!target/dependencies/
target/dependencies/*
!target/dependencies/opentelemetry-javaagent.jar
```

Adapt to the real Dockerfile inputs. Do not hide a required script/certificate/config file.

## 5. Docker Layer-Ordering Pattern

```dockerfile
FROM amazoncorretto:17

WORKDIR /tmp
RUN <expensive stable runtime setup>

COPY ./target/<adaptor>-exec.jar /local/<adaptor>-exec.jar
COPY ./target/dependencies/opentelemetry-javaagent.jar /local/opentelemetry-javaagent.jar
RUN <ownership/permissions for copied artefacts>

USER fdpuser
```

The principle is stable-before-volatile. A multi-stage build is not mandatory unless the repository actually benefits from building inside Docker.

## 6. Testcontainers Environment Pattern

The environment class should own:

- network;
- Redis/Kafka/Schema Registry lifecycle;
- required aggregate lifecycle;
- dynamic topic suffix;
- topic creation;
- application start/stop where the suite runs in-JVM;
- readiness checks;
- diagnostics;
- bounded shutdown.

Pseudo-structure:

```java
public final class AdaptorTestcontainersEnvironment {
    public static void requireDockerForMandatoryCi() { ... }
    public static void startInfrastructure() { ... }
    public static void startApplication() { ... }
    public static String kafkaBootstrapServers() { ... }
    public static String schemaRegistryUrl() { ... }
    public static String topicSuffix() { ... }
    public static void shutdown() { ... }
}
```

## 7. Feature / Scenario Inventory Guard

The guard should protect the repository's actual business suite rather than a copied SNS number.

Examples of current evidence:

- SNS: 7 feature files / 14 business scenarios.
- PNR: 15 feature files / 15 declared non-ignored / 21 expanded executable cases.
- PCDP: 131 feature files / 480 declared / 1382 executable; snapshot 125 declared / 229 executable.

For Scenario Outlines, count expanded Examples rows where that is what the runtime actually executes.

## 8. Mandatory Docker / Zero-Test Guard

Mandatory CI must fail rather than silently skip when Docker is unavailable or no target tests are discovered.

Use Maven/Failsafe settings and runner guards appropriate to the repository. Local developer profiles may be more permissive, but the CI profile must not inherit the permissive behaviour.

## 9. Exact Built-Image Runtime Validation

The runtime test should:

1. receive the exact image reference/ID produced by the image-build step;
2. start it on compatible test infrastructure;
3. configure required broker/registry/topic settings;
4. wait for application readiness with a bounded deadline;
5. dump useful diagnostics on failure;
6. stop resources deterministically.

Do not substitute an older registry image or a separately rebuilt image.

## 10. Trivy Critical-Path Pattern

Early preparation:

```sh
trivy image --download-db-only --no-progress --cache-dir "$TRIVY_CACHE_DIR" <approved-db-options>
trivy image --download-java-db-only --no-progress --cache-dir "$TRIVY_CACHE_DIR" <approved-java-db-options>
```

Final scan:

```sh
trivy image --exit-code 0 --no-progress --cache-dir "$TRIVY_CACHE_DIR" \
  <built-image> --severity CRITICAL,HIGH --ignore-unfixed <approved-db-options>
```

The exact repositories/options remain environment-specific. The optimisation is the dependency placement/cache reuse, not a change to the security policy.

## 11. Do Not Reintroduce Rejected Experiments by Default

Do not standardise any of the following without fresh evidence in the target repository:

- Maven parallelism;
- broad image prefetch;
- readiness-result caching;
- broad logging suppression solely for performance;
- hard performance thresholds that turn runner variance into false failures.

## 12. RepoSync Adoption Rule

Common `.drone.star` logic belongs in RepoSync after cross-repository validation. Repository-specific code stays with the repository. The reference implementation should not become a permanent local fork of centrally managed pipeline logic.

