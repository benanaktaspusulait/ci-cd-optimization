# Backlog — Detailed Stories and Tasks

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

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

## Task Summary Table

| ID | Title | Type | Est | Priority | Sprint | Depends on |
|----|-------|------|:---:|:--------:|:------:|------------|
| T1.1 | Review .drone.star pipeline structure | Research | M | Must | W1 | — |
| T1.2 | Identify local vs RepoSync boundaries | Analysis | S | Must | W1 | T1.1 |
| T1.3 | Map CI steps, DIND and Compose usage | Research | M | Must | W1 | T1.1 |
| T1.4 | Assess Testcontainers feasibility in Drone | Research | M | Must | W1 | T1.1, T1.3 |
| T1.5 | Assess BuildKit/cache feasibility | Research | S | Should | W1 | T1.1, T1.3 |
| T2.1 | Select pilot repository/service | Research | S | Must | W1 | T1.2 |
| T2.2 | Capture CI/CD pipeline baseline | Research | M | Must | W1 | T2.1 |
| T2.3 | Capture Docker build & image-size baseline | Research | S | Must | W1 | T2.1 |
| T2.4 | Capture integration-test baseline | Research | M | Must | W1 | T2.1 |
| T3.1 | Review current Dockerfile & build context | Analysis | M | Must | W2 | T2.1 |
| T3.2 | Add or validate .dockerignore | Implementation | S | Must | W2 | T3.1 |
| T3.3 | Apply Dockerfile layering / cache improvement | Implementation | M | Must | W2 | T3.1 |
| T3.4 | Measure local & CI build impact | Analysis | M | Should | W3 | T3.3 |
| T4.1 | Select candidate dependency/test | Research | S | Must | W2 | T2.1 |
| T4.2 | Implement Testcontainers setup | Implementation | L | Must | W2 | T4.1 |
| T4.3 | Compare with docker-compose flow | Analysis | M | Should | W3 | T4.2 |
| T4.4 | Document findings & constraints | Documentation | S | Should | W3 | T4.3 |
| T5.1 | Map services started by docker-compose | Research | S | Must | W3 | T4.4 |
| T5.2 | Classify services & usage | Analysis | M | Must | W3 | T5.1 |
| T5.3 | Recommend reduced Compose role | Documentation | M | Should | W4 | T4.4, T5.2 |
| T6.1 | Consolidate pilot findings | Documentation | M | Must | W4 | T3.4, T4.4, T5.3 |
| T6.2 | Classify ownership & recommend target board | Analysis | M | Must | W4 | T6.1 |
| T6.3 | Share findings with stakeholders | Documentation | S | Should | W4 | T6.2 |

**Estimates:** S ≤ 0.5 day · M = 0.5–1 day · L = 1–2 days. **Total:** ~15 working days (part-time over 4 weeks). Equivalent story points: S=1, M=2, L=3–5.

---

## Story 1 — Pipeline Assessment (Drone/RepoSync)

**Goal:** Understand the Drone pipeline structure, establish local vs central boundaries, and assess Testcontainers + BuildKit feasibility.

**Why:** The `.drone.star` is RepoSync-managed. Without understanding boundaries first, the pilot may propose infeasible changes.

**Acceptance criteria:**
- `.drone.star` structure documented (steps, services, DIND)
- Local vs RepoSync boundaries defined
- CI steps and Docker Compose usage mapped
- Testcontainers CI feasibility assessed
- BuildKit feasibility assessed

### T1.1 — Review .drone.star pipeline structure

**Why:** The `.drone.star` defines the entire CI pipeline. Understanding it is the prerequisite for every other task.

**Goal:** Document the current Drone pipeline: what runs, in what order, with what services.

**Scope:**
- Obtain the `.drone.star` source (RepoSync source repo)
- Document pipeline types (CI, ECR, Artifactory)
- Document steps within each pipeline (order, images, commands)
- Document services (DIND)
- Note existing Testcontainers config (`RYUK_DISABLED`)
- Note MR/pull_request pipeline behaviour

**Acceptance criteria:**
- [ ] Pipeline types and purposes documented
- [ ] Step ordering and dependencies mapped
- [ ] DIND service configuration documented
- [ ] Existing Testcontainers workarounds noted
- [ ] MR pipeline behaviour confirmed

### T1.2 — Identify local vs RepoSync boundaries

**Why:** RepoSync overwrites local pipeline changes. The pilot must know which changes survive and which need ACP coordination.

