package controller.commands;

import controller.FrontCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

public class ErrorRegistrationCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(ErrorRegistrationCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        LOGGER.warn("register warn");
        forward("registration/errorRegistration");
    }

    private void doPost() throws ServletException, IOException {

    }
}
