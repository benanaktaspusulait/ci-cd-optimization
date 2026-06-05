#!/usr/bin/env bash
# build-docs.sh — Build the MkDocs HTML site from project markdown files.
#
# Usage:
#   ./scripts/build-docs.sh
#
# Output:
#   site/ — static HTML site (zip and share, or host on any web server)
#
# Prerequisites:
#   pip install mkdocs mkdocs-material

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
BUILD_DIR="${PROJECT_ROOT}/_build_docs"
CONTENT_DIR="${BUILD_DIR}/content"
SITE_DIR="${PROJECT_ROOT}/site"

echo "[build-docs] Preparing docs source..."
rm -rf "$BUILD_DIR" "$SITE_DIR"
mkdir -p "$CONTENT_DIR"

# Copy all content into CONTENT_DIR (this becomes mkdocs docs_dir)
cp "$PROJECT_ROOT/README.md" "$CONTENT_DIR/index.md"
cp "$PROJECT_ROOT/CONTRIBUTING.md" "$CONTENT_DIR/"
cp "$PROJECT_ROOT/PROJECT-PLAN.md" "$CONTENT_DIR/"
cp "$PROJECT_ROOT/SECURITY.md" "$CONTENT_DIR/"

cp "$PROJECT_ROOT/docs/PROJECT-CONTEXT.md" "$CONTENT_DIR/"
cp "$PROJECT_ROOT/docs/PIPELINE-CONTEXT.md" "$CONTENT_DIR/"
cp "$PROJECT_ROOT/docs/SCOPE-AND-GUARDRAILS.md" "$CONTENT_DIR/"
cp "$PROJECT_ROOT/docs/glossary.md" "$CONTENT_DIR/"

mkdir -p "$CONTENT_DIR/adr"
cp "$PROJECT_ROOT/docs/adr/"*.md "$CONTENT_DIR/adr/"

cp -r "$PROJECT_ROOT/docs/stories" "$CONTENT_DIR/stories"
cp -r "$PROJECT_ROOT/examples" "$CONTENT_DIR/examples"

# Fix internal links: adjust paths so MkDocs can resolve them in the flattened structure.
# In the flattened layout:
#   - Root files (README, PROJECT-PLAN, etc.) are at CONTENT_DIR root
#   - docs/ content is directly under CONTENT_DIR (no docs/ prefix)
echo "[build-docs] Fixing internal links for flat structure..."
find "$CONTENT_DIR" -name "*.md" -exec sed -i '' \
  -e 's|(docs/stories/|(stories/|g' \
  -e 's|(docs/adr/|(adr/|g' \
  -e 's|(docs/PROJECT-CONTEXT.md)|(PROJECT-CONTEXT.md)|g' \
  -e 's|(docs/PIPELINE-CONTEXT.md)|(PIPELINE-CONTEXT.md)|g' \
  -e 's|(docs/SCOPE-AND-GUARDRAILS.md)|(SCOPE-AND-GUARDRAILS.md)|g' \
  -e 's|(docs/glossary.md)|(glossary.md)|g' \
  -e 's|\.\./\.\./\.\./README\.md|../../../index.md|g' \
  -e 's|\.\./\.\./README\.md|../../index.md|g' \
  -e 's|\.\./README\.md|../index.md|g' \
  -e 's|(README\.md)|(index.md)|g' \
  -e 's|\.\./\.\./PROJECT-PLAN\.md|../../PROJECT-PLAN.md|g' \
  -e 's|\.\./\.\./SECURITY\.md|../../SECURITY.md|g' \
  -e 's|\.\./\.\./CONTRIBUTING\.md|../../CONTRIBUTING.md|g' \
  {} \;

# Generate mkdocs.yml in BUILD_DIR (one level above content)
cat > "$BUILD_DIR/mkdocs.yml" << 'EOF'
site_name: Container & CI/CD Optimisation Pilot
site_description: FDP CI/CD optimisation pilot — planning, backlog, ADRs and examples
docs_dir: content
use_directory_urls: false
site_url: ""

theme:
  name: material
  palette:
    - scheme: default
      primary: indigo
      accent: teal
      toggle:
        icon: material/brightness-7
        name: Switch to dark mode
    - scheme: slate
      primary: indigo
      accent: teal
      toggle:
        icon: material/brightness-4
        name: Switch to light mode
  features:
    - navigation.tabs
    - navigation.sections
    - navigation.expand
    - navigation.top
    - content.code.copy

plugins: []

markdown_extensions:
  - tables
  - admonition
  - pymdownx.details
  - pymdownx.superfences
  - pymdownx.highlight:
      anchor_linenums: true
  - pymdownx.inlinehilite
  - pymdownx.tasklist:
      custom_checkbox: true
  - toc:
      permalink: true

