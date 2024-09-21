package app.user;

import app.dto.user.ResponseUser;
import app.entities.Response;
import app.entities.Ticket;
import app.entities.User;
import app.repositories.UserRepository;
import app.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setup(){
        User user= new User(
                1l,
                "Mamonha",
                "mamonha@gmail.com",
                "45 99999-4565",
                "8018742939",
                1,
                new ArrayList<Ticket>(),
                new ArrayList<Response>()
        );

        User user2 = new User(
                2L,
                "João",
                "joao@gmail.com",
                "45 98888-4567",
                "8018741234",
                1,
                new ArrayList<Ticket>(),
                new ArrayList<Response>()
        );

        User user3 = new User(
                3L,
                "Maria",
                "maria@gmail.com",
                "45 97777-4569",
                "8018745678",
                1,
                new ArrayList<Ticket>(),
                new ArrayList<Response>()
        );

        List <User> users= new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(this.userRepository.findAll()).thenReturn(users);
        Mockito.when(this.userRepository.findById(3L)).thenReturn(Optional.of(user3));

    }

    @Test
    void storeTest (){

        User user= new User(
                "Mamonha",
                "mamonha@gmail.com",
                "45 99999-4565",
                "8018742939",
                3
        );

        try{
            User userBd = userService.store(user);
            assertEquals(1L, userBd.getId());
        }catch(Exception e){
            e.printStackTrace();
        };
    }

    @Test
    void indexTest (){
        List <User> users= userService.index();
        assertEquals(3, users.size());
    }

    void showTest(){
        ResponseUser user= userService.show(3l);
        assertEquals("8018745678", user.getCpfNumber());
    }

    @Test
    void updateTest(){
        User user = new User(
                3L,
                "Mamonha",
                "Mamonha@gmail.com",
                "45 97777-4569",
                "8018745678",
                1,
                new ArrayList<Ticket>(),
                new ArrayList<Response>()
        );
        String confirmation = userService.update(user, 3L);
        String validation= "User updated with success User: Mamonha" + " ID: 3";
        assertEquals(validation, confirmation);
    }


}
