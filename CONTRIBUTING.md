# Contributing & How to Use This Backlog

How this backlog is organised and how to work with it. [← Back to overview](README.md)

---

## Structure

```text
README.md                  → entry point: purpose, key constraint, success targets, story map
CONTRIBUTING.md            → this guide
PROJECT-PLAN.md            → timeline, milestones, risk register, branching/CI flow, test strategy
SECURITY.md                → secret management, scanning policy, policy-as-code
docs/
  PROJECT-CONTEXT.md       → background, current state, business impact, technology stack
  PIPELINE-CONTEXT.md      → Drone/RepoSync constraint and CI vs deploy boundary
  SCOPE-AND-GUARDRAILS.md  → pilot scope, assumptions, open questions, deferred work
  adr/                     → Architecture Decision Records (why behind key choices)
    README.md              → ADR index + how to add one
    template.md            → ADR template
    NNNN-*.md              → individual decisions
  stories/
    INDEX.md               → one-page list of every story + task title
    STATUS-BOARD.md        → live task status and issue links
    DEFINITION-OF-DONE.md  → shared DoD + conventions (links to metrics template)
    metrics-template.md    → fillable before/after metrics sheet
    tech-notes.md          → technical reference (base images, BuildKit, security)
    story-<n>-<slug>/
      README.md            → story: goal, why, acceptance criteria, task table
      task-<n>-<slug>.md   → task: metadata, why, goal, scope, acceptance criteria
```

**Levels:** Epic (`README.md`) → Story (`docs/stories/story-*/README.md`) → Task (`task-*.md`).
**Decisions:** recorded as [ADRs](docs/adr/README.md). **Plan & risks:** [PROJECT-PLAN.md](PROJECT-PLAN.md). **Security:** [SECURITY.md](SECURITY.md).

## How to navigate

- Start at the [backlog index](docs/epics/epic-1-pivot-study/stories/INDEX.md) for the full outline.
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

- **Estimate (story points):** use `1`, `2`, `3`, or `5`; `1 SP` is roughly 1 day of effort
- **Priority (MoSCoW):** `Must` · `Should` · `Could` · `Won't (this pilot)`
- **Status:** `Not started` · `In progress` · `Blocked` · `Done`
- **IDs:** stories `S1…S6`, tasks `T<story>.<n>` (e.g. `T3.3`)

> The [status board](docs/epics/epic-1-pivot-study/stories/STATUS-BOARD.md) is the only live progress tracker. Any status values in story/task files are planning snapshots and should not be maintained separately.

## Working a task

1. Set the task **Status** to `In progress` on the [status board](docs/epics/epic-1-pivot-study/stories/STATUS-BOARD.md).
2. Do the work within the task's **scope**.
3. Capture any measurement in the [metrics template](docs/epics/epic-1-pivot-study/stories/metrics-template.md).
4. Tick the task's **acceptance criteria**.
5. Confirm the shared [Definition of Done](docs/epics/epic-1-pivot-study/stories/DEFINITION-OF-DONE.md).
6. If the task settles a significant choice, record an [ADR](docs/adr/README.md).
7. Set **Status** to `Done` (or `Blocked`, with a note on what's blocking).

> The [status board](docs/epics/epic-1-pivot-study/stories/STATUS-BOARD.md) is the single source of truth for progress. Update it there, not in individual files.

## Raising tickets

Confirm the delivery tracker before ticket creation. If the pilot repo is GitLab-hosted, use GitLab issues for task links and GitLab MRs for source review. If Jira is the team's delivery tracker, link the Jira ticket in the `Issue` column and still use GitLab MRs for code changes. Route cross-team follow-ups to the CST, RepoSync/platform, or wider ETO board in Story 6.

Create tickets incrementally, following the order in the README. Don't raise everything at once — keep work controlled until pipeline boundaries, baseline data, and ownership are agreed.
