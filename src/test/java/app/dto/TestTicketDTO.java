package app.dto;

import app.dto.Ticket.ResponseTicket;
import app.dto.category.ResponseCategory;
import app.dto.user.ResponseUser;
import app.entities.Category;
import app.entities.Response;
import app.entities.Ticket;
import app.entities.User;
import app.enums.Priority;
import app.enums.TicketStatus;
import app.factory.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTicketDTO {
    @Test
    void testTicketConversion() {
        User user = UserFactory.createCustomUser("Mamonha", "mamonha@example.com", "123456789", "12345678900", 1);

        Category category1 = CategoryFactory.createCustomCategory(1L, "Category 1", "Description for Category 1");
        Category category2 = CategoryFactory.createCustomCategory(2L, "Category 2", "Description for Category 2");
        List<Category> categories = Arrays.asList(category1, category2);
        List<Response> responses = Arrays.asList(ResponseFactory.createDefaultResponse());
        Ticket customTicket = TicketFactory.createCustomTicket(
                2L,
                "Custom Title",
                "Custom Description",
                Priority.LOW,
                LocalDateTime.now(),
                TicketStatus.CLOSED,
                user,
                categories,
                responses

        );

        ResponseTicket responseTicket = ResponseTicket.ticket(customTicket);
        assertEquals(customTicket.getId(), responseTicket.getId());
        assertEquals(customTicket.getTitle(), responseTicket.getTitle());
        assertEquals(customTicket.getDescription(), responseTicket.getDescription());
        assertEquals(Priority.fromValue(customTicket.getPriority()), responseTicket.getPriority());
        assertEquals(customTicket.getDateTime(), responseTicket.getDateTime());
        assertEquals(TicketStatus.fromValue(customTicket.getStatus()), responseTicket.getStatus());
        assertThat(responseTicket.getUser()).usingRecursiveComparison().isEqualTo(ResponseUser.user(customTicket.getUser()));
        assertEquals(2, responseTicket.getCategories().size());
    }
}
