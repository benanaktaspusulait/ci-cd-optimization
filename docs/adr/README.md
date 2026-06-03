# Architecture Decision Records (ADR)

ADRs capture **why** a significant decision was made — the context, the decision, and its consequences — so the reasoning survives beyond the conversation. [← Back to overview](../../README.md)

## What is an ADR?
A short, immutable record of one architecturally significant decision. When a decision changes, we don't rewrite history — we add a new ADR that **supersedes** the old one.

## Index

| ID | Title | Status |
|----|-------|--------|
| [ADR-0001](0001-record-architecture-decisions.md) | Record architecture decisions | Accepted |
| [ADR-0002](0002-pilot-not-rollout.md) | Run a measured pilot, not a big-bang rollout | Accepted |
| [ADR-0003](0003-testcontainers-for-integration-tests.md) | Use Testcontainers for selected integration tests | Proposed |
| [ADR-0004](0004-reduce-compose-in-ci.md) | Reduce Docker Compose role in CI, keep it for local | Proposed |
| [ADR-0005](0005-buildkit-cache-and-layering.md) | Use BuildKit cache + layered multi-stage builds | Proposed |
| [ADR-0006](0006-base-image-strategy.md) | Use a layered base-image hierarchy | Proposed |
| [ADR-0007](0007-ci-runner-docker-mode.md) | Choose CI runner Docker execution mode for Testcontainers | Proposed |
| [ADR-0008](0008-remote-cache-infra.md) | BuildKit remote cache requires platform/ETO infrastructure | Proposed |

## Statuses
`Proposed` → under discussion · `Accepted` → decided · `Superseded by ADR-XXXX` · `Deprecated`.

## Adding an ADR
1. Copy [`template.md`](template.md) to `NNNN-short-title.md` (next number).
2. Fill in **Context · Decision · Consequences · Alternatives**.
3. Add it to the index above and link it from the relevant story if useful.
