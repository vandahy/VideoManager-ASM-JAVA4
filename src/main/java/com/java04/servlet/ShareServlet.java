package com.java04.servlet;

import com.java04.dao.VideoDAO;
import com.java04.dao.VideoDAOImpl;
import com.java04.entity.User;
import com.java04.entity.Video;
import com.java04.utils.EmailUtils;
import jakarta.mail.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/share")
public class ShareServlet extends HttpServlet {

    private final VideoDAO videoDAO = new VideoDAOImpl(); // Khai báo DAO

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

        try {
            Video video = videoDAO.findById(videoId); // Lấy video từ DB
            String link = video.getLinks(); // Link gốc YouTube

            EmailUtils.sendEmail(email, "Your friend shared a video", "Watch it here: " + link);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("home");
    }
}



