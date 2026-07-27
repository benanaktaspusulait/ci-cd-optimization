# Combined Documentation

Generated from the current project Markdown files. Source documents are left unchanged.

## Included Documents

- `README.md`
- `docs/PROJECT-CONTEXT.md`
- `docs/PIPELINE-CONTEXT.md`
- `docs/SCOPE-AND-GUARDRAILS.md`
- `PROJECT-PLAN.md`
- `SECURITY.md`
- `CONTRIBUTING.md`
- `docs/glossary.md`
- `docs/stories/STATUS-BOARD.md`
- `docs/stories/INDEX.md`
- `docs/stories/STORY-5-6-CONSOLIDATION.md`
- `docs/stories/DEFINITION-OF-DONE.md`
- `docs/stories/metrics-template.md`
- `docs/stories/tech-notes.md`
- `docs/stories/FUTURE-CONSIDERATIONS.md`
- `docs/stories/story-1-pipeline-assessment/README.md`
- `docs/stories/story-1-pipeline-assessment/task-1-review-drone-star.md`
- `docs/stories/story-1-pipeline-assessment/task-2-local-vs-central.md`
- `docs/stories/story-1-pipeline-assessment/task-3-map-ci-steps.md`
- `docs/stories/story-1-pipeline-assessment/task-4-testcontainers-feasibility.md`
- `docs/stories/story-1-pipeline-assessment/task-5-buildkit-feasibility.md`
- `docs/stories/story-2-baseline/README.md`
- `docs/stories/story-2-baseline/task-1-select-repo.md`
- `docs/stories/story-2-baseline/task-2-pipeline-baseline.md`
- `docs/stories/story-2-baseline/task-3-build-image-baseline.md`
- `docs/stories/story-2-baseline/task-4-integration-test-baseline.md`
- `docs/stories/story-3-build/README.md`
- `docs/stories/story-3-build/task-1-review-dockerfile.md`
- `docs/stories/story-3-build/task-2-dockerignore.md`
- `docs/stories/story-3-build/task-3-layering-improvement.md`
- `docs/stories/story-3-build/task-4-measure-impact.md`
- `docs/stories/story-4-testcontainers/README.md`
- `docs/stories/story-4-testcontainers/task-1-select-candidate.md`
- `docs/stories/story-4-testcontainers/task-2-implement-setup.md`
- `docs/stories/story-4-testcontainers/task-3-compare-flows.md`
- `docs/stories/story-4-testcontainers/task-4-document-findings.md`
- `docs/stories/story-5-compose/README.md`
- `docs/stories/story-5-compose/task-1-validate-compose-scope.md`
- `docs/stories/story-5-compose/task-2-decide-compose-role.md`
- `docs/stories/story-6-findings/README.md`
- `docs/stories/story-6-findings/task-1-classify-outcomes.md`
- `docs/stories/story-6-findings/task-2-decide-adoption.md`
- `docs/adr/README.md`
- `docs/adr/0001-pilot-not-rollout.md`
- `docs/adr/0002-testcontainers-for-integration-tests.md`
- `docs/adr/0003-reduce-compose-in-ci.md`
- `docs/adr/0004-buildkit-cache-and-layering.md`
- `docs/adr/0005-ci-runner-docker-mode.md`
- `docs/adr/template.md`
- `examples/README.md`
- `examples/ci/drone-considerations.md`

---

## Overview

> Source: `README.md`

# Container & CI/CD Optimisation Pilot

> **Status:** Pilot planning — not an approved implementation programme.
> **Scope:** FDP as the pilot context; patterns may be reusable more widely if proven.
> **Intent:** Validate a few optimisation ideas through a small, measurable pilot before any wider rollout.

**Key message:** the difference is not Docker vs no Docker — it's *optimised, standardised, cached and test-driven* Docker usage.

> **Repository type:** this repository is a planning and template pack, not the selected application repository. Root-level files (`Dockerfile`, `docker-compose.yml`, `scripts/measure-baseline.sh`) are starting templates to copy/adapt after T2.1 selects a pilot repo with application sources (`pom.xml`, `mvnw`, `.mvn/`, `src/`).

---

## Start Here

| Need | Go to |
|------|-------|
| Understand the pilot quickly | This README |
| Track live task progress | [Status board](docs/stories/STATUS-BOARD.md) |
| See every story and task | [Backlog index](docs/stories/INDEX.md) |
| Understand why this matters | [Project context](docs/PROJECT-CONTEXT.md) |
| Understand Drone / RepoSync constraints | [Pipeline context](docs/PIPELINE-CONTEXT.md) |
| Check in/out of scope and deferred work | [Scope and guardrails](docs/SCOPE-AND-GUARDRAILS.md) |
| Follow the timeline, risks and verification plan | [Project plan](PROJECT-PLAN.md) |

**Audience shortcuts:** developers usually start with the [backlog index](docs/stories/INDEX.md), [status board](docs/stories/STATUS-BOARD.md), [technical notes](docs/stories/tech-notes.md), and [ADRs](docs/adr/README.md). Ops/platform readers should also review the [security plan](SECURITY.md), [pipeline context](docs/PIPELINE-CONTEXT.md), and Story 6 ownership classification.

---

## What This Is

A small, measurable pilot on **one** representative repository:

1. Assess the centrally managed Drone/RepoSync pipeline boundary.
2. Compare at least two candidate pipelines/repos for portability, then select one pilot target.
3. Capture baseline metrics before changing anything.
4. Optimise Docker build inputs and layering.
5. Pilot Testcontainers for one integration dependency.
6. Rationalise Docker Compose usage without breaking local workflows.
7. Consolidate findings and classify ownership as CST-local, ACP-owned, or DSA ETO/Enabling.

The pilot produces evidence and recommendations. It does **not** approve an org-wide rollout, remove all Compose usage, or build shared platform capabilities by itself.

---

## Core Constraint

FDP adaptor repositories use a centrally managed `.drone.star` pipeline deployed via **RepoSync**. Local pipeline edits are not a durable delivery route because RepoSync owns the source of truth.

That means repository-local work can cover Dockerfiles, `.dockerignore`, Maven profiles, tests, examples and measurement. Pipeline-level changes such as DIND image changes, BuildKit enablement, Testcontainers CI environment variables, remote cache and shared templates require ACP/RepoSync coordination.

For reusable changes, the target route is an ACP/RepoSync MR so the pattern can be distributed to other pipelines through the normal process.

Story 1 exists to make that boundary explicit before the rest of the pilot proceeds. Full detail lives in [Pipeline context](docs/PIPELINE-CONTEXT.md).

---

## Ownership and Prioritisation Boundaries

Not all improvement ideas can be progressed through the same route. Three distinct ownership categories apply:

**A. CST / Cerberus Delivery scoped items**

These may be validated locally within CST/Cerberus Delivery, subject to agreement with Thomas Reddy and relevant Cerberus Delivery stakeholders.

- Baseline measurement
- Dockerfile review and `.dockerignore` validation
- Docker Compose usage review
- Local Dockerfile layering experiment
- Local Testcontainers prototype
- Ownership classification and recommendations

**B. ACP-owned CI/CD tooling items**

These touch CI/CD tooling or pipeline capabilities managed by ACP and would require ACP prioritisation.

- Drone runner changes
- DIND image changes
- BuildKit remote cache infrastructure
- CI cache infrastructure
- Pipeline tooling changes (`.drone.star` / RepoSync)
- Testcontainers environment variables in Drone steps

**C. DSA ETO / Enabling / CIT items**

These may require wider DSA ETO/Enabling prioritisation and should be assessed against DSA Tech Strategy, Core Cloud and Data Platform direction.

- Shared engineering templates
- Reusable platform patterns
- Shared Testcontainers helper libraries
- Future platform enablement capabilities
- Cross-project adoption model
- Organisation-maintained base images

> **Note:** The current DSA focus is moving to Core Cloud and Data Platform. Any platform/tooling-level improvement must be clearly separated from CST-local pilot work and aligned with that direction.

---

## Success Targets Summary

Targets are proposed and confirmed against the real baseline in Story 2. Detailed measurement fields live in the [metrics template](docs/stories/metrics-template.md).

**CST-local targets:**

| Success criterion | Target | Evidence |
|-------------------|--------|----------|
| Pilot repo baselined | All baseline metrics captured | Story 2, Drone UI, local scripts |
| Docker build time (local) | **≥ 30%** reduction | Before/after build timing |
| Final image size | **≥ 30%** smaller | `docker images` |
| Build context size | **≥ 50%** smaller | Docker build context log |
| Testcontainers prototype | ≥ 1 dependency running locally and compared to Compose | Story 4 |
| Integration test determinism | No shared state; isolated containers per run | Story 4 repeatability evidence |
| Compose services classified | All services mapped with CI vs local role | Story 5 |
| Ownership documented | CST-local vs ACP vs DSA ETO/Enabling classified | Story 6 |

**Platform-dependent targets after the pilot:**

| Success criterion | Target | Requires |
|-------------------|--------|----------|
| CI build time reduction | **≥ 20%** | ACP/RepoSync: BuildKit enablement in `.drone.star` |
| CI pipeline duration | **≥ 20%** | ACP: remote cache + Testcontainers CI support |
| Testcontainers in CI | Running in Drone pipeline | ACP/RepoSync: Maven step environment changes |

---

## Stories

| # | Story | Tasks | Depends on | Parallel with |
|---|-------|:-----:|------------|----------------|
| 1 | [Pipeline Assessment (Drone/RepoSync)](docs/stories/story-1-pipeline-assessment/README.md) | 5 | — | — |
| 2 | [Baseline & Pilot Scope](docs/stories/story-2-baseline/README.md) | 4 | 1 | — |
| 3 | [Docker Build Optimisation](docs/stories/story-3-build/README.md) | 4 | 2 | 4 |
| 4 | [Testcontainers Pilot](docs/stories/story-4-testcontainers/README.md) | 4 | 2 | 3 |
| 5 | [Docker Compose Rationalisation](docs/stories/story-5-compose/README.md) | 3 | 4 | — |
| 6 | [CST-local vs ACP/ETO Ownership Assessment](docs/stories/story-6-findings/README.md) | 3 | 3, 4, 5 | — |

```text
Story 1 (pipeline assessment, gate)
   └──> Story 2 (baseline, gate)
           ├──> Story 3 ─┐
           └──> Story 4 ─┼──> Story 5
                         └──> Story 6
```

Progress is tracked in the [status board](docs/stories/STATUS-BOARD.md). The [backlog index](docs/stories/INDEX.md) is the compact list of story/task files.

---

## Documentation Map

| Document | Purpose |
|----------|---------|
| [Project context](docs/PROJECT-CONTEXT.md) | Background, current state, business impact, technology stack |
| [Pipeline context](docs/PIPELINE-CONTEXT.md) | Drone/RepoSync constraint and CI vs deploy pipeline boundary |
| [Scope and guardrails](docs/SCOPE-AND-GUARDRAILS.md) | Pilot scope, assumptions, open questions, deferred work |
| [Project plan](PROJECT-PLAN.md) | Timeline, risk register, branching, test strategy |
| [Status board](docs/stories/STATUS-BOARD.md) | Single source of truth for task status |
| [Backlog index](docs/stories/INDEX.md) | Story/task outline with links |
| [Security plan](SECURITY.md) | Secret handling, scanning, supply-chain hardening |
| [Technical notes](docs/stories/tech-notes.md) | Base images, BuildKit, Testcontainers and security references |
| [ADRs](docs/adr/README.md) | Architecture decisions and trade-offs |
| [Examples](examples/README.md) | Docker, CI and Testcontainers examples |
| [Glossary](docs/glossary.md) | FDP/CST/ETO/Drone terminology |
| [Contributing](CONTRIBUTING.md) | How to work with the backlog |


---

> Source: `docs/PROJECT-CONTEXT.md`

# Project Context

Why this pilot exists, what it is trying to improve, and how success is measured. [← Back to overview](../README.md)

---

## Background

CI/CD and container workflows create recurring friction as projects grow. The concrete pain points behind this pilot:

- **Long build times** — repeated dependency downloads, poor layer caching, large build contexts.
- **Heavy integration-test setup** — full Docker Compose stacks are slow to start and share hidden state.
- **Flaky, environment-dependent tests** — failures that depend on local vs CI environment differences.
- **Inconsistent Dockerfiles** across repositories, with no shared base-image strategy.
- **Unclear ownership** — some improvements are local to CST, others need RepoSync/platform or wider ETO.

Concrete baseline numbers are **not assumed**. Story 2 captures real build time, image size, pipeline timing and integration-test baseline data before implementation changes begin.

---

## Current State (to be confirmed in Story 2)

These are **placeholder estimates** based on initial observations. Exact values will be captured in T2.2–T2.4 and recorded in the [metrics template](stories/metrics-template.md).

| Metric | Estimated current state | Target (pilot, local) |
|--------|-------------------------|------------------------|
| Docker build time (local) | ~5 min | < 3.5 min (≥ 30% ↓) |
| Final image size | ~450 MB | < 315 MB (≥ 30% ↓, multi-stage removes JDK) |
| Build context size | ~200 MB (estimated) | < 100 MB (≥ 50% ↓, .dockerignore) |
| Integration test startup (local, Testcontainers) | ~90 sec (Compose) | < 30 sec (isolated containers) |
| Flaky / failed pipeline rate | ~5% | Determinism proven locally; CI rate unchanged until platform acts |
| Developer feedback loop (local change → test green) | ~8 min | < 5 min |

These numbers will be replaced with real data once Story 2 is complete.

---

## Business Impact (estimated)

- **Developer productivity:** multi-stage builds + Testcontainers locally = ~3 min saved per build cycle. A developer hitting this ~8×/day = **~24 min saved per developer per day**. For a team of 5, that's **~2 hours/day** back into delivery.
- **Image size → transfer & storage:** 30% smaller image = faster pulls in every environment (dev/SIT/bVal/prod), less registry storage, faster rollout.
- **CI cost (with platform action):** once RepoSync enables BuildKit + remote cache, the same local gains apply in CI. The pilot provides the **evidence** to justify the change request.
- **Security posture:** smaller runtime image (no JDK/Maven) = reduced attack surface. Deterministic tests = fewer false-positive pipeline failures = security patches deployed without delay. In a border-security context, a delayed patch carries real risk.

---

## Technology Stack

| Area | Tooling |
|------|---------|
| Containers | Docker, BuildKit / `docker buildx` (feasibility TBC), multi-stage builds |
| CI/CD | **Drone CI** (Kubernetes runner, `.drone.star` via RepoSync — centrally managed) |
| Deploy | **Helm** (MMA service repo) → Kubernetes; environments: dev → SIT → bVal → prod |
| Source hosting | GitLab (`gitlab.digital.homeoffice.gov.uk`) |
| Registry / artifacts | `docker.digital.homeoffice.gov.uk`, ECR, **Artifactory** (Helm charts + Maven) |
| Integration testing | Testcontainers (Java, pilot); existing Docker Compose + DIND for comparison |
| Build / deps | Maven (`mvnw`), Maven cache mounts |
| Candidate test deps | Redis, Kafka, Schema Registry, LocalStack (IAM) |
| Tracing | OpenTelemetry + Jaeger |
| Security | Trivy, Sonar, SBOM (Syft), Drone secrets — see [SECURITY.md](../SECURITY.md) |


---

> Source: `docs/PIPELINE-CONTEXT.md`

# Pipeline Context

The Drone/RepoSync and CI/deploy boundary that shapes the pilot. [← Back to overview](../README.md)

---

## Drone / RepoSync Constraint

The FDP adaptor repositories use a **centrally managed `.drone.star`** pipeline (Starlark), deployed via **RepoSync**. Local changes to the pipeline config are not durable because RepoSync owns the source of truth.

This means:

- **Pipeline-level changes** (stage ordering, DIND image, BuildKit enabling, Testcontainers environment) **cannot be made locally** — they require RepoSync / platform / ETO coordination.
- **Repository-level changes** (Dockerfile, `.dockerignore`, Maven profiles, test code) **can be made locally** within the pilot scope.
- The CI pipeline uses a **Kubernetes runner** with a **Docker-in-Docker service** (`DOCKER_HOST=tcp://docker:2375`).
- Docker Compose is the current **CI integration test orchestration** method (Kafka, Redis, Schema Registry, aggregators, command adaptor all started via compose).
- `TESTCONTAINERS_RYUK_DISABLED=true` already appears in one Maven step (ECR pipeline) — indicating prior Testcontainers exploration and a known Drone/Ryuk compatibility constraint.
- Pull request events appear to trigger only a minimal/blank pipeline — to be confirmed in Story 1.

**Consequence for the pilot:** Story 1 (Pipeline Assessment) must be completed first to establish what is locally feasible vs what requires central discussion. This is not a reason to avoid RepoSync-owned improvements; it means reusable changes should be shaped as ACP/RepoSync-ready recommendations or MRs.

---

## Pipeline Landscape

There are **two separate pipelines** in the FDP ecosystem — the pilot targets only the first:

```text
┌─────────────────────────────────────────────────────────────┐
│ CI PIPELINE (per-adaptor repo, .drone.star via RepoSync)    │  ← PILOT SCOPE
│                                                             │
│ clone → Docker/DIND → Maven build + test → Docker Compose   │
│ (Kafka, Redis, Schema Registry, aggregators, cmd-adaptor)   │
│ → Integration tests → Trivy scan → Sonar scan              │
└─────────────────────────────────────────────────────────────┘
        │ produces: Docker image + Helm chart (Artifactory)
        ▼
┌─────────────────────────────────────────────────────────────┐
│ DEPLOY PIPELINE (MMA service repo, separate Drone pipeline) │  ← NOT IN PILOT SCOPE
│                                                             │
│ Helm package → lint → template → mass diff → upload         │
│ → deploy to Kubernetes (dev → SIT → bVal → prod)           │
│ Release day: Thursday. QAT approves at SIT gate.            │
│ Rollback: manual only (helm rollback). No automation.       │
└─────────────────────────────────────────────────────────────┘
```

The pilot optimises the **CI pipeline** (build time, test setup, Docker image). Deploy pipeline improvements (rollback automation, release flow) are captured in [Future considerations](stories/FUTURE-CONSIDERATIONS.md).


---

> Source: `docs/SCOPE-AND-GUARDRAILS.md`

# Scope and Guardrails

What the pilot may change, what needs central coordination, and what is deliberately deferred. [← Back to overview](../README.md)

---

## Pilot Approach

A small, measurable pilot on **one** representative repository:

1. **Compare at least two candidate pipelines/repos** so portability is considered before selecting the pilot.
2. **Baseline** the current state so every change is provable.
3. **Optimise the Docker build** (layering, `.dockerignore`, cache mounts) and measure the delta.
4. **Pilot Testcontainers** for one integration dependency for better isolation/determinism.
5. **Rationalise Docker Compose** — keep it for local debugging, reduce its role in CI.
6. **Consolidate findings** and classify each pattern as CST-local, ACP/RepoSync-owned, or wider ETO/Enabling.

---

## Immediate Pilot Scope

The initial pilot should remain small and measurable.

**CST-local (can do in the repo without RepoSync changes):**

- Baseline measurement (pipeline timing from Drone UI, Docker build locally)
- Pilot repository/service selection after comparing at least two candidate pipelines/repos
- Dockerfile / build context review
- `.dockerignore` validation
- Dockerfile layering experiment (local build)
- Local Testcontainers prototype (runs on developer machine)
- Docker Compose service mapping and classification
- CST-local vs RepoSync/platform vs wider ETO ownership assessment

**Requires central/platform coordination (RepoSync / platform / ETO):**

- Drone pipeline step changes (`.drone.star`)
- CI-level Testcontainers execution (DIND env vars, Ryuk config)
- BuildKit / `docker buildx` enabling in Drone DIND
- Remote cache infrastructure (registry namespace, permissions)
- DIND image changes
- Shared base image adoption across adaptors

---

## Deferred Work

This is the negative scope / guardrail list. These items may be valid later, but they should not start before the pilot has evidence and ownership.

- Treating local `.drone.star` edits as durable pilot changes — reusable pipeline changes must go through ACP/RepoSync
- Organisation-wide rollout
- Replacing all Docker Compose usage
- Building shared base images without platform ownership
- Enabling BuildKit remote cache without Drone/DIND/platform review
- Implementing ephemeral environments
- Creating a shared Testcontainers library before the first pilot proves value
- Opening all candidate tasks as delivery tickets before ownership is agreed
- Changing anything on `main` branch of the pilot repo without baseline captured first
- Assuming CI-level Testcontainers works without completing Story 1 (pipeline feasibility)

---

## Assumptions

- The first pilot will use **one** selected repository/service.
- Baseline metrics will be captured **before** any implementation changes.
- Any platform-impacting work will be reviewed with relevant ACP/ETO stakeholders.
- Docker Compose will not be removed without understanding current CI and local debugging usage.
- Testcontainers will be piloted with one dependency first before wider migration is considered.
- Projected benefits will not be treated as guaranteed until measured.
- The pilot is part-time work (~4 weeks), not a full-time dedicated programme.

