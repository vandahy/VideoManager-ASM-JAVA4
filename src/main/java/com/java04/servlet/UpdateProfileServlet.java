package com.java04.servlet;

import com.java04.dao.UserDAO;
import com.java04.dao.UserDAOImpl;
import com.java04.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/update-profile")
public class UpdateProfileServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin từ form
        String fullName = req.getParameter("fullName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        user.setFullname(fullName);
        user.setPassword(password);
        user.setEmail(email);

        // Update
        UserDAO dao = new UserDAOImpl();
        dao.update(user);

        session.setAttribute("user", user);  // Cập nhật session

        // Chuyển hướng và gửi thông báo
        String message = "Cập nhật thành công!";
        resp.sendRedirect(req.getContextPath() + "/home?message=" + java.net.URLEncoder.encode(message, "UTF-8"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/edit-profile.jsp").forward(request, response);
    }
}