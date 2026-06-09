# Architecture Decisions (ADR)

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

> These decisions are **Proposed** — not yet approved. They document the reasoning behind the pilot's technical approach and will be reviewed with stakeholders as part of Story 6.

---

## ADR Index

| ID | Title | Status | Related ADRs |
|----|-------|--------|--------------|
| ADR-0001 | Run a measured pilot, not a big-bang rollout | Proposed | 0002, 0003, 0004 |
| ADR-0002 | Use Testcontainers for selected integration tests | Proposed | 0001, 0003, 0005 |
| ADR-0003 | Reduce Docker Compose role in CI, keep it for local | Proposed | 0002, 0004, 0005 |
| ADR-0004 | Use BuildKit cache + layered multi-stage builds | Proposed | 0001, 0003, 0005 |
| ADR-0005 | CI runner Docker execution mode (Drone Kubernetes + DIND) | Proposed | 0001, 0002, 0004 |

**Statuses:** `Proposed` → under discussion; `Accepted` → decided; `Superseded by ADR-XXXX`; `Deprecated`.

---

## ADR-0001: Run a Measured Pilot, Not a Rollout

**Status:** Proposed

### Context

The FDP CI/CD pipeline currently suffers from several pain points:

- Docker builds take approximately 5 minutes due to poor layer caching and large build contexts (~200 MB).
- The full pipeline duration is approximately 12 minutes end-to-end.
- Docker Compose integration test setup requires ~90 seconds to start 7+ services.
- Integration tests are flaky due to shared state, port conflicts, and environment drift between local and CI.
- Dockerfiles are single-stage, producing ~450 MB images that ship JDK and build tools to production.
- The `.drone.star` pipeline configuration is centrally managed via RepoSync — local edits are overwritten.

A big-bang rollout across multiple repositories would carry significant risk: if assumptions prove wrong, multiple teams are affected simultaneously without evidence to justify the changes. The team lacks concrete baseline measurements to prove current pain or validate improvement ideas.

Additionally, the current DSA focus is on Core Cloud and Data Platform. Any request for ACP or ETO resources must be backed by measured evidence from a real repository, not theoretical proposals.

### Decision

We will run a measured pilot on **one** representative FDP adaptor repository. The pilot will:

- Capture before/after metrics for every change.
- Validate improvement ideas locally before proposing wider adoption.
- Produce evidence to support any future platform change request to ACP or DSA ETO.
- Classify each improvement by ownership (CST-local, ACP, or DSA ETO).

No changes will be proposed for rollout until pilot evidence confirms benefit.

### Consequences

**Positive:**

- Low blast radius — only one repository is affected during validation.
- Evidence-based — decisions are backed by measured before/after data.
- Stakeholder confidence — concrete numbers are more persuasive than theoretical proposals.
- Clear ownership — the pilot distinguishes what CST can do locally from what requires platform support.
- Reversible — local changes on one repository can be reverted trivially.

**Negative:**

- Slower time-to-value — other repositories do not benefit until after the pilot.
- Pilot repo may not be fully representative of all FDP adaptors.
- Results may not generalise to repositories with different dependency profiles.
- Requires discipline to avoid scope creep beyond one repository.

**Follow-ups:**

- State scope limits explicitly in the Story 6 summary.
- Recommend a second repo before any org-wide rollout.
- Route RepoSync/platform and wider ETO items via Story 6 with evidence attached.

### Alternatives Considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Big-bang rollout across all FDP repos | Fast impact if it works | High risk; no baseline evidence; hard to reverse; unclear ownership | Too risky for unproven changes |
| Do nothing | No effort or risk | Pain points persist; build/test friction continues to grow | Does not address known problems |
| Pilot across many repos simultaneously | Broader evidence base | Heavy coordination; defeats small/controlled intent | Disproportionate for a first pilot |
| Start with ACP/ETO changes first | Addresses infra gaps | Slow; depends on another team's priority; no CST evidence to justify ask | Better to show local evidence first |

---

## ADR-0002: Use Testcontainers for Selected Integration Tests

**Status:** Proposed (pilot scope — one dependency)

### Context

The current integration test setup uses Docker Compose to start a full stack of services:

- Zookeeper, Kafka, Schema Registry, Redis, LocalStack, multiple aggregator services, and the command adaptor.
- Compose startup takes approximately 90 seconds before any test can execute.
- All services start regardless of which dependency a specific test actually needs (all-or-nothing).
- Tests share state across runs — one test's side effects may cause another to fail.
- Environment drift between local and CI causes flaky behaviour (different Docker versions, resource limits, network configurations).
- Compose teardown between test suites is slow and sometimes incomplete.

Testcontainers is a Java library that starts lightweight, isolated containers per-test (or per-test-class). Each test declares only the dependencies it needs. Containers are started fresh, eliminating shared state.

However, Testcontainers requires Docker access at test time. In the Drone CI environment, Docker is provided via DIND (Docker-in-Docker) at `tcp://docker:2375`, but the Maven step currently lacks the `DOCKER_HOST` environment variable. This means Testcontainers may only work locally until ACP/RepoSync changes are made.

### Decision

We will pilot Testcontainers for **one** integration dependency (likely Redis or Kafka) in the selected pilot repository. The pilot will:

- Implement Testcontainers setup for one dependency only.
- Run locally to validate isolation and determinism improvements.
- Keep Docker Compose in place for all other dependencies and for local exploratory development.
- Document CI feasibility findings (DOCKER_HOST requirement, DIND compatibility).
- Disable in CI if Docker access cannot be confirmed (fail gracefully with `@DisabledIfEnvironment` or similar).

