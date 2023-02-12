package services;

import db.dao.mysql.MySqlStaffDAO;
import db.dao.mysql.entity.Staff;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaffServiceTest {
    @Mock
    private MySqlStaffDAO mySqlStaffDAO;
    private StaffService staffService = new StaffService(mySqlStaffDAO);

    private final List<List<Object>> incorrectStaff = Arrays.asList(
            Arrays.asList(null, "Малинов", Staff.Specialization.CAPTAIN, 1),
            Arrays.asList("Володимир", null, Staff.Specialization.CAPTAIN, 1),
            Arrays.asList("Володимир", "Малинов", null, 1),
            Arrays.asList("Володимир", "Малинов", Staff.Specialization.CAPTAIN, -1)
    );

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() throws IllegalFieldException, DBException {
        staffService = new StaffService(mySqlStaffDAO);

        List<Staff> expectedStaff = Arrays.asList(
                Staff.createStaff("Володимир", "Малинов",
                        Staff.Specialization.CAPTAIN, 1),
                Staff.createStaff("Іоана", "Малинова",
                        Staff.Specialization.SAILOR, 1)
        );

        Mockito.when(mySqlStaffDAO.getAll()).
                thenReturn(expectedStaff);

        assertEquals(expectedStaff, mySqlStaffDAO.getAll());
        assertEquals(expectedStaff.getClass(), mySqlStaffDAO.getAll().getClass());
        assertNotNull(staffService.getAll());
    }

    @Test
    public void read() throws SQLException, IllegalFieldException, DBException {
        Staff expectedStaff = Staff.createStaff(
                "Володимир", "Малинов",
                Staff.Specialization.CAPTAIN, 1);

        Mockito.when(mySqlStaffDAO.read(12)).
                thenReturn(expectedStaff);
        Mockito.when(mySqlStaffDAO.read(13)).
                thenReturn(null);

        assertEquals(expectedStaff, mySqlStaffDAO.read(12));
        assertNull(mySqlStaffDAO.read(13));
        assertThrows(IllegalFieldException.class, () ->
                staffService.read(-1));
    }

    @Test
    public void create() {
        assertThrows(NullPointerException.class, () ->
                staffService.create(null));

        for (List<Object> incorrectStaff : incorrectStaff) {
            assertThrows(IllegalFieldException.class, () ->
                    staffService.create(Staff.createStaff(
                            (String) incorrectStaff.get(0),
                            (String) incorrectStaff.get(1),
                            (Staff.Specialization) incorrectStaff.get(2),
                            Long.parseLong(incorrectStaff.get(3).toString())
                    )));
        }
    }

    @Test
    public void update() {
        assertThrows(NullPointerException.class, () ->
                staffService.update(null));

        for (List<Object> incorrectStaff : incorrectStaff) {
            assertThrows(IllegalFieldException.class, () ->
                    staffService.update(Staff.createStaff(
                            (String) incorrectStaff.get(0),
                            (String) incorrectStaff.get(1),
                            (Staff.Specialization) incorrectStaff.get(2),
                            Long.parseLong(incorrectStaff.get(3).toString())
                    )));
        }
    }

    @Test
    public void delete() {
        assertThrows(NullPointerException.class, () ->
                staffService.delete(null));

        for (List<Object> incorrectStaff : incorrectStaff) {
            assertThrows(IllegalFieldException.class, () ->
                    staffService.delete(Staff.createStaff(
                            (String) incorrectStaff.get(0),
                            (String) incorrectStaff.get(1),
                            (Staff.Specialization) incorrectStaff.get(2),
                            Long.parseLong(incorrectStaff.get(3).toString())
                    )));
        }
    }
}
