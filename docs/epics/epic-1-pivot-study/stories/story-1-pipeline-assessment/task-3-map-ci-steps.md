# T1.3 — Map CI pipeline steps, DIND usage and Docker Compose commands

**Story:** [Story 1 — Pipeline Assessment](README.md)

| Field | Value |
|-------|-------|
| **ID** | T1.3 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 1 — Drone/RepoSync Pipeline Assessment |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `drone`, `docker-compose`, `dind`, `mapping` |
| **Sprint** | Week 1 |
| **Depends on** | T1.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The current CI pipeline is heavy — multiple docker-compose up/down cycles, wait containers, aggregator startups, and Maven builds. Understanding exactly what happens (and how long each step takes) is essential for identifying optimisation opportunities and measuring baseline.

## Goal
Produce a step-by-step map of the CI pipeline with timing data where available.

## Scope
For the CI pipeline (`ci_pipeline` in `.drone.star`), document each step:
- Step name and image
- Docker Compose commands executed (which services, detached or foreground)
- DIND interactions (docker build, docker push, compose up)
- Wait/health-check mechanisms
- Estimated or measured duration (from Drone UI)
- Potential duplicate work (e.g. `mvn clean install` + compose `integration-tests` container both running Maven)
- **CI pipeline vs deploy pipeline boundary** — clearly separate what the adaptor repo's CI does vs what the MMA service repo's deploy pipeline does (pilot scope is CI only)

Expected pipeline map:
```
RepoSync Version → Secrets → Wait for Docker → Extract Info →
Kafka & Redis (compose) → Aggregators (compose) → mvn clean install →
Command Adaptor (compose) → Pre-Integration Tests (compose) →
Integration Tests (compose) → Sonar → Trivy → Slack
```

## Acceptance criteria
- [ ] All CI pipeline steps are listed with their purpose
- [ ] Docker Compose commands and services per step are documented
- [ ] DIND usage points are identified
- [ ] Step durations are captured (from recent Drone runs if available)
- [ ] Potential duplicate work or unnecessary waits are flagged
