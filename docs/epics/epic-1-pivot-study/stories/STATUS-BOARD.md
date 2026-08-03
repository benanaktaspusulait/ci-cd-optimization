# Status Board

Single source of truth for pilot task progress and proposed follow-up epic status. [← Back to overview](../../../../README.md)

> **Pilot closure:** evidence and recommendations are prepared, but T6.2 owner review, share-out and decisions are not recorded. The pilot therefore remains `In progress`. No further implementation stories will be added to it.

> **Note:** The backlog below is a **candidate structure only**. Individual tickets should not be created until priority, ownership and target board are agreed. The purpose is to support review and prioritisation — not to imply that every task will be implemented immediately.

Update the **Status** column as work moves; status-looking metadata in story/task files is only an initial planning snapshot.
Estimates use story points: `1`, `2`, `3`, or `5`; `1 SP` is roughly 1 day of effort. Priority: MoSCoW.

> **Tickets:** Confirm the delivery tracker before ticket creation (record the final choice in T2.1 once the pilot repo is selected). For GitLab-hosted repos, use GitLab issues for task links and GitLab MRs for source review; if Jira is the team's delivery tracker, link Jira tickets in the `Issue` column. Issue creation order: Epic → S1 → T1.1 → T1.2 → S2 → T2.1 (see [CONTRIBUTING.md](../../../../CONTRIBUTING.md)).

| ID | Item | SP | Priority | Status | Owner | Issue |
|----|------|:---:|:--------:|--------|-------|-------|
| **S1** | **Pipeline Assessment (Drone/RepoSync)** | — | Must | Not started | _TBD_ | — |
| T1.1 | Review .drone.star pipeline structure | 2 | Must | Not started | _TBD_ | — |
| T1.2 | Identify local vs RepoSync boundaries | 1 | Must | Not started | _TBD_ | — |
| T1.3 | Map CI steps, DIND and Compose usage | 2 | Must | Not started | _TBD_ | — |
| T1.4 | Assess Testcontainers feasibility in Drone | 2 | Must | Not started | _TBD_ | — |
| T1.5 | Assess BuildKit/cache feasibility | 1 | Should | Not started | _TBD_ | — |
| **S2** | **Baseline & Pilot Scope** | — | Must | Not started | _TBD_ | — |
| T2.1 | Compare candidate pipelines and select pilot repo | 1 | Must | Not started | _TBD_ | — |
| T2.2 | Capture CI/CD pipeline baseline | 2 | Must | Not started | _TBD_ | — |
| T2.3 | Capture Docker build & image-size baseline | 1 | Must | Not started | _TBD_ | — |
| T2.4 | Capture integration-test baseline | 2 | Must | Not started | _TBD_ | — |
| **S3** | **Docker Build Optimisation** | — | Must | Not started | _TBD_ | — |
| T3.1 | Review current Dockerfile & build context | 2 | Must | Not started | _TBD_ | — |
| T3.2 | Add or validate .dockerignore | 1 | Must | Not started | _TBD_ | — |
| T3.3 | Apply Dockerfile layering / cache improvement | 2 | Must | Not started | _TBD_ | — |
| T3.4 | Measure local & CI build impact | 2 | Should | Not started | _TBD_ | — |
| **S4** | **Testcontainers Pilot** | — | Must | Not started | _TBD_ | — |
| T4.1 | Select candidate dependency/test | 1 | Must | Not started | _TBD_ | — |
| T4.2 | Implement Testcontainers setup | 3 | Must | Not started | _TBD_ | — |
| T4.3 | Compare with docker-compose flow | 2 | Should | Not started | _TBD_ | — |
| T4.4 | Document findings & constraints | 1 | Should | Not started | _TBD_ | — |
| **S5** | **Docker Compose Rationalisation** | — | Should | Done — analysis only | _TBD_ | — |
| T5.1 | Validate current Compose scope | 3 | Must | Done — evidence prepared | _TBD_ | — |
| T5.2 | Decide the target Compose role | 2 | Must | Done — target-role recommendation prepared; implementation and adoption not approved | _TBD_ | — |
| **S6** | **Pilot Outcome, Ownership and Adoption** | — | Must | In progress | _TBD_ | — |
| T6.1 | Classify pilot outcomes and ownership routes | 4 | Must | Done — evidence prepared | _TBD_ | — |
| T6.2 | Decide adoption route and publish pilot outcome | 2 | Must | Not completed — materials prepared | _TBD_ | — |

---

## Separate Proposed Delivery Epics

| ID | Item | Status | Dependency |
|---|---|---|---|
| **E2** | [Post-Pilot Container and CD Delivery](../../epic-2-post-pilot-delivery/README.md) | Proposed / New | Pilot evidence and story-specific owner approval |
| E2-S1 | Productionise validated SNS outcomes | Proposed / New | Validated Story 3/4 evidence |
| E2-S1.1 | Implement validated SNS image-build changes | Proposed / New | Story 3 evidence and owner approval |
| E2-S1.2 | Productise the opt-in Redis Testcontainers workflow | Proposed / New | Story 4 evidence and owner approval |
| E2-S1.3 | Validate approved SNS changes in CI and decide adoption | Proposed / New | Approved E2 outputs and platform route |
| E2-S2 | Review the CD pipeline and define the target | Proposed / New | Independent CD evidence and owners |
| E2-S2.1 | Review the current CD pipeline | Proposed / New | CD repository/pipeline access |
| E2-S2.2 | Define the CD target design and delivery plan | Proposed / New | Completed E2-S2.1 evidence and owner input |

These entries are planning records, not approvals or pilot stories. Broad SNS topology candidates are tracked separately and are not Epic 2 commitments.

---

## Ticket-Creation Order

Create incrementally — not all at once:

1. Epic
2. Story 1 → T1.1 (pipeline structure) → T1.2 (local vs RepoSync boundaries)
3. Story 2 → T2.1 (select repo) → T2.2 (pipeline baseline)

Open the rest once pipeline boundaries are understood and the baseline is underway.
