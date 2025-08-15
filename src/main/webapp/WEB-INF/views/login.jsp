<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex justify-content-center align-items-center" style="min-height: 100vh;">
<c:if test="${not empty message}">
    <script>
        alert("${message}");
    </script>
    <c:remove var="message"/>
</c:if>
<c:if test="${not empty error}">
    <script>
        alert("${error}");
    </script>
    <c:remove var="error"/>
</c:if>

<div class="card shadow-lg p-4" style="width: 100%; max-width: 400px;">
    <h3 class="text-center mb-4 text-primary">Đăng nhập</h3>
    <form method="post" action="login">
        <div class="mb-3">
            <label for="username" class="form-label">Tên đăng nhập hoặc Email:</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Mật khẩu:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-primary">Đăng nhập</button>
        </div>
    </form>

    <c:if test="${not empty message}">
        <div class="mt-3 alert alert-danger text-center p-2">${message}</div>
    </c:if>

    <div class="mt-3 text-center">
        <span>Chưa có tài khoản? <a href="logup">Đăng ký ngay</a></span>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

