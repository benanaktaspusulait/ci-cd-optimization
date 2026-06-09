# Source Content Coverage

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

This page maps the original repository documentation to the Confluence-ready page set. Its purpose is to make sure no meaningful content from the old structure is left behind.

---

## Coverage Summary

| Original source | Confluence page(s) | Coverage |
|-----------------|--------------------|----------|
| `README.md` | [Parent Overview](00-parent-overview.md), [Backlog Summary](08-backlog-summary.md), [Source Content Coverage](17-source-content-coverage.md) | Covered |
| `PROJECT-PLAN.md` | [Project Plan and Governance](11-project-plan-and-governance.md), [Phased Plan](02-phased-plan.md), [Risks and DACI](03-risks-and-daci.md), [Deployment and Release Context](06-deployment-and-release.md) | Covered |
| `SECURITY.md` | [Security Plan](13-security-plan.md), [Technical Details](04-technical-details.md) | Covered |
| `CONTRIBUTING.md` | [Working Agreements and Metrics](12-working-agreements-and-metrics.md) | Covered |
| `COMBINED-DOCUMENTATION.md` | This is a generated aggregate of the same source documents; its contents are covered by the full Confluence page set | Covered |
| `standards.md` | Conversion standard used to shape this Confluence set; not project delivery content | Used as standard |
| `docs/PROJECT-CONTEXT.md` | [Parent Overview](00-parent-overview.md), [Proposal Matrix](01-proposal-matrix.md), [Working Agreements and Metrics](12-working-agreements-and-metrics.md) | Covered |
| `docs/PIPELINE-CONTEXT.md` | [Pipeline and Drone Context](05-pipeline-and-drone.md), [Deployment and Release Context](06-deployment-and-release.md), [Project Plan and Governance](11-project-plan-and-governance.md) | Covered |
| `docs/SCOPE-AND-GUARDRAILS.md` | [Parent Overview](00-parent-overview.md), [Phased Plan](02-phased-plan.md), [Future Considerations](09-future-considerations.md), [Detailed Task Definitions](15-detailed-task-definitions.md) | Covered |
| `docs/glossary.md` | [Glossary](14-glossary.md) | Covered |
| `docs/stories/INDEX.md` | [Backlog Summary](08-backlog-summary.md), [Working Agreements and Metrics](12-working-agreements-and-metrics.md) | Covered |
| `docs/stories/STATUS-BOARD.md` | [Working Agreements and Metrics](12-working-agreements-and-metrics.md), [Backlog Summary](08-backlog-summary.md) | Covered |
| `docs/stories/DEFINITION-OF-DONE.md` | [Working Agreements and Metrics](12-working-agreements-and-metrics.md) | Covered |
| `docs/stories/metrics-template.md` | [Working Agreements and Metrics](12-working-agreements-and-metrics.md) | Covered |
| `docs/stories/tech-notes.md` | [Technical Details](04-technical-details.md), [Security Plan](13-security-plan.md), [Code Examples and Templates](16-code-examples-and-templates.md) | Covered |
| `docs/stories/FUTURE-CONSIDERATIONS.md` | [Future Considerations](09-future-considerations.md), [Deployment and Release Context](06-deployment-and-release.md) | Covered |
| `docs/stories/story-*/README.md` | [Backlog Summary](08-backlog-summary.md), [Detailed Task Definitions](15-detailed-task-definitions.md) | Covered |
| `docs/stories/story-*/*.md` task files | [Detailed Task Definitions](15-detailed-task-definitions.md), [Working Agreements and Metrics](12-working-agreements-and-metrics.md) | Covered |
| `docs/adr/README.md` | [References](07-references.md), [Architecture Decisions](10-decisions-adr.md) | Covered |
| `docs/adr/0001-pilot-not-rollout.md` | [Architecture Decisions](10-decisions-adr.md), [Detailed Task Definitions](15-detailed-task-definitions.md) | Covered |
| `docs/adr/0002-testcontainers-for-integration-tests.md` | [Architecture Decisions](10-decisions-adr.md), [Code Examples and Templates](16-code-examples-and-templates.md) | Covered |
| `docs/adr/0003-reduce-compose-in-ci.md` | [Architecture Decisions](10-decisions-adr.md), [Pipeline and Drone Context](05-pipeline-and-drone.md), [Code Examples and Templates](16-code-examples-and-templates.md) | Covered |
| `docs/adr/0004-buildkit-cache-and-layering.md` | [Architecture Decisions](10-decisions-adr.md), [Technical Details](04-technical-details.md), [Code Examples and Templates](16-code-examples-and-templates.md) | Covered |
| `docs/adr/0005-ci-runner-docker-mode.md` | [Architecture Decisions](10-decisions-adr.md), [Pipeline and Drone Context](05-pipeline-and-drone.md), [Code Examples and Templates](16-code-examples-and-templates.md) | Covered |
| `docs/adr/template.md` | [Architecture Decisions](10-decisions-adr.md) | Covered |
| `examples/README.md` | [Code Examples and Templates](16-code-examples-and-templates.md), [Technical Details](04-technical-details.md) | Covered |
| `examples/ci/drone-considerations.md` | [Code Examples and Templates](16-code-examples-and-templates.md), [Pipeline and Drone Context](05-pipeline-and-drone.md) | Covered |
| `examples/ci/gitlab-ci-integration-test.yml` | [Code Examples and Templates](16-code-examples-and-templates.md) | Covered |
| `examples/docker/Dockerfile` | [Code Examples and Templates](16-code-examples-and-templates.md), [Technical Details](04-technical-details.md) | Covered |
| `examples/docker/.dockerignore` | [Technical Details](04-technical-details.md), [Code Examples and Templates](16-code-examples-and-templates.md), [Detailed Task Definitions](15-detailed-task-definitions.md) | Covered |
| `examples/docker/docker-compose.yml` | [Technical Details](04-technical-details.md), [Code Examples and Templates](16-code-examples-and-templates.md) | Covered |
| `examples/testcontainers/*.java` | [Technical Details](04-technical-details.md), [Code Examples and Templates](16-code-examples-and-templates.md), [Detailed Task Definitions](15-detailed-task-definitions.md) | Covered |
| `examples/testcontainers/pom-dependencies.xml` | [Code Examples and Templates](16-code-examples-and-templates.md), [Technical Details](04-technical-details.md) | Covered |

