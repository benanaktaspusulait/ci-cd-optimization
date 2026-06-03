# ADR-0007: Choose CI runner Docker execution mode for Testcontainers

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** CST + platform/ETO (runner configuration)
- **Related:** [ADR-0003 — Testcontainers](0003-testcontainers-for-integration-tests.md), [PROJECT-PLAN.md — R3](../../PROJECT-PLAN.md), [T3.2](../stories/story-3-testcontainers/task-2-implement-setup.md)

## Context

Testcontainers requires a Docker daemon to start containers during tests. GitLab CI runners can provide Docker access in three ways, each with different security, performance, and operational trade-offs:

1. **Docker-in-Docker (DinD):** A separate `docker:dind` service container runs a Docker daemon. Tests communicate with it via `DOCKER_HOST=tcp://docker:2375`. Requires `--privileged` on the service, which is a significant security concern on shared runners.

2. **Docker socket mount:** The host's `/var/run/docker.sock` is bind-mounted into the job container. Testcontainers connects to the host daemon directly. No `--privileged` required on the job container, but grants the container root-equivalent access to the host Docker daemon — effectively root on the host.

3. **Rootless Docker / Sysbox:** A rootless Docker daemon runs inside the job container without `--privileged`. More secure but requires specific runner/kernel configuration and may not be available on the organisation's current runner fleet.

The choice is **not a CST-local decision** — it depends on how platform/ETO has configured the GitLab CI runner fleet. T3.2 must assess which mode is available and document the finding. Until that assessment is complete, this ADR captures the decision framework rather than a final choice.

## Decision

We will assess the available Docker execution modes in T3.2 and select the most secure option that supports Testcontainers:

- **Preferred:** Docker socket mount (if the runner fleet already exposes it and the security posture is acceptable).
- **Acceptable:** DinD with `--privileged`, restricted to a dedicated runner tag (e.g. `privileged`) — not on general-purpose runners.
- **Fallback (per ADR-0003):** If neither mode is available or acceptable in CI, Testcontainers runs **locally only**; Docker Compose remains in CI.

The final choice is documented in T3.2 findings and carried into T3.4 and T5.2.

## Consequences

- **Positive:**
  - Explicit, documented decision that can be reviewed by security/platform teams.
  - Fallback path (local-only Testcontainers) is pre-agreed, so T3.2 does not block if CI mode is unavailable.

- **Negative / trade-offs:**
  - Socket mount: broad Docker daemon access from within CI job; acceptable only if runners are not shared with untrusted workloads.
  - DinD + `--privileged`: security risk on shared runners; requires a dedicated runner tag.
  - Rootless: operational setup cost; not guaranteed to be available.

- **Follow-ups:**
  - T3.2: confirm which mode is available on the pilot runner; document the method used.
  - T5.2: classify as CST-local (config change to `.gitlab-ci.yml`) or platform/ETO (runner reconfiguration).
  - If platform/ETO action is needed: raise as a separate ticket with this ADR attached.

## Alternatives considered

| Option | Pros | Cons | Why not chosen (default) |
|--------|------|------|--------------------------|
| Docker-in-Docker (`--privileged`) | Widely documented; fully isolated daemon | `--privileged` = security risk on shared runners; slow startup | Acceptable only on a dedicated runner tag |
| Docker socket mount | No `--privileged` on job; reuses host daemon | Grants root-equivalent host access | Preferred if security posture allows; confirm with platform/ETO |
| Rootless Docker / Sysbox | Secure; no host privilege escalation | Requires specific kernel/runner setup | Assess in T3.2; not assumed available |
| No Docker in CI (fallback) | No privilege concerns | No Testcontainers in CI; Compose remains | Valid fallback per ADR-0003 — not ideal but acceptable |
