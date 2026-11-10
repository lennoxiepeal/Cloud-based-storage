#!/usr/bin/env python3
"""Identify build inputs so start-local can rebuild after source changes."""
import hashlib
from pathlib import Path
root = Path(__file__).resolve().parent.parent
paths = [root / 'pom.xml']
for directory in ['src/main/java', 'src/main/resources', 'frontend/src', 'frontend/public', 'scripts/local-services']:
    paths.extend(p for p in (root / directory).rglob('*') if p.is_file())
paths.extend(root / 'frontend' / name for name in ['package.json', 'package-lock.json'])
h = hashlib.sha256()
for p in sorted(set(paths)):
    relative = p.relative_to(root).as_posix()
    if relative.startswith('src/main/resources/static/') or relative == 'src/main/resources/application.properties':
        continue
    h.update(relative.encode() + b'\0' + p.read_bytes() + b'\0')
print(h.hexdigest())
