package com.java04.servlet;

import com.java04.dao.FavoriteDAO;
import com.java04.dao.FavoriteDAOImpl;
import com.java04.entity.Favorite;
import com.java04.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/like")
public class LikeVideoServlet extends HttpServlet {

    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy user đang đăng nhập từ session
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Nếu chưa login → chuyển hướng tới trang đăng nhập
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String userId = user.getId();
        String videoId = req.getParameter("videoId");

        // Nếu đã like rồi thì bỏ qua
        if (!favoriteDAO.isLiked(userId, videoId)) {
            favoriteDAO.like(userId, videoId);
        }

        // Quay lại trang video detail
        resp.sendRedirect("home");
    }
}

