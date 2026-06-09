# Proposal Overview Matrix

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

---

## Proposal Ratings

Each proposal is assessed on Value, Risk, Complexity, Effort, and MoSCoW priority.

**Rating guidance:**
- Low risk + low effort + high value = strong quick win (Phase 1 candidate).
- High risk + high complexity + unclear value = needs more investigation.
- Items with cross-team or ACP/ETO impact may need a DACI decision record.

> **Note:** Numeric improvement estimates (e.g. "~450 MB → ~300 MB") are initial targets, subject to validation after Story 2 baseline capture.

| # | Proposal | Description | Value | Risk | Complexity | Effort | MoSCoW | Owner | Phase | Notes |
|---|----------|-------------|:-----:|:----:|:----------:|:------:|:------:|-------|:-----:|-------|
| 1 | `.dockerignore` validation | Exclude `.git`, `target`, `docs`, `src/test`, IDE files from Docker build context. Reduces context from ~200 MB to ~50 MB. | High | Low | Low | Low | Must | CST | 1 | Quick win — 30 min effort, immediate measurable gain |
| 2 | Dockerfile multi-stage build | Separate dependency resolution → build → runtime. Ship only the application JAR and minimal runtime base (no build tools, no source). Reduces image ~450 MB → ~300 MB. | High | Low | Medium | Medium | Must | CST | 1 | Local-only; no pipeline change needed |
| 3 | BuildKit cache mounts (local) | `--mount=type=cache,target=/root/.m2` persists Maven repository across local builds. Dependencies not re-downloaded. | High | Low | Low | Low | Must | CST | 1 | Works locally; ephemeral per-build in CI DIND |
| 4 | Testcontainers local prototype | Replace one Compose dependency (Redis recommended) with Testcontainers. Proves isolation/determinism with before/after timing. | High | Medium | Medium | Medium | Must | CST | 2 | Redis = simplest candidate. Kafka+ZK = complex but higher value |
| 5 | Docker Compose CI rationalisation | Map all Compose services, classify CI-required vs local-debug, recommend reduced CI set. Produces evidence for what to remove. | Medium | Low | Low | Medium | Should | CST | 2 | Does not remove Compose — clarifies its role |
| 6 | Trivy security scan (CI) | Scan built image for HIGH+CRITICAL vulnerabilities. Report-only (non-blocking during pilot). | Medium | Low | Low | Low | Should | CST | 1 | Already in tag pipeline; extend to CI flow |
| 7 | BuildKit remote cache (CI) | Registry-backed cache (`--cache-from`/`--cache-to`). CI builds reuse layers across runs. | High | Medium | High | High | Could | ACP | 4 | Requires ACP: registry namespace, write permissions, `.drone.star` change |
| 8 | Testcontainers in CI | Run Testcontainers in Drone pipeline (DIND + `DOCKER_HOST` + `RYUK_DISABLED`). | High | Medium | High | Medium | Could | ACP | 3–4 | Requires ACP: RepoSync Maven step env var change |
| 9 | Shared base image strategy | Org-maintained `base-os → base-runtime → base-build → application` hierarchy. Digest-pinned, centrally rebuilt. | Medium | Medium | High | High | Won't (this pilot) | DSA ETO | 4+ | Requires governance, lifecycle, rebuild cadence — post-pilot |
| 10 | Reusable Drone pipeline templates | Extract optimised patterns into Starlark functions in central `.drone.star`. All adaptors inherit via RepoSync. | Medium | Low | Medium | Medium | Won't (this pilot) | ACP | 4+ | After pilot proves patterns; requires ACP/RepoSync ownership |

---

## Quick Win Summary

Proposals 1–3 are the lowest-hanging fruit:
- **Low risk, low effort, high value, no coordination needed.**
- Can be delivered in Week 1–2 of the pilot.
- Provide immediate measurable evidence (build time, image size, context size).
- Proven approach in the industry — no experimental risk.

Proposals 4–6 are medium-effort and form the core pilot validation work (Week 2–3).

Proposals 7–10 require ACP or DSA ETO coordination and are post-pilot recommendations backed by the pilot's evidence.

---

*Feedback or questions? Contact the page owner or comment below.*
