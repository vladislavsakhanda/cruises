package com.my.tlds;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Trip;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class CruisesTLD {
    public static Trip getTripByUserIdAndLinerId(long user_id, long liner_id) throws SQLException {
        return new MySqlTripDAO().readByUserIdAndLinerId(user_id, liner_id);
    }

    public static List<Trip> getAllTrip() throws SQLException {
        return new MySqlTripDAO().getAll();
    }

    public static List<Trip> getAllTripByUserId(long id) throws SQLException {
        return new MySqlTripDAO().getAllByUserId(id);
    }

    public static int getLinerFreePlaces(Liner liner) {
        int linerCapacity = liner.getCapacity();
        int busyLinerPlaces = 0;
        try {
            busyLinerPlaces = new MySqlTripDAO().getAllByLiner(new Liner(liner.getId())).size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linerCapacity - busyLinerPlaces;
    }

    public static Liner getLinerById(long id) {
        Liner liner = null;
        try {
            liner = new MySqlLinerDAO().read(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liner;
    }

    public static HashMap getLinerRouteInMap(Liner liner) {
        String jsonData = liner.getRoute();
        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        HashMap route = new Gson().fromJson(jsonObject, HashMap.class);

        return route;
    }

    public static double countPriceLiner(Liner liner) {
        String jsonData = liner.getRoute();
        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        HashMap route = new Gson().fromJson(jsonObject, HashMap.class);

        int amountRoutes = 0;
        for (Object s : route.keySet()) {
            amountRoutes++;
        }
        return round(amountRoutes * liner.getPrice_coefficient(), 2);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
