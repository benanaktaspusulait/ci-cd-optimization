# Deployment and Release Context

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

> **This page is context only.** Deployment and release improvements are outside the initial pilot scope. This information is documented for awareness and to inform future work.

---

## Deploy Pipeline

Deployment is managed through the **MMA service repository** (Helm-based), not through individual adaptor repositories.

```text
Tag pipeline (per-adaptor) → builds image + Helm chart → uploads to Artifactory
                                                                     │
Service repo pipeline (MMA) ← pulls chart version from Artifactory ──┘
    → Helm package → lint → template → mass diff → upload → deploy to K8s
```

---

## Release Flow

```text
feature/MMA-XXXXX → develop → release/X.Y.Z → tag (vX.Y.Z) → tag pipeline → Artifactory
                                                                                    │
                                              Service repo deploy pipeline ◄────────┘
                                              dev → SIT (QAT approval) → bVal → prod
```

- **Feature branches:** created from Jira tickets, developed, MR into `develop`.
- **Release branches:** cut from `develop` when sprint is ready.
- **Tags:** developer creates tag on release branch → triggers tag pipeline.
- **Deploy:** service repo picks up new chart version and deploys via Helm.
- **Release day:** Thursday.

---

## Environments

| Environment | Purpose | Approval gate |
|-------------|---------|---------------|
| dev | Development testing | None (automatic) |
| SIT | System Integration Testing | QAT must approve before promotion |
| bVal | Business Validation | TBC |
| prod | Production | TBC |

---

## Rollback

**Current state:** No automated rollback exists. If a deployment causes issues, the team uses manual `helm rollback` or attempts to fix forward.

This was confirmed in KT sessions. Automated rollback is a post-pilot improvement recommendation (see [Risks and DACI](03-risks-and-daci.md)).

---

## Feature Flags

Feature activation is controlled through Helm values files, not code deployments. A service may be deployed but specific features disabled per environment. Dev teams decide what is enabled where.

---

## Validation

| Stage | Who validates | Method |
|-------|---------------|--------|
| CI | Pipeline | Maven tests + Trivy + Sonar |
| Post-deploy (dev/SIT) | Dev teams | Playwright / Cypress (starting to adopt) |
| SIT gate | QAT | Manual approval before higher environments |
| bVal/prod | TBC | TBC |

---

## Release Automation (In Progress)

A separate project (Gareth Andrews) is automating service chart management and release flow. The pilot should coordinate with this project to avoid conflicting changes. Gareth's work aims to reduce manual steps around tagging, chart versioning, and deployment triggers.

---

## Why This Is Outside Pilot Scope

The pilot focuses on **build + test** (faster builds, smaller images, deterministic tests). Deployment and release focuses on **deploy + operate** (rollback, feature activation, environment parity). They are complementary but have different owners, timelines, and risk profiles.

Pilot findings may inform future release engineering improvements, but the pilot does not change the deploy pipeline.

---

*Feedback or questions? Contact the page owner or comment below.*
