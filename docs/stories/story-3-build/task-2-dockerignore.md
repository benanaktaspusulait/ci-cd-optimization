# T3.2 — Add or validate .dockerignore

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.2 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 1 |
| **Priority** | Must |
| **Labels** | `docker`, `dockerignore`, `build-context` |
| **Sprint** | Week 2 |
| **Depends on** | T3.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A missing or weak `.dockerignore` sends unnecessary files into the build context, slowing builds and invalidating cache when irrelevant files change. This is one of the cheapest, lowest-risk wins available.

## Goal
Ensure the pilot repository has an appropriate `.dockerignore` proposal or applied change that keeps the build context lean without breaking the current packaging-only Dockerfile.

## Scope
- Check whether a `.dockerignore` exists and what it covers.
- Confirm whether `.dockerignore` is repo-local or RepoSync-managed.
- Exclude IDE/editor files, VCS metadata, logs, local artefacts and unnecessary generated files.
- Retain the two runtime artefacts required by the current Dockerfile:
  - `target/cmd-adaptor-sns-exec.jar`
  - `target/dependencies/opentelemetry-javaagent.jar`

Suggested baseline:
```gitignore
# Source is not needed by the current Dockerfile; it packages pre-built artefacts.
src/

# Keep only the runtime artefacts copied by the Dockerfile.
target/**
!target/
!target/cmd-adaptor-sns-exec.jar
!target/dependencies/
!target/dependencies/opentelemetry-javaagent.jar

# Local tooling and noise.
.git/
.gitignore
.idea/
.vscode/
*.iml
*.log
.DS_Store
tmp/
.tmp/
```

Do not use a blanket `target` exclusion unless the required JAR and OpenTelemetry agent are explicitly re-included and a Docker build verifies that the context still contains them.

## Acceptance criteria
- [ ] Current Docker ignore status is documented from the SNS checkout
- [ ] `.dockerignore` ownership route is confirmed or explicitly left pending
- [ ] Candidate/applied `.dockerignore` retains the runtime artefacts required by the Dockerfile
- [ ] Unnecessary files are excluded from the build context
- [ ] Build-context before/after is measured if the file is applied; otherwise the reduction remains a candidate claim only
