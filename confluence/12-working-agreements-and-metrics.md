# Working Agreements and Metrics

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

This page consolidates the contributing guide, live status rules, Definition of Done, and metrics template.

---

## Documentation Structure

This proposal is organised as a set of Confluence pages beneath a single parent page. The levels are **Epic → Story → Task**:

- **Parent Overview** — entry point: purpose, key constraint, success targets, story map.
- **Working Agreements and Metrics** (this page) — working guide, status board rules, Definition of Done, metrics template.
- **Project Plan and Governance** — timeline, milestones, risk register, branching/CI flow, test strategy.
- **Security Plan** — secret management, scanning policy, policy-as-code.
- **Proposal Matrix**, **Phased Plan**, **Risks and DACI** — decision and prioritisation content.
- **Pipeline & Drone Context** and **Deployment & Release** — current CI/CD and deploy environment.
- **Backlog Summary** — one-page list of every story and task with story points.
- **Detailed Task Definitions** — full why, goal, scope and acceptance criteria for every task.
- **Architecture Decisions (ADRs)** — significant decisions with context and consequences.
- **Technical Details** and **Code Examples and Templates** — Dockerfile, Compose, Testcontainers and CI examples.
- **Glossary** and **References** — terminology and supporting context.

**Decisions** are recorded as ADRs. **Plan and risks** live in the Project Plan. **Security** lives in the Security Plan.

---

## How to Navigate

- Start at the backlog summary for the full outline.
- Drill into a story section for its goal and task list.
- Use the detailed task definitions page for full task why, goal, scope and acceptance criteria.
- Use the status board as the only live progress tracker.

## Reading a Task

Every task follows the same shape:

- **Metadata header:** ID, estimate, priority, owner, status, depends on.
- **Why:** the reason the task exists.
- **Goal:** the outcome it must achieve.
- **Scope:** what is covered.
- **Acceptance criteria:** checklist that must pass.

## Conventions

- **Estimate:** story points `1`, `2`, `3`, or `5`; `1 SP` is roughly 1 day of effort.
- **Priority:** `Must`, `Should`, `Could`, `Won't (this pilot)`.
- **Status:** `Not started`, `In progress`, `Blocked`, `Done`.
- **IDs:** stories `S1` to `S6`; tasks `T<story>.<n>`, for example `T3.3`.

> The status board is the only live progress tracker. Status values in story/task files are planning snapshots and should not be maintained separately.

---

## Working a Task

1. Set the task **Status** to `In progress` on the status board.
2. Do the work within the task's **scope**.
3. Capture any measurement in the metrics template.
4. Tick the task's **acceptance criteria**.
5. Confirm the shared Definition of Done.
6. If the task settles a significant choice, record an ADR.
7. Set **Status** to `Done` or `Blocked`, with a note on what is blocking.

## Raising Tickets

Confirm the delivery tracker before ticket creation. If the pilot repo is GitLab-hosted, use GitLab issues for task links and GitLab MRs for source review. If Jira is the delivery tracker, link the Jira ticket in the `Issue` column and still use GitLab MRs for code changes.

Route cross-team follow-ups to the CST, RepoSync/platform, or wider ETO board in Story 6.

Create tickets incrementally. Do not raise everything at once; keep work controlled until pipeline boundaries, baseline data, and ownership are agreed.

---

## Status Board Rules

Single source of truth for pilot task progress.

> The backlog is a candidate structure only. Individual tickets should not be created until priority, ownership and target board are agreed. The purpose is to support review and prioritisation, not to imply that every task will be implemented immediately.

Update the **Status** column as work moves. Estimates use story points: `1`, `2`, `3`, or `5`; `1 SP` is roughly 1 day of effort. Priority uses MoSCoW.

**Tickets:** Confirm the delivery tracker before ticket creation and record the final choice in T2.1 once the pilot repo is selected. For GitLab-hosted repos, use GitLab issues for task links and GitLab MRs for source review. If Jira is the delivery tracker, link Jira tickets in the Issue column.

**Issue creation order:** Epic -> S1 -> T1.1 -> T1.2 -> S2 -> T2.1.

