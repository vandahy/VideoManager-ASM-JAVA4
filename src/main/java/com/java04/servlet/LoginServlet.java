package com.java04.servlet;

import com.java04.dao.UserDAO;
import com.java04.dao.UserDAOImpl;
import com.java04.dao.VideoDAO;
import com.java04.dao.VideoDAOImpl;
import com.java04.entity.User;
import com.java04.entity.Video;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.findByIdOrEmail(username);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if (user.getAdmin()) {
                // ✅ Admin → forward đến đúng vị trí file thật sự
                response.sendRedirect("user-management");
            } else {
                // ✅ User thường → redirect tới servlet /video
                response.sendRedirect("video");
            }


        } else {
            // ❌ Sai tài khoản hoặc mật khẩu
            request.setAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
