# ADR-0004 & ADR-0005: BuildKit and CI Runner Decisions

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

> These decisions are **Proposed** — not yet approved. They will be reviewed with stakeholders as part of Story 6.

---

## ADR-0004: BuildKit Cache Mounts and Multi-Stage Builds

**Status:** Proposed (local validation; remote cache requires ACP)

### Context

The current Dockerfile has several characteristics that cause slow builds and large images:

- **Single-stage build** — the final image may contain the full JDK, build-time tools, and potentially source or intermediate artifacts alongside the compiled application (~450 MB). The exact contents depend on the current build path.
- **No layer caching strategy** — dependencies are re-downloaded on every build because the Maven cache is not preserved between layers.
- **Large build context** — approximately 200 MB is sent to the Docker daemon because there is no `.dockerignore` (or an insufficient one).
- **Ephemeral Drone pods** — the Drone Kubernetes runner creates fresh pods for each pipeline run. Any local Docker layer cache is lost between runs.

BuildKit is Docker's modern build backend. It provides:

- **Cache mounts** (`--mount=type=cache`) — persist directories (e.g., Maven `.m2` repository) across builds without baking them into image layers.
- **Multi-stage builds** — separate stages for dependencies, compilation, and runtime.
- **Inline cache metadata** — allows pushing cache to a registry for reuse across machines.

However, in the Drone DIND environment, cache mounts are ephemeral — the DIND container is destroyed after each pipeline run, so the cache mount has no persistence between CI runs. Remote cache (pushing/pulling from a registry) requires ACP infrastructure and is out of scope for the pilot.

### Decision

We will implement a multi-stage Dockerfile with three stages:

1. **Dependencies stage** — resolve and download Maven dependencies (cached layer when `pom.xml` unchanged).
2. **Build stage** — compile the application using the resolved dependencies.
3. **Runtime stage** — copy only the compiled JAR into a minimal runtime image.

Configuration:

