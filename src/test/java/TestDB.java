import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlUserDAO;
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

import static db.dao.PBKDF2.*;

public class TestDB {
    @Test
    public void testGetAll() throws Exception {
//        User user = null;
//        try {
//            MySqlUserDAO.initDatabaseConnectionPool();
//            new MySqlDAOFactory().getUserDao().create(User.createUser("Vlad", "Sakhanda", "vladSakhanda@ukr.net",
//                    "root"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            MySqlUserDAO.closeDatabaseConnectionPool();
//        }
    }

    @Test
    public void testHash() throws Exception {
        boolean matched = validatePassword("111111", "1000:d3f32ac66dfca6025951a3828a64e2c0:996e3665303c6cebe1a1afb7c3f6736ad05a68f39ff6ae8a0c2dd3c6c3e314eedcbf8fe66712a98e0b789198a877374294f92621b1dbb6598a651e1d1a27891f");
        System.out.println(matched);
    }


}


















