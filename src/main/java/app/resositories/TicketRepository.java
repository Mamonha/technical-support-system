package app.resositories;

import app.entities.Response;
import app.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository <Ticket, Long> {
}