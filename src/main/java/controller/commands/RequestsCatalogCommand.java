package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.TripService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class RequestsCatalogCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(RequestsCatalogCommand.class);
    private final TripService tripService = new TripService(new MySqlTripDAO());

    @Override
    public void process() throws ServletException, IOException, DBException, IllegalFieldException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        request.setAttribute("pathProjectDirectory", context.getRealPath("/"));

        forward("admin/requestsCatalog");
        ;
    }

    private void doPost() throws ServletException, IOException, IllegalFieldException {
        Trip trip = tripService.read(Long.parseLong(request.getParameter("tripId")));
        trip.setStatus(Trip.Status.valueOf(Integer.parseInt(request.getParameter("status"))));
        tripService.update(trip);
        LOGGER.info("trip status updated");

        sendRedirect("/cruises?command=RequestsCatalog");
    }
}
