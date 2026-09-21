# Working Agreements and Metrics — Cross-Repository Evidence

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Validated pattern; central adoption pending |
| **Last updated** | 2026-09-21 |

## Working Agreements

1. **Evidence before claims.** Performance improvements must be tied to an observed or controlled measurement.
2. **Coverage is part of performance.** A faster pipeline is not an improvement if fewer business scenarios or runtime/security checks execute.
3. **Do not sum overlapping Drone steps.** End-to-end duration is the primary pipeline metric.
4. **Separate evidence types.** Use labels such as measured, observed, controlled experiment and structural improvement.
5. **Keep rejected experiments out of the final design.** Record what was tried so it is not reintroduced without new evidence.
6. **Shared pipeline logic belongs in RepoSync.** Repository branches can prove the pattern; centrally generated logic must be adopted through the central source.
7. **Repository-specific test knowledge stays local.** Topics, scenario counts, aggregate dependencies and application wiring are not central-template concerns.
8. **No unrelated cleanup in optimisation MRs.** Business behaviour/test semantics must not change merely to improve CI timing.

## Cross-Repository Evidence Summary

| Repository | Role | Baseline | Validated result | Coverage / guard evidence | Delivery status |
|---|---|---:|---:|---|---|
| SNS | Reference implementation | 13m35s average, N=10 successful runs | 4m57s and 4m44s observed | 7 feature files / 14 business scenarios; mandatory Docker/zero-test/scenario guards; exact-image runtime validation | Reviewed / accepted; merge deferred for RepoSync common work |
| PNR | Portability validation | Not recorded in supplied evidence | No numeric claim on this page | 15 feature files / 15 declared non-ignored / 21 expanded cases; inventory guard; selective aggregate startup; exact-image runtime validation | Reviewed / accepted; merge deferred for RepoSync common work |
| PCDP | Large-pipeline validation | ~16m30s observed reference | 7m38s observed | 131 feature files / 480 declared / 1382 executable; snapshot 125 declared / 229 executable; exact-image/runtime/security path retained | Reviewed / accepted; merge deferred for RepoSync common work |

## SNS Measured Evidence

| Area | Before | After | Evidence type |
|---|---:|---:|---|
| Full CI | 13m35s average (N=10) | 4m57s / 4m44s | Direct CI measurement |
| Docker layer rebuild | ~75.82–77.90s | ~4.62–5.08s | Controlled same-daemon warm-cache real-JAR-change experiment |
| Runtime image validation | ~1m06s | ~30–34s | Direct CI step observation |
| Adaptor information | ~15–21s | ~11s | Direct CI step observation |
| Final Trivy contribution | ~40–49s | ~14–15s | Direct CI step observation; DB prep moved earlier |

Do not add the component rows together: their scopes overlap and differ.

## PNR Evidence Rule

PNR currently contributes portability/correctness evidence. Until a comparable baseline/final timing set is recorded, do not publish an end-to-end percentage or speed-up for PNR.

## PCDP Evidence Rule

The ~16m30s → 7m38s comparison is an observed validation comparison, not a multi-run statistical baseline. It may be used as an observed result, but not described as an N-run average.

## Definition of Done for Shared RepoSync Adoption

- [ ] Common pipeline pieces extracted from the three implementations.
- [ ] Repository-specific topics/counts/aggregates are not embedded centrally.
- [ ] Mandatory Docker/test/scenario guards remain active.
- [ ] Exact built-image runtime validation remains active.
- [ ] Final Trivy scan/reporting remains active with existing policy.
- [ ] Representative repositories re-run successfully after RepoSync generation.
- [ ] Timing captured using the same evidence rules.
- [ ] Any regression or exception documented before rollout expands.

## Metrics Template for Future Repositories

| Metric | Baseline | After | Evidence type | Source/method |
|---|---:|---:|---|---|
| End-to-end successful CI duration | | | | |
| Build/Test with Testcontainers | | | | |
| Image build | | | | |
| Exact-image runtime validation | | | | |
| Final Trivy contribution | | | | |
| Feature/scenario inventory | | | correctness | |
| Failed/zero-test/Docker guard behaviour | | | correctness | |

