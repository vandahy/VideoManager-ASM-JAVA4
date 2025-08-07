<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Thống kê chia sẻ</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
  <h2 class="text-center mb-4">📊 Thống kê chia sẻ video</h2>

  <table class="table table-bordered table-striped">
    <thead class="table-dark">
    <tr>
      <th>Tiêu đề</th>
      <th>Số lượt chia sẻ</th>
      <th>Ngày đầu</th>
      <th>Ngày cuối</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${shareSummary}">
      <tr>
        <td>${item.title}</td>
        <td>${item.totalShares}</td>
        <td>${item.firstShareDate}</td>
        <td>${item.lastShareDate}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
