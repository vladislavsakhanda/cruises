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
import services.ServiceFactory;
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

    public LoginCommand() throws Exception {
    }

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

    public void doGet() throws ServletException, IOException {
        if (request.getSession().getAttribute("userEmail") == null) {
            request.setAttribute("email", request.getParameter("email"));
            forward("registration/login");
        } else {
            sendRedirect("/cruises?command=SuccessLogin");
        }
    }

    public void doPost()
            throws ServletException, NullPointerException, IOException, IllegalFieldException,
            NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
        UserService userService = serviceFactory.getUserService(MySqlUserDAO.getInstance());
        RoleHasUserService roleHasUserService = serviceFactory.getRoleHasUserService(MySqlRoleHasUserDAO.getInstance());
        User user = userService.read(email);

        if (email.length() == 0) {
            request.setAttribute("messageEmail", "label.lang.registration.messageEmailRequired");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            request.setAttribute("messageEmail", "label.lang.registration.messageEmailFormatInvalid");
        } else if (user == null) {
            request.setAttribute("messageEmail", "label.lang.registration.messageEmailNotExist");
        }

        if (password.length() == 0) {
            request.setAttribute("messagePassword", "label.lang.registration.messagePasswordRequired");
        }

        if (request.getAttribute("messageEmail") != null || request.getAttribute("messagePassword") != null) {
            doGet();
        } else {
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
            } else {
                request.setAttribute("messageErrorLogin", "label.lang.registration.messageErrorLogin");
            }

            doGet();
        }
    }
}
