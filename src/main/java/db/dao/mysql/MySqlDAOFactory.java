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
            mySqlUserDAO = MySqlUserDAO.getInstance();
        }
        return mySqlUserDAO;
    }

    @Override
    public MySqlTripDAO getTripDAO() {
        if (mySqlTripDAO == null) {
            mySqlTripDAO = MySqlTripDAO.getInstance();
        }
        return mySqlTripDAO;
    }

    @Override
    public MySqlStaffDAO getStaffDAO() {
        if (mySqlStaffDAO == null) {
            mySqlStaffDAO = MySqlStaffDAO.getInstance();
        }
        return mySqlStaffDAO;
    }

    @Override
    public MySqlRoleHasUserDAO getRoleHasUserDAO() {
        if (mySqlRoleHasUserDAO == null) {
            mySqlRoleHasUserDAO = MySqlRoleHasUserDAO.getInstance();
        }
        return mySqlRoleHasUserDAO;
    }

    @Override
    public MySqlRoleDAO getRoleDAO() {
        if (mySqlRoleDAO == null) {
            mySqlRoleDAO = MySqlRoleDAO.getInstance();
        }
        return mySqlRoleDAO;
    }

    @Override
    public MySqlLinerDAO getLinerDAO() {
        if (mySqlLinerDAO == null) {
            mySqlLinerDAO = MySqlLinerDAO.getInstance();
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
