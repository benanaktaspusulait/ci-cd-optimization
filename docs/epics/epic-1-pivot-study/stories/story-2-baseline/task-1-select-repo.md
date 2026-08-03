# T2.1 — Compare candidate pipelines and select pilot repo

**Story:** [Story 2 — Baseline & Pilot Scope](README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T2.1 | 1 | Must | _TBD_ | Not started | T1.2 |

## Why
The pilot needs a single, representative target, but the recommendation should be portable. Comparing at least two candidate pipelines/repos keeps one eye on whether the pattern can later be replicated through RepoSync.

## Goal
Compare at least two FDP repositories/services, then agree on one repository/service to use for the pilot.

## Scope
- Review at least two candidate FDP repositories/pipelines.
- Weigh each against: pipeline duration, Docker Compose usage, integration-test complexity, current delivery priority/risk, and portability of the proposed pattern.
- Recommend one repository and record why.

## Acceptance criteria
- [ ] At least two candidate repositories/pipelines are compared
- [ ] One candidate repository/service is selected
- [ ] Selection rationale is documented (why this one, why not others)
- [ ] Portability notes are captured: what would transfer cleanly to another pipeline/repo, and what is repo-specific
- [ ] Pilot scope is agreed with relevant stakeholders

## Selection output (fill in when T2.1 is complete)

| Field | Value |
|-------|-------|
| Selected repository | _TBD_ |
| Compared candidate(s) | _TBD_ |
| GitLab project URL | _TBD_ |
| GitLab environment | _TBD_ (self-hosted / GitLab.com — see [glossary](../../../../glossary.md)) |
| Issue / board tracker | _TBD_ (GitLab issues/MRs for GitLab-hosted repos unless Jira remains the delivery tracker) |
| Primary language / build tool | _TBD_ (e.g. Java 17 / Maven) |
| Selection rationale | _TBD_ |
| Portability notes | _TBD_ |
| Stakeholder who agreed scope | _TBD_ |

> After filling in this table: confirm the tracker, verify Docker/Drone access, and confirm the Maven cache paths in `Dockerfile` / CI steps.
