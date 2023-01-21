package com.my;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Test#doGet");

        Liner liner;
        try {
            MySqlLinerDAO.initDatabaseConnectionPool();
            MySqlLinerDAO mySqlLinerDAO = new MySqlDAOFactory().getLinerDAO();
            List<Liner> liners = mySqlLinerDAO.getAll();
            liner = liners.get(1);
            req.setAttribute("liners", liners);
            System.out.println(liners.get(0).getPrice_coefficient());
        } finally {
            MySqlLinerDAO.closeDatabaseConnectionPool();
        }

        String jsonData = liner.getRoute();
        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        HashMap hashMap = new Gson().fromJson(jsonObject, HashMap.class);
        System.out.println(hashMap);
        req.setAttribute("linersRoute", hashMap);

        getServletContext().getRequestDispatcher("/WEB-INF/pages/test/test.jsp").forward(req, resp);
    }
}