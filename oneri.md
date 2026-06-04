# README Review — Issues, Gaps and Recommended Improvements

## Context

This document captures the review findings for the `ci-cd-optimization` GitHub repository README.

The repository currently contains a single large `README.md` describing a **Container & CI/CD Optimisation Pilot**. The README positions the work as improvement suggestions / pilot planning, with FDP as the immediate pilot context and potential reusable patterns for wider projects.

This review focuses on:

- possible contradictions with the meeting discussion,
- unclear or risky wording,
- missing scope boundaries,
- ownership concerns,
- backlog structure improvements,
- and suggested README changes before sharing more widely.

---

## Overall Assessment

The README is broadly aligned with the discussion and does **not** contain any major contradiction.

The current direction is good:

```text
- It frames the work as improvement suggestions, not a final proposal.
- It uses FDP as the immediate pilot context, not as the only possible scope.
- It proposes a pilot-first approach.
- It separates CST-local work from wider platform/ETO ownership concerns.
- It avoids positioning Docker Compose as something to be removed completely.
- It describes Testcontainers mainly as improving isolation and determinism, not just speed.
```

However, the README can be improved to make the scope safer, reduce ambiguity, and avoid stakeholders interpreting the document as a full delivery plan or platform transformation programme.

---

## Summary of Findings

### No major contradiction found

The README is consistent with the main meeting messages:

```text
- This is a discussion starter.
- This is not a final implementation proposal.
- FDP is the immediate context, but the ideas are not FDP-only.
- The next sensible step is a small measurable pilot.
- Some work can be validated locally within CST.
- Some work may require platform/ETO awareness or ownership.
```

### Main improvement areas

```text
1. README is very long and may be difficult for stakeholders to review.
2. Epic title is slightly FDP-heavy and may imply the work is FDP-only.
3. Immediate pilot scope vs not-in-scope should be made explicit.
4. Base image ownership should be clarified.
5. BuildKit remote cache should be marked as platform/ETO-dependent.
6. Pre-built test images should be marked as later-stage unless baseline proves value.
7. Ephemeral environments should be clearly marked as later-stage / selective.
8. Candidate backlog should be clearly marked as candidate-only until ownership and priority are agreed.
9. Docker Compose role should continue to be framed carefully.
10. Testcontainers should continue to be framed as deterministic integration testing, not simply faster tests.
```

---

## Issue 1 — README Is Very Long

### Observation

The README is currently a large single document. This is useful as a local working document, but it may be heavy for review by delivery managers, engineering leads or platform stakeholders.

### Risk

Readers may not know which parts need review immediately and which parts are supporting detail.

This could make the proposal feel larger than intended.

### Recommendation

For local work, keeping everything in one README is acceptable.

For wider review, consider later splitting into:

```text
README.md
/docs/01-backlog-structure.md
/docs/02-ticket-details.md
/docs/03-technical-recommendations.md
/docs/04-future-platform-opportunities.md
```

### Suggested README note

Add near the top:

```md
> Note: This README is currently a working document. If the pilot progresses, detailed ticket descriptions and technical notes may be split into separate supporting documents for easier review.
```

---

## Issue 2 — Epic Title Is Too FDP-Heavy

### Current title

```text
EPIC: Pilot Container & CI/CD Optimisation Improvements for FDP
```

### Concern

This title may imply that the work is only for FDP.

That slightly conflicts with the intended framing:

```text
FDP is the initial pilot context, but the patterns may be reusable across other projects.
```

### Recommended title

```text
EPIC: Container & CI/CD Optimisation Pilot — FDP Initial Scope
```

### Why this is better

```text
- Keeps FDP as the pilot context
- Avoids implying FDP-only scope
- Keeps the broader optimisation theme visible
- Sounds more strategic and reusable
```

---

## Issue 3 — Candidate Backlog Needs Stronger Disclaimer

### Observation

The README contains a detailed backlog structure with epic, stories and tasks.

This is useful, but it may look like all tickets are ready to be created immediately.

### Risk

Stakeholders may interpret the document as a committed delivery plan rather than a candidate structure.

### Recommendation

Add a clear disclaimer before the backlog section.

### Suggested wording

```md
> Note: The backlog below is a candidate structure only. Individual tickets should not be created until priority, ownership and target board are agreed.
```

### Additional suggested wording

```md
The purpose of this backlog structure is to support review and prioritisation first. It is not intended to imply that every task should be created or implemented immediately.
```

---

## Issue 4 — Immediate Pilot Scope Is Not Explicit Enough

### Observation

The README discusses many ideas:

```text
- Dockerfile optimisation
- base images
- BuildKit cache
- Testcontainers
- Docker Compose review
- pre-built test images
- ephemeral environments
- platform opportunities
```

The document says this is a pilot, but it would be safer to explicitly define what is inside the first pilot and what is not.

