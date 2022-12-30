package dao;

import model.Model;

import java.util.List;

public interface ItemDAO<T extends Model> {
    public List<T> getAll();

    public T getById(long id);

    public void add(T entity);

    public void update(T entity);

    public void delete(T entity);
}
