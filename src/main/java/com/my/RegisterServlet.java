package com.my;

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
        System.out.println("register#doPost");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (name == null || surname == null && email == null && password == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/register.jsp").forward(req, resp);
        }

        if (name.length() == 0) {
            req.setAttribute("messageName", "First name is required.<br>");
        } else if (name.length() < 3) {
            req.setAttribute("messageName", "First name must contain at least 3 characters.<br>");
        } else if (!Pattern.compile(REGEX_NAME).matcher(name).matches()) {
            req.setAttribute("messageName", "First name is incorrect.<br>");
        }

        if (surname.length() == 0) {
            req.setAttribute("messageSurname", "Last name is required.<br>");
        } else if (surname.length() < 3) {
            req.setAttribute("messageSurname", "Last name must contain at least 3 characters.<br>");
        } else if (!Pattern.compile(REGEX_NAME).matcher(surname).matches()) {
            req.setAttribute("messageSurname", "Last name is incorrect.<br>");
        }

        if (email.length() == 0) {
            req.setAttribute("messageEmail", "Email is required.<br>");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            req.setAttribute("messageEmail", "Email format is invalid.<br>");
        } else if (userExist(email)) {
            req.setAttribute("messageEmail", "This email already exists.<br>");
        }

        if (password.length() == 0) {
            req.setAttribute("messagePassword", "Password is required.<br>");
        } else if (password.length() < 6 || password.length() > 18) {
            req.setAttribute("messagePassword",
                    "The password must contain at least 6 characters and no more than 18.<br>");
        }

        System.out.println("\n\n\nmessagePassword - " + req.getAttribute("messagePassword"));

        if (req.getAttribute("messageName") != null ||
                req.getAttribute("messageSurname") != null ||
                req.getAttribute("messageEmail") != null ||
                req.getAttribute("messagePassword") != null
        ) {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/register.jsp").forward(req, resp);
        } else {
            try {
                register(name, surname, email, password);
                getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/successRegistration.jsp").forward(req, resp);
            } catch (Exception e) {
                getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/errorRegistration.jsp").forward(req, resp);
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
        try {
            new MySqlUserDAO().read(requestEmail);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}


