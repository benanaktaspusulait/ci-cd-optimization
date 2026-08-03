# E2-S1.4 — Validate CI and Decide Adoption

| Field | Value |
|---|---|
| **Type** | Real-CI validation, rollout and adoption decision |
| **Status** | Proposed / New — not started |
| **Depends on** | Approved E2-S1.1–E2-S1.3 candidates, [pilot CI baseline](../../../../solution/story-2/T2.2-pipeline-baseline.md) and required RepoSync/platform route |

## Scope

- Run approved image-build and integration-topology changes through real branch/MR pipelines.
- Run the updated existing command and snapshot suites using the intended Maven profiles and tag selections; do not substitute a narrower smoke suite for the existing integration coverage.
- Compare equivalent CI evidence with the baseline and current Compose path.
- Record before/after discovered and executed scenario/test counts, selected/excluded tags and any disabled or quarantined tests.
- Validate Docker/DIND connectivity, Testcontainers lifecycle/cleanup, image artefacts, application readiness and failure diagnostics.
- Record every run reference, environment, image state, failure and retry.
- Define and exercise CI/default-test-path rollout and rollback routes; deployment/CD rollout belongs to E2-S1.5.
- Obtain and record the durable owner’s `adopt`, `revise`, `retain-as-candidate` or `stop` decision.

## Acceptance criteria

- [ ] Exact CI run references, environment, image state and comparison method are recorded.
- [ ] Equivalent command and snapshot coverage is demonstrated using the updated existing suite, or each gap remains explicit and unapproved for adoption.
- [ ] Before/after test and scenario counts, Maven profiles, tag expressions and exclusions are recorded.
- [ ] No existing test is silently disabled, excluded, quarantined or replaced by smoke-only coverage.
- [ ] Local and CI evidence remains separated; failures and retries are not omitted.
- [ ] CI/default-test-path rollout and rollback are executable and have named ownership.
- [ ] No CI saving or reliability improvement is claimed without equivalent evidence.
- [ ] Default-path adoption is not claimed until the owner-approved disposition is recorded.
