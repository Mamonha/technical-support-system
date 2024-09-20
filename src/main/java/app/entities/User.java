package app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String email;

    private String contact;

    private String cpf;

    private int type;

    @OneToMany (mappedBy = "user")
    @JsonBackReference
    private List<Ticket> listTicket;

    @OneToMany (mappedBy = "user")
    @JsonBackReference
    private List<Response> listResponse;

    public User(String name, String email, String contact, String cpf, int type) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.cpf = cpf;
        this.type = type;
    }
}
