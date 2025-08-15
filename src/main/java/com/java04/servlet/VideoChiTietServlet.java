package com.java04.servlet;

import com.java04.dao.VideoDAO;
import com.java04.dao.VideoDAOImpl;
import com.java04.entity.Video;
import com.java04.dao.FavoriteDAO;
import com.java04.dao.FavoriteDAOImpl;
import com.java04.entity.User;
import com.java04.dao.ShareDAO;
import com.java04.dao.ShareDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/videochitiet")
public class VideoChiTietServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String videoId = request.getParameter("videoId");

        VideoDAO dao = new VideoDAOImpl();
        Video currentVideo = dao.findById(videoId);
        if (currentVideo != null) {
            currentVideo.setViews(currentVideo.getViews() + 1);
            dao.update(currentVideo);

            int likeCount = dao.countLikeByViews(videoId);
            request.setAttribute("likeCount", likeCount);

            // Thêm: Lấy số lượt share
            ShareDAO shareDAO = new ShareDAOImpl();
            int shareCount = shareDAO.countSharesByVideoId(videoId);
            request.setAttribute("shareCount", shareCount);

            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;
            boolean hasLiked = false;
            if (user != null) {
                FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
                hasLiked = favoriteDAO.isLiked(user.getId(), videoId);
            }
            request.setAttribute("hasLiked", hasLiked);
        }
        request.setAttribute("video", currentVideo);

        // Lấy toàn bộ video
        List<Video> allVideos = dao.findAll(); // Bạn cần đảm bảo phương thức findAll() có trong DAO

        // Loại bỏ video hiện tại
        allVideos.removeIf(v -> v.getId().equals(videoId));

        // Lấy 9 video đầu tiên
        List<Video> recommended = allVideos.size() > 9 ? allVideos.subList(0, 9) : allVideos;

        request.setAttribute("recommended", recommended);
        request.getRequestDispatcher("/WEB-INF/views/videochitiet.jsp").forward(request, response);
    }
}