package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.DocumentDAO;
import db.dao.mysql.entity.Document;

import java.util.List;

public class MySqlDocumentDAO implements DocumentDAO {
    @Override
    public List<Document> getAll() {
        return null;
    }

    @Override
    public Document read(long id) {
        return null;
    }

    @Override
    public void create(Document entity) {

    }

    @Override
    public void update(Document entity) {

    }

    @Override
    public void delete(Document entity) {

    }
}
