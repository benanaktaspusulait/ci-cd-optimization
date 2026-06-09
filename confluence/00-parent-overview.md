# Container & CI/CD Optimisation Pilot — FDP Initial Scope

| Field | Value |
|-------|-------|
| **Owner** | TBC (CST / Cerberus Delivery) |
| **Status** | Draft |
| **Created** | 2026-06-09 |
| **Last updated** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

---

## Executive Summary

This page presents a proposed pilot to improve container build and integration-test performance for one FDP adaptor repository. The pilot validates improvement ideas locally with before/after evidence before proposing any wider rollout.

The current CI pipeline experiences long build times (~5 min Docker build, ~12 min total pipeline), heavy Docker Compose test setup (~90 sec startup with 7+ services), flaky integration tests (shared state, environment drift), and oversized Docker images (~450 MB shipping a full JDK and OS tools to production). These slow the developer feedback loop and may delay security patches reaching production.

> **Note:** The numbers above are initial observations. Concrete baseline values are not assumed — capturing and validating them is the first step after pipeline assessment (Story 2).

The pilot focuses on what CST/Cerberus Delivery can validate locally (Dockerfile optimisation, Testcontainers prototype, Compose review) while clearly identifying what requires ACP or DSA ETO/Enabling coordination.

A decision is needed on whether to proceed with the pilot and which repository to use.

---

## Context / Problem Statement

CI/CD and container workflows create recurring friction:

- **Long build times** — repeated dependency downloads (~200 MB Maven deps), poor layer caching, large build contexts (~200 MB sent to Docker daemon).
- **Heavy integration-test setup** — full Docker Compose stacks (Zookeeper, Kafka, Schema Registry, Redis, LocalStack, 7 aggregator services, command adaptor) start for every CI run regardless of what the test actually needs.
- **Flaky, environment-dependent tests** — shared state between tests, environment drift between local and CI (port conflicts, resource limits, network differences).
- **Oversized images** — ~450 MB images shipping a full JDK and additional OS/runtime tools to production.
- **Unclear ownership** — some improvements are CST-local; others need ACP (CI tooling) or DSA ETO (wider patterns).
- **Centrally managed pipeline** — `.drone.star` is controlled via RepoSync; local pipeline edits are overwritten.

---

## Objectives

- Reduce local Docker build time by ≥ 30%.
- Reduce final image size by ≥ 30% (multi-stage build removes JDK from runtime).
- Reduce Docker build context by ≥ 50% (`.dockerignore`).
- Validate Testcontainers for one integration dependency (isolation, determinism).
- Map Docker Compose services and clarify CI vs local-debug roles.
- Classify each improvement as CST-local, ACP, or DSA ETO/Enabling.
- Produce evidence to support any platform change request.

---

## Scope

### In Scope

- Drone/RepoSync pipeline constraint assessment.
- Baseline measurement (pipeline timing, build time, image size, test setup).
- Dockerfile multi-stage build and `.dockerignore` validation.
- Local Testcontainers prototype for one dependency (Redis or Kafka).
- Docker Compose service mapping and CI/local classification.
- Ownership classification and stakeholder communication.

### Out of Scope

- Organisation-wide rollout.
- BuildKit remote cache implementation (requires ACP infrastructure).
- Shared base image creation (requires DSA ETO ownership).
- Drone/RepoSync pipeline changes (requires ACP approval).
- Removing all Docker Compose usage.
- Ephemeral environments.
- Deploy pipeline changes (Helm, release flow).

---

## Approach

A small, measurable pilot on **one** representative repository:

1. Assess Drone/RepoSync pipeline constraints (what can be changed locally).
2. Capture baseline metrics before any change.
3. Apply local Docker build optimisations and measure the impact.
4. Prototype Testcontainers locally for one integration dependency.
5. Map Compose usage and recommend a reduced CI role.
6. Consolidate findings and classify ownership (CST / ACP / DSA ETO).

Evidence-first: every change is proved with before/after numbers. No change is assumed beneficial until measured.

---

## Ownership and Prioritisation Boundaries

| Category | Scope | Examples | Agreed with |
|----------|-------|----------|-------------|
| **CST / Cerberus Delivery** | Repo-local changes | Dockerfile, `.dockerignore`, Maven profiles, test code, Compose review, measurement | Thomas Reddy, Cerberus Delivery stakeholders |
| **ACP** | CI/CD tooling | Drone runners, DIND image, BuildKit enablement, RepoSync changes, remote cache | ACP prioritisation |
| **DSA ETO / Enabling / CIT** | Wider platform patterns | Shared base images, shared templates, cross-project adoption, engineering standards | Ezhil's role, DSA Tech Strategy alignment |

