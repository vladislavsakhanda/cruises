package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.RoleHasUserDAO;
import db.dao.mysql.entity.RoleHasUser;

import java.util.List;

public class MySqlRoleHasUserDAO implements RoleHasUserDAO {
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
    public List<RoleHasUser> getAll() {
        return null;
    }

    @Override
    public RoleHasUser read(long id) {
        return null;
    }

    @Override
    public void create(RoleHasUser entity) {

    }

    @Override
    public void update(RoleHasUser entity) {

    }

    @Override
    public void delete(RoleHasUser entity) {

    }
}
