package listeners;

import controller.commands.CruisesCatalogCommand;
import controller.commands.CruisesCatalogLinerCommand;
import db.dao.DataSource;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.LinerService;
import services.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.Timer;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CruisesControllerListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(CruisesCatalogLinerCommand.class);
    private volatile ScheduledExecutorService executor;
    final Runnable myRunnable = new Runnable() {
        public void run() {
            ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
            LinerService linerService = serviceFactory.getLinerService(MySqlLinerDAO.getInstance());
            try {
                linerService.deleteExpiredLiners();
            } catch (DBException e) {
                LOGGER.catching(e);
            }
        }
    };

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("CruisesControllerListener initialized");
        executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(myRunnable, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        final ScheduledExecutorService executor = this.executor;

        if (executor != null)
        {
            executor.shutdown();
            this.executor = null;
        }
    }
}