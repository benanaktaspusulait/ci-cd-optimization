# Story 5 — Docker Compose Rationalisation

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** T1.3, T1.4 and current Story 4 evidence · **Parallel with:** —

## Goal
Define an evidence-backed target role for Docker Compose across CI, full E2E and local debugging, without changing the current default flows.

## Why
The repository uses Compose in more than one orchestration path. Reviewers need one clear account of what is used and one safe recommendation, rather than separate tickets for mapping, classification and documentation.

## Boundaries / non-claims

- This story maps and recommends; it does not change Compose, Maven profiles, CI or production defaults.
- Full E2E, Redis-only and Kafka/Schema Registry scopes remain distinct.
- A local Testcontainers result does not prove that a Compose service can be removed from CI.
- Any durable change to RepoSync-controlled files requires the RepoSync/platform route.
- Unavailable timing, reliability and CI evidence is recorded as `not measured`.

## Acceptance criteria

- [x] Compose services and invocation paths are mapped and classified.
- [x] CI, full E2E and local-debug roles are kept distinct.
- [x] A target Compose role and unresolved decisions are recorded.
- [x] Implementation, ownership and evidence limits are explicit.

## Tasks

| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T5.1 | [Validate current Compose scope](./task-1-validate-compose-scope.md) | 3 | Must | Done — evidence prepared |
| T5.2 | [Decide the target Compose role](./task-2-decide-compose-role.md) | 2 | Must | Done — recommendation prepared |

**Supporting outputs:** [T5.1 evidence](../../../solution/story-5/T5.1-validate-compose-scope.md) · [T5.2 evidence](../../../solution/story-5/T5.2-decide-compose-role.md)

**Consolidation mapping:** [Story 5 and Story 6 consolidation](../STORY-5-6-CONSOLIDATION.md)
