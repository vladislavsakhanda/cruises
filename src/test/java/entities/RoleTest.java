package entities;

import db.dao.mysql.entity.Role;
import db.dao.mysql.entity.RoleHasUser;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {
    private final List<List<Object>> incorrectRoles = Arrays.asList(
            Arrays.asList(-5, Role.Roles.ADMIN),
            Arrays.asList(5, null)
    );

    @Test
    public void createRoleTest() {
        for (List<Object> incorrectRole : incorrectRoles) {
            assertThrows(IllegalFieldException.class, () ->
                    Role.createRole(
                            Long.parseLong(incorrectRole.get(0).toString()),
                            (Role.Roles) incorrectRole.get(1)
                    ));
        }
    }

    @Test
    public void RoleTest() {
        assertDoesNotThrow(() -> {
            new Role();
        });
    }

    @Test
    public void getRoleTest() throws IllegalFieldException {
        Role testRole = Role.createRole(1, Role.Roles.ADMIN);
        assertEquals(Role.Roles.ADMIN, testRole.getRole());
    }

    @Test
    public void setRoleTest() {
        Role testRole = new Role();

        assertDoesNotThrow(() -> {
            testRole.setRole(Role.Roles.ADMIN);
        });

        assertThrows(IllegalFieldException.class, () -> {
            testRole.setRole(null);
        });
    }
}
