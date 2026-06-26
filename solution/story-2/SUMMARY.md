# Story 2 — Baseline & Pilot Scope: Summary

| Field | Value |
|-------|-------|
| **Status** | Draft baseline pack — SNS remains working pilot; first 3 SNS Drone CI timing samples captured; full N=10, failed/cancelled rate, stakeholder and local build confirmations pending |
| **Date** | 2026-06-11 |

---

## Deliverables

| Task | File | Status |
|------|------|:------:|
| T2.1 — Select pilot repo | [T2.1-select-repo.md](./T2.1-select-repo.md) | ⚠️ pending stakeholder confirmation |
| T2.2 — Pipeline baseline | [T2.2-pipeline-baseline.md](./T2.2-pipeline-baseline.md) | ⚠️ 3 actual samples captured; N=10 pending |
| T2.3 — Docker build & image baseline | [T2.3-build-image-baseline.md](./T2.3-build-image-baseline.md) | ⚠️ estimated; DVLA shape checked |
| T2.4 — Integration test baseline | [T2.4-integration-test-baseline.md](./T2.4-integration-test-baseline.md) | ⚠️ baseline drafted; DVLA shape checked |
| T2.5 — Baseline summary and re-measurement method | [T2.5-baseline-summary-remeasurement.md](T2.5-redis-testcontainers-pilot-plan.md) | ⚠️ summary drafted; stakeholder agreement pending |

---

## Baseline Summary Table

> ⚠️ **Pipeline duration now uses the first 3 actual SNS Drone UI samples.** Docker build/image, full develop push, failed/cancelled rate and some integration-test details still require confirmation. DVLA validates portability of the pipeline/build/test shape, not measured timing.

| Metric | Baseline | Target | Story |
|--------|---------------------|--------|-------|
| CI elapsed duration | 13:27 average from 3 successful samples; N=10 pending | ≥ 20% ↓ (post-platform) | S3, S4 |
| CI elapsed duration fastest / slowest | 13:25 / 13:30 | — | S3, S4 |
| Full develop push pipeline | TBC | — | — |
| Docker build time (local, cold) | ~2-3.5 min | ≥ 30% ↓ | S3 |
| Final image size | ~530-610 MB | ≥ 30% ↓ (≤ 370 MB) | S3 |
| Build context size | ~110-170 MB | ≥ 50% ↓ (≤ 80 MB) | S3 |
| Integration test startup | ~2-4 min (full stack ready) | < 30s (Testcontainers subset) | S4 |
| Containers required for integration tests | SNS: 20 defined (17 started in CI); DVLA excerpts show comparable full-stack compose shape | Reduced subset | S4, S5 |
| Test isolation | None (shared state) | Per-test-class | S4 |
| `.dockerignore` | SNS: does not exist; DVLA: TBC | Present | S3 |
| Base image | `amazoncorretto:17` (full JDK, ~450MB) | JRE-only Alpine (~180MB) | S3 |

---

## Key Decisions Made

1. **Working pilot recommendation:** `fdp-cmd-adaptor-sns` — representative, already analysed, lower-risk pilot target; stakeholder confirmation still required
2. **Lightweight comparison candidate:** `fdp-cmd-adaptor-dvla` — source excerpts validate the same RepoSync pipeline family, Dockerfile pattern and integration-test orchestration shape; not selected as pilot target
3. **Measurement method:** Drone UI/manual read for CI elapsed and visible step timings; target is last 10 successful runs where possible; `time docker build` for local Docker metrics
4. **First Testcontainers candidate:** Redis (simplest, fastest startup, best isolation benefit)
5. **Biggest Docker optimisation opportunity:** Switch base image from full JDK to JRE-only Alpine (60% size reduction)
6. **Baseline summary deliverable:** T2.5 now exists as a separate solution document; this file remains the Story 2 overview/index.

---

## Validation Needed

> ⚠️ **Only the first 3 SNS CI elapsed samples are measured so far.** The full N=10 baseline, failed/cancelled rate, Docker build/image and some integration-test values still require confirmation.

| # | Validation | Method | Blocks |
|---|-----------|--------|--------|
| 1 | Run `docker build` locally to get actual image size + build time | `time docker build` + `docker images` | Replaces estimates in T2.3 |
| 2 | Complete SNS Drone UI N=10 successful-run sample | Manual read of elapsed + step durations | Completes T2.2 baseline |
| 2a | Capture failed/cancelled rate | Count failed/cancelled runs across last 20 runs | Completes T2.2 reliability baseline |
| 3 | Confirm `.dockerignore` is not RepoSync-managed | Check RepoSync source repo | Unblocks T3.2 |
| 4 | Confirm stakeholder agreement on pilot repo selection | Approval from Thomas Reddy | T2.1 sign-off |
| 5 | Ask team about known flaky tests | Team discussion | T2.4 completeness |
| 6 | Optional DVLA measured comparator check | Drone UI and/or local build for DVLA, only if comparator timings are required | Improves portability confidence |

### Confidence Levels

| Metric | Confidence | Rationale |
|--------|:----------:|-----------|
| CI elapsed duration (13:27 average from 3 samples) | High for current 3-run sample, medium for final baseline | Based on actual Drone UI screenshots/manual read; full N=10 still pending |
| Docker image size (~530-610 MB) | High | Based on known `amazoncorretto:17` base size + JAR sizes |
| Build context size (~110-170 MB) | Medium | Based on typical Java module structure; not measured |
| Integration test startup (~2-4 min) | Medium | Based on service count and JVM warmup estimates |
| Container count / full-stack shape | **High** | SNS count confirmed; DVLA excerpts show comparable compose orchestration |
| DVLA portability | High for structure, low for timings | Source excerpts confirm comparable pipeline/build/test shape; actual timings are not measured |
