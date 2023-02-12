package services;

import db.dao.StaffDAO;
import db.dao.mysql.entity.Staff;
import exeptions.IllegalFieldException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffService implements StaffDAO {
    private StaffDAO staffDAO;

    public StaffService(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    @Override
    public List<Staff> getAll() {
        List<Staff> staff = new ArrayList<>();
        try {
            staff = staffDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    @Override
    public Staff read(long id) throws IllegalFieldException {
        if (id < 0)
            throw new IllegalFieldException("id must be greater than zero.");
        try {
           return staffDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Staff staff) {
        if (staff == null)
            throw new NullPointerException("staff is null.");

        try {
            staffDAO.create(staff);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Staff staff) {
        if (staff == null)
            throw new NullPointerException("staff is null.");

        try {
            staffDAO.update(staff);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Staff staff) {
        if (staff == null)
            throw new NullPointerException("staff is null.");

        try {
            staffDAO.delete(staff);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
