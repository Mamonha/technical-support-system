package app.controllers;

import app.dto.Ticket.RequestTicket;
import app.dto.Ticket.ResponseTicket;
import app.dto.Ticket.TicketSimplified;
import app.dto.response.RequestResponse;
import app.dto.user.RequestUser;
import app.entities.Ticket;
import app.enums.TicketStatus;
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
        try {
            List<ResponseTicket> tickets = ticketService.index();
            return ResponseEntity.ok(tickets);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping ("/{id}")
    public ResponseEntity<ResponseTicket> show(@PathVariable Long id) {
        try {
            ResponseTicket ticket = ticketService.show(id);
            return ResponseEntity.ok(ticket);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
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

    @PostMapping("/close")
    public ResponseEntity<String> closeTicket(@Valid @RequestBody RequestResponse request){
        try{
            responseService.close(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("ticket closed successfully.");
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to closed ticket: " + err.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <String> destroy (@PathVariable Long id){
        try {
            String mensagem = this.ticketService.destroy(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketSimplified>> findByStatus(@PathVariable int status) {
        try {
            List<TicketSimplified> tickets = ticketService.findByStatus(status);
            return ResponseEntity.ok(tickets);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/waiting/{status}")
    public ResponseEntity<List<TicketSimplified>> findAllByOrderBydateTimedAsc(@PathVariable int status) {
        try {
            List<TicketSimplified> tickets = ticketService.orderBydateTimed(status);
            return ResponseEntity.ok(tickets);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

}
