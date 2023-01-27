package servlets;

import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("userEmail") != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/successProfile.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Profile#doPost?" + req.getParameter("action"));

        if (Objects.equals(req.getParameter("action"), "removeRequest")) {
            try {
                new MySqlTripDAO().delete(new Trip(
                        Long.parseLong(req.getParameter("id")))
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (Objects.equals(req.getParameter("action"), "changePayment")) {
            try {
                Trip trip = new MySqlTripDAO().read(Long.parseLong(req.getParameter("id")));
                trip.setIs_paid(true);
                trip.setStatus(Trip.Status.CONFIRMED.getCode());
                new MySqlTripDAO().update(trip);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        resp.sendRedirect("profile");
    }
}
