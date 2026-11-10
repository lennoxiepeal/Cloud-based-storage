# Customer demo script — Vietnamese narration, English screen directions

**Target duration: 4:45. Maximum: 5:00.** Read only the Vietnamese text in quotation blocks. Perform the English actions while speaking. Timestamps include clicking, loading, and short pauses; do not read the timestamps or instructions aloud. The customer is evaluating how Cloud Storage helps organize and share project files.

## Prepare before recording — outside the 4:45

1. Run `./scripts/start-local.sh` and wait for **Ready**. Do not record installation or startup. The app was previously stopped; this guide does not start it for you.
2. Copy the **three files inside `demo-files/customer-demo/`** to a convenient Windows folder, such as `Desktop\CloudStorageDemo`. Use these files, not the older assignment sample files. All customer names and amounts are fictional.
3. Prepare three separate browser sessions. Keep their window positions consistent and rehearse switching through the taskbar:

   | Window | Session and initial screen | Use |
   |---|---|---|
   | A | Chrome normal window at `http://localhost:8080/login`; logged out | User/customer workflow |
   | B | Edge normal window, already signed in as `admin` / `admin123` | Admin panel |
   | C | Chrome Incognito window, blank tab, never signed in | External recipient |

   **Do not sign the admin into Chrome Incognito.** Incognito windows in the same browser share one session; the guest window must remain unauthenticated.
4. In B, locate the **demo** row. Set its quota to **1 GB**, click **Update**, and dismiss the success alert. Ensure **Create**, **Write**, and **Read** are checked; click **Save Permissions**, dismiss the alert, and wait for the form to finish reloading. Leave B open on the admin panel.
5. In A, use `demo` / `demo123` during the recording. Before a retake, remove only your previous sample folder **Khách hàng An Phát** using its Delete dialog and **delete contents** option, then log out. This prevents duplicate-folder errors. Keep other folders and real files.
6. Allow pop-ups for `http://localhost:8080` in A and C. A text-file download may open in a new tab; a ZIP may download directly. Know how to return to the app tab. Test the file picker once so it opens near `Desktop\CloudStorageDemo`.
7. Record the desktop so switching A/B/C is captured. Use 1080p, readable browser zoom, notifications off, and a second screen or phone for this script. Keep passwords and the terminal out of the sales narrative.
8. Rehearse once with a timer. Leave 1–2 seconds after a visible result. If an upload or request takes longer, pause speaking and trim that wait in editing. Do not add the optional features from the longer assignment walkthrough.

## 0:00–0:20 — Start with the customer's problem

**On screen:** Show A's login page. Briefly point at **Sign Up** without opening it. Enter `demo` / `demo123`, click **Sign In**, and wait for the dashboard.

> “Tài liệu dự án nằm rải rác khiến đội ngũ mất thời gian tìm kiếm và gửi lại cho khách hàng. Với Cloud Storage, bạn có thể tập trung tài liệu, sắp xếp theo dự án và chủ động chia sẻ khi cần. Hãy cùng xem quy trình này với một khách hàng mẫu.”

**Checkpoint:** A shows the user dashboard, not the admin panel.

## 0:20–0:50 — Organize around a real customer

**On screen, in A:**

1. Click **New Folder**, enter `Khách hàng An Phát`, click **Create**.
2. Single-click that folder's name to enter it.
3. Click **New Folder**, enter `Hợp đồng`, click **Create**.
4. Point to the breadcrumb and the new child folder. Stay in **Khách hàng An Phát**; do not enter **Hợp đồng** yet.

> “Ví dụ, đội ngũ của bạn đang chuẩn bị hồ sơ cho khách hàng An Phát. Tôi tạo một thư mục riêng cho khách hàng, rồi thêm thư mục Hợp đồng bên trong. Cấu trúc này giúp phân loại tài liệu theo công việc; thanh điều hướng phía trên cho biết chúng ta đang ở đâu.”

**Checkpoint:** Breadcrumb ends in **Khách hàng An Phát**; one child folder, **Hợp đồng**, is visible.

## 0:50–1:20 — Upload once, then retrieve easily

**On screen, in A:**

1. Click **Choose Files**. Select all three files from `Desktop\CloudStorageDemo` and confirm the file picker.
2. Click **Upload 3 File(s)**. Wait for the success alert, click **OK**, and let the table refresh.
3. Point at the names, sizes, dates, and storage meter.
4. On `de-xuat-hop-tac.txt`, click the downward-arrow **Download** icon. Show the text briefly if it opens in a new tab, then return to the app. If the browser saves it instead, briefly show the completed download.

