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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter

public class TicketSimplified {

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime dateTime;
    private TicketStatus status;
    private String user;
    private List<String> categories;

    public static TicketSimplified tik(Ticket ticket) {

        List <String> categoryName= new ArrayList<>();
        List<ResponseCategory> categoryDTOs = ticket.getListCategory().stream()
                .map(ResponseCategory::category)
                .collect(Collectors.toList());

        for (ResponseCategory categoryDTO : categoryDTOs) {
            categoryName.add(categoryDTO.getName());
        }

        return new TicketSimplified(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                Priority.fromValue(ticket.getPriority()),
                ticket.getDateTime(),
                TicketStatus.fromValue(ticket.getStatus()),
                ResponseUser.user(ticket.getUser()).getNome(),
                categoryName
        );
    }
}
