package app.enums;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketStatusTests {

    @Test
    public void testFromValue_ValidValues() {
        assertEquals(TicketStatus.OPEN, TicketStatus.fromValue(1));
        assertEquals(TicketStatus.IN_PROGRESS, TicketStatus.fromValue(2));
        assertEquals(TicketStatus.CLOSED, TicketStatus.fromValue(3));
    }

    @Test
    public void testFromValue_InvalidValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TicketStatus.fromValue(4);
        });

        assertEquals("Invalid ticket status value: 4", exception.getMessage());
    }
}
