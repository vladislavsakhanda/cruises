package com.my;

import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("register#doPost");

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = User.createUser(name, surname, email, password);

        try {
            MySqlUserDAO.initDatabaseConnectionPool();
            new MySqlDAOFactory().getUserDao().create(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MySqlUserDAO.closeDatabaseConnectionPool();
        }

        response.sendRedirect("registrationDetails.jsp");
    }
}