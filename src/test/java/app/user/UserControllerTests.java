package app.user;

import app.controllers.UserController;
import app.dto.user.RequestUpadate;
import app.dto.user.RequestUser;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class UserControllerTests {

    @Autowired
    UserController userController;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        User user = new User(
                1L,
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

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);

        Mockito.when(this.userRepository.save(any())).thenReturn(user);
        Mockito.when(this.userRepository.findAll()).thenReturn(users);
        Mockito.when(this.userRepository.findById(3L)).thenReturn(Optional.of(user3));
        Mockito.doNothing().when(this.userRepository).delete(any());
    }

    @Test
    void storeTest (){

        RequestUser user= new RequestUser(
                "Mamonha",
                "mamonha@gmail.com",
                "45 99999-4565",
                "8018742939"
        );
        ResponseEntity <String> response = userController.store(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created successfully.", response.getBody());
    }

    @Test
    void indexTest (){
        ResponseEntity <List<ResponseUser>> response = (ResponseEntity<List<ResponseUser>>) userController.index();
        List<ResponseUser> users= response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, users.size() );
    }

    @Test
    void showTest (){
        ResponseUser user = new ResponseUser(
                3L,
                "Mamonha",
                "Mamonha@gmail.com",
                "45 97777-4569",
                "8018745678"
        );
        ResponseEntity <ResponseUser> response = userController.show(3L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mamonha", user.getNome() );
    }

    @Test
    void updateTest (){
        RequestUpadate user = new RequestUpadate(
                3L,
                "Mamonha",
                "Mamonha@gmail.com",
                "45 97777-4569",
                "8018745678"
        );
        ResponseEntity <String> response = userController.update(user, 3L);
        String expected = "User updated with success User: Mamonha" + " ID: 3";
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody() );
    }

    @Test
    void indexTestException() {
        // Simulando uma exceção ao tentar listar usuários
        doThrow(new RuntimeException("Error fetching users"))
                .when(userRepository).findAll();
        ResponseEntity<?> response = userController.index();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to List users: Error fetching users", response.getBody());
    }

    @Test
    void showTestException() {
        Mockito.when(userRepository.findById(4L)).thenThrow(new RuntimeException("User not found"));
        ResponseEntity<ResponseUser> response = userController.show(4L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testStore_ExceptionThrown() throws Exception {
        doThrow(new RuntimeException("Simulated Exception")).when(userRepository).save(any());
        RequestUser requestUser = new RequestUser();
        requestUser.setName("Mamonha");
        requestUser.setCpf("80148742939");
        requestUser.setEmail("email@gmail.com");
        requestUser.setContact("45 99999-4558");
        ResponseEntity<String> response = userController.store(requestUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Failed to create user: Simulated Exception"));
    }

//    @Test
//    public void testUpdate_ExceptionThrown() {
//        User user = new User(
//                1L,
//                "Maria",
//                "maria@gmail.com",
//                "45 97777-4569",
//                "8018745678",
//                1,
//                new ArrayList<Ticket>(),
//                new ArrayList<Response>()
//        );
//        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
//        doThrow(new RuntimeException("Simulated Exception")).when(userRepository).save(any());
//        RequestUpadate requestUpdate = new RequestUpadate(
//                1L,
//                "Mamonha",
//                "Mamonha@gmail.com",
//                "45 97777-4569",
//                "80148742939"
//        );
//        ResponseEntity<String> response = userController.update(requestUpdate, 1L);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertTrue(response.getBody().contains("Failed to update user: Simulated Exception"));
//    }

    @Test
    void deleteTest() {
        ResponseEntity<String> response = userController.delete(3L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteTestException() {
        doThrow(new RuntimeException("Delete failed"))
                .when(userRepository).deleteById(1L);
        ResponseEntity<String> response = userController.delete(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


}
