package com.java04.servlet;

import com.java04.dao.UserDAO;
import com.java04.dao.UserDAOImpl;
import com.java04.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user-management")
public class AdminUserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userDAO.findAll();  // bạn cần có hàm findAll()
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);  // nếu bạn chỉ cần hiển thị lại trang
    }
}