- Base image: `amazoncorretto:17` (Amazon's OpenJDK distribution, already used in the ecosystem).
- Cache mounts: `--mount=type=cache,target=/root/.m2` for Maven repository (effective locally; ephemeral in DIND).
- Remote cache: deferred to post-pilot (requires ACP to provide registry-backed cache infrastructure).
- `.dockerignore`: exclude `.git`, `target/`, IDE files, documentation — reduce build context by ≥ 50%.
- Clean no-cache build must always work (`docker build --no-cache` produces identical output).

### Consequences

**Positive:**

- Image size reduction — runtime image ships only the application JAR and minimal runtime base (~150–300 MB vs ~450 MB). Target ≥ 30% reduction; exact size depends on runtime base choice (full JDK vs JRE-only or slim variant).
- Faster local builds — cache mounts avoid re-downloading ~200 MB of Maven dependencies on every build.
- Security improvement — production image does not contain build tools, source code, or intermediate artifacts.
- Multi-stage works anywhere — no special CI infrastructure required. Benefits are immediate locally.
- Foundation for remote cache — when ACP provides registry infrastructure, the Dockerfile is already structured correctly.

**Negative:**

- Cache mounts are ephemeral in Drone DIND — CI builds do not benefit from cache mounts until remote cache is available.
- Multi-stage adds Dockerfile complexity — developers must understand the stage separation.
- `amazoncorretto:17` must be validated against existing compliance requirements (TBC — may require approved base image from ACP/ETO).
- Remote cache requires ACP prioritisation and is not available during the pilot.

### Drone Constraint Note

- **Multi-stage builds** — work anywhere, no special CI support needed.
- **Cache mounts** — effective locally but ephemeral in DIND (the DIND container is destroyed after each Drone pipeline run).
- **Remote cache** (`--cache-to=type=registry`) — requires ACP to provide and configure registry-backed cache. Post-pilot activity.

**Follow-ups:**

- T3.3 applies one layering change at a time.
- T3.4 compares before/after locally and in CI where available.
- Verify `--no-cache` build still succeeds.
- Route remote-cache infrastructure to ACP/ETO via Story 6.
- Post-pilot: request RepoSync change for `--cache-from` / `--cache-to`.

### Alternatives Considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Status quo (single-stage, no cache) | Simple | Large images; slow builds; no caching; every CI run re-downloads all dependencies | Current pain point |
| Cache mounts only (no multi-stage) | Partial speedup | Runtime image still ships JDK/build tools | Leaves image-size problem unsolved |
| Pre-built dependency layer (base image with deps) | Very fast builds | Governance overhead; must rebuild when deps change; stale deps risk; requires ACP/ETO ownership | Heavier than pilot scope |
| Kaniko (rootless image building) | No Docker daemon needed in CI | Less mature ecosystem; different caching semantics; may not support all Dockerfile features; not available in Drone currently | BuildKit is the standard path; could be revisited if DIND proves problematic |

---

## ADR-0005: CI Runner Docker Execution Mode (Drone Kubernetes + DIND)

**Status:** Proposed (assessment pending — Task T1.4)

### Context

The CI environment has the following characteristics:

- **Drone Kubernetes runner** — pipeline steps run as containers in Kubernetes pods.
- **DIND (Docker-in-Docker)** — Docker access is provided via a sidecar container at `tcp://docker:2375`.
- **RepoSync-managed** — pipeline configuration (`.drone.star`) is centrally managed. Local edits are overwritten on next sync.
- **ECR pipeline** — the existing ECR push step already has `TESTCONTAINERS_RYUK_DISABLED=true` set, suggesting Docker access is available in some steps.
- **Maven step** — the main CI step that runs `mvn verify` does **not** currently have `DOCKER_HOST=tcp://docker:2375` set. This means Testcontainers (which requires Docker access) cannot connect to the DIND sidecar from the Maven step.

For Testcontainers to work in CI, the Maven step needs:

- `DOCKER_HOST=tcp://docker:2375` — tells the Docker client where to connect.
- `TESTCONTAINERS_RYUK_DISABLED=true` — disables the Ryuk resource reaper (which may have issues in DIND).
- Potentially `TESTCONTAINERS_HOST_OVERRIDE=docker` — for container-to-container networking.

These environment variables must be added to the `.drone.star` configuration, which is controlled by RepoSync. This requires either:

1. ACP/RepoSync team adding the variables centrally.
2. A mechanism in RepoSync for per-repo environment variable overrides.

### Decision

We will assess Docker availability in CI as part of Task T1.4 (Testcontainers CI feasibility). The approach is:

1. **Preferred path** — request ACP/RepoSync to add the required environment variables (`DOCKER_HOST`, `TESTCONTAINERS_RYUK_DISABLED`) to the Maven step via RepoSync configuration.
2. **Fallback** — if CI Docker access cannot be confirmed or enabled within pilot timescales, Testcontainers will run locally only. CI integration tests will continue using Docker Compose until the environment is ready.

No changes will be made to the Drone runner infrastructure. The pilot will only request environment variable additions to the existing pipeline configuration.

### Consequences

**Positive:**

- Non-invasive — only environment variable additions, no infrastructure changes.
- Compatible with existing DIND setup — reuses the sidecar already provisioned for ECR push.
- Fallback available — local-only Testcontainers is still valuable for developer experience.
- Evidence for ACP — pilot demonstrates Testcontainers value, supporting the case for CI enablement.

**Negative:**

- Depends on ACP/RepoSync prioritisation — environment variable changes require approval and scheduling.
- DIND at `tcp://docker:2375` is unencrypted — acceptable within pod-internal networking but noted.
- Ryuk disabled means container cleanup depends on pod lifecycle — acceptable in ephemeral Drone pods.
- If DOCKER_HOST is not available, CI Testcontainers is blocked until ACP acts.
- DIND adds startup latency to pipeline.

**Follow-ups:**

- T1.4 confirms DIND connectivity from Maven step.
- If feasible, submit RepoSync change request with env vars.
- T6.1 classifies this as RepoSync/platform-owned; T6.2 records any adoption decision.
- Document CI vs local env vars for the team.

### Alternatives Considered

| Option | Pros | Cons | Why not chosen / default |
|--------|------|------|--------------------------|
| Docker-in-Docker (`--privileged`) | Widely documented; isolated daemon | `--privileged` security risk on shared runners; slow startup | Acceptable only on dedicated runner tag |
| Docker socket mount (`/var/run/docker.sock`) | No privileged job; reuses host daemon | Grants root-equivalent host access; container could affect other workloads on the node; not compatible with Drone K8s runner model | Security risk too high |
| Rootless Docker / Sysbox | Secure; no host privilege escalation | Requires specific kernel/runner setup; may not be available in current ACP K8s cluster; infrastructure changes beyond pilot scope | Assess in T1.4; not assumed available; could be explored post-pilot |
| No Docker in CI (Compose only, Testcontainers local-only) | No privilege concerns | No Testcontainers in CI; Compose remains; developers still benefit locally | Valid fallback per ADR-0002 |

---

## ADR Template

Use this template for new decisions.

```text
# ADR-NNNN: <short title of the decision>

- Status: Proposed | Accepted | Superseded by ADR-XXXX | Deprecated
- Date: YYYY-MM-DD
- Deciders: <names / roles>
- Related: <story / task / ADR links>

## Context
What is the situation and the forces at play? What problem or question forced a decision? Keep it factual.

## Decision
The decision, stated in active voice: "We will ...".

## Consequences
What becomes easier and what becomes harder as a result. Include trade-offs, risks, and follow-up actions.

- Positive:
- Negative / trade-offs:
- Follow-ups:

## Alternatives considered
| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| | | | |
```


---

*Feedback or questions? Contact the page owner or comment below.*
