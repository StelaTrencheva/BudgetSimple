package budget.simple.budgetsimple_back_end;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import budget.simple.budgetsimple_back_end.exception.ExistingUserException;
import budget.simple.budgetsimple_back_end.exception.NoUsersFoundException;
import budget.simple.budgetsimple_back_end.exception.NotExistingUserException;
import budget.simple.budgetsimple_back_end.logic.Role;
import budget.simple.budgetsimple_back_end.logic.User;
import budget.simple.budgetsimple_back_end.logic.UserManager;
import budget.simple.budgetsimple_back_end.logic.mapper.UserMapper;
import budget.simple.budgetsimple_back_end.model.UserContact;
import budget.simple.budgetsimple_back_end.model.UserCredentials;
import budget.simple.budgetsimple_back_end.model.UserDTO;
import budget.simple.budgetsimple_back_end.model.UserPersonalInfo;
import budget.simple.budgetsimple_back_end.repository.IUsersData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserManagerTest {
    @Test
    public void getUserTest() {
        // arrange
        Long userId = 1L;
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);

        IUsersData usersData = mock(IUsersData.class);
        when(usersData.getUser(userId)).thenReturn(user);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        // act
        User foundUser = userManager.getUser(userId);

        // assert
        assertEquals(foundUser, user);
        verify(usersData, times(1)).getUser(userId);
    }

    @Test()
    public void getUserTestException() {
        // arrange
        Long userId = 1L;
        IUsersData usersData = mock(IUsersData.class);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        NotExistingUserException thrown = Assertions.assertThrows(NotExistingUserException.class, () -> {
            //Code under test
            userManager.getUser(userId);
        });
        Assertions.assertEquals("This user does not exist in the system!", thrown.getMessage());
        verify(usersData, times(1)).getUser(userId);
    }

    @Test
    public void getAllUsersTest() {
        //arrange
        Long idSeeker = 1L;
        List<User> usersList = new ArrayList<>();
        usersList.add(new User(idSeeker++, new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0")));
        usersList.add(new User(idSeeker++, new UserPersonalInfo("Test1", "Test1", new Date()), new UserContact("Test1@gmail.com", "Test1", "0123456789"), Role.USER, new UserCredentials("Test1", "Test1")));
        usersList.add(new User(idSeeker++, new UserPersonalInfo("Test2", "Test2", new Date()), new UserContact("Test2@gmail.com", "Test2", "0123456789"), Role.USER, new UserCredentials("Test2", "Test2")));
        usersList.add(new User(idSeeker++, new UserPersonalInfo("Test3", "Test3", new Date()), new UserContact("Test3@gmail.com", "Test3", "0123456789"), Role.USER, new UserCredentials("Test3", "Test3")));

        IUsersData usersData = mock(IUsersData.class);
        when(usersData.getUsers()).thenReturn(usersList);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        //act
        List<User> newUsers = userManager.getAllUsers();

        //assert
        verify(usersData, times(1)).getUsers();
        assertEquals(usersList, newUsers);

    }

    @Test()
    public void getAllUsersTestException() {
        // arrange
        Long userId = 1L;
        IUsersData usersData = mock(IUsersData.class);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        NoUsersFoundException thrown = Assertions.assertThrows(NoUsersFoundException.class, () -> {
            //Code under test
            userManager.getAllUsers();
        });
        Assertions.assertEquals("There are not any users in the system!", thrown.getMessage());
        verify(usersData, times(1)).getUsers();
    }

    @Test()
    public void addUserTest() {
        // arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setFirstName("Test0");
        userDTO.setLastName("Test0");
        userDTO.setUsername("Test0");
        userDTO.setPassword("Test0");
        userDTO.setDateOfBirth(new Date());
        userDTO.setEmail("Test0@gmail.com");
        userDTO.setAddress("Test0");
        userDTO.setPhoneNum("Test0");

        IUsersData usersData = mock(IUsersData.class);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));
        doNothing().when(usersData).addUser(new UserMapper(new BCryptPasswordEncoder()).mapToModel(userDTO));

        //act
        userManager.addUser(userDTO);

        // assert
        verify(usersData, times(1)).addUser(new UserMapper(new BCryptPasswordEncoder()).mapToModel(userDTO));
    }

    @Test
    public void updateUserInfoTest(){
        //arrange
        Long userId=1L;
        User oldUser = new User(userId, new UserPersonalInfo("Test1", "Test1",new Date()), new UserContact("Test1@gmail.com","Test1","0123456789"), Role.USER, new UserCredentials("Test1", "Test1"));
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setFirstName("Test0");
        userDTO.setLastName("Test0");
        userDTO.setUsername("Test0");
        userDTO.setPassword("Test0");
        userDTO.setDateOfBirth(new Date());
        userDTO.setEmail("Test0@gmail.com");
        userDTO.setAddress("Test0");
        userDTO.setPhoneNum("Test0");

        IUsersData usersData=mock(IUsersData.class);
        when(usersData.getUser(userId)).thenReturn(oldUser);
        doNothing().when(usersData).updateUserInfo(oldUser);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));
        //act
        userManager.updateUserInfo(userDTO);
        //assert
        verify(usersData,times(1)).updateUserInfo(oldUser);
        verify(usersData,times(1)).getUser(userId);
    }
    @Test
    public void getUserByUsernameTest() {
        // arrange
        Long userId = 1L;
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);

        IUsersData usersData = mock(IUsersData.class);
        when(usersData.getUser("Test0")).thenReturn(user);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        // act
        User foundUser = userManager.getUser("Test0");

        // assert
        assertEquals(foundUser, user);
        verify(usersData, times(1)).getUser("Test0");
    }

    @Test()
    public void updateUserInfoTestException() {
        // arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setFirstName("Test0");
        userDTO.setLastName("Test0");
        userDTO.setUsername("Test0");
        userDTO.setPassword("Test0");
        userDTO.setDateOfBirth(new Date());
        userDTO.setEmail("Test0@gmail.com");
        userDTO.setAddress("Test0");
        userDTO.setPhoneNum("Test0");

        IUsersData usersData = mock(IUsersData.class);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        NotExistingUserException thrown = Assertions.assertThrows(NotExistingUserException.class, () -> {
            //Code under test
            userManager.updateUserInfo(userDTO);
        });
        Assertions.assertEquals("This user does not exist in the system!", thrown.getMessage());
    }

    @Test()
    public void addUserInfoTestException() {
        // arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setFirstName("Test0");
        userDTO.setLastName("Test0");
        userDTO.setUsername("Test0");
        userDTO.setPassword("Test0");
        userDTO.setDateOfBirth(new Date());
        userDTO.setEmail("Test0@gmail.com");
        userDTO.setAddress("Test0");
        userDTO.setPhoneNum("Test0");

        IUsersData usersData = mock(IUsersData.class);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));
        when(usersData.getUser(userDTO.getUsername())).thenReturn(new User());

        ExistingUserException thrown = Assertions.assertThrows(ExistingUserException.class, () -> {
            //Code under test
            userManager.addUser(userDTO);
        });
        Assertions.assertEquals("This user already exist in the system!", thrown.getMessage());
    }
    @Test
    public void loginUserTest() {
        // arrange
        Long userId = 1L;
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);

        IUsersData usersData = mock(IUsersData.class);
        when(usersData.getUserByUsernameAndPassword("Test0","Test0")).thenReturn(user);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        // act
        User foundUser = userManager.loginUser("Test0","Test0");

        // assert
        assertEquals(foundUser, user);
        verify(usersData, times(1)).getUserByUsernameAndPassword("Test0","Test0");
    }

    @Test()
    public void loginUserTestException() {
        // arrange
        Long userId = 1L;
        IUsersData usersData = mock(IUsersData.class);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));
        when(usersData.getUserByUsernameAndPassword("Test0","Test0")).thenReturn(null);
        NotExistingUserException thrown = Assertions.assertThrows(NotExistingUserException.class, () -> {
            //Code under test
            userManager.loginUser("Test0","Test0");
        });
        Assertions.assertEquals("This user does not exist in the system!", thrown.getMessage());
        verify(usersData, times(1)).getUserByUsernameAndPassword("Test0","Test0");
    }
}
