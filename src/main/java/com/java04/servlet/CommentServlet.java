package com.java04.servlet;

import com.java04.entity.User;
import com.java04.entity.Comment;
import com.java04.dao.CommentDAO;
import com.java04.dao.CommentDAOImpl;
import com.java04.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Servlet xử lý các request liên quan đến Comment
 * Chức năng:
 * - GET: Load comments cho hiển thị (hiện tại không sử dụng)
 * - POST: Tạo comment mới từ form submit

 */
@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Load comments cho hiển thị
     * (Hiện tại không sử dụng, comments được load trong VideoChiTietServlet)
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy videoId từ parameter
        String videoId = request.getParameter("videoId");
        if (videoId != null && !videoId.trim().isEmpty()) {
            try {
                // Tạo CommentDAO và load comments
                CommentDAO commentDAO = new CommentDAOImpl(getServletContext());
                List<Comment> videoComments = commentDAO.findByVideoId(videoId);
                
                // Set comments vào request attribute để JSP hiển thị
                request.setAttribute("comments", videoComments);
            } catch (Exception e) {
                // Log lỗi nhưng không làm crash trang
                System.err.println("Error loading comments: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Xử lý POST request - Tạo comment mới
     * 
     * Flow:
     * 1. Kiểm tra user login
     * 2. Validate input data
     * 3. Tạo Comment object
     * 4. Lưu vào file JSON
     * 5. Redirect về trang video
     * 
     * @param request HttpServletRequest chứa videoId và content
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Set encoding cho request
        request.setCharacterEncoding("UTF-8");
        
        // Lấy session và kiểm tra user đã đăng nhập chưa
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");
        
        if (userObj == null) {
            // Nếu chưa đăng nhập, redirect về trang login
            response.sendRedirect("login");
            return;
        }
        
        try {
            // Lấy thông tin từ form
            String videoId = request.getParameter("videoId");
            String content = request.getParameter("content");
            
            // Validate dữ liệu input
            if (videoId == null || videoId.trim().isEmpty() || 
                content == null || content.trim().isEmpty()) {
                // Nếu thiếu dữ liệu, redirect về trang video với thông báo lỗi
                response.sendRedirect("videochitiet?videoId=" + videoId + "&error=missing_data");
                return;
            }
            
            // Lấy thông tin user từ session
            User user = (User) session.getAttribute("user");
            
            if (user == null) {
                // Nếu không lấy được thông tin user, redirect về login
                response.sendRedirect("login");
                return;
            }
            
            // Lấy thông tin user
            String userId = user.getId();
            String fullName = user.getFullname();
            
            // Tạo Comment object mới
            Comment newComment = new Comment();
            newComment.setVideoId(videoId);
            newComment.setUserId(userId);
            newComment.setFullName(fullName);
            
            // Tạo thời gian comment hiện tại
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            newComment.setCommentTime(currentTime);
            
            // Set nội dung comment (loại bỏ khoảng trắng đầu cuối)
            newComment.setContent(content.trim());
            
            // Lưu comment vào file JSON
            CommentDAO commentDAO = new CommentDAOImpl(getServletContext());
            commentDAO.create(newComment);
            
            // Redirect về trang video để hiển thị comment mới
            response.sendRedirect("videochitiet?videoId=" + videoId);
            
        } catch (Exception e) {
            // Log lỗi
            System.err.println("Error in CommentServlet: " + e.getMessage());
            e.printStackTrace();
            
            // Redirect về trang video với thông báo lỗi
            String videoId = request.getParameter("videoId");
            response.sendRedirect("videochitiet?videoId=" + videoId + "&error=server_error");
        }
    }
}
