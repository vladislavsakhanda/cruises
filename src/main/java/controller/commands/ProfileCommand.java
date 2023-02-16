package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Trip;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.TripService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ProfileCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class);
    private final TripService tripService = new TripService(new MySqlTripDAO());

    @Override
    public void process() throws ServletException, IOException, IllegalFieldException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userEmail") != null) {
            forward("registration/successProfile");
        } else {
            forward("registration/login");
        }
    }

    private void doPost() throws ServletException, IOException, IllegalFieldException {
        if (Objects.equals(request.getParameter("action"), "removeRequest")) {
            tripService.delete(new Trip(Long.parseLong(request.getParameter("id"))));

            forward("registration/successProfile");
        }

        if (Objects.equals(request.getParameter("action"), "changePayment")) {
            Trip trip = tripService.read(Long.parseLong(request.getParameter("id")));
            trip.setIsPaid(true);
            trip.setStatus(Trip.Status.CONFIRMED);
            tripService.update(trip);

            forward("registration/successProfile");
        }
    }
}
