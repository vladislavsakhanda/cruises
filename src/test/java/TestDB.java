import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import db.dao.DAOFactory;
import db.dao.UserDAO;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlStaffDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Staff;
import db.dao.mysql.entity.User;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestDB {
    public static DataSource getPooledConnectionDataSource() {
        String URL = "jdbc:mysql://localhost/cruise_company";
        String USER = "root";
        String PASSWORD = "1tfsS*oKM";
        String FULL_URL = URL + "?" + "user=" + USER + "&password=" + PASSWORD;
        MysqlDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setUrl(FULL_URL);
        return ds;
    }

    public static void connectPooledConnectionDataSource() {
        DataSource ds = getPooledConnectionDataSource();
        try (Connection con = ds.getConnection();) {
            System.out.println("Connected " + con.getClass());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAll() throws Exception {
        connectPooledConnectionDataSource();

        MySqlDAOFactory daoFactory = new MySqlDAOFactory();
        MySqlLinerDAO mySqlLinerDAO = daoFactory.getLinerDAO();
        MySqlStaffDAO mySqlStaffDAO = daoFactory.getStaffDAO();
//        mySqlStaffDAO.delete(new Staff(17));
//        System.out.println(mySqlStaffDAO.read(17));



//        System.out.println(mySqlStaffDAO.getAll());
    }
}















