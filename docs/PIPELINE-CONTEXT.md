# Pipeline Context

The Drone/RepoSync and CI/deploy boundary that shapes the pilot. [← Back to overview](../README.md)

---

## Drone / RepoSync Constraint

The FDP adaptor repositories use a **centrally managed `.drone.star`** pipeline (Starlark), deployed via **RepoSync**. Local changes to the pipeline config are overwritten.

This means:

- **Pipeline-level changes** (stage ordering, DIND image, BuildKit enabling, Testcontainers environment) **cannot be made locally** — they require RepoSync / platform / ETO coordination.
- **Repository-level changes** (Dockerfile, `.dockerignore`, Maven profiles, test code) **can be made locally** within the pilot scope.
- The CI pipeline uses a **Kubernetes runner** with a **Docker-in-Docker service** (`DOCKER_HOST=tcp://docker:2375`).
- Docker Compose is the current **CI integration test orchestration** method (Kafka, Redis, Schema Registry, aggregators, command adaptor all started via compose).
- `TESTCONTAINERS_RYUK_DISABLED=true` already appears in one Maven step (ECR pipeline) — indicating prior Testcontainers exploration and a known Drone/Ryuk compatibility constraint.
- Pull request events appear to trigger only a minimal/blank pipeline — to be confirmed in Story 1.

**Consequence for the pilot:** Story 1 (Pipeline Assessment) must be completed first to establish what is locally feasible vs what requires central discussion.

---

## Pipeline Landscape

There are **two separate pipelines** in the FDP ecosystem — the pilot targets only the first:

```text
┌─────────────────────────────────────────────────────────────┐
│ CI PIPELINE (per-adaptor repo, .drone.star via RepoSync)    │  ← PILOT SCOPE
│                                                             │
│ clone → Docker/DIND → Maven build + test → Docker Compose   │
│ (Kafka, Redis, Schema Registry, aggregators, cmd-adaptor)   │
│ → Integration tests → Trivy scan → Sonar scan              │
└─────────────────────────────────────────────────────────────┘
        │ produces: Docker image + Helm chart (Artifactory)
        ▼
┌─────────────────────────────────────────────────────────────┐
│ DEPLOY PIPELINE (MMA service repo, separate Drone pipeline) │  ← NOT IN PILOT SCOPE
│                                                             │
│ Helm package → lint → template → mass diff → upload         │
│ → deploy to Kubernetes (dev → SIT → bVal → prod)           │
│ Release day: Thursday. QAT approves at SIT gate.            │
│ Rollback: manual only (helm rollback). No automation.       │
└─────────────────────────────────────────────────────────────┘
```

The pilot optimises the **CI pipeline** (build time, test setup, Docker image). Deploy pipeline improvements (rollback automation, release flow) are captured in [Future considerations](stories/FUTURE-CONSIDERATIONS.md).
