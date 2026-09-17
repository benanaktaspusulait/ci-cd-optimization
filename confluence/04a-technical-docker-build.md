# Technical Details — Docker Build & Infrastructure

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Validated pattern; RepoSync centralisation pending |
| **Last updated** | 2026-09-17 |

## Final Direction

The validated work did **not** standardise on a new multi-stage application build. The adaptor pipeline already builds the executable JAR before Docker packaging. The useful optimisation was therefore to keep expensive, stable runtime setup ahead of volatile application artefacts and to minimise the build context.

This distinction matters: the final pattern is based on the actual build architecture, not the original pilot assumption that every adaptor should compile inside a multi-stage Docker build.

## Docker Context

The application-level `.dockerignore` should expose only what the Dockerfile consumes, typically:

- the Dockerfile;
- the packaged executable JAR;
- the OpenTelemetry Java agent and any other explicitly required runtime artefact.

Do not copy source, test resources, repository metadata or unrelated build outputs merely because they exist in the module.

SNS measured the original context at approximately **191.27 MB**. A controlled BuildKit/content-store observation after the targeted ignore rules showed **189 B** of context metadata for the unchanged required inputs. This is not presented as a universal CI network-transfer saving; context transfer depends on builder/content-store state.

## Layer Ordering

Stable and expensive runtime setup should be completed before the application JAR is copied.

Typical structure:

```dockerfile
FROM amazoncorretto:17

WORKDIR /tmp

RUN <install/update runtime packages and create runtime user>

# Volatile application artefacts come after stable setup
COPY ./target/<adaptor>-exec.jar /local/<adaptor>-exec.jar
COPY ./target/dependencies/opentelemetry-javaagent.jar /local/opentelemetry-javaagent.jar

RUN <permissions/ownership required for copied artefacts>

USER fdpuser
```

In the SNS controlled same-daemon real-JAR-change experiment, the old/current ordering was approximately **75.82–77.90s** and the improved ordering approximately **4.62–5.08s**. This is a warm-cache controlled Docker measurement, not a cold-build or full-CI claim.

## BuildKit / Registry Cache

Validated principles:

- prefer the existing/default BuildKit builder where it performs adequately;
- read shared stable cache where appropriate;
- avoid independent work overwriting a common cache without ownership/isolation;
- keep a fallback path where buildx is unavailable;
- measure the target pipeline rather than assuming cache benefit.

A custom docker-container builder was slower than the default builder in the SNS comparison. The observed approximately **18–20s** difference is a builder-path comparison; it must not be labelled as the isolated benefit of registry caching.

## Runtime Image

No image-size reduction is claimed as a core result of this work. The runtime base and required runtime tools were preserved unless a separate requirement justified changing them.

`envconsul` remains part of the runtime where required. The validated SNS path used a fixed version with checksum verification and fail-fast download behaviour; this is security/correctness hardening, not a performance claim.

## Build Validation

Every Docker optimisation must retain:

- clean build success;
- required runtime artefacts;
- non-root/runtime ownership behaviour already expected by the application;
- application startup/readiness;
- exact-image validation after the CI image is built.

## Reusable vs Repository-Specific

### Reusable

- strict Docker context;
- stable-before-volatile layer ordering;
- default-builder-first strategy;
- registry-cache reuse when supported;
- no performance claim without measurement;
- exact-image validation.

### Repository-specific

- JAR/agent paths;
- base image;
- envconsul/runtime package requirements;
- registry/cache refs;
- health endpoint and exposed ports.

