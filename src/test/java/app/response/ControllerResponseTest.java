package app.response;

import app.controllers.ResponseController;
import app.dto.response.ResponseResponse;
import app.services.ResponseService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest

public class ControllerResponseTest {

    @Mock
    private ResponseService responseService;

    @InjectMocks
    private ResponseController responseController;

    @Test
    void testFindById(){
        List<ResponseResponse> responses = Arrays.asList(
                new ResponseResponse(1L, "Response 1", LocalDateTime.now()),
                new ResponseResponse(2L, "Response 2", LocalDateTime.now().minusDays(1)),
                new ResponseResponse(3L, "Response 3", LocalDateTime.now().minusDays(2))
        );
        when(responseService.findByTicket(1L)).thenReturn(responses);
        ResponseEntity<List<ResponseResponse>> response = responseController.findByTicket(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responses, response.getBody());
    }

    @Test
    void testFindById_Exception(){
        when(responseService.findByTicket(1L)).thenThrow(new RuntimeException());
        ResponseEntity<List<ResponseResponse>> response = responseController.findByTicket(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
