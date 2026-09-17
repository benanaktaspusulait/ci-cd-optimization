# Technical Details — Validated CI/CD Pattern

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Validated across SNS, PNR and PCDP |
| **Last updated** | 2026-09-17 |

## Technical Model

The final pattern has four coupled tracks:

1. **Build/context/cache** — minimise Docker inputs, order stable layers before volatile application artefacts, and reuse cache through the simplest supported builder path.
2. **Test-owned integration lifecycle** — Maven/Failsafe/Cucumber owns the infrastructure required by the suite through Testcontainers.
3. **Packaged-image validation** — after in-JVM integration tests succeed, build the Docker image and start that exact image against compatible test infrastructure.
4. **Critical-path/security preservation** — overlap independent preparation such as Trivy DB downloads while retaining the final scan and validation gates.

## Reference CI Dependency Graph

```text
Retrieve secrets
   |-----------------------> Prepare Trivy DB --------------------+
   |-----------------------> Extract adaptor information ----+    |
   +--> Wait for Docker ------------------------------------+|    |
                                                            vv    |
                                             Build/Test with Testcontainers
                                                        |
                                                   Build image
                                                        |
                                             Validate built image runtime
                                                        |
                                                        +----------+
                                                                   |
                                                               Trivy scan
```

The visible steps are not additive because some execute in parallel. End-to-end pipeline duration is the primary measurement; component timings are supporting evidence only when their boundaries are comparable.

## Cross-Repository Evidence

| Repository | Validation focus | Key guard / adaptation |
|---|---|---|
| SNS | Reference implementation | 7 feature files / 14 business scenarios; five aggregate containers; dynamic topic suffix; exact-image smoke |
| PNR | Portability | Custom feature inventory; scenario-outline expansion; selective aggregate startup; exact-image runtime test |
| PCDP | Scale/complexity | 131 feature files / 1382 executable cases; 229 executable snapshot cases; larger aggregate/infrastructure set |

## Technical Child Pages

- **Docker Build & Infrastructure** — `.dockerignore`, layer ordering, BuildKit/registry cache, runtime-image constraints.
- **Testcontainers** — DIND compatibility, infrastructure/application lifecycle, scenario guards and exact-image validation.
- **Pipeline & Drone Context** — RepoSync boundary and before/after CI ownership.

## Evidence Rules

- Mark values as **measured**, **observed**, **controlled experiment**, **structural**, or **inferred**.
- Do not add durations from overlapping steps.
- Do not claim a cross-repository timing benefit without target-repository measurement.
- Do not call a branch implementation centrally adopted until the RepoSync source is updated and verified.

