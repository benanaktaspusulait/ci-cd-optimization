# ADR-0001: Run a Measured Pilot Before Wider Adoption

| Field | Value |
|---|---|
| **Status** | Accepted / fulfilled |
| **Last updated** | 2026-09-18 |

## Context

The original CI/CD improvement work started with incomplete baseline evidence, centrally managed pipeline constraints and uncertainty about how well Docker/Testcontainers changes would transfer across adaptor repositories.

A big-bang rollout would have mixed architecture decisions, platform ownership and performance assumptions across many repositories at once.

## Decision

Use an evidence-first reference implementation before wider adoption:

1. establish a measurable baseline;
2. implement changes in one repository;
3. retain only changes that preserve validation and show useful structural/measured benefit;
4. validate the pattern in additional representative repositories before centralising shared pipeline logic.

## Outcome

This decision has been fulfilled and extended:

- **SNS** became the reference implementation;
- **PNR** validated portability and repository-specific adaptation;
- **PCDP** validated the approach on a much larger pipeline/test surface.

The resulting pattern is now sufficiently mature to move to the shared RepoSync adoption stage.

## Consequences

### Positive

- evidence separated measured benefit from speculation;
- rejected experiments did not remain in the final implementations;
- repository-specific assumptions were exposed before centralisation;
- exact-image/runtime/security checks were retained while the CI graph changed;
- the remaining ownership boundary is explicit: shared pipeline logic belongs in RepoSync.

### Trade-offs

- reviewed repository branches are not yet merged because durable common pipeline ownership is still being resolved through RepoSync;
- some repository-specific code remains necessary and should not be abstracted away merely for consistency.

## Follow-up

ADR-0006 now governs the transition from cross-repository validation to shared RepoSync implementation.

