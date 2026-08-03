# ADR-0005: CI runner Docker execution mode (Drone Kubernetes + DIND)

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** CST + ACP/ETO (RepoSync pipeline owner)
- **Related:** [ADR-0001 — Pilot approach](0001-pilot-not-rollout.md) · [ADR-0002 — Testcontainers](0002-testcontainers-for-integration-tests.md) · [ADR-0004 — BuildKit](0004-buildkit-cache-and-layering.md) · [PROJECT-PLAN.md — R3](../../PROJECT-PLAN.md) · [T1.4](../epics/epic-1-pivot-study/stories/story-1-pipeline-assessment/task-4-testcontainers-feasibility.md) · [Drone considerations](../../examples/ci/drone-considerations.md)

## Context

The FDP CI pipeline runs on **Drone with Kubernetes runner**. Docker access is provided via a **Docker-in-Docker (DIND) service** named `docker`, accessible at `tcp://docker:2375`.

The pipeline is defined in `.drone.star` (Starlark) and **centrally managed via RepoSync** — local changes to the pipeline config are not durable, so accepted reusable changes need to go through the normal ACP/RepoSync process.

Key observations from the current `.drone.star`:
1. A DIND service is added to every pipeline that needs Docker.
2. Steps that need Docker set `DOCKER_HOST=tcp://docker:2375`.
3. The ECR pipeline's Maven step already sets `TESTCONTAINERS_RYUK_DISABLED=true` — indicating prior Testcontainers exploration.
4. The main CI pipeline's `mvn clean install` step does **not** currently have `DOCKER_HOST` set.

For Testcontainers to work in CI:
- The Maven test step needs `DOCKER_HOST=tcp://docker:2375` (to reach DIND).
- Ryuk must be disabled (`TESTCONTAINERS_RYUK_DISABLED=true`) — Ryuk cannot reliably connect to the Drone DIND daemon.
- Pre-flight checks should be skipped (`TESTCONTAINERS_CHECKS_DISABLE=true`).

These are **environment variable changes in `.drone.star`** — controlled by RepoSync, not the adaptor repo.

## Decision

The current Drone setup provides DIND. We will assess its suitability for Testcontainers in T1.4 and select the appropriate execution model:

- **Preferred (if feasible):** Add `DOCKER_HOST`, `TESTCONTAINERS_RYUK_DISABLED=true`, and `TESTCONTAINERS_CHECKS_DISABLE=true` to the Maven step in `.drone.star` via a RepoSync change request. Testcontainers then runs inside the existing CI pipeline.
- **Fallback (per ADR-0002):** If the RepoSync change is not approved or DIND connectivity doesn't work, Testcontainers runs **locally only**; Docker Compose remains in CI.

The final decision is documented in T1.4 findings and carried into Story 4 and Story 6.

## Consequences

- **Positive:**
  - Uses the existing DIND service — no new infrastructure required.
  - Ephemeral Kubernetes pods mean containers die with the pipeline (no orphan cleanup needed even without Ryuk).
  - Prior art exists (`TESTCONTAINERS_RYUK_DISABLED=true` in ECR pipeline) — precedent for the change.

- **Negative / trade-offs:**
  - Requires a RepoSync change request — not CST-local.
  - Ryuk disabled means no automatic cleanup mid-pipeline (acceptable because pods are ephemeral).
  - DIND adds network hop latency for container operations (may be slower than host Docker).
  - If `DOCKER_HOST` is not set in the Maven step, Testcontainers defaults to looking for a local socket (which doesn't exist in the pod).

- **Follow-ups:**
  - T1.4: confirm DIND connectivity from Maven step.
  - If feasible: submit RepoSync change request with env vars.
  - T6.1: classify as a RepoSync/platform-owned change.
  - Document the workaround for the team (env vars needed in CI vs local).

## Alternatives considered

| Option | Pros | Cons | Why not chosen (default) |
|--------|------|------|--------------------------|
| Docker-in-Docker (`--privileged`) | Widely documented; fully isolated daemon | `--privileged` = security risk on shared runners; slow startup | Acceptable only on a dedicated runner tag |
| Docker socket mount | No `--privileged` on job; reuses host daemon | Grants root-equivalent host access | Preferred if security posture allows; confirm with ACP/ETO |
| Rootless Docker / Sysbox | Secure; no host privilege escalation | Requires specific kernel/runner setup | Assess in T1.4; not assumed available |
| No Docker in CI (fallback) | No privilege concerns | No Testcontainers in CI; Compose remains | Valid fallback per ADR-0002 — not ideal but acceptable |
