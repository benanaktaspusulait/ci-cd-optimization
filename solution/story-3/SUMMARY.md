# Story 3 — Docker Build Optimisation: Summary

| Field | Value |
|-------|-------|
| **Status** | Done |
| **Date** | 2026-06-11 |

---

## Deliverables

| Task | File | Status |
|------|------|:------:|
| T3.1 — Review Dockerfile & build context | [T3.1-review-dockerfile.md](./T3.1-review-dockerfile.md) | ✅ |
| T3.2 — Add or validate .dockerignore | [T3.2-dockerignore.md](./T3.2-dockerignore.md) | ✅ |
| T3.3 — Layering / cache improvement | [T3.3-layering-improvement.md](./T3.3-layering-improvement.md) | ✅ |
| T3.4 — Measure impact | [T3.4-measure-impact.md](./T3.4-measure-impact.md) | ✅ (plan ready) |

---

## Key Results

> ⚠️ "Before" numbers are estimates from static analysis; "After" numbers are projected based on known base image sizes. Both require local docker build confirmation.

| Metric | Before (estimated) | After (projected) | Improvement |
|--------|--------|-------|:-----------:|
| Image size | ~550 MB | ~180-200 MB | **~65%** ↓ |
| Build context | ~150 MB | ~80 MB | **~50%** ↓ |
| Build time (cold) | ~2-3 min | ~30-60s | **~70%** ↓ |
| Build time (warm) | ~1.5-2 min | ~5-10s | **~90%** ↓ |

All targets expected to be met (targets were ≥30% build time, ≥30% image size, ≥50% context). Actual validation pending local build execution.

---

## Changes Proposed (RepoSync MR)

1. **`.dockerignore`** — exclude src/, unused target/ files, IDE files
2. **Base image** — `amazoncorretto:17` → `eclipse-temurin:17-jre-alpine`
3. **Layer order** — system deps first (cached), application JARs last (rebuild on change)
4. **Split RUN** — separate packages / envconsul / user creation
5. **HEALTHCHECK** — added for orchestrator readiness
6. **Debug/JMX** — moved to JAVA_OPTS env var (not hardcoded)

---

## Security Improvements

- Removed full JDK from runtime (compiler not needed → reduced attack surface)
- Non-root user retained ✅
- Debug port no longer open by default
- JMX authentication no longer explicitly disabled in base image
- HEALTHCHECK enables faster failure detection

---

## Build/Publish Integrity (P0 — from architecture review)

> This is a distinct architectural finding surfaced by the Story 1 synthesis. It is not a Dockerfile-content issue, but it directly affects the value of every build optimisation here.

**Problem:** The image that is integration-tested and Trivy-scanned in the CI pipeline (`docker-compose-command-adaptor:latest`, built via compose `--build`) is **not** the image that is published — the ECR/Artifactory pipeline performs a *separate* `docker build` and pushes that. (Cross-ref: T1.1 §6, T1.3 §5.1 — "double Maven build".)

**Why it matters:** This breaks build-once-promote. CI's green test result and the Trivy scan apply to an artifact that is then discarded and rebuilt. With unpinned base/tooling images, the published image can differ from the tested one — a release-integrity and supply-chain concern, independent of the size/layering gains in this story.

**Recommended solution:** Build the image **once**, tag by commit SHA, run integration tests + Trivy against that exact image, then **promote the same digest** in the publish stage (retag/push — no rebuild).

| Trade-off | Detail |
|-----------|--------|
| Pro | What is tested/scanned is what ships; removes one full build per push |
| Con | Requires sharing the built image across pipeline stages (interim SHA tag + cleanup policy); RepoSync change to CI + publish stages |

**Ownership:** RepoSync MR (CI + Artifactory pipeline change). Routed via Story 6 / T6.2.

---

## Open Questions

| # | Question | Impact |
|---|----------|--------|
| 1 | Is `.dockerignore` managed by RepoSync? | Determines whether local add is safe or needs MR |
| 2 | What is the actual application port for HEALTHCHECK? | docker-compose.yml shows 7112 but `application.yml` not reviewed |
| 3 | Does envconsul work on Alpine (musl libc)? | May need static binary or glibc compatibility layer |
| 4 | Are there native library dependencies that require glibc? | Would block Alpine switch; Jammy (Ubuntu) fallback available |
