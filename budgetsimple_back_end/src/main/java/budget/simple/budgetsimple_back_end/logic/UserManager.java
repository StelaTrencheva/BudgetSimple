package budget.simple.budgetsimple_back_end.logic;

import budget.simple.budgetsimple_back_end.model.UserDTO;
import budget.simple.budgetsimple_back_end.logic.mapper.UserMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import budget.simple.budgetsimple_back_end.repository.IUsersData;
import budget.simple.budgetsimple_back_end.exception.*;
import java.util.List;
// tests with mocking

@Service
public class UserManager {
    private final IUsersData usersData;
    private final UserMapper userMapper;

    public UserManager(IUsersData usersData, UserMapper userMapper) {
        this.usersData = usersData;
        this.userMapper = userMapper;
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
        if (usersData.getUser(userDTO.getUsername()) != null){
            throw new ExistingUserException();
        }
        usersData.addUser(userMapper.mapToModel(userDTO));
    }
    public void updateUserInfo(UserDTO userDTO) {
        User old = usersData.getUser(userDTO.getId());
        if (old == null) {
            throw new NotExistingUserException();
        }
        usersData.updateUserInfo(userMapper.mapToModel(userDTO));
    }
    public User loginUser(String username,String password) {
        User foundUser = usersData.getUserByUsernameAndPassword(username,password);
        if(foundUser != null){
            return foundUser;
        }
        throw new NotExistingUserException();
    }

    public User getUser(String username){
        return usersData.getUser(username);
    }

    public User getLoggedInUser(){
        return this.getUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
