# Story 2 — Baseline & Pilot Scope: Summary

| Field | Value |
|-------|-------|
| **Status** | Draft baseline pack — T2.1 stakeholder confirmation pending; estimates pending Drone UI + local build confirmation |
| **Date** | 2026-06-11 |

---

## Deliverables

| Task | File | Status |
|------|------|:------:|
| T2.1 — Select pilot repo | [T2.1-select-repo.md](./T2.1-select-repo.md) | ⚠️ pending stakeholder confirmation |
| T2.2 — Pipeline baseline | [T2.2-pipeline-baseline.md](./T2.2-pipeline-baseline.md) | ✅ (estimated) |
| T2.3 — Docker build & image baseline | [T2.3-build-image-baseline.md](./T2.3-build-image-baseline.md) | ✅ (estimated) |
| T2.4 — Integration test baseline | [T2.4-integration-test-baseline.md](./T2.4-integration-test-baseline.md) | ✅ |

---

## Baseline Summary Table

> ⚠️ **All "Before" numbers below are ESTIMATES** based on static analysis. See "Validation Needed" section below for confirmation steps.

| Metric | Baseline (estimated) | Target | Story |
|--------|---------------------|--------|-------|
| CI pipeline duration | ~10-15 min | ≥ 20% ↓ (post-platform) | S3, S4 |
| Full develop push pipeline | ~25-40 min | — | — |
| Docker build time (local, cold) | ~2-3.5 min | ≥ 30% ↓ | S3 |
| Final image size | ~530-610 MB | ≥ 30% ↓ (≤ 370 MB) | S3 |
| Build context size | ~110-170 MB | ≥ 50% ↓ (≤ 80 MB) | S3 |
| Integration test startup | ~2-4 min (full stack ready) | < 30s (Testcontainers subset) | S4 |
| Containers required for integration tests | 20 defined (17 started in CI) | Reduced subset | S4, S5 |
| Test isolation | None (shared state) | Per-test-class | S4 |
| `.dockerignore` | Does not exist | Present | S3 |
| Base image | `amazoncorretto:17` (full JDK, ~450MB) | JRE-only Alpine (~180MB) | S3 |

---

## Key Decisions Made

1. **Working pilot recommendation:** `fdp-cmd-adaptor-sns` — representative, low-risk, portable patterns; DVLA lightweight comparison completed from provided source excerpts; stakeholder confirmation still required
2. **Measurement method:** Drone UI (last 10 runs on develop) for CI; `time docker build` for local
3. **First Testcontainers candidate:** Redis (simplest, fastest startup, best isolation benefit)
4. **Biggest Docker optimisation opportunity:** Switch base image from full JDK to JRE-only Alpine (60% size reduction)

---

## Validation Needed

> ⚠️ **All numbers in this story are ESTIMATES** derived from structural analysis of `.drone.star`, the Dockerfile, and `docker-compose.yml`. They require confirmation via Drone UI and local docker builds.

| # | Validation | Method | Blocks |
|---|-----------|--------|--------|
| 1 | Run `docker build` locally to get actual image size + build time | `time docker build` + `docker images` | Replaces estimates in T2.3 |
| 2 | Check Drone UI for last 10 develop pipeline runs | Manual read of step durations | Replaces estimates in T2.2 |
| 3 | Confirm `.dockerignore` is not RepoSync-managed | Check RepoSync source repo | Unblocks T3.2 |
| 4 | Confirm stakeholder agreement on pilot repo selection | Approval from Thomas Reddy | T2.1 sign-off |
| 5 | Ask team about known flaky tests | Team discussion | T2.4 completeness |

### Confidence Levels

| Metric | Confidence | Rationale |
|--------|:----------:|-----------|
| CI pipeline duration (~10-15 min) | Medium | Based on step count/complexity in .drone.star; real runs vary |
| Docker image size (~530-610 MB) | High | Based on known `amazoncorretto:17` base size + JAR sizes |
| Build context size (~110-170 MB) | Medium | Based on typical Java module structure; not measured |
| Integration test startup (~2-4 min) | Medium | Based on service count and JVM warmup estimates |
| Container count (20 defined) | **Confirmed** | Counted from docker-compose.yml directly |
