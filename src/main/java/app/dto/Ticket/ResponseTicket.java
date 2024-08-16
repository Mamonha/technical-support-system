package app.dto.Ticket;

import app.entities.Ticket;
import app.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ResponseTicket {

    private Long id;
    private String label;
    private String explanation;
    private int privilege;
    private LocalDateTime dateTime;
    private int Ticketstatus;
    private User person;

    public static ResponseTicket ticket(Ticket ticket){
        return new ResponseTicket(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getDateTime(),
                ticket.getStatus(),
                ticket.getUser()
        );
    }
}
