package app.entities;

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
    private List<Ticket> listTicket;

    @OneToMany (mappedBy = "user")
    private List<Response> listResponse;





}
