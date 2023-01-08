import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import db.dao.DAOFactory;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlStaffDAO;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDB {


    @Test
    public void testGetAll() throws Exception {
        DAOFactory.connectPooledConnectionDataSource();

        MySqlDAOFactory daoFactory = new MySqlDAOFactory();
        MySqlLinerDAO mySqlLinerDAO = daoFactory.getLinerDAO();
        MySqlStaffDAO mySqlStaffDAO = daoFactory.getStaffDAO();
        System.out.println(mySqlStaffDAO.getAll());
    }
}















