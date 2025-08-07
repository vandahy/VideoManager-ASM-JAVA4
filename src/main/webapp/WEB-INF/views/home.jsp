<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.java04.entity.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="home">Trang chủ</a>

        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <%-- Chỉ hiện mục Thống kê nếu là Admin --%>
                <%
                    if (user != null && user.getAdmin() != null && user.getAdmin()) {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="summary">Thống kê</a>
                </li>
                <%
                    }
                %>

                <li class="nav-item">
                    <a class="nav-link" href="login">Đăng nhập</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="text-center my-3">
    <h2>Chào mừng, <%= user.getFullname() %>!</h2>
    <%
        if (user.getAdmin()) {
    %>
    <p style="color: red;">Bạn là Admin</p>
    <%
    } else {
    %>
    <p style="color: green;">Bạn là Khách hàng</p>
    <%
        }
    %>
</div>

<div class="container mt-5">
    <h2 class="text-center mb-4">🎬 Tiểu phẩm nổi bật</h2>
    <div class="row">
        <c:forEach var="v" items="${videos}">
            <div class="col-md-4 mb-4">
                <div class="card shadow-sm">
                    <img src="${v.poster}" class="card-img-top" alt="${v.title}">
                    <div class="card-body">
                        <h5 class="card-title text-center">${v.title}</h5>
                        <div class="d-flex justify-content-around">
                            <form action="like" method="post">
                                <input type="hidden" name="videoId" value="${v.id}">
                                <button class="btn btn-outline-danger" type="submit">❤️ Like</button>
                            </form>
                            <form action="share" method="post">
                                <input type="hidden" name="videoId" value="${v.id}">
                                <button class="btn btn-outline-info" type="submit">📤 Share</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
