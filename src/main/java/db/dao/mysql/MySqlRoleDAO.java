package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.RoleDAO;
import db.dao.mysql.entity.Role;

import java.sql.SQLException;
import java.util.List;

public class MySqlRoleDAO implements RoleDAO {

    @Override
    public List<Role> getAll() {
        return null;
    }

    @Override
    public Role read(long id)  {
        return null;
    }

    @Override
    public void create(Role role)  {

    }

    @Override
    public void update(Role role)  {

    }

    @Override
    public void delete(Role role)  {

    }
}
