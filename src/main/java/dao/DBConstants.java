package dao;

public class DBConstants {
    private DBConstants() {
        // hide
    }

    // FIELDS
    public static final String F_USER_ID = "id";
    //
    // QUERIES
    public static final String GET_ALL_USERS = "SELECT * FROM user u ORDER BY u.name;";
    public static final String FIND_USERS_BY_PAID = "SELECT * FROM user u WHERE u.paid = ? ORDER BY u.full_name;";
    public static final String FIND_USERS_BY_LINER_ID = "SELECT * FROM user u WHERE u.liner_id = ? ORDER BY u.liner_id;";
}
