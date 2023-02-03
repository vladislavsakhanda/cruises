package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegisterCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    private static final String REGEX_NAME = "^[a-zA-ZА-Яа-яіІЇїєЄ]{3,}";

    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        forward("registration/register");
    }

    private void doPost() throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        request.setAttribute("name", name);
        request.setAttribute("surname", surname);
        request.setAttribute("email", email);

        if (name.length() == 0) {
            request.setAttribute("messageName", "label.lang.registration.register.messageNameRequired");
        } else if (name.length() < 3) {
            request.setAttribute("messageName", "label.lang.registration.register.messageNameContainMin");
        } else if (!Pattern.compile(REGEX_NAME).matcher(name).matches()) {
            request.setAttribute("messageName", "label.lang.registration.register.messageNameIncorrect");
        }

        if (surname.length() == 0) {
            request.setAttribute("messageSurname", "label.lang.registration.register.messageSurnameRequired");
        } else if (surname.length() < 3) {
            request.setAttribute("messageSurname", "label.lang.registration.register.messageSurnameContainMin");
        } else if (!Pattern.compile(REGEX_NAME).matcher(surname).matches()) {
            request.setAttribute("messageSurname", "label.lang.registration.register.messageSurnameIncorrect");
        }

        if (email.length() == 0) {
            request.setAttribute("messageEmail", "label.lang.registration.register.messageEmailRequired");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            request.setAttribute("messageEmail", "label.lang.registration.register.messageEmailFormatInvalid");
        } else if (userExist(email)) {
            request.setAttribute("messageEmail", "label.lang.registration.register.messageEmailExists");
        }

        if (password.length() == 0) {
            request.setAttribute("messagePassword", "label.lang.registration.register.messagePasswordRequired");
        } else if (password.length() < 6 || password.length() > 18) {
            request.setAttribute("messagePassword", "label.lang.registration.register.messagePasswordContainMin");
        }

        if (request.getAttribute("messageName") != null ||
                request.getAttribute("messageSurname") != null ||
                request.getAttribute("messageEmail") != null ||
                request.getAttribute("messagePassword") != null
        ) {
            forward("registration/register");
        } else {
            try {
                register(name, surname, email, password);
                LOGGER.info("register success");
                sendRedirect("/cruises?command=SuccessRegistration");
            } catch (Exception e) {
                LOGGER.trace("register error");
                sendRedirect("/cruises?command=ErrorRegistration");
            }
        }
    }


    public void register(String name, String surname, String email, String password) throws Exception {
        try {
            new MySqlDAOFactory().getUserDao().create(User.createUser(name, surname, email, password));
            LOGGER.info("User registered");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("User did not register");
        }
    }

    private boolean userExist(String requestEmail) {
        User user = null;

        try {
            user = new MySqlUserDAO().read(requestEmail);
            LOGGER.info("User exists");
        } catch (SQLException ignored) {

        }

        return user != null;
    }
}