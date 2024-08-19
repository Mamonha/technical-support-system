package app.dto.user;

import app.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseUser {

    private Long id;
    private String nome;
    private String emailAddress;
    private String phoneNumber;
    private String cpfNumber;



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
