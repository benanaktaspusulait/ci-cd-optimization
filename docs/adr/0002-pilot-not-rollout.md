# ADR-0002: Run a measured pilot, not a big-bang rollout

- **Status:** Accepted
- **Date:** 2026-06-03
- **Deciders:** Pilot team, stakeholders
- **Related:** [Epic](../../README.md), [Project plan](../../PROJECT-PLAN.md)

## Context
The optimisation ideas are promising but unproven in the FDP context. A full rollout would carry delivery risk and might apply changes that don't actually help here.

## Decision
We will validate the ideas through a small, measurable pilot on **one** representative repository, capturing before/after evidence, before proposing any wider rollout.

## Consequences
- **Positive:** low risk; evidence-based decisions; reusable patterns identified deliberately.
- **Negative / trade-offs:** findings from one repo may not fully generalise (see assumption A1).
- **Follow-ups:** state scope limits in the final summary; recommend a second repo before rollout if needed.

## Alternatives considered
| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Big-bang rollout | Fast if it works | High risk; no evidence; hard to reverse | Too risky for unproven changes |
| Do nothing | No effort | Pain points persist | Doesn't address known friction |
| Pilot across many repos at once | Broader evidence | Heavy coordination; defeats "small" intent | Out of proportion for a first pilot |
