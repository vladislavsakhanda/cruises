package db.dao;

import db.dao.mysql.entity.Entity;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO<T extends Entity> {
    // Create entity in database
    void create(T entity) throws SQLException;

    public T read(long id) throws SQLException;

    public void update(T entity) throws SQLException;

    public void delete(T entity) throws SQLException;

    public List<T> getAll() throws SQLException;


}
