package services;

import db.dao.mysql.MySqlRoleDAO;
import db.dao.mysql.entity.Role;
import db.dao.mysql.entity.Staff;
import db.dao.mysql.entity.Trip;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoleServiceTest {
    @Mock
    private MySqlRoleDAO mySqlRoleDAO;
    private RoleService roleService;

    private final List<List<Object>> incorrectRoles = Arrays.asList(
            Arrays.asList(-5, Role.Roles.ADMIN),
            Arrays.asList(5, null)
    );

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() throws SQLException, IllegalFieldException {
        roleService = new RoleService(mySqlRoleDAO);

        List<Role> expectedRoles = Arrays.asList(
                Role.createRole(1, Role.Roles.ADMIN)
        );

        Mockito.when(mySqlRoleDAO.getAll()).
                thenReturn(expectedRoles);

        assertEquals(expectedRoles, mySqlRoleDAO.getAll());

        assertNotNull(roleService.getAll());
    }

    @Test
    public void read() throws SQLException, IllegalFieldException {
        roleService = new RoleService(mySqlRoleDAO);

        Role expectedRole = Role.createRole(12, Role.Roles.ADMIN);

        Mockito.when(mySqlRoleDAO.read(12)).
                thenReturn(expectedRole);
        Mockito.when(mySqlRoleDAO.read(13)).
                thenReturn(null);

        assertEquals(expectedRole, mySqlRoleDAO.read(12));
        assertNull(mySqlRoleDAO.read(13));
        assertThrows(IllegalFieldException.class, () ->
                roleService.read(-1));
    }

    @Test
    public void create() {
        assertThrows(NullPointerException.class, () ->
                roleService.create(null));

        for (List<Object> incorrectRole : incorrectRoles) {
            assertThrows(IllegalFieldException.class, () ->
                    roleService.create(Role.createRole(
                            Long.parseLong(incorrectRole.get(0).toString()),
                            (Role.Roles) incorrectRole.get(1)
                    )));
        }
    }

    @Test
    public void update() {
        assertThrows(NullPointerException.class, () ->
                roleService.update(null));

        for (List<Object> incorrectRole : incorrectRoles) {
            assertThrows(IllegalFieldException.class, () ->
                    roleService.update(Role.createRole(
                            Long.parseLong(incorrectRole.get(0).toString()),
                            (Role.Roles) incorrectRole.get(1)
                    )));
        }
    }

    @Test
    public void delete() {
        assertThrows(NullPointerException.class, () ->
                roleService.delete(null));

        for (List<Object> incorrectRole : incorrectRoles) {
            assertThrows(IllegalFieldException.class, () ->
                    roleService.delete(Role.createRole(
                            Long.parseLong(incorrectRole.get(0).toString()),
                            (Role.Roles) incorrectRole.get(1)
                    )));
        }
    }
}
