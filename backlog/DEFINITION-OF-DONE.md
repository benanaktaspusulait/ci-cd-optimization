# Definition of Done

Project-wide rules that apply to **every** task, in addition to each task's own acceptance criteria.
A task is only "Done" when all of the following are true.

### Every task
- [ ] Task-specific acceptance criteria are all met
- [ ] Output (findings, change, or decision) is written down in a shareable form
- [ ] Any assumptions or open questions are recorded
- [ ] Result is reviewed by at least one other person
- [ ] Task status is updated on the [status board](../README.md#status-board)

### Tasks that produce a measurement
- [ ] Metric is captured using the shared [metrics template](#metrics-template)
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

Use this table wherever a before/after measurement is captured:

| Metric | Baseline | After | Delta | Source / method |
|--------|----------|-------|-------|-----------------|
| Pipeline duration (avg) | | | | |
| Build stage duration | | | | |
| Docker build time (local) | | | | |
| Docker build time (CI) | | | | |
| Final image size | | | | |
| Integration test startup | | | | |
| Integration test runtime | | | | |
