package entities;

import db.dao.mysql.entity.Entity;
import db.dao.mysql.entity.Liner;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LinerTest {
    private final List<List<Object>> incorrectLiners = Arrays.asList(
            Arrays.asList(null, "description", 200, new ArrayList<>(),
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", null, 200, "{rout}",
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, null,
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, new ArrayList<>(),
                    -10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, new ArrayList<>(),
                    10, null, Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, new ArrayList<>(),
                    10, Date.valueOf("2022-10-10"), null),
            Arrays.asList("", "description", 200, new ArrayList<>(),
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-8-10")),
            Arrays.asList("liner", "description", -200, new ArrayList<>(),
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-12-12")),
            Arrays.asList("liner", "description", 200, new ArrayList<>(),
                    10, Date.valueOf("2022-10-10"), Date.valueOf("2022-8-10"))
    );

    @Test
    public void createLinerTest() {
        for (List<Object> incorrectLiner : incorrectLiners) {
            assertThrows(IllegalFieldException.class, () ->
                    Liner.createLiner(
                            (String) incorrectLiner.get(0),
                            (String) incorrectLiner.get(1),
                            (Integer) incorrectLiner.get(2),
                            Collections.singletonList(incorrectLiner.get(3).toString()),
                            (Integer) incorrectLiner.get(4),
                            (Date) incorrectLiner.get(5),
                            (Date) incorrectLiner.get(6)
                    ));
        }
    }

    @Test
    public void Liner() {
        assertDoesNotThrow(() -> {
            new Liner();
        });
    }

    @Test
    public void LinerWithIdTest() {
        for (long i = 1; i < 15; i++) {
            long finalI = i;
            assertDoesNotThrow(() -> new Liner(finalI * 2));
        }

        for (long i = -15; i < 1; i++) {
            long finalI = i;
            assertThrows(IllegalFieldException.class, () -> new Liner(finalI * 2));
        }
    }

    @Test
    public void LinerWithArgumentsTest() {
        for (List<Object> incorrectLiner : incorrectLiners) {
            assertThrows(IllegalFieldException.class, () ->
                    new Liner(
                            (String) incorrectLiner.get(0),
                            (String) incorrectLiner.get(1),
                            (Integer) incorrectLiner.get(2),
                            Collections.singletonList(incorrectLiner.get(3).toString()),
                            (Integer) incorrectLiner.get(4),
                            (Date) incorrectLiner.get(5),
                            (Date) incorrectLiner.get(6)
                    ));
        }
    }

    @Test
    public void datesCheckTest() throws IllegalFieldException {
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
    public void setNameTest() {
        Liner testLiner = new Liner();

        List<String> correctNames = Arrays.asList("Liner super trip in Egypt!", "L", "Liner1");
        List<String> incorrectNames = Arrays.asList("", null);

        for (String correctName : correctNames) {
            assertDoesNotThrow(() -> testLiner.setName(correctName));
        }

        for (String incorrectName : incorrectNames) {
            assertThrows(IllegalFieldException.class, () -> testLiner.setName(incorrectName));
        }
    }

    @Test
    public void setDescriptionTest() {
        Liner testLiner = new Liner();

        List<String> correctDescriptions = Arrays.asList("Description of liner", "", "default description");

        for (String correctDescription : correctDescriptions) {
            assertDoesNotThrow(() -> testLiner.setDescription(correctDescription));
        }

        assertThrows(IllegalFieldException.class, ()
                -> testLiner.setDescription(null));
    }

    @Test
    public void setCapacityTest() {
        Liner testLiner = new Liner();

        int[] correctCapacities = new int[]{0, 1000, 41, 999};
        int[] incorrectCapacities = new int[]{-1, -15, -45, -100};

        for (int correctCapacity : correctCapacities) {
            assertDoesNotThrow(() -> testLiner.setCapacity(correctCapacity));
        }

        for (int incorrectCapacity : incorrectCapacities) {
            assertThrows(IllegalFieldException.class, ()
                    -> testLiner.setCapacity(incorrectCapacity));
        }
    }

    @Test
    public void setRouteTest() {
        Liner testLiner = new Liner();

        List<String> correctRoutes = Arrays.asList("basic route", "");

        for (String correctRoute : correctRoutes) {
            assertDoesNotThrow(() -> testLiner.setRoute(Collections.singletonList(correctRoute)));
        }

        assertThrows(IllegalFieldException.class, ()
                -> testLiner.setRoute(null));
    }

    @Test
    public void setPriceCoefficientTest() {
        Liner testLiner = new Liner();

        double[] correctPriceCoefficients = new double[]{1.5, 7, 14, 1, 9.14, 0.01};
        double[] incorrectPriceCoefficients = new double[]{0, -1, -13, 0.0, -1.1, -0.01};

        for (double correctPriceCoefficient : correctPriceCoefficients) {
            assertDoesNotThrow(() -> testLiner.setPriceCoefficient(correctPriceCoefficient));
        }

        for (double incorrectPriceCoefficient : incorrectPriceCoefficients) {
            assertThrows(IllegalFieldException.class, ()
                    -> testLiner.setPriceCoefficient(incorrectPriceCoefficient));
        }
    }

    @Test
    public void getNameTest() throws IllegalFieldException {
        Liner testLiner = new Liner();
        testLiner.setName("Anna");

        assertEquals("Anna", testLiner.getName());
    }


    @Test
    public void getDescriptionTest() throws IllegalFieldException {
        Liner testLiner = new Liner();
        testLiner.setDescription("Basic description");

        assertEquals("Basic description", testLiner.getDescription());
    }

    @Test
    public void getCapacityTest() throws IllegalFieldException {
        Liner testLiner = new Liner();
        testLiner.setCapacity(455);

        assertEquals(455, testLiner.getCapacity());
    }

    @Test
    public void getRouteTest() throws IllegalFieldException {
        Liner testLiner = new Liner();
        testLiner.setRoute(new ArrayList<>());

        assertEquals("{route1}", testLiner.getRoute());
    }

    @Test
    public void getPriceCoefficientTest() throws IllegalFieldException {
        Liner testLiner = new Liner();
        testLiner.setPriceCoefficient(5.43);

        assertEquals(5.43, testLiner.getPriceCoefficient());
    }

}
