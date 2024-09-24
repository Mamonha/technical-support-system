package app.services;

import app.dto.Ticket.ResponseTicket;
import app.dto.response.RequestResponse;
import app.dto.response.ResponseResponse;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    @Autowired
    protected ResponseRepository responseRepository;
    @Autowired
    protected TicketRepository ticketRepository;
    @Autowired
    protected UserRepository userRepository;

    public Response findUserAndTicket(RequestResponse requestResponse) {
        Response response = requestResponse.response();
        response.setDateTime(LocalDateTime.now());
        User user = userRepository.findById(requestResponse.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + requestResponse.getUserId()));
        response.setUser(user);
        Ticket ticket = ticketRepository.findById(requestResponse.getTicketId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket ID: " + requestResponse.getTicketId()));
        response.setTicket(ticket);
        return response;
    }

    public Response store(RequestResponse requestResponse) {
        Response response = findUserAndTicket(requestResponse);
        response.getTicket().setStatus(TicketStatus.IN_PROGRESS.getValue());
        ticketRepository.save(response.getTicket());
        return responseRepository.save(response);
    }

    public Response close(RequestResponse requestResponse){
        Response response= findUserAndTicket(requestResponse);
        response.getTicket().setStatus(TicketStatus.CLOSED.getValue());
        ticketRepository.save(response.getTicket());
        return responseRepository.save(response);
    }

    public String destroy (Long id){
        responseRepository.deleteById(id);
        return "Response deleted successfully";
    }

    public List<ResponseResponse> findByTicket(Long id) {
        List<Response> responsesForTicket = responseRepository.findByTicketId(id);
        return responsesForTicket.stream()
                .map(ResponseResponse::response)
                .collect(Collectors.toList());
    }
}
