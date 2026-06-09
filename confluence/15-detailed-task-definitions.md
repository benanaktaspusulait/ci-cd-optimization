# Detailed Task Definitions

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

This page gives the full story and task intent — why, goal, scope and acceptance criteria — for every task. The short Jira-ready list is on the [Backlog Summary](08-backlog-summary.md) page.

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
| GitLab project URL | TBC |
| GitLab environment | TBC |
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

## Story 3 — Docker Build Optimisation

**Depends on:** Story 2. **Parallel with:** Story 4.

**Goal:** Apply practical Dockerfile/build-context improvements and prove impact with before/after numbers.

**Drone constraint:** Multi-stage builds and `.dockerignore` work locally and in any Docker environment. BuildKit cache mounts work locally but are ephemeral in CI. Remote registry cache requires ACP/ETO.

**Why:** Build time and image size are recurring friction. Layer ordering, `.dockerignore`, cache mounts and multi-stage builds can help without changing application behaviour.

**Acceptance criteria:**

- [ ] Current Dockerfile and build context reviewed.
- [ ] `.dockerignore` present and appropriate.
- [ ] At least one layering/cache improvement applied.
- [ ] Build time and image size compared before/after, with keep/adjust recommendation.

### T3.1 — Review Current Dockerfile and Build Context

| Field | Value |
|-------|-------|
| Type | Analysis |
| Estimate | 2 |
| Priority | Must |
| Labels | `docker`, `dockerfile`, `build-context` |
| Sprint | Week 2 |
| Depends on | T2.1 |
| Owner | TBC |
| Status | Not started |

**Why:** Optimisation should be evidence-led, not guesswork.

**Goal:** Document current Dockerfile structure/build context and identify optimisation opportunities.

**Scope:** Base image, layer ordering, dependency installation, COPY instructions, context size, unnecessary files.

**Acceptance criteria:**

- [ ] Current Dockerfile structure is documented.
- [ ] Cache-invalidation risks are identified.
- [ ] Prioritised optimisation opportunities are produced.

### T3.2 — Add or Validate `.dockerignore`

| Field | Value |
|-------|-------|
| Type | Implementation |
| Estimate | 1 |
| Priority | Must |
| Labels | `docker`, `dockerignore`, `build-context` |
| Sprint | Week 2 |
| Depends on | T3.1 |
| Owner | TBC |
| Status | Not started |

**Why:** A missing or weak `.dockerignore` sends unnecessary files into the build context.

**Goal:** Ensure the pilot repo has a lean `.dockerignore`.

**Suggested baseline:**

```gitignore
.git
.gitlab
target
build
.idea
.vscode
*.iml
*.log
.DS_Store
.tmp
```

**Acceptance criteria:**

- [ ] `.dockerignore` exists and is appropriate.
- [ ] Unnecessary files are excluded.
- [ ] Build-context reduction is noted where measurable.

### T3.3 — Apply Dockerfile Layering / Cache Improvement

| Field | Value |
|-------|-------|
| Type | Implementation |
| Estimate | 2 |
| Priority | Must |
| Labels | `docker`, `dockerfile`, `layering`, `cache` |
| Sprint | Week 2 |
| Depends on | T3.1 |
| Owner | TBC |
| Status | Not started |

**Why:** Biggest build-time wins usually come from ordering layers so dependencies are cached separately from source code.

**Goal:** Apply a single, well-understood layering or cache improvement.

**Scope:** Dependency metadata before source, separate dependency resolution from build, multi-stage builds, cache mounts.

**Reference pattern:**

```dockerfile
# syntax=docker/dockerfile:1
FROM company/java17-maven-base:1.0 AS deps
WORKDIR /app
COPY pom.xml .mvn mvnw ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B dependency:go-offline

FROM deps AS build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B package -DskipTests

FROM company/java17-runtime-base:1.0
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
```

Apply one focused change at a time so the effect can be attributed clearly.

**Acceptance criteria:**

