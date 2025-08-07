package com.java04.servlet;

import com.java04.dao.FavoriteDAO;
import com.java04.dao.FavoriteDAOImpl;
import com.java04.entity.Favorite;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/favorite-list")
public class FavoriteListServlet extends HttpServlet {
    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Favorite> favorites = favoriteDAO.findAll();
        request.setAttribute("favorites", favorites);
        System.out.println("Số lượng favorites: " + favorites.size());

        request.getRequestDispatcher("/views/favorite-list.jsp").forward(request, response);
    }
}
