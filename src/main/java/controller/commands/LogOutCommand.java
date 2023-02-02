package controller.commands;

import controller.FrontCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutCommand extends FrontCommand {
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
        String language = String.valueOf(session.getAttribute("lang"));
        session.invalidate();
        request.getSession().setAttribute("lang", language);

        forward("home");
    }

    private void doPost() throws ServletException, IOException {

    }
}