---

## Decision Points

Before creating detailed implementation tickets, the following decisions should be agreed:

1. Which repository/service should be used as the pilot?
2. Which metrics should be captured as the baseline and how (data source, N runs)?
3. Which Dockerfile/build optimisation should be tested first?
4. Which integration dependency should be used for the first Testcontainers pilot?
5. Which items can stay on the CST board?
6. Which items need RepoSync/platform or wider ETO visibility or ownership?
7. What success criteria must be met before considering wider adoption?

---

## Open Questions

- Which FDP repository/service is the best pilot candidate?
- Do we have reliable access to current pipeline timing data (Drone UI / API)?
- Which integration dependency is safest for the first Testcontainers pilot (Redis? Kafka?)?
- Does the Drone DIND service support Docker access from Maven test step (`DOCKER_HOST`)?
- Can Testcontainers run in the main CI `mvn clean install` step, or does it need a separate Drone step?
- Is `TESTCONTAINERS_RYUK_DISABLED=true` sufficient, or does Ryuk need an alternative cleanup strategy?
- Does the current DIND image support BuildKit / `docker buildx`?
- Who owns the RepoSync source for `.drone.star`? What is the change request process?
- Is there an existing platform-owned base image strategy?
- What is the approval process for DIND / privileged runner changes?
- Does the MR (pull_request) event really run only a blank pipeline? If so, how do developers get CI feedback on MRs?
- Is there potential duplicate Maven work between the `mvn clean install` step and the `integration-tests` compose container?

---

## Recommended First Local Changes

The first local changes should be small and low-risk:

1. Add or validate `.dockerignore` (T3.2 — minimal effort, immediate context-size reduction)
2. Capture current Docker build timing with `scripts/measure-baseline.sh`
3. Review Dockerfile layer ordering (T3.1)
4. Propose one Dockerfile cache optimisation (T3.3)
5. Measure local build before/after (T3.4)
6. Identify one candidate integration test for Testcontainers (T4.1)

Avoid combining Dockerfile optimisation and Testcontainers changes in the same MR — keep changes attributable.


---

> Source: `PROJECT-PLAN.md`

# Project Plan

Operational plan for the Container & CI/CD Optimisation pilot: timeline, risk register, branching/CI flow, and test strategy. [← Back to overview](README.md)

> Dates are **relative** (Week 1 = pilot kick-off week). Fill in calendar dates once the team agrees a start date.

---

## Timeline (indicative)

Sized from story point estimates (`1`, `2`, `3`, `5`; `1 SP` is roughly 1 day). Stories 3 and 4 run in parallel after the Story 1/2 gates.

| Week | Focus | Stories / tasks | Exit criteria |
|------|-------|-----------------|---------------|
| **Week 1** | Pipeline assessment + baseline | Story 1 (T1.1–T1.5), Story 2 (T2.1–T2.4) | Pipeline boundaries known; pilot repo agreed; baseline metrics captured |
| **Week 2** | Build + Testcontainers (parallel) | Story 3 (T3.1–T3.3), Story 4 (T4.1–T4.2) | `.dockerignore` + one layering change applied; Testcontainers setup running locally |
| **Week 3** | Measure + compare | Story 3 (T3.4), Story 4 (T4.3–T4.4), Story 5 (T5.1) | Build evidence; scoped Testcontainers/Compose comparison; Compose scope validated |
| **Week 4** | Rationalise + decide | Story 5 (T5.2), Story 6 (T6.1–T6.2) | Compose role recommended; outcomes and ownership classified; adoption decisions and share-out recorded |

> This is a ~4-week part-time pilot, not a full-time programme. Adjust per team capacity.

### Milestones
- **M1 — Pipeline assessed + baseline agreed** (end of Week 1): boundaries known, scope locked, numbers captured.
- **M2 — Optimisations applied** (end of Week 2): build + Testcontainers changes exist.
- **M3 — Evidence in** (end of Week 3): before/after data collected.
- **M4 — Pilot reported** (end of Week 4): findings + ownership shared with stakeholders.

---

## Risk register

Probability (P) and Impact (I): Low / Med / High.

| # | Risk / assumption | P | I | Mitigation | Fallback plan |
|---|-------------------|---|---|------------|---------------|
| R1 | Pilot repo selection slips or stakeholders disagree | Med | High | Time-box selection to Week 1; agree criteria up front (T2.1) | Pick the repo with the slowest known pipeline by default |
| R2 | Drone pipeline history lacks reliable timing data | Med | Med | Use last N pipeline runs from Drone UI; document method (T2.2) | Fall back to repeatable local measurements |
| R3 | Drone Kubernetes runner / DIND limits Testcontainers | Med | High | Assess CI suitability early in T1.4; isolate as a separate finding | **Keep docker-compose in CI**; run Testcontainers locally only |
| R4 | Reducing Compose services breaks a hidden local workflow | Low | Med | Change CI usage only; keep Compose for local debugging (Story 5) | Revert Compose change; document the dependency found |
| R5 | Optimisations turn out to be RepoSync/platform-owned or wider ETO-owned, not CST-local | Med | Med | Classify ownership early (Story 6) before wider changes | Hand item to the RepoSync/platform or wider ETO board with findings attached |
| R6 | Build cache change produces inconsistent/incorrect images | Low | High | Verify image runs after each change (see test strategy) | Disable cache mount; rebuild from clean context |
| A1 | Assumption: one representative repo is enough to validate the ideas | — | Med | State scope limits in the final summary | Recommend a second repo before any rollout |
| R7 | Pipeline changes are RepoSync-owned, so local `.drone.star` edits are not durable | Med | High | Complete Story 1 to identify boundaries; shape reusable changes as ACP/RepoSync-ready recommendations or MRs | Keep pipeline changes out of local-only scope; route accepted patterns through normal ACP/RepoSync process |
| R8 | Deploy pipeline (Helm/service repo) confused with CI pipeline | Low | Med | Clearly document the boundary (see Pipeline Landscape above); pilot scope is CI only | If deploy improvements surface, route them to FUTURE-CONSIDERATIONS, not the pilot backlog |

---

## Branching & CI flow

### Pipeline landscape

The FDP ecosystem has two separate pipelines:

1. **CI pipeline** (per-adaptor repo, `.drone.star` via RepoSync) — build, test, scan, produce image + Helm chart. **This is what the pilot optimises.**
2. **Deploy pipeline** (MMA service repo, separate Drone pipeline) — Helm package, lint, template, diff, upload, deploy to Kubernetes. **Not in pilot scope.**

### Release flow

```text
feature/MMA-XXXXX → develop → release/X.Y.Z → tag (vX.Y.Z) → tag pipeline → Artifactory
                                                                                    │
                                              MMA service repo deploy pipeline ◄────┘
                                              dev → SIT (QAT approval) → bVal → prod
```

- **Feature branch:** created from the agreed delivery ticket (Jira if that is the tracker; otherwise GitLab issue), developed, MR into `develop`
- **Release branch:** cut from `develop` when sprint is ready (e.g. `release/5.9.0`)
- **Tag:** developer creates tag on release branch → triggers tag pipeline (Maven build + test + Trivy + Sonar + Helm package + Artifactory upload)
- **Deploy:** service repo picks up the new chart version and deploys via Helm to Kubernetes
- **Environments:** dev → SIT (QAT must approve) → bVal → prod
- **Release day:** Thursday
- **Rollback:** no automation — manual `helm rollback` only

### Pilot branching

How a pilot task moves from work-in-progress to merged:

```text
feature branch  ──MR──>  develop  ──(stabilise)──>  main
   │                       │                          │
   └ pilot work            └ pilot integration        └ protected; release-ready
```

- **Branch naming:** `pilot/<story>-<short-desc>` (e.g. `pilot/s3-dockerfile-layering`).
- **Merge request (MR) required** into `develop`; no direct pushes to `develop` or `main`.
- **On MR open / update**, Drone CI runs the pipeline (if configured for MR events — confirm in T1.1).
- **Merge to `develop`** when: pipeline green, acceptance criteria met, [Definition of Done](docs/stories/DEFINITION-OF-DONE.md) satisfied, one review approved.
- **Promote `develop` → `main`** at a milestone, once the pilot increment is stable.
- **On merge**, the [status board](docs/stories/STATUS-BOARD.md) entry for the task moves to `Done` and the result is noted.

---

## Test & verification strategy

Testcontainers (Story 4) covers integration tests. Everything else still needs verification — here's how each change type is confirmed.

| Change | How it's verified |
|--------|-------------------|
| **Dockerfile / layering (T3.3)** | Image **builds** cleanly; container **starts**; app smoke-checks (health endpoint / startup logs); image runs the same workload as before |
| **`.dockerignore` (T3.2)** | Build context size compared before/after; image still contains required files; build succeeds |
| **Build cache (T3.3)** | Two consecutive builds: second reuses cache; a clean build (no cache) still succeeds (guards R6) |
| **Testcontainers (T4.2)** | Integration test passes locally; dependency reachable; runs in Drone CI or documented why not |
| **Compose change candidate (T5.2)** | Before adoption, the equivalent integration scope passes with the proposed set and the local debugging workflow is preserved |
| **Metrics (T2.x, T3.4, T4.3)** | Captured via the [metrics template](docs/stories/metrics-template.md); method documented so it's repeatable |

**Verification principles**
- Every build/config change is proven by an actual build + run, not by inspection alone.
- Before/after numbers come from the same method on the same repo.
- A clean (cache-less) build must always still work, so cache is an optimisation, never a dependency.

### Open test strategy decisions (resolve in T4.2 / T4.1)

| Question | Decision | Resolved in |
|----------|----------|-------------|
| Minimum Java version for Testcontainers | Java 11 minimum; Java 17+ recommended. Confirm against `pom.xml` in T4.1. | T4.1 |
| CI Docker execution mode | Drone Kubernetes runner + DIND — assess in T1.4. See [ADR-0005](docs/adr/0005-ci-runner-docker-mode.md). | T1.4 |
| Which CI step runs integration tests | Drone `integration-tests` step via docker-compose. Testcontainers alternative assessed in T1.4/T4.2. | T1.4, T4.2 |
| Testcontainers reuse policy | Local: reuse enabled for faster feedback. CI: reuse disabled — clean, isolated env per run. See [ADR-0002](docs/adr/0002-testcontainers-for-integration-tests.md). | Decided (ADR-0002) |


---

> Source: `SECURITY.md`

# Security Plan

