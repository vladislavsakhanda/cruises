package db.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {
    private static DAOFactory instance;

    public static synchronized DAOFactory getDAOFactory () throws Exception {
        if (instance == null) {
            Class<?> clazz = Class.forName(DAOFactory.daoFactoryFCN);
            instance = (DAOFactory) clazz.getDeclaredConstructor().newInstance();
        }
        return instance;
    }

    protected DAOFactory () {

    }
    private static String daoFactoryFCN;

    public static void setDaoFactoryFCN (String daoFactoryFCN) {
        instance = null;
        DAOFactory.daoFactoryFCN = daoFactoryFCN;
    }

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

    public abstract UserDAO getUserDao();
    public abstract TripDAO getTripDAO();
    public abstract StaffDAO getStaffDAO();
    public abstract ScheduleDAO getScheduleDAO();
    public abstract RoleHasUserDAO getRoleHasUserDAO();
    public abstract RoleDAO getRoleDAO();
    public abstract LinerDAO getLinerDAO();
    public abstract DocumentDAO getDocumentDAO();
}
