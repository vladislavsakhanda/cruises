package db.dao.mysql;

import db.dao.DataSource;
import db.dao.RoleHasUserDAO;
import db.dao.mysql.entity.RoleHasUser;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static db.dao.DataSource.closeConnection;
import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

public class MySqlRoleHasUserDAO implements RoleHasUserDAO {
    @Override
    public List<RoleHasUser> getAll() {
        return null;
    }

    @Override
    public RoleHasUser read(long id) {
        return null;
    }

    @Override
    public RoleHasUser read(long role_id, long user_id) throws DBException, IllegalFieldException {
        RoleHasUser r = new RoleHasUser();
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_ROLE_HAS_USER_BY);

            int k = 0;
            stmt.setLong(++k, role_id);
            stmt.setLong(++k, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs != null && rs.next()) {
                    r.setRoleId(rs.getLong("role_id"));
                    r.setUserId(rs.getLong("user_id"));
                } else {
                    r = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return r;
    }

    @Override
    public void create(RoleHasUser roleHasUser) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(INSERT_ROLE_HAS_USER);
            int k = 0;
            stmt.setLong(++k, roleHasUser.getRoleId());
            stmt.setLong(++k, roleHasUser.getUserId());

            stmt.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(con);
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
    }

    @Override
    public void update(RoleHasUser entity) {

    }

    @Override
    public void delete(RoleHasUser entity) {

    }
}
