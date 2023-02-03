package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;

public class BookTourCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(BookTourCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private boolean TripExist () {
        Trip trip = null;
        try {
            if (request.getParameter("liner_id") != null) {
                trip = new MySqlTripDAO().readByUserIdAndLinerId(
                        (Long) request.getSession().getAttribute("userId"),
                        Long.parseLong(request.getParameter("liner_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trip != null;
    }

    private void doGet() throws ServletException, IOException {
        if (TripExist()) {
            sendRedirect("/cruises?command=ErrorBookTour");
        } else {
            forward("cruisesCatalog/bookTour");
        }
    }

    private void doPost() throws ServletException, IOException {
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

    private void bookTour() throws ServletException, IOException {
        long user_id = (long) request.getSession().getAttribute("userId");
        long liner_id = (long) context.getAttribute("liner_id");
        boolean is_paid = false;
        double price = (double) context.getAttribute("price");
        Date date_start = (Date) context.getAttribute("date_start");
        Date date_end = (Date) context.getAttribute("date_end");
        int status = 0;

        InputStream inputStream = null;
        Part filePart = request.getPart("passport");
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        try {
            new MySqlTripDAO().create(Trip.createTrip(user_id, liner_id, is_paid,
                    price, date_start, date_end, inputStream, status));
            context.setAttribute("actionBook", request.getParameter("bookFinish"));
            LOGGER.info("user booked tour");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.trace("user can`t book tour");
        }
    }

    private void setServletContext() {
        context.setAttribute("actionBook", "bookReady");
        context.setAttribute("liner_id", Long.parseLong(request.getParameter("liner_id")));
        context.setAttribute("price", Double.parseDouble(request.getParameter("price")));
        context.setAttribute("date_start", Date.valueOf(request.getParameter("date_start")));
        context.setAttribute("date_end", Date.valueOf(request.getParameter("date_end")));
    }
}
