package app.services;

import app.entities.User;
import app.resositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    protected UserRepository userRepository;
    public void store(User user) throws Exception {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Failed to create user: " + e.getMessage());
        }
    }

    public List<User> index(){
        return userRepository.findAll();
    }
}
