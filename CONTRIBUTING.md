# Contributing & How to Use This Backlog

How this backlog is organised and how to work with it. [← Back to overview](README.md)

---

## Structure

```text
README.md                  → Epic hub: summary, status board, risks, ticket order
CONTRIBUTING.md            → this guide
backlog/
  INDEX.md                 → one-page list of every story + task title
  DEFINITION-OF-DONE.md    → shared DoD, conventions, metrics template
  tech-notes.md            → technical reference (base images, BuildKit, security)
  story-<n>-<slug>/
    README.md              → story: goal, why, acceptance criteria, task table
    task-<n>-<slug>.md     → task: metadata, why, goal, scope, acceptance criteria
```

**Levels:** Epic (`README.md`) → Story (`story-*/README.md`) → Task (`task-*.md`).

## How to navigate

- Start at the [backlog index](backlog/INDEX.md) for the full outline.
- Drill into a story README for its goal and task list.
- Open a task file for the full detail (why · goal · scope · acceptance criteria).

## Reading a task

Every task file follows the same shape:

- **Metadata header** — `ID · Estimate · Priority · Owner · Status · Depends on`
- **Why** — the reason the task exists
- **Goal** — the outcome it must achieve
- **Scope** — what is covered
- **Acceptance criteria** — checklist that must pass

## Conventions

- **Estimate (T-shirt):** `S` ≤0.5 day · `M` 0.5–1 day · `L` 1–2 days
- **Priority (MoSCoW):** `Must` · `Should` · `Could` · `Won't (this pilot)`
- **Status:** `Not started` · `In progress` · `Blocked` · `Done`
- **IDs:** stories `S1…S5`, tasks `T<story>.<n>` (e.g. `T2.3`)

## Working a task

1. Set the task **Status** to `In progress` on the [status board](README.md#status-board).
2. Do the work within the task's **scope**.
3. Capture any measurement using the [metrics template](backlog/DEFINITION-OF-DONE.md#metrics-template).
4. Tick the task's **acceptance criteria**.
5. Confirm the shared [Definition of Done](backlog/DEFINITION-OF-DONE.md).
6. Set **Status** to `Done` (or `Blocked`, with a note on what's blocking).

> The [status board](README.md#status-board) is the single source of truth for progress. Update it there, not in individual files.

## Raising tickets

Create tickets incrementally, following the order in the README. Don't raise everything at once — keep work controlled until the baseline and ownership are agreed.
