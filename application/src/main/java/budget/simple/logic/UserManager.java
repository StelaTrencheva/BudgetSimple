package budget.simple.logic;

import budget.simple.exceptions.ExistingUserException;
import budget.simple.exceptions.NoUsersFoundException;
import budget.simple.exceptions.NotExistingUserException;
import budget.simple.repository.IUsersData;
import org.springframework.stereotype.Service;

import java.util.List;
// tests with mocking

@Service
public class UserManager {
    private final IUsersData usersData;

    public UserManager(IUsersData usersData) {
        this.usersData = usersData;
    }

    public User getUser(Long id){
        User user=usersData.getUser(id);
        if(user==null){
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
        if (usersData.getUser(user.getId()) != null){
            throw new ExistingUserException();
        }
        usersData.addUser(user);
    }
    public void updateUserInfo(User user) {
        User old = usersData.getUser(user.getId());
        if (old == null) {
            throw new NotExistingUserException();
        }
        usersData.saveUser(user);
    }
    public Long getUserId(String username){
        return usersData.getUserId(username);
    }
}
