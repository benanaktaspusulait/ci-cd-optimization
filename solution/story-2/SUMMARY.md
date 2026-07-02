# Story 2 - Baseline & Pilot Scope: Summary

| Field | Value |
|-------|-------|
| **Status** | Baseline pack published for T2.1-T2.4; core SNS CI elapsed baseline, visible reliability sample, local Docker image/build baseline and structural integration-test baseline captured; T2.5 remains a Redis Testcontainers pilot planning document; stakeholder sign-off, local Maven/test feedback timing, ownership, image-source and implementation confirmations remain pending |
| **Date updated** | 2026-07-02 |

---

## Deliverables

| Task | File | Current state |
|------|------|---------------|
| T2.1 - Select pilot repo | [T2.1-select-repo.md](./T2.1-select-repo.md) | Published; SNS remains working pilot recommendation; DVLA retained as lightweight reference; RoRo TSV identified as stronger follow-up candidate; stakeholder confirmation pending |
| T2.2 - Pipeline baseline | [T2.2-pipeline-baseline.md](./T2.2-pipeline-baseline.md) | Published; N=10 successful CI timing baseline and visible build-list reliability sample captured; local build/test timings remain separate follow-up |
| T2.3 - Docker build & image baseline | [T2.3-build-image-baseline.md](./T2.3-build-image-baseline.md) | Published; measured local Docker baseline captured; RoRo TSV Dockerfile/build-shape follow-up completed structurally; ownership/image-source confirmations remain follow-up |
| T2.4 - Integration test baseline | [T2.4-integration-test-baseline.md](./T2.4-integration-test-baseline.md) | Published; structural integration-test baseline captured; Build #881 internal sample reviewed; N=10 internal breakdown, rerun-confirmed flakiness and Redis implementation treated as deferred/out-of-scope follow-up |
| T2.5 - Redis Testcontainers pilot plan | [T2.5-redis-testcontainers-pilot-plan.md](./T2.5-redis-testcontainers-pilot-plan.md) | Planning document; local opt-in Redis Testcontainers smoke/wiring pilot scoped; implementation prerequisites remain before coding |

---

## Baseline Summary Table

CI duration uses the N=10 successful SNS Drone UI sample. Docker image/build metrics use local measured values. Full publish/CD/cleardown timing, local Maven/test feedback timings, stakeholder sign-off and production ownership/image-source confirmations remain follow-up items and are not blockers for the published evidence-led T2.1-T2.4 baseline pack.

| Metric | Baseline | Target / use | Story |
|--------|----------|--------------|-------|
| CI elapsed duration | `13:35` average; `13:31` median from N=10 successful CI sample | Primary CI before-state; do not sum visible step durations | S3, S4 |
| CI elapsed duration fastest / slowest | `13:20` / `13:57` | Range for reviewed successful sample | S3, S4 |
| Command Adaptor visible step | `11:01` average; `10:49-11:18` range | Cost-concentration indicator, not isolated removable time | S3 |
| Integration Tests visible step | `09:43` average; `09:26-09:57` range | Outer integration-test step baseline; internal N=10 split is optional follow-up | S4 |
| Kafka & Redis visible step | `02:18` average; `02:07-02:33` range | Infrastructure startup cost indicator | S4, S5 |
| Visible build-list reliability sample | 10 successful, 16 failed/unsuccessful, 26 reviewed; observed unsuccessful rate `61.5%` | Point-in-time context only, not long-term KPI or flaky-test rate | S4 |
| Full develop push pipeline | TBC | Optional follow-up if stakeholder reporting needs it | - |
| Docker build time (local, cold/no-cache) | `real 1m17.855s` | Docker before-state for S3 | S3 |
| Docker build time (local, warm cached) | `real 0m0.851s` | Contextual cached rebuild baseline only | S3 |
| Final image size | `906MB` | Image-size before-state for approved candidate change | S3 |
| Build context size | `191.27MB` transferred; local module `356M` after artefact prep | Reduce where safe while retaining required runtime artefacts | S3 |
| Docker ignore file | SNS not found; DVLA not evidenced; RoRo TSV not found by direct local check | Effective ignore only if ownership route allows | S3 |
| Base image | `amazoncorretto:17` full JDK; visible base/rootfs layers `165MB + 300MB` | Review smaller approved runtime candidate only after image-source validation | S3 |
| Integration-test internal sample | Build #881 Maven total `08:27`; integration-test module `07:52` | Single successful log sample; N=10 internal breakdown is optional follow-up, not required for T2.4 structural baseline | S4 |
| Containers required for integration tests | SNS: 20 defined, 17 started in CI; DVLA comparable full-stack compose shape | Redis pilot is smoke/wiring only; full-stack replacement not yet claimed | S4, S5 |
| Test isolation | Structural shared-state risk; no rerun-confirmed flaky-test rate | Structural risk only; no rerun-confirmed flaky-test rate claimed | S4 |

---

## Key Decisions Made

