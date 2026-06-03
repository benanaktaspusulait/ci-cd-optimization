# Container & CI/CD Optimisation Pilot

> **Status:** Improvement suggestions / pilot planning  
> **Scope:** FDP as the immediate pilot context, with potential reusable patterns for wider projects  
> **Intent:** Validate selected optimisation ideas through a small, measurable pilot before proposing wider rollout

---

## 1. Executive Summary

This repository/document captures a structured follow-up for the **Container & CI/CD Optimisation** improvement suggestions.

The aim is **not** to start a large implementation programme immediately. The aim is to organise the ideas into a controlled pilot so that the team can:

- measure the current baseline,
- identify practical optimisation opportunities,
- validate selected ideas locally where possible,
- understand which items need wider platform / ETO input,
- and decide whether any patterns are worth reusing across other projects.

The immediate focus is FDP because that is where the current examples and observations came from. However, the ideas are **not intended to be FDP-only**. If the pilot proves useful, some patterns may become reusable across other projects.

---

## 2. Background

Current CI/CD and container workflows can create repeated friction when projects grow in size and complexity.

Common pain points include:

- long build times,
- heavy Docker Compose based integration test setups,
- slow feedback loops,
- environment differences between local and CI,
- flaky or environment-related integration test failures,
- repeated dependency downloads,
- inconsistent Dockerfile patterns across repositories,
- and unclear ownership for improvements that may require platform-level support.

The improvement suggestions discussed so far include:

- Docker base image strategy,
- Dockerfile layering optimisation,
- `.dockerignore` standardisation,
- Docker BuildKit remote caching,
- Testcontainers-based integration testing,
- pre-built test images,
- selective ephemeral environments,
- CI/CD observability,
- and possible future platform-style reusable capabilities.

---

## 3. Important Framing

This work should be positioned carefully.

### This is

```text
- A discussion starter
- A structured pilot proposal
- A way to validate practical improvements
- A way to capture measurable before/after evidence
- A way to identify reusable patterns
- A way to determine CST-local vs platform/ETO ownership
```

### This is not

```text
- A final implementation proposal
- An already approved change
- A guaranteed performance improvement
- A big-bang rollout
- A replacement for all Docker Compose usage
- A full platform transformation programme
```

### Key message

```text
The difference is not Docker vs no Docker.
The difference is optimised, standardised, cached and test-driven Docker usage.
```

---

## 4. Proposed Approach

The proposed approach is to create **one pilot epic** with a small number of stories and tasks.

The pilot should validate ideas in a controlled way before any wider rollout is considered.

```text
1 Epic
  └── 5 Pilot Stories
        └── Detailed tasks with goal, scope and success criteria
```

The recommended pilot stories are:

1. Baseline measurement and pilot scope
2. Dockerfile and build optimisation pilot
3. Testcontainers integration testing pilot
4. Docker Compose usage review
5. CST-local vs platform/ETO ownership assessment

---

## 5. Proposed Epic

## EPIC: Pilot Container & CI/CD Optimisation Improvements for FDP

### Description

This epic captures a pilot initiative to assess selected Container and CI/CD optimisation improvements within the FDP context.

The purpose is not to implement a full organisation-wide rollout at this stage. The aim is to validate a small number of improvement ideas through measurable pilot work and identify which patterns could be reused across other projects.

The pilot will focus on:

- baseline measurement,
- Dockerfile/build optimisation,
- Testcontainers-based integration testing,
- Docker Compose usage review,
- and identification of CST-local vs platform/ETO ownership areas.

### Goal

Validate whether selected Container and CI/CD optimisation patterns can reduce build/test overhead, improve integration test determinism, and provide reusable patterns for wider adoption.

### Success Criteria

```text
- Baseline metrics are captured for the selected pilot scope
- Dockerfile/build optimisation opportunities are identified and tested
- At least one Testcontainers-based integration test pilot is completed or assessed
- Current Docker Compose usage is reviewed and categorised
- Areas requiring platform/ETO input are identified
- Findings are documented with recommendations for next steps
```

