package app.ticket;

import app.controllers.TicketController;
import app.dto.Ticket.RequestTicket;
import app.dto.Ticket.ResponseTicket;
import app.dto.category.ResponseCategory;
import app.dto.user.ResponseUser;
import app.enums.Priority;
import app.enums.TicketStatus;
import app.services.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ControllerTicketTest {

    @Mock
    protected TicketService ticketService;

    @InjectMocks
    protected TicketController ticketController;

    @Test
    void testOpenTicket() {
        RequestTicket ticket = new RequestTicket();
        ticket.setTitle("Teste ticket");
        ticket.setDescription("Descricao para ticket");
        ticket.setPriority(Priority.HIGH);
        ticket.setUserId(1L);
        ticket.setCategoryIds(Arrays.asList(1L, 2L));

        when(ticketService.open(ticket)).thenReturn(ticket.ticket());

        ResponseEntity<String> response = ticketController.open(ticket);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Ticket opened successfully.", response.getBody());
    }

    @Test
    void testOpenTicket_Exception() {
        RequestTicket ticket = new RequestTicket();
        ticket.setTitle("Teste ticket");
        ticket.setDescription("Descricao para ticket");
        ticket.setPriority(Priority.HIGH);
        ticket.setUserId(1L);
        ticket.setCategoryIds(Arrays.asList(1L, 2L));

        when(ticketService.open(ticket)).thenThrow(new RuntimeException());

        ResponseEntity<String> response = ticketController.open(ticket);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testIndex(){
        ResponseUser user = new ResponseUser(1L, "Mamonha", "mamonha@example.com", "45544444", "1111111111");
        ResponseCategory category1 = new ResponseCategory(1L, "Teste 1", "Teste description 1");
        ResponseCategory category2 = new ResponseCategory(2L, "Teste 2", "Teste description 2");

        ResponseTicket ticket1 = new ResponseTicket(
                1L,
                "TICKET 1",
                "Descricao ticket 1",
                Priority.HIGH,
                LocalDateTime.now(),
                TicketStatus.OPEN,
                user,
                Arrays.asList(category1, category2)
        );

        ResponseTicket ticket2 = new ResponseTicket(
                2L,
                "TICKET 2",
                "Descricao ticket 2",
                Priority.MEDIUM,
                LocalDateTime.now(),
                TicketStatus.CLOSED,
                user,
                Arrays.asList(category1)
        );

        List<ResponseTicket> tickets = Arrays.asList(ticket1, ticket2);

        when(ticketService.index()).thenReturn(tickets);

        ResponseEntity<List<ResponseTicket>> response = ticketController.index();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void testIndex_Exception(){
        when(ticketService.index()).thenThrow(new RuntimeException());
        ResponseEntity<List<ResponseTicket>> response = ticketController.index();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