- [ ] One layering/cache change is applied.
- [ ] Expected benefit is described.
- [ ] Compatibility risks or concerns are noted.
- [ ] Built image passes Trivy scan without new Critical vulnerabilities (non-blocking report).

### T3.4 — Measure Local and CI Build Impact

| Field | Value |
|-------|-------|
| Type | Analysis |
| Estimate | 2 |
| Priority | Should |
| Labels | `docker`, `metrics`, `before-after` |
| Sprint | Week 3 |
| Depends on | T3.3 |
| Owner | TBC |
| Status | Not started |

**Why:** A change is only worth keeping if it measurably helps.

**Goal:** Quantify effect on build time and image size.

**Scope:** Local build before/after, CI build before/after if available, final image size before/after.

**Acceptance criteria:**

- [ ] Before/after build metrics are captured.
- [ ] Improvement or regression is documented.
- [ ] Keep/adjust recommendation is made.

---

## Story 4 — Testcontainers Pilot

**Depends on:** Story 2. **Parallel with:** Story 3.

**Goal:** Prove whether Testcontainers can replace part of docker-compose integration setup for one dependency, with better isolation and determinism.

**Drone constraint:** CI feasibility depends on Story 1 findings. If CI is not feasible, this story stays local-only.

**Why:** Full Compose setups can be slow, share hidden state and cause flaky environment-dependent failures. Stronger value is reliability and local/CI consistency, not only speed.

**Acceptance criteria:**

- [ ] One candidate dependency/test selected with rationale.
- [ ] Testcontainers setup implemented or prototyped and connecting successfully.
- [ ] Flow compared with existing docker-compose flow.
- [ ] Findings, constraints and continue/stop recommendation documented.

### T4.1 — Select Candidate Dependency/Test

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 1 |
| Priority | Must |
| Labels | `testcontainers`, `integration-test`, `selection` |
| Sprint | Week 2 |
| Depends on | T2.1 |
| Owner | TBC |
| Status | Not started |

**Why:** The first pilot should de-risk the idea, not stress-test it.

**Goal:** Choose one integration dependency/test.

**Scope:** Assess Redis, Kafka, Schema Registry, LocalStack. Prefer an already-used dependency with manageable setup complexity and useful validation value.

**Acceptance criteria:**

- [ ] Candidate dependency/test is selected.
- [ ] Selection rationale is documented.
- [ ] Pilot scope is agreed before implementation.

### T4.2 — Implement Testcontainers Setup

| Field | Value |
|-------|-------|
| Type | Implementation |
| Estimate | 3 |
| Priority | Must |
| Labels | `testcontainers`, `integration-test`, `implementation` |
| Sprint | Week 2 |
| Depends on | T4.1 |
| Owner | TBC |
| Status | Not started |

**Why:** A working setup is the only way to get real numbers and a real developer-experience signal.

**Goal:** Implement or prototype Testcontainers for the selected dependency.

**Scope:** Container definition, property wiring, readiness/wait strategy, cleanup/isolation.

**Reference pattern:**

```java
@Container
static KafkaContainer kafka = new KafkaContainer(
    DockerImageName.parse("confluentinc/cp-kafka:7.6.0"));

@DynamicPropertySource
static void props(DynamicPropertyRegistry r) {
    r.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
}
```

Use the same dependency image/tag as the Compose baseline unless T4.1 documents a reason to change it.

**Acceptance criteria:**

- [ ] Dependency starts via Testcontainers.
- [ ] Test connects successfully.
- [ ] Setup runs locally.
- [ ] CI suitability is assessed or noted.

### T4.3 — Compare with Docker Compose Flow

| Field | Value |
|-------|-------|
| Type | Analysis |
| Estimate | 2 |
| Priority | Should |
| Labels | `testcontainers`, `docker-compose`, `comparison` |
| Sprint | Week 3 |
| Depends on | T4.2 |
| Owner | TBC |
| Status | Not started |

**Why:** Adoption should rest on like-for-like comparison.

**Goal:** Compare Testcontainers vs docker-compose for the selected dependency.

**Scope:** Startup time, test runtime, complexity, developer experience, CI suitability, isolation/determinism.

