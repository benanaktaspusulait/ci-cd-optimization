# Story 6 — Pilot Outcome, Ownership and Adoption: Summary

| Field | Value |
|-------|-------|
| **Status** | In progress — classification prepared; owner review/share-out not evidenced |
| **Date consolidated** | 2026-07-23 |

---

## Deliverables

| Task | Primary output | Status |
|---|---|---|
| T6.1 — Classify pilot outcomes and ownership routes | [T6.1-classify-outcomes-and-ownership.md](./T6.1-classify-outcomes-and-ownership.md) | Done — evidence prepared |
| T6.2 — Decide adoption route and publish pilot outcome | [T6.2-decide-adoption-route.md](./T6.2-decide-adoption-route.md) | Not completed — materials prepared |

---

## Ownership routes

| Owner | Scope |
|---|---|
| **CST** | Local evidence, opt-in Redis pilot and authorised repository experiments |
| **RepoSync/platform** | Durable centrally managed files, CI configuration, Compose templates and orchestration decisions |
| **Wider ETO/enabling** | Shared base images, cross-repository patterns and organisation-wide infrastructure/policy |

## Cross-Cutting Architectural Findings

Two structural findings must be decided before a central cutover:

- **Dual container-lifecycle ownership** — Drone and Maven encode separate paths. A single owner remains a candidate, not an approved change.
- **Build/publish integrity gap** — build-once-promote remains a RepoSync/platform candidate, not a pilot implementation.

## Pilot Result

### Validated local outcomes

- Targeted `.dockerignore`: context `191.27MB` → `189B`; required artefacts preserved; image size unchanged; no cold-build or CI saving.
- Docker layer-order candidate: approximately 15–16x faster only for the tested local same-daemon warm-cache JAR-change scenario; no image-size, cold-build or CI improvement.
- Redis Testcontainers pilot: two opt-in local functional runs; no speed, flaky-test, Kafka/Schema Registry, full-E2E or CI benefit claimed.

### Pending closure

- Relevant owner review and adopt/candidate/stop decisions.
- RepoSync/platform routing for centrally owned changes.
- Stakeholder feedback and approvals.
