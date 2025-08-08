<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex justify-content-center align-items-center" style="min-height: 100vh;">

<div class="card p-4 shadow" style="width: 100%; max-width: 500px;">
    <h4 class="text-center bg-success text-white p-2 mb-3">EDIT PROFILE</h4>

    <form method="post" action="update-profile">
        <div class="mb-3">
            <label class="form-label">USERNAME?</label>
            <input type="text" class="form-control" name="username" value="${sessionScope.user.id}" readonly>
        </div>

        <div class="mb-3">
            <label class="form-label">PASSWORD?</label>
            <input type="password" class="form-control" name="password" value="${sessionScope.user.password}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">FULLNAME?</label>
            <input type="text" class="form-control" name="fullName" value="${sessionScope.user.fullname}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">EMAIL ADDRESS?</label>
            <input type="email" class="form-control" name="email" value="${sessionScope.user.email}" required>
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-warning">Update</button>
        </div>
    </form>

    <c:if test="${not empty message}">
        <div class="alert alert-success mt-3 text-center">${message}</div>
    </c:if>
</div>

</body>
</html>