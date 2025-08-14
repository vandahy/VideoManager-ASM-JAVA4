package com.java04.servlet;

import com.java04.dao.FavoriteDAO;
import com.java04.dao.FavoriteDAOImpl;
import com.java04.dao.ShareDAO;
import com.java04.dao.ShareDAOImpl;
import com.java04.dto.FavoriteReportDTO;
import com.java04.dto.FavoriteUserDTO;
import com.java04.dto.SharedFriendDTO;
import com.java04.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/report", 
        "/admin/report/favorite-users", 
        "/admin/report/shared-friends"})
public class ReportAdminServlet extends HttpServlet {
    private FavoriteDAO favoriteDAO;
    private ShareDAO shareDAO;

    @Override
    public void init() throws ServletException {
        favoriteDAO = new FavoriteDAOImpl();
        shareDAO = new ShareDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Require admin
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getAdmin() == null || !user.getAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String uri = req.getRequestURI();
        if (uri.endsWith("/favorite-users")) {
            handleFavoriteUsers(req, resp);
            return;
        }
        if (uri.endsWith("/shared-friends")) {
            handleSharedFriends(req, resp);
            return;
        }

        // Default: load summary favorites for tab 1 and empty lists for others
        List<FavoriteReportDTO> favoriteReports = favoriteDAO.getFavoriteReport();
        req.setAttribute("favoriteReports", favoriteReports);
        req.getRequestDispatcher("/WEB-INF/views/report-admin.jsp").forward(req, resp);
    }

    private void handleFavoriteUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        if (title == null) title = "";
        List<FavoriteUserDTO> favoriteUsers = favoriteDAO.getFavoriteUsersByVideoTitle(title);
        if (favoriteUsers == null || favoriteUsers.isEmpty()) {
            req.setAttribute("alertMessage", "Không tìm thấy video hoặc chưa có người yêu thích");
        }
        req.setAttribute("favoriteUsers", favoriteUsers);
        req.setAttribute("activeReportTab", "favusers");
        // Also preload summary so first tab isn't empty when navigating back within router
        req.setAttribute("favoriteReports", favoriteDAO.getFavoriteReport());
        req.getRequestDispatcher("/WEB-INF/views/report-admin.jsp").forward(req, resp);
    }

    private void handleSharedFriends(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        if (title == null) title = "";
        List<SharedFriendDTO> sharedFriends = shareDAO.getSharedFriendsByVideoTitle(title);
        if (sharedFriends == null || sharedFriends.isEmpty()) {
            req.setAttribute("alertMessage", "Không tìm thấy video hoặc chưa có chia sẻ");
        }
        req.setAttribute("sharedFriends", sharedFriends);
        req.setAttribute("activeReportTab", "shared");
        // Also preload summary so first tab isn't empty when navigating back within router
        req.setAttribute("favoriteReports", favoriteDAO.getFavoriteReport());
        req.getRequestDispatcher("/WEB-INF/views/report-admin.jsp").forward(req, resp);
    }
}


