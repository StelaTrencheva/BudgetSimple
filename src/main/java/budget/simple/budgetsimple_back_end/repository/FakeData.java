package budget.simple.budgetsimple_back_end.repository;
import budget.simple.budgetsimple_back_end.logic.user.Role;
import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserContact;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserCredentials;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserPersonalInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeData implements IUsersData {
    private final List<User> usersList = new ArrayList<>();
    public FakeData() {
        usersList.add(new User((UUID.randomUUID()).toString(),new UserPersonalInfo("Test0","Test0",new Date()),new UserContact("Test0@gmail.com","Test0","0123456789"), Role.USER, new UserCredentials("Test0","Test0")));
        usersList.add(new User((UUID.randomUUID()).toString(),new UserPersonalInfo("Test1","Test1",new Date()),new UserContact("Test1@gmail.com","Test1","0123456789"), Role.USER, new UserCredentials("Test1","Test1")));
        usersList.add(new User((UUID.randomUUID()).toString(),new UserPersonalInfo("Test2","Test2",new Date()),new UserContact("Test2@gmail.com","Test2","0123456789"), Role.USER, new UserCredentials("Test2","Test2")));
        usersList.add(new User((UUID.randomUUID()).toString(),new UserPersonalInfo("Test3","Test3",new Date()),new UserContact("Test3@gmail.com","Test3","0123456789"), Role.USER, new UserCredentials("Test3","Test3")));

    }
    @Override
    public List<User> getUsers() {
        return usersList;
    }

    @Override
    public void addUser(User user) {
        usersList.add(user);
    }



    @Override
    public User getUser(String username) {
        for (User user : usersList) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public void updateUserInfo(User user) {
        // this method will be implemented in the future
            }

    @Override
    public User getUserById(String id) {
        for (User user : usersList) {
            if (user.getId().equals(id))
                return user;
        }
        return null;
    }
}
