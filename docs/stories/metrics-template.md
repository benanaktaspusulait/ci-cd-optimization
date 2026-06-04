# Metrics — Baseline & Results

Fill this in as the pilot progresses. Baseline values come from Story 2; "after" values from Stories 3–5.
Copy a fresh block per pilot iteration if you measure more than once. [← Back to overview](../../README.md)

> **How to measure:** record the method/source for every number so it can be repeated identically for the "after" run. Pipeline duration = rolling average over the last **N** runs (set N in T2.2).

---

## Pilot context

| Field | Value |
|-------|-------|
| Pilot repository | _TBD (T2.1)_ |
| Measurement date (baseline) | _YYYY-MM-DD_ |
| Measurement date (after) | _YYYY-MM-DD_ |
| N (runs averaged) | _TBD_ |
| Measured by | _TBD_ |

## Core metrics

| Metric | Baseline | After | Delta | Target | Source / method |
|--------|----------|-------|-------|--------|-----------------|
| Pipeline duration (avg) | | | | ≥ 20% ↓ (post-platform) | |
| Build stage duration | | | | — | |
| Unit test duration | | | | — | |
| Integration test duration | | | | — | |
| Docker build time (local) | | | | ≥ 30% ↓ | |
| Docker build time (CI) | | | | ≥ 20% ↓ (post-platform) | |
| Final image size | | | | ≥ 30% ↓ | |
| Integration test startup time | | | | < 30 sec | |
| Build context size | | | | ≥ 50% ↓ | |
| Failed-pipeline / flaky rate | | | | no regression | |
| Developer feedback loop (change → test green) | | | | ≤ 5 min | |
| Cache hit/miss rate (if available) | | | | — | |

## Notes & observations
- _Anything that affects interpretation: environment differences, one-off slow runs, cache warm/cold state, etc._

## Source data
- _Links to pipeline runs, build logs, or commands used._

## Source artefact mapping

Use this mapping when copying raw measurements into the core metrics table.

| Source | Produced by | Use for |
|--------|-------------|---------|
| `metrics-output/build-metrics.csv` | `scripts/measure-baseline.sh` in the selected pilot repo | Local warm/cold Docker build time and local image size |
| Drone build step logs | Drone CI pipeline UI (`docker build` step) | CI build duration and registry image size |
| Drone integration-test step logs | Drone CI pipeline UI (integration-tests step) | Integration-test startup + run duration |
| Drone pipeline UI / API | Pipeline listing and step timings | Rolling average pipeline duration and failed/flaky pipeline rate |

The metrics template remains the final human-readable summary; raw artefacts are supporting evidence and should be linked in the `Source / method` column.
