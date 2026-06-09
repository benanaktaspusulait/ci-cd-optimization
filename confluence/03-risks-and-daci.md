# Risks and DACI Decision Areas

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

---

## Risk Register

| # | Risk | Probability | Impact | Mitigation | Fallback | Owner |
|---|------|:-----------:|:------:|------------|----------|-------|
| R1 | Pilot repo selection slips or stakeholders disagree | Med | High | Time-box selection to Week 1; agree criteria up front | Pick the repo with the slowest known pipeline by default | CST / Thomas Reddy |
| R2 | Drone pipeline history lacks reliable timing data for baseline | Med | Med | Use last N pipeline runs from Drone UI; document method | Fall back to repeatable local measurements | CST |
| R3 | Drone DIND does not support Testcontainers in CI (DOCKER_HOST not accessible from Maven step) | Med | High | Assess in Story 1 (T1.4) early; treat as a finding, not a blocker | Testcontainers stays local-only; Docker Compose remains in CI | CST + ACP |
| R4 | Reducing Compose services breaks a hidden local workflow | Low | Med | Change CI usage only; keep Compose for local; map before removing anything | Revert Compose change; document the dependency found | CST |
| R5 | Optimisations turn out to be ACP/ETO-owned, not CST-local | Med | Med | Classify ownership early (Story 6) before progressing wider changes | Hand item to ACP/ETO board with evidence attached | CST |
| R6 | Build cache change produces inconsistent/incorrect images | Low | High | Verify image runs after each change; clean (no-cache) build must always succeed | Disable cache mount; rebuild from clean context | CST |
| R7 | RepoSync overwrites local pipeline changes — pilot cannot modify `.drone.star` | Med | High | Complete Story 1 to identify boundaries; only propose changes through ACP route | Keep pipeline changes as recommendations in Story 6 | CST + ACP |
| R8 | Deploy pipeline (Helm/service repo) confused with CI pipeline scope | Low | Med | Document CI vs Deploy boundary clearly (see Pipeline & Drone page) | Route deploy topics to Future Considerations | CST |

---

## DACI Decision Areas

Some proposals may require a multi-stakeholder decision before proceeding. The pilot itself does not require DACI — it is CST-local exploratory work. DACI applies to the **follow-up actions** that emerge from the pilot findings.

| # | Decision Area | Why DACI May Be Needed | Suggested DACI Roles | Status |
|---|---------------|------------------------|----------------------|--------|
| D1 | Testcontainers CI execution | Requires `DOCKER_HOST` + `RYUK_DISABLED` in Drone Maven step — RepoSync change affecting all adaptors | **Driver:** CST. **Approver:** ACP. **Contributors:** Cerberus Dev leads. **Informed:** DSA ETO. | Not started |
| D2 | BuildKit remote cache infrastructure | Requires registry namespace, write permissions, retention policy, and RepoSync change | **Driver:** CST. **Approver:** ACP. **Contributors:** Platform/Registry team. **Informed:** DSA ETO. | Not started |
| D3 | Shared base image ownership | Org-maintained base images require lifecycle governance, rebuild cadence, deprecation policy | **Driver:** TBC. **Approver:** DSA ETO / Ezhil. **Contributors:** ACP, CST. **Informed:** All adaptor teams. | Not started |
| D4 | Drone pipeline template changes | Reusable Starlark functions in central `.drone.star` affect all repos receiving RepoSync | **Driver:** ACP. **Approver:** ACP lead. **Contributors:** CST (pilot evidence). **Informed:** All adaptor teams. | Not started |
| D5 | Docker Compose CI reduction | Reducing services in CI may affect QAT debugging if they rely on compose-based logs | **Driver:** CST. **Approver:** Thomas Reddy. **Contributors:** QAT, Dev leads. **Informed:** ACP. | Not started |

---

## When to Raise a DACI

Create a DACI record when pilot findings (Story 6) confirm that an item:
- Affects multiple teams.
- Changes shared tooling or infrastructure.
- Has delivery risk beyond CST.
- Requires ACP or DSA ETO/Enabling prioritisation.
- Needs ownership agreement before work starts.

---

*Feedback or questions? Contact the page owner or comment below.*
