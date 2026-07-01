# T3.3 — Apply Dockerfile layering / cache improvement

**Story:** [Story 3 — Docker Build Optimisation](./README.md)

| Field | Value |
|-------|-------|
| **ID** | T3.3 |
| **Type** | Implementation |
| **Epic** | Pilot Container & CI/CD Optimisation |
| **Story** | Story 3 — Docker Build Optimisation |
| **Estimate** | 2 |
| **Priority** | Must |
| **Labels** | `docker`, `dockerfile`, `layering`, `cache` |
| **Sprint** | Week 2 |
| **Depends on** | T3.1 |
| **Owner** | _TBD_ |
| **Status** | Not started |

## Why
The biggest practical Story 3 wins are expected to come from reducing the packaging image size, improving Docker layer reuse and reducing build context. The current Dockerfile packages pre-built Maven artefacts; it does not build the application inside Docker. Applying one focused change keeps the impact measurable and easy to review.

## Goal
Prototype a single, well-understood Dockerfile/build-context improvement locally, or prepare it as a RepoSync-ready change if direct production editing is not owned by the pilot repository.

## Scope
Consider (pick the highest-value one for this repo):
- move package/envconsul/user setup before application `COPY` instructions so code changes do not invalidate rarely changing setup layers
- split the current monolithic `RUN` into logical layers where that improves cache granularity and readability
- review the `amazoncorretto:17` full-JDK runtime base against an approved smaller runtime base available through the organisation's image-source / Artifactory path
- add or validate `.dockerignore` alongside the Dockerfile change so unnecessary generated files are not sent to the daemon
- use BuildKit cache mounts only for local prototypes or after Drone/DIND smoke validation, because CI support is currently unproven

Do not change the lifecycle to build Maven inside the Dockerfile unless that larger design is explicitly selected. The existing pipeline builds Maven artefacts first, then the Dockerfile copies `target/cmd-adaptor-sns-exec.jar` and `target/dependencies/opentelemetry-javaagent.jar`.

Reference pattern for the current packaging-only Dockerfile:
```dockerfile
FROM <approved-java17-runtime-base>

WORKDIR /tmp

# Rarely changing setup first.
RUN <install required packages, envconsul and fdpuser using approved sources>

# Frequently changing artefacts last.
COPY ./target/dependencies/opentelemetry-javaagent.jar /local/opentelemetry-javaagent.jar
COPY ./target/cmd-adaptor-sns-exec.jar /local/cmd-adaptor-sns-exec.jar

WORKDIR /home/fdpuser
USER fdpuser
CMD ["java", "-javaagent:/local/opentelemetry-javaagent.jar", "-jar", "/local/cmd-adaptor-sns-exec.jar"]
```

> Apply **one** focused change at a time — not a full rewrite — so the effect can be attributed clearly. Production Dockerfile changes should be routed through RepoSync unless ownership is confirmed otherwise.

## Acceptance criteria
- [ ] One focused Dockerfile/build-context change is prototyped locally or prepared as a RepoSync-ready change
- [ ] Expected benefit is described qualitatively and tied to the T2.3 measured baseline
- [ ] Compatibility risks or concerns are noted
- [ ] Approved image-source / Artifactory and runtime compatibility constraints are noted for any base-image change
- [ ] Built image passes local smoke checks; Trivy scan result is captured if a candidate image is built
