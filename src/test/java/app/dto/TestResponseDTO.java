package app.dto;

import app.dto.response.RequestResponse;
import app.dto.response.ResponseResponse;
import app.entities.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestResponseDTO {

    @Test
    void testResponse() {
        RequestResponse request = new RequestResponse();
        request.setDescription("Teste description");

        Response response = request.response();

        assertEquals("Teste description", response.getDescription());
    }

    @Test
    void testResponseFromResponse() {
        Response response = new Response();
        response.setId(1L);
        response.setDateTime(LocalDateTime.now());

        ResponseResponse responseResponse = ResponseResponse.response(response);
        assertEquals(1L, responseResponse.getId());
        assertEquals(response.getDateTime(), responseResponse.getDateTime());
    }
}
