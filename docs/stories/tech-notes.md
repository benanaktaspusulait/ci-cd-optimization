# Technical Notes (Reference)

Supporting detail for the pilot. Not tasks — reference only.
Decisions behind these notes are recorded as [ADRs](../adr/README.md); security specifics live in [SECURITY.md](../../SECURITY.md).

### Base image strategy
`base-os → base-runtime → base-build → application`. Benefits: standard runtime, shared layers, central patching, easier compliance. Needs: versioned tags, ownership, deprecation policy, scheduled rebuilds, scanning. _(Likely platform/ETO owned — classify in Story 5.)_

### BuildKit remote cache · [ADR-0004](../adr/0004-buildkit-cache-and-layering.md)
CI runners often lose local cache between jobs. Use a branch-aware registry cache:
```bash
docker buildx build \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:main \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:$CI_COMMIT_REF_SLUG \
  --cache-to=type=registry,ref=$REGISTRY_IMAGE/cache:$CI_COMMIT_REF_SLUG,mode=max \
  --tag $REGISTRY_IMAGE:$CI_COMMIT_SHA --push .
```
Branch builds reuse `main` cache. Keep a working fallback if cache is unavailable. _(Likely platform/ETO owned.)_

### Testcontainers reuse policy · [ADR-0002](../adr/0002-testcontainers-for-integration-tests.md)
Local: reuse may be enabled for faster feedback. CI: reuse disabled — clean, deterministic env per run, no hidden shared state.

### Security & compliance
Summary only — the actionable plan (tools, gates, policies, secret handling) is in **[SECURITY.md](../../SECURITY.md)**.
Versioned base images, digest pinning for critical images, vulnerability scanning, SBOM, image signing if supported, scheduled rebuilds, secret-safe builds:
```dockerfile
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    mvn -B package
```

### Future platform opportunities (not in pilot)

See **[FUTURE-CONSIDERATIONS.md](FUTURE-CONSIDERATIONS.md)** for the full post-pilot readiness list (rollback, monitoring, artifact management, environment strategy, cost, compliance, troubleshooting runbook).

Platform-level capabilities that may become relevant if the pilot proves valuable: golden paths, reusable CI/CD templates, service starter templates, shared Testcontainers helper lib, contract testing, policy as code, dependency proxy/artifact cache, base image lifecycle management.

[← Back to overview](../../README.md)
