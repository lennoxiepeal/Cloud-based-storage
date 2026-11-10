#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/environment.sh"
source scripts/process-local.sh
curl --noproxy '*' --connect-timeout 1 --max-time 3 --fail --silent --show-error http://127.0.0.1:8080/actuator/health || true
printf '\n'
for service in app storage; do
  if service_running "$service"; then
    echo "$service running (PID $(cat "logs/$service.pid"))"
  else
    echo "$service stopped"
  fi
done
