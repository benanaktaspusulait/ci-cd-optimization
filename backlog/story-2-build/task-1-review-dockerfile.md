# T2.1 — Review current Dockerfile & build context

**Story:** [Story 2 — Docker Build Optimisation](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T2.1 | M | Must | _TBD_ | Not started | T1.3 |

## Why
Optimisation should be evidence-led, not guesswork. Reviewing the current Dockerfile reveals where the cache breaks and which layers are rebuilt unnecessarily, so effort goes where it actually helps.

## Goal
Understand and document the current Dockerfile structure and build context, and identify concrete optimisation opportunities.

## Scope
Review:
- current base image
- layer ordering
- dependency installation steps
- COPY instructions
- build-context size
- unnecessary files pulled into the Docker context

## Acceptance criteria
- [ ] Current Dockerfile structure is documented
- [ ] Cache-invalidation risks are identified
- [ ] A prioritised list of optimisation opportunities is produced
