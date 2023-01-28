package db.dao.mysql;

import db.dao.DataSource;
import db.dao.LinerDAO;
import db.dao.mysql.entity.Liner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;

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

    private int numberPageRecords;

    public List<Liner> getAll(int duration, Date date_start, Date date_end, int offset, int recordsPerPage) {
        List<Liner> liners = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_LINERS_PAGINATION)) {

            int k = 0;
            stmt.setInt(++k, duration);
            stmt.setDate(++k, date_start);
            stmt.setDate(++k, date_end);
            stmt.setInt(++k, offset);
            stmt.setInt(++k, recordsPerPage);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                liners.add(mapLiner(rs));
            }

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.numberPageRecords = rs.getInt(1);
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

    public List<Liner> getAll(Date date_start, Date date_end, int offset, int recordsPerPage) {
        List<Liner> liners = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_LINERS_PAGINATION_ALL_DURATION)) {

            int k = 0;
            stmt.setDate(++k, date_start);
            stmt.setDate(++k, date_end);
            stmt.setInt(++k, offset);
            stmt.setInt(++k, recordsPerPage);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                liners.add(mapLiner(rs));
            }

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next()) {
                this.numberPageRecords = rs.getInt(1);
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

    public int getNumberPageRecords() {
        return this.numberPageRecords;
    }

    public enum QueryDate {
        MIN_DATE_START(0), MAX_DATE_START(1), MIN_DATE_END(2), MAX_DATE_END(3);
        private final int code;

        QueryDate(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public Date getDate(QueryDate queryDate) throws SQLException {
        Date date = null;
        Connection con = null;
        Statement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.createStatement();
            String QUERY = null;
            switch (queryDate.getCode()) {
                case 0:
                    QUERY = GET_MIN_DATE_START_FROM_LINER;
                    break;
                case 1:
                    QUERY = GET_MAX_DATE_START_FROM_LINER;
                    break;
                case 2:
                    QUERY = GET_MIN_DATE_END_FROM_LINER;
                    break;
                case 3:
                    QUERY = GET_MAX_DATE_END_FROM_LINER;
                    break;
            }

            try (ResultSet rs = stmt.executeQuery(QUERY)) {
                rs.next();
                date = rs.getDate(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            close(stmt);
            con.close();
        }
        return date;
    }

    public List<Integer> getAllDurationOfTrip() throws SQLException {
        List<Integer> allDuration = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        try {
            con = DataSource.getConnection();

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_DURATION_OF_TRIP_FROM_LINER);
            while (rs.next()) {
                allDuration.add(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            close(stmt);
            con.close();
        }
        return allDuration;
    }


    @Override
    public Liner read(long id) throws SQLException {
        Liner u;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_LINER_BY_ID);

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                u = mapLiner(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            close(stmt);
            con.close();
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
