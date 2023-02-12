package db.dao.mysql;

import db.dao.DataSource;
import db.dao.LinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.DataSource.closeConnection;
import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;

public class MySqlLinerDAO implements LinerDAO {
    private static Liner mapLiner(ResultSet rs) throws SQLException, IllegalFieldException {
        Liner l = new Liner();
        l.setId(rs.getLong(ID));
        l.setName(rs.getString("name"));
        l.setDescription(rs.getString("description"));
        l.setCapacity(rs.getInt("capacity"));
        l.setRoute(rs.getString("route"));
        l.setPriceCoefficient(rs.getDouble("price_coefficient"));
        l.setDateStart(rs.getDate("date_start"));
        l.setDateEnd(rs.getDate("date_end"));
        return l;
    }

    @Override
    public List<Liner> getAll() throws DBException {
        List<Liner> liners = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_LINERS);
            while (rs.next()) {
                liners.add(mapLiner(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return liners;
    }

    private int numberPageRecords;

    @Override
    public List<Liner> getAll(int duration, Date dateStart, Date dateEnd, int offset, int recordsPerPage) throws DBException {
        List<Liner> liners = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_ALL_LINERS_PAGINATION);

            int k = 0;
            stmt.setInt(++k, duration);
            stmt.setDate(++k, dateStart);
            stmt.setDate(++k, dateEnd);
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return liners;
    }

    @Override
    public List<Liner> getAll(Date dateStart, Date dateEnd, int offset, int recordsPerPage) throws DBException {
        List<Liner> liners = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_ALL_LINERS_PAGINATION_ALL_DURATION);

            int k = 0;
            stmt.setDate(++k, dateStart);
            stmt.setDate(++k, dateEnd);
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
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

    public Date getDate(QueryDate queryDate) throws DBException {
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
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return date;
    }

    @Override
    public List<Integer> getAllDurationOfTrip() throws DBException {
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
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return allDuration;
    }


    @Override
    public Liner read(long id) throws DBException, IllegalFieldException {
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
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return u;
    }

    @Override
    public void create(Liner liner) {

    }

    @Override
    public void update(Liner liner) {

    }

    @Override
    public void delete(Liner liner) {

    }

}
