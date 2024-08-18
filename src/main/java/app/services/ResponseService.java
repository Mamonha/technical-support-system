package app.services;

import app.dto.response.RequestResponse;
import app.entities.Response;
import app.entities.Ticket;
import app.entities.User;
import app.enums.TicketStatus;
import app.repositories.ResponseRepository;
import app.repositories.TicketRepository;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResponseService {
    @Autowired
    protected ResponseRepository responseRepository;
    @Autowired
    protected TicketRepository ticketRepository;
    @Autowired
    protected UserRepository userRepository;

    public Response store(RequestResponse requestResponse) {
        Response response = requestResponse.response();
        response.setDateTime(LocalDateTime.now());
        User user = userRepository.findById(requestResponse.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + requestResponse.getUserId()));
        response.setUser(user);
        Ticket ticket = ticketRepository.findById(requestResponse.getTicketId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid ticket ID: " + requestResponse.getTicketId()));
        ticket.setStatus(TicketStatus.IN_PROGRESS.getValue());
        ticketRepository.save(ticket);

        response.setTicket(ticket);

        return responseRepository.save(response);
    }
}
