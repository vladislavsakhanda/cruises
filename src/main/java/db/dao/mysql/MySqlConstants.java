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
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE user.id = ?;";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?;";
    public static final String GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email=? AND password=?;";
    public static final String INSERT_USER = "INSERT INTO user (name, surname, email, password) VALUES (?, ?, ?, ?);";
    public static final String FIND_USERS_BY_PAID = "SELECT * FROM user u WHERE u.paid = ? ORDER BY u.full_name;";
    public static final String FIND_USERS_BY_LINER_ID = "SELECT * FROM user u WHERE u.liner_id = ? ORDER BY u.liner_id;";
    public static final String UPDATE_USER = "UPDATE user SET name = ?, surname = ?, email = ?, password = ? WHERE id = ?;";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = ?;";

    public static final String GET_ALL_TRIPS = "SELECT * FROM trip t ORDER BY t.id;";
    public static final String GET_ALL_TRIPS_BY_LINER = "SELECT * FROM trip t WHERE t.liner_id = ? ORDER BY t.is_paid;";
    public static final String GET_ALL_TRIPS_BY_USER_ID = "SELECT * FROM trip t WHERE t.user_id = ? ORDER BY t.is_paid;";
    public static final String UPDATE_IS_PAID_IN_TRIP = "UPDATE trip SET is_paid = ? WHERE id = ?";
    public static final String GET_TRIP_BY_ID = "SELECT * FROM trip WHERE trip.id = ?;";
    public static final String GET_TRIP_BY_USER_ID = "SELECT * FROM trip WHERE trip.user_id = ?;";
    public static final String GET_TRIP_BY_USER_ID_AND_LINER_ID = "SELECT * FROM trip t WHERE t.user_id = ? && t.liner_id = ?;";
    public static final String DELETE_TRIP = "DELETE FROM trip WHERE id = ?;";
    public static final String INSERT_TRIP = "INSERT INTO trip " +
            "(user_id, liner_id, is_paid, price, date_start, date_end, status, passport)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String GET_ALL_LINERS = "SELECT * FROM liner l ORDER BY l.price_coefficient;";
    public static final String GET_ALL_LINERS_PAGINATION = "SELECT SQL_CALC_FOUND_ROWS * FROM liner l ORDER BY l.date_start LIMIT ?, ?;";
    public static final String INSERT_LINER = "INSERT INTO liner (name, description, capacity, route, price_coefficient) VALUES (?, ?, ?, ?);";
    public static final String GET_LINER_BY_ID = "SELECT * FROM liner WHERE liner.id = ?;";

    public static final String GET_ALL_STAFF = "SELECT * FROM staff s ORDER BY s.specialization;";
    public static final String INSERT_STAFF = "INSERT INTO staff (name, surname, specialization, liner_id) VALUES (?, ?, ?, ?);";
    public static final String GET_STAFF_BY_ID = "SELECT * FROM staff WHERE staff.id = ?;";
    public static final String DELETE_STAFF = "DELETE FROM staff WHERE id = ?;";
    public static final String UPDATE_STAFF = "UPDATE staff SET name = ?, surname = ?, specialization = ?, liner_id = ? WHERE id = ?;";

    public static final String GET_ROLE_HAS_USER_BY = "SELECT * FROM role_has_user r WHERE r.role_id = ? and r.user_id = ?;";
    public static final String INSERT_ROLE_HAS_USER = "INSERT INTO role_has_user (role_id, user_id) VALUES (?, ?);";

}
