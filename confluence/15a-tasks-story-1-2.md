# Task Definitions — Story 1 & 2 (Assessment & Baseline)

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

Full task definitions for Story 1 (Pipeline Assessment) and Story 2 (Baseline & Pilot Scope). These are gate stories — subsequent work depends on their findings.

---

## Story 1 — Drone/RepoSync Pipeline Assessment

**Depends on:** none. **Gate:** entire pilot.

**Goal:** Understand the centrally managed Drone pipeline structure, establish what can be changed locally vs what requires RepoSync/platform coordination, and assess feasibility of Testcontainers and BuildKit in the current CI setup.

**Scope boundary:** this story assesses the **CI pipeline** (per-adaptor repo, `.drone.star`). The **deploy pipeline** (MMA service repo -> Helm -> Kubernetes) is context only and is not optimised by this pilot.

**Why:** FDP adaptor pipelines are generated from a `.drone.star` file managed via RepoSync. Local pipeline changes are not durable, so the pilot must separate repo-local proof points from reusable changes that should be proposed through ACP/RepoSync.

**Acceptance criteria:**

- [ ] `.drone.star` pipeline structure is documented (steps, services, DIND usage).
- [ ] Local vs RepoSync-controlled change boundaries are clearly defined.
- [ ] CI pipeline steps and Docker Compose usage are mapped.
- [ ] Testcontainers feasibility in Drone is assessed (DIND access, Ryuk, DOCKER_HOST).
- [ ] BuildKit feasibility in current DIND setup is assessed.
- [ ] Findings inform which later stories can proceed locally vs need central coordination.

### T1.1 — Review `.drone.star` Pipeline Structure

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 2 |
| Priority | Must |
| Labels | `drone`, `pipeline`, `reposync`, `assessment` |
| Sprint | Week 1 |
| Depends on | — |
| Owner | TBC |
| Status | Not started |

**Why:** The `.drone.star` file defines the entire CI pipeline. Understanding its structure is prerequisite for every other pilot task.

**Goal:** Document the current Drone pipeline structure: what runs, in what order, with what services.

**Scope:**

- Obtain and review the `.drone.star` source from the RepoSync source repo.
- Document pipeline types (CI, ECR, Artifactory, etc.).
- Document steps within each pipeline (order, images, commands).
- Document services (DIND, Kafka, Redis, etc.).
- Note existing Testcontainers-related configuration, for example `TESTCONTAINERS_RYUK_DISABLED`.
- Note how MR/pull_request events are handled.

**Acceptance criteria:**

- [ ] Pipeline types and their purposes are documented.
- [ ] Step ordering and dependencies are mapped.
- [ ] DIND service configuration is documented.
- [ ] Existing Testcontainers workarounds are noted.
- [ ] MR pipeline behaviour is confirmed (blank or full).

### T1.2 — Identify Local vs RepoSync-Controlled Boundaries

| Field | Value |
|-------|-------|
| Type | Analysis |
| Estimate | 1 |
| Priority | Must |
| Labels | `drone`, `reposync`, `ownership`, `boundaries` |
| Sprint | Week 1 |
| Depends on | T1.1 |
| Owner | TBC |
| Status | Not started |

**Why:** RepoSync owns the central pipeline source of truth. The pilot must know exactly which files/changes are repo-local vs centrally controlled.

**Goal:** Produce a table of what can change locally and what should become an ACP/RepoSync change request or recommendation.

**Scope:**

- **Repo-local:** Dockerfile, `.dockerignore`, Maven profiles, `pom.xml` dependencies, test source code, `application-*.yml`, docker-compose files used by Maven plugin.
- **RepoSync-controlled:** `.drone.star`, pipeline steps/ordering, DIND image, Drone secrets, service definitions.
- **Unclear / confirm:** docker-compose files invoked by the pipeline, Maven step environment variables.

**Acceptance criteria:**

- [ ] Local vs central classification exists.
- [ ] RepoSync source repo and change request process are identified.
- [ ] Pilot team knows who to contact for central changes.
- [ ] Centrally controlled files that appear local are flagged.

### T1.3 — Map CI Steps, DIND Usage and Docker Compose Commands

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 2 |
| Priority | Must |
| Labels | `drone`, `docker-compose`, `dind`, `mapping` |
| Sprint | Week 1 |
| Depends on | T1.1 |
| Owner | TBC |
| Status | Not started |

