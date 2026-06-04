# Project Context

Why this pilot exists, what it is trying to improve, and how success is measured. [← Back to overview](../README.md)

---

## Background

CI/CD and container workflows create recurring friction as projects grow. The concrete pain points behind this pilot:

- **Long build times** — repeated dependency downloads, poor layer caching, large build contexts.
- **Heavy integration-test setup** — full Docker Compose stacks are slow to start and share hidden state.
- **Flaky, environment-dependent tests** — failures that depend on local vs CI environment differences.
- **Inconsistent Dockerfiles** across repositories, with no shared base-image strategy.
- **Unclear ownership** — some improvements are local to CST, others need RepoSync/platform or wider ETO.

Concrete baseline numbers are **not assumed**. Story 2 captures real build time, image size, pipeline timing and integration-test baseline data before implementation changes begin.

---

## Current State (to be confirmed in Story 2)

These are **placeholder estimates** based on initial observations. Exact values will be captured in T2.2–T2.4 and recorded in the [metrics template](stories/metrics-template.md).

| Metric | Estimated current state | Target (pilot, local) |
|--------|-------------------------|------------------------|
| Docker build time (local) | ~5 min | < 3.5 min (≥ 30% ↓) |
| Final image size | ~450 MB | < 315 MB (≥ 30% ↓, multi-stage removes JDK) |
| Build context size | ~200 MB (estimated) | < 100 MB (≥ 50% ↓, .dockerignore) |
| Integration test startup (local, Testcontainers) | ~90 sec (Compose) | < 30 sec (isolated containers) |
| Flaky / failed pipeline rate | ~5% | Determinism proven locally; CI rate unchanged until platform acts |
| Developer feedback loop (local change → test green) | ~8 min | < 5 min |

These numbers will be replaced with real data once Story 2 is complete.

---

## Business Impact (estimated)

- **Developer productivity:** multi-stage builds + Testcontainers locally = ~3 min saved per build cycle. A developer hitting this ~8×/day = **~24 min saved per developer per day**. For a team of 5, that's **~2 hours/day** back into delivery.
- **Image size → transfer & storage:** 30% smaller image = faster pulls in every environment (dev/SIT/bVal/prod), less registry storage, faster rollout.
- **CI cost (with platform action):** once RepoSync enables BuildKit + remote cache, the same local gains apply in CI. The pilot provides the **evidence** to justify the change request.
- **Security posture:** smaller runtime image (no JDK/Maven) = reduced attack surface. Deterministic tests = fewer false-positive pipeline failures = security patches deployed without delay. In a border-security context, a delayed patch carries real risk.

---

## Technology Stack

| Area | Tooling |
|------|---------|
| Containers | Docker, BuildKit / `docker buildx` (feasibility TBC), multi-stage builds |
| CI/CD | **Drone CI** (Kubernetes runner, `.drone.star` via RepoSync — centrally managed) |
| Deploy | **Helm** (MMA service repo) → Kubernetes; environments: dev → SIT → bVal → prod |
| Source hosting | GitLab (`gitlab.digital.homeoffice.gov.uk`) |
| Registry / artifacts | `docker.digital.homeoffice.gov.uk`, ECR, **Artifactory** (Helm charts + Maven) |
| Integration testing | Testcontainers (Java, pilot); existing Docker Compose + DIND for comparison |
| Build / deps | Maven (`mvnw`), Maven cache mounts |
| Candidate test deps | Redis, Kafka, Schema Registry, LocalStack (IAM) |
| Tracing | OpenTelemetry + Jaeger |
| Security | Trivy, Sonar, SBOM (Syft), Drone secrets — see [SECURITY.md](../SECURITY.md) |