### Risk

The pilot may look too large.

People may assume all improvement ideas are part of the first delivery scope.

### Recommendation

Add a dedicated section called **Immediate Pilot Scope** and **Not in Initial Pilot Scope**.

### Suggested section

```md
## Immediate Pilot Scope

The initial pilot should remain small and measurable.

Included in the first pilot:

- Baseline measurement
- Pilot repository/service selection
- Dockerfile/build context review
- `.dockerignore` validation
- One small Dockerfile layering experiment
- One small Testcontainers pilot
- Docker Compose usage review
- CST-local vs platform/ETO ownership assessment

## Not in Initial Pilot Scope

The following items are not part of the first pilot unless separately agreed:

- Organisation-wide base image rollout
- BuildKit remote cache rollout
- Pre-built test image rollout
- Ephemeral environment implementation
- Full platform transformation programme
- Organisation-wide CI/CD template rollout
- Shared Testcontainers library implementation
```

---

## Issue 5 — Base Image Ownership Needs Clarification

### Observation

The README includes a Docker base image strategy.

This is technically valid, but creating and maintaining shared base images is usually a platform/shared engineering responsibility.

### Risk

The README may imply that the pilot team or one developer will create and own organisation-level base images.

### Recommendation

Clarify that the first pilot may identify base image opportunities, but shared base image creation requires platform/shared engineering ownership.

### Suggested wording

```md
> Note: The initial pilot may identify where shared base images would help, but creating and maintaining organisation-level base images would require platform/shared engineering ownership, lifecycle management and compatibility guarantees.
```

### Suggested placement

Add this under the **Docker Base Image Strategy** section.

---

## Issue 6 — BuildKit Remote Cache Needs Platform/ETO Dependency Note

### Observation

The README includes a BuildKit remote cache example command.

This is useful technically, but actual implementation depends on CI runner support, registry support and platform configuration.

### Risk

Readers may think BuildKit remote cache can be implemented locally without platform/ETO involvement.

### Recommendation

Mark BuildKit remote cache as a technical recommendation that likely requires platform/ETO input.

### Suggested wording

```md
> Note: BuildKit remote cache is included as a technical recommendation. Actual implementation depends on CI runner capability, registry support, security constraints and platform/ETO guidance.
```

### Suggested placement

Add this under the **BuildKit Remote Cache** section, before the example command.

---

## Issue 7 — Pre-built Test Images Should Be Later-Stage

### Observation

Pre-built test images are mentioned as an improvement suggestion, but they are not part of the immediate backlog.

This is fine, but the scope should be explicit.

### Risk

People may assume pre-built test images are part of the first pilot.

### Recommendation

Mark pre-built test images as a later optimisation unless baseline data proves dependency setup is a major bottleneck.

### Suggested wording

```md
> Note: Pre-built test images are considered a later-stage optimisation and are not part of the first pilot unless baseline data shows dependency/tooling setup is a significant bottleneck.
```

### Suggested placement

Add this in either:

```text
- Technical Recommendations
- Not in Initial Pilot Scope
```

---

## Issue 8 — Ephemeral Environments Should Be Clearly Later-Stage

### Observation

Ephemeral environments are mentioned in future platform opportunities and as a selective capability.

This is correct, but should be made clearer.

### Risk

Ephemeral environments may make the proposal feel expensive or too ambitious.

### Recommendation

Explicitly mark them as later-stage and selective.

### Suggested wording

```md
> Note: Ephemeral environments are treated as a later-stage or selective capability. They are not part of the initial pilot and should only be considered for high-value PR, QA or multi-service validation workflows.
```

---

## Issue 9 — Testcontainers Should Not Be Sold as Just “Faster Tests”

### Observation

The README already does a good job by describing Testcontainers as improving isolation and determinism.

### Recommendation

Keep this framing consistent everywhere.

### Preferred wording

```text
Testcontainers may reduce setup time, but the stronger value is isolated and deterministic integration testing.
```

### Avoid wording like

```text
Testcontainers will make all tests much faster.
```

### Better wording

```text
Testcontainers can reduce reliance on full Docker Compose setup and improve integration test isolation, repeatability and local/CI consistency.
```

---

## Issue 10 — Docker Compose Role Should Stay Balanced

### Observation

The README correctly says Docker Compose should not necessarily be removed.

### Recommendation

Keep reinforcing this message.

### Preferred wording

```text
Docker Compose can still have a role for local manual debugging. The suggestion is mainly to reduce reliance on it as the primary CI integration test orchestration mechanism where Testcontainers is suitable.
```

### Avoid wording like

```text
Replace Docker Compose with Testcontainers.
```

### Why

The meeting discussion made it clear that local experimentation and existing workflows still matter. The goal is not to remove Docker Compose completely.

---

## Issue 11 — “Platform” Framing Should Stay Controlled

