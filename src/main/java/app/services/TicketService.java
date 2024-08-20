package app.services;

import app.dto.Ticket.RequestTicket;
import app.dto.Ticket.ResponseTicket;
import app.entities.Category;
import app.entities.Response;
import app.entities.Ticket;
import app.entities.User;
import app.enums.TicketStatus;
import app.repositories.CategoryRepository;
import app.repositories.ResponseRepository;
import app.repositories.TicketRepository;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ResponseService responseService;

    public Ticket open(RequestTicket requestTicket) {
        Ticket ticket = requestTicket.ticket();
        ticket.setStatus(TicketStatus.OPEN.getValue());
        ticket.setDateTime(LocalDateTime.now());

        User user = userRepository.findById(requestTicket.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + requestTicket.getUserId()));
        ticket.setUser(user);

        List<Category> categories = categoryRepository.findAllById(requestTicket.getCategoryIds());
        ticket.setListCategory(categories);

        return ticketRepository.save(ticket);
    }

    public List<ResponseTicket> index() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ResponseTicket::ticket)
                .collect(Collectors.toList());
    }

    public ResponseTicket show (Long id){
        Ticket ticket= this.ticketRepository.findById(id).get();
        return ResponseTicket.ticket(ticket);

    }

    public String destroy (Long id){
        Ticket ticket= ticketRepository.findById(id).get();
        ticket.setUser(null);
        ticketRepository.save(ticket);
        for (Response response: ticket.getListResponse()){
            responseService.destroy(response.getId());
        }
        ticket.getListCategory().clear();
        ticketRepository.deleteById(id);
        return "Ticket deleted successfully";
    }

    public List<ResponseTicket> findByStatus(int status) {
        List<Ticket> tickets = ticketRepository.ticketStatus(status);
        return tickets.stream()
                .map(ResponseTicket::ticket)
                .collect(Collectors.toList());
    }
}
