<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  session.invalidate(); // Xoá toàn bộ session (user đã đăng nhập)
  response.sendRedirect("login.jsp"); // Chuyển hướng về trang đăng nhập
%>
