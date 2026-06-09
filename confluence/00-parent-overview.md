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

The current CI pipeline experiences long build times, heavy Docker Compose test setup, flaky integration tests, and oversized Docker images. These slow the developer feedback loop and may delay security patches reaching production.

The pilot focuses on what CST/Cerberus Delivery can validate locally (Dockerfile optimisation, Testcontainers prototype, Compose review) while clearly identifying what requires ACP or DSA ETO/Enabling coordination.

A decision is needed on whether to proceed with the pilot and which repository to use.

---

## Context / Problem Statement

CI/CD and container workflows create recurring friction:

- **Long build times** — repeated dependency downloads, poor layer caching, large build contexts (~200 MB sent to Docker daemon).
- **Heavy integration-test setup** — full Docker Compose stacks (~7 aggregators + Kafka + Redis + Schema Registry) start for every CI run regardless of what the test actually needs.
- **Flaky tests** — shared state between tests, environment-dependent failures, differences between local and CI behaviour.
- **Oversized images** — ~450 MB images shipping JDK and build tools to production.
- **Unclear ownership** — some improvements are CST-local; others need ACP (CI tooling) or DSA ETO (wider patterns).

---

## Objectives

- Reduce local Docker build time by ≥ 30%.
- Reduce final image size by ≥ 30% (multi-stage build, remove JDK from runtime).
- Reduce Docker build context by ≥ 50% (`.dockerignore`).
- Validate Testcontainers for one integration dependency (isolation, determinism).
- Map Docker Compose services and clarify CI vs local-debug roles.
- Classify each improvement as CST-local, ACP, or DSA ETO/Enabling.
- Produce evidence to support any platform change request.

---

## Scope

### In Scope

- Baseline measurement (pipeline timing, build time, image size, test setup).
- Dockerfile multi-stage build and `.dockerignore` validation.
- Local Testcontainers prototype for one dependency (Redis or Kafka).
- Docker Compose service mapping and CI/local classification.
- Drone/RepoSync pipeline constraint assessment.
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

## Ownership Boundaries

| Category | Scope | Examples | Agreed with |
|----------|-------|----------|-------------|
| **CST / Cerberus Delivery** | Repo-local changes | Dockerfile, `.dockerignore`, Maven profiles, test code, Compose review, measurement | Thomas Reddy, Cerberus Delivery stakeholders |
| **ACP** | CI/CD tooling | Drone runners, DIND image, BuildKit enablement, RepoSync changes, remote cache | ACP prioritisation |
| **DSA ETO / Enabling / CIT** | Wider platform patterns | Shared base images, shared templates, cross-project adoption, engineering standards | Ezhil's role, DSA Tech Strategy alignment |

---

## Success Summary (CST-local targets)

| Criterion | Target |
|-----------|--------|
| Docker build time (local) | ≥ 30% reduction |
| Final image size | ≥ 30% smaller |
| Build context size | ≥ 50% smaller |
| Testcontainers prototype | ≥ 1 dependency running locally |
| Compose services classified | All services mapped with CI/local role |
| Ownership documented | CST vs ACP vs DSA ETO classified |

---

## Child Pages

| Page | Purpose |
|------|---------|
| [Proposal Matrix](01-proposal-matrix.md) | All proposals rated by Value, Risk, Complexity, Effort, MoSCoW |
| [Phased Plan](02-phased-plan.md) | Phase 1–4 delivery approach with success criteria |
| [Risks and DACI](03-risks-and-daci.md) | Risk register + decision areas requiring multi-stakeholder input |
| [Technical Details](04-technical-details.md) | Dockerfile, Testcontainers, BuildKit, Compose — deep technical content |
| [Pipeline & Drone Context](05-pipeline-and-drone.md) | Drone/RepoSync constraints, CI vs Deploy pipeline |
| [Deployment & Release](06-deployment-and-release.md) | Deploy pipeline context (outside pilot scope) |
| [References](07-references.md) | Source links, ADRs, KT sessions |
| [Backlog Summary](08-backlog-summary.md) | Story/task list (Jira-ready) |

---

*Feedback or questions? Contact the page owner or comment below.*
