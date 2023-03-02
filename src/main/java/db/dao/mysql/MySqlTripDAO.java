package db.dao.mysql;

import db.dao.DataSource;
import db.dao.TripDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Trip;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.DataSource.closeConnection;
import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

public class MySqlTripDAO implements TripDAO {
    private static MySqlTripDAO instance;

    private MySqlTripDAO() {

    }

    public static synchronized MySqlTripDAO getInstance() {
        if (instance == null) {
            instance = new MySqlTripDAO();
        }
        return instance;
    }
    private static Trip mapTrip(ResultSet rs) throws SQLException, IllegalFieldException {
        Trip t = new Trip();
        t.setId(rs.getLong(ID));
        t.setUserId(rs.getLong("user_id"));
        t.setLinerId(rs.getLong("liner_id"));
        t.setIsPaid(rs.getBoolean("is_paid"));
        t.setPrice(rs.getDouble("price"));
        t.setDateStart(rs.getDate("date_start"));
        t.setDateEnd(rs.getDate("date_end"));
        t.setPassport(rs.getBinaryStream("passport"));
        t.setStatus(Trip.Status.valueOf(rs.getInt("status")));
        return t;
    }

    @Override
    public List<Trip> getAll() throws DBException, IllegalFieldException {
        List<Trip> trips = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DataSource.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_ALL_TRIPS);
            while (rs.next()) {
                trips.add(mapTrip(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(rs);
            close(stmt);
            closeConnection(con);
        }
        return trips;
    }

    @Override
    public List<Trip> getAllByLiner(Liner liner) throws DBException, IllegalFieldException {
        List<Trip> trips = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(GET_ALL_TRIPS_BY_LINER);
            int k = 0;
            stmt.setLong(++k, liner.getId());

            stmt.executeQuery();

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trips.add(mapTrip(rs));
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
        return trips;
    }

    @Override
    public List<Trip> getAllByUserId(long userId) throws DBException, IllegalFieldException {
        List<Trip> trips = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(GET_ALL_TRIPS_BY_USER_ID);
            int k = 0;
            stmt.setLong(++k, userId);

            stmt.executeQuery();

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trips.add(mapTrip(rs));
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
        return trips;
    }

    @Override
    public Trip read(long id) throws DBException, IllegalFieldException {
        Trip t = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_TRIP_BY_ID);

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                t = mapTrip(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return t;
    }

    @Override
    public Trip readByLinerId(long liner_id) throws DBException, IllegalFieldException {
        Trip t = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_TRIP_BY_USER_ID);

            stmt.setLong(1, liner_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    t = mapTrip(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return t;
    }

    @Override
    public Trip readByUserIdAndLinerId(long user_id, long liner_id) throws SQLException, IllegalFieldException {
        Trip t = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(GET_TRIP_BY_USER_ID_AND_LINER_ID);

            int k = 0;
            stmt.setLong(++k, user_id);
            stmt.setLong(++k, liner_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    t = mapTrip(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return t;
    }

    @Override
    public void create(Trip trip) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(INSERT_TRIP);

            int k = 0;
            stmt.setLong(++k, trip.getUserId());
            stmt.setLong(++k, trip.getLinerId());
            stmt.setBoolean(++k, trip.getIsPaid());
            stmt.setDouble(++k, trip.getPrice());
            stmt.setDate(++k, trip.getDateStart());
            stmt.setDate(++k, trip.getDateEnd());
            stmt.setInt(++k, trip.getStatus().getCode());
            if (trip.getPassport() != null) {
                stmt.setBinaryStream(++k, trip.getPassport());
            }
            stmt.executeUpdate();
            con.commit();
        } catch (SQLException | NullPointerException e) {
            rollback(con);
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
    }

    @Override
    public void update(Trip trip) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(UPDATE_TRIP);
            int k = 0;
            stmt.setLong(++k, trip.getUserId());
            stmt.setLong(++k, trip.getLinerId());
            stmt.setBoolean(++k, trip.getIsPaid());
            stmt.setDouble(++k, trip.getPrice());
            stmt.setDate(++k, trip.getDateStart());
            stmt.setDate(++k, trip.getDateEnd());
            stmt.setInt(++k, trip.getStatus().getCode());
            stmt.setBinaryStream(++k, trip.getPassport());
            stmt.setLong(++k, trip.getId());

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
    public void updateIsPaid(boolean isPaid, long id) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(UPDATE_IS_PAID_IN_TRIP);
            int k = 0;
            stmt.setBoolean(++k, isPaid);
            stmt.setLong(++k, id);

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
    public void updateIsStatus(Trip.Status status, long id) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(UPDATE_IS_PAID_IN_TRIP);
            int k = 0;
            stmt.setInt(++k, status.getCode());
            stmt.setLong(++k, id);

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
    public void delete(Trip trip) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(DELETE_TRIP);

            stmt.setLong(1, trip.getId());

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
