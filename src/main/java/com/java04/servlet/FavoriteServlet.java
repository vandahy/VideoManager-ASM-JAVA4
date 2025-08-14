package com.java04.servlet;

import com.java04.dao.FavoriteDAO;
import com.java04.dao.FavoriteDAOImpl;
import com.java04.entity.Favorite;
import com.java04.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/favorites")
public class FavoriteServlet extends HttpServlet {
    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        List<Favorite> favorites = favoriteDAO.findByUserId(user.getId()); // user.getId() là String

        // Gửi danh sách yêu thích sang JSP
        request.setAttribute("favorites", favorites);
        request.getRequestDispatcher("/WEB-INF/views/favorites.jsp").forward(request, response);
    }
}
