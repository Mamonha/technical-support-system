package app.dto.Ticket;

import app.entities.Ticket;
import app.entities.User;
import app.enums.Priority;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RequestTicket {

    @NotBlank(message = "title is mandatory")
    @Size(min = 2, max = 120, message = "Title must be between 2 and 120 characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull
    private Priority priority;

    @NotNull
    private Long userId;

    @NotNull
    private List<Long> categoryIds;

    public Ticket ticket (){
        Ticket ticket= new Ticket();
        ticket.setTitle(this.title);
        ticket.setDescription(this.description);
        ticket.setPriority(this.priority.getValue());
        return ticket;
    }
}
