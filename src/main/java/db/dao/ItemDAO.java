package db.dao;

import db.dao.mysql.entity.Entity;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

public interface ItemDAO<T extends Entity> {
    // Create entity in database
    void create(T entity) throws DBException, NoSuchAlgorithmException, InvalidKeySpecException, IllegalFieldException;

    public T read(long id) throws DBException, IllegalFieldException;

    public void update(T entity) throws DBException;

    public void delete(T entity) throws DBException;

    public List<T> getAll() throws DBException, IllegalFieldException;


}
