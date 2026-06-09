# Architecture Decisions (ADR)

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

> These decisions are **Proposed** — not yet approved. They document the reasoning behind the pilot's technical approach and will be reviewed with stakeholders as part of Story 6.

---

## ADR Index

| ID | Title | Status | Page |
|----|-------|--------|------|
| ADR-0001 | Run a measured pilot, not a big-bang rollout | Proposed | ADR — Pilot Approach |
| ADR-0002 | Use Testcontainers for selected integration tests | Proposed | ADR — Testcontainers & Compose |
| ADR-0003 | Reduce Docker Compose role in CI, keep it for local | Proposed | ADR — Testcontainers & Compose |
| ADR-0004 | Use BuildKit cache + layered multi-stage builds | Proposed | ADR — BuildKit & CI Runner |
| ADR-0005 | CI runner Docker execution mode (Drone Kubernetes + DIND) | Proposed | ADR — BuildKit & CI Runner |

**Statuses:** `Proposed` → under discussion; `Accepted` → decided; `Superseded by ADR-XXXX`; `Deprecated`.

---

## ADR Child Pages

| Page | ADRs | Topic |
|------|------|-------|
| ADR — Pilot Approach | 0001 | Why a measured pilot on one repo, not a big-bang rollout |
| ADR — Testcontainers & Compose | 0002, 0003 | Testcontainers for integration tests + reduce Compose in CI |
| ADR — BuildKit & CI Runner | 0004, 0005 | Multi-stage builds, cache mounts + Drone DIND execution mode |

---

## ADR Template

Use this template for new decisions.

```text
# ADR-NNNN: <short title of the decision>

- Status: Proposed | Accepted | Superseded by ADR-XXXX | Deprecated
- Date: YYYY-MM-DD
- Deciders: <names / roles>
- Related: <story / task / ADR links>

## Context
What is the situation and the forces at play?

## Decision
The decision, stated in active voice: "We will ...".

## Consequences
- Positive:
- Negative / trade-offs:
- Follow-ups:

## Alternatives considered
| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| | | | |
```

---

*Feedback or questions? Contact the page owner or comment below.*
