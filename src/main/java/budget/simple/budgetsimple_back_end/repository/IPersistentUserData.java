package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface IPersistentUserData extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findByUuid(@Param("id") String id);

    @Query("SELECT u FROM User u WHERE u.username = :username and u.password = :password")
    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Transactional
    @Modifying(flushAutomatically = true,clearAutomatically = true)
    @Query("update User u set u.firstName = :firstName, u.lastName = :lastName, u.email = :email, u.address = :address, u.phoneNum = :phoneNum, u.username = :username where u.id = :id")
    void updateUserInfo(@Param("firstName") String firstName, @Param("lastName")String lastName, @Param("email")String email, @Param("address")String address, @Param("phoneNum")String phoneNum, @Param("username")String username, @Param("id")String id);
   }