---

## 6. Backlog Structure Summary

### Story 1 — Baseline Measurement & Candidate Selection

```text
STORY: Capture baseline metrics and select pilot scope

TASK: Identify suitable pilot repository/service
TASK: Capture current CI/CD pipeline baseline metrics
TASK: Capture current Docker image build and image size metrics
TASK: Capture current integration test setup/runtime observations
```

### Story 2 — Dockerfile and Build Optimisation Pilot

```text
STORY: Assess Dockerfile layering and build optimisation opportunities

TASK: Review current Dockerfile and build context
TASK: Add or validate .dockerignore for pilot repository
TASK: Propose Dockerfile layering improvements
TASK: Measure local and CI build impact after Dockerfile optimisation
```

### Story 3 — Testcontainers Integration Testing Pilot

```text
STORY: Pilot Testcontainers for selected integration dependency

TASK: Identify candidate integration test for Testcontainers pilot
TASK: Implement Testcontainers setup for one dependency
TASK: Compare Testcontainers-based test flow with existing docker-compose flow
TASK: Document Testcontainers pilot findings and constraints
```

### Story 4 — Docker Compose Usage Review

```text
STORY: Review docker-compose usage in current integration test flow

TASK: Map services currently started by docker-compose
TASK: Identify which services are actually required by integration tests
TASK: Categorise docker-compose usage between CI testing and local debugging
TASK: Recommend reduced docker-compose role for pilot scope
```

### Story 5 — Platform / ETO Ownership Assessment

```text
STORY: Identify CST-local vs platform/ETO-owned optimisation areas

TASK: Identify improvements that can be validated locally within CST
TASK: Identify improvements requiring platform/ETO input or ownership
TASK: Prepare recommendation for ticket ownership and target board
TASK: Share pilot findings with relevant engineering stakeholders
```

---

## 7. Detailed Stories and Tasks

---

# Story 1 — Capture baseline metrics and select pilot scope

## STORY: Capture baseline metrics and select pilot scope

### Description

Before implementing optimisation changes, capture baseline metrics for the selected pilot repository/service.

This provides a measurable starting point so that any future improvements can be compared against current behaviour.

### Goal

Establish a clear before-state for pipeline duration, build time, image size, and integration test setup/runtime.

### Success Criteria

```text
- Pilot repository/service is selected
- Current pipeline duration is captured
- Current Docker build duration is captured
- Current integration test duration/setup time is captured
- Current image size is captured
- Findings are documented and shared
```

---

## TASK: Identify suitable pilot repository/service

### Description

Identify a suitable repository or service to use as the pilot for Container and CI/CD optimisation work.

The selected pilot should be representative enough to validate the improvement ideas but small enough to avoid excessive delivery risk.

### Scope

```text
- Review candidate FDP repositories/services
- Consider pipeline duration, Docker Compose usage, test complexity and current delivery priorities
- Recommend one pilot repository/service
```

### Success Criteria

```text
- One candidate pilot repository/service is identified
- Rationale for selection is documented
- Pilot scope is agreed with relevant stakeholders
```

---

## TASK: Capture current CI/CD pipeline baseline metrics

### Description

Capture current CI/CD pipeline metrics for the selected pilot repository/service.

### Scope

```text
Capture:
- average pipeline duration
- build stage duration
- unit test duration
- integration test duration
- failed pipeline frequency if available
```

### Success Criteria

```text
- Baseline pipeline metrics are documented
- Data source or method of measurement is noted
- Metrics can be used for before/after comparison
```

---

## TASK: Capture current Docker image build and image size metrics

### Description

Capture the current Docker image build time and image size for the selected pilot repository/service.

### Scope

```text
Capture:
- local Docker build time if applicable
- CI Docker build time if available
- final image size
- base image currently used
```

### Success Criteria

```text
- Current Docker build duration is documented
- Current image size is documented
- Current base image/build approach is identified
```

