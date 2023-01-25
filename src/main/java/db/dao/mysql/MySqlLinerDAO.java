package db.dao.mysql;

import com.zaxxer.hikari.HikariDataSource;
import db.dao.DataSource;
import db.dao.LinerDAO;
import db.dao.mysql.entity.Liner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.mysql.MySqlConstants.*;

public class MySqlLinerDAO implements LinerDAO {
    private static Liner mapLiner(ResultSet rs) throws SQLException {
        Liner l = new Liner();
        l.setId(rs.getLong(ID));
        l.setName(rs.getString("name"));
        l.setDescription(rs.getString("description"));
        l.setCapacity(rs.getInt("capacity"));
        l.setRoute(rs.getString("route"));
        l.setPrice_coefficient(rs.getDouble("price_coefficient"));
        l.setDate_start(rs.getDate("date_start"));
        l.setDate_end(rs.getDate("date_end"));
        return l;
    }

    @Override
    public List<Liner> getAll() {
        List<Liner> liners = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_LINERS)) {
            while (rs.next()) {
                liners.add(mapLiner(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return liners;
    }
    private int noOfRecords;
    public List<Liner> getAll(int offset, int noOfRecords) {
        List<Liner> liners = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_LINERS_PAGINATION)) {

            int k = 0;
            stmt.setInt(++k, offset);
            stmt.setInt(++k, noOfRecords);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                liners.add(mapLiner(rs));
            }

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.noOfRecords = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return liners;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public Liner read(long id) throws SQLException {
        Liner u;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_LINER_BY_ID)
        ) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                u = mapLiner(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return u;
    }

    @Override
    public void create(Liner entity) {

    }

    @Override
    public void update(Liner entity) {

    }

    @Override
    public void delete(Liner entity) {

    }

}
