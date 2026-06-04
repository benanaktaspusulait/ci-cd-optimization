# Scope and Guardrails

What the pilot may change, what needs central coordination, and what is deliberately deferred. [← Back to overview](../README.md)

---

## Pilot Approach

A small, measurable pilot on **one** representative repository:

1. **Baseline** the current state so every change is provable.
2. **Optimise the Docker build** (layering, `.dockerignore`, cache mounts) and measure the delta.
3. **Pilot Testcontainers** for one integration dependency for better isolation/determinism.
4. **Rationalise Docker Compose** — keep it for local debugging, reduce its role in CI.
5. **Consolidate findings** and classify each pattern as CST-local, RepoSync/platform, or wider ETO.

---

## Immediate Pilot Scope

The initial pilot should remain small and measurable.

**CST-local (can do in the repo without RepoSync changes):**

- Baseline measurement (pipeline timing from Drone UI, Docker build locally)
- Pilot repository/service selection
- Dockerfile / build context review
- `.dockerignore` validation
- Dockerfile layering experiment (local build)
- Local Testcontainers prototype (runs on developer machine)
- Docker Compose service mapping and classification
- CST-local vs RepoSync/platform vs wider ETO ownership assessment

**Requires central/platform coordination (RepoSync / platform / ETO):**

- Drone pipeline step changes (`.drone.star`)
- CI-level Testcontainers execution (DIND env vars, Ryuk config)
- BuildKit / `docker buildx` enabling in Drone DIND
- Remote cache infrastructure (registry namespace, permissions)
- DIND image changes
- Shared base image adoption across adaptors

---

## Deferred Work

This is the negative scope / guardrail list. These items may be valid later, but they should not start before the pilot has evidence and ownership.

- **Editing `.drone.star` locally** — it is overwritten by RepoSync; changes must go through the central source
- Organisation-wide rollout
- Replacing all Docker Compose usage
- Building shared base images without platform ownership
- Enabling BuildKit remote cache without Drone/DIND/platform review
- Implementing ephemeral environments
- Creating a shared Testcontainers library before the first pilot proves value
- Opening all candidate tasks as delivery tickets before ownership is agreed
- Changing anything on `main` branch of the pilot repo without baseline captured first
- Assuming CI-level Testcontainers works without completing Story 1 (pipeline feasibility)

---

## Assumptions

- The first pilot will use **one** selected repository/service.
- Baseline metrics will be captured **before** any implementation changes.
- Any platform-impacting work will be reviewed with relevant ACP/ETO stakeholders.
- Docker Compose will not be removed without understanding current CI and local debugging usage.
- Testcontainers will be piloted with one dependency first before wider migration is considered.
- Projected benefits will not be treated as guaranteed until measured.
- The pilot is part-time work (~4 weeks), not a full-time dedicated programme.

---

## Decision Points

Before creating detailed implementation tickets, the following decisions should be agreed:

1. Which repository/service should be used as the pilot?
2. Which metrics should be captured as the baseline and how (data source, N runs)?
3. Which Dockerfile/build optimisation should be tested first?
4. Which integration dependency should be used for the first Testcontainers pilot?
5. Which items can stay on the CST board?
6. Which items need RepoSync/platform or wider ETO visibility or ownership?
7. What success criteria must be met before considering wider adoption?

---

## Open Questions

- Which FDP repository/service is the best pilot candidate?
- Do we have reliable access to current pipeline timing data (Drone UI / API)?
- Which integration dependency is safest for the first Testcontainers pilot (Redis? Kafka?)?
- Does the Drone DIND service support Docker access from Maven test step (`DOCKER_HOST`)?
- Can Testcontainers run in the main CI `mvn clean install` step, or does it need a separate Drone step?
- Is `TESTCONTAINERS_RYUK_DISABLED=true` sufficient, or does Ryuk need an alternative cleanup strategy?
- Does the current DIND image support BuildKit / `docker buildx`?
- Who owns the RepoSync source for `.drone.star`? What is the change request process?
- Is there an existing platform-owned base image strategy?
- What is the approval process for DIND / privileged runner changes?
- Does the MR (pull_request) event really run only a blank pipeline? If so, how do developers get CI feedback on MRs?
- Is there potential duplicate Maven work between the `mvn clean install` step and the `integration-tests` compose container?

---

## Recommended First Local Changes

The first local changes should be small and low-risk:

1. Add or validate `.dockerignore` (T3.2 — minimal effort, immediate context-size reduction)
2. Capture current Docker build timing with `scripts/measure-baseline.sh`
3. Review Dockerfile layer ordering (T3.1)
4. Propose one Dockerfile cache optimisation (T3.3)
5. Measure local build before/after (T3.4)
6. Identify one candidate integration test for Testcontainers (T4.1)

Avoid combining Dockerfile optimisation and Testcontainers changes in the same MR — keep changes attributable.
