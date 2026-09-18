# ADR-0004 & ADR-0005: Docker Build/Cache and CI Runner Decisions

| Field | Value |
|---|---|
| **Status** | Accepted / revised after implementation evidence |
| **Last updated** | 2026-09-18 |

## ADR-0004: Use Measured Docker Context, Layering and Cache Optimisation

### Context

The original ADR assumed that a multi-stage Dockerfile was the primary solution. The actual adaptor build flow produces the executable JAR before Docker packaging, so compiling again inside Docker would add complexity without necessarily improving the critical path.

### Decision

Use the simplest build structure that matches the real repository:

- minimise Docker context to actual image inputs;
- keep expensive stable runtime setup before volatile application artefacts;
- use the default BuildKit builder where appropriate;
- reuse registry-backed cache where supported;
- keep cache optional: clean builds must continue to work;
- measure each repository rather than enforcing a multi-stage template.

### Evidence

SNS controlled same-daemon real-JAR-change rebuilds improved from approximately 75.82–77.90s to 4.62–5.08s after layer reordering. This is a controlled warm-cache measurement, not a universal CI saving.

The default builder was also faster than a custom docker-container builder in the measured comparison; that difference must not be mislabelled as an isolated registry-cache benefit.

### Consequences

- no generic image-size reduction claim;
- no mandatory multi-stage requirement;
- cache remains an optimisation, never a correctness dependency;
- Docker build strategy becomes evidence-led and repository-aware.

## ADR-0005: Use Existing Drone Kubernetes + DIND for Testcontainers

### Context

The existing Drone Kubernetes pipeline already provides a DIND Docker daemon. The original ADR treated CI Testcontainers feasibility as unknown.

### Decision

Use the existing DIND execution model for the validated Testcontainers CI path rather than introducing a new runner architecture.

Representative common settings:

```text
DOCKER_HOST=tcp://docker:2375
DOCKER_API_VERSION=1.41
TESTCONTAINERS_HOST_OVERRIDE=docker
TESTCONTAINERS_RYUK_DISABLED=true
```

Mandatory CI must hard-fail if Docker is unavailable. Private registry authentication must exist before internal aggregate images are resolved.

### Consequences

- no new runner technology is required for the validated pattern;
- DIND-specific lifecycle constraints are explicit;
- durable adoption of common environment/step wiring belongs in RepoSync;
- repository-local branches remain evidence vehicles, not the permanent source of shared pipeline behaviour.

