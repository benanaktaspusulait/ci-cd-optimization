# Pipeline & Drone Context — Validated State

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Repository validation complete; RepoSync common implementation pending |
| **Last updated** | 2026-09-17 |

## Pipeline Boundary

This work targets the **CI pipeline** for command-adaptor repositories. The separate deploy/CD pipeline remains outside this optimisation scope.

The important ownership constraint remains: `.drone.star` is generated/managed through RepoSync. Repository-local pipeline edits are useful for validation but are not a durable rollout mechanism if RepoSync would overwrite them.

## Before — Compose-Heavy CI

Simplified historical shape:

```text
Retrieve secrets
   |
Wait for Docker
   |
Kafka & Redis ---- Aggregators
   |                    |
Maven build        Command Adaptor
   |                    |
   +-------> Pre-Integration
                 |
          Integration Tests
                 |
              Trivy
```

Lifecycle ownership was spread across Drone steps, Compose services, helper containers and Maven.

## Validated After — Consolidated Test-Owned Lifecycle

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

Correctness gates remain sequential where required: business/test validation → image build → exact-image runtime validation → final scan.

Independent work such as Docker readiness, adaptor metadata extraction and Trivy DB preparation can overlap.

## What Changed in Ownership

### Test code owns

- ephemeral integration infrastructure;
- dynamic endpoints/topics;
- application startup for in-JVM integration scenarios;
- readiness and diagnostics;
- deterministic shutdown.

### CI pipeline owns

- Docker availability;
- Maven/Testcontainers execution;
- image build/cache policy;
- exact built-image runtime validation;
- final Trivy scan;
- dependency ordering between those gates.

### RepoSync must own durably

The common pipeline pieces that are currently validated in repository branches but centrally generated in normal operation. These are the reason the reviewed SNS/PNR/PCDP changes have not simply been merged as permanent local copies.

## Current Delivery Status

SNS, PNR and PCDP implementations are technically reviewed/accepted. Merge is deferred while common RepoSync-managed elements are extracted and implemented through the shared source.

The next step is therefore **not** another per-repository optimisation pass. It is to make the validated common pipeline pattern durable through RepoSync, then verify representative repositories again.

## CI Docker Settings

The validated Testcontainers path uses the existing DIND service rather than a new runner model. Representative common settings:

```text
DOCKER_HOST=tcp://docker:2375
DOCKER_API_VERSION=1.41
TESTCONTAINERS_HOST_OVERRIDE=docker
TESTCONTAINERS_RYUK_DISABLED=true
```

Private registry authentication must be available before Testcontainers resolves internal aggregate images.

## Trivy

The existing policy remains intact. The optimisation changes **when** vulnerability/Java DB preparation happens, not the severity/exit-code policy.

The final scan still runs after exact-image validation and uses the prepared cache directory. Security policy changes, if desired, are separate work.

## MR / Branch Use During Validation

Repository branches remain appropriate for proving the pattern and obtaining technical review. They are not the final ownership location for centrally generated pipeline logic. After cross-repository validation, shared elements should be moved to the durable RepoSync source and repository-specific elements retained locally.

