package db.dao;

import db.dao.mysql.entity.User;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.SQLException;

public interface UserDAO extends ItemDAO<User> {
    public User read(String email) throws DBException, IllegalFieldException;
    public User read(String email, String password) throws DBException, IllegalFieldException;
}
