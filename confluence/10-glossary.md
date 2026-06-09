# Glossary

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

---

## Purpose

This page defines all terminology, abbreviations, and technical concepts used across the CI/CD optimisation pilot documentation. Definitions are scoped to how terms are used within this project context.

---

## Terms and Abbreviations

| Term | Full Name / Definition |
|------|----------------------|
| **ACP** | Application Container Platform — the organisational CI/CD tooling platform. Owns Drone runners, container registries, BuildKit enablement, RepoSync, and related CI infrastructure. |
| **ADR** | Architecture Decision Record — a structured document capturing the context, decision, consequences, and alternatives for a significant technical choice. |
| **Artifactory** | JFrog Artifactory — the artifact repository used for Maven dependencies and Helm charts. Serves as a dependency proxy and release artifact store. |
| **BuildKit** | Docker's modern build backend. Provides advanced features including cache mounts, multi-stage build optimisation, parallel stage execution, and registry-backed remote cache. Enabled via `DOCKER_BUILDKIT=1` or Docker daemon configuration. |
| **bVal** | Business Validation environment — a pre-production environment intended to mirror production configuration as closely as possible. Used for business acceptance testing before production deployment. |
| **CIT** | TBC — referenced in context of DSA ETO / Enabling. Related to cross-cutting infrastructure and tooling. |
| **CST** | Cerberus Support Team (or Cerberus Delivery) — the team responsible for FDP adaptor development and the primary owner of this pilot initiative. |
| **DIND** | Docker-in-Docker — a pattern where Docker runs inside a container. In the Drone CI environment, DIND is provided as a sidecar container accessible at `tcp://docker:2375`. Used for building Docker images and (potentially) running Testcontainers in CI. |
| **DoD** | Definition of Done — the criteria that must be met before a story or task is considered complete. |
| **Drone** | The CI/CD system used for pipeline execution. Runs on a Kubernetes runner (pods are created per pipeline run). Pipeline configuration is defined in `.drone.star` (Starlark) and managed centrally via RepoSync. |
| **DSA ETO** | DSA Engineering, Technology & Operations (also referred to as Enabling or ETO) — the platform engineering function responsible for wider engineering patterns, shared tooling standards, and cross-project adoption. Ezhil's role encompasses this area. |
| **ECR** | Amazon Elastic Container Registry — one of the container image registries used for storing built Docker images. |
| **FDP** | Forms Data Platform — the broader programme containing the adaptor services that this pilot targets. |
| **Feature flag** | A mechanism for toggling features per environment without code changes. In this context, implemented via Helm values — different environments can enable or disable features by changing Helm chart values at deployment time. |
| **Helm** | A Kubernetes package manager. Used to define, install, and upgrade applications on Kubernetes clusters. Services are deployed via Helm charts stored in the MMA Helm repo. |
| **MMA Helm repo** | The dedicated repository containing Helm charts for service deployments. Each service has a chart defining its Kubernetes resources, configuration, and deployment strategy. |
| **MoSCoW** | A prioritisation framework: Must have, Should have, Could have, Won't have (this time). Used to classify pilot proposals by priority. |
| **MR** | Merge Request — a request to merge a feature branch into the main branch (equivalent to Pull Request in other platforms). Used in the team's source control workflow. |
| **PNR room** | A physical secure room required for production access. Production deployments and troubleshooting that require direct cluster access must be performed from this location (or via approved remote access). |
| **QAT** | Quality Assurance Testing — the testing team or testing phase responsible for validating application quality before production release. |
| **Remote cache** | BuildKit's registry-backed layer cache mechanism. Allows pushing build cache to a container registry (`--cache-to=type=registry`) and pulling it on subsequent builds (`--cache-from=type=registry`). Solves the ephemeral cache problem in Drone DIND where local cache is lost between pipeline runs. Requires ACP infrastructure. |
| **RepoSync** | A central synchronisation mechanism that manages `.drone.star` pipeline configuration across repositories. Changes to pipeline configuration are made centrally and synced to all managed repositories. Local edits to `.drone.star` are overwritten on next sync. Source repository: `dde-adaptor-reposync`. |
| **SBOM** | Software Bill of Materials — a formal, machine-readable inventory of all components, libraries, and dependencies in a software artifact. Used for supply-chain security and compliance auditing. |
| **SIT** | System Integration Testing environment — a lower environment used for early integration validation before promotion to bVal. |
| **Starlark** | A Python-like configuration language used by Drone for pipeline definitions (`.drone.star` files). Allows programmatic pipeline generation with loops, conditionals, and functions. |
| **Testcontainers** | A Java library (also available for other languages) that provides lightweight, throwaway containers for integration testing. Each test (or test class) declares the containers it needs; they are started fresh per test run, providing isolation and eliminating shared state. Requires Docker access at test time. |
| **Tools pod** | A Kubernetes pod provisioned with secrets and tools needed for deployment operations. Used by the team for manual operations that require access to cluster secrets or deployment tooling. |

---

## Environment Clarification

The following table clarifies the infrastructure components referenced throughout the documentation:

| Component | Technology / Location |
|-----------|----------------------|
| **Source hosting** | Self-hosted source control instance |
| **CI system** | Drone CI with Kubernetes runner |
| **Pipeline configuration** | `.drone.star` (Starlark) — managed centrally via RepoSync |
| **RepoSync source** | `dde-adaptor-reposync` repository |
| **Container registries** | `docker.digital.homeoffice.gov.uk`, Amazon ECR, Artifactory (Docker) |
| **Artifact repository** | Artifactory (Maven dependencies + Helm charts) |
| **Docker access in CI** | DIND sidecar at `tcp://docker:2375` (unencrypted, pod-internal) |
| **Deployment mechanism** | Helm charts via MMA Helm repo → SIT → bVal → Production |
| **Environment promotion** | SIT → bVal → Production (manual promotion) |
| **Feature toggling** | Helm values per environment |
| **Production access** | PNR room (physical) or approved remote mechanism |
| **Secrets in CI** | Drone secrets (injected as environment variables into pipeline steps) |
| **Runtime secrets** | Vault / envconsul (injected at container startup) |

---

*Feedback or questions? Contact the page owner or comment below.*
