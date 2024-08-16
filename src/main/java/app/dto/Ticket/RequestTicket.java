package app.dto.Ticket;

import app.entities.Ticket;
import app.entities.User;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestTicket {

    @NotBlank(message = "title is mandatory")
    @Size(min = 2, max = 120, message = "Title must be between 2 and 120 characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Max(value = 700,  message ="Maximum characters 700")
    private String description;

    @NotNull
    private int priority;

    @PastOrPresent
    private LocalDateTime dateTime;

    @NotNull
    private int status;

    @NotNull
    private User user;

    public Ticket ticket (){
        Ticket ticket= new Ticket();
        ticket.setTitle(this.title);
        ticket.setDescription(this.description);
        ticket.setPriority(this.priority);
        ticket.setDateTime(this.dateTime);
        ticket.setStatus(this.status);
        ticket.setUser(this.user);
        return ticket;
    }

}
