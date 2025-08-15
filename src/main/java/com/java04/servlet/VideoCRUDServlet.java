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
            // Basic required field validation
            if (isNullOrEmpty(video.getId()) || isNullOrEmpty(video.getTitle()) || isNullOrEmpty(video.getLinks())) {
                req.setAttribute("error", "Vui lòng nhập đầy đủ thông tin bắt buộc (ID, Title, Link)");
                return;
            }

            // Normalize ID (trim and lower prefix check)
            video.setId(video.getId().trim());

            // Enforce pattern: vd + number
            String id = video.getId();
            int digitIdx = firstDigitIndex(id);
            if (digitIdx <= 0 || !id.substring(0, digitIdx).equalsIgnoreCase("vid")) {
                req.setAttribute("error", "ID video phải có dạng vd + số (ví dụ: vid01, vid11)");
                return;
            }

            // Duplicate ID check
            if (videoDAO.findById(video.getId()) != null) {
                req.setAttribute("error", "Video ID này đã tồn tại");
                return;
            }

            // Sequential ID check (e.g., last vd10 -> new must be vd11)
            String expectedId = computeNextVideoIdForPrefix("vid");
            if (expectedId != null && !video.getId().equalsIgnoreCase(expectedId)) {
                req.setAttribute("error", "ID video " + video.getId() + " không hợp lệ. Phải là " + expectedId);
                return;
            }

            videoDAO.create(video);
            req.setAttribute("message", "Thêm video thành công!");
        } catch (Exception e) {
            String cause = rootCauseMessage(e);
            if (cause != null && cause.toLowerCase().contains("constraint") ||
                cause != null && cause.toLowerCase().contains("duplicate")) {
                req.setAttribute("error", "Video ID này đã tồn tại");
            } else {
                req.setAttribute("error", "Lỗi thêm video: " + (cause != null ? cause : e.getMessage()));
            }
        }
    }

    private void update(HttpServletRequest req, String id) {
        try {
            if (isNullOrEmpty(id) || "null".equalsIgnoreCase(id)) {
                req.setAttribute("error", "Vui lòng bấm Edit video muốn thao tác");
                return;
            }
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
            if (isNullOrEmpty(id) || "null".equalsIgnoreCase(id)) {
                req.setAttribute("error", "Vui lòng bấm Edit video muốn thao tác");
                return;
            }
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

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    // Compute next expected video ID based on existing IDs. If not determinable, return null.
    private String computeNextVideoId() {
        try {
            List<Video> all = videoDAO.findAll();
            String lastId = null;
            int lastNum = -1;
            int padLength = 0;
            String prefix = null;
            for (Video v : all) {
                String vid = v.getId();
                if (vid == null) continue;
                int idx = firstDigitIndex(vid);
                if (idx < 0) continue;
                String pfx = vid.substring(0, idx);
                String numPart = vid.substring(idx);
                try {
                    int num = Integer.parseInt(numPart);
                    if (num > lastNum) {
                        lastNum = num;
                        lastId = vid;
                        padLength = numPart.length();
                        prefix = pfx;
                    }
                } catch (NumberFormatException ignore) {}
            }
            if (lastId == null) return null;
            int next = lastNum + 1;
            String nextNum = padLength > 0 ? String.format("%0" + padLength + "d", next) : String.valueOf(next);
            return (prefix != null ? prefix : "") + nextNum;
        } catch (Exception e) {
            return null;
        }
    }

    private String computeNextVideoIdForPrefix(String requiredPrefixLower) {
        try {
            List<Video> all = videoDAO.findAll();
            int lastNum = 0;
            int padLength = 0;
            boolean found = false;
            for (Video v : all) {
                String vid = v.getId();
                if (vid == null) continue;
                int idx = firstDigitIndex(vid);
                if (idx <= 0) continue;
                String pfx = vid.substring(0, idx);
                if (!pfx.equalsIgnoreCase(requiredPrefixLower)) continue;
                String numPart = vid.substring(idx);
                try {
                    int num = Integer.parseInt(numPart);
                    if (!found || num > lastNum) {
                        lastNum = num;
                        padLength = numPart.length();
                        found = true;
                    }
                } catch (NumberFormatException ignore) {}
            }
            if (!found) {
                // If no existing IDs with this prefix, start with vd01
                return requiredPrefixLower + "01";
            }
            int next = lastNum + 1;
            String nextNum = padLength > 0 ? String.format("%0" + padLength + "d", next) : String.valueOf(next);
            return requiredPrefixLower + nextNum;
        } catch (Exception e) {
            return null;
        }
    }

    private int firstDigitIndex(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) return i;
        }
        return -1;
    }

    private String rootCauseMessage(Throwable t) {
        if (t == null) return null;
        Throwable cur = t;
        while (cur.getCause() != null && cur.getCause() != cur) {
            cur = cur.getCause();
        }
        return cur.getMessage();
    }

    // Lấy ID từ URL (VD: /admin/video/edit/VID01 => VID01)
    private String extractId(String uri) {
        return uri.substring(uri.lastIndexOf('/') + 1);
    }
}
