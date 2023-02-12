package db.dao;

import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Trip;
import exeptions.DBException;
import exeptions.IllegalFieldException;

import java.sql.SQLException;
import java.util.List;

public interface TripDAO extends ItemDAO<Trip> {

    List<Trip> getAllByLiner(Liner liner) throws SQLException, DBException, IllegalFieldException;

    List<Trip> getAllByUserId(long userId) throws SQLException, IllegalFieldException, DBException;

    Trip readByLinerId(long liner_id) throws SQLException, IllegalFieldException, DBException;

    Trip readByUserIdAndLinerId(long user_id, long liner_id) throws SQLException, IllegalFieldException;

    void updateIsPaid(boolean isPaid, long id) throws SQLException, IllegalFieldException, DBException;

    void updateIsStatus(Trip.Status status, long id) throws SQLException, IllegalFieldException, DBException;
}
