# Future Considerations

Items that are **out of scope for the pilot** but should be addressed if the patterns move to production. These are not tasks — they are a decision backlog for the next phase. [← Back to overview](../../README.md)

> **When to revisit:** after Story 5 is complete and stakeholders decide to progress beyond the pilot.

---

## Post-pilot production readiness

| # | Category | What's needed | Why it matters | Likely owner |
|---|----------|---------------|----------------|--------------|
| F1 | **Rollback strategy** | Define how to revert to the previous image/config when a new build causes issues. Options: GitLab environment rollback, re-deploy previous image tag, or automated canary with auto-rollback. | Without rollback, a bad deploy stays live until someone manually intervenes. | CST + platform |
| F2 | **Monitoring & alerting** | Track pipeline health metrics (queue time, failure rate, stage duration trend) and alert the team when thresholds breach. GitLab CI/CD analytics + external dashboards (Grafana / GitLab Insights). | Degradation goes unnoticed until someone manually checks. | CST (setup) / platform (infra) |
| F3 | **Artifact management** | Define where images are stored (GitLab Container Registry), retention/expiry policy (e.g. keep last N tags per branch, expire untagged after 30 days), and cleanup automation. | Unmanaged registries grow indefinitely; stale images consume storage and create confusion. | Platform |
| F4 | **Environment strategy** | Clarify the promotion path: dev → staging → prod. Same pipeline with environment-specific variables? Manual promote gate? GitLab Environments + protected branches. | Pilot assumes one environment; production needs clear separation and gates. | CST + platform |
| F5 | **Cost tracking** | Monitor CI runner minutes, registry storage, and image transfer costs. Set budget alerts. | Optimisation saves time but could shift cost elsewhere (e.g. larger cache storage). | Platform / finance |
| F6 | **Compliance & audit trail** | Ensure pipeline changes are traceable: who approved the MR, what ran, which image was deployed. GitLab audit events + merge request approval rules. | Enterprise/regulated environments require evidence of change control. | Platform / compliance |
| F7 | **Troubleshooting runbook** | Create a developer-facing guide: "pipeline failed — what do I do?" covering common failure modes, how to read logs, how to retry, and when to escalate. | Reduces mean-time-to-recovery and unblocks developers without senior intervention. | CST |

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
