package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.LinerService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CruisesRecordsCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(CruisesRecordsCommand.class);
    private final LinerService linerService = new LinerService(MySqlLinerDAO.getInstance());
    private static final int DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_RECORDS_PER_PAGE = 5;
    private static final int DEFAULT_CHOSE_DURATION = 0;

    @Override
    public void process() throws ServletException, IOException, DBException, IllegalFieldException, SQLException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void action() throws DBException, SQLException {
        if (Objects.equals(request.getParameter("action"), "reset")) {
            context.removeAttribute("linerList");
            context.removeAttribute("numberPages");
            context.removeAttribute("currentPage");
            context.removeAttribute("minDateStart");
            context.removeAttribute("maxDateStart");
            context.removeAttribute("minDateEnd");
            context.removeAttribute("maxDateEnd");
            context.removeAttribute("recordsPerPage");
            context.removeAttribute("choseDuration");
        }
    }

    void doGet() throws ServletException, IOException, DBException, SQLException, IllegalFieldException {
        int currentPage = DEFAULT_CURRENT_PAGE;
        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } else if (context.getAttribute("currentPage") != null) {
            currentPage = (int) context.getAttribute("currentPage");
        }

        int recordsPerPage = DEFAULT_RECORDS_PER_PAGE;
        if (request.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
        } else if (context.getAttribute("recordsPerPage") != null) {
            recordsPerPage = (int) context.getAttribute("recordsPerPage");
        }

        Date minDateStart = linerService.getDate(MySqlLinerDAO.QueryDate.MIN_DATE_START);
        Date maxDateStart = linerService.getDate(MySqlLinerDAO.QueryDate.MAX_DATE_START);
        Date minDateEnd = linerService.getDate(MySqlLinerDAO.QueryDate.MIN_DATE_END);
        Date maxDateEnd = linerService.getDate(MySqlLinerDAO.QueryDate.MAX_DATE_END);

        context.setAttribute("minDateStart", minDateStart);
        context.setAttribute("maxDateStart", maxDateStart);
        context.setAttribute("minDateEnd", minDateEnd);
        context.setAttribute("maxDateEnd", maxDateEnd);

        Date dateStart = minDateStart;
        Object contextCurrentDateStart = context.getAttribute("currentDateStart");
        if (Objects.equals(request.getParameter("action"), "reset")) {
            context.setAttribute("currentDateStart", minDateStart);
        } else if (request.getParameter("action") == null && request.getParameter("dateStart") != null) {
            dateStart = Date.valueOf(request.getParameter("dateStart"));
            context.setAttribute("currentDateStart", request.getParameter("dateStart"));
        } else if (context.getAttribute("currentDateStart") == null) {
            context.setAttribute("currentDateStart", dateStart);
        } else {
            if (contextCurrentDateStart.getClass() == String.class) {
                dateStart = Date.valueOf((String) contextCurrentDateStart);
            } else {
                dateStart = (Date) contextCurrentDateStart;
            }
        }

        Date dateEnd = maxDateEnd;
        Object contextCurrentDateEnd = context.getAttribute("currentDateEnd");
        if (Objects.equals(request.getParameter("action"), "reset")) {
            context.setAttribute("currentDateEnd", maxDateEnd);
        } else if (request.getParameter("dateEnd") != null) {
            dateEnd = Date.valueOf(request.getParameter("dateEnd"));
            context.setAttribute("currentDateEnd", request.getParameter("dateEnd"));
        } else if (contextCurrentDateEnd == null) {
            context.setAttribute("currentDateEnd", maxDateEnd);
        } else {
            if (contextCurrentDateEnd.getClass() == String.class) {
                dateEnd = Date.valueOf((String) contextCurrentDateEnd);
            } else {
                dateEnd = (Date) contextCurrentDateEnd;
            }
        }

        ArrayList<Integer> allDurations = (ArrayList<Integer>) linerService.getAllDurationOfTrip();
        Collections.sort(allDurations);
        request.setAttribute("allDurations", allDurations);
        List<Liner> liners = new ArrayList<>();

        int choseDuration = DEFAULT_CHOSE_DURATION;
        if (request.getParameter("choseDuration") != null) {
            choseDuration = Integer.parseInt(request.getParameter("choseDuration"));
        } else if (context.getAttribute("choseDuration") != null) {
            choseDuration = (int) context.getAttribute("choseDuration");
        }

        if (dateStart.compareTo(dateEnd) <= 0) {
            if (choseDuration == 0) {
                liners = linerService.getAll(dateStart, dateEnd, (currentPage - 1) * recordsPerPage, recordsPerPage);
            } else {
                liners = linerService.getAll(choseDuration, dateStart, dateEnd, (currentPage - 1) * recordsPerPage, recordsPerPage);
            }
        }

        int numberPageRecords = linerService.getNumberPageRecords();

        int numberPages = (int) Math.ceil(numberPageRecords * 1.0 / recordsPerPage);

        context.setAttribute("linerList", liners);
        context.setAttribute("numberPages", numberPages);
        context.setAttribute("currentPage", currentPage);
        context.setAttribute("recordsPerPage", recordsPerPage);
        context.setAttribute("choseDuration", choseDuration);

        forward("admin/cruisesRecords");
    }

    void doPost() throws ServletException, IOException, DBException, IllegalFieldException, SQLException {
        action();
        doGet();
    }
}
