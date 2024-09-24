package app.ticket;

import app.dto.Ticket.RequestTicket;
import app.entities.Ticket;
import app.enums.Priority;
import app.repositories.TicketRepository;
import app.services.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTicketIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketService ticketService;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    void testOpenTicketIntegration_Exception() throws Exception {
        RequestTicket requestTicket = new RequestTicket();
        requestTicket.setTitle("Teste ticket e service");
        requestTicket.setDescription("Descricao ticket");
        requestTicket.setPriority(Priority.MEDIUM);
        requestTicket.setUserId(-99L);
        requestTicket.setCategoryIds(Arrays.asList(1L, 2L));

        when(ticketRepository.save(any(Ticket.class))).thenReturn(new Ticket());

        String request = new ObjectMapper().writeValueAsString(requestTicket);

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to open ticket: Invalid user ID: -99"));
    }

    @Test
    void testOpenTicketIntegration() throws Exception{
        RequestTicket requestTicket = new RequestTicket();
        requestTicket.setTitle("Teste ticket e service");
        requestTicket.setDescription("Descricao ticket");
        requestTicket.setPriority(Priority.MEDIUM);
        requestTicket.setUserId(1L);
        requestTicket.setCategoryIds(Arrays.asList(1L, 2L));

        when(ticketRepository.save(any(Ticket.class))).thenReturn(new Ticket());

        String request = new ObjectMapper().writeValueAsString(requestTicket);

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().string("Ticket opened successfully."));
    }
}