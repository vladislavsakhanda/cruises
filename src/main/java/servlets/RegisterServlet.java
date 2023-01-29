package servlets;

import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlUserDAO;
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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    private static final String REGEX_NAME = "^[a-zA-ZА-Яа-яіІЇїєЄ]{3,}";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("register#doGet");

        req.setAttribute("name", req.getParameter("name"));
        req.setAttribute("surname", req.getParameter("surname"));
        req.setAttribute("email", req.getParameter("email"));
        req.getRequestDispatcher("/WEB-INF/pages/registration/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("register#doPost");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (name.length() == 0) {
            req.setAttribute("messageName", "label.lang.registration.register.messageNameRequired");
        } else if (name.length() < 3) {
            req.setAttribute("messageName", "label.lang.registration.register.messageNameContainMin");
        } else if (!Pattern.compile(REGEX_NAME).matcher(name).matches()) {
            req.setAttribute("messageName", "label.lang.registration.register.messageNameIncorrect");
        }

        if (surname.length() == 0) {
            req.setAttribute("messageSurname", "label.lang.registration.register.messageSurnameRequired");
        } else if (surname.length() < 3) {
            req.setAttribute("messageSurname", "label.lang.registration.register.messageSurnameContainMin");
        } else if (!Pattern.compile(REGEX_NAME).matcher(surname).matches()) {
            req.setAttribute("messageSurname", "label.lang.registration.register.messageSurnameIncorrect");
        }

        if (email.length() == 0) {
            req.setAttribute("messageEmail", "label.lang.registration.register.messageEmailRequired");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            req.setAttribute("messageEmail", "label.lang.registration.register.messageEmailFormatInvalid");
        } else if (userExist(email)) {
            req.setAttribute("messageEmail", "label.lang.registration.register.messageEmailExists");
        }

        if (password.length() == 0) {
            req.setAttribute("messagePassword", "label.lang.registration.register.messagePasswordRequired");
        } else if (password.length() < 6 || password.length() > 18) {
            req.setAttribute("messagePassword", "label.lang.registration.register.messagePasswordContainMin");
        }

        if (req.getAttribute("messageName") != null ||
                req.getAttribute("messageSurname") != null ||
                req.getAttribute("messageEmail") != null ||
                req.getAttribute("messagePassword") != null
        ) {
            doGet(req, resp);
        } else {
            try {
                register(name, surname, email, password);
                req.getRequestDispatcher("/WEB-INF/pages/registration/successRegistration.jsp").forward(req, resp);
            } catch (Exception e) {
                req.getRequestDispatcher("/WEB-INF/pages/registration/errorRegistration.jsp").forward(req, resp);
            }
        }
    }

    public void register(String name, String surname, String email, String password) throws Exception {
        try {
            new MySqlDAOFactory().getUserDao().create(User.createUser(name, surname, email, password));
        } catch (SQLException e) {
            e.printStackTrace();
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


