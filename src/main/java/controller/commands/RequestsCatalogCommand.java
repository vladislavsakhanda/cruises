package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class RequestsCatalogCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(RequestsCatalogCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        request.setAttribute("pathProjectDirectory", context.getRealPath("/"));

        forward("admin/requestsCatalog");;
    }

    private void doPost() throws ServletException, IOException {
        try {
            Trip trip = new MySqlTripDAO().read(Long.parseLong(request.getParameter("trip_id")));
            trip.setStatus(Integer.parseInt(request.getParameter("status")));
            new MySqlTripDAO().update(trip);
            LOGGER.info("trip status update success");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.trace("trip status update error");
        }

        forward("admin/requestsCatalog");
    }
}
