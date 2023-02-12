package entities;

import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.RoleHasUser;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoleHasUserTest {
    private final List<List<Object>> incorrectRoleHasUsers = Arrays.asList(
            Arrays.asList(-5, 1),
            Arrays.asList(1, -1)
    );

    @Test
    public void createRoleHasUserTest() {
        for (List<Object> incorrectRoleHasUser : incorrectRoleHasUsers) {
            assertThrows(IllegalFieldException.class, () ->
                    RoleHasUser.createRoleHasUser(
                            Long.parseLong(incorrectRoleHasUser.get(0).toString()),
                            Long.parseLong(incorrectRoleHasUser.get(1).toString())
                    ));
        }
    }

    @Test
    public void RoleHasUserTest() {
        assertDoesNotThrow(() -> {
            new RoleHasUserTest();
        });
    }

    @Test
    public void RoleHasUserWithIdTest() {
        for (long i = 1; i < 15; i++) {
            long finalI = i;
            assertDoesNotThrow(() -> new RoleHasUser(finalI * 2));
        }

        for (long i = -15; i < 1; i++) {
            long finalI = i;
            assertThrows(IllegalFieldException.class, () -> new RoleHasUser(finalI * 2));
        }
    }


    @Test
    public void RoleHasUserWithArgumentsTest() {
        for (List<Object> incorrectRoleHasUser : incorrectRoleHasUsers) {
            assertThrows(IllegalFieldException.class, () ->
                    new RoleHasUser(
                            Long.parseLong(incorrectRoleHasUser.get(0).toString()),
                            Long.parseLong(incorrectRoleHasUser.get(1).toString())
                    ));
        }
    }

    @Test
    public void setRoleITest() {
        RoleHasUser testRoleHasUser = new RoleHasUser();

        long[] correctRoleIds = new long[]{1, 5, 10};
        long[] incorrectRoleIds = new long[]{0, -5, -10};

        for (long correctRoleId : correctRoleIds) {
            assertDoesNotThrow(() -> testRoleHasUser.setRoleId(correctRoleId));
        }

        for (long incorrectRoleId : incorrectRoleIds) {
            assertThrows(IllegalFieldException.class, ()
                    -> testRoleHasUser.setRoleId(incorrectRoleId));
        }
    }

    @Test
    public void setUserIdTest() {
        RoleHasUser testRoleHasUser = new RoleHasUser();
        long[] correctUserIds = new long[]{1, 5, 10};
        long[] incorrectUserIds = new long[]{0, -5, -10};

        for (long correctUserId : correctUserIds) {
            assertDoesNotThrow(() -> testRoleHasUser.setUserId(correctUserId));
        }

        for (long incorrectUserId : incorrectUserIds) {
            assertThrows(IllegalFieldException.class, ()
                    -> testRoleHasUser.setUserId(incorrectUserId));
        }
    }

    @Test
    public void getRoleIdTest() throws IllegalFieldException {
        for (long i = 1; i < 15; i++) {
            RoleHasUser testRoleHasUser = new RoleHasUser(i * 2);
            testRoleHasUser.setRoleId(i * 2);
            assertEquals(i * 2, testRoleHasUser.getRoleId());
        }
    }

    @Test
    public void getUserIdTest() throws IllegalFieldException {
        for (long i = 1; i < 15; i++) {
            RoleHasUser testRoleHasUser = new RoleHasUser(i * 2);
            testRoleHasUser.setUserId(i * 2);
            assertEquals(i * 2, testRoleHasUser.getUserId());
        }
    }
}
