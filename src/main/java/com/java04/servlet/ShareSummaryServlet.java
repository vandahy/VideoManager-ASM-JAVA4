package com.java04.servlet;

import com.java04.dao.ShareDAO;
import com.java04.dao.ShareDAOImpl;
import com.java04.dto.ShareSummaryDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/summary")
public class ShareSummaryServlet extends HttpServlet {
    private ShareDAO shareDAO = new ShareDAOImpl(); // DAO có hàm getShareSummary()

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ShareSummaryDTO> list = shareDAO.getShareSummary();
        request.setAttribute("shareSummary", list);
        request.getRequestDispatcher("/WEB-INF/views/summary.jsp").forward(request, response);
    }
}