### Observation

The README includes future platform opportunities.

This is valuable, but should not make the current pilot look like a full platform programme.

### Recommendation

Use controlled platform wording.

### Preferred wording

```text
This is not about launching a full platform programme. It is about applying platform-style thinking to repeated CI/CD and container challenges.
```

### Suggested wording for future platform section

```md
These platform opportunities are not part of the immediate pilot scope. They are possible future evolution areas if the initial pilot proves valuable.
```

---

## Issue 12 — Add “Assumptions” Section

### Observation

The README could benefit from documenting assumptions.

### Why

This protects the proposal from over-commitment.

### Suggested section

```md
## Assumptions

- The first pilot will use one selected repository/service.
- Baseline metrics will be captured before implementation changes.
- Any platform-impacting work will be reviewed with relevant platform/ETO stakeholders.
- Docker Compose will not be removed without understanding current CI and local debugging usage.
- Testcontainers will be piloted with one dependency first before wider migration is considered.
- Projected benefits will not be treated as guaranteed until measured.
```

---

## Issue 13 — Add “Decision Points” Section

### Observation

The README describes work, but could be clearer about what decisions need to be made next.

### Suggested section

```md
## Decision Points

Before creating detailed implementation tickets, the following decisions should be agreed:

1. Which repository/service should be used as the pilot?
2. Which metrics should be captured as the baseline?
3. Which Dockerfile/build optimisation should be tested first?
4. Which integration dependency should be used for the first Testcontainers pilot?
5. Which items can stay on the CST board?
6. Which items need platform/ETO visibility or ownership?
7. What success criteria must be met before considering wider adoption?
```

---

## Issue 14 — Add “Open Questions” Section

### Observation

The README could explicitly capture open questions for stakeholders.

### Suggested section

```md
## Open Questions

- Which FDP repository/service is the best pilot candidate?
- Do we have reliable access to current pipeline timing data?
- Which integration dependency is safest for the first Testcontainers pilot?
- Are current CI runners capable of supporting BuildKit/buildx experiments?
- Is there an existing platform-owned base image strategy?
- Which team should own shared base image lifecycle if this progresses?
- Should any items be raised on the ETO/platform board instead of CST?
```

---

## Issue 15 — Add “Success Metrics” Section

### Observation

Success criteria exists per task, but the pilot-level measurable success metrics could be grouped together.

### Suggested section

```md
## Pilot Success Metrics

The pilot should be assessed using measurable before/after evidence where possible.

Potential metrics:

- Pipeline duration before/after
- Docker build duration before/after
- Docker image size before/after
- Integration test setup time before/after
- Integration test runtime before/after
- Number of services required in Docker Compose for CI before/after
- Developer setup steps before/after
- Observed flaky/environment-related failures before/after if available
```

---

## Issue 16 — Add “Recommended First PRs / Local Changes” Section

### Observation

Since the user plans to progress locally, it may help to list first local PRs separately.

### Suggested section

```md
## Recommended First Local Changes

The first local changes should be small and low-risk:

1. Add or validate `.dockerignore`
2. Capture current Docker build timing
3. Review Dockerfile layer ordering
4. Propose one Dockerfile cache optimisation
5. Measure local build before/after
6. Identify one candidate integration test for Testcontainers

Avoid combining Dockerfile optimisation and Testcontainers changes in the same PR.
```

---

## Issue 17 — Add “Do Not Do Yet” Section

### Observation

A negative scope section would prevent accidental overreach.

### Suggested section

```md
## Do Not Do Yet

Do not start with:

- organisation-wide rollout,
- replacing all Docker Compose usage,
- building shared base images without platform ownership,
- enabling BuildKit remote cache without CI/platform review,
- implementing ephemeral environments,
- creating a shared Testcontainers library before the first pilot proves value,
- opening all candidate tasks as delivery tickets before ownership is agreed.
```

---

## Recommended Patch Summary

Apply the following changes to README:

```text
1. Rename epic title:
   EPIC: Container & CI/CD Optimisation Pilot — FDP Initial Scope

2. Add candidate backlog disclaimer near the top.

3. Add Immediate Pilot Scope / Not in Initial Pilot Scope section.

4. Add note under Docker Base Image Strategy about platform/shared ownership.

5. Add note under BuildKit Remote Cache about CI runner, registry and platform/ETO dependency.

6. Mark pre-built test images as later-stage unless baseline proves value.

7. Mark ephemeral environments as later-stage/selective.

8. Add Assumptions section.

9. Add Decision Points section.

10. Add Open Questions section.

11. Add Pilot Success Metrics section.

12. Add Recommended First Local Changes section.

13. Add Do Not Do Yet section.
```

---

## Suggested Text Blocks to Copy Into README

### Candidate backlog disclaimer

