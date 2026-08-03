# Container & CI/CD Optimisation Pilot

> **Status:** In progress — pilot evidence prepared; T6.2 owner review, stakeholder share-out and dispositions are not evidenced.
> **Scope:** FDP as the pilot context; patterns may be reusable more widely if proven.
> **Intent:** Validate a few optimisation ideas through a small, measurable pilot before any wider rollout.

**Key message:** the difference is not Docker vs no Docker — it's *optimised, standardised, cached and test-driven* Docker usage.

> **Repository type:** this repository is a planning and template pack, not the selected application repository. Root-level files (`Dockerfile`, `docker-compose.yml`, `scripts/measure-baseline.sh`) are starting templates to copy/adapt after T2.1 selects a pilot repo with application sources (`pom.xml`, `mvnw`, `.mvn/`, `src/`).

## Pilot Closure Position

The optimisation pilot has established current-state evidence, validated bounded local experiments, recorded limitations and non-claims, and identified ownership and adoption routes. No additional implementation story will be added to this epic.

The epic is not marked `Done` because [T6.2](solution/story-6/T6.2-decide-adoption-route.md) does not yet record stakeholder/owner review, a share-out date, agreed dispositions or routed next actions. These details will not be fabricated. The exact result and missing evidence are captured in the [pilot closure record](docs/PILOT-CLOSURE.md).

Validated SNS productionisation and the independent CD pipeline review/design are tracked as separate stories in a [follow-up delivery epic](docs/epics/INDEX.md), not as Story 7.

---

## Start Here

| Need | Go to |
|------|-------|
| Understand the pilot quickly | This README |
| Review the pilot closure evidence and blockers | [Pilot closure record](docs/PILOT-CLOSURE.md) |
| Track live task progress | [Status board](docs/epics/epic-1-pivot-study/stories/STATUS-BOARD.md) |
| See every story and task | [Backlog index](docs/epics/epic-1-pivot-study/stories/INDEX.md) |
| See pilot and post-pilot epic separation | [Delivery epic index](docs/epics/INDEX.md) |
| Understand why this matters | [Project context](docs/PROJECT-CONTEXT.md) |
| Understand Drone / RepoSync constraints | [Pipeline context](docs/PIPELINE-CONTEXT.md) |
| Check in/out of scope and deferred work | [Scope and guardrails](docs/SCOPE-AND-GUARDRAILS.md) |
| Follow the timeline, risks and verification plan | [Project plan](PROJECT-PLAN.md) |

**Audience shortcuts:** developers usually start with the [backlog index](docs/epics/epic-1-pivot-study/stories/INDEX.md), [status board](docs/epics/epic-1-pivot-study/stories/STATUS-BOARD.md), [technical notes](docs/epics/epic-1-pivot-study/stories/tech-notes.md), and [ADRs](docs/adr/README.md). Ops/platform readers should also review the [security plan](SECURITY.md), [pipeline context](docs/PIPELINE-CONTEXT.md), and Story 6 ownership classification.

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

Targets are proposed and confirmed against the real baseline in Story 2. Detailed measurement fields live in the [metrics template](docs/epics/epic-1-pivot-study/stories/metrics-template.md).

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
| 1 | [Pipeline Assessment (Drone/RepoSync)](docs/epics/epic-1-pivot-study/stories/story-1-pipeline-assessment/README.md) | 5 | — | — |
| 2 | [Baseline & Pilot Scope](docs/epics/epic-1-pivot-study/stories/story-2-baseline/README.md) | 4 | 1 | — |
| 3 | [Docker Build Optimisation](docs/epics/epic-1-pivot-study/stories/story-3-build/README.md) | 4 | 2 | 4 |
| 4 | [Testcontainers Pilot](docs/epics/epic-1-pivot-study/stories/story-4-testcontainers/README.md) | 4 | 2 | 3 |
| 5 | [Docker Compose Rationalisation](docs/epics/epic-1-pivot-study/stories/story-5-compose/README.md) | 2 | 4 | — |
| 6 | [Pilot Outcome, Ownership and Adoption](docs/epics/epic-1-pivot-study/stories/story-6-findings/README.md) | 2 | 3, 4, 5 | — |

```text
Story 1 (pipeline assessment, gate)
   └──> Story 2 (baseline, gate)
           ├──> Story 3 ─┐
           └──> Story 4 ─┼──> Story 5
                         └──> Story 6
```

Progress is tracked in the [status board](docs/epics/epic-1-pivot-study/stories/STATUS-BOARD.md). The [backlog index](docs/epics/epic-1-pivot-study/stories/INDEX.md) is the compact list of story/task files.

## Post-Pilot Delivery Epic

| Epic | Workstreams | Status |
|---|---|---|
| [SNS Delivery Pipeline Optimisation](docs/epics/epic-2-sns-delivery-pipeline-optimisation/README.md) | Two independent stories: validated SNS productionisation and CD current-state review/target planning | Proposed / New |

```text
Container & CI/CD Optimisation Pilot (evidence source; T6.2 closure pending)
   └──> Epic 2 — SNS Delivery Pipeline Optimisation
           ├── E2-S1 — Validated SNS productionisation
           └── E2-S2 — CD review and target planning
```

The delivery epic may cite pilot evidence but is not an additional story within the pilot.

---

## Documentation Map

| Document | Purpose |
|----------|---------|
| [Project context](docs/PROJECT-CONTEXT.md) | Background, current state, business impact, technology stack |
| [Pilot closure record](docs/PILOT-CLOSURE.md) | Final evidence boundaries, missing T6.2 closure record and follow-up destinations |
| [Pipeline context](docs/PIPELINE-CONTEXT.md) | Drone/RepoSync constraint and CI vs deploy pipeline boundary |
| [Scope and guardrails](docs/SCOPE-AND-GUARDRAILS.md) | Pilot scope, assumptions, open questions, deferred work |
| [Project plan](PROJECT-PLAN.md) | Timeline, risk register, branching, test strategy |
| [Status board](docs/epics/epic-1-pivot-study/stories/STATUS-BOARD.md) | Single source of truth for task status |
| [Backlog index](docs/epics/epic-1-pivot-study/stories/INDEX.md) | Story/task outline with links |
| [Delivery epic index](docs/epics/INDEX.md) | Pilot-to-delivery separation, follow-up epic and transition routes |
| [Security plan](SECURITY.md) | Secret handling, scanning, supply-chain hardening |
| [Technical notes](docs/epics/epic-1-pivot-study/stories/tech-notes.md) | Base images, BuildKit, Testcontainers and security references |
| [ADRs](docs/adr/README.md) | Architecture decisions and trade-offs |
| [Examples](examples/README.md) | Docker, CI and Testcontainers examples |
| [Glossary](docs/glossary.md) | FDP/CST/ETO/Drone terminology |
| [Contributing](CONTRIBUTING.md) | How to work with the backlog |
