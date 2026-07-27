# Story 6 — Pilot Outcome, Ownership and Adoption

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** T3.4, current Story 4 evidence and the [T5.2 target-role decision](../../../solution/story-5/T5.2-decide-compose-role.md) · **Parallel with:** —

## Goal
Turn the validated pilot evidence into clear ownership routes and an explicit adopt, retain-as-candidate or stop decision for each outcome.

## Why
A PoC is useful only when reviewers can see what was actually proved, who can act, and what remains unapproved. This story keeps the evidence in supporting documents and makes the decision path concise.

## Boundaries / non-claims

- Measured, observed, structural and inferred evidence remain separate.
- Missing metrics are `not measured`; local evidence is not represented as CI evidence.
- The Docker layer-order result is only a local same-daemon warm-cache JAR-change observation of approximately 15–16x; no image-size, cold-build or CI improvement was demonstrated.
- The Redis pilot does not prove flaky-test improvement, Kafka/Schema Registry scope or full E2E replacement.
- A prototype, temporary repository experiment or prepared stakeholder pack is not production adoption, RepoSync approval or completed rollout.

## Acceptance criteria

- [x] Validated outcomes and non-claims are consolidated with evidence links.
- [x] Each candidate has a CST-local, RepoSync/platform or wider-owner route.
- [ ] Adopt/candidate/stop recommendations are reviewed with the relevant owners.
- [ ] Stakeholder feedback, approvals and next actions are recorded.

## Tasks

| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T6.1 | [Classify pilot outcomes and ownership routes](./task-1-classify-outcomes.md) | 4 | Must | Done — evidence prepared |
| T6.2 | [Decide the adoption route and publish the pilot outcome](./task-2-decide-adoption.md) | 2 | Must | Not completed — materials prepared |

**Supporting outputs:** [T6.1 evidence](../../../solution/story-6/T6.1-classify-outcomes-and-ownership.md) · [T6.2 evidence](../../../solution/story-6/T6.2-decide-adoption-route.md)

**Consolidation mapping:** [Story 5 and Story 6 consolidation](../STORY-5-6-CONSOLIDATION.md)
