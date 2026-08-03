# Phased Plan

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | In progress — pilot closure evidence pending |
| **Last updated** | 2026-08-03 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

> This is a ~4-week part-time pilot, not a full-time programme. Adjust per team capacity.

---

## Timeline Overview

| Week | Focus | Stories | Exit criteria |
|------|-------|---------|---------------|
| **Week 1** | Assess + baseline | Story 1 (T1.1–T1.5), Story 2 (T2.1–T2.4) | Pipeline boundaries understood; at least two candidates compared; one pilot repo selected; baseline captured |
| **Week 2** | Quick wins + prototype | Story 3 (T3.1–T3.3), Story 4 (T4.1–T4.2) | `.dockerignore` + multi-stage applied; Testcontainers running locally |
| **Week 3** | Measure + compare | Story 3 (T3.4), Story 4 (T4.3–T4.4), Story 5 (T5.1) | Build evidence; scoped Testcontainers/Compose comparison; Compose scope validated |
| **Week 4** | Rationalise + decide | Story 5 (T5.2), Story 6 (T6.1–T6.2) | Compose role recommended; outcomes/ownership classified; adoption decisions and share-out recorded |

### Milestones

- **M1 — Boundaries + baseline** (end Week 1): scope locked, pipeline understood, numbers captured.
- **M2 — Optimisations applied** (end Week 2): build + Testcontainers changes exist locally.
- **M3 — Evidence collected** (end Week 3): before/after data, comparisons documented.
- **M4 — Pilot reported** (end Week 4): findings + ownership shared with stakeholders.

---

## Phase 1: Low-Risk Quick Wins (Week 1–2)

**Objective:** Establish pilot boundaries, capture baseline, and deliver immediate measurable improvements with zero coordination risk.

**Candidate changes:**
- Assess Drone/RepoSync pipeline structure and local vs central boundaries.
- Compare at least two candidate repositories/pipelines for portability, select one pilot repository, and capture baseline metrics (build time, image size, pipeline duration, integration test timing).
- Add or validate `.dockerignore` (reduces build context by ≥ 50%).
- Review and document current Dockerfile structure (layer ordering, base image, cache invalidation risks).
- Apply multi-stage Dockerfile (separate deps → build → runtime).
- Enable BuildKit cache mounts for local Maven repository.

**Expected outcome:**
- Pipeline and build baselines documented with measurement method.
- Build context size reduced by ≥ 50% (initial target, subject to baseline validation).
- Local build time reduced by ≥ 30% (initial target, subject to baseline validation).
- Image size reduced by ≥ 30% (initial target — build tools removed from runtime image).

**Success criteria:**
- [ ] Baseline numbers captured and repeatable.
- [ ] `.dockerignore` present and context reduction measured.
- [ ] Multi-stage Dockerfile builds locally and produces a working smaller image.
- [ ] Clean (no-cache) build still succeeds.

**Risks / dependencies:**
- Candidate comparison or pilot repo selection may slip if stakeholders disagree → mitigation: time-box to Week 1.
- Multi-stage build may require Maven wrapper path adjustments → mitigation: test locally first.
- Pipeline assessment may reveal constraints that limit later phases → mitigation: this is expected and acceptable; document as findings.

---

## Phase 2: Medium-Complexity Improvements (Week 2–3)

**Objective:** Validate Testcontainers locally and map Docker Compose usage for evidence-based rationalisation.

**Candidate changes:**
- Select one integration dependency for Testcontainers pilot (Redis recommended — simplest, fastest to start, already used by multiple FDP services).
- Implement Testcontainers setup locally (container config, Spring `@DynamicPropertySource` wiring, wait strategy, Cucumber integration).
- Compare Testcontainers vs Docker Compose flow (startup time, determinism, developer experience).
- Map all Docker Compose services used in CI (purpose, dependency chain, CI-required vs local-debug).
- Classify each Compose service.

**Expected outcome:**
- Testcontainers working locally for one dependency with before/after timing.
- Compose service inventory complete with CI/local classification.
- Evidence to support reducing Compose services in CI.

**Success criteria:**
- [ ] Testcontainers prototype connects successfully and test passes.
- [ ] Comparison documented: timing, isolation, complexity, developer experience.
- [ ] All Compose services mapped with role assigned.

**Risks / dependencies:**
- Testcontainers may not work with pilot repo's test structure (Cucumber + JUnit Vintage) → mitigation: prototype on simplest test first.
- Compose mapping may reveal undocumented dependencies → mitigation: flag for review, do not remove.

---

## Phase 3: Requires Coordination (Week 3–4)

**Objective:** Assess CI feasibility, consolidate findings, and prepare stakeholder communication.

**Candidate changes:**
- Assess Testcontainers CI feasibility (Drone DIND + `DOCKER_HOST` + `RYUK_DISABLED`).
- Assess BuildKit CI feasibility (does DIND image support `docker buildx`?).
- Recommend reduced Compose role for CI (informed by Testcontainers findings).
- Consolidate all findings into a single summary with before/after evidence.
- Classify each item: CST / ACP / DSA ETO.
- Share findings with Thomas Reddy and relevant stakeholders.

**Expected outcome:**
- Clear "feasible / feasible-with-constraints / local-only" decision for Testcontainers in CI.
- BuildKit CI feasibility documented.
- Consolidated evidence report ready for stakeholder review.
- Ownership classification ready for ticket routing.

**Success criteria:**
- [ ] Each Story has documented output with evidence.
- [ ] Ownership of every follow-up item is assigned to a category (CST / ACP / DSA ETO).
- [ ] Findings shared and feedback captured.

**Risks / dependencies:**
- Testcontainers CI feasibility may be "local only" → this is acceptable per the fallback plan.
- Stakeholder availability → mitigation: book review slot in advance.

---

## Post-pilot delivery epic

Post-pilot delivery is not Phase 4 or Story 7 of this pilot.

| Epic | Initial scope | Status | Gate |
|---|---|---|---|
| SNS Delivery Pipeline Optimisation | Independent SNS productionisation and CD review/target-planning stories | Proposed / New | Story-specific evidence and ownership; no CD migration implementation |

Build-once-promote and wider platform opportunities remain related release/platform candidates. They are coordinated only where ownership overlaps and are not automatic dependencies or approved delivery.

---

*Feedback or questions? Contact the page owner or comment below.*
