#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/environment.sh"
if [[ "$(uname -s)" != Linux || "$(uname -m)" != x86_64 ]]; then
  echo 'Local bootstrap supports Linux x86_64, including Ubuntu on WSL2.' >&2
  exit 1
fi
for dependency in curl tar xz python3 setsid; do
  command -v "$dependency" >/dev/null || { echo "Missing required tool: $dependency. On Ubuntu: sudo apt-get install curl tar xz-utils python3 util-linux" >&2; exit 1; }
done
mkdir -p .tools/downloads logs .local-data/objects
java_ready=false
for java_dir in .tools/jdk-17*; do
  if [[ -x "$java_dir/bin/java" ]]; then java_ready=true; break; fi
done
if [[ "$java_ready" != true ]]; then
  echo 'Installing portable Java 17...'
  curl -fL --retry 2 'https://api.adoptium.net/v3/binary/latest/17/ga/linux/x64/jdk/hotspot/normal/eclipse' -o .tools/downloads/java.tar.gz
  tar -xzf .tools/downloads/java.tar.gz -C .tools
fi
if [[ ! -x .tools/node-v22.16.0-linux-x64/bin/node ]]; then
  echo 'Installing portable Node.js...'
  curl -fL --retry 2 https://nodejs.org/dist/v22.16.0/node-v22.16.0-linux-x64.tar.xz -o .tools/downloads/node.tar.xz
  tar -xJf .tools/downloads/node.tar.xz -C .tools
fi
if [[ ! -x .tools/apache-maven-3.9.9/bin/mvn ]]; then
  echo 'Installing portable Maven...'
  curl -fL --retry 2 https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.tar.gz -o .tools/downloads/maven.tar.gz
  tar -xzf .tools/downloads/maven.tar.gz -C .tools
fi
source scripts/environment.sh
if [[ ! -f .tools/local-services/node_modules/s3rver/package.json ]] || ! cmp -s scripts/local-services/package-lock.json .tools/local-services/package-lock.json; then
  echo 'Installing local service dependencies...'
  mkdir -p .tools/local-services
  cp scripts/local-services/package.json scripts/local-services/package-lock.json .tools/local-services/
  npm ci --prefix .tools/local-services --no-audit --no-fund
fi
fingerprint="$(python3 scripts/build-fingerprint.py)"
previous_fingerprint=""
if [[ -f .tools/build-fingerprint ]]; then read -r previous_fingerprint < .tools/build-fingerprint; fi
if [[ ! -f target/demo-0.0.1-SNAPSHOT.jar || "$fingerprint" != "$previous_fingerprint" ]]; then
  echo 'Building the application (first start needs internet and may take several minutes)...'
  npm ci --prefix frontend --no-audit --no-fund
  scripts/build-local.sh
fi
python3 - <<'PY'
from pathlib import Path
p = Path('demo-files/quota-test.bin')
if not p.exists():
    with p.open('wb') as f:
        f.truncate(110 * 1024 * 1024)
PY
