# Container & CI/CD Optimisation Pilot

> **Status:** Pilot planning — not an approved implementation programme.
> **Scope:** FDP as the pilot context; patterns may be reusable more widely if proven.
> **Intent:** Validate a few optimisation ideas through a small, measurable pilot before any wider rollout.

**Key message:** the difference is not Docker vs no Docker — it's *optimised, standardised, cached and test-driven* Docker usage.

> **Repository type:** this repository is a planning and template pack, not the selected application repository. Root-level files (`Dockerfile`, `docker-compose.yml`, `scripts/measure-baseline.sh`) are starting templates to copy/adapt after T1.1 selects a pilot repo with application sources (`pom.xml`, `mvnw`, `.mvn/`, `src/`).

---

## Audience

| Reader | What to look at |
|--------|-----------------|
| **Developers / engineers** | [Backlog index](docs/stories/INDEX.md), [tech notes](docs/stories/tech-notes.md), [ADRs](docs/adr/README.md), [CONTRIBUTING](CONTRIBUTING.md) |
| **Ops / platform (ETO)** | [Security plan](SECURITY.md), [tech notes](docs/stories/tech-notes.md), ownership classification (Story 5) |
| **Leads / managers** | This README (goals, success targets), [project plan](PROJECT-PLAN.md) (timeline, risks) |

> **Glossary:** **FDP** = the product/team context this pilot runs in. **CST** = the local team that can own and validate changes directly. **ETO / platform** = the wider engineering/platform org that owns shared infrastructure (base images, CI templates, cache infra).

---

## Background — why this pilot

CI/CD and container workflows create recurring friction as projects grow. The concrete pain points behind this pilot:

- **Long build times** — repeated dependency downloads, poor layer caching, large build contexts.
- **Heavy integration-test setup** — full Docker Compose stacks are slow to start and share hidden state.
- **Flaky, environment-dependent tests** — failures that depend on local vs CI environment differences.
- **Inconsistent Dockerfiles** across repositories, with no shared base-image strategy.
- **Unclear ownership** — some improvements are local to CST, others need platform/ETO.

> Concrete baseline numbers (build time, image size, flaky rate) are **not assumed** — capturing them is the first story ([Story 1](docs/stories/story-1-baseline/README.md)). Until then, the pain points above are qualitative.

## Current state (to be confirmed in Story 1)

These are **placeholder estimates** based on initial observations. Exact values will be captured in T1.2–T1.4 and recorded in the [metrics template](docs/stories/metrics-template.md).

| Metric | Estimated current state | Target (pilot) |
|--------|-------------------------|----------------|
| Pipeline duration (avg) | ~12 min | < 10 min (≥ 20% ↓) |
| Final image size | ~450 MB | < 380 MB (≥ 15% ↓) |
| Docker build time (CI) | ~5 min | < 4 min (≥ 20% ↓) |
| Integration test startup | ~90 sec | < 60 sec |
| Flaky / failed pipeline rate | ~5% | < 2% |
| Developer feedback loop (local change → test green) | ~8 min | < 5 min |

> These numbers will be **replaced with real data** once Story 1 is complete. They exist here to make the ambition visible.

### Business impact (estimated)

These translate the technical gains into terms stakeholders care about:

- **Developer productivity:** if a developer hits the pipeline ~8×/day, a 3-min reduction = **~24 min saved per developer per day**. For a team of 5, that's **~2 hours/day** back into delivery.
- **CI cost:** fewer runner minutes per pipeline × number of daily pipelines = direct infrastructure cost reduction (quantify against Drone runner capacity once baseline is captured).
- **Security posture:** fewer flaky failures = faster, more reliable deployments = security patches applied sooner. In a border-security context, delayed patches carry real risk.

## Approach — how we tackle it

A small, measurable pilot on **one** representative repository:

1. **Baseline** the current state so every change is provable.
2. **Optimise the Docker build** (layering, `.dockerignore`, cache mounts) and measure the delta.
3. **Pilot Testcontainers** for one integration dependency for better isolation/determinism.
4. **Rationalise Docker Compose** — keep it for local debugging, reduce its role in CI.
5. **Consolidate findings** and classify each pattern as CST-local vs platform/ETO.

## Drone / RepoSync — core constraint

The FDP adaptor repositories use a **centrally managed `.drone.star`** pipeline (Starlark), deployed via **RepoSync**. Local changes to the pipeline config are overwritten.