> The current DSA focus is Core Cloud and Data Platform. Any platform/tooling-level improvement must be clearly separated from CST-local pilot work.

---

## Success Targets

**CST-local targets (achievable within the pilot):**

| Criterion | Target |
|-----------|--------|
| Docker build time (local) | ≥ 30% reduction |
| Final image size | ≥ 30% smaller |
| Build context size | ≥ 50% smaller |
| Testcontainers prototype | ≥ 1 dependency running locally |
| Compose services classified | All services mapped with CI/local role |
| Ownership documented | CST vs ACP vs DSA ETO classified |

**Platform-dependent targets (require ACP/ETO action after the pilot):**

| Criterion | Target | Requires |
|-----------|--------|----------|
| CI build time reduction | ≥ 20% | ACP/RepoSync: BuildKit enablement in `.drone.star` |
| CI pipeline duration | ≥ 20% | ACP: remote cache + Testcontainers CI support |
| Testcontainers in CI | Running in Drone pipeline | ACP/RepoSync: Maven step environment changes |

---

## Stories

| # | Story | Tasks | Depends on | Phase |
|---|-------|:-----:|------------|:-----:|
| 1 | Pipeline Assessment (Drone/RepoSync) | 5 | — | 1 |
| 2 | Baseline & Pilot Scope | 4 | 1 | 1 |
| 3 | Docker Build Optimisation | 4 | 2 | 1–2 |
| 4 | Testcontainers Pilot | 4 | 2 | 2 |
| 5 | Docker Compose Rationalisation | 3 | 4 | 2–3 |
| 6 | CST-local vs ACP/ETO Ownership Assessment | 3 | 3, 4, 5 | 3 |

```text
Story 1 (pipeline assessment, gate)
   └──> Story 2 (baseline, gate)
           ├──> Story 3 (build) ──────┐
           └──> Story 4 (testcontainers) ─┼──> Story 5 (compose)
                                          └──> Story 6 (ownership)
```

---

## Child Pages

| Page | Purpose |
|------|---------|
| [Proposal Matrix](01-proposal-matrix.md) | All proposals rated by Value, Risk, Complexity, Effort, MoSCoW |
| [Phased Plan](02-phased-plan.md) | Phase 1–4 delivery approach with success criteria |
| [Risks and DACI](03-risks-and-daci.md) | Risk register + decision areas requiring multi-stakeholder input |
| [Technical Details](04-technical-details.md) | Dockerfile, Testcontainers, BuildKit, Compose — full code examples |
| [Pipeline & Drone Context](05-pipeline-and-drone.md) | Drone/RepoSync constraints, CI vs Deploy pipeline |
| [Deployment & Release](06-deployment-and-release.md) | Deploy pipeline context (outside pilot scope) |
| [References](07-references.md) | Repositories, ADR summary, KT sessions, technology documentation |
| [Backlog Summary](08-backlog-summary.md) | 6 stories + 23 tasks, story-point estimates, ticket order |
| [Future Considerations](09-future-considerations.md) | Post-pilot roadmap and architecture decision candidates |
| [Architecture Decisions (ADRs)](10-decisions-adr.md) | 5 ADRs — context, decision, consequences, alternatives, template |
| [Project Plan and Governance](11-project-plan-and-governance.md) | Timeline, milestones, branching/CI flow, governance |
| [Working Agreements and Metrics](12-working-agreements-and-metrics.md) | Status board rules, Definition of Done, metrics template |
| [Security Plan](13-security-plan.md) | Secret handling, scanning, policy-as-code, supply-chain |
| [Glossary](14-glossary.md) | All terminology and environment clarification |
| [Detailed Task Definitions](15-detailed-task-definitions.md) | Full per-task why, goal, scope, acceptance criteria |
| [Code Examples and Templates](16-code-examples-and-templates.md) | Dockerfile, Compose, Testcontainers, CI templates |
| [Source Content Coverage](17-source-content-coverage.md) | Coverage map confirming no content was left behind |

---

## Remaining TBC Items

The following items remain as `TBC` because they are not yet confirmed:

- Final pilot repository and compared candidate repositories.
- Board links for specific tickets (GitLab issues or Jira).
- Page owners beyond "Created by: Benan Aktas".
- KT session dates and related Confluence links.
- Exact baseline metric values from Story 2.
- ACP/ETO acceptance and dates for post-pilot items.

These are not gaps — they are real unknowns to resolve during review and delivery.

---

*Feedback or questions? Contact the page owner or comment below.*
