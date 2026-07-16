# Story 4 - Testcontainers Pilot: Summary

| Field | Value |
|-------|-------|
| **Status** | In progress - T4.1 completed; T4.2 ready to start |
| **Date updated** | 2026-07-16 |

---

## Deliverables

| Task | File | Current state |
|------|------|---------------|
| T4.1 - Confirm Redis pilot candidate and scope | [T4.1-select-candidate.md](./T4.1-select-candidate.md) | Completed; Redis confirmed as the Phase 1 candidate and T4.2 implementation route recorded |
| T4.2 - Implement Redis Testcontainers smoke/wiring pilot | [T4.2-implement-setup.md](./T4.2-implement-setup.md) | Ready to refresh/execute using the T4.1 readiness route |
| T4.3 - Compare Redis pilot with docker-compose support flow | [T4.3-compare-flows.md](./T4.3-compare-flows.md) | Pending T4.2 implementation and local evidence |
| T4.4 - Document Redis pilot findings, limits and recommendation | [T4.4-document-findings.md](./T4.4-document-findings.md) | Pending T4.2/T4.3 evidence |

---

## Current Outcome

T4.1 completed the Redis-first implementation-readiness check.

Confirmed:

- Redis remains the Phase 1 Testcontainers pilot candidate from T2.4/T2.5.
- The target module is `cmd-adaptor-sns-integration-tests`.
- The existing test framework supports an isolated JUnit Jupiter smoke test.
- The compose Redis baseline is `redis:5.0.6`.
- T4.2 should use a local, opt-in Maven route and should not alter `local-int-cmd`, `local-int-snapshot`, existing compose E2E flow or CI defaults.
- RepoSync-controlled files such as docker-compose/pre-integration assets should not be edited for T4.2.
- No local blocker was found for a minimal Redis Testcontainers smoke/wiring pilot.

Selected T4.2 route:

- Add Testcontainers through a repo-local Maven dependency/version-management route.
- Add only the minimal test-scoped Redis client dependency needed for the smoke test.
- Start `redis:5.0.6` with Testcontainers.
- Connect through the mapped host/port from the test JVM.
- Verify `PING` and `SET/GET`.
- Prove repeated local executions do not depend on previous Redis state.

---

## Not Claimed

- No Redis Testcontainers code has been implemented by T4.1.
- No local runtime improvement is claimed.
- No CI saving is claimed.
- No flaky-test improvement is claimed.
- No docker-compose replacement is claimed.
- No Kafka or Schema Registry isolation improvement is claimed.
- No production or default CI adoption is claimed.
- No direct public-registry access is assumed for CI.

---

## Next Step

Proceed to T4.2: implement the minimal, local, opt-in Redis Testcontainers smoke/wiring pilot and record exact commands, dependency versions, image-source route and measurement method.
