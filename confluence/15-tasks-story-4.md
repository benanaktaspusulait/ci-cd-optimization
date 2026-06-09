# Task Definitions — Story 4: Testcontainers Pilot

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

Tasks for Story 4 (Testcontainers Pilot). Prove isolation and determinism for one integration dependency.

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

*Feedback or questions? Contact the page owner or comment below.*
