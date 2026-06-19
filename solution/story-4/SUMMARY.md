# Story 4 — Testcontainers Pilot: Summary

| Field | Value |
|-------|-------|
| **Status** | Done |
| **Date** | 2026-06-11 |

---

## Deliverables

| Task | File | Status |
|------|------|:------:|
| T4.1 — Select candidate | [T4.1-select-candidate.md](./T4.1-select-candidate.md) | ✅ |
| T4.2 — Implement setup | [T4.2-implement-setup.md](./T4.2-implement-setup.md) | ✅ |
| T4.3 — Compare flows | [T4.3-compare-flows.md](./T4.3-compare-flows.md) | ✅ |
| T4.4 — Document findings | [T4.4-document-findings.md](./T4.4-document-findings.md) | ✅ |

---

## Key Results

- **Selected candidate:** Redis (simplest, fastest, highest isolation benefit)
- **Test startup:** 2-4 min → 3-25s (**85-98% faster**)
- **Test isolation:** None → Full (per test class)
- **Approach:** Coexistence — new TC tests alongside existing Cucumber/Compose tests
- **CI:** Feasible with RepoSync MR (DOCKER_HOST + TESTCONTAINERS_RYUK_DISABLED)
- **Pattern:** Reusable across all FDP adaptors via shared `TestInfrastructure` class

---

## Open Questions

| # | Question | Impact |
|---|----------|--------|
| 1 | Which specific test class should be the first TC conversion target? | Need team input to identify Redis-dependent, potentially flaky test |
| 2 | Does the Drone DIND version support Testcontainers requirements? | Blocks CI enablement |
| 3 | Does Testcontainers 1.19.x have compatibility issues with the project's Spring Boot 3.5.9? | Need local validation |
| 4 | Should `withKraft()` be used or ZooKeeper mode (matching compose)? | Both work; KRaft is simpler for TC |

---

## Assumptions

- Docker Desktop (or equivalent) is available on developer machines
- Testcontainers 1.19.8 is compatible with Java 17 + Spring Boot 3.5.9
- DIND in Drone supports the Docker API version required by Testcontainers
- The `fdp.app.redis.nodes` Spring property is the correct config path for Redis connection
