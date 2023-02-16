package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.User;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.UserService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import static db.dao.mysql.entity.EntityConstants.REGEX_EMAIL;
import static db.dao.mysql.entity.EntityConstants.REGEX_NAME_AND_SURNAME;

public class RegisterCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);
    private final UserService userService = new UserService(new MySqlUserDAO());

    @Override
    public void process() throws ServletException, IOException, IllegalFieldException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        forward("registration/register");
    }

    private void doPost() throws ServletException, IOException, IllegalFieldException {
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
        } else if (!Pattern.compile(REGEX_NAME_AND_SURNAME).matcher(name).matches()) {
            request.setAttribute("messageName", "label.lang.registration.register.messageNameIncorrect");
        }

        if (surname.length() == 0) {
            request.setAttribute("messageSurname", "label.lang.registration.register.messageSurnameRequired");
        } else if (surname.length() < 3) {
            request.setAttribute("messageSurname", "label.lang.registration.register.messageSurnameContainMin");
        } else if (!Pattern.compile(REGEX_NAME_AND_SURNAME).matcher(surname).matches()) {
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
            register(name, surname, email, password);
            sendRedirect("/cruises?command=SuccessRegistration");
        }
    }


    public void register(String name, String surname, String email, String password) throws IllegalFieldException {
        userService.create(User.createUser(name, surname, email, password));
    }

    private boolean userExist(String requestEmail) throws IllegalFieldException {
        return userService.read(requestEmail) != null;
    }
}