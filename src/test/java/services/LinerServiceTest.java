package services;

import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LinerServiceTest {
    @Mock
    private static MySqlLinerDAO mySqlLinerDAO;
    private static LinerService linerService;

    private final List<List<Object>> incorrectLiners = Arrays.asList(
            Arrays.asList(null, "description", 200, "{rout}",
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", null, 200, "{rout}",
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, null,
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, "{rout}",
                    -10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, "{rout}",
                    10, null, Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, "{rout}",
                    10, Date.valueOf("2022-10-10"), null),
            Arrays.asList("", "description", 200, "{rout}",
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-8-10")),
            Arrays.asList("liner", "description", -200, "{rout}",
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, "{rout}",
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-8-10"))
    );

    @BeforeEach
    public void setup() {
        linerService = new LinerService(mySqlLinerDAO);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() throws IllegalFieldException, DBException {
        List<Liner> expectedLiners = Arrays.asList(
                Liner.createLiner(
                        "liner",
                        "description",
                        200,
                        new ArrayList<>(),
                        10,
                        Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"))
        );

        Mockito.when(mySqlLinerDAO.getAll()).
                thenReturn(expectedLiners);

        assertEquals(expectedLiners, mySqlLinerDAO.getAll());
        assertNotNull(linerService.getAll());
    }

    @Test
    public void getAllPaginationWithDuration() throws IllegalFieldException, DBException {
        List<Liner> expectedLiners = Arrays.asList(
                Liner.createLiner(
                        "liner",
                        "description",
                        200,
                        new ArrayList<>(),
                        10,
                        Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"))
        );

        List<Object> testArguments =
                Arrays.asList(5, Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"), 3, 5);

        Mockito.when(mySqlLinerDAO.getAll(
                        (Integer) testArguments.get(0),
                        (Date) testArguments.get(1),
                        (Date) testArguments.get(2),
                        (Integer) testArguments.get(3),
                        (Integer) testArguments.get(4))
                ).
                thenReturn(expectedLiners);

        assertEquals(expectedLiners, mySqlLinerDAO.getAll(
                (Integer) testArguments.get(0),
                (Date) testArguments.get(1),
                (Date) testArguments.get(2),
                (Integer) testArguments.get(3),
                (Integer) testArguments.get(4))
        );
        assertNotNull(linerService.getAll());

        List<List<Object>> incorrectArguments = Arrays.asList(
                Arrays.asList(-1, Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"), 3, 5),
                Arrays.asList(5, Date.valueOf("2022-12-12"),
                        Date.valueOf("2022-10-10"), 3, 5),
                Arrays.asList(5, Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"), -1, 5),
                Arrays.asList(5, Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"), 3, -1)
        );

        for (List<Object> incorrectArgument : incorrectArguments) {
            assertThrows(IllegalFieldException.class, () ->
                    linerService.getAll(
                            (Integer) incorrectArgument.get(0),
                            (Date) incorrectArgument.get(1),
                            (Date) incorrectArgument.get(2),
                            (Integer) incorrectArgument.get(3),
                            (Integer) incorrectArgument.get(4)));
        }
    }

    @Test
    public void getAllPaginationWithoutDuration() throws IllegalFieldException, DBException {
        linerService = new LinerService(mySqlLinerDAO);
        List<Liner> expectedLiners = Arrays.asList(
                Liner.createLiner(
                        "liner",
                        "description",
                        200,
                        new ArrayList<>(),
                        10,
                        Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"))
        );

        List<Object> testArguments =
                Arrays.asList(Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"), 3, 5);

        Mockito.when(mySqlLinerDAO.getAll(
                        (Date) testArguments.get(0),
                        (Date) testArguments.get(1),
                        (Integer) testArguments.get(2),
                        (Integer) testArguments.get(3))
                ).
                thenReturn(expectedLiners);

        assertEquals(expectedLiners, mySqlLinerDAO.getAll(
                (Date) testArguments.get(0),
                (Date) testArguments.get(1),
                (Integer) testArguments.get(2),
                (Integer) testArguments.get(3))
        );
        assertNotNull(linerService.getAll());

        List<List<Object>> incorrectArguments = Arrays.asList(
                Arrays.asList(Date.valueOf("2022-12-12"),
                        Date.valueOf("2022-10-10"), 3, 5),
                Arrays.asList(Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"), -1, 5),
                Arrays.asList(Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"), 3, -1)
        );

        for (List<Object> incorrectArgs : incorrectArguments) {
            assertThrows(IllegalFieldException.class, () ->
                    linerService.getAll(
                            (Date) incorrectArgs.get(0),
                            (Date) incorrectArgs.get(1),
                            (Integer) incorrectArgs.get(2),
                            (Integer) incorrectArgs.get(3)
                    ));
        }
    }

    @Test
    public void read() throws SQLException, IllegalFieldException, DBException {
        Liner expectedLiner = Liner.createLiner(
                "liner",
                "description",
                200,
                new ArrayList<>(),
                10,
                Date.valueOf("2022-10-10"),
                Date.valueOf("2022-12-12"));

        Mockito.when(mySqlLinerDAO.read(12)).
                thenReturn(expectedLiner);

        assertEquals(expectedLiner, mySqlLinerDAO.read(12));
        assertNull(mySqlLinerDAO.read(13));
        assertThrows(IllegalFieldException.class, () ->
                linerService.read(-1));
    }

    @Test
    public void getAllDurationOfTrip() throws SQLException, DBException {
        List<Integer> expectedDurations = Arrays.asList(1, 2, 3);

        Mockito.when(mySqlLinerDAO.getAllDurationOfTrip()).
                thenReturn(expectedDurations);

        assertEquals(expectedDurations, mySqlLinerDAO.getAllDurationOfTrip());
        assertNotNull(linerService.getAllDurationOfTrip());
    }

    @Test
    public void create() {
        assertThrows(NullPointerException.class, () ->
                linerService.create(null));

        for (List<Object> incorrectLiner : incorrectLiners) {
            assertThrows(IllegalFieldException.class, () ->
                    linerService.create(Liner.createLiner(
                            (String) incorrectLiner.get(0),
                            (String) incorrectLiner.get(1),
                            (Integer) incorrectLiner.get(2),
                            Collections.singletonList(incorrectLiner.get(3).toString()),
                            (Integer) incorrectLiner.get(4),
                            (Date) incorrectLiner.get(5),
                            (Date) incorrectLiner.get(6)
                    )));
        }
    }

    @Test
    public void update() {
        assertThrows(NullPointerException.class, () ->
                linerService.update(null));

        for (List<Object> incorrectLiner : incorrectLiners) {
            assertThrows(IllegalFieldException.class, () ->
                    linerService.update(Liner.createLiner(
                            (String) incorrectLiner.get(0),
                            (String) incorrectLiner.get(1),
                            (Integer) incorrectLiner.get(2),
                            Collections.singletonList(incorrectLiner.get(3).toString()),
                            (Integer) incorrectLiner.get(4),
                            (Date) incorrectLiner.get(5),
                            (Date) incorrectLiner.get(6)
                    )));
        }
    }

    @Test
    public void delete() {
        assertThrows(NullPointerException.class, () ->
                linerService.delete(null));

        for (List<Object> incorrectLiner : incorrectLiners) {
            assertThrows(IllegalFieldException.class, () ->
                    linerService.delete(Liner.createLiner(
                            (String) incorrectLiner.get(0),
                            (String) incorrectLiner.get(1),
                            (Integer) incorrectLiner.get(2),
                            Collections.singletonList(incorrectLiner.get(3).toString()),
                            (Integer) incorrectLiner.get(4),
                            (Date) incorrectLiner.get(5),
                            (Date) incorrectLiner.get(6)
                    )));
        }
    }
}
