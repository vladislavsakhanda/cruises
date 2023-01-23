package com.my;

import db.dao.PBKDF2;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login#doGet");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null && password == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/login.jsp").forward(req, resp);
        }

        if (email.length() == 0) {
            req.setAttribute("messageEmail", "Email is required.<br>");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            req.setAttribute("messageEmail", "Email format is invalid.<br>");
        } else if (!userExist(email)) {
            req.setAttribute("messageEmail", "This email does not exist.<br>");
        }

        if (password.length() == 0) {
            req.setAttribute("messagePassword", "Password is required.<br>");
        }

        if (req.getAttribute("messageEmail") != null || req.getAttribute("messagePassword") != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/login.jsp").forward(req, resp);
        } else {
            try {
                User user = new MySqlUserDAO().read(email);
                if (user != null && PBKDF2.validatePassword(password, user.getPassword())) {
                    HttpSession session = req.getSession();
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("userName", user.getName());
                    session.setAttribute("userSurname", user.getSurname());
                    session.setAttribute("userEmail", user.getEmail());
                    getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/successLogin.jsp").forward(req, resp);
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("messageErrorLogin", "Email or password invalid!");
                getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/login.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
