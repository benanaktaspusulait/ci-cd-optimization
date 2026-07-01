# Story 2 — Baseline & Pilot Scope: Summary

| Field | Value |
|-------|-------|
| **Status** | Draft baseline pack — SNS remains working pilot; N=10 SNS Drone CI timing baseline and local Docker image/build baseline captured; stakeholder, local Maven/test timing, ownership and image-source confirmations pending |
| **Date** | 2026-06-11 |

---

## Deliverables

| Task | File | Status |
|------|------|:------:|
| T2.1 — Select pilot repo | [T2.1-select-repo.md](./T2.1-select-repo.md) | ⚠️ pending stakeholder confirmation |
| T2.2 — Pipeline baseline | [T2.2-pipeline-baseline.md](./T2.2-pipeline-baseline.md) | ⚠️ CI timing baseline captured; local build/test baseline pending |
| T2.3 — Docker build & image baseline | [T2.3-build-image-baseline.md](./T2.3-build-image-baseline.md) | ✅ measured local Docker baseline captured |
| T2.4 — Integration test baseline | [T2.4-integration-test-baseline.md](./T2.4-integration-test-baseline.md) | ⚠️ structural baseline captured; internal N=10/flakiness pending |
| T2.5 — Redis Testcontainers pilot plan | [T2.5-redis-testcontainers-pilot-plan.md](./T2.5-redis-testcontainers-pilot-plan.md) | ⚠️ draft pilot plan |

---

## Baseline Summary Table

> ⚠️ **CI duration now uses the N=10 successful SNS Drone UI sample.** Docker image/build metrics use local measured values. Full publish/CD/cleardown timing, local Maven/test feedback timings, stakeholder sign-off and production ownership/image-source confirmations still require follow-up.

| Metric | Baseline | Target | Story |
|--------|---------------------|--------|-------|
| CI elapsed duration | 13:35 average; 13:31 median from N=10 successful CI sample | ≥ 20% ↓ (post-platform) | S3, S4 |
| CI elapsed duration fastest / slowest | 13:20 / 13:57 | — | S3, S4 |
| Full develop push pipeline | TBC | — | — |
| Docker build time (local, cold/no-cache) | `real 1m17.855s` | ≥ 30% ↓ | S3 |
| Docker build time (local, warm cached) | `real 0m0.851s` | Contextual only | S3 |
| Final image size | 906MB | ≥ 30% ↓ after approved candidate change | S3 |
| Build context size | 191.27MB transferred; local module 356M after artefact prep | Reduce measured context where safe | S3 |
| Integration Tests outer Drone step | 09:43 average from T2.2 N=10 sample | No regression | S4 |
| Integration-test internal sample | Build #881 Maven total 08:27; integration-test module 07:52 | No regression | S4 |
| Containers required for integration tests | SNS: 20 defined (17 started in CI); DVLA excerpts show comparable full-stack compose shape | Reduced subset | S4, S5 |
| Test isolation | None (shared state) | Per-test-class | S4 |
| Docker ignore file | Not found in SNS local checkout; DVLA TBC; RoRo TSV not found in direct check | Effective ignore if ownership allows | S3 |
| Base image | `amazoncorretto:17` full JDK; visible base/rootfs layers 165MB + 300MB | Smaller approved runtime candidate, if validated | S3 |

---

## Key Decisions Made

1. **Working pilot recommendation:** `fdp-cmd-adaptor-sns` — representative, already analysed, lower-risk pilot target; stakeholder confirmation still required
2. **Lightweight comparison candidate:** `fdp-cmd-adaptor-dvla` — source excerpts validate the same RepoSync pipeline family, Dockerfile pattern and integration-test orchestration shape; not selected as pilot target
3. **Measurement method:** Drone UI/manual read for CI elapsed and visible step timings; local `docker build`/`docker images`/`docker history` for Docker metrics
4. **First Testcontainers candidate:** Redis (simplest, fastest startup, best isolation benefit)
5. **Biggest Docker optimisation opportunities:** Review full JDK runtime base, `yum install/update` layer, effective Docker ignore file and artefact packaging; no production change until ownership/image-source validation and before/after measurement
6. **Redis pilot planning:** T2.5 now captures the Redis Testcontainers pilot plan; it is not a completed implementation.

---

## Validation Needed

> ⚠️ **The core CI elapsed and local Docker baselines are measured.** Remaining items below are confirmations or optional follow-up measurements required before production optimisation claims.

| # | Validation | Method | Blocks |
|---|-----------|--------|--------|
| 1 | Confirm stakeholder agreement on pilot repo selection | Approval from Thomas Reddy | T2.1 sign-off |
| 2 | Confirm Dockerfile and Docker ignore ownership route | Check local headers plus RepoSync source repo | Unblocks durable S3 change route |
| 3 | Confirm approved image-source / Artifactory path for alternative runtime base | ACP/security/Artifactory confirmation | Required before production base-image recommendation |
| 4 | Capture local Maven/test feedback timings if in scope | Direct local command timings | Completes local developer-feedback baseline |
| 5 | Confirm long-term reliability KPI, if required | Define sample window; distinguish failed vs cancelled | Optional T2.2 reliability follow-up |
| 6 | Review `fdp-cmd-adaptor-roro-tsv` beyond Dockerfile/build-shape if broader reuse is claimed | Source-level comparison | Completes stronger portability follow-up |

### Confidence Levels

| Metric | Confidence | Rationale |
|--------|:----------:|-----------|
| CI elapsed duration (13:35 average from N=10 sample) | High for reviewed mixed-branch sample | Based on manual Drone UI read |
| Docker image size (906MB) | High | Measured locally after required Maven artefacts were present |
| Build context size (191.27MB transferred) | High for measured local build | Docker build output from successful full build |
| Integration-test outer step (09:43 average) | High for visible Drone step timing | Reused from T2.2 N=10 sample; internal breakdown is not N=10 |
| Container count / full-stack shape | **High** | SNS count confirmed; DVLA excerpts show comparable compose orchestration |
| DVLA portability | High for structure, low for timings | Source excerpts confirm comparable pipeline/build/test shape; actual timings are not measured |
