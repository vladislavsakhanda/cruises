package db.dao.mysql;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import db.dao.mysql.MySqlConstants;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnectionFactory {
    private static HikariDataSource dataSource;
    public static void initDatabaseConnectionPool() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost/cruise_company");
        dataSource.setUsername("root");
        dataSource.setPassword("1tfsS*oKM");
    }

    public static void closeDatabaseConnectionPool() {
        dataSource.close();
    }

//    public static DataSource getPooledConnectionDataSource() {
//        String URL = "jdbc:mysql://localhost/cruise_company";
//        String USER = "root";
//        String PASSWORD = "1tfsS*oKM";
//        String FULL_URL = URL + "?" + "user=" + USER + "&password=" + PASSWORD;
//        MysqlDataSource ds = new MysqlConnectionPoolDataSource();
//        ds.setUrl(FULL_URL);
//        return ds;
//    }
//
//    public static void connectPooledConnectionDataSource() {
//        DataSource ds = getPooledConnectionDataSource();
//        try (Connection con = ds.getConnection();) {
//            System.out.println("Connected " + con.getClass());
//            System.out.printf(con.getMetaData().getURL());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}