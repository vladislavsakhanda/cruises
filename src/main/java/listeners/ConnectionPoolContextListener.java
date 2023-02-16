package listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.zaxxer.hikari.HikariDataSource;
import controller.commands.CruisesCatalogCommand;
import db.dao.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;



public class ConnectionPoolContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(CruisesCatalogCommand.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ConnectionPoolContextListener initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSource.closeHikariDataSource();
    }
}