> “Tôi đưa vào bản đề xuất cùng hai tệp nháp bằng một lần chọn. Sau khi tải lên, tên tệp, kích thước và ngày tải đều được hiển thị rõ ràng. Dung lượng sử dụng cũng được cập nhật. Khi cần kiểm tra nội dung, tôi có thể tải tài liệu xuống ngay từ danh sách.”

**Checkpoint:** Three files are listed; you have returned to A's app tab.

## 1:20–1:55 — Find the right file and put it in the right place

**On screen, in A:**

1. On `de-xuat-hop-tac.txt`, click the pencil **Rename** icon. Replace the name with `bao-gia-an-phat.txt`; click **Rename** inside the modal.
2. Search `bao-gia-an-phat` in **Search files…** and press Enter. One result should remain.
3. Click the **Advanced Search** icon beside the search button. Keep the filename query, choose **Text** under **File Type**, then click **Search**. Click **Clear Filters** to return to all three files.
4. On `bao-gia-an-phat.txt`, click its folder-shaped **Move** icon. Select **Hợp đồng**, then click **Move** in the modal. Stay in the customer folder.

> “Tôi đổi tên bản đề xuất để dễ nhận biết, rồi tìm lại ngay bằng từ khóa. Khi danh sách lớn hơn, bộ lọc giúp thu hẹp theo loại tệp và kích thước. Sau đó, tôi chuyển bản báo giá vào thư mục Hợp đồng để tài liệu chính thức có vị trí rõ ràng.”

**Checkpoint:** A still shows **Khách hàng An Phát**. Only the two draft files remain in its file table; the proposal is inside **Hợp đồng**.

## 1:55–2:45 — Share with a recipient, then revoke the link

**On screen:**

1. In A, enter **Hợp đồng**. On `bao-gia-an-phat.txt`, click the curved-arrow **Make Public** icon.
2. In the **Public Link** modal, click **Copy Link** and dismiss any copied confirmation. **Keep this modal open.**
3. Switch to C, paste the link in the address bar, and open it. Show the file name, size, and **Download File** button. Click **Download File**, then return to C's shared-file tab if a new tab opens.
4. Switch to A. In the still-open share modal, click **Make Private** and confirm **OK**.
5. Switch to C and reload the original shared-file page. Pause on **File not found or link has been revoked**.

**Read while generating and opening the link:**

> “Để gửi báo giá, tôi tạo một liên kết chia sẻ và mở nó trong một cửa sổ chưa đăng nhập, đóng vai người nhận. Khách hàng có thể xem thông tin và tải tài liệu mà không cần tạo tài khoản. Đây là cách trao đổi thuận tiện khi hai bên cần cùng xem một tài liệu.”

**Read while revoking and reloading:**

> “Khi không cần chia sẻ nữa, tôi chuyển tệp về riêng tư. Tải lại trang, liên kết chia sẻ đã bị thu hồi. Người gửi có thể chủ động quyết định khi nào đường dẫn này còn được sử dụng.”

**Checkpoint:** C displays the revoked-link message. Do not refresh the already-open raw download tab; revocation is demonstrated on the shared-file page.

## 2:45–3:30 — Handle a batch of files with fewer repeated clicks

**On screen, in A:**

1. Switch back to A. Click the **Khách hàng An Phát** breadcrumb to return to the parent folder.
2. Check both rows: `ngan-sach-nhap.csv` and `ghi-chu-nhap.txt`.
3. Click **Download ZIP (2)**, dismiss the download-started alert, and show the completed ZIP briefly. If convenient, open the ZIP to show its two filenames, then return to A. Do not spend time opening each file.
4. With both rows still selected, click the toolbar **Move** button. Choose **Hợp đồng**, click **Move Here**, and enter **Hợp đồng** after the table refreshes.
5. Check **only the two draft rows** again. Leave `bao-gia-an-phat.txt` unchecked. Click **Delete (2)** and confirm **OK**. Point at the surviving proposal and storage meter.

**Read while downloading and moving:**

> “Với nhiều tài liệu, bạn không cần xử lý từng tệp một. Tôi chọn hai bản nháp, tải cùng lúc thành một tệp ZIP, rồi chuyển cả nhóm sang thư mục cần dùng. Những thao tác lặp lại được gói gọn trong vài lần chọn.”

