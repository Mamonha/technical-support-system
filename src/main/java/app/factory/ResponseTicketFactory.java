package app.factory;

import app.dto.Ticket.ResponseTicket;
import app.dto.category.ResponseCategory;
import app.dto.user.ResponseUser;
import app.enums.Priority;
import app.enums.TicketStatus;

import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ResponseTicketFactory {

    public static ResponseTicket createDefaultTicket() {
        return new ResponseTicket(1L, "Titulo padrao", "Descricao padrao", Priority.HIGH, LocalDateTime.now(), TicketStatus.OPEN,
                new ResponseUser(1l, "Teste usuario", "teste@example.com", "5555555555555", "123232313232"),
                Arrays.asList(new ResponseCategory(1L, "Teste category", "category description")));
    }

}
