package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.UserDAO;
import db.dao.mysql.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

public class MySqlUserDAO implements UserDAO {

    private static HikariDataSource dataSource;

    public static void initDatabaseConnectionPool() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost/cruise_company");
        dataSource.setUsername("root");
        dataSource.setPassword("1tfsS*oKM");
    }

    public static void closeDatabaseConnectionPool() {
        dataSource.close();
    }

    private static User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong(ID));
        u.setName(rs.getString("name"));
        u.setSurname(rs.getString("surname"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        return u;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
//        try (Connection con = DriverManager.getConnection(MySqlConstants.FULL_URL)) {
        try (Connection con = dataSource.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_USERS);
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return users;
    }

    @Override
    public User read(long id) throws SQLException {
        User u = new User();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_USER_By_ID)
        ) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                u = mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return u;
    }

    @Override
    public void create(User user) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);

            int k = 0;
            stmt.setString(++k, user.getName());
            stmt.setString(++k, user.getSurname());
            stmt.setString(++k, user.getEmail());
            stmt.setString(++k, user.getPassword());

            int count = stmt.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(con);
            throw e;
        } finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public void update(User user) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(UPDATE_USER);
            int k = 0;
            stmt.setString(++k, user.getName());
            stmt.setString(++k, user.getSurname());
            stmt.setString(++k, user.getEmail());
            stmt.setString(++k, user.getPassword());
            stmt.setLong(++k, user.getId());

            stmt.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(con);
            throw e;
        } finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(DELETE_USER);

            stmt.setLong(1, user.getId());
            stmt.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(con);
            throw e;
        } finally {
            close(stmt);
            close(con);
        }
    }


}
