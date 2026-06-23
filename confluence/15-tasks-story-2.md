# Task Definitions — Story 2: Baseline & Pilot Scope

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-23 |
| **Last reviewed** | 2026-06-23 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

Tasks for Story 2 (Baseline & Pilot Scope). Without baseline data, no optimisation can be proved.

This story does not implement any optimisation. It only selects the pilot target and captures the baseline required to prove before/after impact.

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
| Labels | `baseline`, `repo-selection`, `portability` |
| Sprint | Week 1 |
| Depends on | T1.2 |
| Owner | TBC |
| Status | Not started |

**Why:** The pilot needs a single, representative target, but the recommendation should be portable. Comparing at least two candidates keeps one eye on RepoSync replication.

**Goal:** Compare at least two FDP repositories/services, then agree on one repository/service for the pilot.

**Scope:**

- Review at least two candidate FDP repositories/pipelines.
- Weigh pipeline duration, Docker Compose usage, integration-test complexity, current delivery priority/risk, and portability.
- Recommend one repository and record why.

**Candidate comparison matrix:**

| Criteria | Candidate A | Candidate B | Notes |
|----------|-------------|-------------|-------|
| Pipeline duration | TBC | TBC | Last N runs |
| Docker Compose usage | TBC | TBC | Low / Medium / High |
| Integration-test complexity | TBC | TBC | Low / Medium / High |
| Dockerfile ownership | TBC | TBC | RepoSync / repo-local |
| Testcontainers suitability | TBC | TBC | Low / Medium / High |
| Build optimisation potential | TBC | TBC | Low / Medium / High |
| Delivery risk | TBC | TBC | Low / Medium / High |
| Portability to other adaptors | TBC | TBC | Low / Medium / High |

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

**Recommended measurement window:**

- Use the last 5 successful runs where possible.
- Separately record failed/cancelled runs.
- Exclude obvious one-off infrastructure incidents unless they are recurring.
- Record measurement date and source.

**Acceptance criteria:**

- [ ] Baseline pipeline metrics are documented.
- [ ] Data source / measurement method is recorded.
- [ ] Metrics can be re-measured for before/after comparison.

**Baseline capture output:**

| Metric | Value | Source / method |
|--------|-------|-----------------|
| Average successful CI duration | TBC | Last 5 successful runs |
| Median successful CI duration | TBC | Last 5 successful runs |
| Slowest successful CI duration | TBC | Last 5 successful runs |
| Fastest successful CI duration | TBC | Last 5 successful runs |
| Build stage duration | TBC | Drone UI/API |
| Unit test stage duration | TBC | Drone UI/API or Maven logs |
| Integration test stage duration | TBC | Drone UI/API |
| Failed/cancelled runs in sample | TBC | Last N runs |
| Most expensive step by duration | TBC | Drone UI/API |
| Repeated setup time | TBC | apk/docker-compose install, waits |
| Measurement date | TBC | — |

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

**Docker baseline output:**

| Metric | Value | Source / method |
|--------|-------|-----------------|
| Local Docker build time | TBC | `time docker build ...` |
| CI Docker build time | TBC | Drone publish/build step logs |
| Final image size | TBC | `docker images` / registry metadata |
| Build context size | TBC | Docker build output / `du -sh` |
| Base image | TBC | Dockerfile |
| Runtime image family | TBC | JDK / JRE / slim / approved base |
| Trivy HIGH/CRITICAL count | TBC | Current CI Trivy output |

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

**Integration-test baseline output:**

| Metric / Area | Value | Source / method |
|---------------|-------|-----------------|
| Integration test command | TBC | Drone step / Maven command |
| Maven profile used | TBC | `ci-snapshot`, etc. |
| Compose file path | TBC | docker-compose.yml |
| Required infra services | TBC | Kafka, Redis, LocalStack, etc. |
| Required custom app services | TBC | Aggregators, command-adaptor |
| Startup/wait duration | TBC | Drone logs |
| Actual test execution duration | TBC | Maven/Failsafe logs |
| Known flaky failures | TBC | Recent failed runs |
| Local vs CI topology differences | TBC | Story 1 findings / pipeline map |

### T2.5 — Produce Baseline Summary and Re-Measurement Method

| Field | Value |
|-------|-------|
| Type | Documentation |
| Estimate | 1 |
| Priority | Must |
| Labels | `baseline`, `summary`, `measurement` |
| Sprint | Week 1 |
| Depends on | T2.2, T2.3, T2.4 |
| Owner | TBC |
| Status | Not started |

**Why:** T2.2, T2.3 and T2.4 capture separate baseline slices. A short summary is needed so stakeholders can agree the before state and later compare it with Stories 3, 4 and 5.

**Goal:** Create a concise baseline summary and re-measurement method for before/after comparison.

**Scope:**

- Consolidate CI pipeline, Docker build/image, and integration-test baseline metrics.
- Record the exact measurement method used for each metric.
- Separate measured values from missing data or assumptions.
- Document known limitations so later comparisons do not overclaim improvement.

**Acceptance criteria:**

- [ ] Baseline summary exists.
- [ ] Measurement method is documented.
- [ ] Metrics are separated into CI pipeline, Docker build/image, and integration-test sections.
- [ ] Known limitations and missing data are recorded.
- [ ] Stakeholders can agree this is the baseline for later comparison.

---

*Feedback or questions? Contact the page owner or comment below.*
