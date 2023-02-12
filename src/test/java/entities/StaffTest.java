package entities;

import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Role;
import db.dao.mysql.entity.RoleHasUser;
import db.dao.mysql.entity.Staff;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {
    private final List<List<Object>> incorrectStaff = Arrays.asList(
            Arrays.asList(null, "Малинов", Staff.Specialization.CAPTAIN, 1),
            Arrays.asList("Володимир", null, Staff.Specialization.CAPTAIN, 1),
            Arrays.asList("Володимир", "Малинов", null, 1),
            Arrays.asList("Володимир", "Малинов", Staff.Specialization.CAPTAIN, -1)
    );

    @Test
    public void createStaffTest() {
        for (List<Object> incorrectS : incorrectStaff) {
            assertThrows(IllegalFieldException.class, () ->
                    Staff.createStaff(
                            (String) incorrectS.get(0),
                            (String) incorrectS.get(1),
                            (Staff.Specialization) incorrectS.get(2),
                            Long.parseLong(incorrectS.get(3).toString())
                    ));
        }
    }

    @Test
    public void StaffTest() {
        assertDoesNotThrow(() -> {
            new Staff();
        });
    }

    @Test
    public void StaffWithIdTest() {
        for (long i = 1; i < 15; i++) {
            long finalI = i;
            assertDoesNotThrow(()
                    -> new Staff(finalI * 2));
        }

        for (long i = -15; i < 1; i++) {
            long finalI = i;
            assertThrows(IllegalFieldException.class, ()
                    -> new Staff(finalI * 2));
        }
    }

    @Test
    public void StaffWithArgumentsTest() {
        for (List<Object> incorrectS : incorrectStaff) {
            assertThrows(IllegalFieldException.class, () ->
                    new Staff(
                            (String) incorrectS.get(0),
                            (String) incorrectS.get(1),
                            (Staff.Specialization) incorrectS.get(2),
                            Long.parseLong(incorrectS.get(3).toString())
                    ));
        }
    }

    @Test
    public void setNameTest() {
        Staff testStaff = new Staff();

        List<String> correctNames =
                Arrays.asList("Vlad", "Anna", "Ron");

        List<String> incorrectNames =
                Arrays.asList(null, "aa", "321re32", "43421", "fds43241", "");


        for (String testName : correctNames) {
            assertDoesNotThrow(() -> testStaff.setName(testName));
        }

        for (String testName : incorrectNames) {
            assertThrows(IllegalFieldException.class, ()
                    -> testStaff.setName(testName));
        }
    }

    @Test
    public void setSurnameTest() {
        Staff testStaff = new Staff();
        List<String> correctSurnames =
                Arrays.asList("Vlad", "Anna", "Ron");

        List<String> incorrectSurnames =
                Arrays.asList(null, "aa", "321re32", "43421", "fds43241", "");

        for (String testSurname : correctSurnames) {
            assertDoesNotThrow(()
                    -> testStaff.setSurname(testSurname));
        }

        for (String testSurname : incorrectSurnames) {
            assertThrows(IllegalFieldException.class, ()
                    -> testStaff.setSurname(testSurname));
        }
    }

    @Test
    public void setSpecializationTest() throws IllegalFieldException {
        Staff testStaff = new Staff();

        List<Staff.Specialization> correctSpecializations = Arrays.asList(
                Staff.Specialization.CAPTAIN,
                Staff.Specialization.CLEANER,
                Staff.Specialization.SAILOR,
                Staff.Specialization.COOK
        );

        for (Staff.Specialization correctSpecialization : correctSpecializations) {
            assertDoesNotThrow(()
                    -> testStaff.setSpecialization(correctSpecialization));
        }

        assertThrows(IllegalFieldException.class, ()
                -> testStaff.setSpecialization(null));
    }

    @Test
    public void setLinerIdTest() {
        Staff testStaff = new Staff();

        long[] correctLiner_ids = new long[]{1, 5};
        long[] incorrectLiner_ids = new long[]{0, -1, -5};

        for (long correctLiner_id : correctLiner_ids) {
            assertDoesNotThrow(()
                    -> testStaff.setLinerId(correctLiner_id));
        }

        for (long incorrectLiner_id : incorrectLiner_ids) {
            assertThrows(IllegalFieldException.class, ()
                    -> testStaff.setLinerId(incorrectLiner_id));
        }
    }

    @Test
    public void getNameTest() throws IllegalFieldException {
        Staff testStaff = new Staff();
        testStaff.setName("Anna");

        assertEquals("Anna", testStaff.getName());
    }

    @Test
    public void getSurnameTest() throws IllegalFieldException {
        Staff testStaff = new Staff();
        testStaff.setSurname("Anna");

        assertEquals("Anna", testStaff.getSurname());
    }

    @Test
    public void getSpecializationTest() throws IllegalFieldException {
        Staff testStaff = new Staff();
        testStaff.setSpecialization(Staff.Specialization.COOK);

        assertEquals(Staff.Specialization.COOK, testStaff.getSpecialization());
    }

    @Test
    public void getLinerIdTest() throws IllegalFieldException {
        for (long i = 1; i < 15; i++) {
            Staff testStaff = new Staff();

            testStaff.setLinerId(i * 2);
            assertEquals(i * 2, testStaff.getLiner_id());
        }
    }
}
