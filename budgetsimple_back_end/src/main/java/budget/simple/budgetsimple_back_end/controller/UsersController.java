package budget.simple.budgetsimple_back_end.controller;


import budget.simple.budgetsimple_back_end.controller.dto.LoginDTO;
import budget.simple.budgetsimple_back_end.controller.dto.UserDTO;
import budget.simple.budgetsimple_back_end.logic.User;
import budget.simple.budgetsimple_back_end.logic.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public Long createUser(@RequestBody UserDTO user) {
        um.addUser(user);
        return um.getUserId(user.getUsername());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User loginUser(@RequestBody LoginDTO loginDTO) {
        return um.loginUser(loginDTO.getUsername(),loginDTO.getPassword());
    }

    @PutMapping("/updateUser")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUserInfo(@RequestBody UserDTO user)
    {
            um.updateUserInfo(user);
    }


}
