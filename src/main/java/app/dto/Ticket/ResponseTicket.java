package app.dto.Ticket;

import app.dto.category.ResponseCategory;
import app.dto.user.ResponseUser;
import app.entities.Ticket;
import app.enums.Priority;
import app.enums.TicketStatus;
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
    private Priority priority;
    private LocalDateTime dateTime;
    private TicketStatus status;
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
            Priority.fromValue(ticket.getPriority()),
            ticket.getDateTime(),
            TicketStatus.fromValue(ticket.getStatus()),
            ResponseUser.user(ticket.getUser()),
            categoryDTOs
        );
    }
}
