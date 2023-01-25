package com.my;

import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile/pay")
public class ProfilePayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            new MySqlTripDAO().updateIsPaid(true, Long.parseLong(req.getParameter("id")));
            new MySqlTripDAO().updateIsStatus(Trip.Status.CONFIRMED, Long.parseLong(req.getParameter("id")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/successProfile.jsp").
                forward(req, resp);
    }
}
