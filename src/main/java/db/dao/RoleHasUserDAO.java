package db.dao;

import db.dao.mysql.entity.RoleHasUser;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.SQLException;

public interface RoleHasUserDAO extends ItemDAO<RoleHasUser> {
    RoleHasUser read(long role_id, long user_id) throws SQLException, IllegalFieldException, DBException;
}