**Goal:** Produce a clear "local vs central" classification table.

**Scope:**
- Classify: repo-local (Dockerfile, `.dockerignore`, Maven profiles, test code, compose files) vs RepoSync-controlled (`.drone.star`, step env vars, DIND image, pipeline ordering)
- Identify RepoSync source repo and change request process
- Identify contact for ACP changes

**Acceptance criteria:**
- [ ] Clear local vs central classification exists
- [ ] RepoSync source repo and change process identified
- [ ] Pilot team knows who to contact for central changes

### T1.3 — Map CI steps, DIND and Compose usage

**Why:** The CI pipeline is heavy (multiple compose cycles, waits, aggregator startups). Understanding each step's purpose and duration is essential for optimisation.

**Goal:** Step-by-step map of the CI pipeline with timing data where available.

**Scope:**
- Document each step: name, image, Docker Compose commands, DIND interactions
- Capture duration from recent Drone runs
- Identify potential duplicate work (Maven build + compose integration-tests container)
- Document CI vs deploy pipeline boundary

**Acceptance criteria:**
- [ ] All CI pipeline steps listed with purpose
- [ ] Docker Compose commands and services per step documented
- [ ] Step durations captured where available
- [ ] Potential duplicate work flagged

### T1.4 — Assess Testcontainers feasibility in Drone

**Why:** Testcontainers needs Docker daemon access from the Maven step. The ECR pipeline already has `RYUK_DISABLED` — prior exploration exists. Without confirming feasibility, Story 4 cannot scope correctly.

**Goal:** Determine whether Testcontainers can run in Drone CI and document constraints.

**Scope:**
- Can Maven step access `DOCKER_HOST=tcp://docker:2375`?
- Is `TESTCONTAINERS_RYUK_DISABLED=true` sufficient?
- Is `TESTCONTAINERS_CHECKS_DISABLE=true` needed?
- Can Testcontainers pull images through DIND?
- Would step timeout kill container startups?

**Acceptance criteria:**
- [ ] Docker daemon accessibility confirmed or denied
- [ ] Required environment variables documented
- [ ] Ryuk disabled implications understood
- [ ] Clear feasible / feasible-with-constraints / local-only decision made

### T1.5 — Assess BuildKit/cache feasibility

**Why:** BuildKit multi-stage + cache mounts are proposed (ADR-0004). Need to confirm DIND supports it.

**Goal:** Determine what level of BuildKit optimisation works in current Drone/DIND.

**Scope:**
- Is `DOCKER_BUILDKIT=1` set or settable?
- Does DIND image include `docker buildx`?
- Cache mount behaviour (ephemeral per-build — confirmed)?
- Remote cache: registry read/write permissions available?

**Acceptance criteria:**
- [ ] BuildKit availability confirmed or denied
- [ ] `docker buildx` availability confirmed or denied
- [ ] Cache mount behaviour documented
- [ ] Remote cache feasibility assessed

---

## Story 2 — Baseline & Pilot Scope

**Goal:** Compare candidates, select pilot repo, capture "before" state.

**Why:** Without baseline data, no optimisation can be proved.

**Acceptance criteria:**
- At least two candidates compared
- Pilot repo selected with rationale
- Pipeline, build, image, integration-test baselines captured
- Measurement method repeatable

### T2.1 — Select pilot repository/service

**Why:** The pilot needs a representative target. Comparing two repos ensures portability.

**Goal:** Compare ≥2 FDP repos, select one for the pilot.

**Scope:** Review candidates against: pipeline duration, Compose usage, test complexity, delivery priority, pattern portability.

**Acceptance criteria:**
- [ ] ≥2 candidates compared
- [ ] One selected with documented rationale
- [ ] Scope agreed with stakeholders

### T2.2 — Capture CI/CD pipeline baseline

**Why:** Pipeline duration is the headline stakeholder metric.

**Goal:** Record current pipeline timings.

**Scope:** Average pipeline duration, build/unit/integration stage breakdown, failed-pipeline frequency, data source (Drone UI, last N runs).

**Acceptance criteria:**
- [ ] Baseline pipeline metrics documented
- [ ] Data source and method recorded
- [ ] Repeatable for after-comparison

### T2.3 — Capture Docker build & image-size baseline

**Why:** Story 3 targets these directly — need "before" numbers.

**Goal:** Record build time and image size.

**Scope:** Local Docker build time, CI build time (from Drone logs), final image size, current base image.

**Acceptance criteria:**
- [ ] Build duration documented
- [ ] Image size documented
- [ ] Base image identified

