# E2-S1.5 — Validate Approved SNS Changes in the Real Delivery Path

| Field | Value |
|---|---|
| **Type** | CI validation and adoption decision |
| **Status** | Proposed / New — not started |
| **Depends on** | Approved E2-S1.1–E2-S1.4 candidates, [pilot CI baseline](../../../../solution/story-2/T2.2-pipeline-baseline.md), and required RepoSync/platform route |

## Scope

- Run approved image-build and integration-topology changes through the real branch/MR pipeline.
- Exercise the command and snapshot variants required by the current SNS delivery path.
- Compare equivalent CI evidence with the recorded baseline and current Compose path.
- Validate Docker/DIND connectivity, Testcontainers cleanup, image artefacts, application readiness and failure diagnostics.
- Record all runs, failures and retries.
- Route the resulting adopt, retain-as-candidate, revise or stop decision to the durable owner.

## Acceptance criteria

- [ ] Exact CI run references, environment, image state and comparison method are recorded.
- [ ] Equivalent command and snapshot coverage is demonstrated or each gap remains explicit.
- [ ] Local and CI evidence remains separated.
- [ ] Failures and retries are not omitted.
- [ ] No CI saving is claimed without equivalent evidence.
- [ ] Runtime artefacts, durable ownership and rollback route are recorded.
- [ ] Adoption is not claimed until an owner-approved disposition exists.

