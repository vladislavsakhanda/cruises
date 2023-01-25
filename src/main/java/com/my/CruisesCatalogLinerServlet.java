package com.my;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Liner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/cruisesCatalog/liner")
public class CruisesCatalogLinerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Test#doGet");

        req.setAttribute("id", req.getParameter("id"));

        getServletContext().getRequestDispatcher("/WEB-INF/pages/cruisesCatalog/cruisesCatalogLiner.jsp").forward(req, resp);
    }
}