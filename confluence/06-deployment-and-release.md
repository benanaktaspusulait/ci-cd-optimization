# Deployment and Release Context

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Context retained; separate CD review proposed |
| **Last updated** | 2026-08-03 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

> **This page is context only.** Deployment and release improvements are outside the initial pilot scope. This information is documented for awareness and to inform future work.

> The proposed `kd`/Helm, PVC lifecycle and legacy-component review is a separately gated task workstream in **Post-Pilot Container and CD Delivery**. It is not Story 7, part of Story 6 or an approved migration.

---

## Deploy Pipeline

Deployment is managed through the **MMA service repository** (Helm-based), not through individual adaptor repositories. The deploy pipeline is a separate Drone pipeline.

```text
Tag pipeline (per-adaptor repo):
  → Maven clean install + test
  → Trivy scan + Sonar scan
  → Docker build + push to registry
  → Helm package + Helm dependency build + Artifactory upload

Service repo deploy pipeline (MMA Helm repo):
  → Pulls chart version from Artifactory
  → Helm package → lint → template → mass diff → upload
  → Deploys to Kubernetes (dev → SIT → bVal → prod)
```

---

## Release Flow

```text
feature/MMA-XXXXX  →  develop  →  release/X.Y.Z  →  tag (vX.Y.Z)  →  tag pipeline  →  Artifactory
                                                                                            │
                                                Service repo deploy pipeline  ◄─────────────┘
                                                dev → SIT (QAT approval) → bVal → prod
```

- **Feature branches:** created from Jira tickets, developed, MR into `develop`.
- **Release branches:** cut from `develop` when sprint is ready (e.g. `release/5.9.0`).
- **Tags:** developer creates tag on release branch → triggers tag pipeline (Maven + Trivy + Sonar + Helm + Artifactory upload).
- **Deploy:** service repo picks up new chart version and deploys via Helm to Kubernetes.
- **Release day:** Thursday.

---

## Environments

| Environment | Purpose | Approval gate |
|-------------|---------|---------------|
| dev | Development testing | None (automatic on merge to develop) |
| SIT | System Integration Testing | QAT must approve before promotion |
| bVal | Business Validation (more data than prod in some cases) | TBC |
| prod | Production | TBC |

---

## Rollback

**Current state:** No automated rollback exists. If a deployment causes issues:
- Team attempts to fix forward.
- Manual `helm rollback` is possible but not documented as standard procedure.
- No pipeline step triggers automatic rollback on failure.

This was confirmed in knowledge transfer sessions. Automated rollback is a post-pilot improvement recommendation.

---

## Feature Flags

Feature activation is controlled through **Helm values files**, not code deployments:
- A service may be deployed but specific features disabled per environment.
- Dev teams decide what is enabled where.
- This means deployment success ≠ feature activation ≠ functional validation.

---

## Validation Stages

| Stage | Who validates | Method |
|-------|---------------|--------|
| CI (tag pipeline) | Automated | Maven tests + Trivy + Sonar |
| Post-deploy (dev/SIT) | Dev teams | Playwright / Cypress (starting to adopt) |
| SIT gate | QAT | Manual approval before higher environments |
| bVal/prod | TBC | TBC |

---

## Release Automation (In Progress)

A separate project (Gareth Andrews) is automating:
- Service chart management (currently manual).
- Release-branch → tag → deploy flow automation.
- Jira ticket status integration and changelog generation.

The CI/CD optimisation pilot and release automation are complementary but separate:
- **Pilot:** improves build + test speed.
- **Release automation:** improves deploy + release management.

Coordination is needed to avoid conflicting changes to pipeline or Helm chart structure. Story 6 findings should be shared with Gareth's project.

---

## Why This Is Outside Pilot Scope

The pilot focuses on **build + test** (faster builds, smaller images, deterministic tests). Deployment focuses on **deploy + operate** (rollback, feature activation, environment parity). Different owners, timelines, and risk profiles.

Pilot findings may inform future release engineering improvements, but the pilot does not change the deploy pipeline.

---

*Feedback or questions? Contact the page owner or comment below.*
