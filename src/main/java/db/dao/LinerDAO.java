package db.dao;

import db.dao.mysql.entity.Liner;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface LinerDAO extends ItemDAO<Liner> {
    List<Liner> getAll(int duration, Date date_start, Date date_end, int offset, int recordsPerPage) throws IllegalFieldException, DBException;

    List<Liner> getAll(Date date_start, Date date_end, int offset, int recordsPerPage) throws IllegalFieldException, DBException;

    List<Integer> getAllDurationOfTrip() throws SQLException, DBException;
}