Container reuse is enabled locally (faster developer feedback), disabled in CI (determinism).

### Consequences

**Positive:**

- Per-test isolation — no shared state between tests.
- Selective startup — only the dependency the test needs is started.
- Faster test feedback — no 90-second full-stack startup for a test that only needs Redis.
- Deterministic — fresh container per test eliminates drift and ordering issues.
- Composable with existing Compose — Testcontainers and Compose can coexist during migration.

**Negative:**

- CI feasibility depends on DOCKER_HOST being available in the Maven step (requires ACP/RepoSync change).
- Developers must have Docker running locally to execute tests (already the case for Compose).
- Container startup per-test adds overhead (~2–5 seconds per container) — acceptable for integration tests but not unit tests.
- Migration is incremental — during transition, some tests use Compose and some use Testcontainers.
- Testcontainers RYUK container (resource cleanup) may need to be disabled in CI (`TESTCONTAINERS_RYUK_DISABLED=true`).
- Cold image pulls can be slow on first run.

**Follow-ups:**

- T4.2 assesses CI suitability early.
- If runner cannot provide Docker, document it and treat Testcontainers as local-only.
- If CI is unsuitable, Compose remains in CI.
- If successful, expand to more dependencies post-pilot.
- Route runner-mode decision to ACP/ETO via ADR-0005.

### Alternatives Considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Keep full Docker Compose for all tests | No change; familiar | Slow startup; shared state; flaky; all-or-nothing | This is the problem being solved |
| Shared long-lived test environment | Fast per-test | Hidden shared state; requires coordination; not isolated | Reintroduces determinism problem |
| Mock all external dependencies | Very fast; no Docker | Lower fidelity; misses real integration bugs (serialisation, schema, protocol) | Defeats purpose of integration testing |
| Testcontainers for all dependencies at once | Full isolation immediately | Large refactor; higher pilot risk | Too much for a first pilot |

---

## ADR-0003: Reduce Docker Compose Role in CI, Keep for Local Development

**Status:** Proposed

### Context

A single `docker-compose.yml` file currently serves three purposes:

1. **CI integration tests** — starts all services before running the test suite in Drone.
2. **Local development** — developers use `docker-compose up` to run the full stack locally.
3. **Exploratory testing** — developers start subsets of services for ad-hoc investigation.

This overloading means CI starts services that are only needed for local debugging, and developers maintain a file that must satisfy CI constraints (no volumes, no host networking, deterministic startup).

Analysis of the Compose file reveals that many services are unnecessary for the specific integration tests in CI:

- Some services exist solely for end-to-end debugging locally.
- Some are aggregator services that the adaptor under test does not directly depend on.
- CI test scope could be satisfied with 2–3 services rather than 7+.

However, removing Compose entirely would break the local development workflow, which relies on the full stack for exploratory testing and debugging.

### Decision

We will take a structured approach:

1. **Map** all services in `docker-compose.yml` with their purpose.
2. **Classify** each service as: CI-required, local-only, or unused.
3. **Recommend** a reduced CI role for Compose (or replacement with Testcontainers for CI).

The target state is:

- **CI** → Testcontainers for tested dependencies (selective, isolated, fast).
- **Local development** → Docker Compose retained for full-stack exploratory work.
- **E2E / exploratory** → Compose or ephemeral environments (future).

During the pilot, no services will be removed. The output is a classification and recommendation document.

### Consequences

**Positive:**

- CI starts only what tests actually need — faster startup, less resource consumption.
- Local development workflow is preserved — developers keep their familiar full-stack environment.
- Clear separation of concerns — CI tests are isolated; local Compose is for exploration.
- Evidence-based — classification is documented with rationale for each service.
- Forces service documentation — mapping reveals hidden dependencies.

**Negative:**

- Two mechanisms for container lifecycle (Testcontainers in CI, Compose locally) — increased cognitive load.
- Compose file still needs maintenance for local use.
- Some tests may behave differently between local (Compose) and CI (Testcontainers) if not carefully managed.
- Classification requires knowledge of which tests depend on which services.
- Risk of breaking hidden workflow (R4) — requires mapping first.

**Follow-ups:**

- Map and classify before changing anything.
- Change CI usage only in the pilot.
- Do not change local Compose usage.
- Document any hidden dependency discovered during mapping.
- If a service is borderline, keep it in CI during the pilot and flag for review.

### Alternatives Considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Remove Docker Compose entirely | Simplest mental model | Breaks local debugging; high developer disruption | Too aggressive; not the goal |
| Keep Compose for everything (CI + local) | No change | Slow CI; shared state; mixed-purpose file | Current problem persists |
| Split into multiple Compose files | Clear separation without Testcontainers | More files to maintain; still shared-state in CI | Possible follow-up, heavier than pilot needs |
| Use Testcontainers for everything, drop Compose | Full isolation | Large refactor; loss of full-stack local convenience | Over-rotation |

---

## ADR-0004: BuildKit Cache Mounts and Multi-Stage Builds

**Status:** Proposed (local validation; remote cache requires ACP)

### Context

The current Dockerfile has several characteristics that cause slow builds and large images:

- **Single-stage build** — the final image contains JDK, Maven, build tools, source code, and the compiled application (~450 MB).
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

- Image size reduction — runtime image contains only JRE + JAR (~150–180 MB vs ~450 MB). Target ≥ 30% reduction.
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
- T6.2 classifies as RepoSync/platform-owned.
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
