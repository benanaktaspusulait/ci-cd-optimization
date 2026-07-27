# Story 4 - Testcontainers Pilot: Summary

| Field | Value |
|-------|-------|
| **Status** | Closed — Option A functional goal completed; performance and CI evidence remain intentionally unresolved and documented |
| **Date updated** | 2026-07-27 |

---

## Deliverables

| Task | File | Status |
|------|------|--------|
| T4.1 - Confirm Redis pilot candidate and scope | [T4.1-select-candidate.md](./T4.1-select-candidate.md) | Completed |
| T4.2 - Implement Redis Testcontainers smoke/wiring pilot | [T4.2-implement-setup.md](./T4.2-implement-setup.md) | **Completed** |
| T4.3 - Compare Redis pilot with docker-compose support flow | [T4.3-compare-flows.md](./T4.3-compare-flows.md) | **Functional comparison completed; Compose startup/readiness timing not captured** |
| T4.4 - Document Redis pilot findings, limits and recommendation | [T4.4-document-findings.md](./T4.4-document-findings.md) | **Completed with documented measurement limitations** |

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
- Measured Redis smoke-test isolation confirmed through fresh per-method containers and UUID-based keys; no system-wide isolation improvement is claimed.

**Safety:**
- `git diff --name-only` confirmed only two intended paths changed (pom.xml + MinimalRedisTest.java)
- docker-compose E2E flow, existing profiles, CI config all unchanged
- RepoSync-controlled files untouched

---

## T4.3 Outcome

T4.3 completed a functional comparison with a startup/readiness timing limitation. T4.2 measured Testcontainers evidence was recorded, the Docker Compose Redis service was validated structurally from the repository configuration, and Redis-only Compose functional evidence was captured across two target-machine runs.

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

---

## Not Claimed (across all tasks)

- No CI saving claimed
- No flaky-test improvement claimed
- No local speed improvement over Docker Compose claimed because comparable startup-to-ready timing was not captured.
- No docker-compose replacement claimed
- No Kafka or Schema Registry isolation improvement claimed
- No production or default CI adoption claimed

---

## T4.4 Outcome

T4.4 closes the Redis Option A pilot as functionally complete for its narrow local smoke/wiring goal. It records the unavailable measurements without estimates and makes no performance or CI conclusion.

Final direction:

1. Do not treat Redis Option B as a current follow-up candidate; reconsider it only for a separately agreed non-performance objective.
2. Rerun startup-to-ready measurement only if it becomes decision-critical.
3. Treat Kafka and Schema Registry as follow-up candidates for a separately scoped coverage/isolation objective, based on T4.1/T2.4 rather than Redis performance.
4. Validate CI and RepoSync requirements separately if adoption is proposed.
