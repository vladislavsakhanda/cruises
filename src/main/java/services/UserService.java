package services;

import db.dao.UserDAO;
import db.dao.mysql.entity.Entity;
import db.dao.mysql.entity.User;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static db.dao.mysql.entity.EntityConstants.REGEX_EMAIL;

public class UserService implements UserDAO {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAll() throws IllegalFieldException {
        List<User> users = new ArrayList<>();
        try {
            users = userDAO.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User read(long id) throws IllegalFieldException {
        if (id < 0) {
            throw new IllegalFieldException("id must be greater than zero.");
        }

        try {
            return userDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User read(String email) throws IllegalFieldException {
        if (email == null) {
            throw new IllegalFieldException("Email is null.");
        } else if (email.length() == 0) {
            throw new IllegalFieldException("Email cannot be zero.");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            throw new IllegalFieldException("Email is incorrect.");
        }

        try {
            return userDAO.read(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User read(String email, String password) throws IllegalFieldException {
        if (email == null) {
            throw new IllegalFieldException("Email is null.");
        } else if (email.length() == 0) {
            throw new IllegalFieldException("Email cannot be zero.");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            throw new IllegalFieldException("Email is incorrect.");
        }

        if (password == null) {
            throw new IllegalFieldException("Password is null.");
        } else if (password.length() < 6 || password.length() > 18) {
            throw new IllegalFieldException(
                    "The password must contain at least 6 characters and no more than 18.");
        }

        try {
            return userDAO.read(email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        try {
            userDAO.create(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        try {
            userDAO.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        try {
            userDAO.delete(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