**Read while deleting:**

> “Khi hai bản nháp không còn cần thiết, tôi chọn và xóa chúng cùng lúc. Bản báo giá được giữ lại, còn dung lượng lưu trữ được cập nhật theo thay đổi.”

**Checkpoint:** Only `bao-gia-an-phat.txt` remains in **Hợp đồng**. File deletion is permanent; use only the supplied sample drafts.

## 3:30–4:25 — Show the administrator's control

**On screen:**

1. Switch to B. Click **Refresh**. Point at **Total Users**, **Storage Used**, **Total Quota**, and **Top Users by Storage**. Scroll just enough to show the **demo** row.
2. On the **demo** row, change **Quota (GB)** to `2`, click that row's **Update**, dismiss the success alert, and wait for the form to reload. Point briefly at **Global Storage Quota / Apply to All**, without applying it.
3. On the **demo** row, uncheck **Read**, click **Save Permissions**, dismiss the success alert, and wait for the reload. Leave **Create** and **Write** checked.
4. Switch to A. **Reload the page**, which returns you to the root folder. Enter **Khách hàng An Phát**, then **Hợp đồng**. Show the updated **2 GB** quota in the header. Click the proposal's **Download** icon. Show the failed-download alert and dismiss it; do not click Download a second time.
5. Switch to B. Recheck **Read** on the **demo** row, click **Save Permissions**, dismiss the success alert, and wait for the reload.

**Read while showing the dashboard and quota:**

> “Về phía quản trị, bạn có thể theo dõi số lượng tài khoản, dung lượng đã dùng và những người sử dụng nhiều nhất. Từ đây, tôi tăng hạn mức của tài khoản này lên hai gigabyte. Hệ thống cũng có lựa chọn điều chỉnh hạn mức cho toàn bộ người dùng.”

**Read while disabling Read and demonstrating the blocked download:**

> “Quản trị viên còn có thể điều chỉnh quyền tạo, ghi và tải xuống. Tôi tạm tắt quyền tải xuống của tài khoản đang dùng. Quay lại màn hình người dùng, yêu cầu tải tài liệu mới bị từ chối. Quyền hạn được kiểm tra khi thực hiện thao tác.”

**Read while restoring Read:**

> “Tôi bật lại quyền đọc để người dùng tiếp tục làm việc.”

**Checkpoint:** Read is restored and saved in B. A is still in **Hợp đồng**, with the proposal visible.

## 4:25–4:45 — Close on the customer's outcome

**On screen:** Switch to A. Keep the proposal and the breadcrumb visible. Move the cursor away from the content and hold the screen for the last two seconds. Do not log out during the closing line.

> “Từ sắp xếp hồ sơ, tìm tài liệu, chia sẻ đến quản lý dung lượng và quyền truy cập, Cloud Storage giúp đội ngũ làm việc trên một quy trình rõ ràng hơn. Bạn có thể bắt đầu với một dự án mẫu như thế này, rồi đánh giá cách tổ chức phù hợp với đội ngũ của mình.”

## After recording — do not read aloud

- In B, restore the demo quota to **1 GB** if desired. Ensure Create, Write, and Read are all enabled and saved.
- Run `./scripts/stop-local.sh` when finished. The uploaded proposal and folder metadata remain on disk.
- Aim for **4:45**. If you are behind schedule, skip opening the raw text/ZIP previews; keep the completed-download indicator, link revocation, and permission-denial proof. Cut loading waits rather than speaking faster. Do not add registration, incorrect-password tests, pagination, cloud monitoring, or a terminal walkthrough to this short sales demo.
- This is a **local trial environment**. The script deliberately makes no claim that this deployment is publicly hosted, production certified, backed up to AWS/IBM, or integrated with CloudWatch. Do not call the public link Internet-accessible in this setup.
- Revoking a share blocks new access through its shared-file page. It does not erase downloaded copies or instantly invalidate a previously issued presigned download URL. Turning off Read blocks new download requests; it does not recall earlier downloads either.
- The recording shows nested folders, authentication, multiple uploads, single downloads, metadata/storage usage, rename, filename/type search, single and bulk move, sharing and revocation, ZIP download, bulk deletion, admin statistics, per-user quota updates, and an enforced permission change. Registration, folder rename/delete, pagination, size-filter input, quota-exceeded rejection, and global quota application are outside this five-minute cut.
