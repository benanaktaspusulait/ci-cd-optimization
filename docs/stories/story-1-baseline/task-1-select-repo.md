# T1.1 — Select pilot repository/service

**Story:** [Story 1 — Baseline & Pilot Scope](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T1.1 | S | Must | _TBD_ | Not started | — |

## Why
The pilot needs a single, representative target. Picking a repo that is too large adds delivery risk; one that is too trivial won't validate the ideas. Choosing deliberately keeps the pilot controlled and credible.

## Goal
Agree on one FDP repository/service to use for the entire pilot.

## Scope
- Review candidate FDP repositories.
- Weigh each against: pipeline duration, Docker Compose usage, integration-test complexity, current delivery priority/risk.
- Recommend one repository and record why.

## Acceptance criteria
- [ ] One candidate repository/service is selected
- [ ] Selection rationale is documented (why this one, why not others)
- [ ] Pilot scope is agreed with relevant stakeholders

## Selection output (fill in when T1.1 is complete)

| Field | Value |
|-------|-------|
| Selected repository | _TBD_ |
| GitLab project URL | _TBD_ |
| GitLab environment | _TBD_ (self-hosted / GitLab.com — see [glossary](../../../docs/glossary.md)) |
| Primary language / build tool | _TBD_ (e.g. Java 17 / Maven) |
| Selection rationale | _TBD_ |
| Stakeholder who agreed scope | _TBD_ |

> After filling in this table: update `REGISTRY_IMAGE` in `.gitlab-ci.yml` and confirm the Maven cache path in `Dockerfile`.
