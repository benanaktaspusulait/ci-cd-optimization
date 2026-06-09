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
2. **Create child pages** from the files listed in the Page Structure table below — each becomes a child of the parent page.
3. **Apply labels** to all pages: `proposal`, `ci-cd`, `pilot`, `cerberus-delivery`.
4. **Set status** to **Draft** on all pages until stakeholder review is complete.

### Upload Order

Create pages in numerical order. The parent page (`00`) must exist first, then child pages can be created in any order beneath it.

### Do NOT Upload (internal only)

The following files are prefixed with `_internal-` and should NOT be uploaded to Confluence:

- `_internal-07-backlog-detailed.md` — consolidated into `08-backlog-summary` + `15-detailed-task-definitions`
- `_internal-08-decisions-adr.md` — consolidated into `10-decisions-adr`
- `_internal-10-glossary.md` — consolidated into `14-glossary`
- `_internal-11-security-plan.md` — consolidated into `13-security-plan`
- `_internal-17-source-content-coverage.md` — internal coverage check, not stakeholder content

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
| `07-references.md` | References | Repositories, ADR summary, KT sessions, and technology documentation. |
| `08-backlog-summary.md` | Backlog Summary | 6 stories and 23 tasks, story-point estimates, and ticket creation order. |
| `09-future-considerations.md` | Future Considerations | Post-pilot roadmap, production readiness gaps, technical opportunities. |
| `10-decisions-adr.md` | Architecture Decisions (ADRs) | 5 ADRs — context, decision, consequences, alternatives, and template. |
| `11-project-plan-and-governance.md` | Project Plan and Governance | Timeline, milestones, branching/CI flow, and governance. |
| `12-working-agreements-and-metrics.md` | Working Agreements and Metrics | Status board rules, Definition of Done, and metrics template. |
| `13-security-plan.md` | Security Plan | Secret handling, scanning policy, policy-as-code, supply-chain hardening. |
| `14-glossary.md` | Glossary | All terminology, abbreviations, and environment clarification. |
| `15-detailed-task-definitions.md` | Detailed Task Definitions | Full per-task why, goal, scope, and acceptance criteria for all 23 tasks. |
| `16-code-examples-and-templates.md` | Code Examples and Templates | Dockerfile, Compose, Testcontainers, and CI templates inline. |

---

## Key Points

- **Self-contained** — each page includes all necessary information inline. No other documents are needed to understand the content.
- **Draft status** — all pages are marked as Draft pending stakeholder review and feedback.
- **Consistent metadata** — each page has a metadata table at the top with parent page, status, labels, and last-updated date.
- **Feedback mechanism** — each page ends with a prompt for comments and questions.
- **Internal stubs** — files prefixed with `_internal-` are redirect/migration stubs that should NOT be uploaded to Confluence. They exist to preserve repo links and document consolidation history.

---

## Labels to Apply

When creating pages in Confluence, apply the following labels to all pages:

- `proposal`
- `ci-cd`
- `pilot`
- `cerberus-delivery`

---

*Feedback or questions? Contact the page owner or comment below.*
