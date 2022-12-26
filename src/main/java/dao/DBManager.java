package dao;

import dao.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.DBConstants.*;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost/cruise_company";
    private static final String USER = "root";
    private static final String PASSWORD = "1tfsS*oKM";
    private static final String FULL_URL = URL + "?" + "user=" + USER + "&password=" + PASSWORD;

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(FULL_URL)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_USERS);
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }

    static String escapeForLike (String param) {
return param.replace("!", "!!").replace("%", "!%")
        .replace("_", "!_").replace("[", "![")
        .replace("]", "!]");
    }

    public List<User> findUserByPaid(boolean pattern) throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(FULL_URL);
             PreparedStatement stmt = con.prepareStatement(FIND_USERS_BY_PAID);
        ) {
            stmt.setBoolean(1, pattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }
//
    public List<User> findUserByLinerId(int pattern) throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(FULL_URL);
             PreparedStatement stmt = con.prepareStatement(FIND_USERS_BY_LINER_ID);
        ) {
            stmt.setInt(1, pattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }

    private static User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong(F_USER_ID));
        u.setFull_name(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setPaid(rs.getBoolean("paid"));
        u.setLiner_id(rs.getLong("liner_id"));
        return u;
    }

}
