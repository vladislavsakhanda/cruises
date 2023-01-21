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

@WebServlet("/test2")
public class Test2Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Test#doGet");



        getServletContext().getRequestDispatcher("/WEB-INF/pages/test/test2.jsp").forward(req, resp);
    }
}