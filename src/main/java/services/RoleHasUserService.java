package services;

import db.dao.DataSource;
import db.dao.RoleHasUserDAO;
import db.dao.UserDAO;
import db.dao.mysql.entity.RoleHasUser;
import db.dao.mysql.entity.User;
import exeptions.IllegalFieldException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static db.dao.mysql.MySqlConstants.GET_ROLE_HAS_USER_BY;
import static db.dao.mysql.MySqlConstants.INSERT_ROLE_HAS_USER;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

public class RoleHasUserService implements RoleHasUserDAO {
    private RoleHasUserDAO roleHasUserDAO;

    public RoleHasUserService(RoleHasUserDAO roleHasUserDAO) {
        this.roleHasUserDAO = roleHasUserDAO;
    }
    @Override
    public List<RoleHasUser> getAll() throws IllegalFieldException {
        List<RoleHasUser> roleHasUsers = new ArrayList<>();
        try {
            roleHasUsers = roleHasUserDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleHasUsers;
    }

    @Override
    public RoleHasUser read(long id) throws IllegalFieldException {
        if (id < 0)
            throw new IllegalFieldException("id must be greater than zero.");

        try {
            return roleHasUserDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RoleHasUser read(long role_id, long user_id) throws SQLException, IllegalFieldException {
        if (role_id <= 0)
            throw new IllegalFieldException("id must be greater than zero.");
        if (user_id <= 0)
            throw new IllegalFieldException("id must be greater than zero.");

        try {
            return roleHasUserDAO.read(role_id, user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(RoleHasUser roleHasUser) {
        if (roleHasUser == null) {
            throw new NullPointerException();
        }

        try {
            roleHasUserDAO.create(roleHasUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(RoleHasUser roleHasUser) {
        if (roleHasUser == null) {
            throw new NullPointerException();
        }

        try {
            roleHasUserDAO.create(roleHasUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(RoleHasUser roleHasUser) {
        if (roleHasUser == null) {
            throw new NullPointerException();
        }

        try {
            roleHasUserDAO.create(roleHasUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
