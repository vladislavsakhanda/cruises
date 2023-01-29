package servlets;

import db.dao.PBKDF2;
import db.dao.mysql.MySqlRoleHasUserDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Role;
import db.dao.mysql.entity.RoleHasUser;
import db.dao.mysql.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login#doGet");

        if (req.getSession().getAttribute("userEmail") == null) {
            req.setAttribute("email", req.getParameter("email"));
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/login.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/successLogin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login#doPost");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email.length() == 0) {
            req.setAttribute("messageEmail", "label.lang.registration.messageEmailRequired");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            req.setAttribute("messageEmail", "label.lang.registration.messageEmailFormatInvalid");
        } else if (!userExist(email)) {
            req.setAttribute("messageEmail", "label.lang.registration.messageEmailNotExist");
        }

        if (password.length() == 0) {
            req.setAttribute("messagePassword", "label.lang.registration.messagePasswordRequired");
        }

        if (req.getAttribute("messageEmail") != null || req.getAttribute("messagePassword") != null) {
            doGet(req, resp);
        } else {
            try {
                User user = new MySqlUserDAO().read(email);
                if (user != null && PBKDF2.validatePassword(password, user.getPassword())) {
                    HttpSession session = req.getSession();
                    RoleHasUser roleHasUser = new MySqlRoleHasUserDAO().read(new RoleHasUser(Role.Roles.ADMIN.getCode(), user.getId()));

                    if (roleHasUser != null) {
                        session.setAttribute("role", "admin");
                    }

                    session.setAttribute("userId", user.getId());
                    session.setAttribute("userName", user.getName());
                    session.setAttribute("userSurname", user.getSurname());
                    session.setAttribute("userEmail", user.getEmail());
                } else {
                    req.setAttribute("messageErrorLogin", "label.lang.registration.messageErrorLogin");
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("messageErrorLogin", "label.lang.registration.messageErrorLogin");
            }

            doGet(req, resp);
        }
    }

    private boolean userExist(String requestEmail) {
        User user = null;

        try {
            user = new MySqlUserDAO().read(requestEmail);
        } catch (SQLException ignored) {

        }

        return user != null;
    }
}
