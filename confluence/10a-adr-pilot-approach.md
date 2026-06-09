# ADR-0001: Run a Measured Pilot, Not a Rollout

| Field | Value |
|-------|-------|
| **Parent page** | Container & CI/CD Optimisation Pilot — FDP Initial Scope |
| **Created by** | Benan Aktas |
| **Status** | Draft |
| **Last updated** | 2026-06-09 |
| **Last reviewed** | 2026-06-09 |
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |

> This decision is **Proposed** — not yet approved. It will be reviewed with stakeholders as part of Story 6.

---

## ADR-0001: Run a Measured Pilot, Not a Rollout

**Status:** Proposed

### Context

The FDP CI/CD pipeline currently suffers from several pain points:

- Docker builds take approximately 5 minutes due to poor layer caching and large build contexts (~200 MB).
- The full pipeline duration is approximately 12 minutes end-to-end.
- Docker Compose integration test setup requires ~90 seconds to start 7+ services.
- Integration tests are flaky due to shared state, port conflicts, and environment drift between local and CI.
- Dockerfiles are single-stage, producing ~450 MB images that ship JDK and build tools to production.
- The `.drone.star` pipeline configuration is centrally managed via RepoSync — local edits are overwritten.

A big-bang rollout across multiple repositories would carry significant risk: if assumptions prove wrong, multiple teams are affected simultaneously without evidence to justify the changes. The team lacks concrete baseline measurements to prove current pain or validate improvement ideas.

Additionally, the current DSA focus is on Core Cloud and Data Platform. Any request for ACP or ETO resources must be backed by measured evidence from a real repository, not theoretical proposals.

### Decision

We will run a measured pilot on **one** representative FDP adaptor repository. The pilot will:

- Capture before/after metrics for every change.
- Validate improvement ideas locally before proposing wider adoption.
- Produce evidence to support any future platform change request to ACP or DSA ETO.
- Classify each improvement by ownership (CST-local, ACP, or DSA ETO).

No changes will be proposed for rollout until pilot evidence confirms benefit.

### Consequences

**Positive:**

- Low blast radius — only one repository is affected during validation.
- Evidence-based — decisions are backed by measured before/after data.
- Stakeholder confidence — concrete numbers are more persuasive than theoretical proposals.
- Clear ownership — the pilot distinguishes what CST can do locally from what requires platform support.
- Reversible — local changes on one repository can be reverted trivially.

**Negative:**

- Slower time-to-value — other repositories do not benefit until after the pilot.
- Pilot repo may not be fully representative of all FDP adaptors.
- Results may not generalise to repositories with different dependency profiles.
- Requires discipline to avoid scope creep beyond one repository.

**Follow-ups:**

- State scope limits explicitly in the Story 6 summary.
- Recommend a second repo before any org-wide rollout.
- Route RepoSync/platform and wider ETO items via Story 6 with evidence attached.

### Alternatives Considered

| Option | Pros | Cons | Why not chosen |
|--------|------|------|----------------|
| Big-bang rollout across all FDP repos | Fast impact if it works | High risk; no baseline evidence; hard to reverse; unclear ownership | Too risky for unproven changes |
| Do nothing | No effort or risk | Pain points persist; build/test friction continues to grow | Does not address known problems |
| Pilot across many repos simultaneously | Broader evidence base | Heavy coordination; defeats small/controlled intent | Disproportionate for a first pilot |
| Start with ACP/ETO changes first | Addresses infra gaps | Slow; depends on another team's priority; no CST evidence to justify ask | Better to show local evidence first |

---


---

*Feedback or questions? Contact the page owner or comment below.*
