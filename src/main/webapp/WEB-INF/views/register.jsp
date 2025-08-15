<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light d-flex justify-content-center align-items-center" style="min-height: 100vh;">

<div class="card shadow-lg p-4" style="width: 100%; max-width: 400px;">
    <h3 class="text-center mb-4 text-primary">Đăng Ký</h3>

    <form method="post" action="logup">
        <div class="mb-3">
            <label for="fullname" class="form-label">Họ và tên:</label>
            <input type="text" class="form-control" id="fullname" name="fullname" required>
        </div>

        <div class="mb-3">
            <label for="username" class="form-label">Email:</label>
            <input type="email" class="form-control" id="username" name="username" required>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Mật khẩu:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-primary">Đăng ký</button>
        </div>
    </form>

    <c:if test="${not empty sessionScope.message}">
        <div class="mt-3 alert alert-success text-center p-2">${sessionScope.message}</div>
    </c:if>
    <c:remove var="message" scope="session" />

    <div class="mt-3 text-center">
        <span>Bạn đã có tài khoản? <a href="login">Đăng nhập ngay</a></span>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>