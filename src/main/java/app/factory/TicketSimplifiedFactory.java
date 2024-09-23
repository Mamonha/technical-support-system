package app.factory;

import app.dto.Ticket.TicketSimplified;
import app.enums.Priority;
import app.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TicketSimplifiedFactory {
    public static TicketSimplified createDefault() {
        return new TicketSimplified(
                1L,
                "Test titulo",
                "Test descript",
                Priority.HIGH, //
                LocalDateTime.now(),
                TicketStatus.OPEN,
                "Chad",
                new ArrayList<>()
        );
    }
}