---

## TASK: Capture current integration test setup/runtime observations

### Description

Capture current integration test setup and runtime observations for the selected pilot repository/service.

### Scope

```text
Review:
- how integration tests are currently started
- Docker Compose dependencies involved
- startup/wait time before tests run
- common flaky or environment-related issues if known
```

### Success Criteria

```text
- Current integration test setup is documented
- Required dependencies are listed
- Any known pain points or flaky behaviours are captured
```

---

# Story 2 — Assess Dockerfile layering and build optimisation opportunities

## STORY: Assess Dockerfile layering and build optimisation opportunities

### Description

Assess whether Dockerfile and build context improvements can reduce build time and improve cache reuse for the selected pilot repository/service.

### Goal

Identify and validate practical Docker build optimisations that can be applied locally within the pilot scope.

### Success Criteria

```text
- Current Dockerfile/build context is reviewed
- .dockerignore is added or validated
- Dockerfile layering improvements are proposed
- Build time before/after is compared where possible
```

---

## TASK: Review current Dockerfile and build context

### Description

Review the current Dockerfile and Docker build context for the selected pilot repository/service.

### Scope

```text
Review:
- current base image
- layer ordering
- dependency installation steps
- COPY instructions
- build context size
- unnecessary files included in Docker context
```

### Success Criteria

```text
- Current Dockerfile structure is documented
- Build cache invalidation risks are identified
- Potential optimisation opportunities are listed
```

---

## TASK: Add or validate .dockerignore for pilot repository

### Description

Add or validate a `.dockerignore` file for the selected pilot repository/service to reduce unnecessary Docker build context.

### Suggested `.dockerignore`

```gitignore
.git
.gitlab
target
build
.idea
.vscode
*.iml
*.log
node_modules
.DS_Store
coverage
.tmp
```

### Scope

```text
Check whether unnecessary files are excluded from Docker build context.
```

### Success Criteria

```text
- .dockerignore exists and is appropriate for the repository
- Unnecessary files are excluded from Docker build context
- Build context reduction is noted where measurable
```

---

## TASK: Propose Dockerfile layering improvements

### Description

Propose Dockerfile changes to improve cache reuse and reduce unnecessary rebuilds.

### Scope

```text
Consider:
- copying dependency metadata before source code
- separating dependency resolution from application build
- using multi-stage builds where appropriate
- using common base image where applicable
```

### Example pattern

```dockerfile
# syntax=docker/dockerfile:1

FROM company/java17-maven-base:1.0 AS deps
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B dependency:go-offline

FROM deps AS build
COPY src ./src

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B package -DskipTests

FROM company/java17-runtime-base:1.0
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

### Success Criteria

```text
- Proposed Dockerfile changes are documented
- Expected benefit is described
- Risks or compatibility concerns are noted
```

---

## TASK: Measure local and CI build impact after Dockerfile optimisation

### Description

Measure the impact of Dockerfile/build context changes on local and/or CI build duration.

### Scope

```text
Compare:
- before local build time
- after local build time
- before CI build time if available
- after CI build time if available
- final image size before/after
```

### Success Criteria

```text
- Before/after build metrics are captured
- Any improvement or regression is documented
- Recommendation is made on whether to keep or adjust the changes
```

---

# Story 3 — Pilot Testcontainers for selected integration dependency

## STORY: Pilot Testcontainers for selected integration dependency

### Description

Pilot the use of Testcontainers for one selected integration test dependency in the pilot repository/service.

The aim is to assess whether Testcontainers can reduce reliance on full Docker Compose setup for selected integration tests and improve test isolation/determinism.

### Goal

Validate whether Testcontainers is suitable for at least one FDP integration test dependency and identify any constraints before wider adoption.

### Success Criteria

```text
- Candidate test/dependency is selected
- Testcontainers setup is implemented or prototyped
- Existing Docker Compose based flow is compared with Testcontainers flow
- Findings, risks and recommendations are documented
```

---

## TASK: Identify candidate integration test for Testcontainers pilot

### Description

Identify one integration test or dependency suitable for a small Testcontainers pilot.

### Scope

Assess candidate dependencies such as:

```text
- Redis
- Kafka
- Schema Registry
- LocalStack
```

### Selection Criteria

Prefer a candidate that:

```text
- is used by existing integration tests
- has manageable setup complexity
- provides useful validation value
- does not require large-scale refactoring for the first pilot
```

### Success Criteria

```text
- One candidate test/dependency is selected
- Rationale for selection is documented
- Pilot scope is agreed before implementation
```

---

## TASK: Implement Testcontainers setup for one dependency

### Description

Implement or prototype Testcontainers setup for the selected dependency.

### Scope

```text
Implement:
- container definition
- required environment/property wiring
- readiness/wait strategy
- cleanup/isolation approach
```

### Example pattern

```java
@Container
static KafkaContainer kafka = new KafkaContainer(
    DockerImageName.parse("confluentinc/cp-kafka:7.5.5")
);

