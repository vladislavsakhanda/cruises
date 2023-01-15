package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.RoleDAO;
import db.dao.mysql.entity.Role;

import java.sql.SQLException;
import java.util.List;

public class MySqlRoleDAO implements RoleDAO {
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
    public List<Role> getAll() throws SQLException {
        return null;
    }

    @Override
    public Role read(long id) throws SQLException {
        return null;
    }

    @Override
    public void create(Role entity) throws SQLException {

    }

    @Override
    public void update(Role entity) throws SQLException {

    }

    @Override
    public void delete(Role entity) throws SQLException {

    }
}
