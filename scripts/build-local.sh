#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/environment.sh"
if curl --noproxy '*' --connect-timeout 1 --max-time 2 -fsS http://127.0.0.1:8080/actuator/health >/dev/null 2>&1; then
  echo 'Stop the running app with ./scripts/stop-local.sh before rebuilding.' >&2
  exit 1
fi
npm run build --prefix frontend
mkdir -p src/main/resources/static
cp -R frontend/build/. src/main/resources/static/
mvn -Dmaven.repo.local="$PROJECT_ROOT/.tools/m2" package

python3 scripts/build-fingerprint.py > .tools/build-fingerprint
