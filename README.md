# Container & CI/CD Optimisation Pilot

> **Status:** Pilot planning — not an approved implementation programme.
> **Scope:** FDP as the pilot context; patterns may be reusable more widely if proven.
> **Intent:** Validate a few optimisation ideas through a small, measurable pilot before any wider rollout.

**Key message:** the difference is not Docker vs no Docker — it's *optimised, standardised, cached and test-driven* Docker usage.

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

## Approach — how we tackle it

A small, measurable pilot on **one** representative repository:

1. **Baseline** the current state so every change is provable.
2. **Optimise the Docker build** (layering, `.dockerignore`, cache mounts) and measure the delta.
3. **Pilot Testcontainers** for one integration dependency for better isolation/determinism.
4. **Rationalise Docker Compose** — keep it for local debugging, reduce its role in CI.
5. **Consolidate findings** and classify each pattern as CST-local vs platform/ETO.

## Technology stack

| Area | Tooling |
|------|---------|
| Containers | Docker, BuildKit / `docker buildx`, multi-stage builds |
| CI/CD | **GitLab CI** (`.gitlab-ci.yml`) |
| Registry | GitLab Container Registry |
| Integration testing | Testcontainers (Java); existing Docker Compose for comparison |
| Build / deps | Maven (`mvnw`), Maven cache mounts |
| Candidate test deps | Redis, Kafka, Schema Registry, LocalStack |
| Security | Trivy / Snyk (scanning), SBOM (Syft), secret mounts — see [SECURITY.md](SECURITY.md) |

---

## Epic

**Pilot Container & CI/CD Optimisation Improvements for FDP** — validate selected build and integration-testing improvements with before/after evidence, then identify reusable patterns and their owners (CST vs platform/ETO).

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
| 1 | [Baseline & Pilot Scope](docs/stories/story-1-baseline/README.md) | 4 | — | — |
| 2 | [Docker Build Optimisation](docs/stories/story-2-build/README.md) | 4 | 1 | 3 |
| 3 | [Testcontainers Pilot](docs/stories/story-3-testcontainers/README.md) | 4 | 1 | 2 |
| 4 | [Docker Compose Rationalisation](docs/stories/story-4-compose/README.md) | 3 | 3 | — |
| 5 | [Findings, Ownership & Recommendations](docs/stories/story-5-findings/README.md) | 4 | 2, 3, 4 | — |

```text
Story 1 (baseline, gate)
   ├──> Story 2 ─┐
   └──> Story 3 ─┼──> Story 4
                 └──> Story 5
```

📋 [**Full backlog index**](docs/stories/INDEX.md) — all stories and task titles on one page.

**More docs:** [Project plan & timeline](PROJECT-PLAN.md) · [Security plan](SECURITY.md) · [Architecture decisions (ADR)](docs/adr/README.md) · [Technical notes](docs/stories/tech-notes.md) · [Definition of Done](docs/stories/DEFINITION-OF-DONE.md) · [How to contribute](CONTRIBUTING.md)

---

## Status board

Single source of truth for progress. Update the **Status** column as work moves.
Estimates: `S` ≤0.5d · `M` 0.5–1d · `L` 1–2d. Priority: MoSCoW.

| ID | Item | Est | Priority | Status | Owner |
|----|------|:---:|:--------:|--------|-------|
| **S1** | **Baseline & Pilot Scope** | — | Must | Not started | _TBD_ |
| T1.1 | Select pilot repository/service | S | Must | Not started | _TBD_ |
| T1.2 | Capture CI/CD pipeline baseline | M | Must | Not started | _TBD_ |
| T1.3 | Capture Docker build & image-size baseline | S | Must | Not started | _TBD_ |
| T1.4 | Capture integration-test baseline | M | Must | Not started | _TBD_ |
| **S2** | **Docker Build Optimisation** | — | Must | Not started | _TBD_ |
| T2.1 | Review current Dockerfile & build context | M | Must | Not started | _TBD_ |
| T2.2 | Add or validate .dockerignore | S | Must | Not started | _TBD_ |
| T2.3 | Apply Dockerfile layering / cache improvement | M | Must | Not started | _TBD_ |
| T2.4 | Measure local & CI build impact | M | Should | Not started | _TBD_ |
| **S3** | **Testcontainers Pilot** | — | Must | Not started | _TBD_ |
| T3.1 | Select candidate dependency/test | S | Must | Not started | _TBD_ |
| T3.2 | Implement Testcontainers setup | L | Must | Not started | _TBD_ |
| T3.3 | Compare with docker-compose flow | M | Should | Not started | _TBD_ |
| T3.4 | Document findings & constraints | S | Should | Not started | _TBD_ |
| **S4** | **Docker Compose Rationalisation** | — | Should | Not started | _TBD_ |
| T4.1 | Map services started by docker-compose | S | Must | Not started | _TBD_ |
| T4.2 | Classify services & usage | M | Must | Not started | _TBD_ |
| T4.3 | Recommend reduced Compose role | M | Should | Not started | _TBD_ |
| **S5** | **Findings, Ownership & Recommendations** | — | Must | Not started | _TBD_ |
| T5.1 | Consolidate pilot findings | M | Must | Not started | _TBD_ |
| T5.2 | Classify CST-local vs platform/ETO items | S | Must | Not started | _TBD_ |
| T5.3 | Recommend ticket ownership & target board | S | Should | Not started | _TBD_ |
| T5.4 | Share findings with stakeholders | S | Should | Not started | _TBD_ |

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