| ID | Item | SP | Priority | Status | Owner | Issue |
|----|------|:--:|:--------:|--------|-------|-------|
| **S1** | **Pipeline Assessment (Drone/RepoSync)** | — | Must | Not started | TBC | — |
| T1.1 | Review `.drone.star` pipeline structure | 2 | Must | Not started | TBC | — |
| T1.2 | Identify local vs RepoSync boundaries | 1 | Must | Not started | TBC | — |
| T1.3 | Map CI steps, DIND and Compose usage | 2 | Must | Not started | TBC | — |
| T1.4 | Assess Testcontainers feasibility in Drone | 2 | Must | Not started | TBC | — |
| T1.5 | Assess BuildKit/cache feasibility | 1 | Should | Not started | TBC | — |
| **S2** | **Baseline & Pilot Scope** | — | Must | Not started | TBC | — |
| T2.1 | Compare candidate pipelines and select pilot repo | 1 | Must | Not started | TBC | — |
| T2.2 | Capture CI/CD pipeline baseline | 2 | Must | Not started | TBC | — |
| T2.3 | Capture Docker build & image-size baseline | 1 | Must | Not started | TBC | — |
| T2.4 | Capture integration-test baseline | 2 | Must | Not started | TBC | — |
| **S3** | **Docker Build Optimisation** | — | Must | Not started | TBC | — |
| T3.1 | Review current Dockerfile & build context | 2 | Must | Not started | TBC | — |
| T3.2 | Add or validate `.dockerignore` | 1 | Must | Not started | TBC | — |
| T3.3 | Apply Dockerfile layering / cache improvement | 2 | Must | Not started | TBC | — |
| T3.4 | Measure local & CI build impact | 2 | Should | Not started | TBC | — |
| **S4** | **Testcontainers Pilot** | — | Must | Not started | TBC | — |
| T4.1 | Select candidate dependency/test | 1 | Must | Not started | TBC | — |
| T4.2 | Implement Testcontainers setup | 3 | Must | Not started | TBC | — |
| T4.3 | Compare with docker-compose flow | 2 | Should | Not started | TBC | — |
| T4.4 | Document findings & constraints | 1 | Should | Not started | TBC | — |
| **S5** | **Docker Compose Rationalisation** | — | Should | Not started | TBC | — |
| T5.1 | Map services started by docker-compose | 1 | Must | Not started | TBC | — |
| T5.2 | Classify services & usage | 2 | Must | Not started | TBC | — |
| T5.3 | Recommend reduced Compose role | 2 | Should | Not started | TBC | — |
| **S6** | **Findings, Ownership & Recommendations** | — | Must | Not started | TBC | — |
| T6.1 | Consolidate pilot findings | 2 | Must | Not started | TBC | — |
| T6.2 | Classify ownership & recommend target board | 2 | Must | Not started | TBC | — |
| T6.3 | Share findings with stakeholders | 1 | Should | Not started | TBC | — |

### Ticket-Creation Order

1. Epic.
2. Story 1 -> T1.1 (pipeline structure) -> T1.2 (local vs RepoSync boundaries).
3. Story 2 -> T2.1 (select repo) -> T2.2 (pipeline baseline).

Open the rest once pipeline boundaries are understood and the baseline is underway.

---

## Definition of Done

Project-wide rules apply to every task, in addition to each task's own acceptance criteria.

### Every Task

- [ ] Task-specific acceptance criteria are all met.
- [ ] Output (findings, change, or decision) is written down in a shareable form.
- [ ] Any assumptions or open questions are recorded.
- [ ] Result is reviewed by at least one other person.
- [ ] Task status is updated on the status board.

### Tasks That Produce a Measurement

- [ ] Metric is captured using the shared metrics template.
- [ ] Measurement method/source is noted so it can be repeated.

### Tasks That Change Code or Config

- [ ] Change is small, focused, and reviewable.
- [ ] Compatibility / rollback risk is noted.
- [ ] No secrets are added to the repository or build context.

---

## Metrics Template

Fill this in as the pilot progresses. Baseline values come from Story 2; after values from Stories 3-5. Copy a fresh block per pilot iteration if measuring more than once.

> Record the method/source for every number so it can be repeated identically for the after run. Pipeline duration = rolling average over the last **N** runs, set in T2.2.

### Pilot Context

| Field | Value |
|-------|-------|
| Pilot repository | TBC (T2.1) |
| Measurement date (baseline) | YYYY-MM-DD |
| Measurement date (after) | YYYY-MM-DD |
| N (runs averaged) | TBC |
| Measured by | TBC |

### Core Metrics

| Metric | Baseline | After | Delta | Target | Source / method |
|--------|----------|-------|-------|--------|-----------------|
| Pipeline duration (avg) | | | | >= 20% reduction (post-platform) | |
| Build stage duration | | | | — | |
| Unit test duration | | | | — | |
| Integration test duration | | | | — | |
| Docker build time (local) | | | | >= 30% reduction | |
| Docker build time (CI) | | | | >= 20% reduction (post-platform) | |
| Final image size | | | | >= 30% reduction | |
| Integration test startup time | | | | < 30 sec | |
| Build context size | | | | >= 50% reduction | |
| Failed-pipeline / flaky rate | | | | no regression | |
| Developer feedback loop (change -> test green) | | | | <= 5 min | |
| Cache hit/miss rate (if available) | | | | — | |

### Notes and Observations

- Record anything that affects interpretation: environment differences, one-off slow runs, cache warm/cold state, etc.

### Source Data

- Link to pipeline runs, build logs, or commands used.

### Source Artefact Mapping

| Source | Produced by | Use for |
|--------|-------------|---------|
| `metrics-output/build-metrics.csv` | `scripts/measure-baseline.sh` in the selected pilot repo | Local warm/cold Docker build time and local image size |
| Drone build step logs | Drone CI pipeline UI (`docker build` step) | CI build duration and registry image size |
| Drone integration-test step logs | Drone CI pipeline UI (integration-tests step) | Integration-test startup + run duration |
| Drone pipeline UI / API | Pipeline listing and step timings | Rolling average pipeline duration and failed/flaky pipeline rate |

The metrics template remains the final human-readable summary. Raw artefacts are supporting evidence and should be linked in the Source / method column.

---

*Feedback or questions? Contact the page owner or comment below.*
