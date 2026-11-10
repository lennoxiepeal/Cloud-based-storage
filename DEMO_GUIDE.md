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

## Suggested recording: 8–12 minutes

Use two browser profiles or a private window for the administrator so you can switch between accounts. Save downloaded files if the browser asks; text/images may open in a tab because download URLs serve their original content types.

| Step | What to do | What it demonstrates |
|---|---|---|
| 1 | Explain React → Spring Boot → H2 metadata + S3-compatible file storage. Sign in with an incorrect password once, then correct it. | Architecture, authentication, validation |
| 2 | Optionally register a new account through **Sign Up**, sign in, and show its empty storage. Use `demo` for the remaining steps. | Registration and account isolation |
| 3 | Create `Final Assignment`, enter it with a single click, create `Reports` inside, and navigate using breadcrumbs and Back. | Nested folders and navigation |
| 4 | Choose `project-report.txt`, `storage-results.csv`, and `architecture.svg` from `demo-files/`. Click **Upload 3 File(s)** and wait for success. | Multiple uploads, upload progress, file metadata, storage meter |
| 5 | Download a file and open it. Explain that the backend grants a time-limited URL and bytes go directly to object storage. | Download and presigned access |
| 6 | Rename the report; search its new name. Clear the search and press Enter. Open **Advanced Search**, filter Text or Images, then **Clear Filters**. Size filters use MB. Search `Reports` to show folder search. | Rename, filename/folder search, content type and size filters |
| 7 | Move one file into `Reports`; navigate into it and back. Rename or move an empty test folder. | Single file/folder organization |
| 8 | Click the file's share icon, copy its link, and open it in a private window without signing in. Show its download. Return to the owner, revoke the link, and reload the private window. | Public sharing, anonymous access, revocation |
| 9 | Select two files with checkboxes. Download their ZIP and open it. Bulk move them; then delete disposable copies. Show the storage meter decrease. | Selection, ZIP download, bulk move/delete, accounting |
| 10 | Sign in as `admin` in the second profile. Show total users, used storage, total quota, overall use, and top storage users. | Administration and monitoring |
| 11 | On the `demo` row, set quota to **0.1 GB**, save, and refresh the user page. Try uploading `quota-test.bin` (110 MiB); show the quota error. Restore quota to **1 GB**. | Per-user quota enforcement |
| 12 | Disable **Read** for `demo`, save permissions, and try downloading as that user. Restore Read. Disable Create/Write to show upload denial, then restore all three. | Live administrator permission controls |
| 13 | Set global quota to **2 GB**, click **Apply to All**, and show the updated rows. Restore to **1 GB**. | System-wide quota settings |
| 14 | Delete an empty test folder. To remove a nonempty one, select **delete contents** in its dialog. Log out and show the login screen. | Folder deletion and session termination |

For pagination, upload more than 20 small files and show Next/Previous plus **Select All (Page)** / **Select All Records**. This is optional if recording time is limited; pagination is covered by the API verification.

Deletion is permanent for file bytes. There is no working recycle-bin UI in this project. Revoking a share prevents new download URLs; an already-issued presigned URL remains valid until it expires. Links are reachable on this machine only in this deployment.

## Verification

```bash
python3 scripts/verify-local.py
./scripts/verify-browser.sh
```

The API check creates a fresh `check...` account and exercises real stored bytes, ZIP contents, nested folder operations, search/filter/pagination, sharing/revocation, quota/permission changes, and deletion. Browser checks create a sample workspace under `demo`. These tests alter demo data; run them before recording.

Browser tests are optional and require Chromium and its Linux libraries. After first startup, install Chromium with `PLAYWRIGHT_BROWSERS_PATH="$PWD/.tools/browsers" .tools/local-services/node_modules/.bin/playwright install chromium`. If it reports missing system libraries on a new machine, run `.tools/local-services/node_modules/.bin/playwright install-deps chromium` (requires sudo). The application itself does not require Chromium. Results and screenshots are in `logs/`. See `VERIFICATION.md` for the final results.

If startup fails, read `logs/app.log` and `logs/storage.log`. Ports 8080 and 4569 must be available. Preserve `.local-data/database.mv.db` together with `.local-data/objects/` when backing up the demo; stop services before copying them.

The browser verification replaces sample folders named `Demo workspace` or `Demo workspace <timestamp>` in the demo account. Keep your recording work in `Final Assignment` as suggested above.

## Windows/WSL connection troubleshooting

The startup script uses Python 3 for the health check; ripgrep (`rg`) is not required. The app and S3 service listen on all WSL interfaces so Windows can reach both through localhost. If you run an older instance, stop and start it to apply the binding:

```bash
./scripts/stop-local.sh
./scripts/start-local.sh
./scripts/status-local.sh
```

Open `http://localhost:8080` (plain HTTP). Startup reports Ready only after the application health check succeeds. “Already running” means a matching service process exists; it is followed by the readiness check.
