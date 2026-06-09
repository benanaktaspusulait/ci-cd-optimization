# Proposal Overview Matrix

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

---

## Proposal ratings

Each proposal is rated on Value, Risk, Complexity, Effort, and MoSCoW priority.

**Rating guidance:**
- Low risk + low effort + high value = strong quick win (Phase 1 candidate).
- High risk + high complexity + unclear value = needs more investigation.
- Items with cross-team or ACP/ETO impact may need a DACI decision record.

| # | Proposal | Description | Value | Risk | Complexity | Effort | MoSCoW | Owner | Notes |
|---|----------|-------------|:-----:|:----:|:----------:|:------:|:------:|-------|-------|
| 1 | `.dockerignore` validation | Exclude unnecessary files from Docker build context (~50% context reduction) | High | Low | Low | Low | Must | CST | Quick win — 30 min effort, immediate measurable gain |
| 2 | Dockerfile multi-stage build | Separate dep resolution from build from runtime; ship JRE-only (~30% image size reduction) | High | Low | Medium | Medium | Must | CST | Local-only; no pipeline change needed |
| 3 | BuildKit cache mounts (local) | Persist Maven .m2 cache across local builds; deps not re-downloaded | High | Low | Low | Low | Must | CST | Works locally; ephemeral in CI DIND (per-build) |
| 4 | Testcontainers local prototype | Replace Compose dep for one test with Testcontainers; prove isolation/determinism | High | Medium | Medium | Medium | Must | CST | Redis recommended as first candidate (simplest) |
| 5 | Docker Compose CI rationalisation | Map services, classify CI-required vs local-debug, reduce CI overhead | Medium | Low | Low | Medium | Should | CST | Produces evidence for what to remove from CI |
| 6 | Trivy security scan | Scan built image for vulnerabilities (report-only, non-blocking) | Medium | Low | Low | Low | Should | CST | Already exists in tag pipeline; add to CI flow |
| 7 | BuildKit remote cache (CI) | Registry-backed cache so CI builds reuse layers across runs | High | Medium | High | High | Could | ACP | Requires ACP: registry namespace, permissions, RepoSync change |
| 8 | Testcontainers in CI | Run Testcontainers in Drone pipeline (DIND, Ryuk disabled) | High | Medium | High | Medium | Could | ACP | Requires ACP: DOCKER_HOST + RYUK env vars in .drone.star |
| 9 | Shared base image strategy | Org-maintained base-os → base-runtime → base-build hierarchy | Medium | Medium | High | High | Won't (this pilot) | DSA ETO | Requires governance, lifecycle, rebuild cadence — post-pilot |
| 10 | Reusable Drone pipeline templates | Extract optimised patterns into RepoSync Starlark functions | Medium | Low | Medium | Medium | Won't (this pilot) | ACP | After pilot proves patterns; requires ACP ownership |

---

## Quick win summary

Items 1–3 are the lowest-hanging fruit: low risk, low effort, high value, no coordination needed. These can be delivered in Week 1–2 of the pilot and provide immediate measurable evidence.

---

*Feedback or questions? Contact the page owner or comment below.*
