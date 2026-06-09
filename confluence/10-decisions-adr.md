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

**Statuses:** `Proposed` -> under discussion; `Accepted` -> decided; `Superseded by ADR-XXXX`; `Deprecated`.

---

## ADR-0001: Run a measured pilot, not a big-bang rollout

**Context:** The FDP CI/CD pipeline suffers from long build times, heavy Docker Compose setup, flaky tests, and inconsistent Dockerfiles. Multiple optimisation ideas exist but none are proven. Implementing all at once across multiple repos would be high-risk with no baseline evidence. Some items are CST-local, others require ACP/RepoSync or wider ETO — unclear boundaries.

**Decision:** Validate ideas through a small, measurable pilot on one representative repository. Capture before/after evidence. Only propose wider rollout after evidence and ownership are clear.

**Consequences:**
- (+) Low risk, evidence-based, reusable patterns identified deliberately, ownership explicit.
- (−) Findings from one repo may not fully generalise. ~4 weeks before wider adoption discussed. Remote cache / base images cannot be realised in pilot alone.

**Follow-ups:**
- State scope limits explicitly in the Story 6 summary.
- Recommend a second repo before any org-wide rollout.
- Route RepoSync/platform and wider ETO items via Story 6 with evidence attached.

**Alternatives considered:**

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Big-bang rollout across all FDP repos | Fast impact if it works | High risk; no baseline evidence; hard to reverse; unclear ownership | Too risky for unproven changes |
| Do nothing | No effort or risk | Pain points persist; build/test friction continues to grow | Does not address known problems |
| Pilot across many repos simultaneously | Broader evidence base | Heavy coordination; defeats small/controlled intent | Disproportionate for a first pilot |
| Start with ACP/ETO changes first | Addresses infra gaps | Slow; depends on another team's priority; no CST evidence to justify ask | Better to show local evidence first |

---

## ADR-0002: Use Testcontainers for selected integration tests

**Context:** Integration tests rely on a full Docker Compose stack (~90 sec startup, shared state, environment drift, all-or-nothing). This causes flaky, hard-to-reproduce failures.

**Decision:** Pilot Testcontainers for one dependency. Container reuse enabled locally, disabled in CI. Compare against Compose. Document continue/stop recommendation.

**Consequences:**
- (+) Isolated, deterministic tests. Better local/CI consistency. Selective startup. Simpler debugging.
- (−) Requires Docker in CI (DIND — see ADR-0005). Adds library dependency. Cold image pulls can be slow.

**Follow-ups:**
- T4.2 assesses CI suitability early.
- If runner cannot provide Docker, document it and treat Testcontainers as local-only.
- If CI is unsuitable, Compose remains in CI.
- If successful, expand to more dependencies post-pilot.
- Route runner-mode decision to ACP/ETO via ADR-0005.

**Alternatives considered:**

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Keep full Docker Compose for all tests | No change; familiar | Slow startup; shared state; flaky; all-or-nothing | This is the problem being solved |
| Shared long-lived test environment | Fast per-test | Hidden shared state; requires coordination; not isolated | Reintroduces determinism problem |
| Mock all external dependencies | Very fast; no Docker | Lower fidelity; misses real integration bugs | Defeats purpose of integration testing |
| Testcontainers for all dependencies at once | Full isolation immediately | Large refactor; higher pilot risk | Too much for a first pilot |

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

**Follow-ups:**
- Map and classify before changing anything.
- Change CI usage only in the pilot.
- Do not change local Compose usage.
- Document any hidden dependency discovered during mapping.
- If a service is borderline, keep it in CI during the pilot and flag for review.

**Alternatives considered:**

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Remove Docker Compose entirely | Simplest mental model | Breaks local debugging; high developer disruption | Too aggressive; not the goal |
| Keep Compose for everything | No change | Slow CI; shared state; mixed-purpose file | Current problem |
| Split into two compose files | Clear separation without Testcontainers | More files to maintain; still shared-state in CI | Possible follow-up, heavier than pilot needs |
| Use Testcontainers for everything, drop Compose | Full isolation | Large refactor; loss of full-stack local convenience | Over-rotation |

---

## ADR-0004: Use BuildKit cache + layered multi-stage builds

**Context:** Single-stage Dockerfile, no cache mounts, ~450 MB images shipping JDK+Maven, no remote cache (Drone pods ephemeral).

**Decision:** Multi-stage Dockerfile (deps → build → runtime). BuildKit cache mounts for local. Remote cache documented but deferred (requires ACP). Clean no-cache build must always work.

**Drone constraint:** Multi-stage works anywhere. Cache mounts are ephemeral in DIND. Remote cache needs `.drone.star` change + registry namespace (ACP/ETO post-pilot).

**Consequences:**
- (+) ≥30% faster local rebuilds, ≥30% smaller image, improved security (no JDK in runtime).
- (−) Remote cache needs ACP infra. Slightly more complex Dockerfile. Cache mounts are BuildKit-specific.

**Follow-ups:**
- T3.3 applies one layering change at a time.
- T3.4 compares before/after locally and in CI where available.
- Verify `--no-cache` build still succeeds.
- Route remote-cache infrastructure to ACP/ETO via Story 6.
- Post-pilot: request RepoSync change for `--cache-from` / `--cache-to`.

**Alternatives considered:**

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Single-stage, copy-all Dockerfile | Simple | No layer caching; large images; every change triggers full rebuild | Current pain point |
| Cache mounts only, no multi-stage | Partial speedup | Runtime image still ships JDK/build tools | Leaves image-size problem unsolved |
| Pre-built dependency image | Very fast builds | Governance overhead; must rebuild when deps change | Heavier than pilot scope |
| Kaniko | No Docker daemon needed in CI | Less mature BuildKit features; no cache mounts | BuildKit is the standard path |

---

## ADR-0005: CI runner Docker execution mode (Drone Kubernetes + DIND)

**Context:** Drone Kubernetes runner with DIND service (`tcp://docker:2375`). Pipeline centrally managed via RepoSync. ECR pipeline already has `TESTCONTAINERS_RYUK_DISABLED=true`. Main CI Maven step does NOT have DOCKER_HOST set.

**Decision:** Assess DIND suitability in T1.4. Preferred: add DOCKER_HOST + RYUK_DISABLED + CHECKS_DISABLE via RepoSync. Fallback: Testcontainers local-only, Compose stays in CI.

**Consequences:**
- (+) Uses existing DIND, ephemeral pods = auto cleanup, prior art exists (ECR pipeline).
- (−) Requires RepoSync change (not CST-local). Ryuk disabled = no mid-pipeline cleanup. DIND adds latency.

**Follow-ups:**
- T1.4 confirms DIND connectivity from Maven step.
- If feasible, submit RepoSync change request with env vars.
- T6.2 classifies as RepoSync/platform-owned.
- Document CI vs local env vars for the team.

**Alternatives considered:**

| Option | Pros | Cons | Why not chosen / default |
|--------|------|------|--------------------------|
| Docker-in-Docker (`--privileged`) | Widely documented; isolated daemon | `--privileged` security risk on shared runners; slow startup | Acceptable only on dedicated runner tag |
| Docker socket mount | No privileged job; reuses host daemon | Grants root-equivalent host access | Only if security posture allows; confirm with ACP/ETO |
| Rootless Docker / Sysbox | Secure; no host privilege escalation | Requires specific kernel/runner setup | Assess in T1.4; not assumed available |
| No Docker in CI | No privilege concerns | No Testcontainers in CI; Compose remains | Valid fallback per ADR-0002 |

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