Concrete security practices for the pilot. Turns the high-level notes in [tech-notes](docs/stories/tech-notes.md#security--compliance) into an actionable plan. [← Back to overview](README.md)

> **Scope:** practices the pilot will apply or assess. Items needing org-wide infrastructure (shared scanners, signing infra) are flagged as **ACP/ETO** and routed via Story 6.

---

## 1. Secret management

| Concern | Approach |
|---------|----------|
| CI secrets (registry creds, tokens) | Store in **Drone secrets** (per-repo or organisation-level) — never in the repo. Drone encrypts secrets and injects them as environment variables at runtime. |
| Build-time secrets (e.g. Maven `settings.xml`) | Pass via **BuildKit secret mounts**, not `ARG`/`ENV` or `COPY` (see pattern below) |
| App runtime secrets | Injected at deploy time via the platform secret manager (e.g. **HashiCorp Vault** / cloud secret manager) — out of pilot scope to implement, in scope to document |
| Preventing leaks | `.dockerignore` excludes `.env`, key files; secret scanning in CI (see §2) |

**Secret-safe build pattern (BuildKit):**
```dockerfile
# secret is mounted only for this RUN, never baked into a layer
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    ./mvnw -B package
```
```bash
docker buildx build --secret id=maven_settings,src=$HOME/.m2/settings.xml .
```

**Rules**
- No secrets in image layers, build args, logs, or the repository.
- No real credentials in examples or fixtures — use placeholders.
- Rotate any credential that is suspected to have leaked.

---

## 2. Scanning policy

| What | Tool (candidate) | When | Pilot mode | Target gate |
|------|------------------|------|------------|-------------|
| Image vulnerabilities | **Trivy** or **Snyk** | Every pilot build | Report-only / warn | Fail on **Critical**; review High |
| Dependency vulnerabilities | Trivy / Snyk / `mvn` audit | On MR + weekly schedule | Report-only / warn | Fail on Critical |
| Secret scanning | **gitleaks** / **trufflehog** | On MR | Report-only until tool is chosen | Fail on any verified secret |
| SBOM generation | **Syft** (SPDX/CycloneDX) | On image build | Artefact attached to build | Required artefact |
| Base image freshness | scheduled rebuild + scan | Weekly | Report-only / warn | Flag outdated/EOL base images |

> Tool **choice** is CST-local for the pilot. A shared, org-wide scanning **standard / gate** is **ACP/ETO** — classify in Story 6.
> The template CI starts in report-only mode to avoid blocking before baseline data exists. Promote the target gates only after Story 2 captures the baseline and stakeholders agree the thresholds.

**Severity policy (target gate, after promotion)**
- **Critical:** block merge/build.
- **High:** review and decide (waiver with expiry if accepted).
- **Medium/Low:** track, don't block.

---

## 3. Policy as code

Container/image rules to enforce automatically rather than by review.

| Policy | Rule | How |
|--------|------|-----|
| No `root` runtime | Container must run as non-root `USER` | Dockerfile lint (**hadolint**) + image policy check |
| No unpinned base/job images | Base images and CI job images pinned to a version (digest for critical) | hadolint rule + CI grep/lint |
| No secrets in image | Built image contains no secret material | secret scan of built image |
| Healthcheck present | Long-running images define a healthcheck | hadolint / policy check |
| Approved base images | Use sanctioned base images only | policy check against allowlist *(ACP/ETO)* |

**Enforcement approach**
- Start with **hadolint** for Dockerfile rules (fast, local + CI).
- Express image/admission policies as code (**OPA/Conftest** or equivalent) where a gate is wanted.
- For the pilot, run policies in **warn** mode first; promote to **block** once stable.
- Release alias tags such as `:main` are allowed only when the immutable SHA tag is also pushed; avoid `:latest` in pilot templates.

---

## 4. Supply-chain hardening (assessed, mostly ACP/ETO)

- **Digest pinning** for critical base images (`FROM image@sha256:…`).
- **Image signing / provenance** (e.g. cosign) — assess feasibility, likely ACP/ETO.
- **Scheduled base-image rebuilds** to pick up patches.
- **Deprecated-image policy** so EOL bases are flagged and removed.

---

## Responsibilities

| Area | Owner |
|------|-------|
| Secret-safe builds, `.dockerignore`, hadolint | **CST (pilot)** |
| Scanning tool trial in pilot CI | **CST (pilot)** |
| Org-wide scanning gate, signing infra, base-image allowlist | **Platform / ETO** (route via Story 6) |

## Reporting a vulnerability

This is a planning/pilot repository with docs plus executable templates/config. If a security issue is found in pilot **code, config, or templates**, raise it privately with the pilot lead rather than opening a public issue.


---

> Source: `CONTRIBUTING.md`

# Contributing & How to Use This Backlog

How this backlog is organised and how to work with it. [← Back to overview](README.md)

---

## Structure

```text
README.md                  → entry point: purpose, key constraint, success targets, story map
CONTRIBUTING.md            → this guide
PROJECT-PLAN.md            → timeline, milestones, risk register, branching/CI flow, test strategy
SECURITY.md                → secret management, scanning policy, policy-as-code
docs/
  PROJECT-CONTEXT.md       → background, current state, business impact, technology stack
  PIPELINE-CONTEXT.md      → Drone/RepoSync constraint and CI vs deploy boundary
  SCOPE-AND-GUARDRAILS.md  → pilot scope, assumptions, open questions, deferred work
  adr/                     → Architecture Decision Records (why behind key choices)
    README.md              → ADR index + how to add one
    template.md            → ADR template
    NNNN-*.md              → individual decisions
  stories/
    INDEX.md               → one-page list of every story + task title
    STATUS-BOARD.md        → live task status and issue links
    DEFINITION-OF-DONE.md  → shared DoD + conventions (links to metrics template)
    metrics-template.md    → fillable before/after metrics sheet
    tech-notes.md          → technical reference (base images, BuildKit, security)
    story-<n>-<slug>/
      README.md            → story: goal, why, acceptance criteria, task table
      task-<n>-<slug>.md   → task: metadata, why, goal, scope, acceptance criteria
```

**Levels:** Epic (`README.md`) → Story (`docs/stories/story-*/README.md`) → Task (`task-*.md`).
**Decisions:** recorded as [ADRs](docs/adr/README.md). **Plan & risks:** [PROJECT-PLAN.md](PROJECT-PLAN.md). **Security:** [SECURITY.md](SECURITY.md).

## How to navigate

- Start at the [backlog index](docs/stories/INDEX.md) for the full outline.
- Drill into a story README for its goal and task list.
- Open a task file for the full detail (why · goal · scope · acceptance criteria).

## Reading a task

Every task file follows the same shape:

- **Metadata header** — `ID · Estimate · Priority · Owner · Status · Depends on`
- **Why** — the reason the task exists
- **Goal** — the outcome it must achieve
- **Scope** — what is covered
- **Acceptance criteria** — checklist that must pass

## Conventions

- **Estimate (story points):** use `1`, `2`, `3`, or `5`; `1 SP` is roughly 1 day of effort
- **Priority (MoSCoW):** `Must` · `Should` · `Could` · `Won't (this pilot)`
- **Status:** `Not started` · `In progress` · `Blocked` · `Done`
- **IDs:** stories `S1…S6`, tasks `T<story>.<n>` (e.g. `T3.3`)

> The [status board](docs/stories/STATUS-BOARD.md) is the only live progress tracker. Any status values in story/task files are planning snapshots and should not be maintained separately.

## Working a task

1. Set the task **Status** to `In progress` on the [status board](docs/stories/STATUS-BOARD.md).
2. Do the work within the task's **scope**.
3. Capture any measurement in the [metrics template](docs/stories/metrics-template.md).
4. Tick the task's **acceptance criteria**.
5. Confirm the shared [Definition of Done](docs/stories/DEFINITION-OF-DONE.md).
6. If the task settles a significant choice, record an [ADR](docs/adr/README.md).
7. Set **Status** to `Done` (or `Blocked`, with a note on what's blocking).

> The [status board](docs/stories/STATUS-BOARD.md) is the single source of truth for progress. Update it there, not in individual files.

## Raising tickets

Confirm the delivery tracker before ticket creation. If the pilot repo is GitLab-hosted, use GitLab issues for task links and GitLab MRs for source review. If Jira is the team's delivery tracker, link the Jira ticket in the `Issue` column and still use GitLab MRs for code changes. Route cross-team follow-ups to the CST, RepoSync/platform, or wider ETO board in Story 6.

Create tickets incrementally, following the order in the README. Don't raise everything at once — keep work controlled until pipeline boundaries, baseline data, and ownership are agreed.


---

> Source: `docs/glossary.md`

# Glossary

Key terms and abbreviations used across this project. [← Back to overview](../README.md)

---

| Term | Full name / meaning |
|------|---------------------|
| **FDP** | The product/team context this pilot runs in. "FDP" is the pilot scope — one representative repository owned by this team is selected in T2.1. |
| **CST** | The local development team that can own and directly validate repo-local changes (Dockerfiles, test code, Maven profiles, docs/config). Changes classified as "CST-local" in Story 6 do not require platform approval. |
| **ETO** | DSA ETO / Enabling / CIT — the wider engineering/platform org. Owns shared infrastructure: base images, CI/CD templates, registry, security gates. Prioritisation sits with Ezhil's role and depends on alignment with DSA Tech Strategy. |
| **ACP** | Application Container Platform — manages CI/CD tooling (Drone, runners, DIND images, RepoSync). Pipeline-level changes require ACP prioritisation. |
| **MR** | Merge Request — the GitLab equivalent of a Pull Request. All pilot changes require an MR into `develop`. |
| **MMA Helm repo** | The central "service repo" that deploys all FDP services to Kubernetes via Helm. It has its own Drone pipeline for Helm packaging, linting, templating, and deploying. Separate from the adaptor CI pipeline. |
| **Service repo** | See MMA Helm repo — the single repository responsible for deploying all service charts. |
| **Helm** | Kubernetes package manager. FDP services are packaged as Helm charts, uploaded to Artifactory, and deployed via the service repo pipeline. |
| **Artifactory** | JFrog Artifactory — hosts Maven artefacts and Helm charts. The tag pipeline uploads built charts here; the service repo pulls them for deploy. |
| **SIT** | System Integration Testing environment. QAT must approve a deployment at this stage before it progresses to bVal/prod. |
| **bVal** | Business Validation environment — sits between SIT and prod. Has more data than prod in some cases. |
| **QAT** | Quality Assurance Testing — the team that approves deployments at the SIT gate before promotion to higher environments. |
| **Feature flag** | Controlled via Helm values files. Even if a service is deployed, specific features can be enabled/disabled per environment without redeployment. |
| **Tools pod** | A Kubernetes pod in the deployment namespace with AWS secrets and other environment variables baked in. Used for running operational commands. |
| **PNR room** | Physical secure room required for accessing PNR (Passenger Name Record) data or prod environments. Screen sharing restrictions apply. |
| **CI** | Continuous Integration — automated build and test pipeline triggered on every commit/MR. Here: **Drone CI** (Kubernetes runner, `.drone.star` config). |
| **Drone** | The CI/CD system used by FDP. Runs pipelines on Kubernetes pods. Pipeline config is written in Starlark (`.drone.star`) and centrally managed via RepoSync. |
| **RepoSync** | A central mechanism that synchronises shared files (including `.drone.star`, docker-compose templates) into adaptor repositories. Local changes to synced files are **overwritten** on the next sync, so reusable pipeline changes should be made in the RepoSync source repo through the normal ACP/RepoSync process. |
| **Starlark** | A Python-like configuration language used by Drone for pipeline definitions (`.drone.star`). More expressive than YAML — supports functions, loops, conditionals. |
| **CD** | Continuous Delivery / Deployment — automated promotion of a verified build through environments. |
| **BuildKit** | Docker's next-generation build subsystem (`DOCKER_BUILDKIT=1` / `docker buildx`). Enables parallel build stages, cache mounts (`--mount=type=cache`), and secret mounts (`--mount=type=secret`). |
| **ADR** | Architecture Decision Record — a short document capturing a significant architectural choice, its context, and consequences. Stored in [`docs/adr/`](adr/README.md). |
| **DoD** | Definition of Done — the shared checklist a task must satisfy before it is considered complete. See [`docs/stories/DEFINITION-OF-DONE.md`](stories/DEFINITION-OF-DONE.md). |
| **MoSCoW** | Prioritisation framework: **M**ust / **S**hould / **C**ould / **W**on't. Used in the status board Priority column. |
| **SBOM** | Software Bill of Materials — a machine-readable inventory of all components in an image/artefact. Generated by Syft. |
| **DinD** | Docker-in-Docker — a Docker daemon running inside a Drone pipeline pod (service named `docker`, accessible at `tcp://docker:2375`). Required for Docker builds, Compose, and Testcontainers in CI. See ADR-0005. |
| **Remote cache** | A BuildKit cache stored in a container registry so that CI runners (which have no persistent local disk) can reuse layers across jobs. See [tech-notes.md](stories/tech-notes.md) and post-pilot (remote cache). |
| **Testcontainers** | A Java (and multi-language) library that starts real Docker containers as part of JUnit/TestNG tests, giving each test an isolated, deterministic dependency. See ADR-0002. |

---

## Environment clarification

| Question | Answer |
|----------|--------|
| Source code hosting | **GitLab** (`gitlab.digital.homeoffice.gov.uk`) — self-hosted |
| CI/CD system | **Drone** (Kubernetes runner, DIND service) |
| Pipeline config | `.drone.star` (Starlark) — centrally managed via **RepoSync** |
| Container registries | `docker.digital.homeoffice.gov.uk`, ECR, Artifactory |
| RepoSync source repo | `https://gitlab.digital.homeoffice.gov.uk/dacc-de/dde-adaptor-reposync` |
| Maven cache paths | Docker build stages use `/root/.m2/repository` because the image builds as root. Drone Maven steps can use `.m2/repository` via `MAVEN_OPTS=-Dmaven.repo.local=.m2/repository` if the central template enables a workspace cache. Local developer runs usually use `~/.m2/repository`. Confirm the selected pilot repo matches these paths in T3.3. |
| Java version for Testcontainers | Minimum **Java 11**; Java 17+ recommended. Confirm against pilot repo's `pom.xml` in T4.1. |

[← Back to overview](../README.md)


---

> Source: `docs/stories/STATUS-BOARD.md`

# Status Board

Single source of truth for pilot task progress. [← Back to overview](../../README.md)

> **Note:** The backlog below is a **candidate structure only**. Individual tickets should not be created until priority, ownership and target board are agreed. The purpose is to support review and prioritisation — not to imply that every task will be implemented immediately.

Update the **Status** column as work moves; status-looking metadata in story/task files is only an initial planning snapshot.
Estimates use story points: `1`, `2`, `3`, or `5`; `1 SP` is roughly 1 day of effort. Priority: MoSCoW.

> **Tickets:** Confirm the delivery tracker before ticket creation (record the final choice in T2.1 once the pilot repo is selected). For GitLab-hosted repos, use GitLab issues for task links and GitLab MRs for source review; if Jira is the team's delivery tracker, link Jira tickets in the `Issue` column. Issue creation order: Epic → S1 → T1.1 → T1.2 → S2 → T2.1 (see [CONTRIBUTING.md](../../CONTRIBUTING.md)).

| ID | Item | SP | Priority | Status | Owner | Issue |
|----|------|:---:|:--------:|--------|-------|-------|
| **S1** | **Pipeline Assessment (Drone/RepoSync)** | — | Must | Not started | _TBD_ | — |
| T1.1 | Review .drone.star pipeline structure | 2 | Must | Not started | _TBD_ | — |
| T1.2 | Identify local vs RepoSync boundaries | 1 | Must | Not started | _TBD_ | — |
| T1.3 | Map CI steps, DIND and Compose usage | 2 | Must | Not started | _TBD_ | — |
| T1.4 | Assess Testcontainers feasibility in Drone | 2 | Must | Not started | _TBD_ | — |
| T1.5 | Assess BuildKit/cache feasibility | 1 | Should | Not started | _TBD_ | — |
| **S2** | **Baseline & Pilot Scope** | — | Must | Not started | _TBD_ | — |
| T2.1 | Compare candidate pipelines and select pilot repo | 1 | Must | Not started | _TBD_ | — |
| T2.2 | Capture CI/CD pipeline baseline | 2 | Must | Not started | _TBD_ | — |
| T2.3 | Capture Docker build & image-size baseline | 1 | Must | Not started | _TBD_ | — |
| T2.4 | Capture integration-test baseline | 2 | Must | Not started | _TBD_ | — |
| **S3** | **Docker Build Optimisation** | — | Must | Not started | _TBD_ | — |
| T3.1 | Review current Dockerfile & build context | 2 | Must | Not started | _TBD_ | — |
| T3.2 | Add or validate .dockerignore | 1 | Must | Not started | _TBD_ | — |
| T3.3 | Apply Dockerfile layering / cache improvement | 2 | Must | Not started | _TBD_ | — |
| T3.4 | Measure local & CI build impact | 2 | Should | Not started | _TBD_ | — |
| **S4** | **Testcontainers Pilot** | — | Must | Not started | _TBD_ | — |
| T4.1 | Select candidate dependency/test | 1 | Must | Not started | _TBD_ | — |
| T4.2 | Implement Testcontainers setup | 3 | Must | Not started | _TBD_ | — |
| T4.3 | Compare with docker-compose flow | 2 | Should | Not started | _TBD_ | — |
| T4.4 | Document findings & constraints | 1 | Should | Not started | _TBD_ | — |
| **S5** | **Docker Compose Rationalisation** | — | Should | Done — analysis only | _TBD_ | — |
| T5.1 | Validate current Compose scope | 3 | Must | Done — evidence prepared | _TBD_ | — |
| T5.2 | Decide the target Compose role | 2 | Must | Done — target-role recommendation prepared; implementation and adoption not approved | _TBD_ | — |
| **S6** | **Pilot Outcome, Ownership and Adoption** | — | Must | In progress | _TBD_ | — |
| T6.1 | Classify pilot outcomes and ownership routes | 4 | Must | Done — evidence prepared | _TBD_ | — |
| T6.2 | Decide adoption route and publish pilot outcome | 2 | Must | Not completed — materials prepared | _TBD_ | — |

---

## Ticket-Creation Order

Create incrementally — not all at once:

1. Epic
2. Story 1 → T1.1 (pipeline structure) → T1.2 (local vs RepoSync boundaries)
3. Story 2 → T2.1 (select repo) → T2.2 (pipeline baseline)

Open the rest once pipeline boundaries are understood and the baseline is underway.


---

> Source: `docs/stories/INDEX.md`

# Backlog Index

A single-page outline of the whole backlog: every story and its task titles with initial planning info.
For full detail, follow the links. [← Back to overview](../../README.md)

**Related:** [Status board](STATUS-BOARD.md) · [Project plan](../../PROJECT-PLAN.md) · [Security](../../SECURITY.md) · [ADRs](../adr/README.md) · [Metrics](metrics-template.md) · [Definition of Done](DEFINITION-OF-DONE.md) · [Future considerations](FUTURE-CONSIDERATIONS.md)

> Live progress is tracked only in the [status board](STATUS-BOARD.md); status values here are initial backlog snapshots.

---

### [Story 1 — Pipeline Assessment (Drone/RepoSync)](story-1-pipeline-assessment/README.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T1.1 | [Review .drone.star pipeline structure](story-1-pipeline-assessment/task-1-review-drone-star.md) | 2 | _TBD_ | Not started | Week 1 |
| T1.2 | [Identify local vs RepoSync boundaries](story-1-pipeline-assessment/task-2-local-vs-central.md) | 1 | _TBD_ | Not started | Week 1 |
| T1.3 | [Map CI steps, DIND and Compose usage](story-1-pipeline-assessment/task-3-map-ci-steps.md) | 2 | _TBD_ | Not started | Week 1 |
| T1.4 | [Assess Testcontainers feasibility in Drone](story-1-pipeline-assessment/task-4-testcontainers-feasibility.md) | 2 | _TBD_ | Not started | Week 1 |
| T1.5 | [Assess BuildKit/cache feasibility](story-1-pipeline-assessment/task-5-buildkit-feasibility.md) | 1 | _TBD_ | Not started | Week 1 |

### [Story 2 — Baseline & Pilot Scope](story-2-baseline/README.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T2.1 | [Compare candidate pipelines and select pilot repo](story-2-baseline/task-1-select-repo.md) | 1 | _TBD_ | Not started | Week 1 |
| T2.2 | [Capture CI/CD pipeline baseline](story-2-baseline/task-2-pipeline-baseline.md) | 2 | _TBD_ | Not started | Week 1 |
| T2.3 | [Capture Docker build & image-size baseline](story-2-baseline/task-3-build-image-baseline.md) | 1 | _TBD_ | Not started | Week 1 |
| T2.4 | [Capture integration-test baseline](story-2-baseline/task-4-integration-test-baseline.md) | 2 | _TBD_ | Not started | Week 1 |

### [Story 3 — Docker Build Optimisation](story-3-build/README.md) · [ADR-0004](../adr/0004-buildkit-cache-and-layering.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T3.1 | [Review current Dockerfile & build context](story-3-build/task-1-review-dockerfile.md) | 2 | _TBD_ | Not started | Week 2 |
| T3.2 | [Add or validate .dockerignore](story-3-build/task-2-dockerignore.md) | 1 | _TBD_ | Not started | Week 2 |
| T3.3 | [Apply Dockerfile layering / cache improvement](story-3-build/task-3-layering-improvement.md) | 2 | _TBD_ | Not started | Week 2 |
| T3.4 | [Measure local & CI build impact](story-3-build/task-4-measure-impact.md) | 2 | _TBD_ | Not started | Week 3 |

### [Story 4 — Testcontainers Pilot](story-4-testcontainers/README.md) · [ADR-0002](../adr/0002-testcontainers-for-integration-tests.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T4.1 | [Select candidate dependency/test](story-4-testcontainers/task-1-select-candidate.md) | 1 | _TBD_ | Not started | Week 2 |
| T4.2 | [Implement Testcontainers setup](story-4-testcontainers/task-2-implement-setup.md) | 3 | _TBD_ | Not started | Week 2 |
| T4.3 | [Compare with docker-compose flow](story-4-testcontainers/task-3-compare-flows.md) | 2 | _TBD_ | Not started | Week 3 |
| T4.4 | [Document findings & constraints](story-4-testcontainers/task-4-document-findings.md) | 1 | _TBD_ | Not started | Week 3 |

### [Story 5 — Docker Compose Rationalisation](story-5-compose/README.md) · [ADR-0003](../adr/0003-reduce-compose-in-ci.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T5.1 | [Validate current Compose scope](story-5-compose/task-1-validate-compose-scope.md) | 3 | _TBD_ | Done — evidence prepared | Week 3 |
| T5.2 | [Decide the target Compose role](story-5-compose/task-2-decide-compose-role.md) | 2 | _TBD_ | Done — target-role recommendation prepared; implementation and adoption not approved | Week 4 |

### [Story 6 — Pilot Outcome, Ownership and Adoption](story-6-findings/README.md)

| ID | Task | SP | Assignee | Status | Due |
|----|------|:---:|----------|--------|-----|
| T6.1 | [Classify pilot outcomes and ownership routes](story-6-findings/task-1-classify-outcomes.md) | 4 | _TBD_ | Done — evidence prepared | Week 4 |
| T6.2 | [Decide the adoption route and publish the pilot outcome](story-6-findings/task-2-decide-adoption.md) | 2 | _TBD_ | Not completed — materials prepared | Week 4 |

See the [Story 5 and Story 6 consolidation map](STORY-5-6-CONSOLIDATION.md) for legacy task traceability.


---

> Source: `docs/stories/STORY-5-6-CONSOLIDATION.md`

# Story 5 and Story 6 Consolidation

This inventory records the task structure reviewed before the Story 5 and Story 6 definitions were changed. It is the traceability record for the documentation-only consolidation; no technical implementation or stakeholder approval is implied.

## Existing-task inventory

| Story | Existing task | Purpose | Proposed consolidated goal |
|---|---|---|---|
| Story 5 | T5.1 — Map services started by docker-compose | Inventory services, dependencies, ports and known purpose | T5.1 — Validate current Compose scope |
| Story 5 | T5.2 — Classify services and usage | Separate CI, local-debug, optional and unclear use | T5.1 — Validate current Compose scope |
| Story 5 | T5.3 — Recommend reduced Compose role | Define what Compose should retain, what may move, and what must not change | T5.2 — Decide the target Compose role |
| Story 6 | T6.1 — Consolidate pilot findings | Bring Story 1–5 evidence into a reviewable summary | T6.1 — Classify pilot outcomes and ownership routes |
| Story 6 | T6.2 — Classify ownership and recommend target board | Separate CST-local, RepoSync/platform and wider ETO work | T6.1 — Classify pilot outcomes and ownership routes |
| Story 6 | T6.3 — Share findings with stakeholders | Publish the outcome, capture feedback and record next steps | T6.2 — Decide the adoption route and publish the pilot outcome |

## Old-to-new task mapping

| Old task | New consolidated task | Reason |
|---|---|---|
| T5.1 | T5.1 | Mapping and usage classification are one evidence-gathering outcome. |
| T5.2 | T5.1 | Classification has no independent stakeholder outcome without the service map. |
| T5.3 | T5.2 | The Compose-role recommendation remains a distinct decision outcome. |
| T6.1 | T6.1 | Evidence consolidation is an input to ownership classification, not a separate delivery. |
| T6.2 | T6.1 | Ownership and routing complete the same classification outcome. |
| T6.3 | T6.2 | Sharing, feedback and the adopt/candidate/stop decision form one pilot-close outcome. |

## Status interpretation

- Story 5 evidence has been prepared, so both consolidated documentation outcomes are complete. This does not mean a reduced Compose configuration was implemented or adopted.
- Story 6 classification material has been prepared. Stakeholder sharing, feedback, approval and adoption are not evidenced, so the final adoption/publishing outcome remains not completed.
- Existing Story 1–4 technical evidence remains unchanged. Documentation references to retired task identifiers point directly to the consolidated evidence.


---

> Source: `docs/stories/DEFINITION-OF-DONE.md`

# Definition of Done

Project-wide rules that apply to **every** task, in addition to each task's own acceptance criteria.
A task is only "Done" when all of the following are true.

### Every task
- [ ] Task-specific acceptance criteria are all met
- [ ] Output (findings, change, or decision) is written down in a shareable form
- [ ] Any assumptions or open questions are recorded
- [ ] Result is reviewed by at least one other person
- [ ] Task status is updated on the [status board](STATUS-BOARD.md)

### Tasks that produce a measurement
- [ ] Metric is captured using the shared [metrics template](metrics-template.md)
- [ ] Measurement method/source is noted so it can be repeated

### Tasks that change code or config
- [ ] Change is small, focused, and reviewable
- [ ] Compatibility / rollback risk is noted
- [ ] No secrets are added to the repository or build context

---

## Conventions

**Estimate (story points)** — use `1`, `2`, `3`, or `5`; `1 SP` is roughly 1 day of effort.
**Priority (MoSCoW)** — `Must` · `Should` · `Could` · `Won't (this pilot)`.
**Status** — `Not started` · `In progress` · `Blocked` · `Done`.

---

## Metrics template

Capture every before/after measurement in the dedicated, fillable **[metrics template](metrics-template.md)** — it includes the pilot context, targets, and source-of-method columns. Quick reference of the core fields:

| Metric | Target | Measurement method |
|--------|--------|-------------------|
| Docker build time (local) | ≥ 30% ↓ | `scripts/measure-baseline.sh` / `time docker build` |
| Final image size | ≥ 30% ↓ | `docker images` |
| Build context size | ≥ 50% ↓ | Docker build context log output |
| Integration test startup (local) | < 30 sec | Testcontainers startup logs |
| Developer feedback loop | ≤ 5 min (change → test green) | Local stopwatch / script timing |
| Pipeline duration (CI, post-platform) | ≥ 20% ↓ | Drone pipeline UI (after RepoSync change) |

> Targets are tracked in the [metrics template](metrics-template.md) and the [README success targets summary](../../README.md#success-targets-summary). Keep local pilot targets separate from post-platform CI targets.


---

> Source: `docs/stories/metrics-template.md`

# Metrics — Baseline & Results

Fill this in as the pilot progresses. Baseline values come from Story 2; "after" values from Stories 3–5.
Copy a fresh block per pilot iteration if you measure more than once. [← Back to overview](../../README.md)

> **How to measure:** record the method/source for every number so it can be repeated identically for the "after" run. Pipeline duration = rolling average over the last **N** runs (set N in T2.2).

---

## Pilot context

| Field | Value |
|-------|-------|
| Pilot repository | _TBD (T2.1)_ |
| Measurement date (baseline) | _YYYY-MM-DD_ |
| Measurement date (after) | _YYYY-MM-DD_ |
| N (runs averaged) | _TBD_ |
| Measured by | _TBD_ |

## Core metrics

| Metric | Baseline | After | Delta | Target | Source / method |
|--------|----------|-------|-------|--------|-----------------|
| Pipeline duration (avg) | | | | ≥ 20% ↓ (post-platform) | |
| Build stage duration | | | | — | |
| Unit test duration | | | | — | |
| Integration test duration | | | | — | |
| Docker build time (local) | | | | ≥ 30% ↓ | |
| Docker build time (CI) | | | | ≥ 20% ↓ (post-platform) | |
| Final image size | | | | ≥ 30% ↓ | |
| Integration test startup time | | | | < 30 sec | |
| Build context size | | | | ≥ 50% ↓ | |
| Failed-pipeline / flaky rate | | | | no regression | |
| Developer feedback loop (change → test green) | | | | ≤ 5 min | |
| Cache hit/miss rate (if available) | | | | — | |

## Notes & observations
- _Anything that affects interpretation: environment differences, one-off slow runs, cache warm/cold state, etc._

## Source data
- _Links to pipeline runs, build logs, or commands used._

## Source artefact mapping

Use this mapping when copying raw measurements into the core metrics table.

| Source | Produced by | Use for |
|--------|-------------|---------|
| `metrics-output/build-metrics.csv` | `scripts/measure-baseline.sh` in the selected pilot repo | Local warm/cold Docker build time and local image size |
| Drone build step logs | Drone CI pipeline UI (`docker build` step) | CI build duration and registry image size |
| Drone integration-test step logs | Drone CI pipeline UI (integration-tests step) | Integration-test startup + run duration |
| Drone pipeline UI / API | Pipeline listing and step timings | Rolling average pipeline duration and failed/flaky pipeline rate |

The metrics template remains the final human-readable summary; raw artefacts are supporting evidence and should be linked in the `Source / method` column.


---

> Source: `docs/stories/tech-notes.md`

# Technical Notes (Reference)

Supporting detail for the pilot. Not tasks — reference only.
Decisions behind these notes are recorded as [ADRs](../adr/README.md); security specifics live in [SECURITY.md](../../SECURITY.md).

### Base image strategy
`base-os → base-runtime → base-build → application`. Benefits: standard runtime, shared layers, central patching, easier compliance. Needs: versioned tags, ownership, deprecation policy, scheduled rebuilds, scanning. _(Likely ACP/ETO owned — classify in Story 6.)_

> **Note:** The initial pilot may identify where shared base images would help, but creating and maintaining organisation-level base images would require appropriate ACP/shared engineering ownership, lifecycle management and compatibility guarantees.

### BuildKit remote cache · [ADR-0004](../adr/0004-buildkit-cache-and-layering.md)
CI runners (Drone Kubernetes pods) are ephemeral — no persistent local cache between builds. Use a branch-aware registry cache:
```bash
BRANCH_SLUG="${DRONE_BRANCH:-local}"
COMMIT_SHA="${DRONE_COMMIT_SHA:-local}"

docker buildx build \
  --cache-from=type=registry,ref="$REGISTRY_IMAGE/cache:main" \
  --cache-from=type=registry,ref="$REGISTRY_IMAGE/cache:${BRANCH_SLUG}" \
  --cache-to=type=registry,ref="$REGISTRY_IMAGE/cache:${BRANCH_SLUG}",mode=max \
  --tag "$REGISTRY_IMAGE:${COMMIT_SHA}" --push .
```
Branch builds reuse `main` cache. Replace the variable names with the equivalent RepoSync/Starlark values if the central Drone template exposes different names. Keep a working fallback if cache is unavailable. _(Likely ACP/ETO owned.)_

> **Note:** BuildKit remote cache is included as a technical recommendation only. Actual implementation depends on CI runner capability, DIND image support, registry support, security constraints, RepoSync changes and ACP guidance.

### Testcontainers reuse policy · [ADR-0002](../adr/0002-testcontainers-for-integration-tests.md)
Local: reuse may be enabled for faster feedback. CI: reuse disabled — clean, deterministic env per run, no hidden shared state.

> **Note:** Testcontainers should first be validated locally and then assessed against Drone Kubernetes runner/DIND constraints before being proposed for CI usage. Reusable containers should not be assumed suitable for CI without completing Story 1 (pipeline assessment).

### Security & compliance
Summary only — the actionable plan (tools, gates, policies, secret handling) is in **[SECURITY.md](../../SECURITY.md)**.
Versioned base images, digest pinning for critical images, vulnerability scanning, SBOM, image signing if supported, scheduled rebuilds, secret-safe builds:
```dockerfile
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    mvn -B package
```

### Future platform opportunities (not in pilot)

See **[FUTURE-CONSIDERATIONS.md](FUTURE-CONSIDERATIONS.md)** for the full post-pilot readiness list (rollback, monitoring, artifact management, environment strategy, cost, compliance, troubleshooting runbook).

Platform-level capabilities that may become relevant if the pilot proves valuable: golden paths, reusable CI/CD templates, service starter templates, shared Testcontainers helper lib, contract testing, policy as code, dependency proxy/artifact cache, base image lifecycle management.

[← Back to overview](../../README.md)


---

> Source: `docs/stories/FUTURE-CONSIDERATIONS.md`

# Future Considerations

Items that are **out of scope for the pilot** but should be addressed if the patterns move to production. These are not tasks — they are a decision backlog for the next phase. [← Back to overview](../../README.md)

> **Disclaimer:** These opportunities are subject to ACP / DSA ETO prioritisation and alignment with DSA Tech Strategy, Core Cloud and Data Platform direction. They are not part of the immediate pilot.

> **When to revisit:** after Story 6 is complete and stakeholders decide to progress beyond the pilot.

---

## Post-pilot production readiness

> **How to action:** After T6.1 classifies ownership and T6.2 records owner decisions, create tickets on the agreed boards and replace the `Next action` cells below with issue links.

| # | Category | What's needed | Why it matters | Likely owner | Board | Next action |
|---|----------|---------------|----------------|--------------|-------|-------------|
| F1 | **Rollback strategy** | Define how to revert to the previous image/config when a new build causes issues. Options: re-deploy previous image tag, or automated canary with auto-rollback. **KT confirmed: no automated rollback exists today — only manual `helm rollback`.** | Without rollback, a bad deploy stays live until someone manually intervenes. | CST + platform | CST board + platform board | Raise after T6.2 owner decision — _link issue here_ |
| F2 | **Monitoring & alerting** | Track pipeline health metrics (queue time, failure rate, stage duration trend) and alert the team when thresholds breach. Drone API + external dashboards (Grafana). | Degradation goes unnoticed until someone manually checks. | CST (setup) / platform (infra) | CST board + platform board | Raise after T6.2 owner decision — _link issue here_ |
| F3 | **Artifact management** | Define where images are stored (`docker.digital.homeoffice.gov.uk`, ECR, Artifactory), retention/expiry policy (e.g. keep last N tags per branch, expire untagged after 30 days), and cleanup automation. | Unmanaged registries grow indefinitely; stale images consume storage and create confusion. | Platform | Platform board | Raise after T6.2 owner decision — _link issue here_ |
| F4 | **Environment strategy** | Clarify the promotion path: dev → staging → prod. Same pipeline with environment-specific variables? Manual promote gate? Drone promotion pipelines + protected branches. | Pilot assumes one environment; production needs clear separation and gates. | CST + platform | CST board + platform board | Raise after T6.2 owner decision — _link issue here_ |
| F5 | **Cost tracking** | Monitor CI runner minutes, registry storage, and image transfer costs. Set budget alerts. | Optimisation saves time but could shift cost elsewhere (e.g. larger cache storage). | Platform / finance | Platform board | Raise after T6.2 outcome — _link issue here_ |
| F6 | **Compliance & audit trail** | Ensure pipeline changes are traceable: who approved the MR, what ran, which image was deployed. GitLab audit events + merge request approval rules. | Enterprise/regulated environments require evidence of change control. | Platform / compliance | Platform board | Raise after T6.2 outcome — _link issue here_ |
| F7 | **Troubleshooting runbook** | Create a developer-facing guide: "pipeline failed — what do I do?" covering common failure modes, how to read logs, how to retry, and when to escalate. | Reduces mean-time-to-recovery and unblocks developers without senior intervention. | CST | CST board | Raise after T6.1 (findings consolidated) — _link issue here_ |

---

## Recommended priority (post-pilot)

If the pilot succeeds and the team moves towards production adoption:

1. **F1 Rollback** + **F7 Runbook** — safety net + developer self-service.
2. **F2 Monitoring** — visibility before you scale.
3. **F3 Artifact management** — prevent registry bloat early.
4. **F4 Environment strategy** — required for any real deployment.
5. **F6 Compliance** — depends on org requirements.
6. **F5 Cost tracking** — useful but non-blocking.

---

## Relationship to pilot

These items may surface naturally during the pilot:
- Story 3 (build optimisation) may reveal artifact/registry questions (→ F3).
- Story 4 (Testcontainers) may expose runner cost implications (→ F5).
- Story 6 (ownership) should explicitly list which of F1–F7 are CST-local vs RepoSync/platform vs wider ETO.

When writing the Story 6 consolidated findings, reference this list and recommend which items to pursue next.

---

## Post-pilot architecture decisions (candidates)

These are decisions that will need to be made if the pilot succeeds and the team moves to production. They are **not pilot scope** — they require ACP/ETO involvement. Record them as formal ADRs when the decision point arrives.

### Base image strategy

**Context:** Application Dockerfiles currently inherit from arbitrary upstream images (e.g. `eclipse-temurin:17-jre`). There is no shared base-image governance — each repo pins a different tag, CVE patching requires per-repo manual work, and runtime images often include build tooling.

**Proposed pattern:** A four-layer hierarchy: `base-os → base-runtime → base-build → application`. Application Dockerfiles use versioned, digest-pinned `base-runtime` and `base-build` images rather than direct upstream references.

**Why post-pilot:**
- Requires ACP/ETO to build, publish, scan, and maintain the base layers.
- Needs a rebuild cadence, deprecation policy, and notification process.
- CST can validate the pattern on one repo; ownership and infrastructure are ACP/ETO.

**Consequences if adopted:**
- (+) CVE patches propagate centrally; smaller images; simpler Dockerfiles; central compliance.
- (−) Teams lose direct control of runtime env; operational burden on ACP/ETO.

### BuildKit remote cache infrastructure

**Context:** Drone CI Kubernetes pods are ephemeral — no persistent local cache. Without a registry-backed remote cache, every CI build downloads dependencies and rebuilds layers from scratch. The pattern (`--cache-from`/`--cache-to` with registry refs) is documented in [tech-notes](tech-notes.md), but provisioning it requires:
- Registry storage and retention/eviction policy.
- Write permissions for CI jobs to a cache namespace.
- Runner BuildKit support (`docker buildx`).
- Security: cache images excluded from production promotion paths.

**Why post-pilot:**
- CST cannot provision the cache namespace without ACP/ETO approval.
- The pipeline duration target (≥20% reduction) may not be fully achievable without remote cache.
- CST can implement local cache mounts (partial win) in the pilot; remote cache is the next step.

**Consequences if adopted:**
- (+) Faster CI builds; dependency-heavy layers reused across branches; predictable build time.
- (−) Registry storage cost; cache invalidation must be controlled; fallback (no-cache build) must still work.

---

## Post-pilot technical opportunities

These are concrete next steps that build on the pilot's findings. They do not require ACP/ETO infrastructure — CST could pursue them independently.

### Selective test execution

**What:** Only run tests affected by the changed code. If only `payment/` changed, skip `notification/` tests entirely.

**How:** Maven module selection (`-pl`, `-am`) combined with `git diff` against the merge base. Drone pipeline `when` conditions or Starlark logic can skip steps when certain paths are untouched.

**Expected impact:** After build optimisation, this is the next-largest pipeline speed gain. On a multi-module project, it can cut integration test time by 50%+ for focused changes.

**When:** After Story 4 (Testcontainers) proves which tests are truly independent and isolated.

### Reusable Drone pipeline templates (via RepoSync)

**What:** Extract the pilot's optimised patterns (BuildKit build, Testcontainers env vars, Trivy scan) into reusable functions within the central `.drone.star` that other FDP adaptors can inherit via RepoSync.

**How:** The `.drone.star` already uses Starlark functions (`add_pipeline_step`, etc.). New functions like `add_testcontainers_step()` or `add_buildkit_build()` could encapsulate the optimised patterns. All repos receiving RepoSync would inherit them automatically.

**Expected impact:** Eliminates copy-paste drift; a fix in the template propagates to all adaptors on next sync. Reduces onboarding effort for new services.

**When:** After pilot findings are shared (Story 6) and the RepoSync team agrees to adopt the patterns. Requires central ownership.

### Contract testing (Pact)

**What:** Verify that services agree on their API contracts (request/response shapes) without deploying them together.

**How:** Pact (consumer-driven contract testing). Consumer tests generate contracts; provider verifies them in its own pipeline. No shared environment needed.

**Expected impact:** Catches integration mismatches before deploy, without heavy end-to-end tests. Reduces the need for full-stack staging environments.

**When:** When multiple FDP services interact and integration failures are a recurring problem.

### Ephemeral review environments

**What:** Spin up a short-lived deployment for each MR so reviewers can test the change in a real environment, then tear it down on merge.

**How:** GitLab Environments + Kubernetes namespace per MR (or Docker Compose on a shared VM). GitLab's `environment: on_stop` handles cleanup.

**Expected impact:** Faster feedback on behaviour changes; QA can verify without waiting for a shared staging deploy.

**When:** After the pipeline is fast and reliable (pilot goals achieved); requires ACP/ETO infrastructure for dynamic namespaces.

### Dependency proxy / artifact cache

**What:** Cache Maven dependencies and Docker base image pulls at the organisation level so every pipeline doesn't re-download them from the internet.

**How:** GitLab Dependency Proxy (built-in) for Docker images; Nexus/Artifactory or GitLab Package Registry for Maven.

**Expected impact:** Eliminates network variability from builds; protects against upstream outages (Docker Hub rate limits, Maven Central downtime).

**When:** When multiple teams hit download latency or rate-limit issues. ACP / DSA ETO owned.


### Release pipeline automation (Gareth's project)

**What:** An automation project is already in progress (led by Gareth) that aims to eliminate manual service chart management and automate release-branch → tag → deploy flows.

**Coordination:** The CI optimisation pilot and the release automation project are complementary but separate. The pilot improves **build + test speed**; the release automation improves **deploy + release management**. Findings from Story 6 should be shared with Gareth's project to avoid conflicting changes to the pipeline or Helm chart structure.

**When:** After Story 6 findings are shared — include Gareth as a stakeholder.


---

## Related Future Area: Deployment and Release Safety

A separate Cerberus deployment KT highlighted related release engineering areas. These are **outside the initial Container & CI/CD pilot scope** but are noted here as future opportunities.

**Observations from KT sessions:**

- Deployment is managed through the MMA service/Helm repository rather than individual service repositories.
- Feature activation depends on feature flags and environment-specific Helm value files.
- Deployment success does not necessarily mean feature activation or functional validation.
- Current validation focuses on deployment/pod readiness; functional validation sits with dev teams (Playwright/Cypress) and QAT.
- Automatic rollback is not built into deployment scripts — only manual `helm rollback` is available.
- Manual Helm rollback is possible but not documented as a standard operating procedure.
- Environment parity between pre-prod (bVal) and production should be understood and verified.
- Runbook repositories contain additional approved release steps.
- A release automation project (Gareth Andrews) is in progress to reduce manual service chart management.

**Why this is separate from the pilot:**

The CI/CD optimisation pilot focuses on **build and test** (faster builds, smaller images, deterministic tests). Deployment and release safety focuses on **deploy and operate** (rollback, feature activation, environment parity). They are complementary but have different owners, different timelines, and different risk profiles.

**Recommended next steps (post-pilot):**

- Document the manual rollback procedure as a runbook.
- Assess whether automated rollback (Helm hooks or pipeline step) should be added to the deploy pipeline.
- Coordinate with Gareth's release automation project to avoid conflicting changes.
- Clarify environment parity expectations between bVal and production.


---

> Source: `docs/stories/story-1-pipeline-assessment/README.md`

# Story 1 — Drone/RepoSync Pipeline Assessment

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** — (gate for the entire pilot) · **Parallel with:** —

## Goal
Understand the centrally managed Drone pipeline structure, establish what can be changed locally vs what requires RepoSync/platform coordination, and assess feasibility of Testcontainers and BuildKit in the current CI setup.

> **Scope boundary:** this story assesses the **CI pipeline** (per-adaptor repo, `.drone.star`). The **deploy pipeline** (MMA service repo → Helm → Kubernetes) is a separate concern and is documented as context but not optimised by this pilot.

## Why
The FDP adaptor pipelines are generated from a `.drone.star` file managed via RepoSync. Local pipeline changes are not durable, so the pilot must separate repo-local proof points from reusable changes that should be proposed through ACP/RepoSync.

## Acceptance criteria
- [ ] `.drone.star` pipeline structure is documented (steps, services, DIND usage)
- [ ] Local vs RepoSync-controlled change boundaries are clearly defined
- [ ] CI pipeline steps and Docker Compose usage are mapped
- [ ] Testcontainers feasibility in Drone is assessed (DIND access, Ryuk, DOCKER_HOST)
- [ ] BuildKit feasibility in current DIND setup is assessed
- [ ] Findings inform which later stories can proceed locally vs need central coordination

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T1.1 | [Review .drone.star pipeline structure](./task-1-review-drone-star.md) | 2 | Must | Not started |
| T1.2 | [Identify local vs RepoSync boundaries](./task-2-local-vs-central.md) | 1 | Must | Not started |
| T1.3 | [Map CI steps, DIND and Compose usage](./task-3-map-ci-steps.md) | 2 | Must | Not started |
| T1.4 | [Assess Testcontainers feasibility in Drone](./task-4-testcontainers-feasibility.md) | 2 | Must | Not started |
| T1.5 | [Assess BuildKit/cache feasibility](./task-5-buildkit-feasibility.md) | 1 | Should | Not started |


---

> Source: `docs/stories/story-1-pipeline-assessment/task-1-review-drone-star.md`

# T1.1 — Review .drone.star pipeline structure

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.1 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `drone`, `pipeline`, `reposync`, `assessment` |
| **Sprint** | Week 1 |
| **Depends on** | — |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The `.drone.star` file defines the entire CI pipeline. Understanding its structure is the prerequisite for every other pilot task — without it, we cannot know what is feasible locally.

## Goal
Document the current Drone pipeline structure: what runs, in what order, with what services.

## Scope
- Obtain and review the `.drone.star` source (from the RepoSync source repo)
- Document pipeline types (CI, ECR, Artifactory, etc.)
- Document steps within each pipeline (order, images, commands)
- Document services (DIND, Kafka, Redis, etc.)
- Note any existing Testcontainers-related configuration (e.g. `TESTCONTAINERS_RYUK_DISABLED`)
- Note how MR/pull_request events are handled

## Acceptance criteria
- [ ] Pipeline types and their purposes are documented
- [ ] Step ordering and dependencies are mapped
- [ ] DIND service configuration is documented
- [ ] Existing Testcontainers workarounds are noted
- [ ] MR pipeline behaviour is confirmed (blank or full)


---

> Source: `docs/stories/story-1-pipeline-assessment/task-2-local-vs-central.md`

# T1.2 — Identify local vs RepoSync-controlled change boundaries

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.2 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | 1 |
| **Priority** | Must |
| **Labels** | `drone`, `reposync`, `ownership`, `boundaries` |
| **Sprint** | Week 1 |
| **Depends on** | T1.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
RepoSync owns the central pipeline source of truth. The pilot must know exactly which files/changes are repo-local vs centrally controlled so local work stays realistic and reusable pipeline changes can be shaped for ACP/RepoSync.

## Goal
Produce a clear table of what the pilot team can change locally and what should become an ACP/RepoSync change request or recommendation.

## Scope
Classify:
- **Repo-local (safe to change):** Dockerfile, `.dockerignore`, Maven profiles, `pom.xml` dependencies, test source code, `application-*.yml`, docker-compose files used by Maven plugin.
- **RepoSync-controlled (requires coordination):** `.drone.star`, pipeline steps/ordering, DIND image, Drone secrets, service definitions.
- **Unclear / confirm:** docker-compose files invoked by the pipeline (is the compose file in the repo or generated?), Maven step environment variables.

## Acceptance criteria
- [ ] A clear "local vs central" classification exists
- [ ] The RepoSync source repo and change request process are identified
- [ ] The pilot team knows who to contact for central changes
- [ ] Any centrally controlled files that appear local are flagged


---

> Source: `docs/stories/story-1-pipeline-assessment/task-3-map-ci-steps.md`

# T1.3 — Map CI pipeline steps, DIND usage and Docker Compose commands

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.3 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `drone`, `docker-compose`, `dind`, `mapping` |
| **Sprint** | Week 1 |
| **Depends on** | T1.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The current CI pipeline is heavy — multiple docker-compose up/down cycles, wait containers, aggregator startups, and Maven builds. Understanding exactly what happens (and how long each step takes) is essential for identifying optimisation opportunities and measuring baseline.

## Goal
Produce a step-by-step map of the CI pipeline with timing data where available.

## Scope
For the CI pipeline (`ci_pipeline` in `.drone.star`), document each step:
- Step name and image
- Docker Compose commands executed (which services, detached or foreground)
- DIND interactions (docker build, docker push, compose up)
- Wait/health-check mechanisms
- Estimated or measured duration (from Drone UI)
- Potential duplicate work (e.g. `mvn clean install` + compose `integration-tests` container both running Maven)
- **CI pipeline vs deploy pipeline boundary** — clearly separate what the adaptor repo's CI does vs what the MMA service repo's deploy pipeline does (pilot scope is CI only)

Expected pipeline map:
```
RepoSync Version → Secrets → Wait for Docker → Extract Info →
Kafka & Redis (compose) → Aggregators (compose) → mvn clean install →
Command Adaptor (compose) → Pre-Integration Tests (compose) →
Integration Tests (compose) → Sonar → Trivy → Slack
```

## Acceptance criteria
- [ ] All CI pipeline steps are listed with their purpose
- [ ] Docker Compose commands and services per step are documented
- [ ] DIND usage points are identified
- [ ] Step durations are captured (from recent Drone runs if available)
- [ ] Potential duplicate work or unnecessary waits are flagged


---

> Source: `docs/stories/story-1-pipeline-assessment/task-4-testcontainers-feasibility.md`

# T1.4 — Assess Testcontainers feasibility in Drone/DIND

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.4 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `testcontainers`, `drone`, `dind`, `feasibility` |
| **Sprint** | Week 1 |
| **Depends on** | T1.1, T1.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Testcontainers requires Docker daemon access from within the test JVM. The current Drone pipeline provides DIND but it's unclear whether the Maven step can reach it. The ECR pipeline already sets `TESTCONTAINERS_RYUK_DISABLED=true` — this suggests prior exploration but also a known compatibility issue. Without confirming feasibility, Story 4 cannot determine whether Testcontainers will work in CI or only locally.

## Goal
Determine whether Testcontainers can run in the Drone CI pipeline and document any constraints.

## Scope
Investigate:
- Can the `mvn clean install` step access `DOCKER_HOST=tcp://docker:2375`? (currently it may not have this env var)
- Does `TESTCONTAINERS_RYUK_DISABLED=true` need to be set? What are the cleanup implications?
- Is `TESTCONTAINERS_CHECKS_DISABLE=true` needed to skip pre-flight checks?
- Can Testcontainers pull images through the DIND service (registry access, network)?
- Is there a Drone step timeout that would kill long-running container startups?
- Would Testcontainers conflict with the existing docker-compose usage in the same pipeline?

Known findings from `.drone.star`:
- ECR pipeline Maven step already has `TESTCONTAINERS_RYUK_DISABLED=true`
- DIND service is named `docker` and exposes port 2375
- The Maven step image is `quay.io/ukhomeofficedigital/ileap-java17-mvn`

## Decision outcomes (fill in after investigation)
- [ ] **CI feasible:** Testcontainers can run in CI with these env vars: _TBD_
- [ ] **CI feasible with constraints:** works but with limitations: _TBD_
- [ ] **Local only:** Testcontainers cannot run in CI; pilot stays local-only (fallback per ADR-0002)

## Acceptance criteria
- [ ] Docker daemon accessibility from Maven step is confirmed or denied
- [ ] Required environment variables for Testcontainers in Drone are documented
- [ ] Ryuk disabled implications are understood and documented
- [ ] A clear "feasible / feasible with constraints / local only" decision is made
- [ ] Finding informs Story 4 scope (CI or local-only)


---

> Source: `docs/stories/story-1-pipeline-assessment/task-5-buildkit-feasibility.md`

# T1.5 — Assess BuildKit/cache feasibility in current Drone/DIND setup

**Story:** [Story 1 — Pipeline Assessment](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.5 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | 1 |
| **Priority** | Should |
| **Labels** | `buildkit`, `drone`, `dind`, `cache`, `feasibility` |
| **Sprint** | Week 1 |
| **Depends on** | T1.1, T1.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
ADR-0004 proposes BuildKit multi-stage builds with cache mounts and potentially remote registry cache. But the current pipeline runs `docker build` inside DIND — it's unclear whether BuildKit is enabled, whether `docker buildx` is available, and whether registry cache writes are permitted.

## Goal
Determine what level of BuildKit optimisation is feasible in the current Drone/DIND setup.

## Scope
Investigate:
- Is `DOCKER_BUILDKIT=1` set or settable in the current DIND image?
- Does the DIND image include `docker buildx`?
- Can `--mount=type=cache` work inside the DIND daemon (ephemeral — lost between builds)?
- Can `--cache-from=type=registry` read from the internal registry?
- Can `--cache-to=type=registry` write to the internal registry (permissions, namespace)?
- Would any of these require a `.drone.star` change (RepoSync)?

Likely outcomes:
- **Multi-stage builds:** almost certainly work (standard Docker feature, no special DIND requirement)
- **Local cache mounts:** work per-build but lost between CI runs (still useful for local dev)
- **Remote registry cache:** likely requires ACP/ETO (registry namespace + permissions + .drone.star env vars)

## Acceptance criteria
- [ ] BuildKit availability in DIND is confirmed or denied
- [ ] `docker buildx` availability is confirmed or denied
- [ ] Cache mount behaviour in CI is documented (ephemeral vs persistent)
- [ ] Remote cache feasibility is assessed (registry permissions, .drone.star changes needed)
- [ ] Finding informs Story 3 scope (what can be done locally vs what needs platform)


---

> Source: `docs/stories/story-2-baseline/README.md`

# Story 2 — Baseline & Pilot Scope

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** Story 1 · **Parallel with:** —

## Goal
Compare at least two candidate pipelines/repos, select the pilot repository, and capture a trustworthy "before" state so every later change can be measured and proven.

## Why
Without a baseline there is no way to prove whether an optimisation actually helped. This story fixes the pilot scope and records the starting numbers before any change is made.

## Acceptance criteria
- [ ] At least two candidate pipelines/repos compared for portability
- [ ] Pilot repository selected with documented rationale
- [ ] Pipeline, build, image-size and integration-test baselines captured
- [ ] Measurement method recorded so it can be repeated for the "after" comparison
- [ ] Baseline reviewed and agreed with stakeholders

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T2.1 | [Compare candidate pipelines and select pilot repo](./task-1-select-repo.md) | 1 | Must | Not started |
| T2.2 | [Capture CI/CD pipeline baseline](./task-2-pipeline-baseline.md) | 2 | Must | Not started |
| T2.3 | [Capture Docker build & image-size baseline](./task-3-build-image-baseline.md) | 1 | Must | Not started |
| T2.4 | [Capture integration-test baseline](./task-4-integration-test-baseline.md) | 2 | Must | Not started |


---

> Source: `docs/stories/story-2-baseline/task-1-select-repo.md`

# T2.1 — Compare candidate pipelines and select pilot repo

**Story:** [Story 2 — Baseline & Pilot Scope](./README.md)

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
| GitLab environment | _TBD_ (self-hosted / GitLab.com — see [glossary](../../../docs/glossary.md)) |
| Issue / board tracker | _TBD_ (GitLab issues/MRs for GitLab-hosted repos unless Jira remains the delivery tracker) |
| Primary language / build tool | _TBD_ (e.g. Java 17 / Maven) |
| Selection rationale | _TBD_ |
| Portability notes | _TBD_ |
| Stakeholder who agreed scope | _TBD_ |

> After filling in this table: confirm the tracker, verify Docker/Drone access, and confirm the Maven cache paths in `Dockerfile` / CI steps.


---

> Source: `docs/stories/story-2-baseline/task-2-pipeline-baseline.md`

# T2.2 — Capture CI/CD pipeline baseline

**Story:** [Story 2 — Baseline & Pilot Scope](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T2.2 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 2 — Baseline & Pilot Scope |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `baseline`, `pipeline`, `metrics` |
| **Sprint** | Week 1 |
| **Depends on** | T2.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Pipeline duration is the headline metric stakeholders care about. Capturing it now, with a documented method, makes any later improvement provable rather than anecdotal.

## Goal
Record current CI/CD pipeline timings for the selected repository.

## Scope
- Capture average pipeline duration.
- Break down by stage: build, unit test, integration test.
- Capture failed-pipeline frequency if available.
- Note the data source and measurement method (e.g. last N runs from CI history).

## Acceptance criteria
- [ ] Baseline pipeline metrics are documented
- [ ] Data source / measurement method is recorded
- [ ] Metrics are in a form that can be re-measured later for before/after comparison


---

> Source: `docs/stories/story-2-baseline/task-3-build-image-baseline.md`

# T2.3 — Capture Docker build & image-size baseline

**Story:** [Story 2 — Baseline & Pilot Scope](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T2.3 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 2 — Baseline & Pilot Scope |
| **Estimate** | 1 |
| **Priority** | Must |
| **Labels** | `baseline`, `docker`, `image-size` |
| **Sprint** | Week 1 |
| **Depends on** | T2.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Build optimisation (Story 3) targets build time and image size directly. These numbers must exist before changes are made, otherwise the optimisation cannot be judged.

## Goal
Record current Docker build time and image size for the selected repository.

## Scope
- Local Docker build time (if applicable).
- CI Docker build time (if available).
- Final image size.
- Current base image / build approach.

## Acceptance criteria
- [ ] Current Docker build duration is documented (local and/or CI)
- [ ] Current final image size is documented
- [ ] Current base image and build approach are identified


---

> Source: `docs/stories/story-2-baseline/task-4-integration-test-baseline.md`

# T2.4 — Capture integration-test baseline

**Story:** [Story 2 — Baseline & Pilot Scope](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T2.4 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 2 — Baseline & Pilot Scope |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `baseline`, `integration-test`, `docker-compose` |
| **Sprint** | Week 1 |
| **Depends on** | T2.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The Testcontainers (Story 4) and Compose (Story 5) work both depend on understanding how integration tests run today, what they depend on, and where the pain is. This task captures that starting picture.

## Goal
Document how integration tests currently start and behave for the selected repository.

## Scope
- How integration tests are currently started (command / pipeline step).
- Docker Compose dependencies involved.
- Startup / wait time before tests can run.
- Known flaky or environment-related issues, if any.

## Acceptance criteria
- [ ] Current integration-test setup is documented
- [ ] Required dependencies are listed
- [ ] Known pain points / flaky behaviours are captured


---

> Source: `docs/stories/story-3-build/README.md`

# Story 3 — Docker Build Optimisation

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** T2.3 Docker build & image baseline · **Parallel with:** Story 4

## Goal
Select and apply one safe Dockerfile or build-context optimisation for the SNS pilot repository, using the measured T2.3 before-state and proving the impact with before/after numbers.

> **Drone / ownership constraint:** Local Dockerfile and `.dockerignore` prototypes are in scope for Story 3. Durable production Dockerfile changes are expected to require RepoSync; `.dockerignore` ownership still needs confirmation. BuildKit cache mounts can be tested locally, but Drone/DIND CI support is unproven and cache is ephemeral between CI runs unless ACP/ETO provide registry cache support (see T1.5).

## Why
Build time and image size are recurring sources of friction. Story 2 provides the measured before-state for SNS: final image size `906MB`, cold local Docker build `real 1m17.855s`, warm cached build `real 0m0.851s`, full Docker build context `191.27MB`, and N=10 CI elapsed average `13:35`. Story 3 uses these measured baselines and keeps claim boundaries explicit.

## Outcome

Story 3 produced one keep-now change and one carry-forward prototype:

- **Keep now:** targeted `.dockerignore`, reducing Docker build context from `191.27MB` to `189B` while preserving required runtime artefacts. The targeted `.dockerignore` was validated successfully in the SNS repository. For durable reuse and consistency across command-adaptor repositories, `.dockerignore` ownership and distribution should be considered through RepoSync. RepoSync adoption is a recommended follow-up.
- **Carry forward:** layer-order Dockerfile prototype, improving local same-daemon warm-cache rebuild after a real JAR content change from `75.82-77.90s` to `4.62-5.08s`.

No image-size reduction, cold-build improvement, CI saving, production Dockerfile change, RepoSync approval or broad adaptor-family rollout is claimed.

## Acceptance criteria
- [x] T2.3 Docker/image baseline is reviewed as the before-state
- [x] Dockerfile and `.dockerignore` ownership route is confirmed or documented
- [x] One safe Docker/build-context optimisation candidate is selected
- [x] `.dockerignore`, if added or changed, preserves the runtime artefacts required by the Dockerfile
- [x] At least one focused Dockerfile/build-context improvement is applied locally or prepared as a RepoSync-ready change
- [x] Build time, build context and image size are compared before/after where the change is applied, with a keep/adjust recommendation
- [x] No production/base-image change is recommended without RepoSync, ownership, approved image-source and security validation

## Implementation sequencing

T3.2 may satisfy the first implementation candidate where `.dockerignore` / build-context reduction is selected. T3.3 is used for the selected Dockerfile/build optimisation where a non-`.dockerignore` candidate is chosen, or where a second focused candidate is explicitly selected after T3.1. Story 3 should apply one focused change at a time so before/after impact can be attributed.

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T3.1 | [Confirm Dockerfile ownership route and select optimisation candidate](./task-1-review-dockerfile.md) | 2 | Must | Completed |
| T3.2 | [Add or validate .dockerignore while preserving runtime artefacts](./task-2-dockerignore.md) | 1 | Must | Completed - targeted `.dockerignore` validated locally |
| T3.3 | [Apply one safe Docker build optimisation](./task-3-layering-improvement.md) | 2 | Must | Completed - layer-order prototype measured locally; carry forward only |
| T3.4 | [Measure local Docker build and image impact](./task-4-measure-impact.md) | 2 | Should | Completed - impact summary published |


---

> Source: `docs/stories/story-3-build/task-1-review-dockerfile.md`

# T3.1 — Confirm Dockerfile ownership route and select optimisation candidate

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.1 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `docker`, `dockerfile`, `build-context`, `ownership` |
| **Sprint** | Week 2 |
| **Depends on** | T2.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Optimisation should start from the measured T2.3 Docker/image baseline rather than repeating broad discovery. This task turns the T2.3 findings into an ownership route and one safe implementation candidate for Story 3.

## Goal
Use the T2.3 Docker/image baseline to confirm the RepoSync/platform ownership route and select one safe Docker/build-context optimisation candidate.

## Scope
Review the T2.3 evidence and decide:
- which Dockerfile or build-context candidate is safest to attempt first
- whether Dockerfile changes must go through RepoSync/platform ownership
- whether `.dockerignore` is repo-local or centrally managed
- whether any base-image option has an approved image-source / Artifactory / security route

Use the T2.3 measured SNS baseline as the starting point:

| Metric | Baseline |
|--------|----------|
| Final image size | `906MB` |
| Cold local Docker build | `real 1m17.855s` |
| Warm cached local Docker build | `real 0m0.851s` |
| Full Docker build context transferred | `191.27MB` |
| Base/rootfs layers visible in Docker history | `165MB + 300MB` |
| Executable JAR layer | `173MB` |
| `yum install/update` layer | `249MB` |
| Docker ignore status | Not found in SNS local checkout during T2.3 local file search |

DVLA and RoRo TSV may be used as structural portability evidence only unless direct measurements are captured for them.

Candidate options include:
- `.dockerignore` / build-context reduction while preserving runtime artefacts
- `yum install/update` layer repeatability review
- layer-order improvement for the current packaging-only Dockerfile
- runtime base-image review only after approved image-source / Artifactory validation

## Acceptance criteria
- [ ] T2.3 baseline is reviewed as the before-state.
- [ ] Dockerfile and `.dockerignore` ownership route is confirmed or documented.
- [ ] One safe optimisation candidate is selected.
- [ ] RepoSync/platform route is captured where required.
- [ ] No production/base-image change is recommended without image-source, Artifactory and security validation.
- [ ] No optimisation saving is claimed without before/after measurement.


---

> Source: `docs/stories/story-3-build/task-2-dockerignore.md`

# T3.2 — Add or validate .dockerignore while preserving runtime artefacts

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.2 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 1 |
| **Priority** | Must |
| **Labels** | `docker`, `dockerignore`, `build-context` |
| **Sprint** | Week 2 |
| **Depends on** | T3.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A missing or weak `.dockerignore` sends unnecessary files into the build context, slowing builds and invalidating cache when irrelevant files change. This is one of the cheapest, lowest-risk wins available.

## Goal
Add or validate `.dockerignore` to reduce build context without breaking the required Docker `COPY` inputs for the current packaging-only Dockerfile.

## Scope
- Check whether a `.dockerignore` exists and what it covers.
- Confirm whether `.dockerignore` is repo-local or RepoSync-managed.
- Exclude IDE/editor files, VCS metadata, logs, local artefacts and unnecessary generated files while explicitly retaining the runtime artefacts copied by the Dockerfile.
- Retain the two runtime artefacts required by the current Dockerfile:
  - `target/cmd-adaptor-sns-exec.jar`
  - `target/dependencies/opentelemetry-javaagent.jar`

Suggested baseline:
```gitignore
# Source is not needed by the current Dockerfile; it packages pre-built artefacts.
src/

# Keep only the runtime artefacts copied by the Dockerfile.
target/**
!target/
!target/cmd-adaptor-sns-exec.jar
!target/dependencies/
!target/dependencies/opentelemetry-javaagent.jar

# Local tooling and noise.
.git/
.gitignore
.idea/
.vscode/
*.iml
*.log
.DS_Store
tmp/
.tmp/
```

Do not use a blanket `target` exclusion unless the required JAR and OpenTelemetry agent are explicitly re-included and a Docker build verifies that the context still contains them.

Excluding `src/` is valid only while the Dockerfile continues to package pre-built artefacts and does not copy source files.

## Acceptance criteria
- [ ] `.dockerignore` exists or the ownership route preventing direct addition is documented.
- [ ] Required artefacts are preserved:
  - `target/cmd-adaptor-sns-exec.jar`
  - `target/dependencies/opentelemetry-javaagent.jar`
- [ ] Docker build succeeds after the `.dockerignore` change or validation.
- [ ] Unnecessary files are excluded from the build context.
- [ ] Build context before/after is recorded where measurable.


---

> Source: `docs/stories/story-3-build/task-3-layering-improvement.md`

# T3.3 — Apply one safe Docker build optimisation

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.3 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `docker`, `dockerfile`, `build-context`, `cache` |
| **Sprint** | Week 2 |
| **Depends on** | T3.1 |
| **Related to** | T3.2 where `.dockerignore` / build-context reduction is the selected candidate |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The T2.3 baseline identifies several Docker/build-context optimisation candidates, but Story 3 should apply only one safe change at a time so the impact can be measured and attributed. The current SNS Dockerfile packages pre-built Maven artefacts; it does not build the application inside Docker.

## Goal
Apply one focused Docker/build-context optimisation aligned with the SNS Dockerfile shape and ownership route.

## Scope
Consider (pick the highest-value one for this repo):
- `.dockerignore` / build-context reduction
- layer-order improvement for the current packaging-only Dockerfile
- `yum install/update` layer repeatability review
- runtime base-image review only after approved image-source / Artifactory / security validation

T3.3 should not duplicate T3.2. If `.dockerignore` / build-context reduction is the selected candidate, T3.2 may be the implementation task. Use T3.3 for a non-`.dockerignore` candidate or for a second focused candidate only after T3.1 explicitly selects it.

Do not change the lifecycle to build Maven inside the Dockerfile unless that larger design is explicitly selected. The existing pipeline builds Maven artefacts first, then the Dockerfile copies `target/cmd-adaptor-sns-exec.jar` and `target/dependencies/opentelemetry-javaagent.jar`.

Do not introduce a generic Maven multi-stage Dockerfile pattern unless the repo build design changes. Production Dockerfile changes should be routed through RepoSync unless ownership is confirmed otherwise.

## Acceptance criteria
- [ ] One change is applied only.
- [ ] Expected benefit is documented against the T2.3 baseline.
- [ ] Compatibility risks or concerns are noted.
- [ ] Ownership risks are documented.
- [ ] No generic Maven multi-stage build is introduced unless the repo build design changes.
- [ ] Runtime base-image changes are not recommended without approved image-source / Artifactory / security validation.
- [ ] Built image completes the same local Docker build verification used in T2.3, or an explicitly documented equivalent local verification; Trivy scan result is captured if a candidate image is built.


---

> Source: `docs/stories/story-3-build/task-4-measure-impact.md`

# T3.4 — Measure local Docker build and image impact

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.4 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 2 |
| **Priority** | Should |
| **Labels** | `docker`, `metrics`, `before-after` |
| **Sprint** | Week 3 |
| **Depends on** | T3.2 and T3.3 |
| **Owner** | _TBD_ |
| **Status** | Completed - T3.2 and T3.3 measurements consolidated into the impact summary |
| **Execution result** | [T3.4 impact summary](../../../solution/story-3/T3.4-impact-summary.md) |

## Why
A change is only worth keeping if it measurably helps. Comparing against the Story 2 baseline turns the optimisation into evidence stakeholders can trust.

## Goal
Compare before/after local Docker build impact using the measured SNS before-state from T2.3 and the measured Story 3 results from T3.2 and T3.3. Keep build-context reduction, image-size impact, cold/no-cache build behaviour, and warm-cache rebuild behaviour separate.

## Scope
Compare against the baseline:
- local build time before/after
- build context before/after
- final image size before/after
- runtime artefact preservation after `.dockerignore`
- no-change warm-cache rebuild vs real JAR-change warm-cache rebuild
- same-daemon local warm-cache benefit vs CI benefit
- CI build time before/after only if the CI change is actually run and the metric can be interpreted safely

T3.4 should treat T3.2 and T3.3 as separate optimisation effects:

- T3.2 measures targeted `.dockerignore` impact on Docker build context, image size and cold build behaviour.
- T3.3 measures local same-daemon warm-cache rebuild behaviour after a real application JAR content change.

Before-state from T2.3:

| Metric | Before-state baseline |
|--------|-----------------------|
| Final image size | `906MB` |
| Cold local Docker build | `real 1m17.855s` |
| Warm cached local Docker build | `real 0m0.851s` |
| Full Docker build context transferred | `191.27MB` |

T3.2 measured after-state:

| Metric | T3.2 after-state |
|--------|------------------|
| Build context transfer | `189B` |
| Cold build | `real 78.14s` |
| Final image size | `906MB` |
| Runtime artefacts | Confirmed present |

T3.3 measured after-state:

| Scenario | Current Dockerfile | Layer-order prototype |
|----------|--------------------|-----------------------|
| Warm rebuild after real JAR content change | `77.90s` / `75.82s` | `5.08s` / `4.62s` |

Result: the layer-order prototype is approximately `15-16x` faster for a same-daemon local warm-cache rebuild after a real application JAR content change.

Important measurement boundary:

The T2.3 warm cached local Docker build value (`real 0m0.851s`) represents a no-change warm cached rebuild. It should not be compared directly with the T3.3 warm-cache rebuild after a real application JAR content change. T3.3 measures a different and more meaningful scenario: whether the expensive setup layer is reused when the application artefact changes.

The T2.2 `Command Adaptor` Drone step is useful as CI cost-concentration evidence, but it is not an isolated Docker build timer. Do not subtract local Docker savings directly from total CI elapsed time unless CI after-measurements support that conclusion.

## Claim boundaries

- No CI saving is claimed.
- No cold-build improvement is claimed.
- No image-size reduction is claimed.
- No production Dockerfile change is claimed.
- T3.2 recommendation: keep `.dockerignore` as the proven build-context reduction, subject to repository vs RepoSync/platform ownership confirmation.
- T3.3 recommendation: carry forward the layer-order prototype to the ownership discussion; do not recommend production adoption without RepoSync/platform ownership confirmation.

## Acceptance criteria
- [x] Before/after local Docker build metrics are captured.
- [x] Before/after build-context and image-size metrics are captured.
- [x] T3.2 `.dockerignore` impact is summarised separately from T3.3 layer-order impact.
- [x] No-change warm-cache rebuild and real JAR-change warm-cache rebuild are not mixed.
- [x] Same-daemon local warm-cache benefit is scoped separately from CI benefit.
- [x] Production adoption is not recommended without RepoSync/platform ownership confirmation.
- [x] Production adoption recommendation distinguishes between "keep locally validated change" and "carry forward prototype for RepoSync/platform discussion".
- [x] Improvement or regression is documented.
- [x] CI impact is measured only if available; otherwise it is explicitly recorded as not measured.
- [x] No CI saving is claimed without CI measurement.
- [x] A keep/adjust recommendation is made.


---

> Source: `docs/stories/story-4-testcontainers/README.md`

# Story 4 — Testcontainers Pilot

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** Story 2 · **Parallel with:** Story 3

## Goal
Validate the Redis-first Testcontainers pilot route identified by the T2.4/T2.5 evidence. Phase 1 is a local, opt-in Redis smoke/wiring pilot that tests feasibility, dependency wiring, isolation and the developer workflow without changing the full integration-test flow.

> **Drone constraint:** CI feasibility depends on Story 1 findings (T1.4) and the centrally managed Drone/DIND environment. The Drone pipeline uses DIND with `DOCKER_HOST=tcp://docker:2375`, and Testcontainers may need a Ryuk constraint such as `TESTCONTAINERS_RYUK_DISABLED=true`. CI remains a follow-up unless it is explicitly attempted and measured; production or default CI adoption is not part of Phase 1.

## Why
Redis was selected as the lower-complexity first candidate because it can validate Testcontainers startup, connectivity and local workflow with limited scope. Redis is a support dependency in the current docker-compose/application/pre-integration setup; it is not the central Kafka-driven input and assertion path observed in the integration tests.

Kafka and Schema Registry remain higher-value follow-up candidates because they are central to that path and carry topic, message and offset isolation risks. Their additional value and complexity make them unsuitable for the first wiring pilot.

Phase 1 does not replace docker-compose Redis or the full docker-compose integration stack. It does not claim faster execution, CI savings, fewer flaky tests, improved Kafka isolation or full-stack replacement. Any such outcome requires separate implementation and measurement.

## Acceptance criteria
- [x] Redis is confirmed as the Phase 1 candidate and its scope and non-goals are documented
- [x] A local, opt-in Redis Testcontainers smoke/wiring test is implemented or prototyped and connects successfully
- [ ] The Redis pilot is compared fairly with the existing docker-compose Redis/support-dependency flow
- [ ] Findings, limits and a continue/stop recommendation are documented without unmeasured claims

## Tasks
| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T4.1 | [Confirm Redis pilot candidate and scope](./task-1-select-candidate.md) | 1 | Must | Completed |
| T4.2 | [Implement Redis Testcontainers smoke/wiring pilot](./task-2-implement-setup.md) | 3 | Must | Completed |
| T4.3 | [Compare Redis pilot with docker-compose support flow](./task-3-compare-flows.md) | 2 | Should | In progress - target-machine Compose runtime measurement pending |
| T4.4 | [Document Redis pilot findings, limits and recommendation](./task-4-document-findings.md) | 1 | Should | Not started |


---

> Source: `docs/stories/story-4-testcontainers/task-1-select-candidate.md`

# T4.1 — Confirm Redis pilot candidate and scope

**Story:** [Story 4 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.1 |
| **Type** | Scope confirmation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 1 |
| **Priority** | Must |
| **Labels** | `testcontainers`, `redis`, `integration-test`, `scope` |
| **Sprint** | Week 2 |
| **Depends on** | T2.4, T2.5 |
| **Owner** | _TBD_ |
| **Status** | Completed |

## Why
T2.4/T2.5 already identified Redis as the lower-complexity first Testcontainers candidate. This task confirms that decision and fixes the implementation boundary before T4.2; it does not restart open-ended candidate selection.

T4.1 should reuse the published T2.4/T2.5 decision and perform only the repository-level prerequisite checks needed to make T4.2 implementation-ready. It should not repeat the candidate analysis. Its purpose is to formalise the existing Redis-first decision, verify implementation prerequisites against the actual repository and record the selected opt-in execution route for T4.2—not to reconsider Redis, Kafka, Schema Registry and LocalStack from scratch.

## Goal
Confirm Redis as the first low-complexity Testcontainers pilot candidate, using the T2.4/T2.5 evidence, and define a safe Phase 1 scope for implementation.

## Expected output

T4.1 should produce a short implementation-readiness record containing:

- confirmed target integration-test module and test framework
- selected Testcontainers dependency/version-management route
- selected Redis image, tag and image-source route
- selected Redis connectivity approach
- selected opt-in Maven profile, include pattern or isolated test-class approach
- confirmed non-goals and RepoSync constraints
- a T4.2 ready / blocked decision with any unresolved prerequisites listed

## Scope
Confirm and document that:

- Redis is selected first because its lower setup complexity provides a small test of Testcontainers startup, connectivity, wiring, isolation and local workflow.
- Redis is an infrastructure/support dependency in the current flow, not the central Kafka-driven integration-test input and assertion path.
- Kafka and Schema Registry remain higher-value follow-up candidates because they are central to that path and carry higher setup complexity plus topic, message and offset isolation risk.
- Phase 1 is a local, opt-in Redis smoke/wiring pilot.
- Phase 1 does not replace docker-compose Redis or the full docker-compose stack, change the full E2E flow, change `local-int-cmd` or `local-int-snapshot`, modify RepoSync-managed pre-integration files, or run in CI by default.

Before T4.2, check and record these implementation prerequisites:

- the relevant integration-test module POM and test framework
- the Testcontainers dependency and version-management approach
- an existing Redis client dependency or a simple alternative connectivity approach
- an opt-in Maven profile, test include pattern or clearly isolated test-class approach
- the exact Redis image/tag and image-source route to use, aligned with the compose baseline where available; do not assume direct public-registry access without local or approved registry validation
- confirmation that no local-only edit to `pre-integration-test/app.py` is required or permitted

## Acceptance criteria
- [x] Redis is confirmed as the first candidate and the rationale is documented
- [x] The Kafka and Schema Registry follow-up rationale is documented
- [x] Phase 1 scope and non-goals are documented
- [x] Repository-level implementation prerequisites are checked and recorded, including the confirmed target module, test framework, dependency-management route, Redis image/source, Redis connectivity approach and intended opt-in execution mechanism
- [x] A T4.2 implementation-ready or blocked decision is recorded; any unresolved prerequisite is identified explicitly as a blocker or follow-up
- [x] No CI saving or flaky-test improvement is claimed


---

> Source: `docs/stories/story-4-testcontainers/task-2-implement-setup.md`

# T4.2 — Implement Redis Testcontainers smoke/wiring pilot

**Story:** [Story 4 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.2 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 3 |
| **Priority** | Must |
| **Labels** | `testcontainers`, `redis`, `integration-test`, `implementation` |
| **Sprint** | Week 2 |
| **Depends on** | T4.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A small, runnable Redis test provides evidence about Testcontainers feasibility, wiring and local developer workflow before any higher-risk work on the Kafka-driven assertion path.

## Goal
Implement a Redis Testcontainers smoke/wiring pilot that can be run locally and explicitly, independently of the full docker-compose E2E flow.

## Expected implementation changes
T4.2 should produce only the minimum repo-local changes required for the Redis pilot, following the repository's existing Maven structure and the T4.1 implementation-readiness decision.

Expected changes are limited to:

- Testcontainers dependency/version configuration in the appropriate Maven dependency-management location
- test-scoped Testcontainers and Redis client dependencies in `cmd-adaptor-sns-integration-tests`
- one isolated JUnit Jupiter test class, such as `MinimalRedisTest`
- an opt-in Maven profile, test include pattern or documented explicit `-Dtest=MinimalRedisTest` route
- a short execution/result note recording the command, dependency versions, image source, timing method and observed outcome

T4.2 must not change these files or routes unless temporary isolated pilot experimentation is needed:

- RepoSync-managed docker-compose resources (temporary experimentation allowed if clearly documented; durable changes via RepoSync)
- `pre-integration-test/app.py` (temporary experimentation allowed if clearly documented; durable changes via RepoSync)
- `local-int-cmd`
- `local-int-snapshot`
- `ci-cmd`
- `ci-snapshot`
- Drone/CI configuration
- the full Cucumber E2E flow

## Scope
In the relevant integration-test module:

- use the implementation-readiness decisions recorded by T4.1 for the target module, test framework, dependency-management route, Redis image/source, connectivity approach and opt-in execution mechanism; do not reopen those choices in T4.2 unless repository evidence shows that the selected route is not implementable
- add a small, clearly named Redis Testcontainers test
- prefer opt-in local execution through a Maven profile, test include pattern or otherwise clearly isolated test class
- use the Redis image, tag and approved/local image-source route confirmed by T4.1, aligned with the compose baseline where available; do not assume direct public-registry access
- record the exact resolved Testcontainers version, Redis client version, dependency source/repository route and Redis image source used by the successful local run
- keep all new implementation dependencies test-scoped unless repository evidence requires a different route and that exception is documented
- start Redis through Testcontainers and verify that the container becomes ready
- ensure the test fails clearly if the Redis container does not become ready, mapped-port connectivity fails, or the expected Redis interaction does not return the required result
- verify connectivity with a minimal Redis interaction, such as `PING` or `SET`/`GET`, using an existing Redis client dependency if one exists or the minimal test-scoped Redis client route selected from T4.1
- define cleanup and isolation behaviour appropriate to the smoke test
- verify that the test does not depend on Redis state from a previous run and that repeated local executions start from a clean or explicitly reset state
- record how state isolation is achieved, for example unique keys, explicit cleanup, container lifecycle isolation or database reset
- keep the pilot separate from the full docker-compose E2E flow
- RepoSync-controlled files may be changed temporarily in the target repository for isolated pilot experimentation when needed, provided the change is clearly documented and not presented as the durable ownership route. Long-term adoption should still be handled through the relevant RepoSync/MR process.
- do not change `local-int-cmd` or `local-int-snapshot` as the durable route
- do not replace compose Redis in Phase 1
- do not wire the pilot into CI by default
- document the exact local execution command and, if the test is run, the measurement method and observed local smoke-test time
- if execution time is recorded, identify whether the value is Maven elapsed time, test-framework-reported time or container startup time; do not present one as another
- if more than one timing type is available, report them separately; do not derive docker-compose, CI or full-suite savings from the Redis smoke-test measurement

## Environment and CI note

Docker availability is required for local execution. Drone/DIND feasibility is follow-up work unless CI is explicitly attempted. If it is attempted, record the relevant `DOCKER_HOST` and Ryuk constraints, execution method and result. Otherwise record CI suitability as **not measured**.

The pilot must not claim CI savings, flaky-test improvement or full-stack replacement from a successful Redis smoke test.

## Execution boundary

If local implementation or execution is blocked by missing Docker access, dependency resolution or repository prerequisites, record the environment blocker explicitly and leave the implementation acceptance criteria incomplete. A prepared plan alone does not complete T4.2.

## Completion validation

Before marking T4.2 complete:

- review the final Git diff
- confirm only intended repo-local pilot files changed
- confirm production and RepoSync-managed files were not modified
- confirm existing Maven profiles and E2E commands retain their prior semantics
- confirm the exact opt-in command runs only the Redis smoke/wiring pilot
- confirm the full E2E flow is not required for the pilot execution

## Acceptance criteria
- [ ] A Redis Testcontainers smoke/wiring test is implemented in the relevant integration-test module and is runnable locally through the documented opt-in mechanism
- [ ] Expected implementation changes are limited to the Maven dependency/version route, test-scoped pilot dependencies, one isolated Redis smoke test, the opt-in execution route and the execution/result note
- [ ] Exact Testcontainers version, Redis client version, Redis image/tag and dependency/image-source routes used by the local pilot are documented
- [ ] New pilot dependencies are test-scoped or any exception is explicitly justified
- [ ] Redis starts locally through Testcontainers when the pilot is executed
- [ ] The test connects successfully and completes its minimal Redis interaction
- [ ] Container-startup, connectivity and Redis-interaction failures produce a clear failing test result rather than a silent skip or false pass
- [ ] Repeated local executions pass without depending on state left by a previous run, and the isolation/cleanup mechanism is documented
- [ ] The test is opt-in or otherwise isolated from the full E2E flow
- [ ] The existing docker-compose E2E flow is not replaced
- [ ] RepoSync-managed files may only be modified for isolated pilot experimentation; durable changes must go through RepoSync
- [ ] The local execution command and any measured smoke-test time are documented
- [ ] Final Git diff confirms only intended repo-local pilot files changed
- [ ] Existing Maven profiles and full E2E execution semantics remain unchanged
- [ ] The documented opt-in command runs the Redis pilot without requiring the full docker-compose E2E stack
- [ ] CI suitability is assessed or explicitly recorded as follow-up/not measured
- [ ] No CI saving, flaky-test improvement or full-stack replacement is claimed


---

> Source: `docs/stories/story-4-testcontainers/task-3-compare-flows.md`

# T4.3 — Compare Redis pilot with docker-compose support flow

**Story:** [Story 4 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.3 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 2 |
| **Priority** | Should |
| **Labels** | `testcontainers`, `redis`, `docker-compose`, `comparison` |
| **Sprint** | Week 3 |
| **Depends on** | T4.2 |
| **Owner** | _TBD_ |
| **Status** | In progress — T4.2 evidence available; target-machine Compose runtime measurement pending |

## Why
The Redis smoke test and the full integration-test suite exercise different scopes. A fair comparison must isolate the Redis/support-dependency workflow and avoid presenting a minimal wiring test as a replacement for the Kafka-driven E2E path.

## Goal
Compare the Phase 1 Redis Testcontainers smoke/wiring pilot with the existing docker-compose Redis/support-dependency flow and decide whether the evidence justifies a follow-up.

## Scope
Distinguish clearly between:

- the existing docker-compose Redis/support-dependency path
- the local, opt-in Redis Testcontainers smoke/wiring path
- the full E2E Kafka and Schema Registry input/assertion path, which remains out of scope for Phase 1

Compare the two Redis-related paths across:

- startup/setup time, only where measured with a documented method
- local command complexity
- developer feedback loop
- isolation and determinism
- dependency wiring complexity
- repeated-run behaviour, where practical, to distinguish one-off startup cost from stable local behaviour
- CI feasibility, recorded as not measured when no CI run was attempted
- benefits, drawbacks and limitations
- whether the evidence is sufficient to attempt Redis Option B or prioritise a Kafka and Schema Registry follow-up

## Comparison boundary

Do not compare the minimal Redis smoke test with the full integration-test suite as though they provide equivalent coverage or as though the pilot replaces the suite. Do not infer speed, CI or reliability improvements that were not measured.

The existing `redis_kafka` pre-integration readiness stage combines Redis readiness with Kafka, Schema Registry and Kafdrop readiness plus Kafka topic creation. If Redis startup time cannot be isolated safely from that combined flow, record docker-compose Redis startup time as not separately measured rather than assigning an estimate or treating the combined stage as a Redis-only timing.

Full E2E compose timing may be captured as contextual evidence, but it must not be compared directly with the Redis-only Testcontainers smoke test.

## Acceptance criteria
- [ ] Redis Testcontainers and the docker-compose Redis/support-dependency flow are compared fairly across the defined dimensions
- [ ] Full E2E and docker-compose replacement are explicitly marked out of scope
- [ ] Benefits, drawbacks and limitations are documented
- [ ] CI suitability is not claimed unless it was attempted and measured
- [ ] Existing docker-compose Redis startup time is reported only if it can be isolated with a documented measurement method; otherwise it is explicitly recorded as not separately measured
- [ ] If a Redis-only docker-compose comparison cannot be isolated safely, the limitation and reason are documented, and no like-for-like performance conclusion is made
- [ ] The comparison records the exact commands, environment and measurement method used for each measured value
- [ ] The recommendation states whether to continue to Redis Option B and/or a Kafka and Schema Registry follow-up


---

> Source: `docs/stories/story-4-testcontainers/task-4-document-findings.md`

# T4.4 — Document Redis pilot findings, limits and recommendation

**Story:** [Story 4 — Testcontainers Pilot](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T4.4 |
| **Type** | Documentation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 4 — Testcontainers Pilot |
| **Estimate** | 1 |
| **Priority** | Should |
| **Labels** | `testcontainers`, `redis`, `documentation`, `findings` |
| **Sprint** | Week 3 |
| **Depends on** | T4.3 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The Redis-first pilot is intentionally narrow. Clear findings, constraints and non-claims are needed so stakeholders can decide whether to stop, deepen the Redis experiment or progress to the higher-value Kafka and Schema Registry candidates without overstating what Option A proved.

## Goal
Document the final evidence and recommendation from the Redis-first Option A pilot, including what remains untested.

## Scope
Document:

- what was tested
- what was intentionally not tested
- the local result and any measured timing, including the measurement method
- CI status: attempted and measured, or explicitly not measured/follow-up
- RepoSync constraints, any temporary target-repository pilot changes, and confirmation that no durable RepoSync-managed adoption was approved
- whether Redis Option A succeeded against its smoke/wiring goal
- whether Redis Option B, a local-profile Redis override, should be attempted
- whether Kafka and Schema Registry should be prioritised as the next higher-value follow-up
- limitations, non-claims and the continue/stop recommendation

Where a metric was not captured, record it explicitly as not measured and do not delay the final recommendation unless that metric is decision-critical.

Apply the reuse policy: Testcontainers reuse may be enabled locally for faster feedback only when the setup and effect are documented. Reuse must be disabled in CI for deterministic runs with no hidden shared state.

## Required non-claims

Unless separately implemented and measured, state explicitly:

- no full docker-compose replacement was demonstrated
- no Kafka topic, message or offset isolation improvement results from the Redis pilot
- no flaky-test improvement was demonstrated
- no CI saving was demonstrated
- no broad adaptor rollout is recommended or approved
- no durable RepoSync-managed adoption or broad rollout is approved
- no production or default pipeline change is implemented or approved

## Acceptance criteria
- [ ] Findings and intentionally untested areas are documented
- [ ] Local, CI and RepoSync constraints are documented
- [ ] Every measured value is linked to its measurement method or source evidence
- [ ] Missing or unavailable measurements are explicitly recorded and are not replaced with estimates
- [ ] A continue/stop recommendation is documented
- [ ] The final recommendation distinguishes measured evidence, interpretation and follow-up assumptions
- [ ] Required non-claims are documented
- [ ] Next-step recommendations are separated into:
  - [ ] Option A complete / incomplete
  - [ ] Option B candidate / not candidate
  - [ ] Kafka and Schema Registry follow-up candidate / not candidate
  - [ ] CI follow-up required / not required


---

> Source: `docs/stories/story-5-compose/README.md`

# Story 5 — Docker Compose Rationalisation

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** T1.3, T1.4 and Story 4 final evidence (T4.4 / Story 4 Summary) · **Parallel with:** —

## Goal
Define an evidence-backed target role for Docker Compose across CI, full E2E and local debugging, without changing the current default flows.

## Why
The repository uses Compose in more than one orchestration path. Reviewers need one clear account of what is used and one safe recommendation, rather than separate tickets for mapping, classification and documentation.

## Boundaries / non-claims

- This story maps and recommends; it does not change Compose, Maven profiles, CI or production defaults.
- Full E2E, Redis-only and Kafka/Schema Registry scopes remain distinct.
- A local Testcontainers result does not prove that a Compose service can be removed from CI.
- Any durable change to RepoSync-controlled files requires the RepoSync/platform route.
- Unavailable timing, reliability and CI evidence is recorded as `not measured`.

## Acceptance criteria

- [x] Compose services and invocation paths are mapped and classified.
- [x] CI, full E2E and local-debug roles are kept distinct.
- [x] A target Compose role and unresolved decisions are recorded.
- [x] Implementation, ownership and evidence limits are explicit.

## Tasks

| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T5.1 | [Validate current Compose scope](./task-1-validate-compose-scope.md) | 3 | Must | Done — evidence prepared |
| T5.2 | [Decide the target Compose role](./task-2-decide-compose-role.md) | 2 | Must | Done — target-role recommendation prepared; implementation and adoption not approved |

**Supporting outputs:** [T5.1 evidence](../../../solution/story-5/T5.1-validate-compose-scope.md) · [T5.2 evidence](../../../solution/story-5/T5.2-decide-compose-role.md)

**Consolidation mapping:** [Story 5 and Story 6 consolidation](../STORY-5-6-CONSOLIDATION.md)


---

> Source: `docs/stories/story-5-compose/task-1-validate-compose-scope.md`

# T5.1 — Validate Current Compose Scope

**Story:** [Story 5 — Docker Compose Rationalisation](./README.md)

| Field | Value |
|---|---|
| **ID** | T5.1 |
| **Type** | Analysis |
| **Estimate** | 3 |
| **Priority** | Must |
| **Depends on** | T1.3, T1.4 and Story 4 final evidence ([T4.4](../../../solution/story-4/T4.4-document-findings.md) / [Story 4 Summary](../../../solution/story-4/SUMMARY.md)) |
| **Owner** | _TBD_ |
| **Status** | Done — evidence prepared |
| **Primary output** | [T5.1 — Validate Current Compose Scope](../../../solution/story-5/T5.1-validate-compose-scope.md) |

## Why

A safe Compose decision needs one trusted view of service topology and actual CI, full E2E and local usage.

## Goal

Validate the current Compose scope and identify which roles are confirmed, unclear or only candidates for change.

## Scope

Map services, dependencies and invocation paths; classify their CI, full E2E and local-debug roles; record uncertainties and relevant Story 1–4 evidence.

## Boundaries / non-goals

- No Compose, Maven-profile or CI change.
- No service removal based only on static analysis or a Redis-only pilot.
- Kafka/Schema Registry and full E2E behaviour remain outside the Redis-only evidence.

## Acceptance criteria

- [x] All defined services and material dependencies are represented.
- [x] CI, full E2E and local-debug use are distinguished.
- [x] Confirmed facts, structural observations and inferences are labelled.
- [x] Unclear or unmeasured behaviour is recorded without an optimisation claim.
- [x] The evidence needed for a safe target-role decision is linked.
- [x] Each confirmed invocation path and service-role classification is linked to repository or pipeline evidence.


---

> Source: `docs/stories/story-5-compose/task-2-decide-compose-role.md`

# T5.2 — Decide the Target Compose Role

**Story:** [Story 5 — Docker Compose Rationalisation](./README.md)

| Field | Value |
|---|---|
| **ID** | T5.2 |
| **Type** | Decision recommendation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Depends on** | [T5.1 evidence](../../../solution/story-5/T5.1-validate-compose-scope.md), [T4.4 findings](../../../solution/story-4/T4.4-document-findings.md) and [Story 4 Summary](../../../solution/story-4/SUMMARY.md) |
| **Owner** | _TBD_ |
| **Status** | Done — target-role recommendation prepared; implementation and adoption not approved |
| **Primary output** | [T5.2 — Decide the Target Compose Role](../../../solution/story-5/T5.2-decide-compose-role.md) |

## Why

The pilot needs a clear Compose boundary that preserves useful workflows and makes unproven reductions visible as candidates, not completed changes.

## Goal

Recommend the target role of Compose and state the evidence and ownership required before any adoption.

## Scope

Define explicit keep, candidate, stop/not-now, local-utility and architectural decisions for every material T5.1 area, plus the evidence and ownership route required before change.

## Boundaries / non-goals

- No reduced Compose implementation or default pipeline change.
- No CI benefit, reliability improvement or stakeholder approval is assumed.
- RepoSync/platform ownership remains separate from technical feasibility.

## Acceptance criteria

- [x] Every material T5.1 rationalisation candidate has an explicit target-role decision.
- [x] `KEEP`, `CANDIDATE`, `STOP / NOT NOW`, local-utility and architectural decisions are distinguishable.
- [x] Current validated behaviour is separated from future target-state candidates.
- [x] Full-E2E and local-debug safeguards are retained.
- [x] Missing functional, timing, CI and ownership evidence is explicit.
- [x] RepoSync/platform-owned changes have a durable decision route.
- [x] Story 6 handoff candidates are explicit and are not presented as approved work.
- [x] No prototype or repository analysis is presented as production approval.
- [x] All references point to one canonical T5.2 solution document.


---

> Source: `docs/stories/story-6-findings/README.md`

# Story 6 — Pilot Outcome, Ownership and Adoption

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** T3.4, current Story 4 evidence and the [T5.2 target-role decision](../../../solution/story-5/T5.2-decide-compose-role.md) · **Parallel with:** —

## Goal
Turn the validated pilot evidence into clear ownership routes and an explicit adopt, retain-as-candidate or stop decision for each outcome.

## Why
A PoC is useful only when reviewers can see what was actually proved, who can act, and what remains unapproved. This story keeps the evidence in supporting documents and makes the decision path concise.

## Boundaries / non-claims

- Measured, observed, structural and inferred evidence remain separate.
- Missing metrics are `not measured`; local evidence is not represented as CI evidence.
- The Docker layer-order result is only a local same-daemon warm-cache JAR-change observation of approximately 15–16x; no image-size, cold-build or CI improvement was demonstrated.
- The Redis pilot does not prove flaky-test improvement, Kafka/Schema Registry scope or full E2E replacement.
- A prototype, temporary repository experiment or prepared stakeholder pack is not production adoption, RepoSync approval or completed rollout.

## Acceptance criteria

- [x] Validated outcomes and non-claims are consolidated with evidence links.
- [x] Each candidate has a CST-local, RepoSync/platform or wider-owner route.
- [ ] Adopt/candidate/stop recommendations are reviewed with the relevant owners.
- [ ] Stakeholder feedback, approvals and next actions are recorded.

## Tasks

| Task | Title | SP | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T6.1 | [Classify pilot outcomes and ownership routes](./task-1-classify-outcomes.md) | 4 | Must | Done — evidence prepared |
| T6.2 | [Decide the adoption route and publish the pilot outcome](./task-2-decide-adoption.md) | 2 | Must | Not completed — materials prepared |

**Supporting outputs:** [T6.1 evidence](../../../solution/story-6/T6.1-classify-outcomes-and-ownership.md) · [T6.2 evidence](../../../solution/story-6/T6.2-decide-adoption-route.md)

**Consolidation mapping:** [Story 5 and Story 6 consolidation](../STORY-5-6-CONSOLIDATION.md)


---

> Source: `docs/stories/story-6-findings/task-1-classify-outcomes.md`

# T6.1 — Classify Pilot Outcomes and Ownership Routes

**Story:** [Story 6 — Pilot Outcome, Ownership and Adoption](./README.md)

| Field | Value |
|---|---|
| **ID** | T6.1 |
| **Type** | Evidence and ownership decision |
| **Estimate** | 4 |
| **Priority** | Must |
| **Depends on** | T3.4, current Story 4 evidence and [T5.2 target-role decision](../../../solution/story-5/T5.2-decide-compose-role.md) |
| **Owner** | _TBD_ |
| **Status** | Done — evidence prepared |
| **Primary output** | [T6.1 — Classify Pilot Outcomes and Ownership Routes](../../../solution/story-6/T6.1-classify-outcomes-and-ownership.md) |

## Why

Reviewers need one evidence-led view of what the pilot proved and which owner controls each durable next step.

## Goal

Classify validated outcomes, limitations and follow-ups by evidence type and ownership route.

## Scope

Consolidate relevant Story 1–5 evidence; distinguish CST-local, RepoSync/platform and wider ETO ownership; identify temporary target-repository work and durable adoption routes.

## Boundaries / non-goals

- No fabricated timing, CI, reliability or rollout result.
- No broad command-adaptor or production-adoption claim.
- Technical feasibility does not override RepoSync/platform ownership.

## Acceptance criteria

- [x] Each outcome links to its primary evidence.
- [x] Measured, observed, structural and inferred statements are separated.
- [x] Missing metrics are recorded as `not measured`.
- [x] Each candidate has an owner/board route and rationale.
- [x] Temporary experiments are separated from durable adoption.
- [x] Production, CI and broad-rollout non-claims are explicit.


---

> Source: `docs/stories/story-6-findings/task-2-decide-adoption.md`

# T6.2 — Decide the Adoption Route and Publish the Pilot Outcome

**Story:** [Story 6 — Pilot Outcome, Ownership and Adoption](./README.md)

| Field | Value |
|---|---|
| **ID** | T6.2 |
| **Type** | Adoption decision and communication |
| **Estimate** | 2 |
| **Priority** | Must |
| **Depends on** | T6.1 |
| **Owner** | _TBD_ |
| **Status** | Not completed — materials prepared; review and feedback not evidenced |
| **Primary output** | [T6.2 — Decide the Adoption Route and Publish the Pilot Outcome](../../../solution/story-6/T6.2-decide-adoption-route.md) |

## Why

Prepared findings do not close the pilot until the relevant owners review the recommendations and the resulting next steps are recorded.

## Goal

Agree adopt, retain-as-candidate or stop decisions, publish the concise pilot outcome, and record owner feedback.

## Scope

Prioritise follow-ups; define CI validation and rollout constraints; share the outcome with CST and relevant RepoSync/platform or wider stakeholders; capture decisions and unresolved items.

## Boundaries / non-goals

- Prepared materials are not evidence that sharing or approval occurred.
- No ticket, MR, production change or organisation-wide rollout is pre-approved.
- CI claims require CI measurement after the relevant owner enables the route.

## Acceptance criteria

- [ ] Each candidate has an agreed adopt, retain-as-candidate or stop decision.
- [ ] RepoSync/platform and wider-owner actions are prioritised and routed.
- [ ] Required CI validation and rollout constraints are explicit.
- [ ] The concise outcome is shared with the agreed stakeholders.
- [ ] Feedback, approvals and unresolved decisions are recorded.
- [ ] No prototype or recommendation is described as adopted without evidence.


---

> Source: `docs/adr/README.md`

# Architecture Decision Records (ADR)

ADRs capture **why** a significant decision was made — the context, the decision, and its consequences — so the reasoning survives beyond the conversation. [← Back to overview](../../README.md)

## What is an ADR?
A short, immutable record of one architecturally significant decision. When a decision changes, we don't rewrite history — we add a new ADR that **supersedes** the old one.

## Index

| ID | Title | Status | Related ADRs |
|----|-------|--------|--------------|
| [ADR-0001](0001-pilot-not-rollout.md) | Run a measured pilot, not a big-bang rollout | Proposed | 0002, 0003, 0004 |
| [ADR-0002](0002-testcontainers-for-integration-tests.md) | Use Testcontainers for selected integration tests | Proposed | 0001, 0003, 0005 |
| [ADR-0003](0003-reduce-compose-in-ci.md) | Reduce Docker Compose role in CI, keep it for local | Proposed | 0002, 0004, 0005 |
| [ADR-0004](0004-buildkit-cache-and-layering.md) | Use BuildKit cache + layered multi-stage builds | Proposed | 0001, 0003, 0005 |
| [ADR-0005](0005-ci-runner-docker-mode.md) | CI runner Docker execution mode (Drone Kubernetes + DIND) | Proposed | 0001, 0002, 0004 |

## Statuses
`Proposed` → under discussion · `Accepted` → decided · `Superseded by ADR-XXXX` · `Deprecated`.

## Adding an ADR
1. Copy [`template.md`](template.md) to `NNNN-short-title.md` (next number).
2. Fill in **Context · Decision · Consequences · Alternatives**.
3. Add it to the index above and link it from the relevant story if useful.


---

> Source: `docs/adr/0001-pilot-not-rollout.md`

# ADR-0001: Run a measured pilot, not a big-bang rollout

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team, stakeholders
- **Related:** [Epic](../../README.md) · [Project plan](../../PROJECT-PLAN.md) · [ADR-0002](0002-testcontainers-for-integration-tests.md) · [ADR-0003](0003-reduce-compose-in-ci.md) · [ADR-0004](0004-buildkit-cache-and-layering.md)

## Context

The FDP CI/CD pipeline currently suffers from long build times (~12 min average), heavy Docker Compose integration test setup, flaky environment-dependent test failures, and inconsistent Dockerfile patterns. Multiple optimisation ideas exist (Testcontainers, BuildKit caching, Compose rationalisation, base-image strategy), but none are proven in this specific context.

Implementing all of these at once across multiple repositories would be high-risk: if something breaks, diagnosing the cause is difficult; if something doesn't help, effort is wasted with no evidence to show. The team also lacks baseline data to prove whether any change actually improved the situation.

Additionally, some improvements are CST-local (the team can act immediately), some require RepoSync/platform template changes, and others require wider ETO infrastructure (shared base images, remote cache). Starting a rollout without knowing this boundary creates organisational friction.

## Decision

We will validate the optimisation ideas through a **small, measurable pilot on one representative repository**, capturing before/after evidence, before proposing any wider rollout.

The pilot:
- selects one repo that is representative but low-risk (Story 2)
- captures baseline metrics so improvement is provable (Story 2)
- applies build optimisation and measures the delta (Story 3)
- pilots Testcontainers for one dependency and compares to Compose (Story 4)
- reviews Compose role and recommends a reduced set (Story 5)
- consolidates findings and classifies CST-local vs RepoSync/platform vs wider ETO ownership (Story 6)

Only after evidence is available and ownership is clear will any wider rollout be proposed.

## Consequences

- **Positive:**
  - Low delivery risk — one repo, controlled scope, revertable changes.
  - Evidence-based — every claim is backed by before/after data.
  - Reusable patterns identified deliberately, not accidentally.
  - Ownership is explicit — no wider-impact item progresses without the right team involved.

- **Negative / trade-offs:**
  - Findings from one repo may not fully generalise (assumption A1).
  - Slower than a rollout — the pilot takes ~4 weeks before wider adoption is even discussed.
  - Some benefits (remote cache, base images) cannot be realised in the pilot alone — they require RepoSync/platform or wider ETO action post-pilot.

- **Follow-ups:**
  - State scope limits explicitly in the Story 6 summary.
  - Recommend a second repo before any org-wide rollout.
  - Route RepoSync/platform and wider ETO items via Story 6 with evidence attached.

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Big-bang rollout across all FDP repos | Fast impact if it works | High risk; no baseline evidence; hard to reverse; unclear ownership | Too risky for unproven changes |
| Do nothing | No effort or risk | Pain points persist; build/test friction continues to grow | Doesn't address known problems |
| Pilot across many repos simultaneously | Broader evidence base | Heavy coordination; defeats "small and controlled" intent; blocks on more teams | Disproportionate for a first pilot |
| Start with ACP/ETO changes first | Addresses infra gaps | Slow; depends on another team's priority; no CST evidence to justify the ask | Better to show local evidence first, then make the platform ask |


---

> Source: `docs/adr/0002-testcontainers-for-integration-tests.md`

# ADR-0002: Use Testcontainers for selected integration tests

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [ADR-0001 — Pilot approach](0001-pilot-not-rollout.md) · [ADR-0003 — Compose role](0003-reduce-compose-in-ci.md) · [ADR-0005 — CI runner mode](0005-ci-runner-docker-mode.md) · [Story 4](../stories/story-4-testcontainers/README.md)

## Context

Integration tests in the pilot repository currently rely on a full Docker Compose stack (`docker-compose.yml`) that starts all dependent services (Redis, Kafka, Schema Registry, etc.) before any test runs. This creates several problems:

1. **Slow startup:** the full stack takes ~90 seconds before the first test can execute, regardless of whether the test needs all services.
2. **Shared state:** services persist across test runs, so one test can pollute another. This causes intermittent, hard-to-reproduce failures.
3. **Environment drift:** the Compose setup behaves differently on developer machines vs CI runners (port conflicts, resource limits, network differences), leading to "works on my machine" problems.
4. **All-or-nothing:** you start the entire stack even if your test only needs Redis. This wastes CI minutes and complicates debugging.

Testcontainers (a Java library) offers a different model: each test (or test class) programmatically starts only the containers it needs, with isolated networks and randomised ports. The container lifecycle is managed from the test code — start before test, tear down after.

The stronger value proposition for Testcontainers is **determinism and isolation**, not just speed. A test that manages its own dependencies is reproducible by definition.

## Decision

We will pilot Testcontainers for **one selected integration dependency** (e.g. Redis or Kafka), managing its lifecycle from the test code, and compare it against the existing Compose flow before any wider adoption.

Specifics:
- The candidate dependency is selected in T4.1 based on simplicity and validation value.
- The Testcontainers setup is implemented in T4.2 (container definition, property wiring, wait strategy, cleanup).
- Container **reuse** is allowed locally (`testcontainers.reuse.enable=true`) for faster feedback loops.
- Container **reuse is disabled in CI** — every run gets a clean, isolated environment with no hidden shared state.
- The comparison (T4.3) covers: startup time, test runtime, complexity, local developer experience, CI suitability, and determinism.
- A continue/stop recommendation is documented in T4.4.

## Consequences

- **Positive:**
  - Isolated, deterministic, per-test environments — no shared state between tests.
  - Better local/CI consistency — same container version, same config, same behaviour everywhere.
  - Reduced reliance on Compose in CI (feeds into [ADR-0003](0003-reduce-compose-in-ci.md)).
  - Simpler debugging — test logs include container startup; failure is localised.
  - Selective startup — tests only start what they need, so CI minutes are spent on relevant dependencies.

- **Negative / trade-offs:**
  - Requires a working Docker runtime in CI — this is not trivial (see [ADR-0005](0005-ci-runner-docker-mode.md) for runner mode options).
  - Docker-in-Docker may be slow or restricted on shared runners (risk R3).
  - Adds a library dependency (`org.testcontainers`) to the project's test classpath.
  - First-run cold pull of container images can be slow (mitigated by image caching in CI if available).

- **Follow-ups:**
  - T4.2: assess CI suitability early — if the runner cannot provide Docker, document it and treat Testcontainers as local-only.
  - If CI is unsuitable: Compose remains in CI for integration tests ([ADR-0003](0003-reduce-compose-in-ci.md) fallback).
  - If successful: expand to more dependencies in post-pilot phase.
  - Route runner-mode decision to ACP/ETO via [ADR-0005](0005-ci-runner-docker-mode.md).

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Keep full Docker Compose for all tests | No change; familiar | Slow startup; shared state; flaky; all-or-nothing | This is the problem we're solving |
| Shared long-lived test environment (always-on containers) | Fast per-test (no startup wait) | Hidden shared state; requires coordination; not isolated | Reintroduces the determinism problem |
| Mock all external dependencies | Very fast; no Docker needed | Lower fidelity; misses real integration bugs (serialisation, timeouts, version drift) | Defeats the purpose of integration testing |
| Testcontainers for all dependencies at once | Full isolation immediately | Large refactor; higher pilot risk; hard to attribute improvements | Too much for a first pilot — start with one and expand |


---

> Source: `docs/adr/0003-reduce-compose-in-ci.md`

# ADR-0003: Reduce Docker Compose role in CI, keep it for local

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [ADR-0002 — Testcontainers](0002-testcontainers-for-integration-tests.md) · [ADR-0004 — BuildKit](0004-buildkit-cache-and-layering.md) · [ADR-0005 — CI runner mode](0005-ci-runner-docker-mode.md) · [Story 5](../stories/story-5-compose/README.md)

## Context

The pilot repository uses a single `docker-compose.yml` that serves multiple purposes:

1. **CI integration tests:** the Drone pipeline runs `docker compose up` (via `.drone.star` steps) before integration tests, starting all services regardless of what the test actually needs.
2. **Local debugging:** developers use the same file to spin up services while developing and debugging locally.
3. **Exploratory testing:** occasionally used to stand up the full stack for manual testing.

This mixed usage causes problems:
- **Unnecessary services in CI:** the Compose file may start services (e.g. a debug UI, monitoring tools) that integration tests don't touch — wasting CI minutes and adding failure surface.
- **Mixed-purpose file:** changes to support local debugging (e.g. adding a pgAdmin container) affect CI without anyone intending it.
- **Opacity:** it's unclear which services are truly required for the test suite and which are convenience tools.

[ADR-0002](0002-testcontainers-for-integration-tests.md) introduces Testcontainers as a way for tests to manage their own dependencies. Once a dependency is managed by Testcontainers, the corresponding Compose service is no longer needed in CI — but may still be useful locally.

## Decision

We will reduce Docker Compose's role in **CI** while **keeping it for local debugging**. Specifically:

1. **Validate scope** by mapping and classifying the services and invocation paths (T5.1).
2. **Decide the target role** by identifying what to retain and which changes remain evidence/ownership candidates (T5.2).

We will **not remove Compose entirely**. Docker Compose remains valuable for:
- Spinning up the full stack for local manual testing.
- Debugging scenarios that require multiple services interacting.
- Onboarding new developers who need a quick local environment.

The target model:
```
CI integration tests   → Testcontainers (isolated, deterministic)
Local manual debugging → Docker Compose (convenient, full-stack)
E2E / exploratory      → Compose or ephemeral environments (future)
```

## Consequences

- **Positive:**
  - Leaner, faster CI runs — only services the tests actually need are started.
  - Clearer separation of CI vs local concerns — changes to the local Compose file don't accidentally break CI.
  - Developers keep a familiar tool for local debugging — no workflow disruption.
  - Forces the team to document which services are actually test dependencies vs convenience tools.

- **Negative / trade-offs:**
  - Risk of breaking a hidden local workflow (risk R4) — a service assumed "not needed" turns out to be required.
  - Requires accurate service mapping and classification first (T5.1) — cannot skip straight to removal.
  - Two ways to start dependencies (Testcontainers in code, Compose on CLI) adds mental overhead until the team internalises the split.

- **Follow-ups:**
  - T5.1: map and classify before changing anything; T5.2 records the recommendation and adoption limits.
  - Change **CI usage only** in the pilot — do not change local Compose usage.
  - Document any hidden dependency discovered during mapping.
  - If a service is borderline, keep it in CI during the pilot and flag for review.

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Remove Docker Compose entirely | Simplest mental model; one way to do things | Breaks local debugging; destroys familiar workflow; high developer disruption | Too aggressive; not the goal |
| Keep Compose for everything (status quo) | No change; no disruption | Slow CI; shared state; mixed-purpose file; no isolation | This is the problem we're addressing |
| Split into two compose files (CI vs local) | Clear separation without Testcontainers | More files to maintain; still shared-state in CI; doesn't improve test isolation | Possible follow-up, but heavier than what the pilot needs |
| Use Testcontainers for everything, drop Compose | Full isolation in CI and locally | Large refactor; loss of the "full stack" local convenience | Over-rotation — Compose has legitimate local value |


---

> Source: `docs/adr/0004-buildkit-cache-and-layering.md`

# ADR-0004: Use BuildKit cache + layered multi-stage builds

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [ADR-0001 — Pilot approach](0001-pilot-not-rollout.md) · [ADR-0003 — Compose role](0003-reduce-compose-in-ci.md) · [ADR-0005 — CI runner mode](0005-ci-runner-docker-mode.md) · [Story 3](../stories/story-3-build/README.md) · [tech notes](../stories/tech-notes.md#buildkit-remote-cache) · [Drone considerations](../../examples/ci/drone-considerations.md)

> **Drone constraint:** Multi-stage builds work in any Docker environment. BuildKit cache mounts work locally but are ephemeral in Drone DIND (lost between builds). Remote registry cache requires a `.drone.star` (RepoSync) change + ACP/ETO registry namespace — this is post-pilot.

## Context

The pilot repository's Dockerfile currently follows a pattern that causes unnecessary rebuilds:

1. **Single-stage or unordered COPY:** source code and dependency metadata are copied together, so any source change invalidates the dependency-download layer. Maven downloads ~200 MB of dependencies on every build.
2. **No cache mounts:** even locally, there's no persistent Maven cache between builds — each `docker build` re-downloads the `.m2` repository.
3. **Large runtime image:** the same image that builds the application is shipped to production, including the JDK, Maven, build tools, and intermediate artefacts — resulting in ~450 MB images.
4. **No remote cache:** Drone CI pods are ephemeral. Without registry-backed cache, every CI build starts cold — no layer reuse from previous runs.

The consequence: builds are slow (~5 min CI), images are unnecessarily large, and the team waits for dependency downloads that haven't changed.

BuildKit (Docker's modern build backend) supports:
- **Cache mounts** (`--mount=type=cache`): persist the Maven repository across local builds without baking it into a layer.
- **Multi-stage builds:** separate "resolve dependencies" → "compile" → "runtime" into distinct stages. Only the final runtime stage ships to production.
- **Registry remote cache** (`--cache-from`/`--cache-to`): store cache layers in the container registry so CI runners can reuse them across jobs (requires ACP/ETO infrastructure).

## Decision

We will restructure the pilot Dockerfile using BuildKit features:

1. **Multi-stage build** with three stages:
   - `deps` stage: copy only `pom.xml` / `.mvn` / `mvnw`, resolve dependencies with a cache mount. This layer only rebuilds when dependency metadata changes.
   - `build` stage: copy source, compile the application. Only rebuilds when source changes.
   - `runtime` stage: JRE-only base image + the built JAR. No JDK, no Maven, no source — minimal attack surface and image size.

2. **BuildKit cache mounts** for the Maven local repository (`/root/.m2`), enabled for local builds immediately.

3. **Registry remote cache** (branch-aware: `--cache-from` main + current branch): documented in [tech-notes](../stories/tech-notes.md) and [drone-considerations](../../examples/ci/drone-considerations.md), but requires a RepoSync `.drone.star` change + ACP/ETO registry namespace. This is a post-pilot item (see [FUTURE-CONSIDERATIONS](../stories/FUTURE-CONSIDERATIONS.md)).

4. **Clean build must always work:** a `--no-cache` build must succeed, so cache is an optimisation, never a hard dependency (guards risk R6).

Target Dockerfile pattern:
```dockerfile
# syntax=docker/dockerfile:1
FROM amazoncorretto:17 AS deps
WORKDIR /app
COPY pom.xml .mvn mvnw ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B dependency:go-offline

FROM deps AS build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B package -DskipTests

FROM amazoncorretto:17 AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
USER 1001
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

## Consequences

- **Positive:**
  - Faster rebuilds: dependency layer cached separately — source-only changes rebuild in seconds locally.
  - Smaller runtime image: no Maven/source/build artefacts in the final stage → target ≥30% smaller than baseline.
  - Predictable CI build time: once remote cache is available, branch builds reuse the main branch cache.
  - Improved security posture: runtime image has reduced attack surface (no compiler, no Maven).

- **Negative / trade-offs:**
  - Registry remote cache requires ACP/ETO infrastructure (storage, permissions, retention policy) — deferred to post-pilot.
  - Multi-stage Dockerfiles are slightly more complex to read for developers unfamiliar with the pattern.
  - Cache mounts are BuildKit-specific — if BuildKit is disabled, the Dockerfile still works but without the cache benefit.
  - Risk R6: a corrupt or stale cache could theoretically produce an incorrect image — mitigated by the "clean build must work" rule and T3.4 verification.

- **Follow-ups:**
  - T3.3: apply one layering change at a time and measure (not a full rewrite at once).
  - T3.4: compare before/after locally and in CI; verify a `--no-cache` build still succeeds.
  - Story 6 / T6.1: classify and route remote-cache infrastructure to RepoSync/platform or the relevant wider owner.
  - Post-pilot: ACP/ETO provisions cache namespace; request RepoSync change to add `--cache-from`/`--cache-to` to the Drone build step.

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Single-stage, copy-all Dockerfile (status quo) | Simple; everyone understands it | No layer caching; large images; every change triggers full rebuild | This is the current pain point |
| Cache mounts only, no multi-stage | Partial speedup for dep downloads | Runtime image still ships JDK + build tools; no size reduction | Leaves the image-size problem unsolved |
| Pre-built dependency image (build deps baked into a base) | Very fast builds; no dep resolution at all | Governance overhead; must rebuild when deps change; another image to maintain | Heavier than pilot scope — possible future platform item |
| Kaniko (daemonless build) | No Docker daemon needed in CI | Less mature BuildKit features; no cache mounts; limited multi-stage support | BuildKit is the standard; Kaniko is a workaround for environments without Docker |


---

> Source: `docs/adr/0005-ci-runner-docker-mode.md`

# ADR-0005: CI runner Docker execution mode (Drone Kubernetes + DIND)

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** CST + ACP/ETO (RepoSync pipeline owner)
- **Related:** [ADR-0001 — Pilot approach](0001-pilot-not-rollout.md) · [ADR-0002 — Testcontainers](0002-testcontainers-for-integration-tests.md) · [ADR-0004 — BuildKit](0004-buildkit-cache-and-layering.md) · [PROJECT-PLAN.md — R3](../../PROJECT-PLAN.md) · [T1.4](../stories/story-1-pipeline-assessment/task-4-testcontainers-feasibility.md) · [Drone considerations](../../examples/ci/drone-considerations.md)

## Context

The FDP CI pipeline runs on **Drone with Kubernetes runner**. Docker access is provided via a **Docker-in-Docker (DIND) service** named `docker`, accessible at `tcp://docker:2375`.

The pipeline is defined in `.drone.star` (Starlark) and **centrally managed via RepoSync** — local changes to the pipeline config are not durable, so accepted reusable changes need to go through the normal ACP/RepoSync process.

Key observations from the current `.drone.star`:
1. A DIND service is added to every pipeline that needs Docker.
2. Steps that need Docker set `DOCKER_HOST=tcp://docker:2375`.
3. The ECR pipeline's Maven step already sets `TESTCONTAINERS_RYUK_DISABLED=true` — indicating prior Testcontainers exploration.
4. The main CI pipeline's `mvn clean install` step does **not** currently have `DOCKER_HOST` set.

For Testcontainers to work in CI:
- The Maven test step needs `DOCKER_HOST=tcp://docker:2375` (to reach DIND).
- Ryuk must be disabled (`TESTCONTAINERS_RYUK_DISABLED=true`) — Ryuk cannot reliably connect to the Drone DIND daemon.
- Pre-flight checks should be skipped (`TESTCONTAINERS_CHECKS_DISABLE=true`).

These are **environment variable changes in `.drone.star`** — controlled by RepoSync, not the adaptor repo.

## Decision

The current Drone setup provides DIND. We will assess its suitability for Testcontainers in T1.4 and select the appropriate execution model:

- **Preferred (if feasible):** Add `DOCKER_HOST`, `TESTCONTAINERS_RYUK_DISABLED=true`, and `TESTCONTAINERS_CHECKS_DISABLE=true` to the Maven step in `.drone.star` via a RepoSync change request. Testcontainers then runs inside the existing CI pipeline.
- **Fallback (per ADR-0002):** If the RepoSync change is not approved or DIND connectivity doesn't work, Testcontainers runs **locally only**; Docker Compose remains in CI.

The final decision is documented in T1.4 findings and carried into Story 4 and Story 6.

## Consequences

- **Positive:**
  - Uses the existing DIND service — no new infrastructure required.
  - Ephemeral Kubernetes pods mean containers die with the pipeline (no orphan cleanup needed even without Ryuk).
  - Prior art exists (`TESTCONTAINERS_RYUK_DISABLED=true` in ECR pipeline) — precedent for the change.

- **Negative / trade-offs:**
  - Requires a RepoSync change request — not CST-local.
  - Ryuk disabled means no automatic cleanup mid-pipeline (acceptable because pods are ephemeral).
  - DIND adds network hop latency for container operations (may be slower than host Docker).
  - If `DOCKER_HOST` is not set in the Maven step, Testcontainers defaults to looking for a local socket (which doesn't exist in the pod).

- **Follow-ups:**
  - T1.4: confirm DIND connectivity from Maven step.
  - If feasible: submit RepoSync change request with env vars.
  - T6.1: classify as a RepoSync/platform-owned change.
  - Document the workaround for the team (env vars needed in CI vs local).

## Alternatives considered

| Option | Pros | Cons | Why not chosen (default) |
|--------|------|------|--------------------------|
| Docker-in-Docker (`--privileged`) | Widely documented; fully isolated daemon | `--privileged` = security risk on shared runners; slow startup | Acceptable only on a dedicated runner tag |
| Docker socket mount | No `--privileged` on job; reuses host daemon | Grants root-equivalent host access | Preferred if security posture allows; confirm with ACP/ETO |
| Rootless Docker / Sysbox | Secure; no host privilege escalation | Requires specific kernel/runner setup | Assess in T1.4; not assumed available |
| No Docker in CI (fallback) | No privilege concerns | No Testcontainers in CI; Compose remains | Valid fallback per ADR-0002 — not ideal but acceptable |


---

> Source: `docs/adr/template.md`

# ADR-NNNN: <short title of the decision>

- **Status:** Proposed | Accepted | Superseded by ADR-XXXX | Deprecated
- **Date:** YYYY-MM-DD
- **Deciders:** <names / roles>
- **Related:** <story / task / ADR links>

## Context
What is the situation and the forces at play? What problem or question forced a decision? Keep it factual.

## Decision
The decision, stated in active voice: "We will …".

## Consequences
What becomes easier and what becomes harder as a result. Include trade-offs, risks, and follow-up actions.

- **Positive:**
- **Negative / trade-offs:**
- **Follow-ups:**

## Alternatives considered
| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| | | | |


---

> Source: `examples/README.md`

# Examples

Reference code samples based on the **real FDP project structure**. These match the actual live environment (fdp-cmd-adaptor-dvla, docker-compose via RepoSync, Maven multi-module).

Copy, adapt, and rename as needed when applying to the pilot repository.

## Real project context

| Property | Value |
|----------|-------|
| Group ID | `uk.gov.ho.dacc.fdp` |
| Artifact | `fdp-cmd-adaptor-dvla` |
| Java | 17 (Amazon Corretto) |
| Build | Maven multi-module (parent + 4 modules) |
| Kafka | Confluent cp-kafka 7.5.5 (matches MSK 3.5.1 prod) |
| Schema Registry | cp-schema-registry 7.5.5 |
| Redis | 5.0.6 |
| LocalStack | 0.12.18 (IAM only) |
| Tracing | OpenTelemetry + Jaeger |
| Registry | docker.digital.homeoffice.gov.uk |
| CI runner image | quay.io/ukhomeofficedigital/ileap-java17-mvn:1.3 |
| Test framework | Cucumber + JUnit 4 (vintage) + JUnit 5 Platform |
| Integration test orchestration | docker-compose-maven-plugin (currently) |

## Contents

### Testcontainers + Cucumber + Spring Boot

| File | What it shows |
|------|---------------|
| [pom-dependencies.xml](testcontainers/pom-dependencies.xml) | What to add to the existing pom.xml (minimal — most deps already exist) |
| [TestcontainersBaseIT.java](testcontainers/TestcontainersBaseIT.java) | Cucumber runner (JUnit 4 @RunWith style, matching existing FDP pattern) |
| [CucumberSpringConfig.java](testcontainers/CucumberSpringConfig.java) | Spring Boot ↔ Testcontainers glue with FDP-specific properties (fdp.kafka.broker, fdp.app.redis.nodes, etc.) |
| [RedisContainerConfig.java](testcontainers/RedisContainerConfig.java) | Redis 5.0.6 container (simplest candidate for T4.1) |
| [KafkaContainerConfig.java](testcontainers/KafkaContainerConfig.java) | Zookeeper + Kafka + Schema Registry (cp-7.5.5, matching production MSK) |
| [LocalStackContainerConfig.java](testcontainers/LocalStackContainerConfig.java) | LocalStack (IAM) — if chosen as candidate |

### Docker (build optimisation)

| File | What it shows |
|------|---------------|
| [Dockerfile](docker/Dockerfile) | Optimised multi-stage build (current single-stage amazoncorretto:17 → 3 stages with cache mounts) |
| [docker-compose.yml](docker/docker-compose.yml) | Infrastructure services only (mirrors real RepoSync-controlled compose, without FDP app services) |
| [.dockerignore](docker/.dockerignore) | Lean build context for Java/Maven multi-module project |

### CI/CD (GitLab — illustrative only)

> **Note:** The real FDP CI uses **Drone** (`.drone.star` via RepoSync), not GitLab CI. These snippets show how integration tests *would* look in a GitLab CI context. For Drone, see [drone-considerations.md](ci/drone-considerations.md).

| File | What it shows |
|------|---------------|
| [gitlab-ci-integration-test.yml](ci/gitlab-ci-integration-test.yml) | Two jobs: Testcontainers mode (`-P testcontainers`) + Compose fallback (`-P ci-cmd`) — illustrative |
| [drone-considerations.md](ci/drone-considerations.md) | How Testcontainers/BuildKit would work in the real Drone pipeline |

## How to apply

1. **Add Testcontainers BOM** to parent pom.xml `<dependencyManagement>` (see pom-dependencies.xml)
2. **Add 3 dependencies** to `cmd-adaptor-dvla-integration-tests/pom.xml` (testcontainers, junit-jupiter, kafka)
3. **Add `testcontainers` Maven profile** (skips docker-compose-maven-plugin)
4. **Copy container configs** (RedisContainerConfig, KafkaContainerConfig) to `src/test/java/`
5. **Copy CucumberSpringConfig** (or merge into existing Spring test config)
6. **Run locally:** `./mvnw verify -pl cmd-adaptor-dvla-integration-tests -P testcontainers`
7. **Compare (T4.3):** same tests, Testcontainers vs `-P ci-cmd`, measure timing


---

> Source: `examples/ci/drone-considerations.md`

# Drone CI — Testcontainers & BuildKit Considerations

How the pilot proposals would work within the real FDP Drone pipeline.

> **Key constraint:** The `.drone.star` is centrally managed via RepoSync. Changes to pipeline steps, services, or environment variables require a RepoSync change request — not a local repo commit.

---

## Current pipeline structure (from .drone.star)

```text
Pipeline type: Kubernetes
DIND service: docker (tcp://docker:2375)

CI Pipeline steps:
1. RepoSync Version
2. Retrieve Artifactory Secrets
3. Wait for Docker
4. Extract Adaptor Information
5. Kafka & Redis (docker-compose up)
6. Aggregators (docker-compose up -d)
7. mvn clean install
8. Command Adaptor (docker-compose up --build)
9. Pre-Integration Tests (docker-compose up, wait checks)
10. Integration Tests (docker-compose up --exit-code-from)
11. Sonar Scan
12. Scan with Trivy
13. Slack notifications
```

---

## Testcontainers in Drone

### What needs to happen

For Testcontainers to work in the `mvn clean install` step (or a new Maven step):

```yaml
# These environment variables must be added to the Maven step:
environment:
  DOCKER_HOST: tcp://docker:2375
  TESTCONTAINERS_RYUK_DISABLED: "true"    # Ryuk not compatible with Drone
  TESTCONTAINERS_CHECKS_DISABLE: "true"   # Skip pre-flight checks
```

### Why Ryuk must be disabled

Ryuk is a Testcontainers helper container that cleans up other containers. In Drone's Kubernetes pipeline model, Ryuk cannot connect to the DIND daemon reliably. The ECR pipeline already has this workaround.

**Implication:** Without Ryuk, container cleanup is the responsibility of the pipeline. Since Drone pipelines are ephemeral (pod is destroyed after the pipeline), this is acceptable — containers die with the pod.

### Where this change lives

This is a **RepoSync-controlled change** — the Maven step environment in `.drone.star` must be modified. It cannot be done in the adaptor repo.

### Fallback (from ADR-0002)

If Drone CI execution is not feasible or the RepoSync change is not approved:
- Testcontainers runs **locally only** (developer machines)
- Docker Compose remains the CI integration test mechanism
- The pilot still demonstrates the pattern and local developer experience improvement

---

## BuildKit in Drone

### Multi-stage builds

**Works today** — `docker build` with multi-stage Dockerfiles is standard Docker behaviour. No DIND or pipeline change needed. The existing `docker build -f Dockerfile` step already supports this.

### BuildKit cache mounts (`--mount=type=cache`)

**Works per-build** — `DOCKER_BUILDKIT=1` enables cache mounts. But since DIND is ephemeral per pipeline, the cache is lost between builds. Still useful for multi-stage builds within a single pipeline run (deps stage → build stage).

To enable:
```yaml
# Add to the docker build step environment:
environment:
  DOCKER_BUILDKIT: "1"
```

This is a **RepoSync-controlled change** (environment variable in `.drone.star`).

### Remote registry cache (`--cache-from` / `--cache-to`)

**Requires ACP/ETO:**
- Registry namespace for cache layers (e.g. `docker.digital.homeoffice.gov.uk/dacc-aws/fdp-cache`)
- Write permissions for the Drone pipeline to push cache
- Retention/eviction policy for cache layers
- `.drone.star` change to add `--cache-from` / `--cache-to` flags

This is **post-pilot** — classify in Story 6 as RepoSync/platform or wider ETO, depending on who owns the cache namespace and registry policy.

---

## What CST can do locally (no RepoSync change)

| Action | Works locally | Works in CI |
|--------|:------------:|:-----------:|
| Multi-stage Dockerfile | ✅ | ✅ (standard Docker) |
| `.dockerignore` | ✅ | ✅ |
| BuildKit cache mounts | ✅ (persistent) | ⚠️ (ephemeral per build) |
| Testcontainers tests | ✅ | ❓ (needs T1.4 confirmation) |
| Maven `-P testcontainers` profile | ✅ | ❓ (needs RepoSync change to skip compose) |
| Remote registry cache | ❌ | ❌ (needs ACP/ETO) |

---

## Recommended approach for the pilot

1. **Story 1:** confirm T1.4 (Testcontainers) and T1.5 (BuildKit) feasibility
2. **Story 3:** apply Dockerfile optimisation locally; measure local before/after; CI benefit comes from multi-stage (no special config) and `.dockerignore` (reduces context sent to DIND)
3. **Story 4:** prototype Testcontainers locally; if T1.4 confirms CI feasibility, request RepoSync change to add DOCKER_HOST + RYUK env vars to Maven step
4. **Story 6:** document what was local vs what needs RepoSync/platform action
