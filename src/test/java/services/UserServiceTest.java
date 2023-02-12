package services;

import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.User;
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

public class UserServiceTest {
    @Mock
    private MySqlUserDAO mySqlUserDAO;
    private UserService userService;

    private final List<List<Object>> incorrectUsers = Arrays.asList(
            Arrays.asList(null, "John",
                    "john@ukr.net", "123456"),
            Arrays.asList("John", null,
                    "john@ukr.net", "123456"),
            Arrays.asList("John", "John",
                    null, "123456"),
            Arrays.asList("Jo", "John",
                    "john@ukr.net", null),
            Arrays.asList("John", "Jo",
                    "john@ukr.net", "123456"),
            Arrays.asList("John", "John",
                    "john@ukrnet", "123456"),
            Arrays.asList("John", "John",
                    "johnukr.net", "123456"),
            Arrays.asList("John", "John",
                    "ukr.net", "123456")
    );

    public UserServiceTest() throws IllegalFieldException {
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() throws SQLException, IllegalFieldException, DBException {
        userService = new UserService(mySqlUserDAO);

        List<User> expectedUsers = Arrays.asList(
                User.createUser(
                        "John",
                        "John",
                        "john@ukr.net",
                        "123456")
        );

        Mockito.when(mySqlUserDAO.getAll()).
                thenReturn(expectedUsers);

        assertEquals(expectedUsers, mySqlUserDAO.getAll());
        assertNotNull(userService.getAll());
    }

    @Test
    public void readByIdTest() throws SQLException, IllegalFieldException, DBException {
        userService = new UserService(mySqlUserDAO);

        User expectedUser = new User(
                "John",
                "John",
                "john@ukr.net",
                "123456");

        Mockito.when(mySqlUserDAO.read(7)).
                thenReturn(expectedUser);

        assertEquals(expectedUser, userService.read(7));

        assertNull(userService.read(10));

        assertThrows(IllegalFieldException.class, () ->
                userService.read(-4));
    }

    @Test
    public void readByEmailTest() throws SQLException, IllegalFieldException, DBException {
        userService = new UserService(mySqlUserDAO);

        User expectedUser = new User(
                "John",
                "John",
                "john@ukr.net",
                "123456");

        Mockito.when(mySqlUserDAO.read("john@ukr.net")).
                thenReturn(expectedUser);

        assertEquals(expectedUser, userService.read("john@ukr.net"));

        assertNull(userService.read("john123@ukr.net"));

        List<String> data =
                Arrays.asList(null, "", "john@ukrnet", "johnukr.net", "ukr.net");

        for (String testEmail : data) {
            assertThrows(IllegalFieldException.class, () ->
                    userService.read(testEmail));
        }
    }

    @Test
    public void readByEmailAndPasswordTest() throws SQLException, IllegalFieldException, DBException {
        userService = new UserService(mySqlUserDAO);

        User expectedUser = new User(
                "John",
                "John",
                "john@ukr.net",
                "123456");

        Mockito.when(mySqlUserDAO.read("john@ukr.net", "123456")).
                thenReturn(expectedUser);

        assertEquals(expectedUser, userService.read("john@ukr.net", "123456"));

        assertNull(userService.read("john123@ukr.net", "123456"));
        assertNull(userService.read("john@ukr.net", "123457"));

        List<List<String>> testData = Arrays.asList(
                Arrays.asList(null, "123456"),
                Arrays.asList("", "123456"),
                Arrays.asList("john@ukrnet", "123456"),
                Arrays.asList("johnukr.net", "123456"),
                Arrays.asList("ukr.net", "123456"),
                Arrays.asList("john@ukr.net", null),
                Arrays.asList("john@ukr.net", ""),
                Arrays.asList("john@ukr.net", "12345"),
                Arrays.asList("john@ukr.net", "1234567891234567891")
        );

        for (List<String> testD : testData) {
            assertThrows(IllegalFieldException.class, () ->
                    userService.read(testD.get(0), testD.get(1)));
        }
    }

    @Test
    public void createTest() throws SQLException, IllegalFieldException {
        userService = new UserService(mySqlUserDAO);

        assertThrows(NullPointerException.class, () ->
                userService.create(null));

        for (List<Object> incorrectUser : incorrectUsers) {
            assertThrows(IllegalFieldException.class, () ->
                    userService.create(User.createUser(
                            (String) incorrectUser.get(0),
                            (String) incorrectUser.get(1),
                            (String) incorrectUser.get(2),
                            (String) incorrectUser.get(3)
                    )));
        }
    }

    @Test
    public void updateTest() throws SQLException, IllegalFieldException {
        userService = new UserService(mySqlUserDAO);

        assertThrows(NullPointerException.class, () ->
                userService.update(null));

        for (List<Object> incorrectUser : incorrectUsers) {
            assertThrows(IllegalFieldException.class, () ->
                    userService.update(User.createUser(
                            (String) incorrectUser.get(0),
                            (String) incorrectUser.get(1),
                            (String) incorrectUser.get(2),
                            (String) incorrectUser.get(3)
                    )));
        }
    }

    @Test
    public void deleteTest() throws SQLException, IllegalFieldException {
        userService = new UserService(mySqlUserDAO);

        assertThrows(NullPointerException.class, () ->
                userService.delete(null));

        for (List<Object> incorrectUser : incorrectUsers) {
            assertThrows(IllegalFieldException.class, () ->
                    userService.delete(User.createUser(
                            (String) incorrectUser.get(0),
                            (String) incorrectUser.get(1),
                            (String) incorrectUser.get(2),
                            (String) incorrectUser.get(3)
                    )));
        }
    }
}