@DynamicPropertySource
static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
}
```

### Success Criteria

```text
- Selected dependency can be started by the test using Testcontainers
- Test can connect to the dependency successfully
- Setup can run locally
- CI suitability is assessed or noted
```

---

## TASK: Compare Testcontainers-based test flow with existing docker-compose flow

### Description

Compare the Testcontainers-based test flow with the existing Docker Compose based integration test setup.

### Scope

Compare:

```text
- setup/startup time
- test runtime
- complexity
- local developer experience
- CI suitability
- isolation/determinism
```

### Success Criteria

```text
- Comparison is documented
- Benefits and drawbacks are identified
- Recommendation is made on whether to continue with Testcontainers for further tests
```

---

## TASK: Document Testcontainers pilot findings and constraints

### Description

Document the findings from the Testcontainers pilot.

### Scope

Document:

```text
- what was tested
- what worked
- what did not work
- performance observations
- reliability/isolation observations
- limitations
- recommended next steps
```

### Success Criteria

```text
- Findings are documented and shared
- Constraints are clearly identified
- Recommendation is available for stakeholders
```

---

# Story 4 — Review Docker Compose usage in current integration test flow

## STORY: Review Docker Compose usage in current integration test flow

### Description

Review current Docker Compose usage in the pilot repository/service to understand which services are required for CI integration testing and which are mainly useful for local debugging.

### Goal

Identify opportunities to reduce unnecessary Docker Compose dependency in CI while preserving useful local debugging workflows.

### Success Criteria

```text
- Services started by Docker Compose are mapped
- Required vs optional services are identified
- CI testing usage is separated from local debugging usage
- Recommendation is documented
```

---

## TASK: Map services currently started by docker-compose

### Description

Map all services currently started by Docker Compose for the pilot repository/service.

### Scope

Capture:

```text
- service name
- image/build source
- dependency relationships
- exposed ports
- purpose if known
```

### Success Criteria

```text
- Docker Compose services are listed
- Dependencies between services are understood
- Unclear services are flagged for review
```

---

## TASK: Identify which services are actually required by integration tests

### Description

Identify which Docker Compose services are actually required by the integration test flow.

### Scope

Classify services as:

```text
- required for integration tests
- required only for local debugging
- optional/unclear
- potentially removable from CI flow
```

### Success Criteria

```text
- Required test dependencies are identified
- Non-essential services are identified
- Any uncertainty is documented
```

---

## TASK: Categorise docker-compose usage between CI testing and local debugging

### Description

Categorise current Docker Compose usage into CI integration testing use cases and local manual debugging use cases.

### Scope

Review:

```text
- where Docker Compose is invoked
- whether it is used in CI
- whether it is used by developers locally
- whether the same compose file serves multiple purposes
```

### Success Criteria

```text
- CI vs local Docker Compose usage is documented
- Any mixed-purpose Compose usage is identified
- Recommendation is made for separation if needed
```

---

## TASK: Recommend reduced docker-compose role for pilot scope

### Description

Prepare a recommendation on whether Docker Compose usage can be reduced for the pilot scope.

### Scope

Recommend:

```text
- what should remain in Docker Compose
- what could move to Testcontainers
- what should remain for local debugging
- what should not be changed during the pilot
```

### Success Criteria

```text
- Recommendation is documented
- Risk/impact is noted
- Recommendation is reviewed with relevant stakeholders
```

---

# Story 5 — Identify CST-local vs platform/ETO-owned optimisation areas

## STORY: Identify CST-local vs platform/ETO-owned optimisation areas

### Description

Review the proposed optimisation areas and identify which can be validated locally within CST and which may require platform/ETO awareness, input or ownership.

### Goal

Avoid progressing changes independently where wider engineering ownership or awareness is required.

### Success Criteria

```text
- CST-local items are identified
- Platform/ETO-dependent items are identified
- Recommended board/ownership is proposed for each item
- Stakeholders are informed before broader changes are progressed
```

---

## TASK: Identify improvements that can be validated locally within CST

### Description

Identify which optimisation items can be safely validated locally within CST as part of the pilot.

### Likely CST-local candidates

```text
- Baseline measurement
- Dockerfile review
- .dockerignore validation
- Local Dockerfile layering experiment
- Small Testcontainers pilot
- Docker Compose usage review
```

### Success Criteria

```text
- CST-local candidate items are listed
- Each item has a short rationale
- Any assumptions are documented
```

---

## TASK: Identify improvements requiring platform/ETO input or ownership

### Description

Identify which optimisation areas may require platform/ETO input, awareness or ownership.

### Likely platform/ETO candidates

```text
- Organisation-maintained base images
- Shared CI/CD templates
- BuildKit remote cache infrastructure
- Shared Testcontainers helper libraries
- Security scanning standards
- Ephemeral environment platform capability
```

### Success Criteria

```text
- Platform/ETO-dependent items are listed
- Reason for wider ownership is documented
- Suggested stakeholder/team is identified where possible
```

---

## TASK: Prepare recommendation for ticket ownership and target board

### Description

Prepare a recommendation for where each candidate ticket should sit.

### Scope

For each candidate item, recommend:

```text
- CST board
- ETO/platform board
- shared visibility only
- further discussion needed
```

### Success Criteria

```text
- Candidate items are mapped to suggested ownership/board
- Recommendation is reviewed with Thomas/Allen or relevant stakeholders
- No wider-impact item is progressed without appropriate visibility
```

---

## TASK: Share pilot findings with relevant engineering stakeholders

### Description

Share the pilot findings and candidate ownership recommendations with relevant engineering stakeholders.

### Scope

Share:

```text
- pilot scope
- baseline findings
- local optimisation findings
- Testcontainers findings
- ownership recommendations
- suggested next steps
```

### Success Criteria

```text
- Findings are shared with agreed stakeholders
- Feedback is captured
- Next steps are agreed or documented
```

---

## 8. Recommended First Ticket Creation Order

Do not create all tickets immediately unless agreed.

Recommended order:

```text
1. EPIC: Pilot Container & CI/CD Optimisation Improvements for FDP
2. STORY: Capture baseline metrics and select pilot scope
3. TASK: Identify suitable pilot repository/service
4. TASK: Capture current CI/CD pipeline baseline metrics
5. STORY: Assess Dockerfile layering and build optimisation opportunities
6. TASK: Review current Dockerfile and build context
```

This keeps the work controlled and avoids creating too many implementation tickets before baseline and ownership are agreed.

---

## 9. Technical Recommendations

This section captures technical recommendations that can support the pilot.

---

## 9.1 Baseline Measurement

Before making changes, capture baseline metrics.

### Metrics to capture

```text
- Average pipeline duration
- Build stage duration
- Unit test duration
- Integration test duration
- Docker image build duration
- Integration test environment startup time
- Flaky test rate if available
- Cache hit/miss rate if available
- Docker image size
- Failed pipeline rate if available
```

### Why this matters

Without baseline data, it will be difficult to prove whether any optimisation has actually improved the situation.

---

## 9.2 Dockerfile Standardisation

A common Dockerfile pattern should be introduced or assessed across pilot projects.

### Key principles

```text
- Copy dependency metadata before source code
- Use Maven cache mounts where possible
- Separate dependency resolution from application build
- Use multi-stage builds
- Keep runtime image small
- Avoid copying unnecessary files into the build context
```

---

## 9.3 Docker Base Image Strategy

A base image strategy can reduce duplication and improve consistency.

### Recommended layering model

```text
base-os
  ↓
