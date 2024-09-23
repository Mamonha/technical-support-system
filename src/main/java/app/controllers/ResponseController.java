package app.controllers;

import app.dto.Ticket.ResponseTicket;
import app.dto.response.ResponseResponse;
import app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/response")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @GetMapping("/ticket/{id}")
    public ResponseEntity<List<ResponseResponse>> findByTicket(@PathVariable Long id) {
        try {
            List <ResponseResponse> responses = responseService.findByTicket(id);
            return ResponseEntity.ok(responses);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
