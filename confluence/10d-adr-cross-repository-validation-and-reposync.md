# ADR-0006: Validate Across Representative Repositories Before RepoSync Centralisation

| Field | Value |
|---|---|
| **Status** | Accepted; central implementation pending |
| **Date** | 2026-09-18 |
| **Related** | SNS, PNR, PCDP CI/CD optimisation; RepoSync-managed pipeline |

## Context

The SNS implementation demonstrated a strong result, but `.drone.star` and other shared pipeline concerns are centrally managed through RepoSync. Merging a repository-local version of a shared pattern would create duplication and risk later overwrite.

A reusable solution also needs evidence that the pattern survives meaningful repository differences.

## Decision

Before centralising the common pipeline pattern:

1. validate it in the SNS reference implementation;
2. adapt and validate it in PNR;
3. adapt and validate it in the larger PCDP pipeline;
4. identify the subset genuinely common across those implementations;
5. implement that common subset in the RepoSync-managed source;
6. keep topics, scenario counts, aggregate selection and application-specific wiring in the repository that owns them;
7. verify representative repositories again after RepoSync adoption.

Reviewed repository changes may remain unmerged while the shared source is prepared. That is preferable to merging a known temporary copy of centrally owned pipeline logic.

## Consequences

### Positive

- shared pipeline code is based on three concrete implementations rather than one;
- repository-specific assumptions are less likely to leak into the central template;
- RepoSync remains the durable ownership source;
- branch work remains recoverable/reviewable evidence while central adoption is prepared.

### Trade-offs

- reviewed changes are not immediately merged;
- common adoption requires another review/verification cycle;
- some differences must remain repo-local rather than being hidden behind a large number of central switches.

## Follow-ups

- enumerate common RepoSync-managed changes;
- implement and review them in the central source;
- regenerate/verify representative repository pipelines;
- publish the final reuse guide once central adoption is confirmed.

