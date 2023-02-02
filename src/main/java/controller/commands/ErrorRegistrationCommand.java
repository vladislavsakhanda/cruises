package controller.commands;

import controller.FrontCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class ErrorRegistrationCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        forward("registration/errorRegistration");
    }

    private void doPost() throws ServletException, IOException {

    }
}