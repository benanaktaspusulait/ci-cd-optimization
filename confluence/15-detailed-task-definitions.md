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

| Page | Story | Tasks |
|------|-------|:-----:|
| Task Definitions — Story 1: Pipeline Assessment | 1 | T1.1–T1.5 |
| Task Definitions — Story 2: Baseline & Pilot Scope | 2 | T2.1–T2.4 |
| Task Definitions — Story 3: Docker Build Optimisation | 3 | T3.1–T3.4 |
| Task Definitions — Story 4: Testcontainers Pilot | 4 | T4.1–T4.4 |
| Task Definitions — Story 5: Docker Compose Rationalisation | 5 | T5.1–T5.2 |
| Task Definitions — Story 6: Pilot Outcome, Ownership and Adoption | 6 | T6.1–T6.2 |

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
| T5.1 | Validate current Compose scope | 5 | 3 |
| T5.2 | Decide the target Compose role | 5 | 2 |
| T6.1 | Classify pilot outcomes and ownership routes | 6 | 4 |
| T6.2 | Decide adoption route and publish pilot outcome | 6 | 2 |

**Total: 21 tasks, 36 SP** (indicative sizing; part-time over 4 weeks).

---

*Feedback or questions? Contact the page owner or comment below.*
