package com.java04.servlet;

import com.java04.dao.UserDAO;
import com.java04.dao.UserDAOImpl;
import com.java04.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

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

        if (user != null) {
            // Kiểm tra nếu là user Google (không cần password)
            if ("GOOGLE_OAUTH_USER".equals(user.getPassword())) {
                request.setAttribute("message", "Tài khoản này được đăng ký bằng Google. Vui lòng đăng nhập bằng Google.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }
            
            // Kiểm tra password cho user thường
            if (user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                if (user.getAdmin()) {
                    // ✅ Admin → forward đến đúng vị trí file thật sự
                    response.sendRedirect("user-management");
                } else {
                    // ✅ User thường → redirect tới servlet /video
                    response.sendRedirect("home");
                }
            } else {
                request.setAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
