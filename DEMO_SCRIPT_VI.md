# App presentation — Vietnamese narration, English screen instructions

**Target: 4–5 minutes; timeline below ends at 4:45.** This is a straightforward presentation to your professor. Read the Vietnamese text aloud and do the English actions at the same time. Timestamps include clicking and waiting.

## Before recording

- Start the app with `./scripts/start-local.sh`, then open `http://localhost:8080`. Keep the terminal outside the recording.
- Use `demo` / `demo123` for the user account and `admin` / `admin123` for the administrator. Start on the login page in a normal browser window.
- **No sample files or fixed filenames are required.** Pick any small file already on your computer during the demo. You can select two files if you want to show multiple selection; the same script works with one.
- Keep one Incognito window ready on a blank tab. First use it for the public link, then use that same window to log in as admin after the sharing demo is finished. Leave the normal window signed in as `demo`.
- Allow pop-ups for localhost so downloads can open. If you rehearsed already, use a new folder name such as `Demo 2` instead of recreating an existing `Demo` folder.

## 0:00–0:25 — Introduce the main features

**On screen:** Show the login page. No need to open code, diagrams, or the terminal.

> “Dạ em chào thầy cô. Trong video này, em xin giới thiệu app lưu trữ tệp của nhóm em. App của bọn em có các chức năng chính gồm đăng ký, đăng nhập; quản lý tệp và thư mục; tìm kiếm, chia sẻ tệp; và quản lý người dùng, dung lượng, quyền truy cập. Sau đây em sẽ demo lần lượt các chức năng này.”

## 0:25–0:50 — Authentication

**On screen:** Point briefly at **Sign Up**. Then enter `demo` / `demo123` and click **Sign In**. Wait for the dashboard.

> “Đầu tiên là phần tài khoản. Người dùng có thể đăng ký tài khoản mới ở đây. Để tiết kiệm thời gian, em đăng nhập bằng tài khoản đã có sẵn. Sau khi đăng nhập, hệ thống hiển thị các tệp, thư mục và dung lượng lưu trữ của tài khoản này.”

**Expected result:** You are on the normal user dashboard. You only mention registration in this short recording; you do not fill out its form.

## 0:50–1:20 — Folders

**On screen:**

1. Click **New Folder**, enter `Demo`, and click **Create**.
2. Single-click **Demo** to enter it.
3. Create a child folder named `Thu muc con` in the same way.
4. Point to the breadcrumb above the toolbar. Stay inside **Demo**, without entering the child folder yet.

> “Tiếp theo là quản lý thư mục. Em tạo một thư mục mới, rồi tạo thêm thư mục con bên trong. App hỗ trợ thư mục nhiều cấp để sắp xếp tệp. Phần đường dẫn phía trên giúp người dùng biết mình đang ở thư mục nào và quay lại thư mục trước.”

**Expected result:** The breadcrumb ends in **Demo**, and **Thu muc con** is visible.

## 1:20–1:55 — Upload and download

**On screen:**

1. Click **Choose Files** and select **any small file on your computer**. Optionally select a second file with Ctrl. Do not create or prepare a special file.
2. Click **Upload 1 File(s)**, or the matching button if you selected more files. Wait for success, click **OK**, and wait for the table to refresh.
3. Point to the uploaded filename, size, date, and storage meter.
4. Click the uploaded file's downward-arrow **Download** icon. Show the completed download or new file tab briefly, then return to the app tab.

> “Về quản lý tệp, em chọn một tệp bất kỳ có sẵn trên máy và tải lên. App cũng cho phép chọn nhiều tệp trong một lần. Sau khi tải lên, danh sách hiển thị tên, kích thước và ngày tải; dung lượng sử dụng cũng được cập nhật. Em có thể tải tệp xuống bằng nút này.”

**Expected result:** Your uploaded file is visible in **Demo**. You have returned to the app tab.

## 1:55–2:40 — Rename, search, move, and ZIP download

**On screen:**

1. On one uploaded file, click the pencil **Rename** icon. Add `-demo` before its extension, then click **Rename** in the modal. For example, `photo.jpg` becomes `photo-demo.jpg`; keep your own file's extension.
2. Enter part of that filename in **Search files…** and press Enter. Show the result, then clear the search box and press Enter again.
3. Briefly open **Advanced Search** to show the type and size filters. You do not need to enter filter values; click **Clear Filters**.
4. Click **Select All (Page)**. Click the toolbar **Move**, choose **Thu muc con**, then click **Move Here**. Enter **Thu muc con**.
5. Click **Select All (Page)** again, then **Download ZIP (N)**. Dismiss the download-started alert and return to the app if another tab opens. Leave the file selection checked for the final deletion step.

