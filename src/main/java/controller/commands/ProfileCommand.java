package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ProfileCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
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

    private void doPost() throws ServletException, IOException {
        if (Objects.equals(request.getParameter("action"), "removeRequest")) {
            try {
                System.out.println(request.getParameter("id"));
                new MySqlTripDAO().delete(new Trip(Long.parseLong(request.getParameter("id"))));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            forward("registration/successProfile");
        }

        if (Objects.equals(request.getParameter("action"), "changePayment")) {
            try {
                Trip trip = new MySqlTripDAO().read(Long.parseLong(request.getParameter("id")));
                trip.setIs_paid(true);
                trip.setStatus(Trip.Status.CONFIRMED.getCode());
                new MySqlTripDAO().update(trip);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}