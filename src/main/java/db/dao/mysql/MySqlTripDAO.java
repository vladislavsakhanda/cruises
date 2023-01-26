package db.dao.mysql;

import db.dao.DataSource;
import db.dao.TripDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Trip;
import db.dao.mysql.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;
import static db.dao.mysql.MySqlConstants.*;

import javax.servlet.http.Part;

public class MySqlTripDAO implements TripDAO {
    private static Trip mapTrip(ResultSet rs) throws SQLException {
        Trip t = new Trip();
        t.setId(rs.getLong(ID));
        t.setUser_id(rs.getLong("user_id"));
        t.setLiner_id(rs.getLong("liner_id"));
        t.setIs_paid(rs.getBoolean("is_paid"));
        t.setPrice(rs.getDouble("price"));
        t.setDate_start(rs.getDate("date_start"));
        t.setDate_end(rs.getDate("date_end"));
        t.setPassport(rs.getBinaryStream("passport"));
        t.setStatus(rs.getInt("status"));
        return t;
    }

    @Override
    public List<Trip> getAll() {
        List<Trip> trips = new ArrayList<>();
        try (Connection con = DataSource.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_TRIPS);
            while (rs.next()) {
                trips.add(mapTrip(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return trips;
    }

    public List<Trip> getAllByLiner(Liner liner) throws SQLException {
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
            throw e;
        } finally {
            close(stmt);
            close(con);
        }
        return trips;
    }

    public List<Trip> getAllByUserId(long userId) throws SQLException {
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
            throw e;
        } finally {
            close(stmt);
            close(con);
        }
        return trips;
    }

    @Override
    public Trip read(long id) throws SQLException {
        Trip t = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_TRIP_BY_ID)
        ) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                t = mapTrip(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return t;
    }

    public Trip readByLinerId(long liner_id) throws SQLException {
        Trip t = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_TRIP_BY_USER_ID)
        ) {
            stmt.setLong(1, liner_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    t = mapTrip(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return t;
    }

    public Trip readByUserIdAndLinerId(long user_id, long liner_id) throws SQLException {
        Trip t = null;
        Connection con = null;
        PreparedStatement stmt = null;
//        try (Connection con = DataSource.getConnection();
//             PreparedStatement stmt = con.prepareStatement(GET_TRIP_BY_USER_ID_AND_LINER_ID)
//        ) {
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
            stmt.close();
            con.close();
        }
        return t;
    }

    @Override
    public void create(Trip trip) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRIP)) {

            int k = 0;
            preparedStatement.setLong(++k, trip.getUser_id());
            preparedStatement.setLong(++k, trip.getLiner_id());
            preparedStatement.setBoolean(++k, trip.getIs_paid());
            preparedStatement.setDouble(++k, trip.getPrice());
            preparedStatement.setDate(++k, trip.getDate_start());
            preparedStatement.setDate(++k, trip.getDate_end());
            preparedStatement.setInt(++k, trip.getStatus());
            if (trip.getPassport() != null) {
                preparedStatement.setBinaryStream(++k, trip.getPassport());
            }
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Trip entity) {

    }

    public void updateIsPaid(boolean isPaid, long id) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(UPDATE_IS_PAID_IN_TRIP);
            int k = 0;
            stmt.setBoolean(++k, isPaid);
            stmt.setLong(++k, id);

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

    public void updateIsStatus(Trip.Status status, long id) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(UPDATE_IS_PAID_IN_TRIP);
            int k = 0;
            stmt.setInt(++k, status.getCode());
            stmt.setLong(++k, id);

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
    public void delete(Trip trip) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(DELETE_TRIP);

            stmt.setLong(1, trip.getId());
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
