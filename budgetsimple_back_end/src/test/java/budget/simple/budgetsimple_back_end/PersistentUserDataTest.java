package budget.simple.budgetsimple_back_end;

import budget.simple.budgetsimple_back_end.logic.Role;
import budget.simple.budgetsimple_back_end.logic.User;
import budget.simple.budgetsimple_back_end.model.UserContact;
import budget.simple.budgetsimple_back_end.model.UserCredentials;
import budget.simple.budgetsimple_back_end.model.UserPersonalInfo;
import budget.simple.budgetsimple_back_end.repository.IPersistentUserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
public class PersistentUserDataTest {
    @Autowired
    IPersistentUserData underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    public void findUserByUsernameTest() {
        // arrange
        Long idSeeker = 1L;
        User user1 = new User(idSeeker++, new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));
        User user2 = new User(idSeeker++, new UserPersonalInfo("Test1", "Test1", new Date()), new UserContact("Test1@gmail.com", "Test1", "0123456789"), Role.USER, new UserCredentials("Test1", "Test1"));
        underTest.save(user1);
        underTest.save(user2);

        // act
        User foundUser = underTest.findUserByUsername("Test1");

        // assert
        assertEquals("Test1",foundUser.getFirstName());
        assertEquals("Test1@gmail.com", foundUser.getEmail());
        assertEquals("Test1", foundUser.getUsername());
    }
    @Test
    public void getUserByUsernameAndPasswordTest() {
        // arrange
        Long idSeeker = 1L;
        User user1 = new User(idSeeker++, new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));
        User user2 = new User(idSeeker++, new UserPersonalInfo("Test1", "Test1", new Date()), new UserContact("Test1@gmail.com", "Test1", "0123456789"), Role.USER, new UserCredentials("Test1", "Test1"));
        underTest.save(user1);
        underTest.save(user2);

        // act
        User foundUser = underTest.getUserByUsernameAndPassword("Test0","Test0");

        // assert
        assertEquals("Test0", foundUser.getFirstName());
        assertEquals("Test0@gmail.com", foundUser.getEmail());
        assertEquals("Test0", foundUser.getUsername());
    }

    @Test
    public void getUserByUsernameAndPasswordNotFoundTest() {
        // arrange
        Long idSeeker = 1L;
        User user1 = new User(idSeeker++, new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));
        User user2 = new User(idSeeker++, new UserPersonalInfo("Test1", "Test1", new Date()), new UserContact("Test1@gmail.com", "Test1", "0123456789"), Role.USER, new UserCredentials("Test1", "Test1"));
        underTest.save(user1);
        underTest.save(user2);

        // act
        User foundUser = underTest.getUserByUsernameAndPassword("Test3","Test3");

        // assert
        assertEquals(null, foundUser);
    }


    @Test
    public void updateUserInfoTest() {
        // arrange
        Long userId = 1L;
        User user = new User(userId, new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));
        underTest.save(user);
        Long id = underTest.findUserByUsername("Test0").getId();

        // act
        underTest.updateUserInfo("Test0", "Test1", "Test1@gmail.com", "Test1", "0123456789", "Test1", userId);

        User foundUser = underTest.getById(id);

        // assert
        assertEquals("Test0", foundUser.getFirstName());
        assertEquals("Test1@gmail.com", foundUser.getEmail());
        assertEquals("Test1", foundUser.getUsername());
    }
}
