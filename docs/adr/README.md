# Architecture Decision Records (ADR)

ADRs capture **why** a significant decision was made — the context, the decision, and its consequences — so the reasoning survives beyond the conversation. [← Back to overview](../../README.md)

## What is an ADR?
A short, immutable record of one architecturally significant decision. When a decision changes, we don't rewrite history — we add a new ADR that **supersedes** the old one.

## Index

| ID | Title | Status | Related ADRs |
|----|-------|--------|--------------|
| [ADR-0001](0001-pilot-not-rollout.md) | Run a measured pilot, not a big-bang rollout | Proposed | 0002, 0003, 0004 |
| [ADR-0002](0002-testcontainers-for-integration-tests.md) | Use Testcontainers for selected integration tests | Proposed | 0001, 0003, 0005 |
| [ADR-0003](0003-reduce-compose-in-ci.md) | Reduce Docker Compose role in CI, keep it for local | Proposed | 0002, 0004, 0005 |
| [ADR-0004](0004-buildkit-cache-and-layering.md) | Use BuildKit cache + layered multi-stage builds | Proposed | 0001, 0003, 0005 |
| [ADR-0005](0005-ci-runner-docker-mode.md) | CI runner Docker execution mode (Drone Kubernetes + DIND) | Proposed | 0001, 0002, 0004 |

## Statuses
`Proposed` → under discussion · `Accepted` → decided · `Superseded by ADR-XXXX` · `Deprecated`.

## Adding an ADR
1. Copy [`template.md`](template.md) to `NNNN-short-title.md` (next number).
2. Fill in **Context · Decision · Consequences · Alternatives**.
3. Add it to the index above and link it from the relevant story if useful.
