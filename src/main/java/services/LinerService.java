package services;

import db.dao.DataSource;
import db.dao.LinerDAO;
import db.dao.UserDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.User;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.dao.mysql.MySqlConstants.*;
import static db.dao.mysql.MySqlDAOFactory.close;

public class LinerService implements LinerDAO {
    private LinerDAO linerDAO;

    public LinerService(LinerDAO linerDAO) {
        this.linerDAO = linerDAO;
    }

    @Override
    public List<Liner> getAll() throws IllegalFieldException {
        List<Liner> liners = new ArrayList<>();
        try {
            liners = linerDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liners;
    }

    public List<Liner> getAll(int duration, Date date_start, Date date_end, int offset, int recordsPerPage) throws IllegalFieldException, DBException {
        if (duration < 0)
            throw new IllegalFieldException("duration must be greater than zero.");
        if (date_start == null)
            throw new IllegalFieldException("date_start is null.");
        if (date_end == null)
            throw new IllegalFieldException("date_end is null.");
        if (date_start.compareTo(date_end) > 0)
            throw new IllegalFieldException("date_end is null.");
        if (offset < 0)
            throw new IllegalFieldException("offset must be greater than zero.");
        if (recordsPerPage < 0)
            throw new IllegalFieldException("recordsPerPage must be greater than zero.");

        return linerDAO.getAll(
                duration,
                date_start,
                date_end,
                offset,
                recordsPerPage
        );
    }

    public List<Liner> getAll(Date date_start, Date date_end, int offset, int recordsPerPage)
            throws IllegalFieldException, DBException {
        if (date_start == null)
            throw new IllegalFieldException("date_start is null.");
        if (date_end == null)
            throw new IllegalFieldException("date_end is null.");
        if (date_start.compareTo(date_end) > 0)
            throw new IllegalFieldException("date_end is null.");
        if (offset < 0)
            throw new IllegalFieldException("offset must be greater than zero.");
        if (recordsPerPage < 0)
            throw new IllegalFieldException("recordsPerPage must be greater than zero.");

        return linerDAO.getAll(date_start, date_end, offset, recordsPerPage);
    }

//    public int getNumberPageRecords() {
//        return this.numberPageRecords;
//    }
//
//    public Date getDate(QueryDate queryDate) throws SQLException {
//        Date date = null;
//        Connection con = null;
//        Statement stmt = null;
//        try {
//            con = DataSource.getConnection();
//            stmt = con.createStatement();
//            String QUERY = null;
//            switch (queryDate.getCode()) {
//                case 0:
//                    QUERY = GET_MIN_DATE_START_FROM_LINER;
//                    break;
//                case 1:
//                    QUERY = GET_MAX_DATE_START_FROM_LINER;
//                    break;
//                case 2:
//                    QUERY = GET_MIN_DATE_END_FROM_LINER;
//                    break;
//                case 3:
//                    QUERY = GET_MAX_DATE_END_FROM_LINER;
//                    break;
//            }
//
//            try (ResultSet rs = stmt.executeQuery(QUERY)) {
//                rs.next();
//                date = rs.getDate(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                throw e;
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        } finally {
//            close(stmt);
//            if (con != null) {
//                con.close();
//            }
//        }
//        return date;
//    }

    public List<Integer> getAllDurationOfTrip() throws SQLException {
        List<Integer> durations = new ArrayList<>();
        try {
            durations = linerDAO.getAllDurationOfTrip();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return durations;
    }


    @Override
    public Liner read(long id) throws IllegalFieldException {
        if (id < 0) {
            throw new IllegalFieldException("id must be greater than zero.");
        }

        try {
            return linerDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Liner liner) {
        if (liner == null) {
            throw new NullPointerException();
        }

        try {
            linerDAO.create(liner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Liner liner) {
        if (liner == null) {
            throw new NullPointerException();
        }

        try {
            linerDAO.update(liner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Liner liner) {
        if (liner == null) {
            throw new NullPointerException();
        }

        try {
            linerDAO.delete(liner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
