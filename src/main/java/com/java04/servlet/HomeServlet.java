package com.java04.servlet;

import com.java04.dao.FavoriteDAO;
import com.java04.dao.FavoriteDAOImpl;
import com.java04.dao.VideoDAO;
import com.java04.dao.VideoDAOImpl;
import com.java04.entity.Favorite;
import com.java04.entity.User;
import com.java04.entity.Video;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private VideoDAO videoDAO = new VideoDAOImpl();
    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        int size = 6; // số video mỗi trang

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int totalVideos = videoDAO.count(); // tổng số video
        int totalPages = (int) Math.ceil((double) totalVideos / size);

        List<Video> videos = videoDAO.findByPage(page, size);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            List<Favorite> favorites = favoriteDAO.findByUserId(user.getId());
            request.setAttribute("favorites", favorites);
        }

        request.setAttribute("videos", videos);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}

