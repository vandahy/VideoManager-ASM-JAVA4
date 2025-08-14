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

@WebServlet("/dislike")
public class DislikeVideoServlet extends HttpServlet {

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
        if (videoId != null && !videoId.isEmpty()) {
            favoriteDAO.delete(userId, videoId); // Xóa khỏi yêu thích
        }

        String section = req.getParameter("section");
        if ("favorite".equals(section)) {
            resp.sendRedirect("home?section=favorite");
        } else {
            resp.sendRedirect("home");
        }
    }
}

