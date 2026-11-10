#!/usr/bin/env bash
# Only manage matching services launched from this checkout.
service_running() {
  local name="$1" pid args process_directory
  [[ -f "logs/$name.pid" ]] || return 1
  read -r pid < "logs/$name.pid" || return 1
  [[ "$pid" =~ ^[0-9]+$ ]] || return 1
  kill -0 "$pid" 2>/dev/null || return 1
  process_directory="$(readlink "/proc/$pid/cwd" 2>/dev/null)" || return 1
  [[ "$process_directory" == "$PROJECT_ROOT" ]] || return 1
  args="$(ps -p "$pid" -o args=)" || return 1
  case "$name" in
    app) [[ "$args" == *'java -jar target/demo-0.0.1-SNAPSHOT.jar'* ]] ;;
    storage) [[ "$args" == *'node scripts/storage.cjs'* ]] ;;
    *) return 1 ;;
  esac
}
