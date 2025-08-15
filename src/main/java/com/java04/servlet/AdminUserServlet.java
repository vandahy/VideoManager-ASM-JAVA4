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

@WebServlet(urlPatterns = {"/user-management", 
        "/edit-user", // GET: load form, POST: update
        "/delete-user"})
public class AdminUserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        if (uri.endsWith("/delete-user")) {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                userDAO.deleteById(id);
            }
            response.sendRedirect(request.getContextPath() + "/user-management");
            return;
        }

        if (uri.endsWith("/edit-user")) {
            String id = request.getParameter("id");
            if (id == null || id.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/user-management");
                return;
            }
            User editUser = userDAO.findById(id);
            request.setAttribute("editUser", editUser);
            request.getRequestDispatcher("/WEB-INF/views/user-edit.jsp").forward(request, response);
            return;
        }

        // Xử lý thông báo từ session (sau redirect)
        String error = (String) request.getSession().getAttribute("error");
        String message = (String) request.getSession().getAttribute("message");
        if (error != null) {
            request.setAttribute("error", error);
            request.getSession().removeAttribute("error");
        }
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        
        List<User> users = userDAO.findAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        if (uri.endsWith("/edit-user")) {
            String id = request.getParameter("id");
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");
            
            // Validation: kiểm tra các trường bắt buộc
            if (id == null || id.trim().isEmpty()) {
                request.getSession().setAttribute("error", "ID không được để trống");
                response.sendRedirect(request.getContextPath() + "/user-management");
                return;
            }
            
            if (email == null || email.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Email không được để trống");
                response.sendRedirect(request.getContextPath() + "/user-management");
                return;
            }
            
            if (fullname == null || fullname.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Họ và tên không được để trống");
                response.sendRedirect(request.getContextPath() + "/user-management");
                return;
            }
            
            // Nếu validation pass, cập nhật user
            User existing = userDAO.findById(id);
            if (existing != null) {
                existing.setEmail(email.trim());
                existing.setFullname(fullname.trim());
                existing.setAdmin(request.getParameter("admin") != null);
                // giữ nguyên password
                userDAO.update(existing);
                request.getSession().setAttribute("message", "Cập nhật người dùng thành công!");
            }
            response.sendRedirect(request.getContextPath() + "/user-management");
            return;
        }

        doGet(request, response);
    }
}
