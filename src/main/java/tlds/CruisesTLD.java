package tlds;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.commands.RegisterCommand;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Trip;
import db.dao.mysql.entity.User;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.LinerService;
import services.TripService;
import services.UserService;

import java.io.*;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class CruisesTLD {
    private static final UserService userService = new UserService(new MySqlUserDAO());
    private static final TripService tripService = new TripService(new MySqlTripDAO());
    private static final LinerService linerService = new LinerService(new MySqlLinerDAO());

    private static final Logger LOGGER = LogManager.getLogger(CruisesTLD.class);

    public static User getUserByUserId(long user_id) {
        User user = null;
        try {
            user = userService.read(user_id);
        } catch (IllegalFieldException e) {
            LOGGER.trace("IllegalFieldException", e);
        }
        return user;
    }

    public static String getBlobFromInputStream(Trip trip) throws IOException {
        InputStream inputStream = trip.getPassport();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] imageBytes = outputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        inputStream.close();
        outputStream.close();

        return base64Image;
    }

    public static void getAndWriteTempImageOfPassport(Trip trip, java.lang.String pathProjectDirectory) {
        String pathToStore = null;
        try {
            pathToStore = pathProjectDirectory + "images\\" + trip.getId() + "_temp.jpg";

            File image = new File(pathToStore);
            FileOutputStream fos = new FileOutputStream(image, false);
            byte[] bytes = IOUtils.toByteArray(trip.getPassport());
            fos.write(bytes);
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Trip getTripByUserIdAndLinerId(long user_id, long liner_id) throws SQLException, IllegalFieldException {
        return tripService.readByUserIdAndLinerId(user_id, liner_id);
    }

    public static List<Trip> getAllTrip() throws SQLException, DBException, IllegalFieldException {
        return new MySqlTripDAO().getAll();
    }

    public static List<Trip> getAllTripByUserId(long id) throws SQLException, IllegalFieldException {
        return tripService.getAllByUserId(id);
    }

    public static int getLinerFreePlaces(Liner liner) {
        int linerCapacity = liner.getCapacity();
        int busyLinerPlaces = 0;
        try {
            busyLinerPlaces = tripService.getAllByLiner(new Liner(liner.getId())).size();
        } catch (IllegalFieldException e) {
            e.printStackTrace();
        }
        return linerCapacity - busyLinerPlaces;
    }

    public static Liner getLinerById(long id) throws IllegalFieldException {
        Liner liner = null;
        liner = linerService.read(id);
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
        return round(amountRoutes * liner.getPriceCoefficient(), 2);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
