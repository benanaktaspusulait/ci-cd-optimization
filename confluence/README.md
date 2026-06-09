# Confluence Pages — Container & CI/CD Optimisation Pilot

This folder contains Confluence-ready documentation following the [Confluence Documentation Standards](../standards.md).

## Purpose

These pages are designed for **copy-paste into Confluence** as a parent/child page structure. They provide the same content as the repository documentation but formatted for non-technical stakeholders, decision-makers, and wider engineering teams who do not have GitLab access.

## Page structure

```text
Parent page: 00-parent-overview.md
  ├── 01-proposal-matrix.md        → Value / Risk / Complexity / Effort / MoSCoW
  ├── 02-phased-plan.md            → Phase 1–4 delivery approach
  ├── 03-risks-and-daci.md         → Risk register + DACI decision areas
  ├── 04-technical-details.md      → Dockerfile, Testcontainers, BuildKit, Compose
  ├── 05-pipeline-and-drone.md     → Drone/RepoSync constraints, CI vs Deploy
  ├── 06-deployment-and-release.md → Deploy pipeline, Helm, release flow (context only)
  ├── 07-references.md             → Source links, ADRs, KT sessions, external docs
  └── 08-backlog-summary.md        → Story/task table (Jira-ready)
```

## How to use

1. Create a new parent page in Confluence with the content from `00-parent-overview.md`.
2. Create child pages for each numbered file (01–08).
3. Replace `TBC` items as decisions are made.
4. Apply Confluence labels: `proposal`, `ci-cd`, `pilot`, `cerberus-delivery`.
5. Set status to "Draft" until stakeholder review is complete.

## Relationship to repo docs

These pages are a **Confluence-formatted view** of the same information in `docs/`, `examples/`, and the root README. The repo remains the source of truth for technical detail; Confluence is the collaboration and decision-making surface.

| Need | Use |
|------|-----|
| Detailed task definitions with metadata | `docs/stories/` (repo) |
| ADR full text with alternatives table | `docs/adr/` (repo) |
| Code examples | `examples/` (repo) |
| Stakeholder overview + decision surface | This `confluence/` folder |
