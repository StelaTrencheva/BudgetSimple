package budget.simple.controller;

import budget.simple.logic.User;
import budget.simple.logic.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
        if(users != null) {
            return ResponseEntity.ok().body(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
            um.addUser(user);
            String url = "user" + "/" + user.getUsername();
            URI uri = URI.create(url);
            return new ResponseEntity(uri, HttpStatus.CREATED);
        }

    @PutMapping()
    public ResponseEntity<User> updateUserInfo(@RequestBody User user) {
            um.updateUserInfo(user);
            return ResponseEntity.noContent().build();
    }


}
