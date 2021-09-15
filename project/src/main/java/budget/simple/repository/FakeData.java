package budget.simple.repository;
import budget.simple.logic.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeData implements IUsersData {
    private static int idSeeker=1;
    private final List<User> usersList = new ArrayList<>();
    public FakeData() {
        usersList.add(new User(idSeeker++,"Test0", "Test0","Test0@gmail.com","0123456789","Test0","Test0"));
        usersList.add(new User(idSeeker++,"Test1", "Test1","Test1@gmail.com","0123456789","Test1","Test1"));
        usersList.add(new User(idSeeker++,"Test2", "Test2","Test2@gmail.com","0123456789","Test2","Test2"));
        usersList.add(new User(idSeeker++,"Test3", "Test3","Test3@gmail.com","0123456789","Test3","Test3"));
        usersList.add(new User(idSeeker++,"Test4", "Test4","Test4@gmail.com","0123456789","Test4","Test4"));

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
    public int getUserId(String username) {
        for (User user : usersList) {
            if (user.getUsername()==username)
                return user.getId();
        }
        return -1;
    }

    @Override
    public User getUser(int id) {
        for (User user : usersList) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }
}
