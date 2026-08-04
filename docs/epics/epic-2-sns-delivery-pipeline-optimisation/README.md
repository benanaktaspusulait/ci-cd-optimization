# Epic 2 — SNS Delivery Pipeline Optimisation

| Field | Value |
|---|---|
| **Status** | In progress |
| **Type** | Post-pilot delivery epic |
| **Depends on** | [Pilot closure record](../../PILOT-CLOSURE.md), task-specific pilot evidence and the relevant repository/platform/release owners |

## Why

Pilot follow-up work has two distinct technical outcomes: optimising the SNS image build
and extending the Testcontainers foundation into a full SNS integration path. Each outcome
has a single task covering implementation, validation and adoption.

## Goal

Deliver both outcomes through independently owned stories, each with one task that covers
implementation, CI validation and adoption end to end.

## Stories

| ID | Story | Status | Single task |
|---|---|---|---|
| E2-S1 | [Productionise the SNS Image-Build Optimisation](./story-1-sns-image-build-optimisation/README.md) | In progress | E2-S1.1 — Implement, Validate and Prepare Adoption |
| E2-S2 | [Productise the SNS Testcontainers Integration Path](./story-2-sns-testcontainers-integration/README.md) | Proposed / New | E2-S2.1 — Implement and Validate the SNS Testcontainers Integration Path |

## Structure

```
Epic 2 — SNS Delivery Pipeline Optimisation
├── Story 1 — Productionise the SNS Image-Build Optimisation
│   └── E2-S1.1 — Implement, Validate and Prepare Adoption
│                 of the SNS Image-Build Optimisation
│
└── Story 2 — Productise the SNS Testcontainers Integration Path
    └── E2-S2.1 — Implement and Validate the SNS
                  Testcontainers Integration Path
```

## Boundaries / non-goals

- No Compose migration or service removal.
- No `kd`-to-Helm migration, umbrella-chart adoption, PVC removal or legacy-component deletion.
- No CD pipeline review or target definition — preserved in [future-backlog/](./future-backlog/).
- No local result is represented as CI evidence.
- No production or CI adoption is claimed without the responsible owner's decision.

## Epic acceptance criteria

- [ ] E2-S1 and E2-S2 retain independent scope, dependencies and completion states.
- [ ] Each story is implemented, validated and ready for adoption in a single task.
- [ ] Local and CI evidence remain distinguishable across both stories.
- [ ] Existing Compose/full-E2E path remains unchanged.
- [ ] No adoption is claimed until the responsible owner records the decision.
