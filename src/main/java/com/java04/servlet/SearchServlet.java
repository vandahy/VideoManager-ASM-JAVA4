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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private VideoDAO videoDAO = new VideoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = req.getParameter("keyword");
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Video> results = videoDAO.findByTitleContaining(keyword);
            req.setAttribute("results", results);
        }

        req.getRequestDispatcher("/WEB-INF/views/search.jsp").forward(req, resp);
    }
}
