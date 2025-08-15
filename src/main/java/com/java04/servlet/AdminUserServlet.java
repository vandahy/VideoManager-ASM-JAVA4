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

/**
 * Servlet quản lý người dùng cho Admin
 * Xử lý các chức năng: hiển thị danh sách, chỉnh sửa, xóa người dùng
 */
@WebServlet(urlPatterns = {"/user-management", 
        "/edit-user", // GET: load form, POST: update
        "/delete-user"})
public class AdminUserServlet extends HttpServlet {
    // Khởi tạo DAO để tương tác với database
    private UserDAO userDAO = new UserDAOImpl();

    /**
     * Xử lý các request GET: hiển thị danh sách, xóa user, load form edit
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập encoding UTF-8 để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Lấy URI để xác định action cần thực hiện
        String uri = request.getRequestURI();
        
        // Xử lý xóa user
        if (uri.endsWith("/delete-user")) {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                userDAO.deleteById(id); // Xóa user theo ID
            }
            response.sendRedirect(request.getContextPath() + "/user-management");
            return;
        }

        // Xử lý load form edit user (không dùng nữa vì đã chuyển sang modal)
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

        // Xử lý thông báo từ session (sau redirect từ POST)
        // Lấy thông báo lỗi/thành công từ session và chuyển sang request attribute
        String error = (String) request.getSession().getAttribute("error");
        String message = (String) request.getSession().getAttribute("message");
        if (error != null) {
            request.setAttribute("error", error);
            request.getSession().removeAttribute("error"); // Xóa khỏi session sau khi đã lấy
        }
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message"); // Xóa khỏi session sau khi đã lấy
        }
        
        // Lấy danh sách tất cả user và chuyển đến JSP
        List<User> users = userDAO.findAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user-management.jsp").forward(request, response);
    }

    /**
     * Xử lý các request POST: cập nhật thông tin user
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập encoding UTF-8 để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Lấy URI để xác định action cần thực hiện
        String uri = request.getRequestURI();
        
        // Xử lý cập nhật thông tin user
        if (uri.endsWith("/edit-user")) {
            // Lấy dữ liệu từ form
            String id = request.getParameter("id");
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");
            
            // ===== VALIDATION SERVER-SIDE =====
            // Kiểm tra ID không được để trống
            if (id == null || id.trim().isEmpty()) {
                request.getSession().setAttribute("error", "ID không được để trống");
                response.sendRedirect(request.getContextPath() + "/user-management");
                return;
            }
            
            // Kiểm tra Email không được để trống
            if (email == null || email.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Email không được để trống");
                response.sendRedirect(request.getContextPath() + "/user-management");
                return;
            }
            
            // Kiểm tra Họ và tên không được để trống
            if (fullname == null || fullname.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Họ và tên không được để trống");
                response.sendRedirect(request.getContextPath() + "/user-management");
                return;
            }
            
            // ===== CẬP NHẬT USER NẾU VALIDATION PASS =====
            User existing = userDAO.findById(id);
            if (existing != null) {
                try {
                    // Cập nhật thông tin mới (loại bỏ khoảng trắng thừa)
                    existing.setEmail(email.trim());
                    existing.setFullname(fullname.trim());
                    // Kiểm tra checkbox admin có được chọn không
                    existing.setAdmin(request.getParameter("admin") != null);
                    // Giữ nguyên password (không thay đổi)
                    userDAO.update(existing);
                    
                    // Lưu thông báo thành công vào session
                    request.getSession().setAttribute("message", "Cập nhật người dùng thành công!");
                    
                } catch (javax.persistence.PersistenceException e) {
                    // Xử lý lỗi unique constraint violation
                    if (e.getCause() != null && e.getCause().getMessage().contains("duplicate key")) {
                        request.getSession().setAttribute("error", "Email này đã được sử dụng bởi người dùng khác. Vui lòng chọn email khác.");
                    } else {
                        request.getSession().setAttribute("error", "Có lỗi xảy ra khi cập nhật. Vui lòng thử lại.");
                    }
                } catch (Exception e) {
                    request.getSession().setAttribute("error", "Có lỗi xảy ra khi cập nhật. Vui lòng thử lại.");
                }
            }
            
            // Redirect về trang user-management để hiển thị thông báo
            response.sendRedirect(request.getContextPath() + "/user-management");
            return;
        }

        // Nếu không phải edit-user, chuyển về doGet
        doGet(request, response);
    }
}
