# Future Considerations

Items that are **out of scope for the pilot** but should be addressed if the patterns move to production. These are not tasks — they are a decision backlog for the next phase. [← Back to overview](../../README.md)

> **When to revisit:** after Story 5 is complete and stakeholders decide to progress beyond the pilot.

---

## Post-pilot production readiness

> **How to action:** After T5.2 classifies ownership, create tickets on the relevant boards and replace the `Next action` cells below with issue links.

| # | Category | What's needed | Why it matters | Likely owner | Board | Next action |
|---|----------|---------------|----------------|--------------|-------|-------------|
| F1 | **Rollback strategy** | Define how to revert to the previous image/config when a new build causes issues. Options: GitLab environment rollback, re-deploy previous image tag, or automated canary with auto-rollback. | Without rollback, a bad deploy stays live until someone manually intervenes. | CST + platform | CST board + platform board | Raise after T5.2 — _link issue here_ |
| F2 | **Monitoring & alerting** | Track pipeline health metrics (queue time, failure rate, stage duration trend) and alert the team when thresholds breach. GitLab CI/CD analytics + external dashboards (Grafana / GitLab Insights). | Degradation goes unnoticed until someone manually checks. | CST (setup) / platform (infra) | Platform board | Raise after T5.2 — _link issue here_ |
| F3 | **Artifact management** | Define where images are stored (GitLab Container Registry), retention/expiry policy (e.g. keep last N tags per branch, expire untagged after 30 days), and cleanup automation. | Unmanaged registries grow indefinitely; stale images consume storage and create confusion. | Platform | Platform board | Raise after T5.3 (stakeholder sign-off) — _link issue here_ |
| F4 | **Environment strategy** | Clarify the promotion path: dev → staging → prod. Same pipeline with environment-specific variables? Manual promote gate? GitLab Environments + protected branches. | Pilot assumes one environment; production needs clear separation and gates. | CST + platform | CST board + platform board | Raise after T5.2 — _link issue here_ |
| F5 | **Cost tracking** | Monitor CI runner minutes, registry storage, and image transfer costs. Set budget alerts. | Optimisation saves time but could shift cost elsewhere (e.g. larger cache storage). | Platform / finance | Platform board | Raise after T5.3 — _link issue here_ |
| F6 | **Compliance & audit trail** | Ensure pipeline changes are traceable: who approved the MR, what ran, which image was deployed. GitLab audit events + merge request approval rules. | Enterprise/regulated environments require evidence of change control. | Platform / compliance | Platform board | Raise after T5.3 — _link issue here_ |
| F7 | **Troubleshooting runbook** | Create a developer-facing guide: "pipeline failed — what do I do?" covering common failure modes, how to read logs, how to retry, and when to escalate. | Reduces mean-time-to-recovery and unblocks developers without senior intervention. | CST | CST board | Raise after T5.1 (findings consolidated) — _link issue here_ |

---

## Recommended priority (post-pilot)

If the pilot succeeds and the team moves towards production adoption:

1. **F1 Rollback** + **F7 Runbook** — safety net + developer self-service.
2. **F2 Monitoring** — visibility before you scale.
3. **F3 Artifact management** — prevent registry bloat early.
4. **F4 Environment strategy** — required for any real deployment.
5. **F6 Compliance** — depends on org requirements.
6. **F5 Cost tracking** — useful but non-blocking.

---

## Relationship to pilot

These items may surface naturally during the pilot:
- Story 2 (build optimisation) may reveal artifact/registry questions (→ F3).
- Story 3 (Testcontainers) may expose runner cost implications (→ F5).
- Story 5 (ownership) should explicitly list which of F1–F7 are CST vs platform/ETO.

When writing the Story 5 consolidated findings, reference this list and recommend which items to pursue next.

---

## Post-pilot architecture decisions (candidates)

These are decisions that will need to be made if the pilot succeeds and the team moves to production. They are **not pilot scope** — they require platform/ETO involvement. Record them as formal ADRs when the decision point arrives.

### Base image strategy

**Context:** Application Dockerfiles currently inherit from arbitrary upstream images (e.g. `eclipse-temurin:17-jre`). There is no shared base-image governance — each repo pins a different tag, CVE patching requires per-repo manual work, and runtime images often include build tooling.

**Proposed pattern:** A four-layer hierarchy: `base-os → base-runtime → base-build → application`. Application Dockerfiles use versioned, digest-pinned `base-runtime` and `base-build` images rather than direct upstream references.

