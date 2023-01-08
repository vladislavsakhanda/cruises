package com.my;

import db.dao.DAOFactory;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
