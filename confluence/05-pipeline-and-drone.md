# Pipeline & Drone Context

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

---

## Pipeline Landscape

There are two separate pipelines in the FDP ecosystem. The pilot targets only the CI pipeline.

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
│ Release day: Thursday. QAT approves at SIT gate.            │
│ Rollback: manual only (helm rollback). No automation.       │
└─────────────────────────────────────────────────────────────┘
```

---

## Drone / RepoSync Constraint

- FDP adaptor repositories use a centrally managed `.drone.star` (Starlark language).
- `.drone.star` is deployed via **RepoSync** — local edits in the adaptor repo are overwritten on next sync.
- Pipeline type: **Kubernetes** (pods are ephemeral — destroyed after each run).
- Docker access: **DIND service** named `docker`, accessible at `tcp://docker:2375`.
- Pipeline-level changes (step ordering, images, environment variables) require **ACP coordination** via the RepoSync source repo.
- Repository-level changes (Dockerfile, `.dockerignore`, Maven profiles, test code) can be made locally.

**Consequence:** Story 1 (Pipeline Assessment) must be completed first to confirm what is locally feasible vs what requires ACP/RepoSync discussion.

---

## CI Pipeline Steps (from .drone.star)

```text
 1. RepoSync Version check
 2. Retrieve Artifactory Secrets
 3. Wait for Docker (DIND service readiness)
 4. Extract Adaptor Information
 5. Kafka & Redis (docker-compose up — wait for healthy)
 6. Aggregators (docker-compose up -d — 7 services detached)
 7. mvn clean install (Maven build + unit tests)
 8. Command Adaptor (docker-compose up --build — from source)
 9. Pre-Integration Tests (docker-compose up — wait checks)
10. Integration Tests (docker-compose --exit-code-from integration-tests)
11. Sonar Scan (code quality)
12. Scan with Trivy (image vulnerabilities)
13. Slack notifications
```

Steps 5–10 are all Docker Compose orchestration — this is the heavy integration test setup the pilot addresses.

---

## Local vs RepoSync-Controlled Boundary

| Change type | Can do locally? | Requires ACP/RepoSync? |
|-------------|:--------------:|:----------------------:|
| Dockerfile (multi-stage, layer ordering) | ✅ | ❌ |
| `.dockerignore` | ✅ | ❌ |
| Maven profile (`-P testcontainers`) | ✅ | ❌ |
| Test source code (Testcontainers configs) | ✅ | ❌ |
| `docker-compose.yml` (used by Maven plugin) | ✅ | ❌ |
| BuildKit cache mounts (local builds) | ✅ | ❌ |
| `DOCKER_HOST` env in Maven step | ❌ | ✅ |
| `TESTCONTAINERS_RYUK_DISABLED` | ❌ | ✅ |
| `DOCKER_BUILDKIT=1` in build step | ❌ | ✅ |
| Remote cache (`--cache-from`/`--cache-to`) | ❌ | ✅ |
| DIND image version change | ❌ | ✅ |
| Pipeline step ordering | ❌ | ✅ |

---

## Testcontainers CI Feasibility

**Known facts:**
- DIND service exists and is accessible at `tcp://docker:2375` from steps that set `DOCKER_HOST`.
- `TESTCONTAINERS_RYUK_DISABLED=true` already appears in the ECR pipeline Maven step — prior exploration exists and the workaround is established.
- The main CI `mvn clean install` step does **not** currently set `DOCKER_HOST`.
- Drone Kubernetes pods are ephemeral — containers die with the pod (no cleanup needed even without Ryuk).

**What needs to happen for Testcontainers in CI:**

```text
Environment variables to add to the Maven step in .drone.star:
  DOCKER_HOST: tcp://docker:2375
  TESTCONTAINERS_RYUK_DISABLED: "true"
  TESTCONTAINERS_CHECKS_DISABLE: "true"
```

**Why Ryuk must be disabled:** Ryuk is a Testcontainers helper that cleans up containers. In Drone's Kubernetes model, Ryuk cannot reliably connect to the DIND daemon. Since pods are ephemeral, containers die automatically — Ryuk is not needed.

**This is a RepoSync-controlled change.** It cannot be done in the adaptor repo.

**Questions to resolve in Story 1 (T1.4):**
- Can the Maven step reach the DIND daemon if `DOCKER_HOST` is added?
- Can Testcontainers pull images through DIND (registry connectivity)?
- Would a step timeout kill long container startups?
- Does the Maven test step have sufficient memory/CPU for running extra containers?

**Possible outcomes:**
1. **CI feasible** — add env vars via ACP/RepoSync change request.
2. **CI feasible with constraints** — works but with limitations (documented).
3. **Local only** — Testcontainers stays on developer machines; Docker Compose remains in CI (acceptable fallback per ADR-0002).

---

## BuildKit CI Feasibility

**Multi-stage builds:** Work today. Standard Docker feature, no DIND or pipeline change needed. The existing `docker build -f Dockerfile` step supports this.

**BuildKit cache mounts (`--mount=type=cache`):** Work per-build if `DOCKER_BUILDKIT=1` is set. But DIND is ephemeral — cache is lost between pipeline runs. Still useful within a single multi-stage build (deps stage cached for build stage).

**Remote registry cache:** Requires ACP — registry namespace, write permissions, DIND buildx support, RepoSync `.drone.star` change.

**What CST can prove locally:**

| Optimisation | Local | CI (without ACP change) | CI (with ACP change) |
|-------------|:-----:|:-----------------------:|:-------------------:|
| Multi-stage Dockerfile | ✅ | ✅ | ✅ |
| `.dockerignore` | ✅ | ✅ | ✅ |
| BuildKit cache mounts | ✅ (persistent) | ⚠️ (ephemeral per-build) | ⚠️ (ephemeral per-build) |
| Remote registry cache | ❌ | ❌ | ✅ (ACP provisions namespace) |

---

## MR Pipeline Behaviour

The `.drone.star` `pull_request` event appears to create a minimal/blank pipeline (`blank_pipeline('merge request')`). This should be confirmed in Story 1 (T1.1).

If true, developers do not get full CI feedback on merge requests — only on branch pushes and tags. This is relevant for the "developer feedback loop" target and may indicate an area where pipeline improvement could have significant developer-experience impact.

---

*Feedback or questions? Contact the page owner or comment below.*
