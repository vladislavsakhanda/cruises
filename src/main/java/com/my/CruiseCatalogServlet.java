package com.my;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlTripDAO;
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

@WebServlet("/cruisesCatalog")
public class CruiseCatalogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Test#doGet");

        Liner liner = null;
        try {
            MySqlLinerDAO mySqlLinerDAO = new MySqlDAOFactory().getLinerDAO();
            List<Liner> liners = mySqlLinerDAO.getAll();
            liner = liners.get(0);
            req.setAttribute("liners", liners);
            req.setAttribute("liner", liner);
            System.out.println(liners.get(0).getPrice_coefficient());

            int linerCapacity = liners.get(0).getCapacity();
            int busyLinerPlaces = new MySqlTripDAO().getAllByLiner(new Liner(liners.get(0).getId())).size();
            int freePlaces =
                    linerCapacity - busyLinerPlaces;
            System.out.println("linerCapacity - " + linerCapacity);
            System.out.println("busyLinerPlaces - " + busyLinerPlaces);
            System.out.println("free - " + freePlaces);
            req.setAttribute("freePlaces", freePlaces);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonData = liner.getRoute();
        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        HashMap hashMap = new Gson().fromJson(jsonObject, HashMap.class);
        System.out.println(hashMap);
        req.setAttribute("linersRoute", hashMap);

        getServletContext().getRequestDispatcher("/WEB-INF/pages/test/cruisesCatalog.jsp").forward(req, resp);
    }
}