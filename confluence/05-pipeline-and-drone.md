# Pipeline & Drone Context

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

---

## Pipeline Landscape

There are two separate pipelines. The pilot targets only the CI pipeline.

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
│ DEPLOY PIPELINE (MMA service repo, separate Drone pipeline) │  ← NOT IN PILOT
│                                                             │
│ Helm package → lint → template → mass diff → upload         │
│ → deploy to Kubernetes (dev → SIT → bVal → prod)           │
└─────────────────────────────────────────────────────────────┘
```

---

## Drone / RepoSync Constraint

- FDP adaptor repositories use a centrally managed `.drone.star` (Starlark).
- `.drone.star` is deployed via **RepoSync** — local edits are overwritten.
- Pipeline type: **Kubernetes**.
- Docker access via **DIND service** (`DOCKER_HOST=tcp://docker:2375`).
- Pipeline changes (step ordering, images, environment variables) require **ACP coordination**.

---

## CI Pipeline Steps (from .drone.star)

```text
1. RepoSync Version check
2. Retrieve Artifactory Secrets
3. Wait for Docker (DIND readiness)
4. Extract Adaptor Information
5. Kafka & Redis (docker-compose up — wait for healthy)
6. Aggregators (docker-compose up -d — detached)
7. mvn clean install (Maven build + unit tests)
8. Command Adaptor (docker-compose up --build)
9. Pre-Integration Tests (docker-compose — wait checks)
10. Integration Tests (docker-compose --exit-code-from)
11. Sonar Scan
12. Scan with Trivy
13. Slack notifications
```

---

## Local vs RepoSync-Controlled

| Change | Can do locally? | Requires ACP/RepoSync? |
|--------|:--------------:|:----------------------:|
| Dockerfile multi-stage | ✅ | ❌ |
| `.dockerignore` | ✅ | ❌ |
| Maven profile (testcontainers) | ✅ | ❌ |
| Test source code (Testcontainers configs) | ✅ | ❌ |
| docker-compose.yml (used by Maven plugin) | ✅ | ❌ |
| BuildKit cache mounts (local) | ✅ | ❌ |
| DOCKER_HOST env in Maven step | ❌ | ✅ |
| TESTCONTAINERS_RYUK_DISABLED | ❌ | ✅ |
| DOCKER_BUILDKIT=1 in build step | ❌ | ✅ |
| Remote cache (--cache-from/--cache-to) | ❌ | ✅ |
| DIND image version change | ❌ | ✅ |
| Pipeline step ordering | ❌ | ✅ |

---

## Testcontainers CI Feasibility

**Known facts:**
- DIND service exists and is accessible at `tcp://docker:2375`.
- `TESTCONTAINERS_RYUK_DISABLED=true` already appears in the ECR pipeline Maven step — prior exploration exists.
- The main CI `mvn clean install` step does NOT currently set `DOCKER_HOST`.

**Questions to resolve (Story 1, T1.4):**
- Can the Maven step reach the DIND daemon if `DOCKER_HOST` is added?
- Is `TESTCONTAINERS_CHECKS_DISABLE=true` also needed?
- Can Testcontainers pull images through DIND (registry connectivity)?
- Would a step timeout kill long container startups?

**Possible outcomes:**
1. CI feasible — add env vars via ACP/RepoSync change.
2. CI feasible with constraints — works but with limitations.
3. Local only — Testcontainers stays local; Docker Compose remains in CI (acceptable fallback).

---

## BuildKit CI Feasibility

**Known facts:**
- `docker build` currently works in DIND (images are built and pushed).
- Multi-stage Dockerfiles are standard Docker — should work without special config.
- `DOCKER_BUILDKIT=1` may or may not be set in the current DIND environment.

**Questions to resolve (Story 1, T1.5):**
- Does the DIND image include `docker buildx`?
- Can `--mount=type=cache` work within DIND (ephemeral per-build — yes, but lost between builds)?
- Can `--cache-from=type=registry` read from the internal registry?
- Can `--cache-to=type=registry` write to it (permissions)?

---

## MR Pipeline Behaviour

The `.drone.star` `pull_request` event appears to create a minimal/blank pipeline (`blank_pipeline('GitLab MR')`). This should be confirmed in Story 1.

If true, it means developers do not get full CI feedback on MRs — only on branch pushes and tags. This is relevant for the "developer feedback loop" metric.

---

*Feedback or questions? Contact the page owner or comment below.*
