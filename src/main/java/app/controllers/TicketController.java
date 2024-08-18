package app.controllers;

import app.dto.Ticket.RequestTicket;
import app.dto.Ticket.ResponseTicket;
import app.dto.response.RequestResponse;
import app.dto.user.RequestUser;
import app.entities.Ticket;
import app.services.ResponseService;
import app.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ResponseService responseService;

    @PostMapping
    public ResponseEntity<String> open(@Valid @RequestBody RequestTicket request){
        try{
            ticketService.open(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ticket opened successfully.");
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to open ticket: " + err.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseTicket>> index() {
        List<ResponseTicket> tickets = ticketService.index();
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/response")
    public ResponseEntity<String> responseTicket(@Valid @RequestBody RequestResponse request){
        try{
            responseService.store(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ticket Response created successfully.");
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create response: " + err.getMessage());
        }
    }

}
