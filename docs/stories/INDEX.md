# Backlog Index

A single-page outline of the whole backlog: every story and its task titles with tracking info.
For full detail, follow the links. [← Back to overview](../../README.md)

**Related:** [Project plan](../../PROJECT-PLAN.md) · [Security](../../SECURITY.md) · [ADRs](../adr/README.md) · [Metrics](metrics-template.md) · [Definition of Done](DEFINITION-OF-DONE.md) · [Future considerations](FUTURE-CONSIDERATIONS.md)

---

### [Story 1 — Baseline & Pilot Scope](story-1-baseline/README.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T1.1 | [Select pilot repository/service](story-1-baseline/task-1-select-repo.md) | S | _TBD_ | Not started | Week 1 |
| T1.2 | [Capture CI/CD pipeline baseline](story-1-baseline/task-2-pipeline-baseline.md) | M | _TBD_ | Not started | Week 1 |
| T1.3 | [Capture Docker build & image-size baseline](story-1-baseline/task-3-build-image-baseline.md) | S | _TBD_ | Not started | Week 1 |
| T1.4 | [Capture integration-test baseline](story-1-baseline/task-4-integration-test-baseline.md) | M | _TBD_ | Not started | Week 1 |

### [Story 2 — Docker Build Optimisation](story-2-build/README.md) · [ADR-0004](../adr/0004-buildkit-cache-and-layering.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T2.1 | [Review current Dockerfile & build context](story-2-build/task-1-review-dockerfile.md) | M | _TBD_ | Not started | Week 2 |
| T2.2 | [Add or validate .dockerignore](story-2-build/task-2-dockerignore.md) | S | _TBD_ | Not started | Week 2 |
| T2.3 | [Apply Dockerfile layering / cache improvement](story-2-build/task-3-layering-improvement.md) | M | _TBD_ | Not started | Week 2 |
| T2.4 | [Measure local & CI build impact](story-2-build/task-4-measure-impact.md) | M | _TBD_ | Not started | Week 3 |

### [Story 3 — Testcontainers Pilot](story-3-testcontainers/README.md) · [ADR-0002](../adr/0002-testcontainers-for-integration-tests.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T3.1 | [Select candidate dependency/test](story-3-testcontainers/task-1-select-candidate.md) | S | _TBD_ | Not started | Week 2 |
| T3.2 | [Implement Testcontainers setup](story-3-testcontainers/task-2-implement-setup.md) | L | _TBD_ | Not started | Week 2 |
| T3.3 | [Compare with docker-compose flow](story-3-testcontainers/task-3-compare-flows.md) | M | _TBD_ | Not started | Week 3 |
| T3.4 | [Document findings & constraints](story-3-testcontainers/task-4-document-findings.md) | S | _TBD_ | Not started | Week 3 |

### [Story 4 — Docker Compose Rationalisation](story-4-compose/README.md) · [ADR-0003](../adr/0003-reduce-compose-in-ci.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T4.1 | [Map services started by docker-compose](story-4-compose/task-1-map-services.md) | S | _TBD_ | Not started | Week 3 |
| T4.2 | [Classify services & usage](story-4-compose/task-2-classify-usage.md) | M | _TBD_ | Not started | Week 3 |
| T4.3 | [Recommend reduced Compose role](story-4-compose/task-3-recommend-role.md) | M | _TBD_ | Not started | Week 4 |

### [Story 5 — Findings, Ownership & Recommendations](story-5-findings/README.md)

| ID | Task | Est | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T5.1 | [Consolidate pilot findings](story-5-findings/task-1-consolidate-findings.md) | M | _TBD_ | Not started | Week 4 |
| T5.2 | [Classify ownership & recommend target board](story-5-findings/task-2-classify-ownership.md) | M | _TBD_ | Not started | Week 4 |
| T5.3 | [Share findings with stakeholders](story-5-findings/task-3-share-stakeholders.md) | S | _TBD_ | Not started | Week 4 |
