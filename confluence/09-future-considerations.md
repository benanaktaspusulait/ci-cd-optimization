# Future Considerations

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

> **Disclaimer:** These opportunities are subject to ACP / DSA ETO prioritisation and alignment with DSA Tech Strategy, Core Cloud and Data Platform direction. They are not part of the immediate pilot.

> **When to revisit:** after Story 6 is complete and stakeholders decide to progress beyond the pilot.

---

## Post-Pilot Production Readiness

After T6.2 classifies ownership, create tickets on the relevant boards.

| # | Category | What's needed | Why it matters | Likely owner |
|---|----------|---------------|----------------|--------------|
| F1 | **Rollback strategy** | Define how to revert when a new build causes issues. **KT confirmed: no automated rollback exists — only manual `helm rollback`.** | Bad deploy stays live until manually fixed. | CST + ACP |
| F2 | **Monitoring & alerting** | Track pipeline health (queue time, failure rate, duration trend). Drone API + Grafana. | Degradation unnoticed until manually checked. | CST (setup) / ACP (infra) |
| F3 | **Artifact management** | Image retention policy (keep last N tags, expire untagged after 30 days). Registries: `docker.digital.homeoffice.gov.uk`, ECR, Artifactory. | Unmanaged registries grow indefinitely. | ACP |
| F4 | **Environment strategy** | Promotion path: dev → SIT → bVal → prod. Drone promotion pipelines + protected branches. | Production needs separation and gates. | CST + ACP |
| F5 | **Cost tracking** | CI runner minutes, registry storage, image transfer. Budget alerts. | Optimisation may shift cost elsewhere. | ACP / finance |
| F6 | **Compliance & audit** | Traceable pipeline changes: who approved, what ran, which image deployed. | Regulated environment = evidence of change control. | ACP / compliance |
| F7 | **Troubleshooting runbook** | "Pipeline failed — what do I do?" step-by-step guide. | Reduces MTTR, unblocks developers. | CST |

**Recommended priority:** F1+F7 → F2 → F3 → F4 → F6 → F5.

---

## Post-Pilot Architecture Decisions (Candidates)

### Base image strategy

**Context:** Dockerfiles inherit from arbitrary upstream images (`amazoncorretto:17`). No shared governance — each repo pins a different tag, CVE patching is per-repo.

**Proposed pattern:** `base-os → base-runtime → base-build → application`. Versioned, digest-pinned base images maintained by ACP/ETO.

**Why post-pilot:** Requires ACP/ETO to build, publish, scan, maintain. Needs rebuild cadence + deprecation policy. CST can validate the pattern on one repo; governance is ACP/ETO.

**If adopted:** (+) Central CVE patching, smaller images, simpler Dockerfiles. (−) Teams lose direct runtime env control.

### BuildKit remote cache infrastructure

**Context:** Drone pods are ephemeral — no persistent cache. Registry-backed cache (`--cache-from`/`--cache-to`) needs: registry namespace, write permissions, DIND BuildKit support, security review.

**Why post-pilot:** CST cannot provision without ACP approval. Pipeline ≥20% target may not be achievable without it. Local cache mounts are the interim win.

**If adopted:** (+) Faster CI, layer reuse across branches. (−) Registry cost, cache invalidation complexity.

---

## Post-Pilot Technical Opportunities

### Selective test execution

**What:** Only run tests affected by changed code (`-pl`, `-am` + `git diff`).
**Impact:** 50%+ integration test time reduction for focused changes.
**When:** After Story 4 proves test independence.

### Reusable Drone pipeline templates (via RepoSync)

**What:** Extract optimised patterns into Starlark functions in `.drone.star`. All adaptors inherit via RepoSync.
**Impact:** No copy-paste drift; single fix propagates everywhere.
**When:** After Story 6 + ACP agreement. Requires central ownership.

### Contract testing (Pact)

**What:** Consumer-driven API contract verification without shared environments.
**Impact:** Catches integration mismatches pre-deploy. Reduces full-stack staging need.
**When:** When multiple FDP services interact and integration failures are recurring.

### Ephemeral review environments

**What:** Per-MR short-lived deployment for reviewer testing.
**Impact:** Faster feedback; QA verifies without waiting for shared staging.
**When:** After pipeline is fast/reliable. Requires ACP infrastructure.

### Dependency proxy / artifact cache

**What:** Cache Maven/Docker pulls at org level (Artifactory, GitLab Dependency Proxy).
**Impact:** Eliminates network variability; protects against upstream outages.
**When:** When multiple teams hit download latency. ACP / DSA ETO owned.

### Release automation (Gareth's project)

**What:** Automate service chart management + release flow (in progress, led by Gareth Andrews).
**Coordination:** Complementary to CI pilot. Share Story 6 findings with Gareth. Do not conflict on Helm chart structure.
**When:** After Story 6 — include Gareth as stakeholder.

---

## Related Future Area: Deployment and Release Safety

> Outside pilot scope. Documented for awareness from KT sessions.

**Observations:**
- Deploy managed through MMA service/Helm repo, not adaptor repos.
- Feature activation via feature flags (Helm values), not deployments.
- Deployment success ≠ feature activation or functional validation.
- Validation: pod readiness (automated) → dev teams (Playwright/Cypress) → QAT (approval gate at SIT).
- No automated rollback — manual `helm rollback` only.
- Environment parity (bVal vs prod) should be verified.
- Runbook repos contain approved release steps.

**Recommended future actions:**
- Document manual rollback as a runbook.
- Assess automated rollback (Helm hooks or pipeline step).
- Coordinate with Gareth's release automation.
- Clarify bVal/prod environment parity.

---

*Feedback or questions? Contact the page owner or comment below.*
