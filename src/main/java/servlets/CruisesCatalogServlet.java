package servlets;

import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@WebServlet("/cruisesCatalog")
public class CruisesCatalogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 3;
        if (req.getParameter("recordsPerPage") != null)
            recordsPerPage = Integer.parseInt(req.getParameter("recordsPerPage"));
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        try {
            MySqlLinerDAO dao = new MySqlLinerDAO();
            Date date_start = null;
            Date date_end = null;

            if (req.getParameter("date_start") == null) {
                date_start = dao.getDate(MySqlLinerDAO.QueryDate.MIN_DATE_START);
            } else {
                date_start = Date.valueOf(req.getParameter("date_start"));
            }
            if (req.getParameter("date_end") == null) {
                date_end = dao.getDate(MySqlLinerDAO.QueryDate.MAX_DATE_END);
            } else {
                date_end = Date.valueOf(req.getParameter("date_end"));
            }

            req.setAttribute("minDate", date_start);
            req.setAttribute("maxDate", date_end);

            ArrayList<Integer> allDuration = (ArrayList<Integer>) dao.getAllDurationOfTrip();
            Collections.sort(allDuration);
            req.setAttribute("allDuration", allDuration);
            List<Liner> liners = new ArrayList<>();
            int choseDuration = 0;
            if (req.getParameter("choseDuration") == null || Objects.equals(req.getParameter("choseDuration"), "all")) {
                liners = dao.getAll(date_start, date_end, (page - 1) * recordsPerPage, recordsPerPage);
            }
            else {
                choseDuration = Integer.parseInt(req.getParameter("choseDuration"));
                liners = dao.getAll(choseDuration, date_start, date_end, (page - 1) * recordsPerPage, recordsPerPage);
            }

            int numberPageRecords = dao.getNumberPageRecords();
            int numberPages = (int) Math.ceil(numberPageRecords * 1.0 / recordsPerPage);

            req.setAttribute("linerList", liners);
            req.setAttribute("numberPages", numberPages);
            req.setAttribute("currentPage", page);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/WEB-INF/pages/cruisesCatalog/cruisesCatalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("cruisesCatalog");
    }
}
