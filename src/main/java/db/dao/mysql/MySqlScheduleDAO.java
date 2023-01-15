package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.ScheduleDAO;
import db.dao.mysql.entity.Schedule;

import java.util.List;

public class MySqlScheduleDAO implements ScheduleDAO {
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
    public List<Schedule> getAll() {
        return null;
    }

    @Override
    public Schedule read(long id) {
        return null;
    }

    @Override
    public void create(Schedule entity) {

    }

    @Override
    public void update(Schedule entity) {

    }

    @Override
    public void delete(Schedule entity) {

    }
}
