# Task Definitions — Story 6: Findings, Ownership & Recommendations

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

Tasks for Story 6 (Findings, Ownership and Recommendations). Consolidate evidence, classify ownership, share with stakeholders.

---

## Story 6 — Findings, Ownership and Recommendations

**Depends on:** Stories 3, 4, 5.

**Goal:** Consolidate pilot evidence, classify each item into CST/Cerberus Delivery, ACP, or DSA ETO/Enabling/CIT, and recommend the target operating model for reusable patterns.

**Why:** A pilot is only valuable if it ends in a clear decision.

**Acceptance criteria:**

- [ ] Consolidated findings summary exists.
- [ ] Each item classified with rationale.
- [ ] Each candidate mapped to suggested board/owner.
- [ ] Target operating model explains what should be replicated through ACP/RepoSync.
- [ ] Findings and next steps shared; feedback captured.

### T6.1 — Consolidate Pilot Findings

| Field | Value |
|-------|-------|
| Type | Documentation |
| Estimate | 2 |
| Priority | Must |
| Labels | `findings`, `summary`, `consolidation` |
| Sprint | Week 4 |
| Depends on | T3.4, T4.4, T5.3 |
| Owner | TBC |
| Status | Not started |

**Why:** Evidence spread across stories is hard to act on.

**Goal:** Bring all pilot evidence into one shareable findings summary.

**Scope:** Baseline, build results, Testcontainers comparison, Compose review, pipeline assessment; present before -> after / observations narrative; classify CST-local, RepoSync/platform, ETO/wider; include target operating model.

**Acceptance criteria:**

- [ ] Single consolidated findings summary exists.
- [ ] It links to supporting story evidence.
- [ ] It includes target operating model / RepoSync distribution recommendation.
- [ ] It is suitable for stakeholder sharing.

### T6.2 — Classify Ownership and Recommend Target Board

| Field | Value |
|-------|-------|
| Type | Analysis |
| Estimate | 2 |
| Priority | Must |
| Labels | `ownership`, `cst-vs-eto`, `classification`, `target-board` |
| Sprint | Week 4 |
| Depends on | T6.1 |
| Owner | TBC |
| Status | Not started |

**Why:** Some improvements are CST-local; others touch ACP CI tooling or wider platform patterns. Classification without board/owner recommendation is incomplete.

**Goal:** Classify each optimisation item and recommend owner/board.

**Scope:**

- **CST / Cerberus Delivery:** baseline measurement, Dockerfile review, `.dockerignore`, local layering experiment, Testcontainers local prototype, Compose review.
- **ACP:** `.drone.star` / RepoSync changes, DIND environment, BuildKit enablement, Testcontainers CI env vars, CI cache infrastructure.
- **DSA ETO / Enabling / CIT:** org base images, shared templates, reusable Testcontainers libraries, cross-project adoption model, remote cache infrastructure.

**Recommend:** CST board, ACP board, DSA ETO / Enabling board, shared visibility only, or further discussion needed.

**Acceptance criteria:**

- [ ] Each item is classified with rationale.
- [ ] Each item is mapped to suggested owner/board.
- [ ] ACP-owned items identify whether they should become RepoSync MR candidates.
- [ ] No wider-impact item progresses without appropriate visibility.
- [ ] Assumptions are documented.

### T6.3 — Share Findings with Stakeholders

| Field | Value |
|-------|-------|
| Type | Documentation |
| Estimate | 1 |
| Priority | Should |
| Labels | `stakeholders`, `communication`, `findings` |
| Sprint | Week 4 |
| Depends on | T6.2 |
| Owner | TBC |
| Status | Not started |

**Why:** The pilot's purpose is to inform a decision.

**Goal:** Share consolidated findings and ownership recommendations with agreed engineering stakeholders, and capture feedback.

**Scope:** Pilot scope, baseline findings, build optimisation results, Testcontainers findings, ownership recommendations, suggested next steps.

**Acceptance criteria:**

- [ ] Findings are shared with agreed stakeholders.
- [ ] Feedback is captured.
- [ ] Next steps are agreed or documented.





---

*Feedback or questions? Contact the page owner or comment below.*
