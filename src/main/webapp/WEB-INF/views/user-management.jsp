<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.java04.entity.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || !user.getAdmin()) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<form method="post" action="user-management">
<!-- 🔶 NAVBAR MÀU VÀNG -->
<nav class="navbar navbar-expand-lg navbar-warning bg-warning">
    <div class="container-fluid">
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
            Xin chào, ${sessionScope.user.fullname} |
            <a href="logout" class="text-dark fw-bold">Đăng xuất</a>
        </span>
    </div>
</nav>

<!-- 🔽 NỘI DUNG CHÍNH -->
<div class="container mt-4">
    <!-- Hiển thị thông báo lỗi/thành công -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Lỗi:</strong> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Thành công:</strong> ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    
    <h3 class="text-primary mb-4">🧑‍💼 Danh sách người dùng</h3>

    <table class="table table-striped table-bordered">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Email</th>
            <th>Họ và tên</th>
            <th>Admin?</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td>${u.email}</td>
                <td>${u.fullname}</td>
                <td><c:if test="${u.admin}">✅</c:if><c:if test="${!u.admin}">❌</c:if></td>
                <td>
                    <button type="button"
                            class="btn btn-sm btn-warning"
                            data-bs-toggle="modal"
                            data-bs-target="#editUserModal"
                            data-id="${u.id}"
                            data-email="${u.email}"
                            data-fullname="${u.fullname}"
                            data-admin="${u.admin}">Sửa</button>
                    <a href="delete-user?id=${u.id}" class="btn btn-sm btn-danger" onclick="return confirm('Xoá người dùng này?')">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</form>
<!-- Modal Edit User -->
<div class="modal fade" id="editUserModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-warning">
                <h5 class="modal-title">Chỉnh sửa người dùng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/edit-user">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">ID</label>
                        <input type="text" name="id" id="edit-id" class="form-control" readonly>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Email <span class="text-danger">*</span></label>
                        <input type="email" name="email" id="edit-email" class="form-control" required>
                        <div class="invalid-feedback">Email không được để trống</div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Họ và tên <span class="text-danger">*</span></label>
                        <input type="text" name="fullname" id="edit-fullname" class="form-control" required>
                        <div class="invalid-feedback">Họ và tên không được để trống</div>
                    </div>
                    <div class="form-check mb-2">
                        <input class="form-check-input" type="checkbox" name="admin" id="edit-admin">
                        <label class="form-check-label" for="edit-admin">Admin</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Lưu</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                </div>
            </form>
        </div>
    </div>
    </div>
<!-- End Modal Edit User -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const editModal = document.getElementById('editUserModal');
    if (editModal) {
        editModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            if (!button) return;
            const id = button.getAttribute('data-id');
            const email = button.getAttribute('data-email');
            const fullname = button.getAttribute('data-fullname');
            const isAdmin = button.getAttribute('data-admin') === 'true';

            document.getElementById('edit-id').value = id || '';
            document.getElementById('edit-email').value = email || '';
            document.getElementById('edit-fullname').value = fullname || '';
            document.getElementById('edit-admin').checked = isAdmin;
            
            // Reset validation state khi mở modal
            resetValidation();
        });
    }
    
    // Validation client-side
    function resetValidation() {
        const emailInput = document.getElementById('edit-email');
        const fullnameInput = document.getElementById('edit-fullname');
        
        emailInput.classList.remove('is-invalid');
        fullnameInput.classList.remove('is-invalid');
    }
    
    // Xử lý form submit với validation
    document.querySelector('#editUserModal form').addEventListener('submit', function(e) {
        const emailInput = document.getElementById('edit-email');
        const fullnameInput = document.getElementById('edit-fullname');
        let hasError = false;
        
        // Reset validation
        resetValidation();
        
        // Kiểm tra email
        if (!emailInput.value.trim()) {
            emailInput.classList.add('is-invalid');
            hasError = true;
        }
        
        // Kiểm tra fullname
        if (!fullnameInput.value.trim()) {
            fullnameInput.classList.add('is-invalid');
            hasError = true;
        }
        
        // Nếu có lỗi, ngăn form submit
        if (hasError) {
            e.preventDefault();
            return false;
        }
    });
    
    // Real-time validation khi blur input
    document.getElementById('edit-email').addEventListener('blur', function() {
        if (!this.value.trim()) {
            this.classList.add('is-invalid');
        } else {
            this.classList.remove('is-invalid');
        }
    });
    
    document.getElementById('edit-fullname').addEventListener('blur', function() {
        if (!this.value.trim()) {
            this.classList.add('is-invalid');
        } else {
            this.classList.remove('is-invalid');
        }
    });
</script>
</body>
</html>
