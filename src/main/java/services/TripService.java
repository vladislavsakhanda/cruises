package services;

import db.dao.TripDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Trip;
import exeptions.IllegalFieldException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TripService implements TripDAO {
    private TripDAO tripDAO;

    public TripService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    @Override
    public List<Trip> getAll() {
        List<Trip> liners = new ArrayList<>();
        try {
            liners = tripDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liners;
    }

    public List<Trip> getAllByLiner(Liner liner) {
        List<Trip> liners = new ArrayList<>();

        if (liner == null) {
            throw new NullPointerException();
        }

        try {
            liners = tripDAO.getAllByLiner(liner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liners;
    }

    public List<Trip> getAllByUserId(long userId) throws IllegalFieldException {
        List<Trip> liners = new ArrayList<>();

        if (userId < 0)
            throw new IllegalFieldException("user_id must be greater than zero.");

        try {
            liners = tripDAO.getAllByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liners;
    }

    @Override
    public Trip read(long id) throws IllegalFieldException {
        if (id < 0)
            throw new IllegalFieldException("liner_id must be greater than zero.");

        try {
            return tripDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Trip readByLinerId(long liner_id) throws IllegalFieldException {
        if (liner_id < 0)
            throw new IllegalFieldException("liner_id must be greater than zero.");

        try {
            return tripDAO.readByLinerId(liner_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Trip readByUserIdAndLinerId(long user_id, long liner_id) throws IllegalFieldException {
        if (user_id < 0)
            throw new IllegalFieldException("user_id must be greater than zero.");
        if (liner_id < 0)
            throw new IllegalFieldException("liner_id must be greater than zero.");

        try {
            return tripDAO.readByUserIdAndLinerId(user_id, liner_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Trip trip) {
        if (trip == null) {
            throw new NullPointerException();
        }

        try {
            tripDAO.create(trip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Trip trip) {
        if (trip == null) {
            throw new NullPointerException();
        }

        try {
            tripDAO.update(trip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateIsPaid(boolean isPaid, long id) throws IllegalFieldException {
        if (id < 0)
            throw new IllegalFieldException("liner_id must be greater than zero.");

        try {
            tripDAO.updateIsPaid(isPaid, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateIsStatus(Trip.Status status, long id) throws IllegalFieldException {
        if (id < 0)
            throw new IllegalFieldException("liner_id must be greater than zero.");

        try {
            tripDAO.updateIsStatus(status, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Trip trip) {
        if (trip == null) {
            throw new NullPointerException();
        }

        try {
            tripDAO.delete(trip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
