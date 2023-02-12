package entities;

import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.RoleHasUser;
import db.dao.mysql.entity.Trip;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripTest {
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

    @Test
    public void datesCheckTest() {
        assertDoesNotThrow(() -> {
            Liner.datesCheck(
                    Date.valueOf("2022-10-10"),
                    Date.valueOf("2022-12-12")
            );
        });

        assertDoesNotThrow(() -> {
            Liner.datesCheck(
                    Date.valueOf("2022-10-10"),
                    Date.valueOf("2022-10-10")
            );
        });

        assertThrows(IllegalFieldException.class, () -> {
            Liner.datesCheck(
                    Date.valueOf("2022-10-10"),
                    Date.valueOf("2022-8-10")
            );
        });
    }

    @Test
    public void createTripTest() {
        for (List<Object> incorrectTrip : incorrectTrips) {
            assertThrows(IllegalFieldException.class, () ->
                    Trip.createTrip(
                            Long.parseLong(incorrectTrip.get(0).toString()),
                            Long.parseLong(incorrectTrip.get(1).toString()),
                            Boolean.parseBoolean(incorrectTrip.get(2).toString()),
                            Double.parseDouble(incorrectTrip.get(3).toString()),
                            (Date) incorrectTrip.get(4),
                            (Date) incorrectTrip.get(5),
                            (Trip.Status) incorrectTrip.get(6),
                            (InputStream) incorrectTrip.get(7)
                    ));
        }
    }

    @Test
    public void TripTest() {
        assertDoesNotThrow(() -> {
            new Trip();
        });
    }

    @Test
    public void TripWithIdTest() {
        for (long i = 1; i < 15; i++) {
            long finalI = i;
            assertDoesNotThrow(()
                    -> new Trip(finalI * 2));
        }

        for (long i = -15; i < 1; i++) {
            long finalI = i;
            assertThrows(IllegalFieldException.class, ()
                    -> new Trip(finalI * 2));
        }
    }

    @Test
    public void TripWithArgumentsTest() {
        for (List<Object> incorrectTrip : incorrectTrips) {
            assertThrows(IllegalFieldException.class, () ->
                    new Trip(
                            Long.parseLong(incorrectTrip.get(0).toString()),
                            Long.parseLong(incorrectTrip.get(1).toString()),
                            Boolean.parseBoolean(incorrectTrip.get(2).toString()),
                            Double.parseDouble(incorrectTrip.get(3).toString()),
                            (Date) incorrectTrip.get(4),
                            (Date) incorrectTrip.get(5),
                            (Trip.Status) incorrectTrip.get(6),
                            (InputStream) incorrectTrip.get(7)
                    ));
        }
    }

    @Test
    public void setUserIdTest() {
        Trip testTrip = new Trip();
        long[] correctUserIds = new long[]{1, 5, 10};
        long[] incorrectUserIds = new long[]{0, -5, -10};

        for (long correctUserId : correctUserIds) {
            assertDoesNotThrow(() -> testTrip.setUserId(correctUserId));
        }

        for (long incorrectUserId : incorrectUserIds) {
            assertThrows(IllegalFieldException.class, ()
                    -> testTrip.setUserId(incorrectUserId));
        }
    }

    @Test
    public void setLinerIdTest() {
        Trip testTrip = new Trip();
        long[] correctLinerIds = new long[]{1, 5, 10};
        long[] incorrectLinerIds = new long[]{0, -5, -10};

        for (long correctLinerId : correctLinerIds) {
            assertDoesNotThrow(() -> testTrip.setLinerId(correctLinerId));
        }

        for (long incorrectLinerId : incorrectLinerIds) {
            assertThrows(IllegalFieldException.class, ()
                    -> testTrip.setLinerId(incorrectLinerId));
        }
    }

    @Test
    public void setPriceTest() {
        Trip testTrip = new Trip();
        long[] correctPrices = new long[]{0, 1, 5, 10};
        long[] incorrectPrices = new long[]{-1, -5, -10};

        for (long correctPrice : correctPrices) {
            assertDoesNotThrow(() -> testTrip.setPrice(correctPrice));
        }

        for (long incorrectPrice : incorrectPrices) {
            assertThrows(IllegalFieldException.class, ()
                    -> testTrip.setPrice(incorrectPrice));
        }
    }

    @Test
    public void setDateStartTest() {
        assertThrows(IllegalFieldException.class, ()
                -> new Trip().setDateStart(null));
    }

    @Test
    public void setDateEndTest() {
        assertThrows(IllegalFieldException.class, ()
                -> new Trip().setDateEnd(null));
    }

    @Test
    public void setPassportTest() {

    }

    @Test
    public void setIsPaidTest() {
        Trip testTrip = new Trip();

        testTrip.setIsPaid(true);
        assertTrue(testTrip.getIsPaid());

        testTrip.setIsPaid(false);
        assertFalse(testTrip.getIsPaid());
    }

    @Test
    public void getUserIdTest() throws IllegalFieldException {
        for (long i = 1; i < 15; i++) {
            Trip testTrip = new Trip();
            testTrip.setUserId(i * 2);
            assertEquals(i * 2, testTrip.getUserId());
        }
    }

    @Test
    public void getLinerIdTest() throws IllegalFieldException {
        for (long i = 1; i < 15; i++) {
            Trip testTrip = new Trip();
            testTrip.setLinerId(i * 2);
            assertEquals(i * 2, testTrip.getLinerId());
        }
    }

    @Test
    public void getPriceTest() throws IllegalFieldException {
        for (long i = 1; i < 15; i++) {
            Trip testTrip = new Trip();
            testTrip.setPrice(i * 0.33);
            assertEquals(i * 0.33, testTrip.getPrice());
        }
    }

    @Test
    public void getDateStartTest() throws IllegalFieldException {
        List<java.sql.Date> testDates = Arrays.asList(
                Date.valueOf("2012-2-4"),
                Date.valueOf("2020-6-3"),
                Date.valueOf("2021-1-9"),
                Date.valueOf("2011-10-1"),
                Date.valueOf("2015-1-12")
        );

        for (Date testDate : testDates) {
            Trip testTrip = new Trip();
            testTrip.setDateStart(testDate);
            assertEquals(testDate, testTrip.getDateStart());
        }
    }

    @Test
    public void getDateEndTest() throws IllegalFieldException {
        List<java.sql.Date> testDates = Arrays.asList(
                Date.valueOf("2012-2-4"),
                Date.valueOf("2020-6-3"),
                Date.valueOf("2021-1-9"),
                Date.valueOf("2011-10-1"),
                Date.valueOf("2015-1-12")
        );

        for (Date testDate : testDates) {
            Trip testTrip = new Trip();
            testTrip.setDateEnd(testDate);
            assertEquals(testDate, testTrip.getDateEnd());
        }
    }

    @Test
    public void getPassportTest() throws IllegalFieldException {
        Trip testTrip = new Trip();

        testTrip.setPassport(null);
        assertNull(testTrip.getPassport());
    }

    @Test
    public void getIsPaidTest() {
        Trip testTrip = new Trip();

        testTrip.setIsPaid(true);
        assertTrue(testTrip.getIsPaid());

        testTrip.setIsPaid(false);
        assertFalse(testTrip.getIsPaid());
    }
}
