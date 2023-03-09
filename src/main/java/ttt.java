import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.IllegalFieldException;
import services.LinerService;
import services.ServiceFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ttt {
    public static void main(String[] args) throws IllegalFieldException {
        ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
        LinerService linerService = serviceFactory.getLinerService(MySqlLinerDAO.getInstance());
        Liner liner = linerService.read(11);

        ArrayList<String> newRoute = new ArrayList<>();
        System.out.println();
        newRoute.add("aaa");
        newRoute.add("bbb");
        liner.setRoute(newRoute);

        System.out.println("route: " + liner.getRoute());
        System.out.println("route: " + linerService.read(11).getRoute());

        linerService.update(liner);




    }
}
