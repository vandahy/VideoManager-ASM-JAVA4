package com.java04.servlet;

import com.java04.dao.VideoDAO;
import com.java04.dao.VideoDAOImpl;
import com.java04.entity.Video;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/admin/video",             // danh sách
        "/admin/video/create",      // POST tạo
        "/admin/video/update/*",    // POST cập nhật (VD: /admin/video/update/VID01)
        "/admin/video/delete/*",    // GET xóa (VD: /admin/video/delete/VID01)
        "/admin/video/edit/*",      // GET chỉnh sửa (VD: /admin/video/edit/VID01)
        "/admin/video/reset"        // GET reset form
})
public class VideoCRUDServlet extends HttpServlet {
    private final VideoDAO videoDAO = new VideoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();

        if (uri.contains("/edit/")) {
            String id = extractId(uri);
            edit(req, id);
        } else if (uri.contains("/delete/")) {
            String id = extractId(uri);
            delete(req, id);
        } else if (uri.contains("/reset")) {
            reset(req);
        }

        loadVideos(req);
        req.getRequestDispatcher("/WEB-INF/views/video-list-admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();

        if (uri.endsWith("/create")) {
            create(req);
        } else if (uri.contains("/update/")) {
            String id = extractId(uri);
            update(req, id);
        }

        loadVideos(req);
        req.getRequestDispatcher("/WEB-INF/views/video-list-admin.jsp").forward(req, resp);
    }

    private void loadVideos(HttpServletRequest req) {
        List<Video> list = videoDAO.findAll();
        req.setAttribute("videos", list);
    }

    private void create(HttpServletRequest req) {
        try {
            Video video = extractForm(req);
            videoDAO.create(video);
            req.setAttribute("message", "Thêm video thành công!");
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi thêm video: " + e.getMessage());
        }
    }

    private void update(HttpServletRequest req, String id) {
        try {
            Video video = extractForm(req);
            video.setId(id); // cập nhật theo id trên URL
            videoDAO.update(video);
            req.setAttribute("message", "Cập nhật video thành công!");
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi cập nhật video: " + e.getMessage());
        }
    }

    private void delete(HttpServletRequest req, String id) {
        try {
            videoDAO.deleteById(id);
            req.setAttribute("message", "Xóa video thành công!");
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi xóa video: " + e.getMessage());
        }
    }

    private void edit(HttpServletRequest req, String id) {
        Video video = videoDAO.findById(id);
        req.setAttribute("form", video);
    }

    private void reset(HttpServletRequest req) {
        req.setAttribute("form", new Video());
    }

    private Video extractForm(HttpServletRequest req) {
        Video v = new Video();
        v.setId(req.getParameter("id"));
        v.setTitle(req.getParameter("title"));
        v.setPoster(req.getParameter("poster"));
        String viewsStr = req.getParameter("views");
        v.setViews(viewsStr != null && !viewsStr.isEmpty() ? Integer.parseInt(viewsStr) : 0);
        v.setDescription(req.getParameter("description"));
        v.setActive(req.getParameter("active") != null);
        v.setLinks(req.getParameter("links"));
        return v;
    }

    // Lấy ID từ URL (VD: /admin/video/edit/VID01 => VID01)
    private String extractId(String uri) {
        return uri.substring(uri.lastIndexOf('/') + 1);
    }
}
