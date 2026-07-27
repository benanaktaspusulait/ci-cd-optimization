# Story 5 — Docker Compose Rationalisation: Summary

| Field | Value |
|-------|-------|
| **Status** | Done — analysis and recommendation only |
| **Date consolidated** | 2026-07-23 |

---

## Deliverables

| Task | Primary output | Status |
|---|---|---|
| T5.1 — Validate current Compose scope | [T5.1-validate-compose-scope.md](./T5.1-validate-compose-scope.md) | Done |
| T5.2 — Decide the target Compose role | [T5.2-decide-compose-role.md](./T5.2-decide-compose-role.md) | Done — recommendation prepared |

---

## Key Results

- **Observed:** 20 services are defined and 17 are started by the mapped CI path.
- **Measured locally:** the opt-in Redis Testcontainers smoke/wiring pilot completed two functional runs.
- **Not measured:** reduced-Compose CI timing, full-E2E performance, Kafka/Schema Registry replacement and flaky-test impact.
- **Recommendation:** retain current Compose defaults for full E2E and local debugging; keep reductions and a single-orchestrator model as ownership/validation candidates.
- **Adoption:** no Compose, CI or production change is approved or implemented by Story 5.

---

## Confirmed Facts

| Fact | Source |
|------|--------|
| 20 services defined | Counted from docker-compose.yml |
| 3 services not started in the mapped CI path (kafka-rest, kafka-topic-extract, aggregate-v1id-v2id) | Story 1 pipeline mapping |
| `kafdrop` started via `depends_on` chain, not direct pipeline reference | docker-compose.yml `pre-integration-test.depends_on` |
| docker-compose.yml is RepoSync-controlled | File header warning |

---

## Open Questions

| # | Question | Impact |
|---|----------|--------|
| 1 | Is `kafdrop` functionally needed in CI, or only a dependency side effect? | Requires equivalent functional validation |
| 2 | Would removing Jaeger affect adaptor startup or full E2E behaviour? | Requires equivalent functional validation |
| 3 | Which owner should control the durable orchestration model? | CST design plus RepoSync/platform decision |
