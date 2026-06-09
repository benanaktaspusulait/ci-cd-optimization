# Backlog Summary

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

> **Note:** This backlog is a **candidate structure only**. Individual tickets should not be created until priority, ownership and target board are agreed with Thomas Reddy and relevant Cerberus Delivery stakeholders.

---

## Story Overview

| # | Story | Tasks | Depends on | Phase |
|---|-------|:-----:|------------|:-----:|
| 1 | Pipeline Assessment (Drone/RepoSync) | 5 | — | 1 |
| 2 | Baseline & Pilot Scope | 4 | 1 | 1 |
| 3 | Docker Build Optimisation | 4 | 2 | 1–2 |
| 4 | Testcontainers Pilot | 4 | 2 | 2 |
| 5 | Docker Compose Rationalisation | 3 | 4 | 2–3 |
| 6 | CST-local vs ACP/ETO Ownership Assessment | 3 | 3, 4, 5 | 3 |

```text
Story 1 (pipeline assessment, gate)
   └──> Story 2 (baseline, gate)
           ├──> Story 3 (build) ──────┐
           └──> Story 4 (testcontainers) ─┼──> Story 5 (compose)
                                          └──> Story 6 (ownership)
```

---

## Full Task List (Jira-ready)

| ID | Title | Type | Est | Priority | Owner | Sprint | Status |
|----|-------|------|:---:|:--------:|-------|:------:|--------|
| **S1** | **Pipeline Assessment** | Story | — | Must | TBC | W1 | Not started |
| T1.1 | Review .drone.star pipeline structure | Research | M | Must | TBC | W1 | Not started |
| T1.2 | Identify local vs RepoSync boundaries | Analysis | S | Must | TBC | W1 | Not started |
| T1.3 | Map CI steps, DIND and Compose usage | Research | M | Must | TBC | W1 | Not started |
| T1.4 | Assess Testcontainers feasibility in Drone | Research | M | Must | TBC | W1 | Not started |
| T1.5 | Assess BuildKit/cache feasibility | Research | S | Should | TBC | W1 | Not started |
| **S2** | **Baseline & Pilot Scope** | Story | — | Must | TBC | W1 | Not started |
| T2.1 | Select pilot repository/service | Research | S | Must | TBC | W1 | Not started |
| T2.2 | Capture CI/CD pipeline baseline | Research | M | Must | TBC | W1 | Not started |
| T2.3 | Capture Docker build & image-size baseline | Research | S | Must | TBC | W1 | Not started |
| T2.4 | Capture integration-test baseline | Research | M | Must | TBC | W1 | Not started |
| **S3** | **Docker Build Optimisation** | Story | — | Must | TBC | W2 | Not started |
| T3.1 | Review current Dockerfile & build context | Analysis | M | Must | TBC | W2 | Not started |
| T3.2 | Add or validate .dockerignore | Implementation | S | Must | TBC | W2 | Not started |
| T3.3 | Apply Dockerfile layering / cache improvement | Implementation | M | Must | TBC | W2 | Not started |
| T3.4 | Measure local & CI build impact | Analysis | M | Should | TBC | W3 | Not started |
| **S4** | **Testcontainers Pilot** | Story | — | Must | TBC | W2 | Not started |
| T4.1 | Select candidate dependency/test | Research | S | Must | TBC | W2 | Not started |
| T4.2 | Implement Testcontainers setup | Implementation | L | Must | TBC | W2 | Not started |
| T4.3 | Compare with docker-compose flow | Analysis | M | Should | TBC | W3 | Not started |
| T4.4 | Document findings & constraints | Documentation | S | Should | TBC | W3 | Not started |
| **S5** | **Docker Compose Rationalisation** | Story | — | Should | TBC | W3 | Not started |
| T5.1 | Map services started by docker-compose | Research | S | Must | TBC | W3 | Not started |
| T5.2 | Classify services & usage | Analysis | M | Must | TBC | W3 | Not started |
| T5.3 | Recommend reduced Compose role | Documentation | M | Should | TBC | W4 | Not started |
| **S6** | **CST-local vs ACP/ETO Ownership Assessment** | Story | — | Must | TBC | W4 | Not started |
| T6.1 | Consolidate pilot findings | Documentation | M | Must | TBC | W4 | Not started |
| T6.2 | Classify ownership & recommend target board | Analysis | M | Must | TBC | W4 | Not started |
| T6.3 | Share findings with stakeholders | Documentation | S | Should | TBC | W4 | Not started |

