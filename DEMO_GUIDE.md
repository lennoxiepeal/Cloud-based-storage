# Local deployment and recording guide

Open **http://localhost:8080** in your browser. The React frontend and Spring Boot API share this address.

| Account | Password | Purpose |
|---|---|---|
| demo | demo123 | File and folder demonstration |
| admin | admin123 | Quotas, permissions, and system statistics |

This is a local classroom deployment: H2 stores metadata on disk and S3rver provides persistent S3-compatible object storage. Uploads use real presigned URLs and direct browser PUT requests. AWS SQS/Lambda, CloudWatch publishing, and IBM backup are disabled. Do not describe those cloud integrations as verified in this recording. Roles are displayed; the UI edits create/read/write permissions, not roles.

## Start, stop, and rebuild

Run from this repository in the Ubuntu/WSL terminal:

```bash
./scripts/start-local.sh
./scripts/stop-local.sh
```

You do not need to keep a terminal open. Start again after a machine/WSL restart. Data remains in `.local-data/`; logs are in `logs/`. This setup does not install a boot-time service.

To rebuild changed source, stop the app first:

```bash
./scripts/stop-local.sh
./scripts/build-local.sh
./scripts/start-local.sh
```

On a new Linux x86_64 / Ubuntu WSL2 machine, clone this repository and run `./scripts/start-local.sh`. First start automatically installs portable Java, Node.js, Maven, and locked dependencies under `.tools/`, then builds and starts the application. It requires internet access and the base tools Bash, curl, tar, xz, Python 3, and util-linux. On a minimal Ubuntu install, install missing base tools with `sudo apt-get install curl tar xz-utils python3 util-linux`.

Later starts reuse the installation. Application source changes trigger a rebuild automatically. `setup-local.sh` remains an alias for `start-local.sh`. No AWS account is needed. Windows users should run these commands in Ubuntu on WSL2; native Windows and macOS bootstrap are not supported.

Each new clone has fresh demo/admin accounts and local sample files. Existing uploads and accounts live only in `.local-data/` and are not pushed to Git.

The local app and storage service bind to all WSL interfaces so Windows localhost forwarding can reach them. Use this demo on a trusted network. In a Windows browser on this WSL machine, use the same localhost URL. To pick sample files in Windows, browse to `\\wsl.localhost\Ubuntu-24.04\home\hoang\workspace\Cloud-based-storage\demo-files` (replace Ubuntu-24.04 with your distribution name if different).

## Suggested recording: 4–5 minutes

Follow **[DEMO_SCRIPT_VI.md](./DEMO_SCRIPT_VI.md)** for a simple **4:45 presentation to your professor**. It starts by listing the app's main features, then provides Vietnamese narration and English click instructions for each feature.

**Use any small file already on your computer. No sample files, fixed filenames, or customer story are required.** You can select a second file during upload if you want to demonstrate batch actions with multiple files. The same script also works with one file.

The sequence covers authentication, folders, uploads/downloads, rename/search, move/ZIP, public sharing and revocation, admin statistics, quotas, permissions, deletion, and logout. Start the app before recording. The script ends at **4:45**; rehearse once and trim loading waits to stay under five minutes.

## Verification

```bash
python3 scripts/verify-local.py
./scripts/verify-browser.sh
```

The API check creates a fresh `check...` account and exercises real stored bytes, ZIP contents, nested folder operations, search/filter/pagination, sharing/revocation, quota/permission changes, and deletion. Browser checks create a sample workspace under `demo`. These tests alter demo data; run them before recording.

Browser tests are optional and require Chromium and its Linux libraries. After first startup, install Chromium with `PLAYWRIGHT_BROWSERS_PATH="$PWD/.tools/browsers" .tools/local-services/node_modules/.bin/playwright install chromium`. If it reports missing system libraries on a new machine, run `.tools/local-services/node_modules/.bin/playwright install-deps chromium` (requires sudo). The application itself does not require Chromium. Results and screenshots are in `logs/`. See `VERIFICATION.md` for the final results.

If startup fails, read `logs/app.log` and `logs/storage.log`. Ports 8080 and 4569 must be available. Preserve `.local-data/database.mv.db` together with `.local-data/objects/` when backing up the demo; stop services before copying them.

The browser verification replaces sample folders named `Demo workspace` or `Demo workspace <timestamp>` in the demo account. Keep your recording work in the separate `Demo` folder used by the short script.

## Windows/WSL connection troubleshooting

The startup script uses Python 3 for the health check; ripgrep (`rg`) is not required. The app and S3 service listen on all WSL interfaces so Windows can reach both through localhost. If you run an older instance, stop and start it to apply the binding:

```bash
./scripts/stop-local.sh
./scripts/start-local.sh
./scripts/status-local.sh
```

Open `http://localhost:8080` (plain HTTP). Startup reports Ready only after the application health check succeeds. “Already running” means a matching service process exists; it is followed by the readiness check.
