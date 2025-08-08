<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.java04.entity.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-box {
            border: 2px solid #e67e22;
            padding: 20px;
            border-radius: 10px;
        }
        .form-box h3 {
            background-color: #dff0d8;
            padding: 10px;
            font-weight: bold;
            color: #2c3e50;
            border-bottom: 2px solid #e67e22;
        }
        .btn-orange {
            background-color: #e67e22;
            color: white;
            font-weight: bold;
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5">
    <form action="change-password" method="post" class="form-box">
        <h3>Change Password</h3>

        <div class="row mb-3">
            <div class="col-md-6">
                <label>Username?</label>
                <input type="text" class="form-control" name="username" value="${user.id}" readonly>
            </div>
            <div class="col-md-6">
                <label>Current Password?</label>
                <input type="password" class="form-control" name="currentPassword" required>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label>New Password?</label>
                <input type="password" class="form-control" name="newPassword" required>
            </div>
            <div class="col-md-6">
                <label>Confirm New Password?</label>
                <input type="password" class="form-control" name="confirmPassword" required>
            </div>
        </div>

        <div class="text-end">
            <button type="submit" class="btn btn-orange">Change</button>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-info mt-3">${message}</div>
        </c:if>
    </form>
</div>
</body>
</html>