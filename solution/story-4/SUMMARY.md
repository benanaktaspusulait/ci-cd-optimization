# Story 4 - Testcontainers Pilot: Summary

| Field | Value |
|-------|-------|
| **Status** | In progress — T4.1 completed, T4.2 completed, T4.3 in progress (Docker Compose baseline pending) |
| **Date updated** | 2026-07-16 |

---

## Deliverables

| Task | File | Status |
|------|------|--------|
| T4.1 - Confirm Redis pilot candidate and scope | [T4.1-select-candidate.md](./T4.1-select-candidate.md) | Completed |
| T4.2 - Implement Redis Testcontainers smoke/wiring pilot | [T4.2-implement-setup.md](./T4.2-implement-setup.md) | **Completed** |
| T4.3 - Compare Redis pilot with docker-compose support flow | [T4.3-compare-flows.md](./T4.3-compare-flows.md) | **In progress — Docker Compose baseline not yet measured** |
| T4.4 - Document Redis pilot findings, limits and recommendation | [T4.4-document-findings.md](./T4.4-document-findings.md) | Pending T4.3 evidence |

---

## T4.2 Outcome

A minimal Redis Testcontainers smoke/wiring pilot was implemented in `cmd-adaptor-sns-integration-tests` and validated locally across two independent runs.

**Implementation:**
- Testcontainers 1.19.8 + Jedis 4.4.3 added as test-scoped dependencies
- `local-testcontainers` Maven profile added (opt-in, skips docker-compose)
- `MinimalRedisTest.java` created — verifies PING and SET/GET from Java JVM through mapped Redis port

**Measured results:**
- Run 1: 2 tests, 0 failures, test-framework time 6.659s, Maven total 11.712s
- Run 2: 2 tests, 0 failures, test-framework time 4.556s, Maven total 9.939s
- Both runs: `BUILD SUCCESS`
- Repeated-run isolation confirmed (UUID-based keys, fresh container per method)

**Safety:**
- `git diff --name-only` confirmed only two intended paths changed (pom.xml + MinimalRedisTest.java)
- docker-compose E2E flow, existing profiles, CI config all unchanged
- RepoSync-controlled files untouched

---

## T4.3 Status

T4.3 is in progress. T4.2 measured Testcontainers evidence has been recorded in the T4.3 document (Section A).

**No Docker Compose comparison result is claimed yet because a comparable Redis/support-flow baseline has not been measured.** The T4.3 document contains only:
- Measured Testcontainers evidence from T4.2
- Structural observations from repository review (clearly labelled as structural, not measured)
- A Docker Compose measurement plan (not yet executed)

No speed improvement, startup reduction, or developer experience improvement over Docker Compose is claimed.

---

## Not Claimed (across all tasks)

- No CI saving claimed
- No flaky-test improvement claimed
- No faster local execution claimed (not measured against Docker Compose baseline)
- No docker-compose replacement claimed
- No Kafka or Schema Registry isolation improvement claimed
- No production or default CI adoption claimed
- No speed improvement over Docker Compose claimed (not yet measured)

---

## Next Step

T4.3 requires Docker Compose baseline measurements on the target machine before a comparison can be produced. Specifically:
1. Validate whether Redis can be started independently from the compose file
2. Collect full E2E compose timing (`mvn verify -Plocal-int-cmd`)
3. If safe, collect Redis-only compose timing
4. Compare with T4.2 measured Testcontainers results
