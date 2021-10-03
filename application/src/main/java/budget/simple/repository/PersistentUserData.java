package budget.simple.repository;

import budget.simple.logic.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Long getUserId(String username) {
        return null;
    }
}
