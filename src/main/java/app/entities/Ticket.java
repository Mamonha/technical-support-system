package app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private int priority;

    private LocalDateTime dateTime;

    private int status;

    @ManyToOne (cascade = CascadeType.ALL)
    @JsonManagedReference
    private User user;

    @OneToMany (mappedBy = "ticket")
    private List <Response> listResponse;

    @ManyToMany
    @JoinTable(
            name = "ticket_category",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )

    @JsonManagedReference
    private List <Category> listCategory;
}
