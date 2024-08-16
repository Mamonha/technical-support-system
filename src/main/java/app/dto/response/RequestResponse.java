package app.dto.response;

import app.entities.Response;
import app.entities.Ticket;
import app.entities.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public class RequestResponse {

    @NotBlank(message = "Description is mandatory")
    @Max(value = 700, message ="Maximum characters 700" )
    private String description;

    @PastOrPresent
    private LocalDateTime dateTime;

    @NotNull
    private User user;

    @NotNull
    private Ticket ticket;

    public Response response(){
        Response response= new Response();
        response.setDescription(this.description);
        response.setDateTime(this.dateTime);
        response.setUser(this.user);
        response.setTicket(this.ticket);
        return response;
    }
}
