# Metrics — Baseline & Results

Fill this in as the pilot progresses. Baseline values come from Story 1; "after" values from Stories 2–3.
Copy a fresh block per pilot iteration if you measure more than once. [← Back to overview](../../README.md)

> **How to measure:** record the method/source for every number so it can be repeated identically for the "after" run. Pipeline duration = rolling average over the last **N** runs (set N in T1.2).

---

## Pilot context

| Field | Value |
|-------|-------|
| Pilot repository | _TBD (T1.1)_ |
| Measurement date (baseline) | _YYYY-MM-DD_ |
| Measurement date (after) | _YYYY-MM-DD_ |
| N (runs averaged) | _TBD_ |
| Measured by | _TBD_ |

## Core metrics

| Metric | Baseline | After | Delta | Target | Source / method |
|--------|----------|-------|-------|--------|-----------------|
| Pipeline duration (avg) | | | | ≥ 20% ↓ | |
| Build stage duration | | | | — | |
| Unit test duration | | | | — | |
| Integration test duration | | | | — | |
| Docker build time (local) | | | | ≥ 20% ↓ | |
| Docker build time (CI) | | | | ≥ 20% ↓ | |
| Final image size | | | | ≥ 15% ↓ | |
| Integration test startup time | | | | — | |
| Build context size | | | | — | |
| Failed-pipeline / flaky rate | | | | no regression | |
| Cache hit/miss rate (if available) | | | | — | |

## Notes & observations
- _Anything that affects interpretation: environment differences, one-off slow runs, cache warm/cold state, etc._

## Source data
- _Links to pipeline runs, build logs, or commands used._