**Why:** The current CI pipeline is heavy: multiple compose cycles, wait containers, aggregator startups and Maven builds. Optimisation needs a precise map.

**Goal:** Produce a step-by-step map of the CI pipeline with timing data where available.

**Scope:**

- Step name and image.
- Docker Compose commands executed.
- DIND interactions (`docker build`, `docker push`, compose up).
- Wait/health-check mechanisms.
- Estimated or measured duration from Drone UI.
- Potential duplicate work.
- CI pipeline vs deploy pipeline boundary.

Expected pipeline map:

```text
RepoSync Version -> Secrets -> Wait for Docker -> Extract Info ->
Kafka & Redis (compose) -> Aggregators (compose) -> mvn clean install ->
Command Adaptor (compose) -> Pre-Integration Tests (compose) ->
Integration Tests (compose) -> Sonar -> Trivy -> Slack
```

**Acceptance criteria:**

- [ ] All CI pipeline steps are listed with their purpose.
- [ ] Docker Compose commands and services per step are documented.
- [ ] DIND usage points are identified.
- [ ] Step durations are captured where available.
- [ ] Duplicate work or unnecessary waits are flagged.

### T1.4 — Assess Testcontainers Feasibility in Drone/DIND

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 2 |
| Priority | Must |
| Labels | `testcontainers`, `drone`, `dind`, `feasibility` |
| Sprint | Week 1 |
| Depends on | T1.1, T1.3 |
| Owner | TBC |
| Status | Not started |

**Why:** Testcontainers requires Docker daemon access from the test JVM. Drone provides DIND, but the Maven step may not be configured to reach it.

**Goal:** Determine whether Testcontainers can run in Drone CI and document constraints.

**Scope:**

- Can the Maven step access `DOCKER_HOST=tcp://docker:2375`?
- Is `TESTCONTAINERS_RYUK_DISABLED=true` required, and what are cleanup implications?
- Is `TESTCONTAINERS_CHECKS_DISABLE=true` needed?
- Can Testcontainers pull images through DIND?
- Is there a Drone step timeout risk?
- Would Testcontainers conflict with existing docker-compose usage?

Known findings:

- ECR pipeline Maven step already has `TESTCONTAINERS_RYUK_DISABLED=true`.
- DIND service is named `docker` and exposes port 2375.
- Maven step image is `quay.io/ukhomeofficedigital/ileap-java17-mvn`.

**Decision outcomes:**

- [ ] CI feasible: required env vars TBC.
- [ ] CI feasible with constraints: limitations TBC.
- [ ] Local only: Testcontainers cannot run in CI; pilot stays local-only.

**Acceptance criteria:**

- [ ] Docker daemon accessibility from Maven step is confirmed or denied.
- [ ] Required environment variables are documented.
- [ ] Ryuk disabled implications are understood and documented.
- [ ] Feasible / feasible with constraints / local only decision is made.
- [ ] Finding informs Story 4 scope.

### T1.5 — Assess BuildKit/Cache Feasibility

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 1 |
| Priority | Should |
| Labels | `buildkit`, `drone`, `dind`, `cache`, `feasibility` |
| Sprint | Week 1 |
| Depends on | T1.1, T1.3 |
| Owner | TBC |
| Status | Not started |

**Why:** ADR-0004 proposes BuildKit multi-stage builds with cache mounts and remote registry cache, but Drone DIND support is uncertain.

**Goal:** Determine what level of BuildKit optimisation is feasible in Drone/DIND.

**Scope:**

- Is `DOCKER_BUILDKIT=1` set or settable?
- Does DIND include `docker buildx`?
- Can `--mount=type=cache` work inside DIND?
- Can `--cache-from=type=registry` read from the internal registry?
- Can `--cache-to=type=registry` write to it?
- Which items require `.drone.star` / RepoSync change?

Likely outcomes:

- Multi-stage builds almost certainly work.
- Local cache mounts work per-build but are lost between CI runs.
- Remote registry cache likely requires ACP/ETO.

**Acceptance criteria:**

