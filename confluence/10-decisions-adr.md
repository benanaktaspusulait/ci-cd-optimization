# Architecture Decisions (ADR)

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

> These decisions are **Proposed** — not yet approved. They document the reasoning behind the pilot's technical approach and will be reviewed with stakeholders as part of Story 6.

---

## ADR-0001: Run a measured pilot, not a big-bang rollout

**Context:** The FDP CI/CD pipeline suffers from long build times, heavy Docker Compose setup, flaky tests, and inconsistent Dockerfiles. Multiple optimisation ideas exist but none are proven. Implementing all at once across multiple repos would be high-risk with no baseline evidence. Some items are CST-local, others require ACP/RepoSync or wider ETO — unclear boundaries.

**Decision:** Validate ideas through a small, measurable pilot on one representative repository. Capture before/after evidence. Only propose wider rollout after evidence and ownership are clear.

**Consequences:**
- (+) Low risk, evidence-based, reusable patterns identified deliberately, ownership explicit.
- (−) Findings from one repo may not fully generalise. ~4 weeks before wider adoption discussed. Remote cache / base images cannot be realised in pilot alone.

**Alternatives rejected:** Big-bang rollout (too risky), do nothing (pain persists), multi-repo pilot (too heavy), start with ACP/ETO first (no local evidence to justify).

---

## ADR-0002: Use Testcontainers for selected integration tests

**Context:** Integration tests rely on a full Docker Compose stack (~90 sec startup, shared state, environment drift, all-or-nothing). This causes flaky, hard-to-reproduce failures.

**Decision:** Pilot Testcontainers for one dependency. Container reuse enabled locally, disabled in CI. Compare against Compose. Document continue/stop recommendation.

**Consequences:**
- (+) Isolated, deterministic tests. Better local/CI consistency. Selective startup. Simpler debugging.
- (−) Requires Docker in CI (DIND — see ADR-0005). Adds library dependency. Cold image pulls can be slow.

**Alternatives rejected:** Keep Compose (the problem), long-lived test env (shared state), mock everything (low fidelity), Testcontainers for all at once (too large a refactor).

---

## ADR-0003: Reduce Docker Compose role in CI, keep it for local

**Context:** Single `docker-compose.yml` serves CI tests, local debugging, and exploratory testing. Unnecessary services run in CI. Changes for local debugging affect CI.

**Decision:** Reduce Compose in CI (prefer Testcontainers where suitable). Keep Compose for local debugging. Map → classify → recommend. Do not remove Compose entirely.

**Target model:**
- CI integration tests → Testcontainers (isolated, deterministic)
- Local manual debugging → Docker Compose (convenient, full-stack)
- E2E / exploratory → Compose or ephemeral environments (future)

**Consequences:**
- (+) Leaner CI, clear CI/local separation, developers keep familiar tooling, forces service documentation.
- (−) Risk of breaking hidden workflow (R4), requires mapping first, two ways to start deps.

**Alternatives rejected:** Remove Compose entirely (breaks local), keep Compose everywhere (slow CI), split compose files (heavier than needed), Testcontainers for everything (loses local convenience).

---

## ADR-0004: Use BuildKit cache + layered multi-stage builds

**Context:** Single-stage Dockerfile, no cache mounts, ~450 MB images shipping JDK+Maven, no remote cache (Drone pods ephemeral).

**Decision:** Multi-stage Dockerfile (deps → build → runtime). BuildKit cache mounts for local. Remote cache documented but deferred (requires ACP). Clean no-cache build must always work.

**Drone constraint:** Multi-stage works anywhere. Cache mounts are ephemeral in DIND. Remote cache needs `.drone.star` change + registry namespace (ACP/ETO post-pilot).

**Consequences:**
- (+) ≥30% faster local rebuilds, ≥30% smaller image, improved security (no JDK in runtime).
- (−) Remote cache needs ACP infra. Slightly more complex Dockerfile. Cache mounts are BuildKit-specific.

**Alternatives rejected:** Status quo (pain point), cache mounts only (no size reduction), pre-built dep image (governance overhead), Kaniko (less mature).

---

## ADR-0005: CI runner Docker execution mode (Drone Kubernetes + DIND)

**Context:** Drone Kubernetes runner with DIND service (`tcp://docker:2375`). Pipeline centrally managed via RepoSync. ECR pipeline already has `TESTCONTAINERS_RYUK_DISABLED=true`. Main CI Maven step does NOT have DOCKER_HOST set.

**Decision:** Assess DIND suitability in T1.4. Preferred: add DOCKER_HOST + RYUK_DISABLED + CHECKS_DISABLE via RepoSync. Fallback: Testcontainers local-only, Compose stays in CI.

**Consequences:**
- (+) Uses existing DIND, ephemeral pods = auto cleanup, prior art exists (ECR pipeline).
- (−) Requires RepoSync change (not CST-local). Ryuk disabled = no mid-pipeline cleanup. DIND adds latency.

**Alternatives rejected:** Socket mount (root-equivalent access), rootless Docker (may not be available), no Docker in CI (valid fallback but not ideal).

---

*Feedback or questions? Contact the page owner or comment below.*
