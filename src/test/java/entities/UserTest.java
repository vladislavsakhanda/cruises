package entities;

import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.User;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private final List<List<Object>> incorrectUsers = Arrays.asList(
            Arrays.asList(null, "John",
                    "john@ukr.net", "123456"),
            Arrays.asList("John", null,
                    "john@ukr.net", "123456"),
            Arrays.asList("John", "John",
                    null, "123456"),
            Arrays.asList("John", "Jo",
                    "john@ukr.net", "123456"),
            Arrays.asList("John", "John",
                    "john@ukrnet", "123456"),
            Arrays.asList("John", "John",
                    "johnukr.net", "123456"),
            Arrays.asList("John", "John",
                    "ukr.net", "123456")
    );

    @Test
    public void createUserTest() {
        for (List<Object> incorrectUser : incorrectUsers) {
            assertThrows(IllegalFieldException.class, () ->
                    User.createUser(
                            Objects.nonNull(incorrectUser.get(0))
                                    ? String.valueOf(incorrectUser.get(0)) : null,
                            Objects.nonNull(incorrectUser.get(1))
                                    ? String.valueOf(incorrectUser.get(1)) : null,
                            Objects.nonNull(incorrectUser.get(2))
                                    ? String.valueOf(incorrectUser.get(2)) : null,
                            Objects.nonNull(incorrectUser.get(3))
                                    ? String.valueOf(incorrectUser.get(3)) : null
                    ));
        }
    }

    @Test
    public void createUserWithIdTest() {
        List<List<Object>> incorrectUsersWithId = Arrays.asList(
                Arrays.asList(-1, "John", "John",
                        "john@ukr.net", "123456"),
                Arrays.asList(0, "John", "John",
                        "john@ukr.net", "123456")
        );

        for (List<Object> incorrectUser : incorrectUsers) {
            assertThrows(IllegalFieldException.class, () ->
                    User.createUser(
                            1,
                            Objects.nonNull(incorrectUser.get(0))
                                    ? String.valueOf(incorrectUser.get(0)) : null,
                            Objects.nonNull(incorrectUser.get(1))
                                    ? String.valueOf(incorrectUser.get(1)) : null,
                            Objects.nonNull(incorrectUser.get(2))
                                    ? String.valueOf(incorrectUser.get(2)) : null,
                            Objects.nonNull(incorrectUser.get(3))
                                    ? String.valueOf(incorrectUser.get(3)) : null
                    ));
        }

        for (List<Object> incorrectUserWithId : incorrectUsersWithId) {
            assertThrows(IllegalFieldException.class, () ->
                    User.createUser(
                            Long.parseLong(String.valueOf(incorrectUserWithId.get(0))),
                            Objects.nonNull(incorrectUserWithId.get(0))
                                    ? String.valueOf(incorrectUserWithId.get(0)) : null,
                            Objects.nonNull(incorrectUserWithId.get(1))
                                    ? String.valueOf(incorrectUserWithId.get(1)) : null,
                            Objects.nonNull(incorrectUserWithId.get(2))
                                    ? String.valueOf(incorrectUserWithId.get(2)) : null,
                            Objects.nonNull(incorrectUserWithId.get(3))
                                    ? String.valueOf(incorrectUserWithId.get(3)) : null
                    ));
        }
    }

    @Test
    public void UserTest() {
        assertDoesNotThrow(() -> {
            new User();
        });
    }

    @Test
    public void UserWithIdTest() {
        for (long i = 1; i < 15; i++) {
            long finalI = i;
            assertDoesNotThrow(()
                    -> new User(finalI * 2));
        }

        for (long i = -15; i < 1; i++) {
            long finalI = i;
            assertThrows(IllegalFieldException.class, ()
                    -> new User(finalI * 2));
        }
    }

    @Test
    public void UserWithArgumentsTest() {
        for (List<Object> incorrectUser : incorrectUsers) {
            assertThrows(IllegalFieldException.class, () ->
                    new User(
                            Objects.nonNull(incorrectUser.get(0))
                                    ? String.valueOf(incorrectUser.get(0)) : null,
                            Objects.nonNull(incorrectUser.get(1))
                                    ? String.valueOf(incorrectUser.get(1)) : null,
                            Objects.nonNull(incorrectUser.get(2))
                                    ? String.valueOf(incorrectUser.get(2)) : null,
                            Objects.nonNull(incorrectUser.get(3))
                                    ? String.valueOf(incorrectUser.get(3)) : null
                    ));
        }
    }

    @Test
    public void getNameTest() throws IllegalFieldException {
        User testUser = new User();
        testUser.setName("Anna");

        assertEquals("Anna", testUser.getName());
    }

    @Test
    public void getSurnameTest() throws IllegalFieldException {
        User testUser = new User();
        testUser.setSurname("Anna");

        assertEquals("Anna", testUser.getSurname());
    }

    @Test
    public void getEmailTest() throws IllegalFieldException {
        User testUser = new User();
        testUser.setEmail("Anna@ukr.net");

        assertEquals("Anna@ukr.net", testUser.getEmail());
    }

    @Test
    public void getPasswordTest() throws IllegalFieldException {
        User testUser = new User();
        testUser.setPassword("123456");

        assertEquals("123456", testUser.getPassword());
    }

    @Test
    public void setNameTest() {
        User testUser = new User();

        List<String> correctNames = Arrays.asList("Vlad", "Sergey", "Anton", "Ron");
        List<String> incorrectNames =
                Arrays.asList("", "V", "vl", null, "5314", "aaa2", "a2a3");

        for (String correctName : correctNames) {
            assertDoesNotThrow(() -> testUser.setName(correctName));
        }

        for (String incorrectName : incorrectNames) {
            assertThrows(IllegalFieldException.class, () -> testUser.setName(incorrectName));
        }
    }

    @Test
    public void setSurnameTest() {
        User testUser = new User();

        List<String> correctSurnames = Arrays.asList("Vlad", "Sergey", "Anton", "Ron");
        List<String> incorrectSurnames =
                Arrays.asList("", "V", "vl", null, "5314", "aaa2", "a2a3");

        for (String correctSurname : correctSurnames) {
            assertDoesNotThrow(() -> testUser.setSurname(correctSurname));
        }

        for (String incorrectSurname : incorrectSurnames) {
            assertThrows(IllegalFieldException.class, () -> testUser.setSurname(incorrectSurname));
        }
    }

    @Test
    public void setEmailTest() {
        User testUser = new User();

        List<String> correctEmails = Arrays.asList("john@ukr.net", "John@ukr.net", "john123@ukr.net");
        List<String> incorrectEmails =
                Arrays.asList("", null, "john@ukrnet", "johnukr.net", "ukr.net", "john");

        for (String correctEmail : correctEmails) {
            assertDoesNotThrow(() -> testUser.setEmail(correctEmail));
        }

        for (String incorrectEmail : incorrectEmails) {
            assertThrows(IllegalFieldException.class, () -> testUser.setEmail(incorrectEmail));
        }
    }

    @Test
    public void setPasswordTest() {
        User testUser = new User();

        List<String> correctPasswords =
                Arrays.asList("123456", "e21t54", "A21t4fesf23", "8a1f3(;89@2A1");

        for (String correctPassword : correctPasswords) {
            assertDoesNotThrow(() -> testUser.setPassword(correctPassword));
        }
    }
}
