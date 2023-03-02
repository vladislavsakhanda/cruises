package controller.commands;

import controller.FrontCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

public class ErrorBookTourCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(ErrorBookTourCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    void doGet() throws ServletException, IOException {
        LOGGER.warn("book tour warn");
        forward("cruisesCatalog/errorBookTour");
    }

    void doPost() throws ServletException, IOException {

    }
}
