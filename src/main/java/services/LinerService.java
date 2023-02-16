package services;

import db.dao.LinerDAO;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Liner> getAll(int duration, Date dateStart, Date dateEnd, int offset, int recordsPerPage) throws IllegalFieldException, DBException {
        if (duration < 0)
            throw new IllegalFieldException("duration must be greater than zero.");
        if (dateStart == null)
            throw new IllegalFieldException("dateStart is null.");
        if (dateEnd == null)
            throw new IllegalFieldException("dateEnd is null.");
        if (dateStart.compareTo(dateEnd) > 0)
            throw new IllegalFieldException("dateEnd is null.");
        if (offset < 0)
            throw new IllegalFieldException("offset must be greater than zero.");
        if (recordsPerPage < 0)
            throw new IllegalFieldException("recordsPerPage must be greater than zero.");

        return linerDAO.getAll(
                duration,
                dateStart,
                dateEnd,
                offset,
                recordsPerPage
        );
    }

    public List<Liner> getAll(Date dateStart, Date dateEnd, int offset, int recordsPerPage)
            throws IllegalFieldException, DBException {
        if (dateStart == null)
            throw new IllegalFieldException("dateStart is null.");
        if (dateEnd == null)
            throw new IllegalFieldException("dateEnd is null.");
        if (dateStart.compareTo(dateEnd) > 0)
            throw new IllegalFieldException("dateStart cannot be later than dateEnd.");
        if (offset < 0)
            throw new IllegalFieldException("offset must be greater than zero.");
        if (recordsPerPage < 0)
            throw new IllegalFieldException("recordsPerPage must be greater than zero.");

        return linerDAO.getAll(dateStart, dateEnd, offset, recordsPerPage);
    }

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
    public Date getDate(MySqlLinerDAO.QueryDate queryDate) throws SQLException, DBException {
        Date date = null;
        try {
            date = linerDAO.getDate(queryDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getNumberPageRecords() {
        try {
            return linerDAO.getNumberPageRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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
