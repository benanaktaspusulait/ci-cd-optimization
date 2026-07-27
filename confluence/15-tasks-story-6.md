# Task Definitions — Story 6: Pilot Outcome, Ownership and Adoption

| Field | Value |
|---|---|
| **Status** | Consolidated; adoption/share-out incomplete |
| **Last updated** | 2026-07-23 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `ownership` |

## Story goal

Turn validated pilot evidence into clear ownership routes and an explicit adopt, retain-as-candidate or stop decision for each outcome.

**Why:** A PoC is useful only when reviewers can see what was proved, who can act and what remains unapproved.

**Dependencies:** T3.4, current Story 4 evidence and T5.2.

**Boundaries:** Evidence types remain separate. Missing metrics are `not measured`. Local evidence is not CI evidence. The Docker result is only an approximately 15–16x local same-daemon warm-cache JAR-change observation; no image-size, cold-build or CI improvement was shown. Redis-only evidence does not prove flaky-test improvement, Kafka/Schema Registry scope or full-E2E replacement. Prepared materials do not prove approval or adoption.

## T6.1 — Classify Pilot Outcomes and Ownership Routes

**Goal:** Classify validated outcomes, limitations and follow-ups by evidence type and owner.

**Scope:** Story 1–5 evidence, CST-local vs RepoSync/platform vs wider ownership, temporary experiments and durable routes.

**Status:** Done — evidence prepared.

**Depends on:** T3.4, current Story 4 evidence and T5.2.

**Primary output:** `solution/story-6/T6.1-classify-outcomes-and-ownership.md`

**Acceptance criteria:**

- [x] Every outcome links to its primary evidence.
- [x] Measured, observed, structural and inferred claims are separated.
- [x] Missing metrics are `not measured`.
- [x] Each candidate has an owner/route and rationale.
- [x] Temporary experiments are separated from durable adoption.
- [x] Production, CI and broad-rollout non-claims are explicit.

## T6.2 — Decide the Adoption Route and Publish the Pilot Outcome

**Goal:** Agree adopt, retain-as-candidate or stop decisions, publish the concise outcome and record owner feedback.

**Scope:** Priorities, CI validation, rollout constraints, stakeholder share-out, decisions and unresolved items.

**Status:** Not completed — materials prepared; review and feedback not evidenced.

**Depends on:** T6.1.

**Primary output:** `solution/story-6/T6.2-decide-adoption-route.md`

**Acceptance criteria:**

- [ ] Each candidate has an agreed disposition.
- [ ] Owner actions are prioritised and routed.
- [x] CI validation and rollout constraints are explicit.
- [ ] The outcome is shared with agreed stakeholders.
- [ ] Feedback, approvals and unresolved decisions are recorded.
- [x] No recommendation is described as adopted without evidence.

Legacy mapping: former T6.1 + T6.2 → T6.1; former T6.3 → T6.2.
