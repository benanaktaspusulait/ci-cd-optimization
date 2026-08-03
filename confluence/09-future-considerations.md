# Future Considerations

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Candidate register — routed to the proposed follow-up epic where applicable |
| **Last updated** | 2026-08-03 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

---

## Purpose

This page captures improvements, architecture decisions, and technical opportunities that are **out of scope for the current pilot** but may be pursued after pilot evidence is collected. Items here require either ACP/DSA ETO prioritisation or further investigation beyond the single-repo pilot.

SNS production implementation and the separately gated `kd`/Helm, PVC and legacy CD pipeline review are task workstreams under **Post-Pilot Container and CD Delivery**. The epic is proposed/new; it is not an additional pilot story or an approval.

> **Prioritisation disclaimer:** ACP and DSA ETO capacity is currently focused on Core Cloud and Data Platform. Any request for platform-level work must be backed by measured evidence from the pilot and aligned with ETO prioritisation cycles. Items on this page are candidates, not commitments.

---

## F1–F7: Production Readiness Considerations

These areas must be addressed before any pilot findings could be rolled out to production pipelines at scale.

| ID | Area | Current State | Gap | Priority | Requires |
|----|------|---------------|-----|----------|----------|
| **F1** | Rollback strategy | KT confirmed: rollback is manual only (previous Helm release via `helm rollback` or re-deploy of prior image tag). No automated rollback mechanism exists. | No automated rollback. Manual process is understood but not documented in runbook form for all services. | High | CST: document process. ACP/ETO: assess automated rollback feasibility. |
| **F2** | Monitoring & observability | TBC — pipeline metrics (build time, test duration, failure rate) are not currently tracked centrally. | No pipeline performance baseline tracking. Cannot detect regressions. | High | ACP: Drone metrics export. CST: define key metrics. |
| **F3** | Artifact management | Images pushed to ECR and docker.digital.homeoffice.gov.uk. Helm charts in Artifactory. Retention policies TBC. | Image retention/cleanup policy unclear. Old images may accumulate. | Medium | ACP: registry retention policies. CST: tagging strategy. |
| **F4** | Environment strategy | Environments: SIT → bVal → Production. bVal is intended to mirror production configuration. Feature flags via Helm values. | Parity between bVal and production is aspirational — drift may exist. No automated parity checks. | Medium | CST: environment parity audit. ACP: environment provisioning. |
| **F5** | Cost impact | TBC — build time reduction may reduce Drone runner compute costs. Image size reduction reduces registry storage and pull times. | No cost baseline. Cannot quantify savings without measurement. | Low | ACP: compute cost visibility. CST: estimate from pilot metrics. |
| **F6** | Compliance & supply chain | No current SBOM generation. Image scanning exists (TBC — Trivy or Snyk). No image signing. | Gap against emerging supply-chain security expectations. | Medium | ACP/ETO: org-wide scanning gates and signing infrastructure. CST: SBOM generation in pilot. |
| **F7** | Troubleshooting & support | Troubleshooting currently relies on Drone build logs and developer knowledge. No structured troubleshooting guide for pipeline failures. | No runbook for common CI failures. New team members lack context. | High | CST: create troubleshooting guide from pilot learnings. |

### Recommended Priority Order

**F1 + F7 → F2 → F3 → F4 → F6 → F5**

Rationale:

- F1 (rollback) and F7 (troubleshooting) are high-impact, low-dependency items that CST can start immediately.
- F2 (monitoring) provides the foundation for detecting regressions from any future changes.
- F3 (artifacts) and F4 (environments) are medium-priority infrastructure items.
- F6 (compliance) is important but depends on ACP/ETO providing organisational gates.
- F5 (cost) is lowest priority — useful but not blocking.

---

## Architecture Decisions (Post-Pilot)

### Base Image Strategy

A 4-layer base image strategy could standardise container builds across FDP:

| Layer | Purpose | Owner | Example |
|-------|---------|-------|---------|
| 1. OS base | Minimal Linux distribution | ACP/ETO | `amazoncorretto:17-alpine` or approved equivalent |
| 2. Runtime | JRE + common runtime libraries | ACP/ETO | Security patches, certificates, timezone data |
| 3. Middleware | Application framework base | CST / team | Spring Boot actuator, common logging config |
| 4. Application | Compiled JAR + config | Service team | The specific microservice |

