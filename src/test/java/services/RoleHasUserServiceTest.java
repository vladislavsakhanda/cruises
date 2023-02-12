package services;

import db.dao.mysql.MySqlRoleHasUserDAO;
import db.dao.mysql.entity.RoleHasUser;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoleHasUserServiceTest {
    @Mock
    private static MySqlRoleHasUserDAO mySqlRoleHasUserDAO;
    private static RoleHasUserService roleHasUserService;

    private final List<List<Object>> incorrectRoleHasUser = Arrays.asList(
            Arrays.asList(-5, 1),
            Arrays.asList(1, -1)
    );

    @BeforeEach
    public void setup() {
        roleHasUserService = new RoleHasUserService(mySqlRoleHasUserDAO);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() throws IllegalFieldException {
        List<RoleHasUser> expectedRoleHasUser = Arrays.asList(
                RoleHasUser.createRoleHasUser(1, 1)
        );

        Mockito.when(mySqlRoleHasUserDAO.getAll()).
                thenReturn(expectedRoleHasUser);

        assertEquals(expectedRoleHasUser, mySqlRoleHasUserDAO.getAll());
        assertNotNull(roleHasUserService.getAll());
    }

    @Test
    public void readById() throws SQLException, IllegalFieldException {
        RoleHasUser expectedRoleHasUser =
                RoleHasUser.createRoleHasUser(1, 1);

        Mockito.when(mySqlRoleHasUserDAO.read(12)).
                thenReturn(expectedRoleHasUser);
        Mockito.when(mySqlRoleHasUserDAO.read(13)).
                thenReturn(null);

        assertEquals(expectedRoleHasUser, mySqlRoleHasUserDAO.read(12));
        assertNull(mySqlRoleHasUserDAO.read(13));
        assertThrows(IllegalFieldException.class, () ->
                roleHasUserService.read(-1));
    }

    @Test
    public void readByRoleHasUser() throws SQLException, IllegalFieldException, DBException {
        RoleHasUser expectedRoleHasUser =
                RoleHasUser.createRoleHasUser(1, 1);

        Mockito.when(mySqlRoleHasUserDAO.read(1, 1)).
                thenReturn(expectedRoleHasUser);

        assertNull(roleHasUserService.read(1, 2));
        assertNull(roleHasUserService.read(2, 1));

        List<List<Integer>> incorrectData = Arrays.asList(
                Arrays.asList(-1, 1),
                Arrays.asList(1, -1)
        );

        for (List<Integer> incorrectD : incorrectData) {
            assertThrows(IllegalFieldException.class, () ->
                    roleHasUserService.read(incorrectD.get(0), incorrectD.get(1)));
        }
    }

    @Test
    public void create() {
        assertThrows(NullPointerException.class, () ->
                roleHasUserService.create(null));

        for (List<Object> incorrectRoleHasUser : incorrectRoleHasUser) {
            assertThrows(IllegalFieldException.class, () ->
                    roleHasUserService.create(RoleHasUser.createRoleHasUser(
                            Long.parseLong(incorrectRoleHasUser.get(0).toString()),
                            Long.parseLong(incorrectRoleHasUser.get(1).toString())
                    )));
        }
    }

    @Test
    public void update() {
        assertThrows(NullPointerException.class, () ->
                roleHasUserService.update(null));

        for (List<Object> incorrectRoleHasUser : incorrectRoleHasUser) {
            assertThrows(IllegalFieldException.class, () ->
                    roleHasUserService.update(RoleHasUser.createRoleHasUser(
                            Long.parseLong(incorrectRoleHasUser.get(0).toString()),
                            Long.parseLong(incorrectRoleHasUser.get(1).toString())
                    )));
        }
    }

    @Test
    public void delete() {
        assertThrows(NullPointerException.class, () ->
                roleHasUserService.delete(null));

        for (List<Object> incorrectRoleHasUser : incorrectRoleHasUser) {
            assertThrows(IllegalFieldException.class, () ->
                    roleHasUserService.delete(RoleHasUser.createRoleHasUser(
                            Long.parseLong(incorrectRoleHasUser.get(0).toString()),
                            Long.parseLong(incorrectRoleHasUser.get(1).toString())
                    )));
        }
    }
}
