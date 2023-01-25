package com.my;

import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cruisesCatalog")
public class CruisesCatalogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 3;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        MySqlLinerDAO dao = new MySqlLinerDAO();
        List<Liner> liners = dao.getAll((page - 1) * recordsPerPage, recordsPerPage);

        int noOfRecords = dao.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("linerList", liners);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);

        req.getRequestDispatcher("/WEB-INF/pages/cruisesCatalog/cruisesCatalog.jsp").forward(req, resp);
    }
}