**Ownership:** Layers 1–2 would need to be owned by ACP or ETO (Ezhil's role) to ensure timely security patching and compliance. Layer 3 could be team-owned. Layer 4 is always service-specific.

**Status:** Requires ACP/ETO agreement to own and maintain base images. Not actionable until after pilot evidence demonstrates the value of multi-stage builds.

### BuildKit Remote Cache

BuildKit supports pushing layer cache to a container registry (`--cache-to=type=registry`). This would solve the ephemeral cache problem in Drone DIND — each CI build could pull cache from the registry rather than starting cold.

**Requirements:**

- ACP must provide a cache-enabled registry endpoint (or allow cache push to existing ECR/Artifactory).
- `.drone.star` (RepoSync) must be updated to enable BuildKit and pass cache arguments.
- Cache invalidation strategy must be defined (TTL, branch-scoped cache keys).

**Status:** Deferred to post-pilot. Requires ACP infrastructure and prioritisation.

---

## Technical Opportunities

### Selective Test Execution

Rather than running the full test suite on every commit, selective execution could run only tests affected by changed files. Approaches include:

- Maven module-level selection (if project is multi-module).
- Test impact analysis tools (e.g., Selektor, custom dependency graph).
- Separate "fast" and "full" test profiles triggered by branch or label.

**Benefit:** Faster feedback on feature branches. Full suite still runs on merge to main.

### Reusable Drone Templates via RepoSync

RepoSync already manages `.drone.star` centrally. This mechanism could be extended to provide reusable pipeline templates:

- Standard build steps with configurable parameters.
- Opt-in features (BuildKit, Testcontainers, SBOM generation) as template options.
- Per-repo overrides within a governed template structure.

**Benefit:** Consistency across repositories without per-repo maintenance. Changes propagate centrally.

**Requires:** ACP/RepoSync team to design and implement the template mechanism.

### Contract Testing (Pact)

For services that communicate via APIs or messaging, contract testing (e.g., Pact) could replace some integration tests:

- Consumer-driven contracts verify API compatibility without starting the full provider.
- Faster than full integration tests — no container startup required.
- Catches breaking changes before deployment.

**Benefit:** Reduced reliance on heavy integration test environments for API compatibility validation.

### Ephemeral Review Environments

Spin up a full (or partial) environment per merge request for manual review and automated acceptance testing:

- Kubernetes namespace per MR.
- Torn down automatically on merge or close.
- Enables QAT review without shared environment contention.

**Benefit:** Parallel testing without environment conflicts.

**Requires:** ACP infrastructure for dynamic namespace provisioning. Significant effort — low priority for now.

### Dependency Proxy

A local proxy (e.g., Artifactory or Nexus) for Maven Central and Docker Hub:

- Reduces external network dependency during builds.
- Speeds up dependency resolution.
- Provides audit trail of consumed dependencies.

**Benefit:** Build resilience and speed. May already partially exist via Artifactory (TBC).

### Release Automation

Automated semantic versioning, changelog generation, and release tagging:

- Conventional commits → automated version bump.
- Changelog generated from commit messages.
- Tag triggers release pipeline.

**Coordination:** Gareth Andrews is understood to be working on a related release/deployment improvement project. Any release automation work should coordinate with Gareth's project to avoid duplication or conflict.

---

## Deployment & Release Safety

Context from knowledge transfer sessions:

### Current Deployment Model

- Services are deployed via the **MMA Helm repo** — a dedicated repository containing Helm charts for each service.
- Deployments target: SIT → bVal (business validation) → Production.
- **bVal** is intended to mirror production configuration as closely as possible.
- **Feature flags** are implemented via Helm values — toggling features per environment without code changes.

### Rollback

- KT confirmed: **rollback is manual only**.
- Process: re-deploy a previous image tag or run `helm rollback` to a prior Helm release.
- **No automated rollback** mechanism exists (no canary analysis, no automated health-gate rollback).
- Manual rollback is understood by the team but may not be formally documented in a runbook for all services.

### Production Access

- Production changes require access via the **PNR room** (physical secure room) or approved remote access mechanism.
- Runbook repositories exist for production operations (TBC — specific repo locations).

### Coordination with Gareth Andrews' Project

Gareth Andrews is working on deployment and release improvements. Specific scope TBC. Any future automation of the release process from this pilot should coordinate with Gareth's project to:

- Avoid duplication of effort.
- Ensure compatibility with any new release tooling being introduced.
- Align on versioning and tagging conventions.

---

*Feedback or questions? Contact the page owner or comment below.*
