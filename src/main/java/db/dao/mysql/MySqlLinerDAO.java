package db.dao.mysql;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import db.dao.DataSource;
import db.dao.LinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static db.dao.DataSource.closeConnection;
import static db.dao.PBKDF2.generatePasswordHash;
import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;
import static db.dao.mysql.MySqlDAOFactory.rollback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;

public class MySqlLinerDAO implements LinerDAO {
    private static MySqlLinerDAO instance;

    private MySqlLinerDAO() {

    }

    public static synchronized MySqlLinerDAO getInstance() {
        if (instance == null) {
            instance = new MySqlLinerDAO();
        }
        return instance;
    }
    private static Liner mapLiner(ResultSet rs) throws SQLException, IllegalFieldException, IOException {
        Liner l = new Liner();
        l.setId(rs.getLong(ID));
        l.setName(rs.getString("name"));
        l.setDescription(rs.getString("description"));
        l.setCapacity(rs.getInt("capacity"));


        String jsonData = rs.getString("route");
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {};
        Map<String, String> map = objectMapper.readValue(jsonData, typeRef);
        List<String> route = List.copyOf(map.values());
        l.setRoute(route);

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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
        return u;
    }

    @Override
    public void create(Liner liner) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(INSERT_LINER, Statement.RETURN_GENERATED_KEYS);

            int k = 0;
            stmt.setString(++k, liner.getName());
            stmt.setString(++k, liner.getDescription());
            stmt.setInt(++k, liner.getCapacity());
            stmt.setString(++k, liner.getRoute());
            stmt.setDouble(++k, liner.getPriceCoefficient());
            stmt.setDate(++k, liner.getDateStart());
            stmt.setDate(++k, liner.getDateEnd());

            int count = stmt.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        liner.setId(rs.getInt(1));
                    }
                }
            }
            con.commit();
        } catch (SQLException | IllegalFieldException e) {
            e.printStackTrace();
            rollback(con);
            throw new DBException();
        } finally {
            close(stmt);
            closeConnection(con);
        }
    }

    @Override
    public void update(Liner liner) throws DBException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(UPDATE_LINER);
            int k = 0;
            stmt.setString(++k, liner.getName());
            stmt.setString(++k, liner.getDescription());
            stmt.setInt(++k, liner.getCapacity());
            stmt.setString(++k, liner.getRoute());
            stmt.setDouble(++k, liner.getPriceCoefficient());
            stmt.setDate(++k, liner.getDateStart());
            stmt.setDate(++k, liner.getDateEnd());
            stmt.setLong(++k, liner.getId());

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
    public void delete(Liner liner) {

    }

}
