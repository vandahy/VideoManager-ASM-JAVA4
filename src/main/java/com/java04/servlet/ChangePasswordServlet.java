package com.java04.servlet;

import com.java04.dao.UserDAO;
import com.java04.dao.UserDAOImpl;
import com.java04.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị form đổi mật khẩu
        request.getRequestDispatcher("/WEB-INF/views/changepassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // Lấy dữ liệu từ form
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        String message;

        // Kiểm tra mật khẩu hiện tại
        if (!currentUser.getPassword().equals(currentPassword)) {
            message = "❌ Mật khẩu hiện tại không đúng.";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/WEB-INF/views/changepassword.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            message = "❌ Mật khẩu mới và xác nhận không khớp.";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/WEB-INF/views/changepassword.jsp").forward(req, resp);
            return;
        }

        // ✅ Thành công: cập nhật, xoá session, redirect
        currentUser.setPassword(newPassword);
        userDAO.update(currentUser);
        session.invalidate(); // Đăng xuất người dùng

        // ✅ Chuyển hướng về trang login
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}