package entities;

import db.dao.mysql.entity.Entity;
import exeptions.IllegalFieldException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    @Test
    public void EntityTest() {
        assertDoesNotThrow(() -> {
            new Entity();
        });
    }

    @Test
    public void EntityWithIdTest() throws IllegalFieldException {
        for (long i = 1; i < 15; i++) {
            long finalI = i;
            assertDoesNotThrow(()
                    -> new Entity(finalI * 2));
        }

        for (long i = -15; i < 1; i++) {
            long finalI = i;
            assertThrows(IllegalFieldException.class, ()
                    -> new Entity(finalI * 2));
        }
    }

    @Test
    public void getIdTest() throws IllegalFieldException {
        for (long i = 1; i < 50; i++) {
            assertEquals(i, new Entity(i).getId());
        }
    }

    @Test
    public void setIdTest() throws IllegalFieldException {
        Entity testEntity = new Entity();

        for (long i = 1; i < 15; i++) {
            long finalI = i;
            assertDoesNotThrow(() -> {
                testEntity.setId(finalI * 2);
                assertEquals(finalI * 2, testEntity.getId());
            });
        }

        for (long i = -15; i < 0; i++) {
            long finalI = i;
            assertThrows(IllegalFieldException.class, ()
                    -> testEntity.setId(finalI * 2));
        }
    }
}
