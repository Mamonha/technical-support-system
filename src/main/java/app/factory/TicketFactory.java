package app.factory;

import app.entities.Category;
import app.entities.Response;
import app.entities.Ticket;
import app.entities.User;
import app.enums.Priority;
import app.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static app.factory.UserFactory.createDefaultUser;

public class TicketFactory {

    public static Ticket createDefaultTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Test titulo");
        ticket.setDescription("Test Description");
        ticket.setPriority(Priority.HIGH.getValue());
        ticket.setDateTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.OPEN.getValue());
        ticket.setUser(createDefaultUser());
        ticket.setListCategory(new ArrayList<>());
        ticket.setListResponse(new ArrayList<>());
        return ticket;
    }

    public static Ticket createCustomTicket(
            Long id,
            String title,
            String description,
            Priority priority,
            LocalDateTime dateTime,
            TicketStatus status,
            User user,
            List<Category> categories,
            List<Response> responses
    ) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority.getValue());
        ticket.setDateTime(dateTime);
        ticket.setStatus(status.getValue());
        ticket.setUser(user);
        ticket.setListCategory(categories);
        ticket.setListResponse(responses);
        return ticket;
    }

}
