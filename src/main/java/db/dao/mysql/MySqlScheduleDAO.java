package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.ScheduleDAO;
import db.dao.mysql.entity.Schedule;

import java.util.List;

public class MySqlScheduleDAO implements ScheduleDAO {
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
