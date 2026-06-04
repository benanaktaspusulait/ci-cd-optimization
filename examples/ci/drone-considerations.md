# Drone CI — Testcontainers & BuildKit Considerations

How the pilot proposals would work within the real FDP Drone pipeline.

> **Key constraint:** The `.drone.star` is centrally managed via RepoSync. Changes to pipeline steps, services, or environment variables require a RepoSync change request — not a local repo commit.

---

## Current pipeline structure (from .drone.star)

```text
Pipeline type: Kubernetes
DIND service: docker (tcp://docker:2375)

CI Pipeline steps:
1. RepoSync Version
2. Retrieve Artifactory Secrets
3. Wait for Docker
4. Extract Adaptor Information
5. Kafka & Redis (docker-compose up)
6. Aggregators (docker-compose up -d)
7. mvn clean install
8. Command Adaptor (docker-compose up --build)
9. Pre-Integration Tests (docker-compose up, wait checks)
10. Integration Tests (docker-compose up --exit-code-from)
11. Sonar Scan
12. Scan with Trivy
13. Slack notifications
```

---

## Testcontainers in Drone

### What needs to happen

For Testcontainers to work in the `mvn clean install` step (or a new Maven step):

```yaml
# These environment variables must be added to the Maven step:
environment:
  DOCKER_HOST: tcp://docker:2375
  TESTCONTAINERS_RYUK_DISABLED: "true"    # Ryuk not compatible with Drone
  TESTCONTAINERS_CHECKS_DISABLE: "true"   # Skip pre-flight checks
```

### Why Ryuk must be disabled

Ryuk is a Testcontainers helper container that cleans up other containers. In Drone's Kubernetes pipeline model, Ryuk cannot connect to the DIND daemon reliably. The ECR pipeline already has this workaround.

**Implication:** Without Ryuk, container cleanup is the responsibility of the pipeline. Since Drone pipelines are ephemeral (pod is destroyed after the pipeline), this is acceptable — containers die with the pod.

### Where this change lives

This is a **RepoSync-controlled change** — the Maven step environment in `.drone.star` must be modified. It cannot be done in the adaptor repo.

### Fallback (from ADR-0002)

If Drone CI execution is not feasible or the RepoSync change is not approved:
- Testcontainers runs **locally only** (developer machines)
- Docker Compose remains the CI integration test mechanism
- The pilot still demonstrates the pattern and local developer experience improvement

---

## BuildKit in Drone

### Multi-stage builds

**Works today** — `docker build` with multi-stage Dockerfiles is standard Docker behaviour. No DIND or pipeline change needed. The existing `docker build -f Dockerfile` step already supports this.

### BuildKit cache mounts (`--mount=type=cache`)

**Works per-build** — `DOCKER_BUILDKIT=1` enables cache mounts. But since DIND is ephemeral per pipeline, the cache is lost between builds. Still useful for multi-stage builds within a single pipeline run (deps stage → build stage).

To enable:
```yaml
# Add to the docker build step environment:
environment:
  DOCKER_BUILDKIT: "1"
```

This is a **RepoSync-controlled change** (environment variable in `.drone.star`).

### Remote registry cache (`--cache-from` / `--cache-to`)

**Requires ACP/ETO:**
- Registry namespace for cache layers (e.g. `docker.digital.homeoffice.gov.uk/dacc-aws/fdp-cache`)
- Write permissions for the Drone pipeline to push cache
- Retention/eviction policy for cache layers
- `.drone.star` change to add `--cache-from` / `--cache-to` flags

This is **post-pilot** — classify in Story 6 as RepoSync/platform or wider ETO, depending on who owns the cache namespace and registry policy.

---

## What CST can do locally (no RepoSync change)

| Action | Works locally | Works in CI |
|--------|:------------:|:-----------:|
| Multi-stage Dockerfile | ✅ | ✅ (standard Docker) |
| `.dockerignore` | ✅ | ✅ |
| BuildKit cache mounts | ✅ (persistent) | ⚠️ (ephemeral per build) |
| Testcontainers tests | ✅ | ❓ (needs T1.4 confirmation) |
| Maven `-P testcontainers` profile | ✅ | ❓ (needs RepoSync change to skip compose) |
| Remote registry cache | ❌ | ❌ (needs ACP/ETO) |

---

## Recommended approach for the pilot

1. **Story 1:** confirm T1.4 (Testcontainers) and T1.5 (BuildKit) feasibility
2. **Story 3:** apply Dockerfile optimisation locally; measure local before/after; CI benefit comes from multi-stage (no special config) and `.dockerignore` (reduces context sent to DIND)
3. **Story 4:** prototype Testcontainers locally; if T1.4 confirms CI feasibility, request RepoSync change to add DOCKER_HOST + RYUK env vars to Maven step
4. **Story 6:** document what was local vs what needs RepoSync/platform action