**Why post-pilot:**
- Requires platform/ETO to build, publish, scan, and maintain the base layers.
- Needs a rebuild cadence, deprecation policy, and notification process.
- CST can validate the pattern on one repo; ownership and infrastructure are platform/ETO.

**Consequences if adopted:**
- (+) CVE patches propagate centrally; smaller images; simpler Dockerfiles; central compliance.
- (−) Teams lose direct control of runtime env; operational burden on platform/ETO.

### BuildKit remote cache infrastructure

**Context:** GitLab CI runners are ephemeral — no persistent local cache. Without a registry-backed remote cache, every CI build downloads dependencies and rebuilds layers from scratch. The pattern (`--cache-from`/`--cache-to` with registry refs) is documented in [tech-notes](tech-notes.md), but provisioning it requires:
- Registry storage and retention/eviction policy.
- Write permissions for CI jobs to a cache namespace.
- Runner BuildKit support (`docker buildx`).
- Security: cache images excluded from production promotion paths.

**Why post-pilot:**
- CST cannot provision the cache namespace without platform/ETO approval.
- The pipeline duration target (≥20% reduction) may not be fully achievable without remote cache.
- CST can implement local cache mounts (partial win) in the pilot; remote cache is the next step.

**Consequences if adopted:**
- (+) Faster CI builds; dependency-heavy layers reused across branches; predictable build time.
- (−) Registry storage cost; cache invalidation must be controlled; fallback (no-cache build) must still work.

---

## Post-pilot technical opportunities

These are concrete next steps that build on the pilot's findings. They do not require platform/ETO infrastructure — CST could pursue them independently.

### Selective test execution

**What:** Only run tests affected by the changed code. If only `payment/` changed, skip `notification/` tests entirely.

**How:** Maven module selection (`-pl`, `-am`) combined with `git diff` against the merge base. GitLab CI `rules:changes` can also skip entire jobs when certain paths are untouched.

**Expected impact:** After build optimisation, this is the next-largest pipeline speed gain. On a multi-module project, it can cut integration test time by 50%+ for focused changes.

**When:** After Story 3 (Testcontainers) proves which tests are truly independent and isolated.

### Reusable Drone pipeline templates (via RepoSync)

**What:** Extract the pilot's optimised patterns (BuildKit build, Testcontainers env vars, Trivy scan) into reusable functions within the central `.drone.star` that other FDP adaptors can inherit via RepoSync.

**How:** The `.drone.star` already uses Starlark functions (`add_pipeline_step`, etc.). New functions like `add_testcontainers_step()` or `add_buildkit_build()` could encapsulate the optimised patterns. All repos receiving RepoSync would inherit them automatically.

**Expected impact:** Eliminates copy-paste drift; a fix in the template propagates to all adaptors on next sync. Reduces onboarding effort for new services.

**When:** After pilot findings are shared (Story 5) and the RepoSync team agrees to adopt the patterns. Requires central ownership.

### Contract testing (Pact)

**What:** Verify that services agree on their API contracts (request/response shapes) without deploying them together.

**How:** Pact (consumer-driven contract testing). Consumer tests generate contracts; provider verifies them in its own pipeline. No shared environment needed.

**Expected impact:** Catches integration mismatches before deploy, without heavy end-to-end tests. Reduces the need for full-stack staging environments.

**When:** When multiple FDP services interact and integration failures are a recurring problem.

### Ephemeral review environments

**What:** Spin up a short-lived deployment for each MR so reviewers can test the change in a real environment, then tear it down on merge.

**How:** GitLab Environments + Kubernetes namespace per MR (or Docker Compose on a shared VM). GitLab's `environment: on_stop` handles cleanup.

**Expected impact:** Faster feedback on behaviour changes; QA can verify without waiting for a shared staging deploy.

**When:** After the pipeline is fast and reliable (pilot goals achieved); requires platform/ETO infrastructure for dynamic namespaces.

### Dependency proxy / artifact cache

**What:** Cache Maven dependencies and Docker base image pulls at the organisation level so every pipeline doesn't re-download them from the internet.

**How:** GitLab Dependency Proxy (built-in) for Docker images; Nexus/Artifactory or GitLab Package Registry for Maven.

**Expected impact:** Eliminates network variability from builds; protects against upstream outages (Docker Hub rate limits, Maven Central downtime).

**When:** When multiple teams hit download latency or rate-limit issues. Platform/ETO owned.
