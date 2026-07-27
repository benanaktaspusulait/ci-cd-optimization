# Task Definitions — Story 5: Docker Compose Rationalisation

| Field | Value |
|---|---|
| **Status** | Consolidated |
| **Last updated** | 2026-07-27 |
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

**Status:** Done — target-role recommendation prepared; implementation and adoption not approved.

**Depends on:** T5.1 evidence, T4.4 findings and Story 4 Summary.

**Primary output:** `solution/story-5/T5.2-decide-compose-role.md`

**Acceptance criteria:**

- [x] Every material T5.1 rationalisation candidate has an explicit target-role decision.
- [x] Keep, candidate, stop/not-now, local-utility and architectural decisions are distinguishable.
- [x] Current validated behaviour is separated from future candidates.
- [x] Full-E2E and local-debug safeguards are retained.
- [x] Missing functional, timing, CI and ownership evidence is explicit.
- [x] RepoSync/platform changes have a durable decision route.
- [x] Story 6 handoff candidates are explicit and not presented as approved work.
- [x] No prototype is presented as production-approved.
- [x] One canonical T5.2 solution document remains.

Legacy mapping: former T5.1 + T5.2 → T5.1; former T5.3 → T5.2.
