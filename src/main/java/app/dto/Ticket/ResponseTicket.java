package app.dto.Ticket;

import app.dto.category.ResponseCategory;
import app.dto.user.ResponseUser;
import app.entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class ResponseTicket {

    private Long id;
    private String title;
    private String description;
    private int priority;
    private LocalDateTime dateTime;
    private int status;
    private ResponseUser user;
    private List<ResponseCategory> categories;

    public static ResponseTicket ticket(Ticket ticket) {
        List<ResponseCategory> categoryDTOs = ticket.getListCategory().stream()
                .map(ResponseCategory::category)
                .collect(Collectors.toList());

        return new ResponseTicket(
            ticket.getId(),
            ticket.getTitle(),
            ticket.getDescription(),
            ticket.getPriority(),
            ticket.getDateTime(),
            ticket.getStatus(),
            ResponseUser.user(ticket.getUser()),
            categoryDTOs
        );
    }
}
