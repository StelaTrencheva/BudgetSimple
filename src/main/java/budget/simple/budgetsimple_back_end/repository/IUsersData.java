package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.user.User;

import java.util.List;

public interface IUsersData {
    List<User> getUsers();
    void addUser(User user);
    User getUserById(String id);
    User getUser(String username);
    User getUserByUsernameAndPassword(String username, String password);
    void updateUserInfo(User user);

}
