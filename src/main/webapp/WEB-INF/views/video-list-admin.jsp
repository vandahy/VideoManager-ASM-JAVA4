<%-- Cấu hình trang JSP: thiết lập Content-Type là HTML UTF-8, sử dụng ngôn ngữ Java và bật EL (Expression Language) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%-- Khai báo sử dụng JSTL core (c:forEach, c:if, ...) --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- Bắt đầu tài liệu HTML -->
<html>
<head>
    <!-- Tiêu đề trang -->
    <title>Video Manager</title>
    <!-- Nạp CSS Bootstrap từ CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- Thanh điều hướng (Navbar) dùng lại giống trang user-management -->
<nav class="navbar navbar-expand-lg navbar-warning bg-warning">
    <div class="container-fluid">
        <!-- Logo/tiêu đề khu vực Admin -->
        <a class="navbar-brand fw-bold text-dark" href="#">👑 Admin Dashboard</a>
        <ul class="navbar-nav me-auto">
            <li class="nav-item">
                <!-- Link tới trang quản lý người dùng, dùng contextPath để không phụ thuộc cấu hình app -->
                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/home">Trang chủ</a>
            </li>
            <li class="nav-item">
                <!-- Link tới trang quản lý người dùng, dùng contextPath để không phụ thuộc cấu hình app -->
                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/user-management">Quản lý người dùng</a>
            </li>
            <li class="nav-item">
                <!-- Link tới trang quản lý video -->
                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/admin/video">Quản lý video</a>
            </li>
            <li class="nav-item">
                <!-- Link tới trang thống kê -->
                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/admin/report">Thống kê</a>
            </li>
        </ul>
        <span class="navbar-text text-dark">
            <!-- Hiển thị tên người dùng từ session và link đăng xuất -->
            Xin chào, ${sessionScope.user.fullname} |
            <a href="${pageContext.request.contextPath}/logout" class="text-dark fw-bold">Đăng xuất</a>
        </span>
    </div>
  </nav>
