# References

| Field | Value |
|-------|-------|
| **Parent page** | [Container & CI/CD Optimisation Pilot](00-parent-overview.md) |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |

---

## Internal Repositories

| Repository | Purpose | Access |
|------------|---------|--------|
| `fdp-cmd-adaptor-dvla` | Pilot candidate adaptor (DVLA trailer registration) | Requires GitLab access |
| `dde-adaptor-reposync` | Central RepoSync source for `.drone.star` pipeline | Requires GitLab access |
| `ci-cd-optimization` (this repo) | Pilot planning, templates, examples | GitHub (benanaktaspusulait) |
| MMA Helm service repo | Deploys all FDP services to Kubernetes | Requires GitLab access |
| Runbook repository | Approved release steps for environments | Requires GitLab access |

---

## Architecture Decision Records (ADRs)

| ADR | Title | Key decision |
|-----|-------|--------------|
| ADR-0001 | Pilot not rollout | Validate on one repo before proposing wider adoption |
| ADR-0002 | Testcontainers for integration tests | Pilot one dependency locally; reuse disabled in CI |
| ADR-0003 | Reduce Compose in CI, keep for local | CI: prefer Testcontainers; local: keep Compose |
| ADR-0004 | BuildKit cache + multi-stage builds | Multi-stage local; remote cache post-pilot (ACP) |
| ADR-0005 | CI runner Docker mode (Drone/DIND) | Assess DIND access; fallback = local-only Testcontainers |

Full ADR text with alternatives and consequences: see `docs/adr/` in the repository.

---

## KT Sessions (Knowledge Transfer)

| Date | Presenter | Topic | Key takeaways |
|------|-----------|-------|---------------|
| TBC | Liam Moncur | Deployment scripts / Helm | Deploy via MMA service repo; no automated rollback; feature flags via Helm values |
| TBC | Herbie Barnett / Benjamin Reynolds | Release tagging | Tag → tag pipeline → Maven + Trivy + Sonar + Helm package → Artifactory; Jira integration for changelog |
| TBC | Stephen Craine | Feature branch → release flow | Feature branches from Jira; Thursdays = release day; PNR room for prod access; Gareth automating release |

---

## Technology Documentation

| Topic | Source |
|-------|--------|
| Testcontainers | https://java.testcontainers.org/ |
| Testcontainers Kafka module | https://java.testcontainers.org/modules/kafka/ |
| Docker BuildKit | https://docs.docker.com/build/buildkit/ |
| Docker multi-stage builds | https://docs.docker.com/build/building/multi-stage/ |
| Drone CI | https://docs.drone.io/ |
| Drone Starlark | https://docs.drone.io/pipeline/scripting/starlark/ |
| Helm | https://helm.sh/docs/ |
| Confluent Platform (cp-kafka 7.5.5) | https://docs.confluent.io/platform/7.5/ |
| Amazon Corretto 17 | https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/ |
| Trivy | https://aquasecurity.github.io/trivy/ |

---

## Related Confluence Pages

| Page | Status |
|------|--------|
| TBC — link to DSA Tech Strategy page | TBC |
| TBC — link to ACP CI/CD tooling page | TBC |
| TBC — link to Cerberus Delivery board | TBC |

---

*Feedback or questions? Contact the page owner or comment below.*
