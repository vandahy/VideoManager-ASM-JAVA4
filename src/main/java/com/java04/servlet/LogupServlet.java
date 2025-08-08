package com.java04.servlet;

import com.java04.dao.UserDAO;
import com.java04.dao.UserDAOImpl;
import com.java04.entity.User;
import com.java04.utils.EmailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logup")
public class LogupServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String fullname = req.getParameter("fullname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validate dữ liệu
        if (fullname == null || fullname.isEmpty() ||
                username == null || username.isEmpty() ||
                password == null || password.isEmpty()) {

            req.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra username đã tồn tại chưa
        if (userDAO.findByIdOrEmail(username) != null) {
            req.setAttribute("error", "Tên đăng nhập đã tồn tại.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        // Tạo và lưu user mới
        String newId = userDAO.generateNewUserId();
        User user = new User();
        System.out.println(newId);
        user.setId(newId);
        user.setFullname(fullname);
        user.setEmail(username);
        user.setPassword(password);
        user.setAdmin(false);

        userDAO.create(user);
        try {
            EmailUtils.sendEmail(username, "Đăng ký thành công", "Bạn đã đăng ký thành công Online Entertainment");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi để biết lý do
        }
        // Gửi thông báo thành công và chuyển hướng
        req.getSession().setAttribute("message", "Đăng ký thành công. Vui lòng đăng nhập.");
        resp.sendRedirect("login");
    }
}
