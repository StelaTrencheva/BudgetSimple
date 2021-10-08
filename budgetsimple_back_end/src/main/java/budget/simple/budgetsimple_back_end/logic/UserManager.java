package budget.simple.budgetsimple_back_end.logic;

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
    public void addUser(User user){
        if (usersData.getUserId(user.getUsername()) != null){
            throw new ExistingUserException();
        }
        usersData.addUser(user);
    }
    public void updateUserInfo(User user) {
        User old = usersData.getUser(user.getId());
        if (old == null) {
            throw new NotExistingUserException();
        }
        usersData.updateUserInfo(user);
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
