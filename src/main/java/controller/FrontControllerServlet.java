package controller;

import controller.commands.CruisesCatalogCommand;
import controller.commands.ErrorPageCommand;
import controller.commands.UnknownCommand;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,  // 10 MB
        maxFileSize = 1024 * 1024 * 50,       // 50 MB
        maxRequestSize = 1024 * 1024 * 100)    // 100 MB
public class FrontControllerServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(CruisesCatalogCommand.class);
    private FrontCommand command;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("method", "GET");

        command = getCommand(request);
        command.init(getServletContext(), request, response);
        try {
            command.process();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.catching(e);
            response.sendRedirect("/cruises?command=ErrorPage");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("method", "POST");

        command = getCommand(request);
        command.init(getServletContext(), request, response);
        try {
            command.process();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.catching(e);
            response.sendRedirect("/cruises?command=ErrorPage");
        }
    }

    private FrontCommand getCommand(HttpServletRequest request) {
        try {
            Class type = Class.forName(String.format(
                    "controller.commands.%sCommand",
                    request.getParameter("command")));
            return (FrontCommand) type.asSubclass(FrontCommand.class).newInstance();
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }
}