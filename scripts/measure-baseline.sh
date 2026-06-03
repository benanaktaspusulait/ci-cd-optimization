#!/usr/bin/env bash
# measure-baseline.sh — Capture local Docker build and image-size metrics (T1.3 / T2.4)
#
# Usage:
#   ./scripts/measure-baseline.sh [LABEL]
#
#   LABEL  Optional label for this measurement (e.g. "before" or "after-layering").
#          Defaults to the current git short SHA.
#
# Output:
#   Appends one row to metrics-output/build-metrics.csv
#   Prints a human-readable summary to stdout
#
# Prerequisites:
#   - Docker with BuildKit enabled
#   - jq (for JSON parsing)
#   - The Dockerfile must be at the project root
#
# References:
#   docs/stories/story-1-baseline/task-3-build-image-baseline.md
#   docs/stories/story-2-build/task-4-measure-impact.md
#   docs/stories/metrics-template.md

set -euo pipefail

# ── Configuration ─────────────────────────────────────────────────────────────
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
OUTPUT_DIR="${PROJECT_ROOT}/metrics-output"
CSV_FILE="${OUTPUT_DIR}/build-metrics.csv"
IMAGE_TAG="pilot-baseline-measure:tmp"
LABEL="${1:-$(git -C "$PROJECT_ROOT" rev-parse --short HEAD 2>/dev/null || echo "unknown")}"

# ── Setup ─────────────────────────────────────────────────────────────────────
mkdir -p "$OUTPUT_DIR"

# Write CSV header if file does not exist.
if [[ ! -f "$CSV_FILE" ]]; then
  echo "timestamp,label,build_duration_s,image_size_bytes,image_size_mb,no_cache_duration_s" > "$CSV_FILE"
fi

# ── Helpers ───────────────────────────────────────────────────────────────────
log() { echo "[$(date '+%H:%M:%S')] $*"; }

# ── Warm build (with cache) ───────────────────────────────────────────────────
log "Starting warm build (with local cache) — label: ${LABEL}"
WARM_START=$(date +%s)
DOCKER_BUILDKIT=1 docker build \
  --tag "$IMAGE_TAG" \
  --file "${PROJECT_ROOT}/Dockerfile" \
  "$PROJECT_ROOT" \
  2>&1 | tail -5
WARM_END=$(date +%s)
WARM_DURATION=$(( WARM_END - WARM_START ))
log "Warm build completed in ${WARM_DURATION}s"

# ── Image size ────────────────────────────────────────────────────────────────
IMAGE_SIZE_BYTES=$(docker image inspect "$IMAGE_TAG" --format '{{.Size}}')
IMAGE_SIZE_MB=$(echo "scale=1; ${IMAGE_SIZE_BYTES} / 1048576" | bc)
log "Image size: ${IMAGE_SIZE_MB} MB (${IMAGE_SIZE_BYTES} bytes)"

# ── Cold build (no cache) ─────────────────────────────────────────────────────
log "Starting cold build (--no-cache) to measure worst-case..."
COLD_START=$(date +%s)
DOCKER_BUILDKIT=1 docker build \
  --no-cache \
  --tag "${IMAGE_TAG}-nocache" \
  --file "${PROJECT_ROOT}/Dockerfile" \
  "$PROJECT_ROOT" \
  2>&1 | tail -5
COLD_END=$(date +%s)
COLD_DURATION=$(( COLD_END - COLD_START ))
log "Cold build completed in ${COLD_DURATION}s"

# ── Record ────────────────────────────────────────────────────────────────────
TIMESTAMP=$(date '+%Y-%m-%dT%H:%M:%S')
echo "${TIMESTAMP},${LABEL},${WARM_DURATION},${IMAGE_SIZE_BYTES},${IMAGE_SIZE_MB},${COLD_DURATION}" >> "$CSV_FILE"

# ── Summary ───────────────────────────────────────────────────────────────────
echo ""
echo "──────────────────────────────────────────────"
echo "  Measurement: ${LABEL}"
echo "  Timestamp:   ${TIMESTAMP}"
echo "  Warm build:  ${WARM_DURATION}s"
echo "  Cold build:  ${COLD_DURATION}s"
echo "  Image size:  ${IMAGE_SIZE_MB} MB"
echo "──────────────────────────────────────────────"
echo "  Saved to: ${CSV_FILE}"
echo ""

# ── Cleanup ───────────────────────────────────────────────────────────────────
docker image rm "$IMAGE_TAG" "${IMAGE_TAG}-nocache" 2>/dev/null || true