base-runtime
  ↓
base-build
  ↓
application image
```

### Benefits

```text
- Standard runtime across projects
- Faster builds due to shared layers
- Centralised patching
- Easier compliance and vulnerability management
- Reduced duplication across repositories
```

### Governance requirements

```text
- Versioned tags
- Clear ownership
- Deprecation policy
- Compatibility testing
- Scheduled rebuilds
- Security scanning
```

---

## 9.4 BuildKit Remote Cache

CI runners may not keep local Docker cache between jobs. Remote cache can reduce repeated work.

### Example branch-aware cache strategy

```bash
docker buildx build \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:main \
  --cache-from=type=registry,ref=$REGISTRY_IMAGE/cache:$CI_COMMIT_REF_SLUG \
  --cache-to=type=registry,ref=$REGISTRY_IMAGE/cache:$CI_COMMIT_REF_SLUG,mode=max \
  --tag $REGISTRY_IMAGE:$CI_COMMIT_SHA \
  --push .
```

### Benefits

```text
- Branch builds can reuse main branch cache
- Repeated branch builds become faster
- Dependency-heavy layers are reused
- CI build time becomes more predictable
```

### Considerations

```text
- Cache invalidation must be controlled
- Separate cache refs may be needed per branch
- Fallback builds should still work if cache is unavailable
```

---

## 9.5 Testcontainers Strategy

Testcontainers should be assessed for integration tests that require dependencies such as:

```text
- Kafka
- Redis
- Schema Registry
- Databases
- LocalStack
```

### Main value

```text
- More isolated integration test environments
- More deterministic test setup
- Fewer environment-related failures
- Better local/CI consistency
- Reduced dependency on full Docker Compose setup in CI
```

### Important point

Testcontainers should not be described only as a speed improvement.

The stronger value is:

```text
isolated and deterministic integration testing
```

---

## 9.6 Testcontainers Reuse Policy

Reusable containers may be useful locally but should be treated carefully.

### Recommended policy

```text
Local development:
- Reuse may be enabled for faster feedback

