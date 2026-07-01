# Story 3 Review TODO

> This checklist tracks this documentation review only; it does not mark Jira tasks as done.

| # | Task | Status | Notes |
|---|------|--------|-------|
| 1 | Review Story 1 solution outputs for Story 3 build-optimisation constraints | Done | RepoSync/ACP ownership, BuildKit feasibility, Dockerfile and `.dockerignore` constraints identified |
| 2 | Review Story 2 solution outputs for baseline, pilot repo, DVLA portability and pending measurements | Done | N=10 CI baseline, local Docker baseline, pending ownership/image-source/local Maven-test timing noted; Story 2 summary aligned |
| 3 | Review `docs/stories/story-3-build` task definitions | Done | README and T3.1-T3.4 checked and corrected for evidence-led scope |
| 4 | Compare Story 3 tasks against Story 1 and Story 2 evidence | Done | Removed misleading Maven-in-Docker pattern, added measured T2.3 baselines and ownership constraints |
| 5 | Update Story 3 task/solution docs where needed | Done | Task definitions and solution docs aligned to measured Story 2 baselines |
| 6 | Verify markdown and consistency | Done | `git diff --check` passed; targeted stale-claim checks passed |

## Working Rules

- Do not mark Jira/task-definition work as done unless explicitly requested.
- Keep Story 3 evidence-led: no optimisation saving claims without measured before/after data.
- Treat production Dockerfile, `.drone.star`, Docker Compose, BuildKit CI and cache changes as RepoSync/ACP-owned unless ownership is confirmed.
- Keep local prototype results separate from production rollout claims.
