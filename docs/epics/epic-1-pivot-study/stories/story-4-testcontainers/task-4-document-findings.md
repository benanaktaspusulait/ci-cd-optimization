# T4.4 — Document Redis pilot findings, limits and recommendation

**Story:** [Story 4 — Testcontainers Pilot](README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.4 |
| **Type** | Documentation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 1 |
| **Priority** | Should |
| **Labels** | `testcontainers`, `redis`, `documentation`, `findings` |
| **Sprint** | Week 3 |
| **Depends on** | T4.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The Redis-first pilot is intentionally narrow. Clear findings, constraints and non-claims are needed so stakeholders can decide whether to stop, deepen the Redis experiment or progress to the higher-value Kafka and Schema Registry candidates without overstating what Option A proved.

## Goal
Document the final evidence and recommendation from the Redis-first Option A pilot, including what remains untested.

## Scope
Document:

- what was tested
- what was intentionally not tested
- the local result and any measured timing, including the measurement method
- CI status: attempted and measured, or explicitly not measured/follow-up
- RepoSync constraints, any temporary target-repository pilot changes, and confirmation that no durable RepoSync-managed adoption was approved
- whether Redis Option A succeeded against its smoke/wiring goal
- whether Redis Option B, a local-profile Redis override, should be attempted
- whether Kafka and Schema Registry should be prioritised as the next higher-value follow-up
- limitations, non-claims and the continue/stop recommendation

Where a metric was not captured, record it explicitly as not measured and do not delay the final recommendation unless that metric is decision-critical.

Apply the reuse policy: Testcontainers reuse may be enabled locally for faster feedback only when the setup and effect are documented. Reuse must be disabled in CI for deterministic runs with no hidden shared state.

## Required non-claims

Unless separately implemented and measured, state explicitly:

- no full docker-compose replacement was demonstrated
- no Kafka topic, message or offset isolation improvement results from the Redis pilot
- no flaky-test improvement was demonstrated
- no CI saving was demonstrated
- no broad adaptor rollout is recommended or approved
- no durable RepoSync-managed adoption or broad rollout is approved
- no production or default pipeline change is implemented or approved

## Acceptance criteria
- [ ] Findings and intentionally untested areas are documented
- [ ] Local, CI and RepoSync constraints are documented
- [ ] Every measured value is linked to its measurement method or source evidence
- [ ] Missing or unavailable measurements are explicitly recorded and are not replaced with estimates
- [ ] A continue/stop recommendation is documented
- [ ] The final recommendation distinguishes measured evidence, interpretation and follow-up assumptions
- [ ] Required non-claims are documented
- [ ] Next-step recommendations are separated into:
  - [ ] Option A complete / incomplete
  - [ ] Option B candidate / not candidate
  - [ ] Kafka and Schema Registry follow-up candidate / not candidate
  - [ ] CI follow-up required / not required
