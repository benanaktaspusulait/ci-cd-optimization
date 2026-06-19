# Story 5 — Docker Compose Rationalisation: Summary

| Field | Value |
|-------|-------|
| **Status** | Done |
| **Date** | 2026-06-11 |

---

## Deliverables

| Task | File | Status |
|------|------|:------:|
| T5.1 — Map services | [T5.1-map-services.md](./T5.1-map-services.md) | ✅ |
| T5.2 — Classify usage | [T5.2-classify-usage.md](./T5.2-classify-usage.md) | ✅ |
| T5.3 — Recommend role | [T5.3-recommend-role.md](./T5.3-recommend-role.md) | ✅ |

---

## Key Results

- **20 services** defined in docker-compose.yml
- **17 started in CI** (3 already excluded: kafka-rest, kafka-topic-extract, aggregate-v1id-v2id)
- **5 infrastructure services** replaceable by Testcontainers (Kafka, ZK, SR, Redis, LocalStack)
- **Target:** CI compose reduced from 17 → 8 services (**53% reduction**)
- **Recommendation:** Keep compose for custom apps (aggregators) + local debugging; use Testcontainers for infrastructure in tests
- **Architectural decision — OPEN (T5.3 §8, decision register T6.2 §5):** container lifecycle should have a **single owner**, but the choice is not locked. **Recommended option:** `mvn verify` (Maven) with Drone reduced to provisioning DIND — gives local/CI parity and removes the dual-orchestration divergence (Drone 6 aggregators vs Maven 7; two `CORE_TAG` paths). **Alternative:** Drone retains ownership. Decision required; sequence per T1.2 "Decision Order".

---

## Confirmed Facts

| Fact | Source |
|------|--------|
| 20 services defined | Counted from docker-compose.yml |
| 3 services not started in CI (kafka-rest, kafka-topic-extract, aggregate-v1id-v2id) | Not referenced in `.drone.star` CI steps |
| `kafdrop` started via `depends_on` chain, not direct pipeline reference | docker-compose.yml `pre-integration-test.depends_on` |
| All aggregator images sourced from `docker.digital.homeoffice.gov.uk/dacc-aws/` | docker-compose.yml image references |
| docker-compose.yml is RepoSync-controlled | File header warning |

---

## Open Questions

| # | Question | Impact |
|---|----------|--------|
| 1 | Is `kafdrop` functionally needed in CI, or just a depends_on side-effect? | Could remove 1 more service from CI |
| 2 | Would removing Jaeger from CI break adaptor startup (OTel connection)? | Need to verify OTel is optional/graceful |
| 3 | Can Docker Compose profiles be added to the RepoSync template? | Affects rationalisation approach |
