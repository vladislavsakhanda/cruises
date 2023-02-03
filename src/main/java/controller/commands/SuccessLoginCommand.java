package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class SuccessLoginCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(SuccessLoginCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        LOGGER.info("login success");
        forward("registration/successLogin");
    }

    private void doPost() throws ServletException, IOException {
        LOGGER.info("login success");
        forward("registration/successLogin");
    }
}
