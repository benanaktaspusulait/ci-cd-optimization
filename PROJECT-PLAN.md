# Project Plan

Operational plan for the Container & CI/CD Optimisation pilot: timeline, risk register, branching/CI flow, and test strategy. [← Back to overview](README.md)

> Dates are **relative** (Week 1 = pilot kick-off week). Fill in calendar dates once the team agrees a start date.

---

## Timeline (indicative)

Sized from the story estimates (S ≤0.5d · M 0.5–1d · L 1–2d). Stories 3 and 4 run in parallel after the Story 1/2 gates.

| Week | Focus | Stories / tasks | Exit criteria |
|------|-------|-----------------|---------------|
| **Week 1** | Pipeline assessment + baseline | Story 1 (T1.1–T1.5), Story 2 (T2.1–T2.4) | Pipeline boundaries known; pilot repo agreed; baseline metrics captured |
| **Week 2** | Build + Testcontainers (parallel) | Story 3 (T3.1–T3.3), Story 4 (T4.1–T4.2) | `.dockerignore` + one layering change applied; Testcontainers setup running locally |
| **Week 3** | Measure + compare | Story 3 (T3.4), Story 4 (T4.3–T4.4), Story 5 (T5.1–T5.2) | Before/after build metrics; Testcontainers vs Compose comparison; Compose services mapped |
| **Week 4** | Rationalise + consolidate | Story 5 (T5.3), Story 6 (T6.1–T6.3) | Compose recommendation; consolidated findings; ownership classified; findings shared |

> This is a ~4-week part-time pilot, not a full-time programme. Adjust per team capacity.

### Milestones
- **M1 — Pipeline assessed + baseline agreed** (end of Week 1): boundaries known, scope locked, numbers captured.
- **M2 — Optimisations applied** (end of Week 2): build + Testcontainers changes exist.
- **M3 — Evidence in** (end of Week 3): before/after data collected.
- **M4 — Pilot reported** (end of Week 4): findings + ownership shared with stakeholders.

---

## Risk register

Probability (P) and Impact (I): Low / Med / High.

| # | Risk / assumption | P | I | Mitigation | Fallback plan |
|---|-------------------|---|---|------------|---------------|
| R1 | Pilot repo selection slips or stakeholders disagree | Med | High | Time-box selection to Week 1; agree criteria up front (T2.1) | Pick the repo with the slowest known pipeline by default |
| R2 | Drone pipeline history lacks reliable timing data | Med | Med | Use last N pipeline runs from Drone UI; document method (T2.2) | Fall back to repeatable local measurements |
| R3 | Drone Kubernetes runner / DIND limits Testcontainers | Med | High | Assess CI suitability early in T1.4; isolate as a separate finding | **Keep docker-compose in CI**; run Testcontainers locally only |
| R4 | Reducing Compose services breaks a hidden local workflow | Low | Med | Change CI usage only; keep Compose for local debugging (Story 5) | Revert Compose change; document the dependency found |
| R5 | Optimisations turn out to be RepoSync/platform-owned or wider ETO-owned, not CST-local | Med | Med | Classify ownership early (Story 6) before wider changes | Hand item to the RepoSync/platform or wider ETO board with findings attached |
| R6 | Build cache change produces inconsistent/incorrect images | Low | High | Verify image runs after each change (see test strategy) | Disable cache mount; rebuild from clean context |
| A1 | Assumption: one representative repo is enough to validate the ideas | — | Med | State scope limits in the final summary | Recommend a second repo before any rollout |
| R7 | RepoSync overwrites local pipeline changes — pilot cannot modify `.drone.star` | Med | High | Complete Story 1 to identify boundaries; only propose changes that are repo-local or explicitly request RepoSync modification | Keep pipeline changes as recommendations in Story 6; do not assume they will be applied during the pilot |
| R8 | Deploy pipeline (Helm/service repo) confused with CI pipeline | Low | Med | Clearly document the boundary (see Pipeline Landscape above); pilot scope is CI only | If deploy improvements surface, route them to FUTURE-CONSIDERATIONS, not the pilot backlog |

---

## Branching & CI flow

### Pipeline landscape

The FDP ecosystem has two separate pipelines:

