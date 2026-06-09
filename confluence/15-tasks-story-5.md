# Task Definitions — Story 5: Docker Compose Rationalisation

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

Tasks for Story 5 (Docker Compose Rationalisation). Clarify which services are needed for CI vs local.

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


---

*Feedback or questions? Contact the page owner or comment below.*
