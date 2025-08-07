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

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private VideoDAO videoDAO = new VideoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Video> videos = videoDAO.findAllByViewsDesc();
        request.setAttribute("videos", videos);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}

