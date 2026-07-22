# Story 4 - Testcontainers Pilot: Summary

| Field | Value |
|-------|-------|
| **Status** | In progress — T4.1 completed, T4.2 completed, T4.3 functional comparison completed with startup/readiness timing limitation |
| **Date updated** | 2026-07-22 |

---

## Deliverables

| Task | File | Status |
|------|------|--------|
| T4.1 - Confirm Redis pilot candidate and scope | [T4.1-select-candidate.md](./T4.1-select-candidate.md) | Completed |
| T4.2 - Implement Redis Testcontainers smoke/wiring pilot | [T4.2-implement-setup.md](./T4.2-implement-setup.md) | **Completed** |
| T4.3 - Compare Redis pilot with docker-compose support flow | [T4.3-compare-flows.md](./T4.3-compare-flows.md) | **Functional comparison completed; Compose startup/readiness timing not captured** |
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

T4.3 is in progress. T4.2 measured Testcontainers evidence has been recorded, the Docker Compose Redis service has been validated structurally from the repository configuration, and Redis-only Compose functional evidence has been captured across two target-machine runs.

Validated Compose Redis evidence:

- service name: `redis`
- image/tag: `redis:5.0.6`
- service-level `depends_on`: none observed
- network: default Compose network
- host port: `6379:6379`
- healthcheck: `redis-cli ping`

Compose functional evidence:

- Run 1: `PING=PONG`, `SET=OK`, `GET=value-compose-run-1`, `DEL=1`, cleanup `1.0794s`
- Run 2: `PING=PONG`, `SET=OK`, `GET=value-compose-run-2`, `DEL=1`, cleanup `1.07837s`

**No Docker Compose startup/readiness timing or performance preference is claimed because `compose_startup_to_ready_seconds` was not captured.** The T4.3 document contains:

- Measured Testcontainers evidence from T4.2
- Structural Docker Compose Redis validation from repository review
- Two-run Compose functional evidence and cleanup timings
- Exact target-machine Redis-only Compose measurement commands and remaining timing limitation
- Explicit limitations and claim boundaries

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

T4.3 has enough evidence for a functional Redis-only comparison, but not for a performance preference. Next:
1. Rerun only if `compose_startup_to_ready_seconds` is required for the decision
2. Keep Compose cleanup time separate from startup/readiness time
3. Optionally collect full E2E compose timing only as contextual evidence
4. Do not proceed to Redis Option B on performance grounds from the current evidence
