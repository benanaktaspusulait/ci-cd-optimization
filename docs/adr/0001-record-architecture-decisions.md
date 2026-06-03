# ADR-0001: Record architecture decisions

- **Status:** Accepted
- **Date:** 2026-06-03
- **Deciders:** Pilot team
- **Related:** [ADR index](README.md)

## Context
The pilot makes several non-obvious technical choices (testing approach, caching, Compose usage). Without a record, the reasoning is lost and decisions get re-litigated.

## Decision
We will record architecturally significant decisions as ADRs in `docs/adr/`, using a lightweight Context / Decision / Consequences / Alternatives format. ADRs are immutable; changes are captured by a new superseding ADR.

## Consequences
- **Positive:** reasoning is preserved; onboarding is faster; decisions are reviewable.
- **Negative / trade-offs:** small ongoing authoring effort.
- **Follow-ups:** keep the [index](README.md) current as ADRs are added.

## Alternatives considered
| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| No ADRs | Zero overhead | Reasoning lost; repeated debates | Unacceptable for a pilot meant to inform wider decisions |
| Decisions only in tickets | Close to work | Scattered, hard to find later | Poor long-term traceability |
