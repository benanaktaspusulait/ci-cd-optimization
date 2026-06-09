# ADR-0002 & ADR-0003: Testcontainers and Compose Decisions

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


---

*Feedback or questions? Contact the page owner or comment below.*
