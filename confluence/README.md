# Confluence Pages — README

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

---

## What Is This Folder?

This folder contains **Confluence-ready pages** for the CI/CD Optimisation Pilot proposal. Each file is a self-contained page — all information is inline and no external documents are needed.

---

## How to Upload to Confluence

1. **Create the parent page** from `00-parent-overview.md` — this is the top-level page that all others sit beneath.
2. **Create child pages** from the canonical files in the Page Structure table below.
3. **Apply labels** to all pages: `proposal`, `ci-cd`, `pilot`, `cerberus-delivery`.
4. **Set status** to **Draft** on all pages until stakeholder review is complete.

### Upload Order

Create pages in numerical order. The parent page (`00`) must exist first, then child pages can be created in any order beneath it.

### Duplicate-Numbered Files

Some numbers have two files (e.g. `07-backlog-detailed` and `07-references`). This is because the set was built in two passes. Both files contain valid content and can both be uploaded as separate child pages — they cover different topics despite sharing a number prefix.

---

## Page Structure

| File | Title | Purpose |
|------|-------|---------|
| `00-parent-overview.md` | Container & CI/CD Optimisation Pilot — FDP Initial Scope | Top-level parent page. Executive summary, context, objectives, scope, approach. |
| `01-proposal-matrix.md` | Proposal Matrix | All proposals rated by Value, Risk, Complexity, Effort, and MoSCoW priority. |
| `02-phased-plan.md` | Phased Plan | Phase 1–4 delivery approach with success criteria per phase. |
| `03-risks-and-daci.md` | Risks and DACI | Risk register and decision areas requiring multi-stakeholder input. |
| `04-technical-details.md` | Technical Details | Dockerfile, Testcontainers, BuildKit, Compose — full code examples inline. |
| `05-pipeline-and-drone.md` | Pipeline & Drone Context | Drone/RepoSync constraints, CI vs Deploy pipeline, DIND details. |
| `06-deployment-and-release.md` | Deployment & Release | Deploy pipeline context (outside pilot scope, documented for awareness). |
| `07-backlog-detailed.md` | Backlog — Detailed Stories and Tasks | 6 stories + 23 tasks with full why/goal/scope/acceptance criteria. |
| `07-references.md` | References | Repositories, ADR summary, KT sessions, and technology documentation. |
| `08-backlog-summary.md` | Backlog Summary | 6 stories and 23 tasks, story-point estimates, dependencies, and ticket creation order. |
| `08-decisions-adr.md` | Architecture Decision Records (ADRs) | 5 ADRs with detailed context, decision, consequences, alternatives. |
| `09-future-considerations.md` | Future Considerations | Post-pilot roadmap, production readiness gaps, technical opportunities. |
| `10-decisions-adr.md` | Architecture Decisions (ADR) | 5 ADRs — concise format with ADR Index, follow-ups, and template. |
| `10-glossary.md` | Glossary | All terminology with detailed definitions and environment clarification. |
| `11-project-plan-and-governance.md` | Project Plan and Governance | Timeline, milestones, branching/CI flow, and governance. |
| `11-security-plan.md` | Security Plan | Secret management, scanning, policy-as-code, supply-chain, reporting. |
| `12-working-agreements-and-metrics.md` | Working Agreements and Metrics | Status board rules, Definition of Done, and metrics template. |
| `13-security-plan.md` | Security Plan | Secret handling, scanning policy, policy-as-code, supply-chain hardening (consolidated). |
| `14-glossary.md` | Glossary | All terminology, abbreviations, and environment clarification (consolidated). |
| `15-detailed-task-definitions.md` | Detailed Task Definitions | Full per-task why, goal, scope, and acceptance criteria for all 23 tasks. |
| `16-code-examples-and-templates.md` | Code Examples and Templates | Dockerfile, Compose, Testcontainers, and CI templates inline. |
| `17-source-content-coverage.md` | Source Content Coverage | Internal coverage map confirming no content was left behind. |

---

## Key Points

- **Self-contained** — each page includes all necessary information inline. No other documents are needed to understand the content.
- **Draft status** — all pages are marked as Draft pending stakeholder review and feedback.
- **Consistent metadata** — each page has a metadata table at the top with parent page, status, labels, and last-updated date.
- **Feedback mechanism** — each page ends with a prompt for comments and questions.
- **Internal stubs** — some files share a number prefix with another file (e.g. `07-backlog-detailed` and `07-references`). Both contain valid content on different topics and can both be uploaded as separate Confluence child pages.

---

## Labels to Apply

When creating pages in Confluence, apply the following labels to all pages:

- `proposal`
- `ci-cd`
- `pilot`
- `cerberus-delivery`

---

*Feedback or questions? Contact the page owner or comment below.*
