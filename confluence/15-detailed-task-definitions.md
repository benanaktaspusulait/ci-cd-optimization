# Detailed Task Definitions

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

This page is the index for full task definitions. Each child page below contains the why, goal, scope and acceptance criteria for its stories.

---

## Task Definition Pages

| Page | Stories | Tasks |
|------|---------|:-----:|
| Task Definitions — Story 1 & 2 | Pipeline Assessment + Baseline & Pilot Scope | T1.1–T1.5, T2.1–T2.4 |
| Task Definitions — Story 3 & 4 | Docker Build Optimisation + Testcontainers Pilot | T3.1–T3.4, T4.1–T4.4 |
| Task Definitions — Story 5 & 6 | Compose Rationalisation + Findings & Ownership | T5.1–T5.3, T6.1–T6.3 |

---

## Quick Reference

| ID | Title | Story | SP |
|----|-------|-------|:--:|
| T1.1 | Review .drone.star pipeline structure | 1 | 2 |
| T1.2 | Identify local vs RepoSync boundaries | 1 | 1 |
| T1.3 | Map CI steps, DIND and Compose usage | 1 | 2 |
| T1.4 | Assess Testcontainers feasibility in Drone | 1 | 2 |
| T1.5 | Assess BuildKit/cache feasibility | 1 | 1 |
| T2.1 | Compare candidate pipelines and select pilot repo | 2 | 1 |
| T2.2 | Capture CI/CD pipeline baseline | 2 | 2 |
| T2.3 | Capture Docker build & image-size baseline | 2 | 1 |
| T2.4 | Capture integration-test baseline | 2 | 2 |
| T3.1 | Review current Dockerfile & build context | 3 | 2 |
| T3.2 | Add or validate .dockerignore | 3 | 1 |
| T3.3 | Apply Dockerfile layering / cache improvement | 3 | 2 |
| T3.4 | Measure local & CI build impact | 3 | 2 |
| T4.1 | Select candidate dependency/test | 4 | 1 |
| T4.2 | Implement Testcontainers setup | 4 | 3 |
| T4.3 | Compare with docker-compose flow | 4 | 2 |
| T4.4 | Document findings & constraints | 4 | 1 |
| T5.1 | Map services started by docker-compose | 5 | 1 |
| T5.2 | Classify services & usage | 5 | 2 |
| T5.3 | Recommend reduced Compose role | 5 | 2 |
| T6.1 | Consolidate pilot findings | 6 | 2 |
| T6.2 | Classify ownership & recommend target board | 6 | 2 |
| T6.3 | Share findings with stakeholders | 6 | 1 |

**Total: 23 tasks, 35 SP** (~15 working days, part-time over 4 weeks).

---

*Feedback or questions? Contact the page owner or comment below.*
