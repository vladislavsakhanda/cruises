package db.dao.mysql;

import db.dao.DataSource;
import db.dao.UserDAO;
import db.dao.mysql.entity.User;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.DataSource.closeConnection;
import static db.dao.PBKDF2.generatePasswordHash;
import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

public class MySqlUserDAO implements UserDAO {
    private static User mapUser(ResultSet rs) throws SQLException, IllegalFieldException {
        User u = new User();
        u.setId(rs.getLong(ID));
        u.setName(rs.getString("name"));
        u.setSurname(rs.getString("surname"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        return u;
    }

    @Override
    public List<User> getAll() throws DBException, IllegalFieldException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DataSource.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_ALL_USERS);
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            closeConnection(con);
        }
        return users;
    }

    @Override
    public User read(long id) throws DBException, IllegalFieldException {
        User u = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_USER_BY_ID);

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                u = mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return u;
    }

    @Override
    public User read(String email) throws DBException, IllegalFieldException {
//        User u = null;
//        Connection con = null;
//        PreparedStatement stmt = null;
//        try {
//            con = DataSource.getConnection();
//            stmt = con.prepareStatement(GET_USER_BY_EMAIL);
//
//            int k = 0;
//            stmt.setString(++k, email);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs != null && rs.next()) {
//                    u = mapUser(rs);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DBException();
//        } finally {
//            close(stmt);
//            closeConnection(con);
//        }
//        return u;
        User u = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_USER_BY_EMAIL))
        {
            int k = 0;
            stmt.setString(++k, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs != null && rs.next()) {
                    u = mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }
        return u;
    }

    @Override
    public User read(String email, String password) throws DBException, IllegalFieldException {
        User u;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_USER_BY_EMAIL_AND_PASSWORD);

            int k = 0;
            stmt.setString(++k, email);
            stmt.setString(++k, password);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                u = mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return u;
    }

    @Override
    public void create(User user) throws DBException, NoSuchAlgorithmException, InvalidKeySpecException, IllegalFieldException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);

            int k = 0;
            stmt.setString(++k, user.getName());
            stmt.setString(++k, user.getSurname());
            stmt.setString(++k, user.getEmail());
            stmt.setString(++k, generatePasswordHash(user.getPassword()));

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
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
    }

    @Override
    public void update(User user) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
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
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
    }

    @Override
    public void delete(User user) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(DELETE_USER);

            stmt.setLong(1, user.getId());
            stmt.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback(con);
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
    }
}