1. **Working pilot recommendation:** `fdp-cmd-adaptor-sns` remains the lower-risk pilot target because it is representative, already analysed and has measured baselines. Stakeholder confirmation is still pending.
2. **Lightweight comparison candidate:** `fdp-cmd-adaptor-dvla` validates the same broad RepoSync pipeline, Dockerfile, Docker Compose and Maven profile family at structural level. DVLA is not measured timing evidence and is not treated as the strongest comparison candidate.
3. **Stronger follow-up comparison:** `fdp-cmd-adaptor-roro-tsv` has been reviewed at Dockerfile/build-shape level in T2.3. This supports Dockerfile/build-shape portability only; RoRo TSV image-size/build-time and broader source-level pipeline/test comparison are not claimed.
4. **Measurement method:** Drone UI/manual read for CI elapsed and visible step timings; local `docker build`, `docker images` and `docker history` for Docker metrics.
5. **First Testcontainers candidate:** Redis is selected as a low-complexity, local opt-in smoke/wiring pilot. It does not replace compose Redis, does not modify RepoSync-controlled `pre-integration-test/app.py`, and does not solve the main Kafka/Schema Registry assertion path.
6. **Higher-value Testcontainers follow-up:** Kafka + Schema Registry remain the stronger value target after the Redis smoke pilot proves dependency wiring and workflow.
7. **Docker optimisation opportunities:** Review full JDK runtime base, `yum install/update` layer, effective Docker ignore file and artefact packaging. No production change should be recommended until ownership/image-source validation and before/after measurement exist.

---

## Validation Needed

The core CI elapsed baseline and local Docker baseline are measured. The following items are follow-up validations for implementation, production recommendation or broader reporting. They do not block the published evidence-led baseline pack.

| # | Validation | Method | Blocks |
|---|------------|--------|--------|
| 1 | Confirm stakeholder agreement on pilot repo selection | Approval from Thomas Reddy | T2.1 sign-off |
| 2 | Confirm Dockerfile and Docker ignore ownership route | Check local headers plus RepoSync source repo | Durable S3 change route |
| 3 | Confirm approved image-source / Artifactory path for alternative runtime base | ACP/security/Artifactory confirmation | Production base-image recommendation |
| 4 | Capture local Maven/test feedback timings if in scope | Direct local command timings | Local developer-feedback baseline |
| 5 | Define long-term reliability KPI, if required | Define sample window; distinguish failed vs cancelled; inspect logs if root causes are needed | Reliability reporting beyond point-in-time sample |
| 6 | Complete RoRo TSV comparison beyond Dockerfile/build-shape before broad reuse is claimed | Source-level pipeline/test comparison and optional timing/image measurement | Broad adaptor-family portability claim |
| 7 | Confirm T2.5 implementation prerequisites before coding | Inspect integration-tests POM, test framework, Testcontainers dependency approach, Redis client dependency and opt-in profile design | Redis Testcontainers implementation, not the T2.5 planning document |

---

## Confidence Levels

| Metric / finding | Confidence | Rationale |
|------------------|:----------:|-----------|
| CI elapsed duration (`13:35` average from N=10 sample) | High for reviewed mixed-branch sample | Manual Drone UI read from 10 successful SNS CI runs |
| Visible step cost concentration | Medium-High | Manual Drone UI read; step durations may overlap |
| Visible build-list unsuccessful rate (`61.5%`) | Medium for the reviewed point-in-time sample | Useful reliability context, not a long-term KPI or rerun-confirmed flaky-test rate |
| Docker image size (`906MB`) | High | Measured locally after required Maven artefacts were present |
| Build context size (`191.27MB` transferred) | High for measured local build | Docker build output from successful full build |
| Integration-test outer step (`09:43` average) | High for visible Drone step timing | Reused from T2.2 N=10 sample; internal breakdown is not N=10 |
| Build #881 internal integration-test sample | Medium | One successful log-level sample; useful but not statistically broad |
| Container count / full-stack shape | High | SNS count confirmed; DVLA excerpts show comparable compose orchestration |
| DVLA portability | High for structure, low for timings | Source excerpts confirm comparable pipeline/build/test shape; actual timings are not measured |
| RoRo TSV Dockerfile/build-shape portability | High for structure, low for timings | Source excerpts confirm same Dockerfile/build-shape pattern; image size/build time not measured |
| Redis Testcontainers pilot readiness | Medium-Low | Pilot scope is defined as local opt-in smoke/wiring; implementation dependencies, profile and smoke test class remain pending before coding |

---

## Not Claimed

- Story 2 does not mark stakeholder pilot scope as signed off.
- DVLA and RoRo TSV do not contribute measured SNS timing evidence.
- RoRo TSV does not have image-size or Docker build-time measurements.
- The visible build-list unsuccessful rate is not a long-term reliability KPI.
- No flaky-test rate is confirmed.
- No local developer feedback speed-up is claimed.
- No Docker, Testcontainers or CI optimisation saving is claimed without before/after measurement.
- T2.5 does not claim Redis Testcontainers implementation is complete.
- T2.4 deferred follow-up items are not treated as blockers for the structural baseline.
