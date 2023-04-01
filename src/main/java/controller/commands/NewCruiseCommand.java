package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.LinerService;
import services.ServiceFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewCruiseCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(NewCruiseCommand.class);

    @Override
    public void process() throws ServletException, IOException, IllegalFieldException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException, IllegalFieldException {
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("messageName", "label.lang.empty");
        request.setAttribute("messageDescription", "label.lang.empty");
        request.setAttribute("messageCapacity", "label.lang.empty");
        request.setAttribute("messagePriceCoefficient", "label.lang.empty");
        request.setAttribute("messageDateStart", "label.lang.empty");
        request.setAttribute("messageDateEnd", "label.lang.empty");

        forward("admin/newCruise");
    }

    private void doPost() throws ServletException, IOException, IllegalFieldException {
        request.setAttribute("id", request.getParameter("id"));
        action();
    }

    private void action() throws IllegalFieldException, ServletException, IOException {
        boolean isReadyForCreation = true;

        String name = request.getParameter("name");
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");

        ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
        LinerService linerService = serviceFactory.getLinerService(MySqlLinerDAO.getInstance());
        Liner liner = new Liner();

        if (name == null || name.length() == 0) {
            request.setAttribute("messageName", "label.lang.empty");
            isReadyForCreation = false;
        } else if (name.length() >= 90) {
            request.setAttribute("messageName", "label.lang.index.welcome.admin.cruisesRecordsLiner.nameMoreThanNinetyCharacters");
            isReadyForCreation = false;
        } else {
            request.setAttribute("messageName", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
            liner.setName(name);
        }

        if (dateStart == null || dateStart.length() == 0) {
            request.setAttribute("messageDateStart", "label.lang.empty");
            isReadyForCreation = false;
        } else if (dateEnd == null || dateEnd.length() == 0) {
            request.setAttribute("messageDateEnd", "label.lang.empty");
            isReadyForCreation = false;
        } else {
            try {
                Liner.datesCheck(Date.valueOf(dateStart), Date.valueOf(dateEnd));
                request.setAttribute("messageDateStart", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
                request.setAttribute("messageDateEnd", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
                liner.setDateStart(Date.valueOf(dateStart));
                liner.setDateEnd(Date.valueOf(dateEnd));
            } catch (IllegalFieldException e) {
                isReadyForCreation = false;
                request.setAttribute("messageDateStart", "label.lang.index.welcome.admin.cruisesRecordsLiner.dateStartLaterThanDateEnd");
                request.setAttribute("messageDateEnd", "label.lang.index.welcome.admin.cruisesRecordsLiner.dateStartLaterThanDateEnd");
            }
        }


        if (isReadyForCreation = true) {
            ArrayList<String> route = new ArrayList<String>();
            for (int i = 0; i < liner.getNumberDays(); i++) {
                route.add("Port" + (i + 1));
            }
            liner.setRoute(route);

            liner.setDescription("");
            liner.setCapacity(1);
            liner.setPriceCoefficient(1);
            linerService.create(liner);
            context.setAttribute("message", "label.lang.success.CruiseAdded");
            sendRedirect("/cruises?command=Success");
        } else {
            forward("admin/newCruise");
        }
    }
}
