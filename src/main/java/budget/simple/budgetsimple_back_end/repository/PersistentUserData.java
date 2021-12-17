package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class PersistentUserData implements IUsersData{

    private IPersistentUserData data;

    public PersistentUserData(IPersistentUserData data){
        this.data = data;
    }

    @Override
    public List<User> getUsers() {
        return data.findAll();
    }

    @Override
    public void addUser(User user) {
        data.save(user);
    }


    @Override
    public User getUserById(String id) {
        return data.findByUuid(id);
    }

    @Override
    public User getUser(String username) {
        return data.findUserByUsername(username);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return data.getUserByUsernameAndPassword(username,password);
    }

    @Override
    public void updateUserInfo(User user) {
        data.updateUserInfo(user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress(), user.getPhoneNum(),user.getUsername(),user.getId());
    }

}
