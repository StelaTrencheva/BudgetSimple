package budget.simple.budgetsimple_back_end.repository;
import budget.simple.budgetsimple_back_end.logic.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class FakeData implements IUsersData {
    private static Long idSeeker= 1L;
    private final List<User> usersList = new ArrayList<>();
    public FakeData() {
        usersList.add(new User(idSeeker++,new UserPersonalInfo("Test0","Test0",new Date()),new UserContact("Test0@gmail.com","Test0","0123456789"),Position.USER, new UserCredentials("Test0","Test0")));
        usersList.add(new User(idSeeker++,new UserPersonalInfo("Test1","Test1",new Date()),new UserContact("Test1@gmail.com","Test1","0123456789"),Position.USER, new UserCredentials("Test1","Test1")));
        usersList.add(new User(idSeeker++,new UserPersonalInfo("Test2","Test2",new Date()),new UserContact("Test2@gmail.com","Test2","0123456789"),Position.USER, new UserCredentials("Test2","Test2")));
        usersList.add(new User(idSeeker++,new UserPersonalInfo("Test3","Test3",new Date()),new UserContact("Test3@gmail.com","Test3","0123456789"),Position.USER, new UserCredentials("Test3","Test3")));

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
    public void saveUser(User newUser) {
        for (User user : usersList){
            if(user.getId().equals(newUser.getId())){
                int index = usersList.indexOf(user);
                usersList.set(index,newUser);
                return;
            }
        }
    }

    @Override
    public Long getUserId(String username) {
        for (User user : usersList) {
            if (user.getUsername().equals(username))
                return user.getId();
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
    public User getUser(Long id) {
        for (User user : usersList) {
            if (user.getId().equals(id))
                return user;
        }
        return null;
    }
}
