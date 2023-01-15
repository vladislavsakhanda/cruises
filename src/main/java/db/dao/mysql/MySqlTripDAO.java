package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.TripDAO;
import db.dao.mysql.entity.Trip;

import java.util.List;

public class MySqlTripDAO implements TripDAO {
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

    @Override
    public List<Trip> getAll() {
        return null;
    }

    @Override
    public Trip read(long id) {
        return null;
    }

    @Override
    public void create(Trip entity) {

    }

    @Override
    public void update(Trip entity) {

    }

    @Override
    public void delete(Trip entity) {

    }
}
