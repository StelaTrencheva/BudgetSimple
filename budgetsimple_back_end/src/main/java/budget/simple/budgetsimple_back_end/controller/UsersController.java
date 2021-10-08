package budget.simple.budgetsimple_back_end.controller;


import budget.simple.budgetsimple_back_end.controller.DTO.LoginDTO;
import budget.simple.budgetsimple_back_end.logic.User;
import budget.simple.budgetsimple_back_end.logic.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UserManager um;

    @GetMapping("{id}")
    public ResponseEntity<User> getUserPath(@PathVariable(value = "id") Long id) {
        User user = um.getUser(id);
        return ResponseEntity.ok().body(user);

    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = um.getAllUsers();
        if (users != null) {
            return ResponseEntity.ok().body(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@RequestBody User user) {
        um.addUser(user);
        return um.getUserId(user.getUsername());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User loginUser(@RequestBody LoginDTO loginDTO) {
        User user = um.loginUser(loginDTO.getUsername(),loginDTO.getPassword());
        return user;
    }

    @PutMapping("/updateUser")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUserInfo(@RequestBody User user)
    {
            um.updateUserInfo(user);
    }


}
