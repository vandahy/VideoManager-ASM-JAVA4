<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.java04.entity.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    request.setAttribute("user", user); // Cho phép dùng ${user} trong EL
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách video</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">📺 Danh sách video</a>

            <div class="d-flex align-items-center ms-auto">
                <span class="text-white me-3">
                    Xin chào, <strong>${user.fullname}</strong> 👋
                </span>
                <a href="logout" class="btn btn-outline-light btn-sm">Đăng xuất</a>
            </div>
        </div>
    </nav>
<div class="container mt-5">
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="video" items="${videos}">
            <div class="col">
                <div class="card h-100 shadow-sm">
                    <img src="${video.poster}" class="card-img-top" alt="${video.title}">
                    <div class="card-body">
                        <h5 class="card-title">${video.title}</h5>
                        <p class="card-text">${video.description}</p>
                        <p class="text-muted">👁️ Lượt xem: ${video.views}</p>
                        <c:if test="${not empty video.links}">
                            <a href="${video.links}" class="btn btn-primary btn-sm" target="_blank">Xem video</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty videos}">
            <div class="alert alert-warning text-center">⚠️ Không có video nào để hiển thị.</div>
        </c:if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
