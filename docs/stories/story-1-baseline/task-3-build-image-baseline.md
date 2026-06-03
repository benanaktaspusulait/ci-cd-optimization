# T1.3 — Capture Docker build & image-size baseline

**Story:** [Story 1 — Baseline & Pilot Scope](./README.md)

| ID | Estimate | Priority | Owner | Status | Depends on |
|----|:--------:|:--------:|-------|--------|------------|
| T1.3 | S | Must | _TBD_ | Not started | T1.1 |

## Why
Build optimisation (Story 2) targets build time and image size directly. These numbers must exist before changes are made, otherwise the optimisation cannot be judged.

## Goal
Record current Docker build time and image size for the selected repository.

## Scope
- Local Docker build time (if applicable).
- CI Docker build time (if available).
- Final image size.
- Current base image / build approach.

## Acceptance criteria
- [ ] Current Docker build duration is documented (local and/or CI)
- [ ] Current final image size is documented
- [ ] Current base image and build approach are identified
