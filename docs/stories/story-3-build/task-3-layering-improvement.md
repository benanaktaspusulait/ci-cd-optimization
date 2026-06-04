# T3.3 — Apply Dockerfile layering / cache improvement

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.3 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | M |
| **Priority** | Must |
| **Labels** | `docker`, `dockerfile`, `layering`, `cache` |
| **Sprint** | Week 2 |
| **Depends on** | T3.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The biggest build-time wins usually come from ordering layers so dependencies are cached separately from source code, and from using build cache mounts. Applying one focused change keeps the impact measurable and easy to review.

## Goal
Apply a single, well-understood layering or cache improvement to the pilot Dockerfile.

## Scope
Consider (pick the highest-value one for this repo):
- copy dependency metadata before source code
- separate dependency resolution from application build
- use multi-stage builds
- use cache mounts for the dependency cache

Reference pattern:
```dockerfile
# syntax=docker/dockerfile:1
FROM company/java17-maven-base:1.0 AS deps
WORKDIR /app
COPY pom.xml .mvn mvnw ./
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B dependency:go-offline

FROM deps AS build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B package -DskipTests

FROM company/java17-runtime-base:1.0
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
```

> Apply **one** focused change at a time — not a full rewrite — so the effect can be attributed clearly.

## Acceptance criteria
- [ ] One layering/cache change is applied
- [ ] Expected benefit is described
- [ ] Compatibility risks or concerns are noted
- [ ] Built image passes Trivy scan without new Critical vulnerabilities (non-blocking report)