> “Ngoài tải lên và tải xuống, người dùng còn có thể đổi tên tệp và tìm kiếm theo tên. Phần tìm kiếm nâng cao có thêm bộ lọc theo loại và kích thước. Ở đây, em chọn tệp rồi chuyển sang thư mục con. Với các tệp đã chọn, app cũng hỗ trợ tải xuống dưới dạng ZIP. Nếu chọn nhiều tệp thì các thao tác này được áp dụng cho cả nhóm.”

**Expected result:** You are inside **Demo / Thu muc con**, with your uploaded file or files visible and selected. A ZIP has downloaded. With one file, the ZIP contains one file; do not describe it as containing multiple files.

## 2:40–3:25 — Public sharing and revocation

**On screen:**

1. On your renamed file, click the curved-arrow **Make Public** icon.
2. In the **Public Link** modal, click **Copy Link** and dismiss any copied confirmation. Keep this modal open.
3. Switch to Incognito. Paste the link in the address bar and open it. Show the file information and **Download File** button; no login is needed.
4. Return to the normal window. Click **Make Private** in the still-open modal and confirm **OK**.
5. Return to Incognito and reload the shared-file page. Show the revoked-link message.

**While opening the link:**

> “Chức năng tiếp theo là chia sẻ tệp. Em tạo một liên kết công khai rồi mở bằng cửa sổ ẩn danh, tức là chưa đăng nhập. Người có liên kết có thể xem thông tin và tải tệp xuống mà không cần tài khoản.”

**While revoking it:**

> “Khi không muốn chia sẻ nữa, em chuyển tệp về riêng tư. Sau khi tải lại trang chia sẻ, hệ thống thông báo liên kết đã bị thu hồi.”

**Expected result:** Incognito shows **File not found or link has been revoked**. Keep that Incognito window for the next step.

## 3:25–4:25 — Administrator features

**On screen:**

1. In the same Incognito window, navigate to `http://localhost:8080/login`. Sign in with `admin` / `admin123`.
2. Show **Total Users**, **Storage Used**, **Total Quota**, and **Top Users by Storage**.
3. Find the **demo** row in **User Management**. Change its **Quota (GB)** to `2`, click that row's **Update**, and dismiss the success alert. Wait for the form to reload.
4. Briefly point to **Global Storage Quota / Apply to All**, without applying it.
5. On the **demo** row, uncheck **Read**, click **Save Permissions**, dismiss the alert, and wait for the form to reload. Then recheck **Read**, save again, and dismiss the success alert. Leave **Create**, **Write**, and **Read** enabled.

**While showing the statistics:**

> “Cuối cùng là phần quản trị. Khi đăng nhập bằng tài khoản admin, hệ thống hiển thị trang quản lý riêng. Admin có thể xem tổng số người dùng, dung lượng đã sử dụng, tổng hạn mức và những tài khoản đang dùng nhiều dung lượng nhất.”

**While editing the quota:**

> “Trong danh sách người dùng, admin có thể đặt hạn mức cho từng tài khoản. Ví dụ, em tăng dung lượng của tài khoản demo lên hai gigabyte. Ngoài ra, phần này cho phép áp dụng cùng một hạn mức cho tất cả người dùng.”

**While changing and restoring Read:**

> “Admin cũng có thể bật hoặc tắt các quyền tạo, ghi và đọc. Em thử tắt quyền đọc rồi lưu lại. Sau đó em bật lại để tài khoản tiếp tục tải tệp bình thường.”

**Expected result:** The demo row shows **2.00 GB**, and Read is enabled again. This short recording demonstrates saving permissions in the admin UI; it does not include a separate blocked-download test.

## 4:25–4:45 — Delete, log out, and finish

**On screen:**

1. Return to the normal user window. It should still be inside **Thu muc con**. If selection was cleared by a refresh, click **Select All (Page)** again.
2. Click the toolbar **Delete (N)** and confirm **OK**. Wait for the file table to refresh.
3. Click **Logout** and finish on the login page.

> “Cuối cùng, em xóa các tệp vừa dùng để demo. Dung lượng được cập nhật lại sau khi xóa. Người dùng có thể đăng xuất khi sử dụng xong. Đó là các chức năng chính của app lưu trữ tệp mà nhóm em đã thực hiện. Em cảm ơn thầy cô đã theo dõi.”

**Expected result:** The uploaded copies are removed from the app; the original files on your computer are unchanged. You are back on the login page.

## After recording

- If desired, restore the demo account's quota to **1 GB** in the admin window. Keep Create, Write, and Read enabled.
- Run `./scripts/stop-local.sh` when finished.
- Rehearse once to keep the total under five minutes. If behind schedule, skip the Advanced Search panel and opening the ZIP/file preview; keep the actual actions and visible results. Trim long loading waits instead of rushing the narration.
- This recording shows the local app. Do not claim that AWS/IBM backup, CloudWatch, or Internet hosting are demonstrated. Revoking a share blocks the shared-file page, but does not erase downloaded copies or immediately invalidate an earlier presigned download URL.
