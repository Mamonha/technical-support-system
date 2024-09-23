package app.factory;

import app.entities.Response;

import java.time.LocalDateTime;

public class ResponseFactory {

    public static Response createDefaultResponse() {
        Response response = new Response();
        response.setId(1L);
        response.setDescription("Teste response");
        response.setDateTime(LocalDateTime.now());
        response.setUser(UserFactory.createDefaultUser());
        response.setTicket(TicketFactory.createDefaultTicket());
        return response;
    }
}
