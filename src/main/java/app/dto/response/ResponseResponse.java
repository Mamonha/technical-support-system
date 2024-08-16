package app.dto.response;

import app.entities.Response;
import app.entities.Ticket;
import app.entities.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class ResponseResponse {

    private Long id;
    private String explanation;
    private LocalDateTime dateTime;
    private User person;
    private Ticket ticket;

    private ResponseResponse response (Response response){
        return new ResponseResponse(
            getId(),
            getExplanation(),
            getDateTime(),
            getPerson(),
            getTicket()
        );
    }


}
