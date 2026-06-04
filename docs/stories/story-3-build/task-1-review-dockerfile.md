# T3.1 — Review current Dockerfile & build context

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.1 |
| **Type** | Analysis |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | M |
| **Priority** | Must |
| **Labels** | `docker`, `dockerfile`, `build-context` |
| **Sprint** | Week 2 |
| **Depends on** | T2.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

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
