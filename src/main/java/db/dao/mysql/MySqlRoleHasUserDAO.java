package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.DataSource;
import db.dao.RoleHasUserDAO;
import db.dao.mysql.entity.RoleHasUser;
import db.dao.mysql.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

public class MySqlRoleHasUserDAO implements RoleHasUserDAO {
    @Override
    public List<RoleHasUser> getAll() {
        return null;
    }

    @Override
    public RoleHasUser read(long id) throws SQLException {
        return null;
    }

    public RoleHasUser read(RoleHasUser roleHasUser) throws SQLException {
        RoleHasUser r = new RoleHasUser();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ROLE_HAS_USER_BY)
        ) {
            int k = 0;
            stmt.setLong(++k, roleHasUser.getRole_id());
            stmt.setLong(++k, roleHasUser.getUser_id());
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                r.setRole_id(rs.getLong("role_id"));
                r.setUser_id(rs.getLong("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return r;
    }

    @Override
    public void create(RoleHasUser roleHasUser) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(INSERT_ROLE_HAS_USER);
            int k = 0;
            stmt.setLong(++k, roleHasUser.getRole_id());
            stmt.setLong(++k, roleHasUser.getUser_id());

            stmt.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(con);
            throw e;
        } finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public void update(RoleHasUser entity) {

    }

    @Override
    public void delete(RoleHasUser entity) {

    }
}
