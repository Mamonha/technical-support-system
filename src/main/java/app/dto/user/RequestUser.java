package app.dto.user;

import app.entities.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
@AllArgsConstructor
@Getter
@Setter
public class RequestUser {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Contact is mandatory")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Contact must be in the format (XX) XXXXX-XXXX")
    private String contact;

    @NotBlank(message = "CPF is mandatory")
    @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits")
    @CPF(message = "CPF invalido")
    private String cpf;

    public User user(){
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setContact(this.contact);
        user.setCpf(this.cpf);
        return user;
    }
}
