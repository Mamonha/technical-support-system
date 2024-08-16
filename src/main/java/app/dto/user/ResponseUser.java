package app.dto.user;

import app.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUser {

    private Long id;
    private String nome;
    private String emailAddress;
    private String phoneNumber;
    private String cpfNumber;

    public ResponseUser(Long id, String nome, String emailAddress, String phoneNumber, String cpfNumber) {
        this.id = id;
        this.nome = nome;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.cpfNumber = cpfNumber;
    }

    public static ResponseUser user(User user) {
        return new ResponseUser(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getContact(),
            user.getCpf()
        );
    }
}
