import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.User;
import org.junit.Test;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static db.dao.PBKDF2.*;

public class TestDB {
    @Test
    public void testGetAll() throws Exception {
//       Liner liner1;
//        try {
//            MySqlLinerDAO.initDatabaseConnectionPool();
//            MySqlLinerDAO mySqlLinerDAO = new MySqlDAOFactory().getLinerDAO();
//            List<Liner> liners = mySqlLinerDAO.getAll();
//            liner1 = liners.get(1);
//            System.out.println(liners.get(0).getPrice_coefficient());
//        } finally {
//            MySqlLinerDAO.closeDatabaseConnectionPool();
//        }

//        String jsonData = liner1.getRoute();
//        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
//        HashMap yourHashMap = new Gson().fromJson(jsonObject, HashMap.class);
//        System.out.println(yourHashMap);
    }



}


