---

## Story Details

### Story 1 — Pipeline Assessment (Drone/RepoSync)

**Goal:** Understand the centrally managed Drone pipeline structure, establish what can be changed locally vs what requires ACP/RepoSync coordination, and assess feasibility of Testcontainers and BuildKit.

**Why:** The `.drone.star` pipeline is managed via RepoSync. Local changes are not durable. The pilot must separate repo-local proof points from changes that need ACP coordination.

**Acceptance criteria:**
- `.drone.star` structure documented (steps, services, DIND)
- Local vs RepoSync boundaries defined
- CI steps and Docker Compose usage mapped
- Testcontainers feasibility assessed (DIND access, Ryuk, DOCKER_HOST)
- BuildKit feasibility assessed
- Findings inform later stories

---

### Story 2 — Baseline & Pilot Scope

**Goal:** Compare candidate repos, select one, and capture a trustworthy "before" state.

**Why:** Without a baseline there is no way to prove whether an optimisation helped.

**Acceptance criteria:**
- At least two candidates compared
- Pilot repo selected with rationale
- Pipeline, build, image-size and integration-test baselines captured
- Measurement method recorded (repeatable)
- Baseline agreed with stakeholders

---

### Story 3 — Docker Build Optimisation

**Goal:** Apply practical Dockerfile/build-context improvements and prove impact with before/after numbers.

**Why:** Small changes (layer ordering, `.dockerignore`, cache mounts, multi-stage) often deliver disproportionate gains without changing application behaviour.

**Drone constraint:** Multi-stage and `.dockerignore` work anywhere. Cache mounts work locally but are ephemeral in CI DIND. Remote cache requires ACP.

**Acceptance criteria:**
- Dockerfile/context reviewed; cache-invalidation risks identified
- `.dockerignore` present and appropriate
- At least one layering/cache improvement applied
- Build time and image size compared before/after

---

### Story 4 — Testcontainers Pilot

**Goal:** Prove whether Testcontainers can replace part of docker-compose for one dependency with better isolation and determinism.

**Why:** Full Compose stacks are slow, share state, and cause flaky failures. Testcontainers offers isolated, deterministic, per-test environments.

**Drone constraint:** CI feasibility depends on T1.4. DIND + DOCKER_HOST + RYUK_DISABLED needed. If CI not feasible → stays local-only (still valuable).

**Acceptance criteria:**
- One candidate dependency selected with rationale
- Testcontainers setup implemented and connecting
- Flow compared with existing Compose
- Findings and continue/stop recommendation documented

---

### Story 5 — Docker Compose Rationalisation

**Goal:** Clarify which Compose services are truly needed for CI vs local debugging, recommend reduced role.

**Why:** Compose files grow and serve mixed purposes. Separating CI from local reduces overhead without removing developer tooling.

**Note:** Docker Compose should not be removed without mapping current usage. Goal = reduce unnecessary CI orchestration, not remove local workflows.

**Acceptance criteria:**
- All services mapped (image, ports, dependencies, purpose)
- Services classified (CI-required / local-debug / optional / removable)
- CI vs local usage separated
- Reduced role recommended with risk/impact

---

### Story 6 — CST-local vs ACP/ETO Ownership Assessment

**Goal:** Consolidate evidence, classify each item into three ownership categories (CST / ACP / DSA ETO), recommend target operating model.

**Why:** A pilot is only valuable if it ends in a clear decision. This routes follow-up work to the right board.

**Acceptance criteria:**
- Consolidated findings summary exists
- Each item classified with rationale
- Each item mapped to suggested board/owner
- Target operating model recommendation
- Findings shared; feedback captured

---

Create incrementally — not all at once:

1. Epic: Container & CI/CD Optimisation Pilot — FDP Initial Scope
2. Story 1 → T1.1 (pipeline structure) → T1.2 (boundaries)
3. Story 2 → T2.1 (select repo) → T2.2 (pipeline baseline)
4. Story 3 → T3.1 (Dockerfile review)

Open the rest once pipeline boundaries are understood and baseline is underway.

---

## Estimates

- **S** (Small): ≤ 0.5 day
- **M** (Medium): 0.5–1 day
- **L** (Large): 1–2 days
- **Total estimated effort:** ~15 working days (part-time over 4 weeks)

---

*Feedback or questions? Contact the page owner or comment below.*
