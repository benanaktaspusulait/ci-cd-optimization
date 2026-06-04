# Status Board

Single source of truth for pilot task progress. [← Back to overview](../../README.md)

> **Note:** The backlog below is a **candidate structure only**. Individual tickets should not be created until priority, ownership and target board are agreed. The purpose is to support review and prioritisation — not to imply that every task will be implemented immediately.

Update the **Status** column as work moves; status-looking metadata in story/task files is only an initial planning snapshot.
Estimates: `S` ≤0.5d · `M` 0.5–1d · `L` 1–2d. Priority: MoSCoW.

> **Tickets:** Confirm the delivery tracker before ticket creation (record the final choice in T2.1 once the pilot repo is selected). For GitLab-hosted repos, use GitLab issues for task links and GitLab MRs for source review; if Jira is the team's delivery tracker, link Jira tickets in the `Issue` column. Issue creation order: Epic → S1 → T1.1 → T1.2 → S2 → T2.1 (see [CONTRIBUTING.md](../../CONTRIBUTING.md)).

| ID | Item | Est | Priority | Status | Owner | Issue |
|----|------|:---:|:--------:|--------|-------|-------|
| **S1** | **Pipeline Assessment (Drone/RepoSync)** | — | Must | Not started | _TBD_ | — |
| T1.1 | Review .drone.star pipeline structure | M | Must | Not started | _TBD_ | — |
| T1.2 | Identify local vs RepoSync boundaries | S | Must | Not started | _TBD_ | — |
| T1.3 | Map CI steps, DIND and Compose usage | M | Must | Not started | _TBD_ | — |
| T1.4 | Assess Testcontainers feasibility in Drone | M | Must | Not started | _TBD_ | — |
| T1.5 | Assess BuildKit/cache feasibility | S | Should | Not started | _TBD_ | — |
| **S2** | **Baseline & Pilot Scope** | — | Must | Not started | _TBD_ | — |
| T2.1 | Select pilot repository/service | S | Must | Not started | _TBD_ | — |
| T2.2 | Capture CI/CD pipeline baseline | M | Must | Not started | _TBD_ | — |
| T2.3 | Capture Docker build & image-size baseline | S | Must | Not started | _TBD_ | — |
| T2.4 | Capture integration-test baseline | M | Must | Not started | _TBD_ | — |
| **S3** | **Docker Build Optimisation** | — | Must | Not started | _TBD_ | — |
| T3.1 | Review current Dockerfile & build context | M | Must | Not started | _TBD_ | — |
| T3.2 | Add or validate .dockerignore | S | Must | Not started | _TBD_ | — |
| T3.3 | Apply Dockerfile layering / cache improvement | M | Must | Not started | _TBD_ | — |
| T3.4 | Measure local & CI build impact | M | Should | Not started | _TBD_ | — |
| **S4** | **Testcontainers Pilot** | — | Must | Not started | _TBD_ | — |
| T4.1 | Select candidate dependency/test | S | Must | Not started | _TBD_ | — |
| T4.2 | Implement Testcontainers setup | L | Must | Not started | _TBD_ | — |
| T4.3 | Compare with docker-compose flow | M | Should | Not started | _TBD_ | — |
| T4.4 | Document findings & constraints | S | Should | Not started | _TBD_ | — |
| **S5** | **Docker Compose Rationalisation** | — | Should | Not started | _TBD_ | — |
| T5.1 | Map services started by docker-compose | S | Must | Not started | _TBD_ | — |
| T5.2 | Classify services & usage | M | Must | Not started | _TBD_ | — |
| T5.3 | Recommend reduced Compose role | M | Should | Not started | _TBD_ | — |
| **S6** | **Findings, Ownership & Recommendations** | — | Must | Not started | _TBD_ | — |
| T6.1 | Consolidate pilot findings | M | Must | Not started | _TBD_ | — |
| T6.2 | Classify ownership & recommend target board | M | Must | Not started | _TBD_ | — |
| T6.3 | Share findings with stakeholders | S | Should | Not started | _TBD_ | — |

---

## Ticket-Creation Order

Create incrementally — not all at once:

1. Epic
2. Story 1 → T1.1 (pipeline structure) → T1.2 (local vs RepoSync boundaries)
3. Story 2 → T2.1 (select repo) → T2.2 (pipeline baseline)

Open the rest once pipeline boundaries are understood and the baseline is underway.
