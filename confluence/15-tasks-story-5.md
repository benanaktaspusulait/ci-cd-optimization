# Task Definitions — Story 5: Docker Compose Rationalisation

| Field | Value |
|---|---|
| **Status** | Consolidated |
| **Last updated** | 2026-07-23 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

## Story goal

Define an evidence-backed target role for Docker Compose across CI, full E2E and local debugging, without changing current default flows.

**Why:** Reviewers need one validated scope and one safe decision, not separate tickets for mapping, classification and documentation.

**Dependencies:** T1.3, T1.4 and current Story 4 evidence.

**Boundaries:** No Compose, Maven-profile, default CI or production change. Redis-only evidence does not prove Kafka/Schema Registry or full-E2E replacement. CI, timing and reliability benefits remain `not measured` unless equivalent evidence exists. Durable RepoSync-controlled changes require the RepoSync/platform route.

## T5.1 — Validate Current Compose Scope

**Goal:** Validate service topology and CI, full-E2E and local-debug roles in one evidence record.

**Scope:** Services, dependencies, invocation paths, classifications, uncertainties and relevant Story 1–4 evidence.

**Status:** Done — evidence prepared.

**Depends on:** T1.3, T1.4 and current Story 4 evidence.

**Primary output:** `solution/story-5/T5.1-validate-compose-scope.md`

**Acceptance criteria:**

- [x] All defined services and material dependencies are represented.
- [x] CI, full E2E and local-debug use are distinguished.
- [x] Facts, structural observations and inferences are labelled.
- [x] Unmeasured behaviour is recorded without an optimisation claim.
- [x] Decision-relevant evidence is linked.

## T5.2 — Decide the Target Compose Role

**Goal:** Recommend what Compose should retain and which changes remain validation/ownership candidates.

**Scope:** Keep/candidate/stop decisions, full-E2E and local safeguards, missing evidence and the durable adoption route.

**Status:** Done — recommendation prepared; adoption not approved.

**Depends on:** T5.1 and current Story 4 evidence.

**Primary output:** `solution/story-5/T5.2-decide-compose-role.md`

**Acceptance criteria:**

- [x] Keep, candidate and stop/not-now decisions are explicit.
- [x] Full-E2E and local-debug safeguards are retained.
- [x] Missing functional, timing and CI evidence is identified.
- [x] RepoSync/platform changes have a durable route.
- [x] No prototype is presented as production-approved.

Legacy mapping: former T5.1 + T5.2 → T5.1; former T5.3 → T5.2.
