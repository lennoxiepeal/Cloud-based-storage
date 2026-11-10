# Local deployment verification

Verified on 10 November 2026 on this Ubuntu 24.04 / WSL machine.

| Check | Result |
|---|---|
| React production build and Maven package | PASS; existing React lint/dependency-data warnings remain |
| Application and storage service startup | PASS; `http://localhost:8080/actuator/health` reports UP |
| API integration checks | PASS (`logs/api-verification.log`) |
| Chromium browser workflow checks | PASS (`logs/browser-verification.log`); no uncaught page errors |
| Stop/start persistence | PASS (`logs/persistence-verification.log`); folder metadata and SHA-256 file hashes preserved |

API verification covers correct/incorrect login, registration, anonymous request denial, user/admin separation, hidden password hashes, nested folders and cycle rejection, breadcrumbs, folder rename/move, direct S3 uploads, exact download bytes, storage accounting, pagination, basic/advanced search, file rename/move, public access and revocation, ZIP contents, bulk move/delete, quotas, live permission changes, administrator statistics, folder deletion, and logout.

Browser verification covers the compiled React application: login/logout, folder creation/navigation, multiple file uploads including S3 CORS, listing, rename, public links in a separate browser session, public route reload, revocation, filename and folder search, advanced type filtering and reset, per-user/global quota editing, permission editing, and admin statistics/users.

Sample files are in `demo-files/`. The `demo` account contains `Demo workspace`, a nested `Reports` folder, and three uploaded files. Normal demo/admin permissions and 1 GB quotas are restored. Test accounts prefixed `check` remain available for examples of multiple users.

Fixes made while preparing the deployment include incorrect demo credentials/download permissions, double-counted upload quota usage, backup work running despite being disabled, S3 client selection/endpoint configuration, local S3 chunked-upload compatibility, nested-folder creation/cycle checks, nonempty folder deletion, file/folder write permission checks, password hash serialization, duplicate ZIP entry handling, current-folder listing, folder search visibility, and upload error details.

This verifies local application behavior. It does not verify AWS/IBM infrastructure, backups through SQS/Lambda, CloudWatch publishing, Internet access from other machines, or production load/security readiness. The existing JMeter plan was not run. No teacher grading rubric was supplied; the recording guide covers the implemented main features.

Windows/WSL follow-up: removed the startup script's ripgrep dependency and changed app/storage bindings so Windows localhost forwarding reaches both. Verified startup with `PATH=/usr/bin:/bin` (without Codex's `rg`), Windows `curl.exe` frontend/health access, and a real Windows upload/download through a presigned S3 URL with identical bytes. The temporary connection-check file was deleted.

Fresh-checkout bootstrap verification: exported the staged Git tree to a new `/tmp/cloud-storage-fresh.*` directory with no `.tools`, build outputs, dependencies, or database. Ran `env -u JAVA_HOME PATH=/usr/bin:/bin ./scripts/start-local.sh`; it downloaded its own Java/Node/Maven, installed dependencies from lockfiles, built the frontend/backend, and reached Ready. The complete API integration suite passed against this fresh database/storage. Windows `curl.exe` reached the fresh deployment at localhost. A subsequent start reused the installation without npm installation or a build. The scripts do not require ripgrep. Supported bootstrap target: Linux x86_64 / Ubuntu WSL2.