**Acceptance criteria:**

- [ ] Comparison is documented across the dimensions above.
- [ ] Benefits and drawbacks are identified.
- [ ] Recommendation is made on whether to continue.

### T4.4 — Document Findings and Constraints

| Field | Value |
|-------|-------|
| Type | Documentation |
| Estimate | 1 |
| Priority | Should |
| Labels | `testcontainers`, `documentation`, `findings` |
| Sprint | Week 3 |
| Depends on | T4.3 |
| Owner | TBC |
| Status | Not started |

**Why:** A pilot only pays off if lessons are captured.

**Goal:** Document what the Testcontainers pilot showed, including limits and recommendation.

**Scope:** What was tested, what worked/did not, performance observations, reliability/isolation observations, limitations, next steps. Reuse may be enabled locally but disabled in CI.

**Acceptance criteria:**

- [ ] Findings are documented and shared.
- [ ] Constraints are clearly identified.
- [ ] Recommendation is available for stakeholders.

---

## Story 5 — Docker Compose Rationalisation

**Depends on:** Story 4.

**Goal:** Clarify which Compose services are needed for CI integration tests vs local debugging, and recommend a reduced/clearer role.

**Why:** Compose files tend to grow and serve mixed purposes. Separating CI from local use reduces overhead without removing useful developer tooling.

**Acceptance criteria:**

- [ ] All Compose services mapped.
- [ ] Services classified as CI-required / local-debug only / optional / removable.
- [ ] CI vs local usage separated; mixed-purpose usage flagged.
- [ ] Reduced Compose role recommended with risk/impact.

### T5.1 — Map Services Started by Docker Compose

| Field | Value |
|-------|-------|
| Type | Research |
| Estimate | 1 |
| Priority | Must |
| Labels | `docker-compose`, `mapping`, `inventory` |
| Sprint | Week 3 |
| Depends on | T4.4 |
| Owner | TBC |
| Status | Not started |

**Why:** You cannot rationalise what you have not mapped.

**Goal:** Produce complete inventory of services the pilot repo starts via docker-compose.

**Scope:** Service name, image/build source, dependency relationships, exposed ports, purpose.

**Acceptance criteria:**

- [ ] All Compose services are listed.
- [ ] Dependencies between services are understood.
- [ ] Services with unclear purpose are flagged for review.

### T5.2 — Classify Services and Usage

| Field | Value |
|-------|-------|
| Type | Analysis |
| Estimate | 2 |
| Priority | Must |
| Labels | `docker-compose`, `classification`, `ci-vs-local` |
| Sprint | Week 3 |
| Depends on | T5.1 |
| Owner | TBC |
| Status | Not started |

**Why:** Not every service in Compose is needed for CI tests.

**Goal:** Classify each Compose service by necessity and usage location.

**Scope:** Required for integration tests, local-debug only, optional/unclear, removable from CI. Note CI vs local invocation and mixed-purpose files.

**Acceptance criteria:**

- [ ] Required test dependencies are identified.
- [ ] Non-essential services are identified.
- [ ] CI vs local usage is documented.
- [ ] Uncertainty is recorded for follow-up.

### T5.3 — Recommend Reduced Compose Role

| Field | Value |
|-------|-------|
| Type | Documentation |
| Estimate | 2 |
| Priority | Should |
| Labels | `docker-compose`, `recommendation`, `rationalisation` |
| Sprint | Week 4 |
| Depends on | T4.4, T5.2 |
| Owner | TBC |
| Status | Not started |

**Why:** The aim is to right-size Compose: lean in CI, still useful locally.

**Goal:** Recommend a reduced/clarified Compose role.

**Scope:** What remains in Compose, what could move to Testcontainers, what remains for local debugging, what should not change during the pilot.

```text
CI integration tests   -> prefer Testcontainers where suitable
Local manual debugging -> keep Docker Compose where useful
E2E / exploratory      -> consider ephemeral environments selectively
```

**Acceptance criteria:**

