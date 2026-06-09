# Technical Details

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

> This page contains deep technical content for engineers. Non-technical readers should refer to the parent overview and proposal matrix.

---

## Technical Child Pages

| Page | Covers |
|------|--------|
| Technical Details — Docker Build & Infrastructure | Dockerfile optimisation (current vs proposed multi-stage), .dockerignore, Docker Compose CI services, BuildKit cache strategy, base image strategy |
| Technical Details — Testcontainers | Testcontainers approach, container configurations (Redis, Kafka, LocalStack), Spring/Cucumber integration, Maven dependencies and profile, CI feasibility, reuse policy |

---

## Summary

The pilot's technical approach has two main tracks:

1. **Docker Build** — multi-stage Dockerfile, .dockerignore, BuildKit cache mounts (local), reduced Compose CI role.
2. **Testcontainers** — isolated, deterministic integration tests starting with one dependency (Redis or Kafka), running locally first, CI feasibility assessed in Story 1.

Both tracks produce measurable before/after evidence. Code Examples and Templates page provides copy/adapt snippets.

---

*Feedback or questions? Contact the page owner or comment below.*
