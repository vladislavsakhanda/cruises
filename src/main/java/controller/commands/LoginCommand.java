package controller.commands;

import controller.FrontCommand;
import db.dao.PBKDF2;
import db.dao.mysql.MySqlRoleHasUserDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Role;
import db.dao.mysql.entity.RoleHasUser;
import db.dao.mysql.entity.User;
import exeptions.IllegalFieldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.RoleHasUserService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import static db.dao.mysql.entity.EntityConstants.REGEX_EMAIL;

public class LoginCommand extends FrontCommand {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private final UserService userService = new UserService(new MySqlUserDAO());
    private final RoleHasUserService roleHasUserService = new RoleHasUserService(new MySqlRoleHasUserDAO());

    @Override
    public void process()
            throws ServletException, IOException, IllegalFieldException,
            SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        if (request.getSession().getAttribute("userEmail") == null) {
            request.setAttribute("email", request.getParameter("email"));
            forward("registration/login");
        } else {
            sendRedirect("/cruises?command=SuccessLogin");
        }
    }

    private void doPost() throws ServletException, NullPointerException, IOException, IllegalFieldException, NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email.length() == 0) {
            request.setAttribute("messageEmail", "label.lang.registration.messageEmailRequired");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            request.setAttribute("messageEmail", "label.lang.registration.messageEmailFormatInvalid");
        } else if (!userExist(email)) {
            request.setAttribute("messageEmail", "label.lang.registration.messageEmailNotExist");
        }

        if (password.length() == 0) {
            request.setAttribute("messagePassword", "label.lang.registration.messagePasswordRequired");
        }

        if (request.getAttribute("messageEmail") != null || request.getAttribute("messagePassword") != null) {
            doGet();
        } else {
            User user = userService.read(email);

            if (user != null && PBKDF2.validatePassword(password, user.getPassword())) {
                HttpSession session = request.getSession();
                RoleHasUser roleHasUser = roleHasUserService.
                        read(Role.Roles.ADMIN.getCode(), user.getId());

                if (roleHasUser != null) {
                    session.setAttribute("role", "admin");
                }

                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userSurname", user.getSurname());
                session.setAttribute("userEmail", user.getEmail());
                LOGGER.info("login success");
            } else {
                request.setAttribute("messageErrorLogin", "label.lang.registration.messageErrorLogin");
            }

            doGet();
        }

    }

    private boolean userExist(String requestEmail) throws IllegalFieldException {
        return userService.read(requestEmail) != null;
    }
}