```md
> Note: The backlog below is a candidate structure only. Individual tickets should not be created until priority, ownership and target board are agreed.
```

### Immediate pilot scope

```md
## Immediate Pilot Scope

The initial pilot should remain small and measurable.

Included in the first pilot:

- Baseline measurement
- Pilot repository/service selection
- Dockerfile/build context review
- `.dockerignore` validation
- One small Dockerfile layering experiment
- One small Testcontainers pilot
- Docker Compose usage review
- CST-local vs platform/ETO ownership assessment
```

### Not in initial pilot scope

```md
## Not in Initial Pilot Scope

The following items are not part of the first pilot unless separately agreed:

- Organisation-wide base image rollout
- BuildKit remote cache rollout
- Pre-built test image rollout
- Ephemeral environment implementation
- Full platform transformation programme
- Organisation-wide CI/CD template rollout
- Shared Testcontainers library implementation
```

### Base image ownership note

```md
> Note: The initial pilot may identify where shared base images would help, but creating and maintaining organisation-level base images would require platform/shared engineering ownership, lifecycle management and compatibility guarantees.
```

### BuildKit dependency note

```md
> Note: BuildKit remote cache is included as a technical recommendation. Actual implementation depends on CI runner capability, registry support, security constraints and platform/ETO guidance.
```

### Pre-built test image note

```md
> Note: Pre-built test images are considered a later-stage optimisation and are not part of the first pilot unless baseline data shows dependency/tooling setup is a significant bottleneck.
```

### Ephemeral environment note

```md
> Note: Ephemeral environments are treated as a later-stage or selective capability. They are not part of the initial pilot and should only be considered for high-value PR, QA or multi-service validation workflows.
```

### Assumptions

```md
## Assumptions

- The first pilot will use one selected repository/service.
- Baseline metrics will be captured before implementation changes.
- Any platform-impacting work will be reviewed with relevant platform/ETO stakeholders.
- Docker Compose will not be removed without understanding current CI and local debugging usage.
- Testcontainers will be piloted with one dependency first before wider migration is considered.
- Projected benefits will not be treated as guaranteed until measured.
```

### Decision points

```md
## Decision Points

Before creating detailed implementation tickets, the following decisions should be agreed:

1. Which repository/service should be used as the pilot?
2. Which metrics should be captured as the baseline?
3. Which Dockerfile/build optimisation should be tested first?
4. Which integration dependency should be used for the first Testcontainers pilot?
5. Which items can stay on the CST board?
6. Which items need platform/ETO visibility or ownership?
7. What success criteria must be met before considering wider adoption?
```

### Open questions

```md
## Open Questions

- Which FDP repository/service is the best pilot candidate?
- Do we have reliable access to current pipeline timing data?
- Which integration dependency is safest for the first Testcontainers pilot?
- Are current CI runners capable of supporting BuildKit/buildx experiments?
- Is there an existing platform-owned base image strategy?
- Which team should own shared base image lifecycle if this progresses?
- Should any items be raised on the ETO/platform board instead of CST?
```

### Pilot success metrics

```md
## Pilot Success Metrics

The pilot should be assessed using measurable before/after evidence where possible.

Potential metrics:

- Pipeline duration before/after
- Docker build duration before/after
- Docker image size before/after
- Integration test setup time before/after
- Integration test runtime before/after
- Number of services required in Docker Compose for CI before/after
- Developer setup steps before/after
- Observed flaky/environment-related failures before/after if available
```

### Recommended first local changes

```md
## Recommended First Local Changes

The first local changes should be small and low-risk:

1. Add or validate `.dockerignore`
2. Capture current Docker build timing
3. Review Dockerfile layer ordering
4. Propose one Dockerfile cache optimisation
5. Measure local build before/after
6. Identify one candidate integration test for Testcontainers

Avoid combining Dockerfile optimisation and Testcontainers changes in the same PR.
```

### Do not do yet

```md
## Do Not Do Yet

Do not start with:

- organisation-wide rollout,
- replacing all Docker Compose usage,
- building shared base images without platform ownership,
- enabling BuildKit remote cache without CI/platform review,
- implementing ephemeral environments,
- creating a shared Testcontainers library before the first pilot proves value,
- opening all candidate tasks as delivery tickets before ownership is agreed.
```

---

## Final Recommendation

The README is already strong and aligned with the discussion.

Before sharing more widely, apply the small wording and scope-control improvements listed above.

The most important improvements are:

```text
1. Add Immediate Pilot Scope / Not in Initial Pilot Scope.
2. Rename the epic to avoid FDP-only interpretation.
3. Add ownership notes for base images and BuildKit remote cache.
4. Add assumptions, decision points and open questions.
5. Make it clear that the backlog is candidate-only until agreed.
```

These changes will make the repository more stakeholder-friendly, reduce the risk of scope creep, and protect the pilot-first framing.
