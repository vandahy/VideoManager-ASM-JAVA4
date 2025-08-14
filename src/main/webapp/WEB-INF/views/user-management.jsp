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
                <a class="nav-link text-dark" href="user-management">Quản lý người dùng</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-dark" href="admin/video">Quản lý video</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-dark" href="admin/report">Thống kê</a>
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
                    <a href="edit-user?id=${u.id}" class="btn btn-sm btn-warning">Sửa</a>
                    <a href="delete-user?id=${u.id}" class="btn btn-sm btn-danger" onclick="return confirm('Xoá người dùng này?')">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</form>
</body>
</html>