- [ ] BuildKit availability in DIND is confirmed or denied.
- [ ] `docker buildx` availability is confirmed or denied.
- [ ] Cache mount behaviour in CI is documented.
- [ ] Remote cache feasibility is assessed.
- [ ] Finding informs Story 3 scope.

---

## Story 2 — Baseline & Pilot Scope

**Depends on:** Story 1.

**Goal:** Compare at least two candidate pipelines/repos, select the pilot repository, and capture a trustworthy before state.

**Why:** Without a baseline there is no way to prove whether an optimisation actually helped.

**Acceptance criteria:**

- [ ] At least two candidate pipelines/repos compared for portability.
- [ ] Pilot repository selected with documented rationale.
- [ ] Pipeline, build, image-size and integration-test baselines captured.
- [ ] Measurement method recorded for after comparison.
- [ ] Baseline reviewed and agreed with stakeholders.

### T2.1 — Compare Candidate Pipelines and Select Pilot Repo

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 1 |
| Priority | Must |
| Depends on | T1.2 |
| Owner | TBC |
| Status | Not started |

**Why:** The pilot needs a single, representative target, but the recommendation should be portable. Comparing at least two candidates keeps one eye on RepoSync replication.

**Goal:** Compare at least two FDP repositories/services, then agree on one repository/service for the pilot.

**Scope:**

- Review at least two candidate FDP repositories/pipelines.
- Weigh pipeline duration, Docker Compose usage, integration-test complexity, current delivery priority/risk, and portability.
- Recommend one repository and record why.

**Acceptance criteria:**

- [ ] At least two candidate repositories/pipelines are compared.
- [ ] One candidate repository/service is selected.
- [ ] Selection rationale is documented.
- [ ] Portability notes are captured.
- [ ] Pilot scope is agreed with stakeholders.

**Selection output:**

| Field | Value |
|-------|-------|
| Selected repository | TBC |
| Compared candidate(s) | TBC |
| Application project location | TBC |
| Delivery / source-control environment | TBC |
| Issue / board tracker | TBC |
| Primary language / build tool | TBC |
| Selection rationale | TBC |
| Portability notes | TBC |
| Stakeholder who agreed scope | TBC |

### T2.2 — Capture CI/CD Pipeline Baseline

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 2 |
| Priority | Must |
| Labels | `baseline`, `pipeline`, `metrics` |
| Sprint | Week 1 |
| Depends on | T2.1 |
| Owner | TBC |
| Status | Not started |

**Why:** Pipeline duration is the headline metric stakeholders care about.

**Goal:** Record current CI/CD pipeline timings for the selected repository.

**Scope:**

- Capture average pipeline duration.
- Break down build, unit test and integration test stages.
- Capture failed-pipeline frequency if available.
- Note data source and method, for example last N runs from CI history.

**Acceptance criteria:**

- [ ] Baseline pipeline metrics are documented.
- [ ] Data source / measurement method is recorded.
- [ ] Metrics can be re-measured for before/after comparison.

### T2.3 — Capture Docker Build and Image-Size Baseline

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 1 |
| Priority | Must |
| Labels | `baseline`, `docker`, `image-size` |
| Sprint | Week 1 |
| Depends on | T2.1 |
| Owner | TBC |
| Status | Not started |

**Why:** Build optimisation targets build time and image size directly.

**Goal:** Record current Docker build time and image size.

**Scope:** Local Docker build time, CI Docker build time if available, final image size, current base image/build approach.

**Acceptance criteria:**

- [ ] Current Docker build duration is documented.
- [ ] Current final image size is documented.
- [ ] Current base image and build approach are identified.

### T2.4 — Capture Integration-Test Baseline

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 2 |
| Priority | Must |
| Labels | `baseline`, `integration-test`, `docker-compose` |
| Sprint | Week 1 |
| Depends on | T2.1 |
| Owner | TBC |
| Status | Not started |

**Why:** Testcontainers and Compose rationalisation both depend on understanding how integration tests run today.

**Goal:** Document how integration tests currently start and behave.

**Scope:** Start command/pipeline step, Compose dependencies, startup/wait time, flaky/environment-related issues.

**Acceptance criteria:**

- [ ] Current integration-test setup is documented.
- [ ] Required dependencies are listed.
- [ ] Known pain points / flaky behaviours are captured.

---


---

*Feedback or questions? Contact the page owner or comment below.*
