# T2.2 — Add or validate .dockerignore

**Story:** [Story 2 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T2.2 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 2 — Docker Build Optimisation |
| **Estimate** | S |
| **Priority** | Must |
| **Labels** | `docker`, `dockerignore`, `build-context` |
| **Sprint** | Week 2 |
| **Depends on** | T2.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
A missing or weak `.dockerignore` sends unnecessary files into the build context, slowing builds and invalidating cache when irrelevant files change. This is one of the cheapest, lowest-risk wins available.

## Goal
Ensure the pilot repository has an appropriate `.dockerignore` that keeps the build context lean.

## Scope
- Check whether a `.dockerignore` exists and what it covers.
- Exclude build output, IDE/editor files, VCS metadata, logs and local artefacts.

Suggested baseline:
```gitignore
.git
.gitlab
target
build
.idea
.vscode
*.iml
*.log
node_modules
.DS_Store
coverage
.tmp
```

## Acceptance criteria
- [ ] `.dockerignore` exists and is appropriate for the repository
- [ ] Unnecessary files are excluded from the build context
- [ ] Build-context reduction is noted where measurable
