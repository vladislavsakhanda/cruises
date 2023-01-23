package db.dao.mysql;

import db.dao.DataSource;
import db.dao.TripDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Trip;

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
        return null;
    }

    public List<Trip> getAllByLiner(Liner liner) throws SQLException {
        List<Trip> trips = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        int rows = 0;
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

    @Override
    public Trip read(long id) {
        return null;
    }

    @Override
    public void create(Trip trip) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int k = 0;
            preparedStatement.setLong(++k, trip.getUser_id());
            preparedStatement.setLong(++k, trip.getLiner_id());
            preparedStatement.setBoolean(++k, trip.getIs_paid());
            preparedStatement.setDouble(++k, trip.getPrice());
            preparedStatement.setDate(++k, trip.getDate_start());
            preparedStatement.setDate(++k, trip.getDate_end());
            preparedStatement.setInt(++k, trip.getStatus());
            if (trip.getPassport() != null) {
                preparedStatement.setBlob(++k, trip.getPassport());
            }
            System.out.println("\nPassport: ");
            System.out.println(trip.getPassport());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Trip entity) {

    }

    @Override
    public void delete(Trip entity) {

    }

    private static final String sql = "INSERT INTO trip " +
            "(user_id, liner_id, is_paid, price, date_start, date_end, passport)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";


}
