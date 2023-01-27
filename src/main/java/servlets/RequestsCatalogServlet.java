package servlets;

import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/requestsCatalog")
public class RequestsCatalogServlet extends HttpServlet {

    public RequestsCatalogServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("requestsCatalog#doGet");

        req.setAttribute("pathProjectDirectory", getServletContext().getRealPath("/"));

        req.getRequestDispatcher("/WEB-INF/pages/admin/requestsCatalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Trip trip = new MySqlTripDAO().read(Long.parseLong(req.getParameter("trip_id")));
            trip.setStatus(Integer.parseInt(req.getParameter("status")));
            new MySqlTripDAO().update(trip);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("requestsCatalog");
    }
}
