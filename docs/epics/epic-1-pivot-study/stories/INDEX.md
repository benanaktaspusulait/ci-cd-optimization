# Pilot Backlog Index

A single-page outline of the bounded pilot backlog. No Story 7 or production implementation work will be added here.
For full detail, follow the links. [← Back to overview](../../../../README.md)

**Related:** [Delivery epic index](../../INDEX.md) · [Status board](STATUS-BOARD.md) · [Project plan](../../../../PROJECT-PLAN.md) · [Security](../../../../SECURITY.md) · [ADRs](../../../adr/README.md) · [Metrics](metrics-template.md) · [Definition of Done](DEFINITION-OF-DONE.md) · [Future considerations](FUTURE-CONSIDERATIONS.md)

> Live progress is tracked only in the [status board](STATUS-BOARD.md); status values here are initial backlog snapshots.

---

### [Story 1 — Pipeline Assessment (Drone/RepoSync)](story-1-pipeline-assessment/README.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T1.1 | [Review .drone.star pipeline structure](story-1-pipeline-assessment/task-1-review-drone-star.md) | 2 | _TBD_ | Not started | Week 1 |
| T1.2 | [Identify local vs RepoSync boundaries](story-1-pipeline-assessment/task-2-local-vs-central.md) | 1 | _TBD_ | Not started | Week 1 |
| T1.3 | [Map CI steps, DIND and Compose usage](story-1-pipeline-assessment/task-3-map-ci-steps.md) | 2 | _TBD_ | Not started | Week 1 |
| T1.4 | [Assess Testcontainers feasibility in Drone](story-1-pipeline-assessment/task-4-testcontainers-feasibility.md) | 2 | _TBD_ | Not started | Week 1 |
| T1.5 | [Assess BuildKit/cache feasibility](story-1-pipeline-assessment/task-5-buildkit-feasibility.md) | 1 | _TBD_ | Not started | Week 1 |

### [Story 2 — Baseline & Pilot Scope](story-2-baseline/README.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T2.1 | [Compare candidate pipelines and select pilot repo](story-2-baseline/task-1-select-repo.md) | 1 | _TBD_ | Not started | Week 1 |
| T2.2 | [Capture CI/CD pipeline baseline](story-2-baseline/task-2-pipeline-baseline.md) | 2 | _TBD_ | Not started | Week 1 |
| T2.3 | [Capture Docker build & image-size baseline](story-2-baseline/task-3-build-image-baseline.md) | 1 | _TBD_ | Not started | Week 1 |
| T2.4 | [Capture integration-test baseline](story-2-baseline/task-4-integration-test-baseline.md) | 2 | _TBD_ | Not started | Week 1 |

### [Story 3 — Docker Build Optimisation](story-3-build/README.md) · [ADR-0004](../../../adr/0004-buildkit-cache-and-layering.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T3.1 | [Review current Dockerfile & build context](story-3-build/task-1-review-dockerfile.md) | 2 | _TBD_ | Not started | Week 2 |
| T3.2 | [Add or validate .dockerignore](story-3-build/task-2-dockerignore.md) | 1 | _TBD_ | Not started | Week 2 |
| T3.3 | [Apply Dockerfile layering / cache improvement](story-3-build/task-3-layering-improvement.md) | 2 | _TBD_ | Not started | Week 2 |
| T3.4 | [Measure local & CI build impact](story-3-build/task-4-measure-impact.md) | 2 | _TBD_ | Not started | Week 3 |

### [Story 4 — Testcontainers Pilot](story-4-testcontainers/README.md) · [ADR-0002](../../../adr/0002-testcontainers-for-integration-tests.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T4.1 | [Select candidate dependency/test](story-4-testcontainers/task-1-select-candidate.md) | 1 | _TBD_ | Not started | Week 2 |
| T4.2 | [Implement Testcontainers setup](story-4-testcontainers/task-2-implement-setup.md) | 3 | _TBD_ | Not started | Week 2 |
| T4.3 | [Compare with docker-compose flow](story-4-testcontainers/task-3-compare-flows.md) | 2 | _TBD_ | Not started | Week 3 |
| T4.4 | [Document findings & constraints](story-4-testcontainers/task-4-document-findings.md) | 1 | _TBD_ | Not started | Week 3 |

### [Story 5 — Docker Compose Rationalisation](story-5-compose/README.md) · [ADR-0003](../../../adr/0003-reduce-compose-in-ci.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T5.1 | [Validate current Compose scope](story-5-compose/task-1-validate-compose-scope.md) | 3 | _TBD_ | Done — evidence prepared | Week 3 |
| T5.2 | [Decide the target Compose role](story-5-compose/task-2-decide-compose-role.md) | 2 | _TBD_ | Done — target-role recommendation prepared; implementation and adoption not approved | Week 4 |

### [Story 6 — Pilot Outcome, Ownership and Adoption](story-6-findings/README.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T6.1 | [Classify pilot outcomes and ownership routes](story-6-findings/task-1-classify-outcomes.md) | 4 | _TBD_ | Done — evidence prepared | Week 4 |
| T6.2 | [Decide the adoption route and publish the pilot outcome](story-6-findings/task-2-decide-adoption.md) | 2 | _TBD_ | Not completed — materials prepared | Week 4 |

See the [Story 5 and Story 6 consolidation map](STORY-5-6-CONSOLIDATION.md) for legacy task traceability.

## Separate Delivery Epics

| Epic | Stories | Status |
|---|---:|---|
| [Epic 2 — Post-Pilot Container and CD Delivery](../../epic-2-post-pilot-delivery/README.md) | 2 | Proposed / New |

The two follow-up stories have independent ownership and evidence gates. Neither is Story 7 or an extension of Story 6.
