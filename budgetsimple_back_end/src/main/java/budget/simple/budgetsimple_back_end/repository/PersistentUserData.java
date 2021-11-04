package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.User;
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
    public void saveUser(User user) {
        data.save(user);
    }

    @Override
    public User getUser(Long id) {
        return data.findById(id).orElse(null);
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