CI:
- Reuse disabled
- Clean deterministic environment per run
- Avoid hidden shared state
```

---

## 9.7 Docker Compose Role Change

Docker Compose should not necessarily be removed.

Recommended model:

```text
CI integration tests:
- Prefer Testcontainers where suitable

Local manual debugging:
- Keep Docker Compose where useful

End-to-end / exploratory testing:
- Consider ephemeral environments selectively
```

### Key message

```text
Docker Compose can still have a role for local debugging; the suggestion is to reduce reliance on it in CI integration testing.
```

---

## 9.8 Security and Compliance Considerations

Optimisation should not bypass security controls.

### Recommended controls

```text
- Versioned base images
- Digest pinning for critical images where appropriate
- Vulnerability scanning
- SBOM generation
- Image signing if supported
- Scheduled base image rebuilds
- Deprecated image policy
- Secret-safe Docker builds
```

### Secret handling example

```dockerfile
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/settings.xml \
    mvn -B package
```

---

## 10. Future Platform Opportunities

These are not part of the immediate pilot scope, but they may become relevant if the pilot proves valuable.

### Potential future platform capabilities

```text
- Golden paths / paved roads
- Reusable CI/CD templates
- Service starter templates
- Internal developer portal / service catalogue
- Engineering scorecards
- Self-service preview environments
- Shared Testcontainers helper library
- Contract testing platform
- Standard observability package
- Policy as code
- Dependency proxy / artifact cache
- Standard security pipeline templates
- Engineering productivity dashboard
- One-command local development
- Environment abstraction
- Base image lifecycle management
- Developer documentation hub
- Migration playbooks
```

### Controlled framing

```text
The immediate focus is CI/CD and container optimisation, but if these patterns prove useful, they could evolve into reusable platform capabilities such as CI templates, maintained base images, shared test infrastructure, service templates and engineering scorecards.
```

---

## 11. Suggested Local Working Plan

Since this will be progressed locally first, the following working plan can be used.

### Step 1 — Prepare repository notes

```text
- Identify candidate pilot repository
- Capture existing pipeline links
- Capture current Dockerfile and docker-compose files
- Capture current integration test command
```

### Step 2 — Capture baseline

```text
- Run current pipeline or collect recent pipeline timings
- Capture local Docker build time
- Capture image size
- Capture integration test startup/runtime observations
```

### Step 3 — Review Dockerfile

```text
- Check base image
- Check COPY order
- Check dependency resolution layer
- Check build context
- Check .dockerignore
```

### Step 4 — Propose small change

```text
- Do not change too much at once
- Apply one Dockerfile/cache improvement
- Measure before/after
- Document findings
```

### Step 5 — Identify Testcontainers candidate

```text
- Pick one dependency first
- Prefer manageable scope
- Document existing docker-compose setup
- Prototype Testcontainers setup
```

### Step 6 — Compare and document

```text
- Compare startup time
- Compare complexity
- Compare local developer experience
- Note CI suitability
- Capture risks/constraints
```

### Step 7 — Share findings

```text
- Share measured results
- Share recommended next steps
- Identify items requiring CST vs platform/ETO ownership
```

---

## 12. Suggested Follow-up Message

Use this message when sharing the proposed structure.

```text
Hi all,

