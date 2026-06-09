# Phased Plan

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

---

## Phase 1: Low-Risk Quick Wins (Week 1–2)

**Objective:** Establish pilot boundaries, capture baseline, and deliver immediate measurable improvements with zero coordination risk.

**Candidate changes:**
- Assess Drone/RepoSync pipeline structure and local vs central boundaries.
- Compare at least two candidate pipelines/repositories, select one pilot repository, and capture baseline metrics (build time, image size, pipeline duration, integration test timing).
- Add or validate `.dockerignore` (reduces build context by ≥ 50%).
- Review and document current Dockerfile structure.
- Apply multi-stage Dockerfile (separate deps → build → runtime).

**Expected outcome:**
- Pipeline and build baselines documented with measurement method.
- Build context size reduced by ≥ 50%.
- Local build time reduced by ≥ 30% (dependency caching + multi-stage).
- Image size reduced by ≥ 30% (JDK removed from runtime image).

**Success criteria:**
- Baseline numbers captured and repeatable.
- `.dockerignore` present and context reduction measured.
- Multi-stage Dockerfile builds locally and produces a working smaller image.

**Risks / dependencies:**
- Pilot repo selection may slip if stakeholders disagree → mitigation: time-box to Week 1.
- Multi-stage build may require Maven wrapper path adjustments → mitigation: test locally first.

---

## Phase 2: Medium-Complexity Improvements (Week 2–3)

**Objective:** Validate Testcontainers locally and map Docker Compose usage for evidence-based rationalisation.

**Candidate changes:**
- Select one integration dependency for Testcontainers pilot (Redis recommended — simplest, fastest to start, already used by multiple services).
- Implement Testcontainers setup locally (container config, Spring wiring, wait strategy).
- Compare Testcontainers vs Docker Compose flow (startup time, determinism, developer experience).
- Map all Docker Compose services used in CI (purpose, dependency chain, CI-required vs local-debug).
- Classify each Compose service.

**Expected outcome:**
- Testcontainers working locally for one dependency with before/after timing.
- Compose service inventory complete with CI/local classification.
- Evidence to support reducing Compose services in CI.

**Success criteria:**
- Testcontainers prototype connects successfully and test passes.
- Comparison documented: timing, isolation, complexity.
- All Compose services mapped with role assigned.

**Risks / dependencies:**
- Testcontainers may not work with the pilot repo's test structure (Cucumber + JUnit Vintage) → mitigation: prototype on simplest test first.
- Compose mapping may reveal undocumented dependencies → mitigation: flag for review, do not remove.

---

## Phase 3: Requires Coordination (Week 3–4)

**Objective:** Assess CI feasibility, consolidate findings, and prepare stakeholder communication.

**Candidate changes:**
- Assess Testcontainers CI feasibility (Drone DIND + DOCKER_HOST + Ryuk disabled).
- Assess BuildKit CI feasibility (DIND image supports buildx?).
- Recommend reduced Compose role for CI (informed by Testcontainers findings).
- Consolidate all findings into a single summary with before/after evidence.
- Classify each item: CST / ACP / DSA ETO.
- Share findings with stakeholders.

**Expected outcome:**
- Clear "feasible / feasible-with-constraints / local-only" decision for Testcontainers in CI.
- BuildKit CI feasibility documented.
- Consolidated evidence report ready for stakeholder review.
- Ownership classification ready for ticket routing.

**Success criteria:**
- Each Story has documented output.
- Ownership of every follow-up item is assigned.
- Findings shared and feedback captured.

**Risks / dependencies:**
- Testcontainers CI feasibility may be "local only" if DIND access cannot be confirmed → mitigation: this is an acceptable outcome per the fallback plan.
- Stakeholder availability for review → mitigation: book review slot in advance.

---

## Phase 4: Future / Post-Pilot (ACP / DSA ETO dependent)

**Objective:** Implement improvements that require platform coordination, informed by pilot evidence.

**Candidate changes:**
- BuildKit remote cache (registry namespace + RepoSync `.drone.star` change).
- Testcontainers CI execution (RepoSync Maven step environment change).
- Shared base image hierarchy (platform-owned, rebuild cadence, deprecation policy).
- Reusable Drone pipeline templates (Starlark functions for optimised patterns).
- Rollback automation in deploy pipeline.
- Release process automation (coordinate with Gareth's project).

**Expected outcome:**
- CI-level gains matching the local pilot evidence.
- Repeatable patterns available to all FDP adaptor repositories.

**Success criteria:**
- TBC — dependent on ACP/ETO prioritisation decisions.

**Risks / dependencies:**
- Subject to ACP / DSA ETO prioritisation and alignment with DSA Tech Strategy.
- Current DSA focus is Core Cloud and Data Platform — timing uncertain.
- Pilot evidence strengthens the case but does not guarantee priority.

---

*Feedback or questions? Contact the page owner or comment below.*
