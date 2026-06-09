# Supporting Context

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

---

This page consolidates supporting context that readers may need while reviewing the pilot. It is self-contained: repository locations, external trackers, and historic planning documents are not required to understand the proposal.

## System Context

| Item | Purpose | Pilot relevance |
|------|---------|-----------------|
| `fdp-cmd-adaptor-dvla` | Candidate FDP command adaptor for DVLA trailer registration flows. | One possible pilot target; final selection happens in T2.1 after comparing at least two candidates. |
| RepoSync-managed pipeline configuration | Central mechanism that manages shared `.drone.star` pipeline content for adaptor repositories. | Explains why local pipeline edits are not durable and why central pipeline changes require ACP coordination. |
| MMA Helm service repository | Service deployment area for Helm packaging, linting, templating, diffing, and Kubernetes deployment. | Deployment context only; not changed by this CI/container pilot. |
| Operational runbooks | Approved release and production-operation steps for the service estate. | Relevant for deployment awareness and future release-safety work, but outside the pilot implementation scope. |

---

## Architecture Decision Records (ADRs)

| ADR | Title | Key decision |
|-----|-------|--------------|
| ADR-0001 | Pilot not rollout | Validate on one repo before proposing wider adoption |
| ADR-0002 | Testcontainers for integration tests | Pilot one dependency locally; reuse disabled in CI |
| ADR-0003 | Reduce Compose in CI, keep for local | CI: prefer Testcontainers; local: keep Compose |
| ADR-0004 | BuildKit cache + multi-stage builds | Multi-stage local; remote cache post-pilot (ACP) |
| ADR-0005 | CI runner Docker mode (Drone/DIND) | Assess DIND access; fallback = local-only Testcontainers |

Full ADR text with alternatives and consequences is captured on the Architecture Decisions (ADRs) page.

---

## KT Sessions (Knowledge Transfer)

| Date | Presenter | Topic | Key takeaways |
|------|-----------|-------|---------------|
| TBC | Liam Moncur | Deployment scripts / Helm | Deploy via MMA service repo; no automated rollback; feature flags via Helm values |
| TBC | Herbie Barnett / Benjamin Reynolds | Release tagging | Tag → tag pipeline → Maven + Trivy + Sonar + Helm package → Artifactory; Jira integration for changelog |
| TBC | Stephen Craine | Feature branch → release flow | Feature branches from Jira; Thursdays = release day; PNR room for prod access; Gareth automating release |

---

## Optional Technology References

These are optional vendor or tool references for deeper reading. The pilot pages include the required context inline.

| Topic | Reference |
|-------|-----------|
| Testcontainers | https://java.testcontainers.org/ |
| Testcontainers Kafka module | https://java.testcontainers.org/modules/kafka/ |
| Docker BuildKit | https://docs.docker.com/build/buildkit/ |
| Docker multi-stage builds | https://docs.docker.com/build/building/multi-stage/ |
| Drone CI | https://docs.drone.io/ |
| Drone Starlark | https://docs.drone.io/pipeline/scripting/starlark/ |
| Helm | https://helm.sh/docs/ |
| Confluent Platform (cp-kafka 7.5.5) | https://docs.confluent.io/platform/7.5/ |
| Amazon Corretto 17 | https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/ |
| Trivy | https://aquasecurity.github.io/trivy/ |

---

## Review Topics to Confirm

| Topic | Status |
|-------|--------|
| DSA Tech Strategy alignment for post-pilot platform work | TBC |
| ACP CI/CD tooling ownership and prioritisation route | TBC |
| Cerberus Delivery board and Jira issue ownership | TBC |

---

*Feedback or questions? Contact the page owner or comment below.*
