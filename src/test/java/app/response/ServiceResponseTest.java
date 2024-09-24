package app.response;

import app.dto.response.RequestResponse;
import app.dto.response.ResponseResponse;
import app.entities.Response;
import app.entities.Ticket;
import app.entities.User;
import app.enums.TicketStatus;
import app.factory.RequestResponseFactory;
import app.factory.ResponseFactory;
import app.factory.TicketFactory;
import app.factory.UserFactory;
import app.repositories.ResponseRepository;
import app.repositories.TicketRepository;
import app.repositories.UserRepository;
import app.services.ResponseService;
import app.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceResponseTest {

    @Autowired
    private ResponseService responseService;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ResponseRepository responseRepository;

    private RequestResponse requestResponse;
    private User user;
    private Ticket ticket;

    private Response response1;
    private Response response2;
    private List<Response> responseList;

    @BeforeEach
    void setUp() {
        requestResponse = RequestResponseFactory.createDefaultRequestResponse();
        user = UserFactory.createDefaultUser();
        ticket = TicketFactory.createDefaultTicket();
        when(userRepository.findById(requestResponse.getUserId())).thenReturn(Optional.of(user));
        when(ticketRepository.findById(requestResponse.getTicketId())).thenReturn(Optional.of(ticket));

        response1 = new Response();
        response2 = new Response();
        responseList = Arrays.asList(response1, response2);
    }

    @Test
    void testFindUserAndTicket() {
        Response response = responseService.findUserAndTicket(requestResponse);

        assertNotNull(response);
        assertEquals(user, response.getUser());
        assertEquals(ticket, response.getTicket());
        assertNotNull(response.getDateTime());
    }

    @Test
    void testFindUserAndTicket_InvalidUser() {
        RequestResponse invalidRequestResponse = new RequestResponse();
        invalidRequestResponse.setUserId(-1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            responseService.findUserAndTicket(invalidRequestResponse);
        });
        assertEquals("Invalid user ID: -1", exception.getMessage());
    }

    @Test
    void testFindUserAndTicket_InvalidTicket(){
        RequestResponse invalidRequestResponse = new RequestResponse();
        invalidRequestResponse.setUserId(1L);
        invalidRequestResponse.setTicketId(-1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            responseService.findUserAndTicket(invalidRequestResponse);
        });

        assertEquals("Invalid ticket ID: -1", exception.getMessage());
    }

    @Test
    void testDestroy() {
        long id = 1L;
        String result = responseService.destroy(id);
        assertEquals("Response deleted successfully", result);
        verify(responseRepository).deleteById(id);
    }

    @Test
    void testFindByTicket() {
        Long ticketId = 1L;
        when(responseRepository.findByTicketId(ticketId)).thenReturn(responseList);
        List<ResponseResponse> responseResponses = responseService.findByTicket(ticketId);
        assertEquals(2, responseResponses.size());
    }

}


