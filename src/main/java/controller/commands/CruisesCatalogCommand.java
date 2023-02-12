package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger(CruisesCatalogCommand.class);
    @Override
    public void process() throws ServletException, DBException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException, DBException {
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

        MySqlLinerDAO dao = new MySqlLinerDAO();
        Date dateStart = null;
        Date dateEnd = null;

        Date minDate = dao.getDate(MySqlLinerDAO.QueryDate.MIN_DATE_START);
        Date maxDate = dao.getDate(MySqlLinerDAO.QueryDate.MAX_DATE_END);
        context.setAttribute("minDate", minDate);
        context.setAttribute("maxDate", maxDate);

        Object contextCurrentDateStart = context.getAttribute("currentDateStart");
        if (request.getParameter("dateStart") != null) {
            dateStart = Date.valueOf(request.getParameter("dateStart"));
            context.setAttribute("currentDateStart", request.getParameter("dateStart"));
        } else if (context.getAttribute("currentDateStart") == null) {
            dateStart = minDate;
            context.setAttribute("currentDateStart", dateStart);
        } else {
            if (contextCurrentDateStart.getClass() == String.class) {
                dateStart = Date.valueOf((String) contextCurrentDateStart);
            } else {
                dateStart = (Date) contextCurrentDateStart;
            }
        }

        Object contextCurrentDateEnd = context.getAttribute("currentDateEnd");
        if (request.getParameter("dateEnd") != null) {
            dateEnd = Date.valueOf(request.getParameter("dateEnd"));
            context.setAttribute("currentDateEnd", request.getParameter("dateEnd"));
        } else if (contextCurrentDateEnd == null) {
            dateEnd = maxDate;
            context.setAttribute("currentDateEnd", dateEnd);
        } else {
            if (contextCurrentDateEnd.getClass() == String.class) {
                dateEnd = Date.valueOf((String) contextCurrentDateEnd);
            } else {
                dateEnd = (Date) contextCurrentDateEnd;
            }
        }

        ArrayList<Integer> allDuration = (ArrayList<Integer>) dao.getAllDurationOfTrip();
        Collections.sort(allDuration);
        request.setAttribute("allDuration", allDuration);
        List<Liner> liners = new ArrayList<>();
        int choseDuration = 0;

        if (request.getParameter("choseDuration") == null || Objects.equals(request.getParameter("choseDuration"), "all")) {
            liners = dao.getAll(dateStart, dateEnd, (page - 1) * recordsPerPage, recordsPerPage);
        } else {
            choseDuration = Integer.parseInt(request.getParameter("choseDuration"));
            liners = dao.getAll(choseDuration, dateStart, dateEnd, (page - 1) * recordsPerPage, recordsPerPage);
        }

        int numberPageRecords = dao.getNumberPageRecords();
        int numberPages = (int) Math.ceil(numberPageRecords * 1.0 / recordsPerPage);

        context.setAttribute("linerList", liners);
        context.setAttribute("numberPages", numberPages);
        context.setAttribute("currentPage", page);

        forward("cruisesCatalog/cruisesCatalog");
    }

    private void doPost() throws ServletException, IOException, DBException {
        doGet();
    }
}