### T2.4 — Capture integration-test baseline

**Why:** Stories 4 and 5 depend on understanding current test setup.

**Goal:** Document how integration tests run today.

**Scope:** How tests start (pipeline step), Compose dependencies, startup/wait time, known flaky issues.

**Acceptance criteria:**
- [ ] Current setup documented
- [ ] Dependencies listed
- [ ] Known pain points captured

---

## Story 3 — Docker Build Optimisation

**Goal:** Apply practical build improvements, prove with before/after numbers.

**Why:** Small changes deliver disproportionate gains without changing app behaviour.

**Acceptance criteria:**
- Dockerfile/context reviewed; cache-invalidation risks identified
- `.dockerignore` present
- ≥1 layering/cache improvement applied
- Before/after compared

### T3.1 — Review current Dockerfile & build context

**Why:** Evidence-led optimisation — find where cache breaks.

**Goal:** Document structure, identify opportunities.

**Scope:** Base image, layer ordering, dep install steps, COPY instructions, context size, unnecessary files.

**Acceptance criteria:**
- [ ] Structure documented
- [ ] Cache-invalidation risks identified
- [ ] Prioritised opportunity list produced

### T3.2 — Add or validate .dockerignore

**Why:** Cheapest, lowest-risk win — reduces context immediately.

**Goal:** Ensure appropriate `.dockerignore` exists.

**Scope:** Exclude `.git`, `target`, `docs`, `src/test`, IDE files, compose files.

**Acceptance criteria:**
- [ ] `.dockerignore` exists and is appropriate
- [ ] Context reduction measured

### T3.3 — Apply Dockerfile layering / cache improvement

**Why:** Biggest build-time wins come from separating deps from source.

**Goal:** Apply one focused layering/cache change.

**Scope:** Multi-stage (deps → build → runtime), BuildKit cache mounts, or dependency-metadata-first COPY.

**Acceptance criteria:**
- [ ] One change applied
- [ ] Expected benefit described
- [ ] Risks/concerns noted
- [ ] Built image passes basic verification

### T3.4 — Measure local & CI build impact

**Why:** Change is only worth keeping if measurably helpful.

**Goal:** Quantify before/after.

**Scope:** Local warm/cold build time, CI build time (if available), image size.

**Acceptance criteria:**
- [ ] Before/after metrics captured
- [ ] Improvement or regression documented
- [ ] Keep/adjust recommendation made

---

## Story 4 — Testcontainers Pilot

**Goal:** Prove Testcontainers can replace one Compose dependency with better isolation.

**Why:** Full Compose is slow, shares state, causes flaky failures. Testcontainers offers determinism.

**Acceptance criteria:**
- Candidate selected with rationale
- Setup implemented and connecting
- Compared with Compose flow
- Continue/stop recommendation documented

### T4.1 — Select candidate dependency/test

**Why:** Start simple — de-risk the idea with a manageable candidate.

**Goal:** Choose one dependency for the prototype.

**Scope:** Redis (simplest, fastest), Kafka (complex but high value), LocalStack (IAM). Prefer already-used, manageable, no big refactor.

**Acceptance criteria:**
- [ ] One candidate selected
- [ ] Rationale documented
- [ ] Scope agreed

### T4.2 — Implement Testcontainers setup

**Why:** A working setup is the only way to get real numbers.

**Goal:** Prototype Testcontainers for the selected dependency.

**Scope:** Container definition, Spring property wiring (`@DynamicPropertySource`), wait strategy, Cucumber integration, cleanup/isolation.

**Acceptance criteria:**
- [ ] Dependency starts via Testcontainers
- [ ] Test connects successfully
- [ ] Runs locally
- [ ] CI suitability assessed (from T1.4)

### T4.3 — Compare with docker-compose flow

**Why:** Decision should rest on like-for-like comparison.

**Goal:** Compare both flows on the same dependency.

**Scope:** Startup time, test runtime, complexity, local DX, CI suitability, isolation/determinism.

**Acceptance criteria:**
- [ ] Comparison documented
- [ ] Benefits and drawbacks identified
- [ ] Recommendation made

### T4.4 — Document findings & constraints

**Why:** Pilot only pays off if lessons are captured.

**Goal:** Document what the Testcontainers pilot showed.

**Scope:** What worked/didn't, performance, reliability, limitations, reuse policy (local enabled, CI disabled), next steps.

**Acceptance criteria:**
- [ ] Findings documented and shareable
- [ ] Constraints identified
- [ ] Recommendation available

