package app.enums;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PriorityTests {

    @Test
    public void testFromValue_ValidValues() {
        assertEquals(Priority.LOW, Priority.fromValue(1));
        assertEquals(Priority.MEDIUM, Priority.fromValue(2));
        assertEquals(Priority.HIGH, Priority.fromValue(3));
    }

    @Test
    public void testFromValue_InvalidValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Priority.fromValue(4);
        });

        assertEquals("Invalid priority value: 4", exception.getMessage());
    }
}
