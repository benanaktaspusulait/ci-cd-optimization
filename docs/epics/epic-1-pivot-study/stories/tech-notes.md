# Technical Notes (Reference)

Supporting detail for the pilot. Not tasks — reference only.
Decisions behind these notes are recorded as [ADRs](../../../adr/README.md); security specifics live in [SECURITY.md](../../../../SECURITY.md).

### Base image strategy
`base-os → base-runtime → base-build → application`. Benefits: standard runtime, shared layers, central patching, easier compliance. Needs: versioned tags, ownership, deprecation policy, scheduled rebuilds, scanning. _(Likely ACP/ETO owned — classify in Story 6.)_

> **Note:** The initial pilot may identify where shared base images would help, but creating and maintaining organisation-level base images would require appropriate ACP/shared engineering ownership, lifecycle management and compatibility guarantees.

### BuildKit remote cache · [ADR-0004](../../../adr/0004-buildkit-cache-and-layering.md)
CI runners (Drone Kubernetes pods) are ephemeral — no persistent local cache between builds. Use a branch-aware registry cache:
```bash
BRANCH_SLUG="${DRONE_BRANCH:-local}"
COMMIT_SHA="${DRONE_COMMIT_SHA:-local}"

docker buildx build \
  --cache-from=type=registry,ref="$REGISTRY_IMAGE/cache:main" \
  --cache-from=type=registry,ref="$REGISTRY_IMAGE/cache:${BRANCH_SLUG}" \
  --cache-to=type=registry,ref="$REGISTRY_IMAGE/cache:${BRANCH_SLUG}",mode=max \
  --tag "$REGISTRY_IMAGE:${COMMIT_SHA}" --push .
```
Branch builds reuse `main` cache. Replace the variable names with the equivalent RepoSync/Starlark values if the central Drone template exposes different names. Keep a working fallback if cache is unavailable. _(Likely ACP/ETO owned.)_

> **Note:** BuildKit remote cache is included as a technical recommendation only. Actual implementation depends on CI runner capability, DIND image support, registry support, security constraints, RepoSync changes and ACP guidance.

### Testcontainers reuse policy · [ADR-0002](../../../adr/0002-testcontainers-for-integration-tests.md)
Local: reuse may be enabled for faster feedback. CI: reuse disabled — clean, deterministic env per run, no hidden shared state.

> **Note:** Testcontainers should first be validated locally and then assessed against Drone Kubernetes runner/DIND constraints before being proposed for CI usage. Reusable containers should not be assumed suitable for CI without completing Story 1 (pipeline assessment).

### Security & compliance
Summary only — the actionable plan (tools, gates, policies, secret handling) is in **[SECURITY.md](../../../../SECURITY.md)**.
Versioned base images, digest pinning for critical images, vulnerability scanning, SBOM, image signing if supported, scheduled rebuilds, secret-safe builds:
```dockerfile
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    mvn -B package
```

### Future platform opportunities (not in pilot)

See **[FUTURE-CONSIDERATIONS.md](FUTURE-CONSIDERATIONS.md)** for the full post-pilot readiness list (rollback, monitoring, artifact management, environment strategy, cost, compliance, troubleshooting runbook).

Platform-level capabilities that may become relevant if the pilot proves valuable: golden paths, reusable CI/CD templates, service starter templates, shared Testcontainers helper lib, contract testing, policy as code, dependency proxy/artifact cache, base image lifecycle management.

[← Back to overview](../../../../README.md)
