package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Liner;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.LinerService;
import services.ServiceFactory;
import services.UserService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Date;

public class CruisesRecordsLinerCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(CruisesRecordsLinerCommand.class);

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
        action();
        forward("admin/cruisesRecordsLiner");
    }

    private void doPost() throws ServletException, IOException, IllegalFieldException {
        doGet();
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
