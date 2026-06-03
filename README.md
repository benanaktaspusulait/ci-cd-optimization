# Container & CI/CD Optimisation Pilot

> **Status:** Pilot planning — not an approved implementation programme.
> **Scope:** FDP as the pilot context; patterns may be reusable more widely if proven.
> **Intent:** Validate a few optimisation ideas through a small, measurable pilot before any wider rollout.

**Key message:** the difference is not Docker vs no Docker — it's *optimised, standardised, cached and test-driven* Docker usage.

---

## Epic

**Pilot Container & CI/CD Optimisation Improvements for FDP** — validate selected build and integration-testing improvements with before/after evidence, then identify reusable patterns and their owners (CST vs platform/ETO).

**Success:** pilot repo baselined · ≥1 build optimisation measured · ≥1 Testcontainers test compared to docker-compose · Compose role reviewed · ownership documented · findings shared.

**Out of scope:** org-wide rollout, removing all Compose, building shared platform capabilities, guaranteeing a specific speedup.

---

## Stories

| # | Story | Tasks | Depends on | Parallel with |
|---|-------|:-----:|------------|----------------|
| 1 | [Baseline & Pilot Scope](backlog/story-1-baseline/README.md) | 4 | — | — |
| 2 | [Docker Build Optimisation](backlog/story-2-build/README.md) | 4 | 1 | 3 |
| 3 | [Testcontainers Pilot](backlog/story-3-testcontainers/README.md) | 4 | 1 | 2 |
| 4 | [Docker Compose Rationalisation](backlog/story-4-compose/README.md) | 3 | 3 | — |
| 5 | [Findings, Ownership & Recommendations](backlog/story-5-findings/README.md) | 4 | 2, 3, 4 | — |

```text
Story 1 (baseline, gate)
   ├──> Story 2 ─┐
   └──> Story 3 ─┼──> Story 4
                 └──> Story 5
```

📋 [**Full backlog index**](backlog/INDEX.md) — all stories and task titles on one page.

See also: [Technical Notes](backlog/tech-notes.md) · [Definition of Done](backlog/DEFINITION-OF-DONE.md)

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

## Risks & assumptions

| # | Risk / assumption | Impact | Mitigation |
|---|-------------------|--------|------------|
| R1 | Pilot repo selection slips or stakeholders disagree | Blocks all later work (Story 1 is the gate) | Time-box selection; agree criteria up front in T1.1 |
| R2 | CI history lacks reliable timing data for a clean baseline | Weak before/after evidence | Fall back to local measurements; document the method |
| R3 | Docker-in-Docker / runner constraints limit Testcontainers in CI | Testcontainers pilot stays local-only | Assess CI suitability early in T3.2; treat CI as a separate finding |
| R4 | Reducing Compose services breaks a hidden local workflow | Developer disruption | Keep Compose for local debugging; change CI usage only (Story 4) |
| R5 | Optimisations turn out to be platform/ETO-owned, not CST-local | Limited CST autonomy to act | Classify ownership early (Story 5) before progressing wider changes |
| A1 | Assumption: one representative repo is enough to validate the ideas | Findings may not generalise | State scope limits explicitly in the final summary |

---

## How to use this backlog

See [CONTRIBUTING.md](CONTRIBUTING.md) for structure, conventions, and the task workflow. In short:

- **Browse** from the [backlog index](backlog/INDEX.md) → story → task.
- **Track progress** only in the [status board](#status-board) — it's the single source of truth.
- **Close a task** when its acceptance criteria **and** the shared [Definition of Done](backlog/DEFINITION-OF-DONE.md) are met.

1. Epic
2. Story 1 → T1.1 (select repo) → T1.2 (pipeline baseline)
3. Story 2 → T2.1 (review Dockerfile)

Open the rest once the baseline and a first build review are underway. This keeps work controlled and avoids raising implementation tickets before baseline and ownership are agreed.