nav:
  - Home: index.md
  - Context:
    - Project Context: PROJECT-CONTEXT.md
    - Pipeline Context: PIPELINE-CONTEXT.md
    - Scope & Guardrails: SCOPE-AND-GUARDRAILS.md
    - Glossary: glossary.md
  - Plan:
    - Project Plan: PROJECT-PLAN.md
    - Security: SECURITY.md
    - Contributing: CONTRIBUTING.md
  - Backlog:
    - Index: stories/INDEX.md
    - Status Board: stories/STATUS-BOARD.md
    - Definition of Done: stories/DEFINITION-OF-DONE.md
    - Metrics Template: stories/metrics-template.md
    - Technical Notes: stories/tech-notes.md
    - Future Considerations: stories/FUTURE-CONSIDERATIONS.md
  - Stories:
    - "Story 1 — Pipeline Assessment":
      - Overview: stories/story-1-pipeline-assessment/README.md
      - "T1.1 Review .drone.star": stories/story-1-pipeline-assessment/task-1-review-drone-star.md
      - "T1.2 Local vs RepoSync": stories/story-1-pipeline-assessment/task-2-local-vs-central.md
      - "T1.3 Map CI steps": stories/story-1-pipeline-assessment/task-3-map-ci-steps.md
      - "T1.4 Testcontainers feasibility": stories/story-1-pipeline-assessment/task-4-testcontainers-feasibility.md
      - "T1.5 BuildKit feasibility": stories/story-1-pipeline-assessment/task-5-buildkit-feasibility.md
    - "Story 2 — Baseline":
      - Overview: stories/story-2-baseline/README.md
      - "T2.1 Select repo": stories/story-2-baseline/task-1-select-repo.md
      - "T2.2 Pipeline baseline": stories/story-2-baseline/task-2-pipeline-baseline.md
      - "T2.3 Build & image baseline": stories/story-2-baseline/task-3-build-image-baseline.md
      - "T2.4 Integration test baseline": stories/story-2-baseline/task-4-integration-test-baseline.md
    - "Story 3 — Build Optimisation":
      - Overview: stories/story-3-build/README.md
      - "T3.1 Review Dockerfile": stories/story-3-build/task-1-review-dockerfile.md
      - "T3.2 .dockerignore": stories/story-3-build/task-2-dockerignore.md
      - "T3.3 Layering improvement": stories/story-3-build/task-3-layering-improvement.md
      - "T3.4 Measure impact": stories/story-3-build/task-4-measure-impact.md
    - "Story 4 — Testcontainers":
      - Overview: stories/story-4-testcontainers/README.md
      - "T4.1 Select candidate": stories/story-4-testcontainers/task-1-select-candidate.md
      - "T4.2 Implement setup": stories/story-4-testcontainers/task-2-implement-setup.md
      - "T4.3 Compare flows": stories/story-4-testcontainers/task-3-compare-flows.md
      - "T4.4 Document findings": stories/story-4-testcontainers/task-4-document-findings.md
    - "Story 5 — Compose Rationalisation":
      - Overview: stories/story-5-compose/README.md
      - "T5.1 Map services": stories/story-5-compose/task-1-map-services.md
      - "T5.2 Classify usage": stories/story-5-compose/task-2-classify-usage.md
      - "T5.3 Recommend role": stories/story-5-compose/task-3-recommend-role.md
    - "Story 6 — Findings & Ownership":
      - Overview: stories/story-6-findings/README.md
      - "T6.1 Consolidate findings": stories/story-6-findings/task-1-consolidate-findings.md
      - "T6.2 Classify ownership": stories/story-6-findings/task-2-classify-ownership.md
      - "T6.3 Share with stakeholders": stories/story-6-findings/task-3-share-stakeholders.md
  - ADRs:
    - Index: adr/README.md
    - "ADR-0001 Pilot not rollout": adr/0001-pilot-not-rollout.md
    - "ADR-0002 Testcontainers": adr/0002-testcontainers-for-integration-tests.md
    - "ADR-0003 Reduce Compose in CI": adr/0003-reduce-compose-in-ci.md
    - "ADR-0004 BuildKit cache": adr/0004-buildkit-cache-and-layering.md
    - "ADR-0005 CI runner mode": adr/0005-ci-runner-docker-mode.md
  - Examples:
    - Overview: examples/README.md
    - Drone Considerations: examples/ci/drone-considerations.md
EOF

echo "[build-docs] Building MkDocs site..."
mkdocs build -f "$BUILD_DIR/mkdocs.yml" -d "$SITE_DIR" --quiet 2>&1 | grep -v "WARNING\|INFO" || true

echo "[build-docs] Cleaning up build dir..."
rm -rf "$BUILD_DIR"

PAGES=$(find "$SITE_DIR" -name "*.html" | wc -l | tr -d ' ')
echo ""
echo "✅ Site built: ${PAGES} pages in $SITE_DIR/"
echo ""
echo "   View locally:  open $SITE_DIR/index.html"
echo "   Zip to share:  (cd $(dirname $SITE_DIR) && zip -r site.zip site/)"
