package db.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import controller.commands.CruisesCatalogCommand;
import controller.commands.ProfileCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static List<HikariDataSource> hikariDataSources = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class);
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/cruise_company" +
                "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false");
        config.setUsername("root");
        config.setPassword("root");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(20000);
        config.setMinimumIdle(18740000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(600000);

        config.setAutoCommit(false);
//        config.setTransactionIsolation("TRANSACTION_REPEATABLE_READ");
        config.setLeakDetectionThreshold(7500);

        ds = new HikariDataSource(config);
    }

    public static void closeHikariDataSource() {
        if (ds != null) {
            ds.close();
        }
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void closeConnection(Connection dataSource) {
        try {
            if (dataSource != null) {
                dataSource.close();
            }
        } catch (SQLException sqlException) {
            LOGGER.catching(sqlException);
        }
    }
}