package app.ticket;
import app.controllers.TicketController;
import app.dto.Ticket.ResponseTicket;
import app.dto.Ticket.TicketSimplified;
import app.dto.response.RequestResponse;
import app.enums.Priority;
import app.enums.TicketStatus;
import app.factory.ResponseTicketFactory;
import app.factory.TicketSimplifiedFactory;
import app.services.ResponseService;
import app.services.TicketService;
import ch.qos.logback.classic.encoder.JsonEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TicketController.class)
public class ControllerTicketMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private ResponseService responseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testShow() throws Exception {
        ResponseTicket ticket = ResponseTicketFactory.createDefaultTicket();
        when(ticketService.show(1L)).thenReturn(ticket);

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticket.getId()))
                .andExpect(jsonPath("$.title").value(ticket.getTitle()));
    }

    @Test
    void testShow_Exception() throws Exception{
        when(ticketService.show(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testResponseTicket() throws Exception {
        RequestResponse request = new RequestResponse();
        request.setTicketId(1L);
        request.setDescription("Teste description");
        request.setUserId(1L);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/tickets/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Ticket Response created successfully."));
    }

    @Test
    void testResponseTicket_Exception() throws Exception {
        doThrow(new RuntimeException()).when(responseService).store(any(RequestResponse.class));
        RequestResponse request = new RequestResponse();
        request.setTicketId(1L);
        request.setDescription("Teste description");
        request.setUserId(1L);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/tickets/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Failed to create response:")));
    }

    @Test
    void testCloseTicket() throws Exception {
        RequestResponse request = new RequestResponse();
        request.setTicketId(1L);
        request.setDescription("Closing ticket");
        request.setUserId(1L);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/tickets/close")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("ticket closed successfully."));
    }

    @Test
    void testCloseTicket_Exception() throws Exception {
        doThrow(new RuntimeException()).when(responseService).close(any(RequestResponse.class));

        RequestResponse request = new RequestResponse();
        request.setTicketId(1L);
        request.setDescription("Closing ticket");
        request.setUserId(1L);
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/tickets/close")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Failed to closed ticket:")));
    }

    @Test
    void testDestroy() throws Exception {
        when(ticketService.destroy(1L)).thenReturn("Ticket deleted successfully.");

        mockMvc.perform(delete("/api/tickets/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket deleted successfully."));
    }

    @Test
    void testDestroy_Exception() throws Exception {
        Long ticketId = 1L;
        when(ticketService.destroy(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/api/tickets/{id}", ticketId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindByStatus() throws Exception {
        int status = TicketStatus.OPEN.getValue();
        List<TicketSimplified> expectedTickets = new ArrayList<>();
        expectedTickets.add(TicketSimplifiedFactory.createDefault());

        when(ticketService.findByStatus(status)).thenReturn(expectedTickets);

        mockMvc.perform(get("/api/tickets/status/{status}", status))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testFindByStatus_Exception() throws Exception {
        int status = TicketStatus.OPEN.getValue();
        doThrow(new RuntimeException("Service error")).when(ticketService).findByStatus(status);

        mockMvc.perform(get("/api/tickets/status/{status}", status))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindAllByOrderBydateTimed() throws Exception {
        int status = TicketStatus.OPEN.getValue();
        List<TicketSimplified> expectedTickets = new ArrayList<>();
        expectedTickets.add(new TicketSimplified(1L, "Title", "Description", Priority.LOW, LocalDateTime.now(), TicketStatus.OPEN, "user", new ArrayList<>()));

        when(ticketService.orderBydateTimed(status)).thenReturn(expectedTickets);

        mockMvc.perform(get("/api/tickets/waiting/{status}", status))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testFindAllByOrderBydateTimed_Exception() throws Exception {
        int status = TicketStatus.OPEN.getValue();
        doThrow(new RuntimeException("Service error")).when(ticketService).orderBydateTimed(status);

        mockMvc.perform(get("/api/tickets/waiting/{status}", status))
                .andExpect(status().isBadRequest());
    }

}
