package com.java04.servlet;

import com.java04.dao.ShareDAO;
import com.java04.dao.ShareDAOImpl;
import com.java04.dao.VideoDAO;
import com.java04.dao.VideoDAOImpl;
import com.java04.dao.ShareDAO;
import com.java04.dao.ShareDAOImpl;
import com.java04.entity.User;
import com.java04.entity.Video;
import com.java04.entity.Share;
import com.java04.entity.User;
import com.java04.utils.EmailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/share")
public class ShareServlet extends HttpServlet {

    private final VideoDAO videoDAO = new VideoDAOImpl(); // Khai báo DAO
    private final ShareDAO shareDAO = new ShareDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String email = req.getParameter("email");
        String videoId = req.getParameter("videoId");
        String returnUrl = req.getParameter("returnUrl");

        try {
            Video video = videoDAO.findById(videoId); // Lấy video từ DB
            String link = video.getLinks(); // Link gốc YouTube

            EmailUtils.sendEmail(email, "Your friend shared a video", "Watch it here: " + link);
            // Thêm: Tạo record Share trong database
            ShareDAO shareDAO = new ShareDAOImpl();
            Share share = new Share();
            share.setUser(user);
            share.setVideo(video);
            share.setEmails(email);
            share.setShareDate(new Date());
            shareDAO.create(share);

            // Lưu lịch sử chia sẻ để phục vụ thống kê
            User currentUser = (User) req.getSession().getAttribute("user");
            if (currentUser != null && video != null) {
                Share s = new Share();
                s.setUser(currentUser);
                s.setVideo(video);
                s.setEmails(email);
                s.setShareDate(new Date());
                shareDAO.create(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (returnUrl == null || returnUrl.isEmpty()) {
            returnUrl = "home";
        }
        resp.sendRedirect(returnUrl);
    }
}
