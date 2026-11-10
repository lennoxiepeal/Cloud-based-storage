#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/environment.sh"
source scripts/process-local.sh
scripts/prepare-local.sh
source scripts/environment.sh
mkdir -p logs .local-data/objects
for dependency in curl python3 setsid; do
  command -v "$dependency" >/dev/null || { echo "Missing required tool: $dependency" >&2; exit 1; }
done
start_service() {
  local name="$1"; shift
  if service_running "$name"; then
    echo "$name already running"
  else
    setsid "$@" >"logs/$name.log" 2>&1 < /dev/null &
    echo $! >"logs/$name.pid"
  fi
}
start_service storage node scripts/storage.cjs
storage_ready=false
for attempt in {1..30}; do
  if curl --noproxy '*' --connect-timeout 1 --max-time 2 -s http://127.0.0.1:4569/ > /dev/null; then storage_ready=true; break; fi
  sleep 1
done
if [[ "$storage_ready" != true ]]; then
  echo 'Storage startup failed. Recent log:' >&2
  tail -n 20 logs/storage.log >&2
  exit 1
fi
start_service app java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=local --server.address=0.0.0.0
for attempt in {1..60}; do
  if curl --noproxy '*' --connect-timeout 1 --max-time 2 -fsS http://127.0.0.1:8080/actuator/health 2>/dev/null | python3 -c 'import json,sys; sys.exit(0 if json.load(sys.stdin).get("status") == "UP" else 1)' 2>/dev/null; then
    echo 'Ready: http://localhost:8080'
    echo 'User: demo / demo123 | Admin: admin / admin123'
    exit 0
  fi
  sleep 1
done
echo 'Startup failed. Recent service logs:' >&2
tail -n 20 logs/app.log logs/storage.log >&2
exit 1
