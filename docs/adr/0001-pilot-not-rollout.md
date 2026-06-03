# ADR-0001: Run a measured pilot, not a big-bang rollout

- **Status:** Proposed
- **Date:** 2026-06-03
- **Deciders:** Pilot team, stakeholders
- **Related:** [Epic](../../README.md) · [Project plan](../../PROJECT-PLAN.md) · [ADR-0002](0002-testcontainers-for-integration-tests.md) · [ADR-0003](0003-reduce-compose-in-ci.md) · [ADR-0004](0004-buildkit-cache-and-layering.md)

## Context

The FDP CI/CD pipeline currently suffers from long build times (~12 min average), heavy Docker Compose integration test setup, flaky environment-dependent test failures, and inconsistent Dockerfile patterns. Multiple optimisation ideas exist (Testcontainers, BuildKit caching, Compose rationalisation, base-image strategy), but none are proven in this specific context.

Implementing all of these at once across multiple repositories would be high-risk: if something breaks, diagnosing the cause is difficult; if something doesn't help, effort is wasted with no evidence to show. The team also lacks baseline data to prove whether any change actually improved the situation.

Additionally, some improvements are CST-local (the team can act immediately) while others require platform/ETO infrastructure (shared base images, remote cache). Starting a rollout without knowing this boundary creates organisational friction.

## Decision

We will validate the optimisation ideas through a **small, measurable pilot on one representative repository**, capturing before/after evidence, before proposing any wider rollout.

The pilot:
- selects one repo that is representative but low-risk (Story 1)
- captures baseline metrics so improvement is provable (Story 1)
- applies build optimisation and measures the delta (Story 2)
- pilots Testcontainers for one dependency and compares to Compose (Story 3)
- reviews Compose role and recommends a reduced set (Story 4)
- consolidates findings and classifies CST vs platform/ETO ownership (Story 5)

Only after evidence is available and ownership is clear will any wider rollout be proposed.

## Consequences

- **Positive:**
  - Low delivery risk — one repo, controlled scope, revertable changes.
  - Evidence-based — every claim is backed by before/after data.
  - Reusable patterns identified deliberately, not accidentally.
  - Ownership is explicit — no wider-impact item progresses without the right team involved.

- **Negative / trade-offs:**
  - Findings from one repo may not fully generalise (assumption A1).
  - Slower than a rollout — the pilot takes ~4 weeks before wider adoption is even discussed.
  - Some benefits (remote cache, base images) cannot be realised in the pilot alone — they require platform/ETO action post-pilot.

- **Follow-ups:**
  - State scope limits explicitly in the Story 5 summary.
  - Recommend a second repo before any org-wide rollout.
  - Route platform/ETO items via Story 5 with evidence attached.

## Alternatives considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Big-bang rollout across all FDP repos | Fast impact if it works | High risk; no baseline evidence; hard to reverse; unclear ownership | Too risky for unproven changes |
| Do nothing | No effort or risk | Pain points persist; build/test friction continues to grow | Doesn't address known problems |
| Pilot across many repos simultaneously | Broader evidence base | Heavy coordination; defeats "small and controlled" intent; blocks on more teams | Disproportionate for a first pilot |
| Start with platform/ETO changes first | Addresses infra gaps | Slow; depends on another team's priority; no CST evidence to justify the ask | Better to show local evidence first, then make the platform ask |
