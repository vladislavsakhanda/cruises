package controller.commands;

import controller.FrontCommand;
import db.dao.PBKDF2;
import db.dao.mysql.MySqlRoleHasUserDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Role;
import db.dao.mysql.entity.RoleHasUser;
import db.dao.mysql.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

public class LoginCommand extends FrontCommand {
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";

    @Override
    public void process() throws ServletException, IOException {
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

    private void doPost() throws ServletException, IOException {
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
            try {
                User user = new MySqlUserDAO().read(email);
                if (user != null && PBKDF2.validatePassword(password, user.getPassword())) {
                    HttpSession session = request.getSession();
                    RoleHasUser roleHasUser = new MySqlRoleHasUserDAO().read(new RoleHasUser(Role.Roles.ADMIN.getCode(), user.getId()));

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
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("messageErrorLogin", "label.lang.registration.messageErrorLogin");
            }

            doGet();
        }

    }

    private boolean userExist(String requestEmail) {
        User user = null;

        try {
            user = new MySqlUserDAO().read(requestEmail);
        } catch (Exception ignored) {

        }

        return user != null;
    }
}
