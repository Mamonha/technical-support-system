package app.factory;

import app.entities.User;

import java.util.ArrayList;

public class UserFactory {
    public static User createDefaultUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Mamonha");
        user.setEmail("mamonha@example.com");
        user.setContact("123456789");
        user.setCpf("12345678900");
        user.setType(1);
        user.setListTicket(new ArrayList<>());
        user.setListResponse(new ArrayList<>());
        return user;
    }

    public static User createCustomUser(String name, String email, String contact, String cpf, int type) {
        User user = createDefaultUser();
        user.setName(name);
        user.setEmail(email);
        user.setContact(contact);
        user.setCpf(cpf);
        user.setType(type);

        return user;
    }
}
