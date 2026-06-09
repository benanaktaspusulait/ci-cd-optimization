# Task Definitions — Story 2: Baseline & Pilot Scope

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

Tasks for Story 2 (Baseline & Pilot Scope). Without baseline data, no optimisation can be proved.

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

*Feedback or questions? Contact the page owner or comment below.*
