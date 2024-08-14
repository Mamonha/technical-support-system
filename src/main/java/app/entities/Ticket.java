package app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Ticket {
    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String category;

    private int priority;

    private LocalDateTime dateTime;

    private int status;

    @ManyToOne (cascade = CascadeType.ALL)
    private User user;

    @OneToMany (mappedBy = "ticket")
    private List <Response> listResponse;
}
