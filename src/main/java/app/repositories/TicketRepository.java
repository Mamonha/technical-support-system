package app.repositories;

import app.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository <Ticket, Long> {

    @Query("select t from Ticket t where t.status = :status")
    public List<Ticket> ticketStatus(@Param("status") int status);

    @Query("SELECT t FROM Ticket t WHERE t.status = :status ORDER BY t.dateTime DESC")
    List<Ticket> orderFindDateTime(@Param("status") int status);

}