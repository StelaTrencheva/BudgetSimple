package budget.simple.budgetsimple_back_end.repository;
import budget.simple.budgetsimple_back_end.logic.Position;
import budget.simple.budgetsimple_back_end.logic.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class FakeData implements IUsersData {
    private static Long idSeeker= 1L;
    private final List<User> usersList = new ArrayList<>();
    public FakeData() {
        usersList.add(new User(idSeeker++,"Test0", "Test0","Test0@gmail.com","0123456789","Test0","Test0", Position.USER,new Date()));
        usersList.add(new User(idSeeker++,"Test1", "Test1","Test1@gmail.com","0123456789","Test1","Test1", Position.USER,new Date()));
        usersList.add(new User(idSeeker++,"Test2", "Test2","Test2@gmail.com","0123456789","Test2","Test2", Position.USER,new Date()));
        usersList.add(new User(idSeeker++,"Test3", "Test3","Test3@gmail.com","0123456789","Test3","Test3", Position.USER,new Date()));
        usersList.add(new User(idSeeker++,"Test4", "Test4","Test4@gmail.com","0123456789","Test4","Test4", Position.USER,new Date()));

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
            if(user.getId() == newUser.getId()){
                int index = usersList.indexOf(user);
                usersList.set(index,newUser);
                return;
            }
        }
    }

    @Override
    public Long getUserId(String username) {
        for (User user : usersList) {
            if (user.getUsername()==username)
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
            }

    @Override
    public User getUser(Long id) {
        for (User user : usersList) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }
}
