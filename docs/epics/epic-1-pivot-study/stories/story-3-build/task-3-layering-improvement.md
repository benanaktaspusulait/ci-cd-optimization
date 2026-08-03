# T3.3 — Apply one safe Docker build optimisation

**Story:** [Story 3 — Docker Build Optimisation](README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.3 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `docker`, `dockerfile`, `build-context`, `cache` |
| **Sprint** | Week 2 |
| **Depends on** | T3.1 |
| **Related to** | T3.2 where `.dockerignore` / build-context reduction is the selected candidate |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The T2.3 baseline identifies several Docker/build-context optimisation candidates, but Story 3 should apply only one safe change at a time so the impact can be measured and attributed. The current SNS Dockerfile packages pre-built Maven artefacts; it does not build the application inside Docker.

## Goal
Apply one focused Docker/build-context optimisation aligned with the SNS Dockerfile shape and ownership route.

## Scope
Consider (pick the highest-value one for this repo):
- `.dockerignore` / build-context reduction
- layer-order improvement for the current packaging-only Dockerfile
- `yum install/update` layer repeatability review
- runtime base-image review only after approved image-source / Artifactory / security validation

T3.3 should not duplicate T3.2. If `.dockerignore` / build-context reduction is the selected candidate, T3.2 may be the implementation task. Use T3.3 for a non-`.dockerignore` candidate or for a second focused candidate only after T3.1 explicitly selects it.

Do not change the lifecycle to build Maven inside the Dockerfile unless that larger design is explicitly selected. The existing pipeline builds Maven artefacts first, then the Dockerfile copies `target/cmd-adaptor-sns-exec.jar` and `target/dependencies/opentelemetry-javaagent.jar`.

Do not introduce a generic Maven multi-stage Dockerfile pattern unless the repo build design changes. Production Dockerfile changes should be routed through RepoSync unless ownership is confirmed otherwise.

## Acceptance criteria
- [ ] One change is applied only.
- [ ] Expected benefit is documented against the T2.3 baseline.
- [ ] Compatibility risks or concerns are noted.
- [ ] Ownership risks are documented.
- [ ] No generic Maven multi-stage build is introduced unless the repo build design changes.
- [ ] Runtime base-image changes are not recommended without approved image-source / Artifactory / security validation.
- [ ] Built image completes the same local Docker build verification used in T2.3, or an explicitly documented equivalent local verification; Trivy scan result is captured if a candidate image is built.
