# Task Definitions — Story 3 & 4 (Build Optimisation & Testcontainers)

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

Full task definitions for Story 3 (Docker Build Optimisation) and Story 4 (Testcontainers Pilot). These run in parallel after baseline capture.

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
.ci
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


---

*Feedback or questions? Contact the page owner or comment below.*
