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

public class CruisesRecordsLinerCommand extends FrontCommand {
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

        forward("admin/cruisesRecordsLiner");
    }

    private void doPost() throws ServletException, IOException, IllegalFieldException {
        request.setAttribute("id", request.getParameter("id"));

        if (Objects.equals(request.getParameter("action"), "delete")) {
            ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
            LinerService linerService = serviceFactory.getLinerService(MySqlLinerDAO.getInstance());
            linerService.delete(new Liner(Long.parseLong(request.getParameter("linerId"))));

            context.setAttribute("message", "label.lang.success.CruiseDeleted");
            sendRedirect("/cruises?command=Success");
        } else {
            action();
            forward("admin/cruisesRecordsLiner");
        }
    }

    private void action() throws IllegalFieldException {
        long linerId = Long.parseLong(request.getParameter("id"));

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String capacity = request.getParameter("capacity");
        String priceCoefficient = request.getParameter("priceCoefficient");
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");


        ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
        LinerService linerService = serviceFactory.getLinerService(MySqlLinerDAO.getInstance());
        Liner updatedLiner = linerService.read(linerId);

        List<String> route = updatedLiner.getRoute();

        ArrayList<String> newRoute = new ArrayList<String>();
        for (int i = 0; i < updatedLiner.getNumberDays(); i++) {
            if (!Objects.equals(request.getParameter("route" + (i + 1)), "")) {
                newRoute.add(request.getParameter("route" + (i + 1)));
            } else if (i < route.size()) {
                newRoute.add(route.get(i));
            } else {
                newRoute.add("Port" + (i + 1));
            }
        }
//        System.out.println(newRoute);
        updatedLiner.setRoute(newRoute);
//        updatedLiner.setRoute(updatedLiner.getRoute());


        if (name == null || name.length() == 0) {
            request.setAttribute("messageName", "label.lang.empty");
        } else if (name.length() >= 90) {
            request.setAttribute("messageName", "label.lang.index.welcome.admin.cruisesRecordsLiner.nameMoreThanNinetyCharacters");
        } else {
            request.setAttribute("messageName", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
            updatedLiner.setName(name);
        }

        if (description == null || description.length() == 0) {
            request.setAttribute("messageDescription", "label.lang.empty");
        } else if (description.length() >= 1000) {
            request.setAttribute("messageDescription", "label.lang.index.welcome.admin.cruisesRecordsLiner.descriptionMoreThanThousandCharacters");
        } else {
            request.setAttribute("messageDescription", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
            updatedLiner.setDescription(description);
        }

        if (capacity == null || capacity.length() == 0) {
            request.setAttribute("messageCapacity", "label.lang.empty");
        } else if (Integer.parseInt(capacity) >= 3000) {
            request.setAttribute("messageCapacity", "label.lang.index.welcome.admin.cruisesRecordsLiner.capacityMoreThanThreeThousand");
        } else if (Integer.parseInt(capacity) < 0) {
            request.setAttribute("messageCapacity", "label.lang.index.welcome.admin.cruisesRecordsLiner.capacityLessThanZero");
        } else {
            request.setAttribute("messageCapacity", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
            updatedLiner.setCapacity(Integer.parseInt(capacity));
        }

        if (priceCoefficient == null || priceCoefficient.length() == 0) {
            request.setAttribute("messagePriceCoefficient", "label.lang.empty");
        } else if (Double.parseDouble(priceCoefficient) <= 0) {
            request.setAttribute("messagePriceCoefficient", "label.lang.index.welcome.admin.cruisesRecordsLiner.priceCoefficientMustBeGreaterThanZero");
        } else {
            request.setAttribute("messagePriceCoefficient", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
            updatedLiner.setPriceCoefficient(Double.parseDouble(priceCoefficient));
        }

        if (dateStart == null || dateStart.length() == 0) {
            request.setAttribute("messageDateStart", "label.lang.empty");

            if (dateEnd == null || dateEnd.length() == 0) {
                request.setAttribute("messageDateEnd", "label.lang.empty");
            } else {
                try {
                    Liner.datesCheck(linerService.read(linerId).getDateStart(), Date.valueOf(dateEnd));
                    request.setAttribute("messageDateEnd", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
                    updatedLiner.setDateEnd(Date.valueOf(dateEnd));
                } catch (IllegalFieldException e) {
                    request.setAttribute("messageDateEnd", "label.lang.index.welcome.admin.cruisesRecordsLiner.dateStartLaterThanDateEnd");
                }
            }
        }

        if (dateEnd == null || dateEnd.length() == 0) {
            request.setAttribute("messageDateEnd", "label.lang.empty");

            if (dateStart == null || dateStart.length() == 0) {
                request.setAttribute("messageDateStart", "label.lang.empty");
            } else {
                try {
                    Liner.datesCheck(Date.valueOf(dateStart), linerService.read(linerId).getDateEnd());
                    request.setAttribute("messageDateStart", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
                    updatedLiner.setDateStart(Date.valueOf(dateStart));
                } catch (IllegalFieldException e) {
                    request.setAttribute("messageDateStart", "label.lang.index.welcome.admin.cruisesRecordsLiner.dateStartLaterThanDateEnd");
                }
            }
        }

        if (dateStart != null && dateStart.length() != 0 && dateEnd != null && dateEnd.length() != 0) {
            try {
                Liner.datesCheck(Date.valueOf(dateStart), Date.valueOf(dateEnd));
                request.setAttribute("messageDateStart", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
                request.setAttribute("messageDateEnd", "label.lang.index.welcome.admin.cruisesRecordsLiner.changed");
                updatedLiner.setDateStart(Date.valueOf(dateStart));
                updatedLiner.setDateEnd(Date.valueOf(dateEnd));
            } catch (IllegalFieldException e) {
                request.setAttribute("messageDateStart", "label.lang.index.welcome.admin.cruisesRecordsLiner.dateStartLaterThanDateEnd");
                request.setAttribute("messageDateEnd", "label.lang.index.welcome.admin.cruisesRecordsLiner.dateStartLaterThanDateEnd");
            }
        }

        linerService.update(updatedLiner);
    }
}
