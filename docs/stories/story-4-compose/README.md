# Story 4 — Docker Compose Rationalisation

**Epic:** [Container & CI/CD Optimisation Pilot](../../../README.md)
**Depends on:** Story 3 (uses its findings) · **Parallel with:** —

## Goal
Clarify which Compose services are truly needed for CI integration tests versus local debugging, and recommend a reduced/clearer role.

## Why
Compose files tend to grow and serve mixed purposes, dragging extra services into every CI run. Separating "needed for CI" from "useful for local debugging" reduces CI overhead without removing tools developers rely on locally.

## Acceptance criteria
- [ ] All Compose services mapped (image, ports, dependencies, purpose)
- [ ] Services classified: required for CI tests / local-debug only / optional / removable
- [ ] CI vs local usage separated; mixed-purpose usage flagged
- [ ] Reduced Compose role recommended with risk/impact

## Tasks
| Task | Title | Est | Priority | Status |
|------|-------|:---:|:--------:|--------|
| T4.1 | [Map services started by docker-compose](./task-1-map-services.md) | S | Must | Not started |
| T4.2 | [Classify services & usage](./task-2-classify-usage.md) | M | Must | Not started |
| T4.3 | [Recommend reduced Compose role](./task-3-recommend-role.md) | M | Should | Not started |
