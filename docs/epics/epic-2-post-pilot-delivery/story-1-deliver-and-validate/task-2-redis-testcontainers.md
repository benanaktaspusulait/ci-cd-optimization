# E2-S1.2 — Productise the Opt-In Redis Testcontainers Workflow

| Field | Value |
|---|---|
| **Type** | Repository workflow implementation and documentation |
| **Status** | Proposed / New — not started |
| **Depends on** | [Story 4 final evidence](../../../../solution/story-4/T4.4-document-findings.md) and [Story 5 Compose decision](../../../../solution/story-5/T5.2-decide-compose-role.md) |

## Scope

- Retain Redis Option A as a narrow opt-in local path.
- Productise it as the Redis dependency component of the wider SNS integration-test target while retaining a repeatable repository-standard invocation.
- Document prerequisites, intended use and limitations.
- Repeat the functional smoke/wiring path and record failures as well as successes.
- Supply Redis host/port dynamically to tests and application paths that are explicitly included in the approved target.
- Preserve the default Compose/full-E2E route until E2-S1.4 and E2-S1.5 establish functional and CI equivalence.

## Acceptance criteria

- [ ] The Redis workflow remains opt-in, concise and reproducible.
- [ ] Repeated local functional execution and any failures/retries are recorded.
- [ ] Existing default test and Compose/full-E2E behaviour is preserved.
- [ ] The Redis container lifecycle and dynamic connection wiring can be reused by the approved wider integration path without fixed host ports.
- [ ] No default CI enablement occurs without separate validation and approval.
- [ ] No Compose replacement or unmeasured speed/reliability conclusion is claimed from the Redis evidence.
