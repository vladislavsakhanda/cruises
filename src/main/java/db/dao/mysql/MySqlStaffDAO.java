package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.DataSource;
import db.dao.StaffDAO;
import db.dao.mysql.entity.Staff;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.DataSource.closeConnection;
import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

public class MySqlStaffDAO implements StaffDAO {
    private static Staff mapStaff(ResultSet rs) throws SQLException, IllegalFieldException {
        Staff s = new Staff();
        s.setId(rs.getLong(ID));
        s.setName(rs.getString("name"));
        s.setSurname(rs.getString("surname"));
        s.setSpecialization(Staff.Specialization.valueOf(rs.getString("specialization")));
        s.setLinerId(rs.getLong("liner_id"));
        return s;
    }

    @Override
    public List<Staff> getAll() throws DBException, IllegalFieldException {
        List<Staff> staff = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DataSource.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_ALL_STAFF);
            while (rs.next()) {
                staff.add(mapStaff(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(rs);
            close(stmt);
            closeConnection(con);
        }
        return staff;
    }

    @Override
    public Staff read(long id) throws DBException, IllegalFieldException {
        Staff s = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_STAFF_BY_ID);

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                s = mapStaff(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return s;
    }

    @Override
    public void create(Staff staff) throws DBException, IllegalFieldException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(INSERT_STAFF, Statement.RETURN_GENERATED_KEYS);

            int k = 0;
            stmt.setString(++k, staff.getName());
            stmt.setString(++k, staff.getSurname());
            stmt.setString(++k, staff.getSpecialization().getCode());
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
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
    }

    @Override
    public void update(Staff staff) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(UPDATE_STAFF);
            int k = 0;
            stmt.setString(++k, staff.getName());
            stmt.setString(++k, staff.getSurname());
            stmt.setString(++k, staff.getSpecialization().getCode());
            stmt.setLong(++k, staff.getLiner_id());
            stmt.setLong(++k, staff.getId());
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
    public void delete(Staff staff) throws DBException {
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
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
    }
}
