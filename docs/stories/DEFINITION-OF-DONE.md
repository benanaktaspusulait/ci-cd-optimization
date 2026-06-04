# Definition of Done

Project-wide rules that apply to **every** task, in addition to each task's own acceptance criteria.
A task is only "Done" when all of the following are true.

### Every task
- [ ] Task-specific acceptance criteria are all met
- [ ] Output (findings, change, or decision) is written down in a shareable form
- [ ] Any assumptions or open questions are recorded
- [ ] Result is reviewed by at least one other person
- [ ] Task status is updated on the [status board](../../README.md#status-board)

### Tasks that produce a measurement
- [ ] Metric is captured using the shared [metrics template](metrics-template.md)
- [ ] Measurement method/source is noted so it can be repeated

### Tasks that change code or config
- [ ] Change is small, focused, and reviewable
- [ ] Compatibility / rollback risk is noted
- [ ] No secrets are added to the repository or build context

---

## Conventions

**Estimate (T-shirt size)** — `S` ≈ ≤0.5 day · `M` ≈ 0.5–1 day · `L` ≈ 1–2 days.
**Priority (MoSCoW)** — `Must` · `Should` · `Could` · `Won't (this pilot)`.
**Status** — `Not started` · `In progress` · `Blocked` · `Done`.

---

## Metrics template

Capture every before/after measurement in the dedicated, fillable **[metrics template](metrics-template.md)** — it includes the pilot context, targets, and source-of-method columns. Quick reference of the core fields:

| Metric | Target | Measurement method |
|--------|--------|-------------------|
| Docker build time (local) | ≥ 30% ↓ | `scripts/measure-baseline.sh` / `time docker build` |
| Final image size | ≥ 30% ↓ | `docker images` |
| Build context size | ≥ 50% ↓ | Docker build context log output |
| Integration test startup (local) | < 30 sec | Testcontainers startup logs |
| Developer feedback loop | ≤ 5 min (change → test green) | Local stopwatch / script timing |
| Pipeline duration (CI, post-platform) | ≥ 20% ↓ | Drone pipeline UI (after RepoSync change) |

> Targets are tracked in the [metrics template](metrics-template.md) and the [README success criteria](../../README.md#success-criteria--targets). Keep local pilot targets separate from post-platform CI targets.
