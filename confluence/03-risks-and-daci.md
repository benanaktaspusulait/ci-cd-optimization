# Risks and DACI Decision Areas

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

---

## Risk Register

| # | Risk | Impact | Mitigation | Owner / Follow-up |
|---|------|--------|------------|-------------------|
| R1 | Pilot repo selection slips or stakeholders disagree | Blocks all later work (Story 1 is the gate) | Time-box selection to Week 1; agree criteria up front | CST / Thomas Reddy |
| R2 | Drone pipeline history lacks reliable timing data | Weak before/after evidence | Use last N pipeline runs from Drone UI; fall back to local measurements | CST |
| R3 | Drone DIND does not support Testcontainers in CI | Testcontainers pilot stays local-only | Assess in Story 1 (T1.4); treat as a finding, not a blocker | CST + ACP |
| R4 | Reducing Compose services breaks a hidden local workflow | Developer disruption | Change CI usage only; keep Compose for local debugging; map before removing | CST |
| R5 | Optimisations turn out to be ACP/ETO-owned, not CST-local | Limited CST autonomy to act | Classify ownership early (Story 6) before progressing wider changes | CST |
| R6 | Build cache change produces inconsistent/incorrect images | Bad image in registry | Verify image runs after each change; clean (no-cache) build must always succeed | CST |
| R7 | RepoSync overwrites local pipeline changes | Wasted effort on .drone.star edits | Complete Story 1 to identify boundaries; only propose changes through ACP route | CST + ACP |
| R8 | Deploy pipeline (Helm/service repo) confused with CI pipeline scope | Scope creep into deploy improvements | Document CI vs Deploy boundary clearly; route deploy topics to FUTURE-CONSIDERATIONS | CST |

---

## DACI Decision Areas

Some proposals may require a multi-stakeholder decision before proceeding. These are identified below.

| # | Decision Area | Why DACI May Be Needed | Suggested Participants | Status |
|---|---------------|------------------------|------------------------|--------|
| D1 | Testcontainers CI execution | Requires DOCKER_HOST + RYUK_DISABLED in Drone Maven step — this is a RepoSync change affecting all adaptors using the same .drone.star | Driver: CST. Approver: ACP. Contributors: Cerberus Dev leads. Informed: DSA ETO. | Not started |
| D2 | BuildKit remote cache infrastructure | Requires registry namespace, write permissions, retention policy, and RepoSync change | Driver: CST. Approver: ACP. Contributors: Platform/Registry team. Informed: DSA ETO. | Not started |
| D3 | Shared base image ownership | Creating org-maintained base images requires lifecycle governance, rebuild cadence, deprecation policy | Driver: TBC. Approver: DSA ETO / Ezhil. Contributors: ACP, CST. Informed: All adaptor teams. | Not started |
| D4 | Drone pipeline template changes | Extracting reusable Starlark functions into the central .drone.star template affects all repos receiving RepoSync | Driver: ACP. Approver: ACP lead. Contributors: CST (pilot evidence). Informed: All adaptor teams. | Not started |
| D5 | Docker Compose CI reduction | Reducing Compose services in CI may affect QAT's ability to debug CI failures if they rely on compose-based log output | Driver: CST. Approver: Thomas Reddy. Contributors: QAT, Dev leads. Informed: ACP. | Not started |

---

## When to raise a DACI

A DACI record should be created when the pilot findings (Story 6) confirm that an item:
- Affects multiple teams.
- Changes shared tooling or infrastructure.
- Has delivery risk beyond CST.
- Requires ACP or DSA ETO/Enabling prioritisation.
- Needs ownership agreement before work starts.

The pilot itself does not require DACI approval — it is CST-local exploratory work. DACI applies to the **follow-up actions** that emerge from the pilot.

---

*Feedback or questions? Contact the page owner or comment below.*
