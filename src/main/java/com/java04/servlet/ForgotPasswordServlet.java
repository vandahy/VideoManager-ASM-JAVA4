package com.java04.servlet;

import com.java04.dao.UserDAO;
import com.java04.dao.UserDAOImpl;
import com.java04.entity.User;
import com.java04.utils.EmailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");

        System.out.println("Username: " + username);
        System.out.println("Email: " + email);

        User user = userDAO.findByIdOrEmail(username);
        if (user == null || !user.getEmail().equals(email)) {
            req.getSession().setAttribute("error", "Thông tin không hợp lệ");
            resp.sendRedirect("login");
            return;
        }

        String subject = "Your password";
        String content = "Mật khẩu của bạn là: " + user.getPassword();

        try {
            EmailUtils.sendEmail(email, subject, content);
            req.getSession().setAttribute("message", "Mật khẩu đã được gửi đến email của bạn");
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Không thể gửi email: " + e.getMessage());
        }

        // Quay lại trang login và hiển thị thông báo
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
