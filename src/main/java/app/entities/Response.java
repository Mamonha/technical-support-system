package app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Response {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDateTime dateTime;

    @ManyToOne (cascade = CascadeType.ALL)
    private User user;

    @ManyToOne (cascade= CascadeType.ALL)
    private Ticket ticket;
}
