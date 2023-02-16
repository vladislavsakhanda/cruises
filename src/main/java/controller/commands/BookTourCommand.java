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
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

public class BookTourCommand extends FrontCommand {
    private final TripService tripService = new TripService(new MySqlTripDAO());


    @Override
    public void process() throws ServletException, IOException, IllegalFieldException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private boolean TripExist() throws IllegalFieldException {
        Trip trip = null;

        if (request.getParameter("linerId") != null) {
            trip = tripService.readByUserIdAndLinerId(
                    (Long) request.getSession().getAttribute("userId"),
                    Long.parseLong(request.getParameter("linerId")));
        }

        return trip != null;
    }

    private void doGet() throws ServletException, IOException, IllegalFieldException {
        if (TripExist()) {
            sendRedirect("/cruises?command=ErrorBookTour");
        } else {
            forward("cruisesCatalog/bookTour");
        }
    }

    private void doPost() throws ServletException, IOException, IllegalFieldException {
        if (Objects.equals(request.getParameter("actionBook"), "bookView")) {
            setServletContext();
            doGet();
        } else if (Objects.equals(context.getAttribute("actionBook"), "bookReady")) {
            bookTour();
            sendRedirect("/cruises?command=SuccessBookTour");
        } else {
            sendRedirect("/cruises?command=ErrorBookTour");
        }
    }

    private void bookTour() throws ServletException, IOException, IllegalFieldException {
        long userId = (long) request.getSession().getAttribute("userId");
        long linerId = (long) context.getAttribute("linerId");
        double price = (double) context.getAttribute("price");
        Date dateStart = (Date) context.getAttribute("dateStart");
        Date dateEnd = (Date) context.getAttribute("dateEnd");

        InputStream inputStream = null;
        Part filePart = request.getPart("passport");
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        tripService.create(Trip.createTrip(userId, linerId, false,
                price, dateStart, dateEnd, Trip.Status.PENDING, inputStream));
        context.setAttribute("actionBook", request.getParameter("bookFinish"));

    }

    private void setServletContext() {
        context.setAttribute("actionBook", "bookReady");
        context.setAttribute("linerId", Long.parseLong(request.getParameter("linerId")));
        context.setAttribute("price", Double.parseDouble(request.getParameter("price")));
        context.setAttribute("dateStart", Date.valueOf(request.getParameter("dateStart")));
        context.setAttribute("dateEnd", Date.valueOf(request.getParameter("dateEnd")));
    }
}
