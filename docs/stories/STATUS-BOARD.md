# Status Board

Single source of truth for pilot task progress. [← Back to overview](../../README.md)

> **Note:** The backlog below is a **candidate structure only**. Individual tickets should not be created until priority, ownership and target board are agreed. The purpose is to support review and prioritisation — not to imply that every task will be implemented immediately.

Update the **Status** column as work moves; status-looking metadata in story/task files is only an initial planning snapshot.
Estimates use story points: `1`, `2`, `3`, or `5`; `1 SP` is roughly 1 day of effort. Priority: MoSCoW.

> **Tickets:** Confirm the delivery tracker before ticket creation (record the final choice in T2.1 once the pilot repo is selected). For GitLab-hosted repos, use GitLab issues for task links and GitLab MRs for source review; if Jira is the team's delivery tracker, link Jira tickets in the `Issue` column. Issue creation order: Epic → S1 → T1.1 → T1.2 → S2 → T2.1 (see [CONTRIBUTING.md](../../CONTRIBUTING.md)).

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
| **S5** | **Docker Compose Rationalisation** | — | Should | Not started | _TBD_ | — |
| T5.1 | Map services started by docker-compose | 1 | Must | Not started | _TBD_ | — |
| T5.2 | Classify services & usage | 2 | Must | Not started | _TBD_ | — |
| T5.3 | Recommend reduced Compose role | 2 | Should | Not started | _TBD_ | — |
| **S6** | **Findings, Ownership & Recommendations** | — | Must | Not started | _TBD_ | — |
| T6.1 | Consolidate pilot findings | 2 | Must | Not started | _TBD_ | — |
| T6.2 | Classify ownership & recommend target board | 2 | Must | Not started | _TBD_ | — |
| T6.3 | Share findings with stakeholders | 1 | Should | Not started | _TBD_ | — |

---

## Ticket-Creation Order

Create incrementally — not all at once:

1. Epic
2. Story 1 → T1.1 (pipeline structure) → T1.2 (local vs RepoSync boundaries)
3. Story 2 → T2.1 (select repo) → T2.2 (pipeline baseline)

Open the rest once pipeline boundaries are understood and the baseline is underway.
