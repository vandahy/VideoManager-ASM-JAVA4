package com.java04.servlet;

import com.java04.dao.VideoDAO;
import com.java04.dao.VideoDAOImpl;
import com.java04.entity.Video;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/video")
public class VideoServlet extends HttpServlet {
    private VideoDAO videoDAO = new VideoDAOImpl();  // Đã có rồi

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Video> videos = videoDAO.findAll();  // hoặc findAllByViewsDesc()
        request.setAttribute("videos", videos);
        request.getRequestDispatcher("/WEB-INF/views/video-list.jsp").forward(request, response);
        System.out.println("Số video: " + videos.size());
    }
}
