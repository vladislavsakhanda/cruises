package db.dao.mysql.entity;

import exeptions.IllegalFieldException;

import java.io.Serializable;
import java.util.regex.Pattern;

import static db.dao.mysql.entity.EntityConstants.REGEX_NAME_AND_SURNAME;

public class Entity implements Serializable {

    private long id;

    public Entity() {

    }

    public Entity(long id) throws IllegalFieldException {
        if (id <= 0) {
            throw new IllegalFieldException("Id must be greater than zero.");
        }
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) throws IllegalFieldException {
        if (id < 0) {
            throw new IllegalFieldException("Id must be greater than zero.");
        }
        this.id = id;
    }
}