<!-- Nội dung chính của trang -->
<div class="container mt-4">
    <h3 class="text-center text-uppercase">Quản lý video</h3>
    <hr>

    <!-- Tabs: chuyển đổi giữa form chỉnh sửa và danh sách -->
    <ul class="nav nav-tabs" id="videoTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="edit-tab" data-bs-toggle="tab" data-bs-target="#edit" type="button"
                    role="tab">Video Edition
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="list-tab" data-bs-toggle="tab" data-bs-target="#list" type="button" role="tab">
                Video List
            </button>
        </li>
    </ul>

    <!-- Nội dung của từng tab -->
    <div class="tab-content" id="videoTabContent">
        <!-- Tab Form: tạo/cập nhật video -->
        <div class="tab-pane fade show active" id="edit" role="tabpanel">
            <div class="row mt-3">
                <!-- Cột hiển thị Poster -->
                <div class="col-md-4 text-center">
                    <div class="border bg-secondary-subtle mb-3" style="height: 180px;">
                        <!-- Hiển thị ảnh poster dựa vào giá trị trong form -->
                        <img src="${form.poster}" class="img-fluid h-100" alt="Poster">
                    </div>
                </div>

                <!-- Cột Form nhập liệu -->
                <div class="col-md-8">
                    <!-- Form gửi dữ liệu qua phương thức POST -->
                    <form method="post">
                        <div class="mb-3">
                            <label for="id" class="form-label">ID</label>
                            <!-- Trường ID: khi đã có ID (chỉnh sửa) thì readonly -->
                            <input type="text" name="id" id="id" class="form-control" value="${form.id}"
                                   <c:if test="${form.id != null}">readonly</c:if>>

                        </div>

                        <div class="mb-3">
                            <label for="title" class="form-label">Title</label>
                            <!-- Tiêu đề video -->
                            <input type="text" name="title" id="title" class="form-control" value="${form.title}">
                        </div>

                        <div class="mb-3">
                            <label for="links" class="form-label">YouTube Link</label>
                            <!-- Liên kết YouTube của video -->
                            <input type="text" name="links" id="links" class="form-control" value="${form.links}">
                        </div>

                        <div class="mb-3">
                            <label for="views" class="form-label">Views</label>
                            <!-- Số lượt xem -->
                            <input type="number" name="views" id="views" class="form-control" value="${form.views}">
                        </div>

                        <div class="mb-3">
                            <label for="poster" class="form-label">Poster URL</label>
                            <!-- URL ảnh poster -->
                            <input type="text" name="poster" id="poster" class="form-control" value="${form.poster}">
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <!-- Mô tả video -->
                            <textarea name="description" id="description"
                                      class="form-control">${form.description}</textarea>
                        </div>

                        <div class="form-check mb-3">
                            <!-- Tình trạng kích hoạt -->
                            <input class="form-check-input" type="checkbox" name="active"
                                   id="active" ${form.active ? "checked" : ""}>
                            <label class="form-check-label" for="active">Active</label>
                        </div>

                        <!-- Hiển thị thông báo lỗi/thành công nếu có -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                            <script>
                                (function(){
                                    try {
                                        var id = document.getElementById('id');
                                        var title = document.getElementById('title');
                                        var links = document.getElementById('links');
                                        if (id && !id.value) id.classList.add('is-invalid');
                                        if (title && !title.value) title.classList.add('is-invalid');
                                        if (links && !links.value) links.classList.add('is-invalid');
                                    } catch(e) {}
                                })();
                            </script>
                        </c:if>
                        <c:if test="${not empty message}">
                            <div class="alert alert-success">${message}</div>
                        </c:if>

                        <!--  Các nút thao tác: xác định endpoint bằng thuộc tính formaction -->
                        <div class="mb-3">
                            <button class="btn btn-primary"
                                    formaction="${pageContext.request.contextPath}/admin/video/create">Create
                            </button>

                            <button class="btn btn-success"
                                    formaction="${pageContext.request.contextPath}/admin/video/update/${form.id}">Update
                            </button>

                            <a href="${pageContext.request.contextPath}/admin/video/delete/${form.id}"
                               class="btn btn-danger">Delete</a>
                            <a href="${pageContext.request.contextPath}/admin/video/reset"
                               class="btn btn-warning">Reset</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>

        <!-- Tab Danh sách Video -->
        <div class="tab-pane fade" id="list" role="tabpanel">
            <table class="table table-bordered table-striped table-hover mt-3">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Link</th>
                    <th>Poster</th>
                    <th>Views</th>
                    <th>Description</th>
                    <th>Active</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <%-- Lặp qua danh sách video để hiển thị từng dòng --%>
                <c:forEach var="video" items="${videos}">
                    <tr>
                        <td>${video.id}</td>
                        <td>${video.title}</td>
                        <!-- Liên kết tới video ở tab mới -->
                        <td><a href="${video.links}" target="_blank">${video.links}</a></td>
                        <td><img src="${video.poster}" style="height: 50px;" alt="Poster"></td>
                        <td>${video.views}</td>
                        <td>${video.description}</td>
                        <td>${video.active ? "Yes" : "No"}</td>
                        <td>
                            <!-- Link chuyển sang trang chỉnh sửa video theo ID -->
                            <a href="${pageContext.request.contextPath}/admin/video/edit/${video.id}"
                               class="btn btn-sm btn-warning">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Nạp JS Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Router phía client: chặn click navbar để load phần nội dung chính bằng fetch, tránh reload toàn trang
    (function () {
        // CSS selector dùng để xác định khối nội dung sẽ được thay thế
        const containerSelector = '.container.mt-4';

        function loadPage(url, pushState) {
            // Gọi tới URL đích, thêm header để biết đây là yêu cầu AJAX
            fetch(url, {headers: {'X-Requested-With': 'XMLHttpRequest'}})
                .then(function (res) { return res.text(); }) // Lấy HTML trả về dạng text
                .then(function (html) {
                    var parser = new DOMParser(); // Tạo parser để parse HTML string
                    var doc = parser.parseFromString(html, 'text/html'); // Chuyển HTML string thành Document
                    var newContainer = doc.querySelector(containerSelector); // Lấy khối nội dung từ trang mới
                    var currentContainer = document.querySelector(containerSelector); // Khối nội dung hiện tại
                    if (newContainer && currentContainer) {
                        currentContainer.innerHTML = newContainer.innerHTML; // Thay thế nội dung
                        if (pushState) {
                            history.pushState({url: url}, '', url); // Cập nhật URL lịch sử nếu là điều hướng chủ động
                        }
                        window.scrollTo(0, 0); // Cuộn lên đầu trang
                    } else if (pushState) {
                        // Dự phòng: nếu không tìm thấy cấu trúc mong đợi, chuyển trang full
                        window.location.href = url;
                    }
                })
                .catch(function () { window.location.href = url; }); // Dự phòng khi có lỗi mạng
        }

        function handleNavbarClick(event) {
            var anchor = event.currentTarget; // Thẻ <a> được click
            var url = anchor.getAttribute('href'); // URL điều hướng
            if (!url || url.startsWith('http')) { return; } // Bỏ qua URL ngoài domain
            event.preventDefault(); // Ngăn trình duyệt chuyển trang mặc định
            // Cho phép logout vẫn chuyển trang đầy đủ (vì cần xử lý session server-side)
            if (url.indexOf('/logout') !== -1) {
                window.location.href = url;
                return;
            }
            loadPage(url, true); // Tải nội dung và cập nhật lịch sử
        }

        // Gán handler click cho các link trong navbar
        document.querySelectorAll('.navbar a.nav-link').forEach(function (a) {
            a.addEventListener('click', handleNavbarClick);
        });

        window.addEventListener('popstate', function (e) {
            // Khi người dùng bấm Back/Forward, tải lại nội dung tương ứng từ lịch sử
            if (e.state && e.state.url) {
                loadPage(e.state.url, false);
            }
        });
    })();
</script>
</body>
</html>
