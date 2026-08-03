# E2-S1.3 — Validate CI and Decide Adoption

| Field | Value |
|---|---|
| **Type** | Real-CI validation, rollout and adoption decision |
| **Status** | Proposed / New — not started |
| **Depends on** | Approved E2-S1.1/E2-S1.2 candidates, [pilot CI baseline](../../../../solution/story-2/T2.2-pipeline-baseline.md) and required RepoSync/platform route |

## Scope

- Run the approved image-build and integration-topology changes through real branch/MR pipelines.
- Exercise equivalent command and snapshot variants required by the SNS delivery path.
- Compare equivalent CI evidence with the baseline and current Compose path.
- Validate Docker/DIND connectivity, Testcontainers lifecycle/cleanup, image artefacts, application readiness and failure diagnostics.
- Record every run reference, environment, image state, failure and retry.
- Define and exercise the rollout and rollback route.
- Obtain and record the durable owner’s `adopt`, `revise`, `retain-as-candidate` or `stop` decision.

## Acceptance criteria

- [ ] Exact CI run references, environment, image state and comparison method are recorded.
- [ ] Equivalent command and snapshot coverage is demonstrated or each gap remains explicit.
- [ ] Local and CI evidence remains separated; failures and retries are not omitted.
- [ ] Rollout and rollback are executable and have named ownership.
- [ ] No CI saving or reliability improvement is claimed without equivalent evidence.
- [ ] Default-path adoption is not claimed until the owner-approved disposition is recorded.

