package budget.simple.budgetsimple_back_end;

import budget.simple.budgetsimple_back_end.logic.Role;
import budget.simple.budgetsimple_back_end.logic.User;
import budget.simple.budgetsimple_back_end.logic.UserManager;
import budget.simple.budgetsimple_back_end.logic.mapper.UserMapper;
import budget.simple.budgetsimple_back_end.model.UserContact;
import budget.simple.budgetsimple_back_end.model.UserCredentials;
import budget.simple.budgetsimple_back_end.model.UserPersonalInfo;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class UserTest {
    @Test
    public void toStringTest() {
        // arrange
        Long userId = 1L;
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());

        // act
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);

        // assert
        assertEquals("" +
                " name='Test0 Test0'" +
                ", email='Test0@gmail.com'"+
                ", address='Test0'" +
                ", phone number='0123456789'" +
                ", username='Test0'"+
                ", position='USER'"+
                '}', user.toString() );
    }
}
