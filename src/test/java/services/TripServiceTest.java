package services;

import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Trip;
import exeptions.DBException;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripServiceTest {
    @Mock
    private MySqlTripDAO mySqlTripDAO;
    private TripService tripService;
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

    private final List<List<Object>> incorrectTrips = Arrays.asList(
            Arrays.asList(
                    1L, 1L, true, 500, null,
                    Date.valueOf("2022-12-12"), Trip.Status.PENDING, null),
            Arrays.asList(
                    1L, 1L, true, 500, Date.valueOf("2022-10-10"),
                    null, Trip.Status.PENDING, null),
            Arrays.asList(
                    1L, 1L, true, 500, Date.valueOf("2022-12-12"),
                    Date.valueOf("2022-10-10"), Trip.Status.CONFIRMED, null),
            Arrays.asList(
                    -1L, 1L, true, 500, Date.valueOf("2022-10-10"),
                    Date.valueOf("2022-12-12"), Trip.Status.PENDING, null),
            Arrays.asList(
                    1L, -1L, true, 500, Date.valueOf("2022-10-10"),
                    Date.valueOf("2022-12-12"), Trip.Status.PENDING, null),
            Arrays.asList(
                    1L, 1L, true, -500, Date.valueOf("2022-10-10"),
                    Date.valueOf("2022-12-12"), Trip.Status.CONFIRMED, null),
            Arrays.asList(
                    1L, 1L, true, 500, Date.valueOf("2022-10-10"),
                    Date.valueOf("2022-12-12"), null, null)
    );

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTest() throws IllegalFieldException, DBException {
        tripService = new TripService(mySqlTripDAO);

        List<Trip> expectedLiners = Arrays.asList(
                Trip.createTrip(
                        1, 1, true, 500,
                        Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"),
                        Trip.Status.PENDING, null)
        );

        Mockito.when(mySqlTripDAO.getAll()).
                thenReturn(expectedLiners);

        assertEquals(expectedLiners, mySqlTripDAO.getAll());
        assertNotNull(tripService.getAll());
    }

    @Test
    public void getAllByLiner() throws SQLException, IllegalFieldException, DBException {
        tripService = new TripService(mySqlTripDAO);

        List<Trip> expectedLiners = Arrays.asList(
                Trip.createTrip(
                        1, 1, true, 500,
                        Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"),
                        Trip.Status.PENDING, null)
        );

        Liner testCorrectLiner = Liner.createLiner(
                "liner",
                "description",
                200,
                new ArrayList<>(),
                10,
                Date.valueOf("2022-10-10"),
                Date.valueOf("2022-12-12"));

        Mockito.when(mySqlTripDAO.getAllByLiner(testCorrectLiner)).
                thenReturn(expectedLiners);
        assertEquals(expectedLiners, mySqlTripDAO.getAllByLiner(testCorrectLiner));

        assertThrows(NullPointerException.class, () ->
                tripService.getAllByLiner(null));
        assertNotNull(tripService.getAllByLiner(testCorrectLiner));

        for (List<Object> incorrectLiner : incorrectLiners) {
            assertThrows(IllegalFieldException.class, () ->
                    tripService.getAllByLiner(Liner.createLiner(
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
    public void getAllByUserId() throws SQLException, IllegalFieldException, DBException {
        tripService = new TripService(mySqlTripDAO);

        List<Trip> expectedLiners = Arrays.asList(
                Trip.createTrip(
                        1, 1, true, 500,
                        Date.valueOf("2022-10-10"),
                        Date.valueOf("2022-12-12"),
                        Trip.Status.PENDING, null)
        );

        Mockito.when(mySqlTripDAO.getAllByUserId(12)).
                thenReturn(expectedLiners);

        assertEquals(expectedLiners, mySqlTripDAO.getAllByUserId(12));
        assertNotNull(tripService.getAllByUserId(12));
        assertThrows(IllegalFieldException.class, () ->
                tripService.getAllByUserId(-1));
    }

    @Test
    public void read() throws SQLException, IllegalFieldException, DBException {
        tripService = new TripService(mySqlTripDAO);

        Trip expectedTrip = Trip.createTrip(
                12, 1, true, 500,
                Date.valueOf("2022-10-10"),
                Date.valueOf("2022-12-12"),
                Trip.Status.PENDING, null);

        Mockito.when(mySqlTripDAO.read(12)).
                thenReturn(expectedTrip);
        Mockito.when(mySqlTripDAO.read(13)).
                thenReturn(null);

        assertEquals(expectedTrip, mySqlTripDAO.read(12));
        assertNull(mySqlTripDAO.read(13));
        assertThrows(IllegalFieldException.class, () ->
                tripService.read(-1));
    }

    @Test
    public void readByLinerId() throws SQLException, IllegalFieldException, DBException {
        tripService = new TripService(mySqlTripDAO);

        Trip expectedTrip = Trip.createTrip(
                1, 6, true, 500,
                Date.valueOf("2022-10-10"),
                Date.valueOf("2022-12-12"),
                Trip.Status.PENDING, null);

        Mockito.when(mySqlTripDAO.readByLinerId(6)).
                thenReturn(expectedTrip);
        Mockito.when(mySqlTripDAO.readByLinerId(13)).
                thenReturn(null);

        assertEquals(expectedTrip, mySqlTripDAO.readByLinerId(6));
        assertNull(mySqlTripDAO.readByLinerId(13));
        assertThrows(IllegalFieldException.class, () ->
                tripService.readByLinerId(-1));
    }

    @Test
    public void readByUserIdAndLinerId() throws SQLException, IllegalFieldException {
        tripService = new TripService(mySqlTripDAO);

        Trip expectedTrip = Trip.createTrip(
                3, 3, true, 500,
                Date.valueOf("2022-10-10"),
                Date.valueOf("2022-12-12"),
                Trip.Status.PENDING, null);

        Mockito.when(mySqlTripDAO.readByUserIdAndLinerId(3, 4)).
                thenReturn(expectedTrip);
        Mockito.when(mySqlTripDAO.readByUserIdAndLinerId(4, 4)).
                thenReturn(null);

        assertEquals(expectedTrip, mySqlTripDAO.readByUserIdAndLinerId(3, 4));
        assertNull(mySqlTripDAO.readByUserIdAndLinerId(4, 4));
        assertThrows(IllegalFieldException.class, () ->
                tripService.readByUserIdAndLinerId(-2, 2));
        assertThrows(IllegalFieldException.class, () ->
                tripService.readByUserIdAndLinerId(2, -2));
    }

    public java.util.Date objectToDate(Object object) {
        if (object == null) {
            return null;
        }
        return (java.util.Date) object;
    }

    @Test
    public void create() throws IllegalFieldException {
        tripService = new TripService(mySqlTripDAO);

        assertThrows(NullPointerException.class, () ->
                tripService.create(null));

        for (List<Object> incorrectTrip : incorrectTrips) {
            assertThrows(IllegalFieldException.class, () ->
                    tripService.create(Trip.createTrip(
                            Long.parseLong(incorrectTrip.get(0).toString()),
                            Long.parseLong(incorrectTrip.get(1).toString()),
                            Boolean.parseBoolean(incorrectTrip.get(2).toString()),
                            Double.parseDouble(incorrectTrip.get(3).toString()),
                            (Date) objectToDate(incorrectTrip.get(4)),
                            (Date) objectToDate(incorrectTrip.get(5)),
                            (Trip.Status) incorrectTrip.get(6),
                            (InputStream) incorrectTrip.get(7)
                    )));
        }
    }

    @Test
    public void update() {
        tripService = new TripService(mySqlTripDAO);

        assertThrows(NullPointerException.class, () ->
                tripService.update(null));

        for (List<Object> incorrectTrip : incorrectTrips) {
            assertThrows(IllegalFieldException.class, () ->
                    tripService.update(Trip.createTrip(
                            Long.parseLong(incorrectTrip.get(0).toString()),
                            Long.parseLong(incorrectTrip.get(1).toString()),
                            Boolean.parseBoolean(incorrectTrip.get(2).toString()),
                            Double.parseDouble(incorrectTrip.get(3).toString()),
                            (Date) objectToDate(incorrectTrip.get(4)),
                            (Date) objectToDate(incorrectTrip.get(5)),
                            (Trip.Status) incorrectTrip.get(6),
                            (InputStream) incorrectTrip.get(7)
                    )));
        }
    }

    @Test
    public void updateIsPaid() throws SQLException {
        tripService = new TripService(mySqlTripDAO);

        assertThrows(IllegalFieldException.class, () ->
                tripService.updateIsPaid(false, -1));
    }

    @Test
    public void updateIsStatus() {
        tripService = new TripService(mySqlTripDAO);

        assertThrows(IllegalFieldException.class, () ->
                tripService.updateIsStatus(Trip.Status.valueOf(7), 1));
        assertThrows(IllegalFieldException.class, () ->
                tripService.updateIsStatus(Trip.Status.valueOf(-1), 1));
        assertThrows(IllegalFieldException.class, () ->
                tripService.updateIsStatus(Trip.Status.REQUIRING_PAYMENT, -1));
    }

    @Test
    public void delete() {
        tripService = new TripService(mySqlTripDAO);

        assertThrows(NullPointerException.class, () ->
                tripService.delete(null));

        for (List<Object> incorrectTrip : incorrectTrips) {
            assertThrows(IllegalFieldException.class, () ->
                    tripService.delete(Trip.createTrip(
                            Long.parseLong(incorrectTrip.get(0).toString()),
                            Long.parseLong(incorrectTrip.get(1).toString()),
                            Boolean.parseBoolean(incorrectTrip.get(2).toString()),
                            Double.parseDouble(incorrectTrip.get(3).toString()),
                            (Date) objectToDate(incorrectTrip.get(4)),
                            (Date) objectToDate(incorrectTrip.get(5)),
                            (Trip.Status) incorrectTrip.get(6),
                            (InputStream) incorrectTrip.get(7)
                    )));
        }
    }
}














