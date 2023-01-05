package db.dao.mysql;

public class MySqlConstants {
    private MySqlConstants() {
        // hide
    }

    // Database Constants
    public static final String URL = "jdbc:mysql://localhost/cruise_company";
    public static final String USER = "root";
    public static final String PASSWORD = "1tfsS*oKM";
    public static final String FULL_URL = URL + "?" + "user=" + USER + "&password=" + PASSWORD;

    // FIELDS
    public static final String ID = "id";

    // QUERIES
    public static final String GET_ALL_USERS = "SELECT * FROM user u ORDER BY u.name;";
    public static final String GET_ALL_USERS_By_Name = "SELECT * FROM user u ORDER BY u.name;";
    public static final String GET_ALL_USERS_By_Surname = "SELECT * FROM user u ORDER BY u.surname;";
    public static final String GET_USER_By_ID = "SELECT * FROM user WHERE user.id = ?;";
    public static final String INSERT_USER = "INSERT INTO user (name, surname, email, password) VALUES (?, ?, ?, ?);";
    public static final String FIND_USERS_BY_PAID = "SELECT * FROM user u WHERE u.paid = ? ORDER BY u.full_name;";
    public static final String FIND_USERS_BY_LINER_ID = "SELECT * FROM user u WHERE u.liner_id = ? ORDER BY u.liner_id;";
    public static final String UPDATE_USER = "UPDATE user SET name = ?, surname = ?, email = ?, password = ? WHERE id = ?;";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = ?;";

    public static final String GET_ALL_TRIPS = "SELECT * FROM user u ORDER BY u.name;";


    public static final String GET_ALL_LINERS = "SELECT * FROM liner l ORDER BY l.price_coefficient;";
    public static final String INSERT_LINER = "INSERT INTO liner (name, description, capacity, route, price_coefficient) VALUES (?, ?, ?, ?);";
    public static final String GET_LINER_By_ID = "SELECT * FROM liner WHERE liner.id = ?;";

    public static final String GET_ALL_STAFF = "SELECT * FROM staff s ORDER BY s.specialization;";
    public static final String INSERT_STAFF = "INSERT INTO staff (name, surname, specialization, liner_id) VALUES (?, ?, ?, ?);";
    public static final String GET_STAFF_By_ID = "SELECT * FROM staff WHERE staff.id = ?;";
    public static final String DELETE_STAFF = "DELETE FROM staff WHERE id = ?;";
    public static final String UPDATE_STAFF = "UPDATE staff SET name = ?, surname = ?, specialization = ?, liner_id = ? WHERE id = ?;";
}
