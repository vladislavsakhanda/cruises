package services;

import db.dao.RoleDAO;
import db.dao.UserDAO;
import db.dao.mysql.MySqlRoleDAO;
import db.dao.mysql.entity.Role;
import db.dao.mysql.entity.User;
import exeptions.IllegalFieldException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleService implements RoleDAO {
    private RoleDAO roleDAO;

    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getAll() throws IllegalFieldException {
        List<Role> roles = new ArrayList<>();
        try {
            roles = roleDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role read(long id) throws IllegalFieldException {
        if (id < 0)
            throw new IllegalFieldException("id must be greater than zero.");

        try {
            return roleDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Role role) {
        if (role == null)
            throw new NullPointerException("role is null.");

        try {
            roleDAO.create(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Role role) {
        if (role == null)
            throw new NullPointerException("role is null.");

        try {
            roleDAO.create(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Role role) {
        if (role == null)
            throw new NullPointerException("role is null.");

        try {
            roleDAO.create(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
