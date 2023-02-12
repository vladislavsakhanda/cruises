package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlDAOFactory extends DAOFactory {
    private MySqlUserDAO mySqlUserDAO;
    private MySqlTripDAO mySqlTripDAO;
    private MySqlStaffDAO mySqlStaffDAO;
    private MySqlRoleHasUserDAO mySqlRoleHasUserDAO;
    private MySqlRoleDAO mySqlRoleDAO;
    private MySqlLinerDAO mySqlLinerDAO;

    @Override
    public MySqlUserDAO getUserDao() {
        if (mySqlUserDAO == null) {
            mySqlUserDAO = new MySqlUserDAO();
        }
        return mySqlUserDAO;
    }

    @Override
    public MySqlTripDAO getTripDAO() {
        if (mySqlTripDAO == null) {
            mySqlTripDAO = new MySqlTripDAO();
        }
        return mySqlTripDAO;
    }

    @Override
    public MySqlStaffDAO getStaffDAO() {
        if (mySqlStaffDAO == null) {
            mySqlStaffDAO = new MySqlStaffDAO();
        }
        return mySqlStaffDAO;
    }

    @Override
    public MySqlRoleHasUserDAO getRoleHasUserDAO() {
        if (mySqlRoleHasUserDAO == null) {
            mySqlRoleHasUserDAO = new MySqlRoleHasUserDAO();
        }
        return mySqlRoleHasUserDAO;
    }

    @Override
    public MySqlRoleDAO getRoleDAO() {
        if (mySqlRoleDAO == null) {
            mySqlRoleDAO = new MySqlRoleDAO();
        }
        return mySqlRoleDAO;
    }

    @Override
    public MySqlLinerDAO getLinerDAO() {
        if (mySqlLinerDAO == null) {
            mySqlLinerDAO = new MySqlLinerDAO();
        }
        return mySqlLinerDAO;
    }

    public static void close(AutoCloseable stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
