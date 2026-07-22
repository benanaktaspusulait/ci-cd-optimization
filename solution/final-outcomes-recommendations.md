# Pilot Final Outcomes and Recommendations

## Validated Changes

| Change | Evidence | Current status | Durable route |
|---|---|---|---|
| Targeted `.dockerignore` | Context reduced from `191.27MB` to observed `189B`; build and runtime artefacts validated | Keep locally validated change | RepoSync follow-up |
| Dockerfile layer ordering | Same-daemon JAR-change rebuild improved from `75–78s` to `4.6–5.1s` | Prototype only | RepoSync/platform discussion |
| Redis Testcontainers pilot | Two successful local runs; PING and SET/GET validated | Local opt-in pilot | CI feasibility follow-up |

## RepoSync Follow-ups

- Add/manage the validated `.dockerignore` through RepoSync.
- Review the layer-order Dockerfile candidate through RepoSync/platform ownership.
- Record any temporary target-repo experiments that should become durable centrally managed changes.

## Future Recommendations

- **Taskfile Workflow:** Evaluate a Taskfile-based developer workflow to encapsulate prerequisite builds, pilot execution and measurement commands, reducing reliance on lengthy handover instructions. This is a recommended follow-up for developer experience improvement.

## Not Claimed

- No CI saving.
- No production Dockerfile adoption.
- No broad adaptor rollout.
- No RepoSync approval yet.