This means:
- **Pipeline-level changes** (stage ordering, DIND image, BuildKit enabling, Testcontainers environment) **cannot be made locally** — they require RepoSync / platform / ETO coordination.
- **Repository-level changes** (Dockerfile, `.dockerignore`, Maven profiles, test code) **can be made locally** within the pilot scope.
- The CI pipeline uses a **Kubernetes runner** with a **Docker-in-Docker service** (`DOCKER_HOST=tcp://docker:2375`).
- Docker Compose is the current **CI integration test orchestration** method (Kafka, Redis, Schema Registry, aggregators, command adaptor all started via compose).
- `TESTCONTAINERS_RYUK_DISABLED=true` already appears in one Maven step (ECR pipeline) — indicating prior Testcontainers exploration and a known Drone/Ryuk compatibility constraint.
- Pull request events appear to trigger only a minimal/blank pipeline — to be confirmed in Story 0.

**Consequence for the pilot:** Story 0 (Pipeline Assessment) must be completed first to establish what is locally feasible vs what requires central discussion.

---

## Technology stack

| Area | Tooling |
|------|---------|
| Containers | Docker, BuildKit / `docker buildx` (feasibility TBC), multi-stage builds |
| CI/CD | **Drone CI** (Kubernetes runner, `.drone.star` via RepoSync — centrally managed) |
| Source hosting | GitLab (`gitlab.digital.homeoffice.gov.uk`) |
| Registry | `docker.digital.homeoffice.gov.uk` (internal), ECR, Artifactory |
| Integration testing | Testcontainers (Java, pilot); existing Docker Compose + DIND for comparison |
| Build / deps | Maven (`mvnw`), Maven cache mounts |
| Candidate test deps | Redis, Kafka, Schema Registry, LocalStack (IAM) |
| Tracing | OpenTelemetry + Jaeger |
| Security | Trivy (scanning in pipeline), SBOM (Syft), secret mounts, Drone secrets — see [SECURITY.md](SECURITY.md) |

---

## Epic

**Pilot Container & CI/CD Optimisation — FDP Initial Scope** — validate selected build and integration-testing improvements with before/after evidence, then identify reusable patterns and their owners (CST vs platform/ETO).

**Out of scope:** org-wide rollout, removing all Compose, building shared platform capabilities, guaranteeing a specific speedup beyond the pilot targets below.

### Success criteria & targets

Targets are **proposed** and confirmed against the real baseline in Story 1.

| Success criterion | Target | Measured |
|-------------------|--------|----------|
| Pilot repo baselined | All baseline metrics captured | Once, in Story 1 |
| Build optimisation measured | **≥ 20%** reduction in Docker build time | Before vs after (T2.4) |
| Image size reduced | **≥ 15%** smaller final image | Before vs after (T2.4) |
| Pipeline duration improved | **≥ 20%** reduction (stretch; depends on cache infra) | Per-pipeline avg over last N runs |
| Testcontainers validated | ≥ 1 dependency running + compared to Compose | Before vs after (T3.3) |
| Integration test reliability | No new flakiness; isolation improved | Across pilot test runs |
| Ownership documented | Every item classified CST vs platform/ETO | Once, in Story 5 |

**Measurement cadence:** build/image metrics on **every pilot build** (before/after pairs); pipeline duration as a **rolling average over the last N runs** (N agreed in T1.2); a **weekly** snapshot during the pilot to track trend. All numbers go into the [metrics template](docs/stories/metrics-template.md).

---

## Stories

| # | Story | Tasks | Depends on | Parallel with |
|---|-------|:-----:|------------|----------------|
| 0 | [Pipeline Assessment (Drone/RepoSync)](docs/stories/story-0-pipeline-assessment/README.md) | 5 | — | — |
| 1 | [Baseline & Pilot Scope](docs/stories/story-1-baseline/README.md) | 4 | 0 | — |
| 2 | [Docker Build Optimisation](docs/stories/story-2-build/README.md) | 4 | 1 | 3 |
| 3 | [Testcontainers Pilot](docs/stories/story-3-testcontainers/README.md) | 4 | 1 | 2 |
| 4 | [Docker Compose Rationalisation](docs/stories/story-4-compose/README.md) | 3 | 3 | — |
| 5 | [Findings, Ownership & Recommendations](docs/stories/story-5-findings/README.md) | 3 | 2, 3, 4 | — |

```text
Story 0 (pipeline assessment, gate)
   └──> Story 1 (baseline, gate)
           ├──> Story 2 ─┐
           └──> Story 3 ─┼──> Story 4
                         └──> Story 5
```