---

## Notes on Transfer Style

- Stakeholder summary content was transferred to [Parent Overview](00-parent-overview.md).
- Decision and prioritisation content was split into [Proposal Matrix](01-proposal-matrix.md), [Phased Plan](02-phased-plan.md), [Risks and DACI](03-risks-and-daci.md), and [Architecture Decisions](10-decisions-adr.md).
- Deep technical and executable example content was kept out of the parent page and moved to [Technical Details](04-technical-details.md) and [Code Examples and Templates](16-code-examples-and-templates.md).
- Live tracking rules, Definition of Done and metrics are preserved in [Working Agreements and Metrics](12-working-agreements-and-metrics.md).
- Security policy is preserved in [Security Plan](13-security-plan.md).
- Glossary/environment clarification is preserved in [Glossary](14-glossary.md).

---

## Remaining TBC Items

The content has been transferred, but some values intentionally remain `TBC` because they are not yet confirmed:

- Final pilot repository and compared candidate repositories.
- GitLab/board links for specific tickets.
- Page owners beyond "Created by: Benan Aktas".
- KT session dates and related Confluence links.
- Exact baseline metric values from Story 2.
- ACP/ETO acceptance and dates for post-pilot items.

These are not transfer gaps; they are real unknowns to resolve during review and delivery.

---

*Feedback or questions? Contact the page owner or comment below.*
