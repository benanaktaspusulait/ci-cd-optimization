# Container & CI/CD Optimisation Pilot — FDP Initial Scope

| Field | Value |
|-------|-------|
| **Owner** | TBC (CST / Cerberus Delivery) |
| **Created by** | Benan Aktas |
| **Status** | In progress — pilot evidence prepared; T6.2 closure evidence missing |
| **Created** | 2026-06-09 |
| **Last updated** | 2026-08-03 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

---

## Executive Summary

> **Lifecycle update:** No implementation story will be added after Story 6. Owner review, stakeholder share-out, dispositions and routed next actions are still missing from T6.2, so the pilot is not marked Done. Approved SNS productionisation and the separately gated CD pipeline review are task workstreams in one proposed follow-up epic.

This page presents a proposed pilot to improve container build and integration-test performance for one FDP adaptor repository. The pilot validates improvement ideas locally with before/after evidence before proposing any wider rollout.

The current CI pipeline experiences long build times (~5 min Docker build, ~12 min total pipeline), heavy Docker Compose test setup (~90 sec startup with 7+ services), flaky integration tests (shared state, environment drift), and oversized Docker images (~450 MB shipping a full JDK and OS tools to production). These slow the developer feedback loop and may delay security patches reaching production.

> **Note:** The numbers above are initial observations. Concrete baseline values are not assumed — capturing and validating them is the first step after pipeline assessment (Story 2).

The pilot focuses on what CST/Cerberus Delivery can validate locally (Dockerfile optimisation, Testcontainers prototype, Compose review) while clearly identifying what requires ACP or DSA ETO/Enabling coordination.

A decision is needed on whether to proceed with the pilot and which repository to use.

---

## Context / Problem Statement

CI/CD and container workflows create recurring friction:

- **Long build times** — poor Docker layer caching, large build contexts (~200 MB sent to Docker daemon). The CI Maven step may also suffer from repeated dependency resolution (~200 MB) if caching is not effective; this will be validated during Story 2 baseline capture.
- **Heavy integration-test setup** — full Docker Compose stacks (Zookeeper, Kafka, Schema Registry, Redis, LocalStack, 7 aggregator services, command adaptor) start for every CI run regardless of what the test actually needs.
- **Flaky, environment-dependent tests** — shared state between tests, environment drift between local and CI (port conflicts, resource limits, network differences).
- **Oversized images** — ~450 MB images shipping a full JDK and additional OS/runtime tools to production.
- **Unclear ownership** — some improvements are CST-local; others need ACP (CI tooling) or DSA ETO (wider patterns).
- **Centrally managed pipeline** — `.drone.star` is controlled via RepoSync; local pipeline edits are overwritten.

---

## Objectives

> **Note:** Numeric targets below are initial aspirations. They will be validated and adjusted once Story 2 captures the concrete baseline.

- Reduce local Docker build time by ≥ 30%.
- Reduce final image size by ≥ 30% (multi-stage build removes build tools from runtime; runtime base choice — e.g. `amazoncorretto:17` slim variant or JRE-only — to be validated in Story 3).
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

A small, measurable pilot on **one** representative repository, selected after comparing at least **two** candidate repositories/pipelines for portability:

1. Assess Drone/RepoSync pipeline constraints (what can be changed locally).
2. Compare at least two candidate repositories/pipelines and select one pilot target.
3. Capture baseline metrics before any change.
4. Apply local Docker build optimisations and measure the impact.
5. Prototype Testcontainers locally for one integration dependency.
6. Map Compose usage and recommend a reduced CI role.
7. Consolidate findings and classify ownership (CST / ACP / DSA ETO).

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

> **Note:** Numeric targets are initial aspirations, subject to validation after Story 2 baseline capture. They may be adjusted once concrete measurements are available.

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
| 5 | Docker Compose Rationalisation | 2 | 4 | 2–3 |
| 6 | Pilot Outcome, Ownership and Adoption | 2 | 3, 4, 5 | 3 |

```text
Story 1 (pipeline assessment, gate)
   └──> Story 2 (baseline, gate)
           ├──> Story 3 (build) ──────┐
           └──> Story 4 (testcontainers) ─┼──> Story 5 (compose)
                                          └──> Story 6 (findings/ownership)
```

## Proposed follow-up delivery epic

| Epic | Scope | Status |
|---|---|---|
| Post-Pilot Container and CD Delivery | Full SNS build/integration/CI transition plus evidence-gated CD implementation | Proposed / New |

These are not Story 7 or further implementation stories within the pilot.

---

## Child Pages

| Page | Purpose |
|------|---------|
| Proposal Matrix | All proposals rated by Value, Risk, Complexity, Effort, MoSCoW |
| Phased Plan | Phase 1–4 delivery approach with success criteria |
| Risks and DACI | Risk register + decision areas requiring multi-stakeholder input |
| Technical Details | Index — Docker build and Testcontainers technical content |
| Technical Details — Docker Build | Dockerfile, .dockerignore, Compose, BuildKit, base image |
| Technical Details — Testcontainers | Container configs, Spring/Cucumber integration, Maven, CI feasibility |
| Pipeline & Drone Context | Drone/RepoSync constraints, CI vs Deploy pipeline |
| Deployment & Release | Deploy pipeline context (outside pilot scope) |
| Supporting Context | System context, ADR summary, KT sessions, optional technology references |
| Backlog Summary | 6 stories + 23 tasks, story-point estimates, dependencies, ticket order |
| Future Considerations | Post-pilot roadmap and architecture decision candidates |
| Architecture Decisions (ADRs) | Index — 5 ADRs with status and template |
| ADR — Pilot Approach | ADR-0001: Why a measured pilot, not rollout |
| ADR — Testcontainers & Compose | ADR-0002 + ADR-0003: Integration test and Compose decisions |
| ADR — BuildKit & CI Runner | ADR-0004 + ADR-0005: Build cache and CI Docker mode decisions |
| Project Plan and Governance | Timeline, milestones, branching/CI flow, governance |
| Working Agreements and Metrics | Status board rules, Definition of Done, metrics template |
| Security Plan | Secret handling, scanning, policy-as-code, supply-chain |
| Glossary | All terminology and environment clarification |
| Detailed Task Definitions | Index for all 21 task definitions, split by story |
| Tasks — Story 1 | Pipeline Assessment (T1.1–T1.5) |
| Tasks — Story 2 | Baseline & Pilot Scope (T2.1–T2.4) |
| Tasks — Story 3 | Docker Build Optimisation (T3.1–T3.4) |
| Tasks — Story 4 | Testcontainers Pilot (T4.1–T4.4) |
| Tasks — Story 5 | Docker Compose Rationalisation (T5.1–T5.2) |
| Tasks — Story 6 | Pilot Outcome, Ownership and Adoption (T6.1–T6.2) |
| Code Examples and Templates | Dockerfile, Compose, Testcontainers, CI templates |

---

## Remaining TBC Items

Open items to resolve during review and delivery:

- Final pilot repository and compared candidate repositories.
- Jira ticket links and target board ownership for specific tickets.
- Page owners beyond "Created by: Benan Aktas".
- KT session dates and related Confluence links.
- Exact baseline metric values from Story 2.
- ACP/ETO acceptance and dates for post-pilot items.
- Calendar dates for the 4-week plan (currently relative weeks).

These items are acceptable while the page is in Draft status. They must be assigned or resolved before the page moves to In Review or Approved.

---

*Feedback or questions? Contact the page owner or comment below.*
