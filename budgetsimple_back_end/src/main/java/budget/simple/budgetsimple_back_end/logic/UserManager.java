package budget.simple.budgetsimple_back_end.logic;

import budget.simple.budgetsimple_back_end.controller.dto.UserDTO;
import budget.simple.budgetsimple_back_end.logic.mappers.UserMapper;
import org.springframework.stereotype.Service;
import budget.simple.budgetsimple_back_end.repository.IUsersData;
import budget.simple.budgetsimple_back_end.exceptions.*;
import java.util.List;
// tests with mocking

@Service
public class UserManager {
    private final IUsersData usersData;

    public UserManager(IUsersData usersData) {
        this.usersData = usersData;
    }

    public User getUser(Long id){
        User user = usersData.getUser(id);
        if (user == null){
            throw new NotExistingUserException();
        }
        return user;
    }
    public List<User> getAllUsers(){
        List<User> users=usersData.getUsers();
        if(users.isEmpty()){
            throw new NoUsersFoundException();
        }
        return users;
    }
    public void addUser(UserDTO userDTO){
        if (usersData.getUserId(userDTO.getUsername()) != null){
            throw new ExistingUserException();
        }
        UserMapper newUser = new UserMapper(userDTO);
        usersData.addUser(newUser.getUser());
    }
    public void updateUserInfo(UserDTO userDTO) {
        User old = usersData.getUser(userDTO.getId());
        if (old == null) {
            throw new NotExistingUserException();
        }
        UserMapper newUser = new UserMapper(userDTO);
        usersData.updateUserInfo(newUser.getUser());
    }
    public User loginUser(String username,String password) {
        User foundUser = usersData.getUserByUsernameAndPassword(username,password);
        if(foundUser != null){
            return foundUser;
        }
        throw new NotExistingUserException();
    }
    public Long getUserId(String username){
        return usersData.getUserId(username);
    }
}
