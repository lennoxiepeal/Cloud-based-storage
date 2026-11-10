#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/environment.sh"
source scripts/process-local.sh
for service in app storage; do
  file="logs/$service.pid"
  if [[ -f "$file" ]]; then
    pid="$(cat "$file")"
    if service_running "$service"; then
      kill "$pid"
      for attempt in {1..30}; do
        service_running "$service" || break
        sleep 1
      done
    fi
    unlink "$file"
  fi
done
echo 'Stopped. Database and files are preserved in .local-data/.'