1. **CI pipeline** (per-adaptor repo, `.drone.star` via RepoSync) — build, test, scan, produce image + Helm chart. **This is what the pilot optimises.**
2. **Deploy pipeline** (MMA service repo, separate Drone pipeline) — Helm package, lint, template, diff, upload, deploy to Kubernetes. **Not in pilot scope.**

### Release flow

```text
feature/MMA-XXXXX → develop → release/X.Y.Z → tag (vX.Y.Z) → tag pipeline → Artifactory
                                                                                    │
                                              MMA service repo deploy pipeline ◄────┘
                                              dev → SIT (QAT approval) → bVal → prod
```

- **Feature branch:** created from the agreed delivery ticket (Jira if that is the tracker; otherwise GitLab issue), developed, MR into `develop`
- **Release branch:** cut from `develop` when sprint is ready (e.g. `release/5.9.0`)
- **Tag:** developer creates tag on release branch → triggers tag pipeline (Maven build + test + Trivy + Sonar + Helm package + Artifactory upload)
- **Deploy:** service repo picks up the new chart version and deploys via Helm to Kubernetes
- **Environments:** dev → SIT (QAT must approve) → bVal → prod
- **Release day:** Thursday
- **Rollback:** no automation — manual `helm rollback` only

### Pilot branching

How a pilot task moves from work-in-progress to merged:

```text
feature branch  ──MR──>  develop  ──(stabilise)──>  main
   │                       │                          │
   └ pilot work            └ pilot integration        └ protected; release-ready
```

- **Branch naming:** `pilot/<story>-<short-desc>` (e.g. `pilot/s3-dockerfile-layering`).
- **Merge request (MR) required** into `develop`; no direct pushes to `develop` or `main`.
- **On MR open / update**, Drone CI runs the pipeline (if configured for MR events — confirm in T1.1).
- **Merge to `develop`** when: pipeline green, acceptance criteria met, [Definition of Done](docs/stories/DEFINITION-OF-DONE.md) satisfied, one review approved.
- **Promote `develop` → `main`** at a milestone, once the pilot increment is stable.
- **On merge**, the status board entry for the task moves to `Done` and the result is noted.

---

## Test & verification strategy

Testcontainers (Story 4) covers integration tests. Everything else still needs verification — here's how each change type is confirmed.

| Change | How it's verified |
|--------|-------------------|
| **Dockerfile / layering (T3.3)** | Image **builds** cleanly; container **starts**; app smoke-checks (health endpoint / startup logs); image runs the same workload as before |
| **`.dockerignore` (T3.2)** | Build context size compared before/after; image still contains required files; build succeeds |
| **Build cache (T3.3)** | Two consecutive builds: second reuses cache; a clean build (no cache) still succeeds (guards R6) |
| **Testcontainers (T4.2)** | Integration test passes locally; dependency reachable; runs in Drone CI or documented why not |
| **Compose change (T5.3)** | Integration suite passes with the reduced set; local debugging workflow still works |
| **Metrics (T2.x, T3.4, T4.3)** | Captured via the [metrics template](docs/stories/metrics-template.md); method documented so it's repeatable |

**Verification principles**
- Every build/config change is proven by an actual build + run, not by inspection alone.
- Before/after numbers come from the same method on the same repo.
- A clean (cache-less) build must always still work, so cache is an optimisation, never a dependency.

### Open test strategy decisions (resolve in T4.2 / T4.1)

| Question | Decision | Resolved in |
|----------|----------|-------------|
| Minimum Java version for Testcontainers | Java 11 minimum; Java 17+ recommended. Confirm against `pom.xml` in T4.1. | T4.1 |
| CI Docker execution mode | Drone Kubernetes runner + DIND — assess in T1.4. See [ADR-0005](docs/adr/0005-ci-runner-docker-mode.md). | T1.4 |
| Which CI step runs integration tests | Drone `integration-tests` step via docker-compose. Testcontainers alternative assessed in T1.4/T4.2. | T1.4, T4.2 |
| Testcontainers reuse policy | Local: reuse enabled for faster feedback. CI: reuse disabled — clean, isolated env per run. See [ADR-0002](docs/adr/0002-testcontainers-for-integration-tests.md). | Decided (ADR-0002) |
