package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.DataSource;
import db.dao.StaffDAO;
import db.dao.mysql.entity.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

public class MySqlStaffDAO implements StaffDAO {
    private static Staff mapStaff(ResultSet rs) throws SQLException {
        Staff s = new Staff();
        s.setId(rs.getLong(ID));
        s.setName(rs.getString("name"));
        s.setSurname(rs.getString("surname"));
        s.setSpecialization(rs.getString("specialization"));
        s.setLiner_id(rs.getLong("liner_id"));
        return s;
    }

    @Override
    public List<Staff> getAll() {
        List<Staff> staff = new ArrayList<>();
        try (Connection con = DataSource.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_STAFF);
            while (rs.next()) {
                staff.add(mapStaff(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return staff;
    }

    @Override
    public Staff read(long id) throws SQLException {
        Staff s = new Staff();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_STAFF_BY_ID)
        ) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                s = mapStaff(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return s;
    }

    @Override
    public void create(Staff staff) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(INSERT_STAFF, Statement.RETURN_GENERATED_KEYS);

            int k = 0;
            stmt.setString(++k, staff.getName());
            stmt.setString(++k, staff.getSurname());
            stmt.setString(++k, staff.getSpecialization());
            stmt.setLong(++k, staff.getLiner_id());

            int count = stmt.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        staff.setId(rs.getInt(1));
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
    public void update(Staff staff) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(UPDATE_STAFF);
            int k = 0;
            stmt.setString(++k, staff.getName());
            stmt.setString(++k, staff.getSurname());
            stmt.setString(++k, staff.getSpecialization());
            stmt.setLong(++k, staff.getLiner_id());
            stmt.setLong(++k, staff.getId());

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
    public void delete(Staff staff) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(DELETE_STAFF);

            stmt.setLong(1, staff.getId());
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
