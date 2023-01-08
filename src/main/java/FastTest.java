import db.dao.DAOFactory;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class FastTest {
    public static void main(String[] args) {
        DAOFactory.connectPooledConnectionDataSource();
        MySqlDAOFactory daoFactory = new MySqlDAOFactory();
        MySqlUserDAO mySqlUserDAO = daoFactory.getUserDao();
        List<User> users = mySqlUserDAO.getAll();
        System.out.println(users);
    }
//
    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }

}
