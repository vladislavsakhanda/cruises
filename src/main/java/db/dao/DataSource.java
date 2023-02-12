package db.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import controller.commands.ProfileCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class);
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost/cruise_company" +
                "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false");
        config.setUsername("root");
        config.setPassword("1tfsS*oKM");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(34000);
        config.setMinimumIdle(18740000);
        config.setIdleTimeout(28740000);
        config.setMaxLifetime(28740000);
        ds = new HikariDataSource(config);
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
            LOGGER.trace("Can`t close hikari pool", sqlException);
        }
    }
}