---

## Story 5 — Docker Compose Rationalisation

**Goal:** Clarify which Compose services are needed for CI vs local.

**Why:** Compose files grow, serve mixed purposes, drag extra services into CI.

**Acceptance criteria:**
- All services mapped
- Services classified (CI-required / local-debug / optional)
- Reduced role recommended

### T5.1 — Map services started by docker-compose

**Why:** Cannot rationalise what is not mapped.

**Goal:** Complete inventory of Compose services.

**Scope:** Service name, image/build, dependencies, ports, purpose.

**Acceptance criteria:**
- [ ] All services listed
- [ ] Dependencies understood
- [ ] Unclear services flagged

### T5.2 — Classify services & usage

**Why:** Not every service is CI-needed — some are local-debug leftovers.

**Goal:** Classify by necessity and usage location.

**Scope:** Required for CI / local-debug only / optional / removable. Note where Compose is invoked (CI vs local).

**Acceptance criteria:**
- [ ] Required test deps identified
- [ ] Non-essential services identified
- [ ] CI vs local usage documented

### T5.3 — Recommend reduced Compose role

**Why:** Right-size Compose: lean in CI, useful locally.

**Goal:** Recommend what stays, what moves to Testcontainers, what stays local-only.

**Scope:** Target model: CI → Testcontainers, local → Compose, E2E → future ephemeral.

**Acceptance criteria:**
- [ ] Recommendation documented
- [ ] Risk/impact noted
- [ ] Reviewed with stakeholders

---

## Story 6 — CST-local vs ACP/ETO Ownership Assessment

**Goal:** Consolidate evidence, classify ownership (CST / ACP / DSA ETO), share.

**Why:** Pilot is only valuable if it ends in clear decisions and routed follow-up.

**Acceptance criteria:**
- Consolidated findings exist
- Each item classified with rationale
- Each item mapped to board/owner
- Findings shared, feedback captured

### T6.1 — Consolidate pilot findings

**Why:** Scattered evidence is hard to act on.

**Goal:** Single shareable findings summary.

**Scope:** Baseline + build results + Testcontainers comparison + Compose review + pipeline assessment. Before → after narrative.

**Acceptance criteria:**
- [ ] Single summary exists
- [ ] Links to supporting evidence
- [ ] Suitable for stakeholder sharing

### T6.2 — Classify ownership & recommend target board

**Why:** Classification only helps if it leads to action.

**Goal:** Classify into CST / ACP / DSA ETO and recommend board for each item.

**Scope:**
- CST/Cerberus Delivery: baseline, Dockerfile, `.dockerignore`, Testcontainers local, Compose review.
- ACP: `.drone.star` changes, DIND env, BuildKit enablement, CI cache, Testcontainers CI env.
- DSA ETO/Enabling: base images, shared templates, cross-project adoption, engineering standards.

**Acceptance criteria:**
- [ ] Each item classified with rationale
- [ ] Each mapped to CST / ACP / DSA ETO board
- [ ] No wider-impact item progressed without visibility

### T6.3 — Share findings with stakeholders

**Why:** Closes the loop — determines whether patterns move forward.

**Goal:** Share findings, capture feedback, agree next steps.

**Scope:** Pilot scope, baseline, build results, Testcontainers findings, ownership, next steps.

**Acceptance criteria:**
- [ ] Shared with agreed stakeholders
- [ ] Feedback captured
- [ ] Next steps agreed or documented

---

## Definition of Done (applies to every task)

A task is "Done" when:
- [ ] Task-specific acceptance criteria are all met
- [ ] Output is written down in a shareable form
- [ ] Assumptions or open questions are recorded
- [ ] Result is reviewed by at least one other person
- [ ] Status is updated on the status board

Additionally for measurement tasks:
- [ ] Metric captured using the standard fields (baseline, after, delta, method)
- [ ] Method documented so it can be repeated

Additionally for code/config change tasks:
- [ ] Change is small, focused, reviewable
- [ ] Compatibility/rollback risk noted
- [ ] No secrets added to repo or build context

---

## Ticket Creation Order

Create incrementally:
1. Epic: Container & CI/CD Optimisation Pilot — FDP Initial Scope
2. Story 1 → T1.1 → T1.2
3. Story 2 → T2.1 → T2.2
4. Story 3 → T3.1

Open the rest once boundaries are understood and baseline is underway.

---

*Feedback or questions? Contact the page owner or comment below.*
