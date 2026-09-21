# Security Plan — Controls Preserved by CI/CD Optimisation

| Field | Value |
|---|---|
| **Parent page** | FDP Container & CI/CD Optimisation |
| **Status** | Validated controls; no security-policy change |
| **Last updated** | 2026-09-21 |

## Scope

This page now distinguishes **controls actually preserved/validated by the CI/CD optimisation** from broader security-platform ideas that remain separate work.

The optimisation did not redefine vulnerability severity policy, introduce a new enterprise gate or claim a complete supply-chain programme.

## Controls Retained

### Trivy image scanning

The existing image scan remains in the final CI path.

Representative retained policy:

```text
--severity CRITICAL,HIGH
--ignore-unfixed
--exit-code 0
```

`--exit-code 0` means HIGH/CRITICAL findings are reported but do not fail the pipeline. That is the existing policy retained by the optimisation. Changing it would be a separate security-policy decision.

### Trivy database preparation

The optimisation moves vulnerability/Java DB preparation earlier so it can overlap other useful work. The final scan still depends on successful DB preparation and exact-image runtime validation.

This is a critical-path optimisation, not a weakening of scanning.

### Secret scanning

Existing secret-scanning behaviour remains part of the pipeline/security posture. No scanner is disabled to achieve the CI speed-up.

### Exact built-image validation

The exact image built by CI is started and must become ready before the final scan. This protects against a false sense of safety from in-JVM tests that do not validate Docker packaging/runtime behaviour.

### Private registry authentication

Registry authentication must be available before Testcontainers resolves internal aggregate images. Credentials remain supplied by the CI secret mechanism; they must not be copied into repository files or image layers.

### Runtime download integrity

Where the Dockerfile downloads runtime tooling such as envconsul, the validated pattern uses fail-fast download behaviour and checksum verification. This is retained independently of performance optimisation.

## Controls Not Introduced by This Work

The following may be valid future security initiatives but should not be presented as delivered by the CI/CD optimisation unless separately implemented and approved:

- SBOM generation/attestation programme;
- cosign/image signing;
- organisation-wide OPA/Conftest policy gates;
- new hadolint gate;
- new Critical/High build-blocking policy;
- new shared-base-image governance programme;
- scheduled security rebuild programme.

## Security Decision Boundary

| Change | This work? |
|---|:---:|
| Move Trivy DB preparation earlier | Yes |
| Retain final image scan | Yes |
| Retain existing severity/exit-code policy | Yes |
| Change `--exit-code 0` to blocking | No — separate policy decision |
| Remove vulnerability or secret scanning for speed | No |
| Validate exact image runtime | Yes |
| Introduce signing/SBOM enterprise standard | No |

## Adoption Check

When the common pipeline moves into RepoSync, verify that:

- the final scan still targets the built image;
- DB preparation failure cannot bypass the scan;
- secret scanning remains present;
- registry credentials are injected, not persisted;
- runtime-image validation remains a required predecessor;
- no repository-specific optimisation changes the existing security policy implicitly.

