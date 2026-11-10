#!/usr/bin/env bash
# Kept as an alias; start-local now handles first-run installation and builds.
set -euo pipefail
exec "$(dirname "$0")/start-local.sh"
