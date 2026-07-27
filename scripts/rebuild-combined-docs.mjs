import fs from "node:fs";

const files = [
  "README.md",
  "docs/PROJECT-CONTEXT.md",
  "docs/PIPELINE-CONTEXT.md",
  "docs/SCOPE-AND-GUARDRAILS.md",
  "PROJECT-PLAN.md",
  "SECURITY.md",
  "CONTRIBUTING.md",
  "docs/glossary.md",
  "docs/stories/STATUS-BOARD.md",
  "docs/stories/INDEX.md",
  "docs/stories/STORY-5-6-CONSOLIDATION.md",
  "docs/stories/DEFINITION-OF-DONE.md",
  "docs/stories/metrics-template.md",
  "docs/stories/tech-notes.md",
  "docs/stories/FUTURE-CONSIDERATIONS.md",
  "docs/stories/story-1-pipeline-assessment/README.md",
  "docs/stories/story-1-pipeline-assessment/task-1-review-drone-star.md",
  "docs/stories/story-1-pipeline-assessment/task-2-local-vs-central.md",
  "docs/stories/story-1-pipeline-assessment/task-3-map-ci-steps.md",
  "docs/stories/story-1-pipeline-assessment/task-4-testcontainers-feasibility.md",
  "docs/stories/story-1-pipeline-assessment/task-5-buildkit-feasibility.md",
  "docs/stories/story-2-baseline/README.md",
  "docs/stories/story-2-baseline/task-1-select-repo.md",
  "docs/stories/story-2-baseline/task-2-pipeline-baseline.md",
  "docs/stories/story-2-baseline/task-3-build-image-baseline.md",
  "docs/stories/story-2-baseline/task-4-integration-test-baseline.md",
  "docs/stories/story-3-build/README.md",
  "docs/stories/story-3-build/task-1-review-dockerfile.md",
  "docs/stories/story-3-build/task-2-dockerignore.md",
  "docs/stories/story-3-build/task-3-layering-improvement.md",
  "docs/stories/story-3-build/task-4-measure-impact.md",
  "docs/stories/story-4-testcontainers/README.md",
  "docs/stories/story-4-testcontainers/task-1-select-candidate.md",
  "docs/stories/story-4-testcontainers/task-2-implement-setup.md",
  "docs/stories/story-4-testcontainers/task-3-compare-flows.md",
  "docs/stories/story-4-testcontainers/task-4-document-findings.md",
  "docs/stories/story-5-compose/README.md",
  "docs/stories/story-5-compose/task-1-validate-compose-scope.md",
  "docs/stories/story-5-compose/task-2-decide-compose-role.md",
  "docs/stories/story-6-findings/README.md",
  "docs/stories/story-6-findings/task-1-classify-outcomes.md",
  "docs/stories/story-6-findings/task-2-decide-adoption.md",
  "docs/adr/README.md",
  "docs/adr/0001-pilot-not-rollout.md",
  "docs/adr/0002-testcontainers-for-integration-tests.md",
  "docs/adr/0003-reduce-compose-in-ci.md",
  "docs/adr/0004-buildkit-cache-and-layering.md",
  "docs/adr/0005-ci-runner-docker-mode.md",
  "docs/adr/template.md",
  "examples/README.md",
  "examples/ci/drone-considerations.md",
];

const included = files.map((file) => `- \`${file}\``).join("\n");
const sections = files
  .map((file) => `> Source: \`${file}\`\n\n${fs.readFileSync(file, "utf8").trim()}`)
  .join("\n\n\n---\n\n");
const output = `# Combined Documentation

Generated from the current project Markdown files. Source documents are left unchanged.

## Included Documents

${included}

---

## Overview

${sections}
`;

fs.writeFileSync("COMBINED-DOCUMENTATION.md", output);
fs.writeFileSync("solution/COMBINED-DOCUMENTATION.md", output);
