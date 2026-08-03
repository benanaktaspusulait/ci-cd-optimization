# T3.2 — Add or validate .dockerignore while preserving runtime artefacts

**Story:** [Story 3 — Docker Build Optimisation](README.md)

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
Add or validate `.dockerignore` to reduce build context without breaking the required Docker `COPY` inputs for the current packaging-only Dockerfile.

## Scope
- Check whether a `.dockerignore` exists and what it covers.
- Confirm whether `.dockerignore` is repo-local or RepoSync-managed.
- Exclude IDE/editor files, VCS metadata, logs, local artefacts and unnecessary generated files while explicitly retaining the runtime artefacts copied by the Dockerfile.
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

Excluding `src/` is valid only while the Dockerfile continues to package pre-built artefacts and does not copy source files.

## Acceptance criteria
- [ ] `.dockerignore` exists or the ownership route preventing direct addition is documented.
- [ ] Required artefacts are preserved:
  - `target/cmd-adaptor-sns-exec.jar`
  - `target/dependencies/opentelemetry-javaagent.jar`
- [ ] Docker build succeeds after the `.dockerignore` change or validation.
- [ ] Unnecessary files are excluded from the build context.
- [ ] Build context before/after is recorded where measurable.