Following the discussion, I’ve structured the Container & CI/CD optimisation suggestions into a proposed pilot backlog rather than creating individual implementation tickets immediately.

The idea is to start with one pilot epic and a small number of stories covering:

- baseline measurement and pilot scope
- Dockerfile/build optimisation
- Testcontainers pilot
- Docker Compose usage review
- CST-local vs platform/ETO ownership assessment

I’ll use this structure to review priority and ownership first, before creating detailed individual tickets. This should help us keep the work controlled and make sure anything requiring wider platform/ETO visibility is identified early.
```

---

## 13. Glossary

### Docker base image

A shared starting image used by application Dockerfiles. It can standardise runtime/tooling and improve cache reuse.

### Dockerfile layering

The ordering of Dockerfile instructions so that expensive and rarely changing steps can be cached effectively.

### BuildKit

Docker build engine features that support better caching, cache mounts, secrets, and remote cache import/export.

### Testcontainers

A testing library/framework that allows tests to start containerised dependencies such as Kafka, Redis or databases dynamically during test execution.

### Ephemeral environment

A temporary environment created for a short period, often for a pull request or QA validation, and then automatically destroyed.

### TTL cleanup

Time-to-live based cleanup. Temporary environments are automatically deleted after a defined period or when no longer needed.

### Platform enablement

Creating reusable standards, templates and tooling that product teams can adopt instead of solving the same problems independently.

---

## 14. Final Recommendation

Do not start with a large rollout.

Start with:

```text
1. One pilot repository/service
2. Baseline measurement
3. One Dockerfile/build optimisation experiment
4. One small Testcontainers pilot
5. Docker Compose usage review
6. CST vs platform/ETO ownership assessment
7. Documented findings and recommendations
```

The goal is to create measurable evidence before proposing broader adoption.

---

## 15. Current Status

```text
Status: Draft / local working document
Owner: Benan Aktas
Next step: Review proposed backlog structure and agree first pilot scope
```
