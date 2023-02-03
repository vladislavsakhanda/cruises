package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CruisesCatalogCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        System.out.println();
        System.out.println();
        int page = 1;
        int recordsPerPage = 3;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
        }

        try {
            MySqlLinerDAO dao = new MySqlLinerDAO();
            Date date_start = null;
            Date date_end = null;

            Date minDate = dao.getDate(MySqlLinerDAO.QueryDate.MIN_DATE_START);
            Date maxDate = dao.getDate(MySqlLinerDAO.QueryDate.MAX_DATE_END);
            context.setAttribute("minDate", minDate);
            context.setAttribute("maxDate", maxDate);

            Object contextCurrentDateStart= context.getAttribute("currentDateStart");
            if (request.getParameter("date_start") != null) {
                date_start = Date.valueOf(request.getParameter("date_start"));
                context.setAttribute("currentDateStart", request.getParameter("date_start"));
            } else if (context.getAttribute("currentDateStart") == null) {
                date_start = minDate;
                context.setAttribute("currentDateStart", date_start);
            } else {
                if (contextCurrentDateStart.getClass() == String.class) {
                    date_start = java.sql.Date.valueOf((String) contextCurrentDateStart);
                } else {
                    date_start = (Date) contextCurrentDateStart;
                }
            }

            Object contextCurrentDateEnd = context.getAttribute("currentDateEnd");
            if (request.getParameter("date_end") != null) {
                date_end = Date.valueOf(request.getParameter("date_end"));
                context.setAttribute("currentDateEnd", request.getParameter("date_end"));
            } else if (contextCurrentDateEnd == null) {
                date_end = maxDate;
                context.setAttribute("currentDateEnd", date_end);
            } else {
                if (contextCurrentDateEnd.getClass() == String.class) {
                    date_end = java.sql.Date.valueOf((String) contextCurrentDateEnd);
                } else {
                    date_end = (Date) contextCurrentDateEnd;
                }
            }

            ArrayList<Integer> allDuration = (ArrayList<Integer>) dao.getAllDurationOfTrip();
            Collections.sort(allDuration);
            request.setAttribute("allDuration", allDuration);
            List<Liner> liners = new ArrayList<>();
            int choseDuration = 0;

            if (request.getParameter("choseDuration") == null || Objects.equals(request.getParameter("choseDuration"), "all")) {
                liners = dao.getAll(date_start, date_end, (page - 1) * recordsPerPage, recordsPerPage);
            } else {
                choseDuration = Integer.parseInt(request.getParameter("choseDuration"));
                liners = dao.getAll(choseDuration, date_start, date_end, (page - 1) * recordsPerPage, recordsPerPage);
            }

            int numberPageRecords = dao.getNumberPageRecords();
            int numberPages = (int) Math.ceil(numberPageRecords * 1.0 / recordsPerPage);

            context.setAttribute("linerList", liners);
            context.setAttribute("numberPages", numberPages);
            context.setAttribute("currentPage", page);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        forward("cruisesCatalog/cruisesCatalog");
    }

    private void doPost() throws ServletException, IOException {
        doGet();
    }
}
