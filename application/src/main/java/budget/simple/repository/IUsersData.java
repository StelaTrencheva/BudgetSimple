package budget.simple.repository;
import budget.simple.logic.User;

import java.util.List;
import java.util.Optional;

public interface IUsersData {
    List<User> getUsers();
    void addUser(User user);
    void saveUser(User user);
    User getUser(Long id);
    Long getUserId(String username);

}
