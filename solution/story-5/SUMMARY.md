# Story 5 — Docker Compose Rationalisation: Summary

| Field | Value |
|-------|-------|
| **Status** | Done — analysis and recommendation only |
| **Date consolidated** | 2026-07-27 |

---

## Deliverables

| Task | Primary output | Status |
|---|---|---|
| T5.1 — Validate current Compose scope | [T5.1-validate-compose-scope.md](./T5.1-validate-compose-scope.md) | Done |
| T5.2 — Decide the target Compose role | [T5.2-decide-compose-role.md](./T5.2-decide-compose-role.md) | Done — target-role recommendation prepared; implementation and adoption not approved |

---

## Key Results

- **Repository mapping:** 20 services are defined, and the mapped Drone configuration directly or transitively references 17 of them, including a transient helper.
- **Measured locally:** the opt-in Redis Testcontainers smoke/wiring pilot completed two functional runs.
- **Not measured:** reduced-Compose CI timing, full-E2E performance, Kafka/Schema Registry replacement and flaky-test impact.
- **Recommendation:** Compose is the currently validated full-E2E/custom-application mechanism; immediate reduction is not supported, while specific validation and architectural candidates are routed to Story 6.
- **Adoption:** no Compose, CI or production change is approved or implemented by Story 5.

---

## Confirmed Facts

| Fact | Source |
|------|--------|
| 20 services defined | Counted from docker-compose.yml |
| 3 services not invoked by the mapped CI path (`kafka-rest`, `kafka-topic-extract`, `aggregate-v1id-v2id`) | Direct `.drone.star` invocation map plus Compose dependency closure |
| `kafdrop` starts transitively and is explicitly awaited by readiness code | `docker-compose.yml` plus `pre-integration-test/app.py` |
| `aggregate-v1id-v2id` starts in `local-int-snapshot` but not in the mapped CI path | Maven Compose plugin plus `.drone.star` |
| `kafka-topic-extract` has an explicit local utility script; `kafka-rest` has no mapped invocation | `bin/extract_local_kafka_topics.sh` plus repository invocation search |
| docker-compose.yml is RepoSync-controlled | File header warning |

---

## Open Questions

| # | Question | Impact |
|---|----------|--------|
| 1 | Is `kafdrop` functionally needed, or is its explicit readiness coupling inherited convenience? | Requires equivalent functional validation |
| 2 | Would removing Jaeger affect adaptor startup or full E2E behaviour? | Requires equivalent functional validation |
| 3 | Does SNS CI/E2E use LocalStack after its explicit startup/wait step? | Requires endpoint/credential tracing and equivalent validation |
| 4 | Why does local snapshot start `aggregate-v1id-v2id` while CI excludes it? | Requires coverage intent and owner confirmation |
| 5 | Which owner should control the durable orchestration model? | CST design plus RepoSync/platform decision |
| 6 | Which scenarios require `aggregate-matching`, and should its readiness be checked explicitly? | Requires scenario mapping and equivalent validation |
| 7 | Is `kafka-rest` used by an undocumented manual or shared workflow? | Requires owner/workflow confirmation before any removal decision |
| 8 | Should CI and local execution converge on a single integration-test and orchestration owner? | Requires target design, local/CI validation and RepoSync/platform decision |
