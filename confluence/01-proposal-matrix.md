# Proposal Overview Matrix — Validated Outcome

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Validated outcome |
| **Last updated** | 2026-09-17 |

The original proposal matrix is retained here as a record of what was investigated. The important change is that proposal status is now based on implementation evidence from SNS, PNR and PCDP rather than aspiration.

| # | Original proposal | Final outcome | Reuse classification | Notes |
|---|---|---|---|---|
| 1 | `.dockerignore` validation | **Retained** | Reusable | Restrict context to actual Dockerfile inputs; exact patterns remain repo-specific. |
| 2 | Multi-stage Dockerfile | **Not adopted as a blanket requirement** | Reassess per repo | Existing adaptor build flow already produces the executable JAR before image packaging. Layer ordering around expensive stable setup was the useful change. |
| 3 | BuildKit local cache mounts | **Partially superseded** | Reusable principle | Final work focused on default BuildKit builder and registry-backed reuse where supported; do not require cache mounts merely because they were in the original proposal. |
| 4 | One-dependency Testcontainers prototype | **Expanded and retained** | Core reusable pattern | Progressed to test-owned Redis/Kafka/Schema Registry and required aggregate dependencies in CI, with repo-specific application/test wiring. |
| 5 | Docker Compose CI rationalisation | **Retained / implemented in CI path** | Core reusable pattern | Compose-heavy orchestration was replaced by consolidated Maven/Testcontainers ownership for the validated path; Compose can remain for local/exploratory use. |
| 6 | Trivy scan in CI | **Retained and optimised** | Core reusable pattern | Existing scan policy remains; vulnerability/Java DB preparation moved earlier so the final scan contributes less to the critical path. |
| 7 | BuildKit remote cache | **Validated in repository work; durable centralisation pending** | Shared RepoSync pattern | Shared-read / isolated-write cache strategy used where applicable. Do not attribute unrelated builder differences to cache benefit. |
| 8 | Testcontainers in CI | **Validated** | Core reusable pattern | Works with Drone Kubernetes + DIND and explicit Docker/Testcontainers configuration. |
| 9 | Shared base-image strategy | **Not required by this optimisation** | Separate platform concern | No image-size or base-image programme is claimed as part of the validated result. |
| 10 | Reusable Drone pipeline templates | **Next adoption step** | RepoSync-managed | Cross-repository evidence now exists; common pipeline elements should move to the durable RepoSync source rather than remain duplicated. |

## Reusable Pattern vs Repository-Specific Adaptation

### Reusable

- Test-owned infrastructure lifecycle.
- Mandatory Docker/test/scenario guards.
- Exact built-image runtime validation.
- Maven local-repository/reactor reuse.
- Docker context restriction and stable-before-volatile layer ordering.
- Independent preparation work overlapped where safe.
- Trivy DB preparation before the final scan.
- Deterministic cleanup and bounded readiness/polling.

### Repository-specific

- Topic catalogue and suffix rules.
- Feature/scenario inventory counts.
- Which aggregate containers are required.
- Application startup mechanism and health endpoint.
- Kafka listener/stream topology details.
- Runtime environment/property names.

## Rejected or Reverted Experiments

A proposed optimisation is not retained simply because it appears faster in theory. Examples evaluated during the work and rejected/reverted where they did not provide reproducible value or changed validation semantics include Maven parallelism, broad image prefetch, readiness caching and broad logging changes.

