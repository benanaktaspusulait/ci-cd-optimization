# Backlog Index

A single-page outline of the whole backlog: every story and its task titles with initial planning info.
For full detail, follow the links. [← Back to overview](../../README.md)

**Related:** [Status board](STATUS-BOARD.md) · [Project plan](../../PROJECT-PLAN.md) · [Security](../../SECURITY.md) · [ADRs](../adr/README.md) · [Metrics](metrics-template.md) · [Definition of Done](DEFINITION-OF-DONE.md) · [Future considerations](FUTURE-CONSIDERATIONS.md)

> Live progress is tracked only in the [status board](STATUS-BOARD.md); status values here are initial backlog snapshots.

---

### [Story 1 — Pipeline Assessment (Drone/RepoSync)](story-1-pipeline-assessment/README.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T1.1 | [Review .drone.star pipeline structure](story-1-pipeline-assessment/task-1-review-drone-star.md) | M | _TBD_ | Not started | Week 1 |
| T1.2 | [Identify local vs RepoSync boundaries](story-1-pipeline-assessment/task-2-local-vs-central.md) | S | _TBD_ | Not started | Week 1 |
| T1.3 | [Map CI steps, DIND and Compose usage](story-1-pipeline-assessment/task-3-map-ci-steps.md) | M | _TBD_ | Not started | Week 1 |
| T1.4 | [Assess Testcontainers feasibility in Drone](story-1-pipeline-assessment/task-4-testcontainers-feasibility.md) | M | _TBD_ | Not started | Week 1 |
| T1.5 | [Assess BuildKit/cache feasibility](story-1-pipeline-assessment/task-5-buildkit-feasibility.md) | S | _TBD_ | Not started | Week 1 |

### [Story 2 — Baseline & Pilot Scope](story-2-baseline/README.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T2.1 | [Select pilot repository/service](story-2-baseline/task-1-select-repo.md) | S | _TBD_ | Not started | Week 1 |
| T2.2 | [Capture CI/CD pipeline baseline](story-2-baseline/task-2-pipeline-baseline.md) | M | _TBD_ | Not started | Week 1 |
| T2.3 | [Capture Docker build & image-size baseline](story-2-baseline/task-3-build-image-baseline.md) | S | _TBD_ | Not started | Week 1 |
| T2.4 | [Capture integration-test baseline](story-2-baseline/task-4-integration-test-baseline.md) | M | _TBD_ | Not started | Week 1 |

### [Story 3 — Docker Build Optimisation](story-3-build/README.md) · [ADR-0004](../adr/0004-buildkit-cache-and-layering.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T3.1 | [Review current Dockerfile & build context](story-3-build/task-1-review-dockerfile.md) | M | _TBD_ | Not started | Week 2 |
| T3.2 | [Add or validate .dockerignore](story-3-build/task-2-dockerignore.md) | S | _TBD_ | Not started | Week 2 |
| T3.3 | [Apply Dockerfile layering / cache improvement](story-3-build/task-3-layering-improvement.md) | M | _TBD_ | Not started | Week 2 |
| T3.4 | [Measure local & CI build impact](story-3-build/task-4-measure-impact.md) | M | _TBD_ | Not started | Week 3 |

### [Story 4 — Testcontainers Pilot](story-4-testcontainers/README.md) · [ADR-0002](../adr/0002-testcontainers-for-integration-tests.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T4.1 | [Select candidate dependency/test](story-4-testcontainers/task-1-select-candidate.md) | S | _TBD_ | Not started | Week 2 |
| T4.2 | [Implement Testcontainers setup](story-4-testcontainers/task-2-implement-setup.md) | L | _TBD_ | Not started | Week 2 |
| T4.3 | [Compare with docker-compose flow](story-4-testcontainers/task-3-compare-flows.md) | M | _TBD_ | Not started | Week 3 |
| T4.4 | [Document findings & constraints](story-4-testcontainers/task-4-document-findings.md) | S | _TBD_ | Not started | Week 3 |

### [Story 5 — Docker Compose Rationalisation](story-5-compose/README.md) · [ADR-0003](../adr/0003-reduce-compose-in-ci.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T5.1 | [Map services started by docker-compose](story-5-compose/task-1-map-services.md) | S | _TBD_ | Not started | Week 3 |
| T5.2 | [Classify services & usage](story-5-compose/task-2-classify-usage.md) | M | _TBD_ | Not started | Week 3 |
| T5.3 | [Recommend reduced Compose role](story-5-compose/task-3-recommend-role.md) | M | _TBD_ | Not started | Week 4 |

### [Story 6 — Findings, Ownership & Recommendations](story-6-findings/README.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T6.1 | [Consolidate pilot findings](story-6-findings/task-1-consolidate-findings.md) | M | _TBD_ | Not started | Week 4 |
| T6.2 | [Classify ownership & recommend target board](story-6-findings/task-2-classify-ownership.md) | M | _TBD_ | Not started | Week 4 |
| T6.3 | [Share findings with stakeholders](story-6-findings/task-3-share-stakeholders.md) | S | _TBD_ | Not started | Week 4 |
