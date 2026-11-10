#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/environment.sh"
export PLAYWRIGHT_BROWSERS_PATH="$PROJECT_ROOT/.tools/browsers"
export LD_LIBRARY_PATH="$PROJECT_ROOT/.tools/browser-libs/usr/lib/x86_64-linux-gnu:${LD_LIBRARY_PATH:-}"
node scripts/verify-browser.cjs