- [ ] Recommendation is documented.
- [ ] Risk / impact is noted.
- [ ] Recommendation is reviewed with stakeholders.

---

## Story 6 — Findings, Ownership and Recommendations

**Depends on:** Stories 3, 4, 5.

**Goal:** Consolidate pilot evidence, classify each item into CST/Cerberus Delivery, ACP, or DSA ETO/Enabling/CIT, and recommend the target operating model for reusable patterns.

**Why:** A pilot is only valuable if it ends in a clear decision.

**Acceptance criteria:**

- [ ] Consolidated findings summary exists.
- [ ] Each item classified with rationale.
- [ ] Each candidate mapped to suggested board/owner.
- [ ] Target operating model explains what should be replicated through ACP/RepoSync.
- [ ] Findings and next steps shared; feedback captured.

### T6.1 — Consolidate Pilot Findings

| Field | Value |
|-------|-------|
| Type | Documentation |
| Estimate | 2 |
| Priority | Must |
| Labels | `findings`, `summary`, `consolidation` |
| Sprint | Week 4 |
| Depends on | T3.4, T4.4, T5.3 |
| Owner | TBC |
| Status | Not started |

**Why:** Evidence spread across stories is hard to act on.

**Goal:** Bring all pilot evidence into one shareable findings summary.

**Scope:** Baseline, build results, Testcontainers comparison, Compose review, pipeline assessment; present before -> after / observations narrative; classify CST-local, RepoSync/platform, ETO/wider; include target operating model.

**Acceptance criteria:**

- [ ] Single consolidated findings summary exists.
- [ ] It links to supporting story evidence.
- [ ] It includes target operating model / RepoSync distribution recommendation.
- [ ] It is suitable for stakeholder sharing.

### T6.2 — Classify Ownership and Recommend Target Board

| Field | Value |
|-------|-------|
| Type | Analysis |
| Estimate | 2 |
| Priority | Must |
| Labels | `ownership`, `cst-vs-eto`, `classification`, `target-board` |
| Sprint | Week 4 |
| Depends on | T6.1 |
| Owner | TBC |
| Status | Not started |

**Why:** Some improvements are CST-local; others touch ACP CI tooling or wider platform patterns. Classification without board/owner recommendation is incomplete.

**Goal:** Classify each optimisation item and recommend owner/board.

**Scope:**

- **CST / Cerberus Delivery:** baseline measurement, Dockerfile review, `.dockerignore`, local layering experiment, Testcontainers local prototype, Compose review.
- **ACP:** `.drone.star` / RepoSync changes, DIND environment, BuildKit enablement, Testcontainers CI env vars, CI cache infrastructure.
- **DSA ETO / Enabling / CIT:** org base images, shared templates, reusable Testcontainers libraries, cross-project adoption model, remote cache infrastructure.

**Recommend:** CST board, ACP board, DSA ETO / Enabling board, shared visibility only, or further discussion needed.

**Acceptance criteria:**

- [ ] Each item is classified with rationale.
- [ ] Each item is mapped to suggested owner/board.
- [ ] ACP-owned items identify whether they should become RepoSync MR candidates.
- [ ] No wider-impact item progresses without appropriate visibility.
- [ ] Assumptions are documented.

### T6.3 — Share Findings with Stakeholders

| Field | Value |
|-------|-------|
| Type | Documentation |
| Estimate | 1 |
| Priority | Should |
| Labels | `stakeholders`, `communication`, `findings` |
| Sprint | Week 4 |
| Depends on | T6.2 |
| Owner | TBC |
| Status | Not started |

**Why:** The pilot's purpose is to inform a decision.

**Goal:** Share consolidated findings and ownership recommendations with agreed engineering stakeholders, and capture feedback.

**Scope:** Pilot scope, baseline findings, build optimisation results, Testcontainers findings, ownership recommendations, suggested next steps.

**Acceptance criteria:**

- [ ] Findings are shared with agreed stakeholders.
- [ ] Feedback is captured.
- [ ] Next steps are agreed or documented.

---

*Feedback or questions? Contact the page owner or comment below.*