📋 [**Full backlog index**](docs/stories/INDEX.md) — all stories and task titles on one page.

**More docs:** [Project plan & timeline](PROJECT-PLAN.md) · [Security plan](SECURITY.md) · [Architecture decisions (ADR)](docs/adr/README.md) · [Technical notes](docs/stories/tech-notes.md) · [Definition of Done](docs/stories/DEFINITION-OF-DONE.md) · [Future considerations](docs/stories/FUTURE-CONSIDERATIONS.md) · [Example code](examples/README.md) · [Glossary](docs/glossary.md) · [How to contribute](CONTRIBUTING.md)

---

## Status board

> **Note:** The backlog below is a **candidate structure only**. Individual tickets should not be created until priority, ownership and target board are agreed. The purpose is to support review and prioritisation — not to imply that every task will be implemented immediately.

Single source of truth for progress. Update the **Status** column as work moves; status-looking metadata in story/task files is only an initial planning snapshot.
Estimates: `S` ≤0.5d · `M` 0.5–1d · `L` 1–2d. Priority: MoSCoW.

> **Tickets:** Confirm the tracker in T1.1 (default: GitLab issues/MRs for GitLab-hosted pilot repos; use GitHub only if the pilot repo is GitHub-hosted). Add links to the `Issue` column below as work is picked up. Issue creation order: Epic → S1 → T1.1 → T1.2 → S2 → T2.1 (see [CONTRIBUTING.md](CONTRIBUTING.md)).

| ID | Item | Est | Priority | Status | Owner | Issue |
|----|------|:---:|:--------:|--------|-------|-------|
| **S0** | **Pipeline Assessment (Drone/RepoSync)** | — | Must | Not started | _TBD_ | — |
| T0.1 | Review .drone.star pipeline structure | M | Must | Not started | _TBD_ | — |
| T0.2 | Identify local vs RepoSync boundaries | S | Must | Not started | _TBD_ | — |
| T0.3 | Map CI steps, DIND and Compose usage | M | Must | Not started | _TBD_ | — |
| T0.4 | Assess Testcontainers feasibility in Drone | M | Must | Not started | _TBD_ | — |
| T0.5 | Assess BuildKit/cache feasibility | S | Should | Not started | _TBD_ | — |
| **S1** | **Baseline & Pilot Scope** | — | Must | Not started | _TBD_ | — |
| T1.1 | Select pilot repository/service | S | Must | Not started | _TBD_ | — |
| T1.2 | Capture CI/CD pipeline baseline | M | Must | Not started | _TBD_ | — |
| T1.3 | Capture Docker build & image-size baseline | S | Must | Not started | _TBD_ | — |
| T1.4 | Capture integration-test baseline | M | Must | Not started | _TBD_ | — |
| **S2** | **Docker Build Optimisation** | — | Must | Not started | _TBD_ | — |
| T2.1 | Review current Dockerfile & build context | M | Must | Not started | _TBD_ | — |
| T2.2 | Add or validate .dockerignore | S | Must | Not started | _TBD_ | — |
| T2.3 | Apply Dockerfile layering / cache improvement | M | Must | Not started | _TBD_ | — |
| T2.4 | Measure local & CI build impact | M | Should | Not started | _TBD_ | — |
| **S3** | **Testcontainers Pilot** | — | Must | Not started | _TBD_ | — |
| T3.1 | Select candidate dependency/test | S | Must | Not started | _TBD_ | — |
| T3.2 | Implement Testcontainers setup | L | Must | Not started | _TBD_ | — |
| T3.3 | Compare with docker-compose flow | M | Should | Not started | _TBD_ | — |
| T3.4 | Document findings & constraints | S | Should | Not started | _TBD_ | — |
| **S4** | **Docker Compose Rationalisation** | — | Should | Not started | _TBD_ | — |
| T4.1 | Map services started by docker-compose | S | Must | Not started | _TBD_ | — |
| T4.2 | Classify services & usage | M | Must | Not started | _TBD_ | — |
| T4.3 | Recommend reduced Compose role | M | Should | Not started | _TBD_ | — |
| **S5** | **Findings, Ownership & Recommendations** | — | Must | Not started | _TBD_ | — |
| T5.1 | Consolidate pilot findings | M | Must | Not started | _TBD_ | — |
| T5.2 | Classify ownership & recommend target board | M | Must | Not started | _TBD_ | — |
| T5.3 | Share findings with stakeholders | S | Should | Not started | _TBD_ | — |

---

## Risks (summary)

