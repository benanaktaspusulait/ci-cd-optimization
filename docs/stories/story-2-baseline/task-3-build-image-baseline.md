# T2.3 — Capture Docker build & image-size baseline

**Story:** [Story 2 — Baseline & Pilot Scope](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T2.3 |
| **Type** | Research |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 2 — Baseline & Pilot Scope |
| **Estimate** | S |
| **Priority** | Must |
| **Labels** | `baseline`, `docker`, `image-size` |
| **Sprint** | Week 1 |
| **Depends on** | T2.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
Build optimisation (Story 3) targets build time and image size directly. These numbers must exist before changes are made, otherwise the optimisation cannot be judged.

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
