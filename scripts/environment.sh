#!/usr/bin/env bash
# Source from other scripts. Safe before portable runtimes are installed.
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
for java_dir in "$PROJECT_ROOT"/.tools/jdk-17*; do
  if [[ -x "$java_dir/bin/java" ]]; then export JAVA_HOME="$java_dir"; break; fi
done
export npm_config_cache="$PROJECT_ROOT/.tools/npm-cache"
export PATH="$PROJECT_ROOT/.tools/node-v22.16.0-linux-x64/bin:$PROJECT_ROOT/.tools/apache-maven-3.9.9/bin:${JAVA_HOME:+$JAVA_HOME/bin:}$PATH"
cd "$PROJECT_ROOT"