Top risks only — the full risk register with fallback plans lives in the [project plan](PROJECT-PLAN.md#risk-register).

| # | Risk | Mitigation / fallback |
|---|------|-----------------------|
| R1 | Pilot repo selection slips | Time-box selection; agree criteria in T1.1 |
| R3 | Testcontainers too slow / unsupported in CI runners | **Fallback:** keep docker-compose in CI; treat Testcontainers as local-only |
| R4 | Reducing Compose breaks a local workflow | Change CI usage only; keep Compose for local debugging |

---

## How to use this backlog

See [CONTRIBUTING.md](CONTRIBUTING.md) for structure, conventions, and the task workflow. In short:

- **Browse** from the [backlog index](docs/stories/INDEX.md) → story → task.
- **Track progress** only in the [status board](#status-board) — it's the single source of truth.
- **Close a task** when its acceptance criteria **and** the shared [Definition of Done](docs/stories/DEFINITION-OF-DONE.md) are met.

### Ticket-creation order

Create incrementally — not all at once:

1. Epic
2. Story 1 → T1.1 (select repo) → T1.2 (pipeline baseline)
3. Story 2 → T2.1 (review Dockerfile)

Open the rest once the baseline and a first build review are underway.

---

## Immediate pilot scope

The initial pilot should remain small and measurable.

**CST-local (can do in the repo without RepoSync changes):**
- Baseline measurement (pipeline timing from Drone UI, Docker build locally)
- Pilot repository/service selection
- Dockerfile / build context review
- `.dockerignore` validation
- Dockerfile layering experiment (local build)
- Local Testcontainers prototype (runs on developer machine)
- Docker Compose service mapping and classification
- CST-local vs platform/ETO ownership assessment

**Requires central/platform coordination (RepoSync / ETO):**
- Drone pipeline step changes (`.drone.star`)
- CI-level Testcontainers execution (DIND env vars, Ryuk config)
- BuildKit / `docker buildx` enabling in Drone DIND
- Remote cache infrastructure (registry namespace, permissions)
- DIND image changes
- Shared base image adoption across adaptors

**Not in initial pilot scope** (unless separately agreed):
- Organisation-wide base image rollout
- BuildKit remote cache rollout
- Pre-built test image rollout
- Ephemeral environment implementation
- Full platform transformation programme
- Organisation-wide Drone pipeline template changes
- Shared Testcontainers library implementation
- Replacing all Docker Compose usage

---

## Assumptions

- The first pilot will use **one** selected repository/service.
- Baseline metrics will be captured **before** any implementation changes.
- Any platform-impacting work will be reviewed with relevant platform/ETO stakeholders.
- Docker Compose will not be removed without understanding current CI and local debugging usage.
- Testcontainers will be piloted with one dependency first before wider migration is considered.
- Projected benefits will not be treated as guaranteed until measured.
- The pilot is part-time work (~4 weeks), not a full-time dedicated programme.

---

## Decision points

Before creating detailed implementation tickets, the following decisions should be agreed:

1. Which repository/service should be used as the pilot?
2. Which metrics should be captured as the baseline and how (data source, N runs)?
3. Which Dockerfile/build optimisation should be tested first?
4. Which integration dependency should be used for the first Testcontainers pilot?
5. Which items can stay on the CST board?
6. Which items need platform/ETO visibility or ownership?
7. What success criteria must be met before considering wider adoption?

---

## Open questions

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

## Recommended first local changes

The first local changes should be small and low-risk:

1. Add or validate `.dockerignore` (T2.2 — minimal effort, immediate context-size reduction)
2. Capture current Docker build timing with `scripts/measure-baseline.sh`
3. Review Dockerfile layer ordering (T2.1)
4. Propose one Dockerfile cache optimisation (T2.3)
5. Measure local build before/after (T2.4)
6. Identify one candidate integration test for Testcontainers (T3.1)

> Avoid combining Dockerfile optimisation and Testcontainers changes in the same MR — keep changes attributable.

---

## Do not do yet

Do not start with:

- **Editing `.drone.star` locally** — it is overwritten by RepoSync; changes must go through the central source
- Organisation-wide rollout
- Replacing all Docker Compose usage
- Building shared base images without platform ownership
- Enabling BuildKit remote cache without Drone/DIND/platform review
- Implementing ephemeral environments
- Creating a shared Testcontainers library before the first pilot proves value
- Opening all candidate tasks as delivery tickets before ownership is agreed
- Changing anything on `main` branch of the pilot repo without baseline captured first
- Assuming CI-level Testcontainers works without completing Story 0 (pipeline feasibility)
