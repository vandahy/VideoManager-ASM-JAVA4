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
        
        // Kiểm tra nếu đây là đăng ký từ Google
        String googleParam = request.getParameter("google");
        if ("true".equals(googleParam)) {
            // Lấy thông tin Google từ session
            HttpSession session = request.getSession();
            String googleEmail = (String) session.getAttribute("google_email");
            String googleName = (String) session.getAttribute("google_name");
            
            if (googleEmail != null) {
                request.setAttribute("google_email", googleEmail);
                request.setAttribute("google_name", googleName);
                request.setAttribute("is_google_signup", true);
            }
        }
        
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String fullname = req.getParameter("fullname");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String isGoogleSignup = req.getParameter("is_google_signup");

        // Validate dữ liệu
        if (fullname == null || fullname.isEmpty() ||
                username == null || username.isEmpty()) {

            req.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        // Nếu không phải Google signup thì cần password
        if (!"true".equals(isGoogleSignup) && (password == null || password.isEmpty())) {
            req.setAttribute("error", "Vui lòng nhập mật khẩu.");
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
        
        // Nếu là Google signup thì không cần password
        if ("true".equals(isGoogleSignup)) {
            user.setPassword("GOOGLE_OAUTH_USER"); // Đánh dấu là user Google
        } else {
            user.setPassword(password);
        }
        
        user.setAdmin(false);

        try {
            userDAO.create(user);
            
            // Xóa thông tin Google khỏi session
            HttpSession session = req.getSession();
            session.removeAttribute("google_email");
            session.removeAttribute("google_name");
            session.removeAttribute("google_picture");
            
            try {
                EmailUtils.sendEmail(username, "Đăng ký thành công", "Bạn đã đăng ký thành công Online Entertainment");
            } catch (Exception e) {
                e.printStackTrace(); // In lỗi để biết lý do
            }
            
            // Gửi thông báo thành công và chuyển hướng
            req.getSession().setAttribute("message", "Đăng ký thành công. Vui lòng đăng nhập.");
            resp.sendRedirect("login");
            
        } catch (javax.persistence.PersistenceException e) {
            // Xử lý lỗi unique constraint violation
            if (e.getCause() != null && e.getCause().getMessage().contains("duplicate key")) {
                req.setAttribute("error", "Email này đã được sử dụng. Vui lòng chọn email khác.");
            } else {
                req.setAttribute("error", "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại.");
            }
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại.");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
