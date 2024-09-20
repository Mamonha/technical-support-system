package app.services;

import app.dto.Ticket.ResponseTicket;
import app.dto.user.ResponseUser;
import app.entities.Ticket;
import app.entities.User;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    protected UserRepository userRepository;
    public User store(User user) throws Exception {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Failed to create user: " + e.getMessage());
        }
    }

    public List<User> index(){
        return userRepository.findAll();
    }

    public ResponseUser show (Long id){
        User user= this.userRepository.findById(id).get();
        return ResponseUser.user(user);
    }

    public String update(User user, Long id){
        try {
            user.setId(id);
            User userToUpdate = userRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user ID: " + id)
            );
            user.setListTicket(userToUpdate.getListTicket());
            user.setListResponse(userToUpdate.getListResponse());
            userRepository.save(user);
            return "User updated with success" + " User: " + user.getName() + " ID: " + user.getId();
        }catch (Exception e){
            return "Error updating user: " + e.getMessage();
        }
    }
}
