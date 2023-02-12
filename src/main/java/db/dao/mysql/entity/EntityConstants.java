package db.dao.mysql.entity;

public class EntityConstants {
    private EntityConstants() {
        // hide
    }

    public static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    public static final String REGEX_NAME_AND_SURNAME = "^[a-zA-ZА-Яа-яіІЇїєЄ]{3,}";
}
