package app.controllers;

import app.dto.user.RequestUser;
import app.dto.user.ResponseUser;
import app.entities.User;
import app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    protected UserService userService;

    @PostMapping
    public ResponseEntity<String> store(@Valid @RequestBody RequestUser request){
        try{
            userService.store(request.user());
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user: " + err.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> index(){
        try{
            List<User> users = userService.index();
            List<ResponseUser> responseUsers = users.stream()
                    .map(ResponseUser::user)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseUsers);

        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to List users: " + err.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity <ResponseUser> destroy (@PathVariable Long id){
        try {
            ResponseUser responseUser = this.userService.show(id);
            return new ResponseEntity<>(responseUser, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }
}
