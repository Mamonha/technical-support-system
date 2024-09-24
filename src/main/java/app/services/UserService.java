package app.services;

import app.dto.Ticket.ResponseTicket;
import app.dto.user.ResponseUser;
import app.entities.Ticket;
import app.entities.User;
import app.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private SendEmailService sendEmailService;

    public User store(User user) throws Exception {
        try {
            User userBd =userRepository.save(user);
            String emailBody = "Dear "+userBd.getName()+",\n\n" +
                    "Welcome to Technical Support System!\n\n" +
                    "We are excited to inform you that your account has been successfully created. You can now log in and start exploring all the features available to you.\n\n" +
                    "Please make sure to keep your login details safe. If you did not create this account, or if you have any questions, feel free to contact our support team.\n\n" +
                    "Thank you for choosing Technical Support System! We are thrilled to have you onboard.\n\n";
            sendEmailService.sendEmailText(userBd.getEmail(), "Welcome to Technical Support System – Account Successfully Created!", emailBody  );
            return userBd;
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

    public String destroy(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
        return "User deleted successfully";
    }
}
