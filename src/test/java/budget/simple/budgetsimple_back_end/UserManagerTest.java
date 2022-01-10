package budget.simple.budgetsimple_back_end;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import budget.simple.budgetsimple_back_end.exception.ExistingUserException;
import budget.simple.budgetsimple_back_end.exception.NoUsersFoundException;
import budget.simple.budgetsimple_back_end.exception.NotExistingUserException;
import budget.simple.budgetsimple_back_end.logic.user.Role;
import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.logic.user.UserManager;
import budget.simple.budgetsimple_back_end.logic.mapper.UserMapper;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserContact;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserCredentials;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserDTO;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserPersonalInfo;
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
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserManagerTest {
    @Test
    public void getUserTest() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);

        IUsersData usersData = mock(IUsersData.class);
        when(usersData.getUserById(userId)).thenReturn(user);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        // act
        User foundUser = userManager.getUserById(userId);

        // assert
        assertEquals(foundUser, user);
        verify(usersData, times(1)).getUserById(userId);
    }

    @Test()
    public void getUserTestException() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        IUsersData usersData = mock(IUsersData.class);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        NotExistingUserException thrown = Assertions.assertThrows(NotExistingUserException.class, () -> {
            //Code under test
            userManager.getUserById(userId);
        });
        Assertions.assertEquals("This user does not exist in the system!", thrown.getMessage());
        verify(usersData, times(1)).getUserById(userId);
    }

    @Test
    public void getAllUsersTest() {
        //arrange
        List<User> usersList = new ArrayList<>();
        usersList.add(new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0")));
        usersList.add(new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test1", "Test1", new Date()), new UserContact("Test1@gmail.com", "Test1", "0123456789"), Role.USER, new UserCredentials("Test1", "Test1")));
        usersList.add(new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test2", "Test2", new Date()), new UserContact("Test2@gmail.com", "Test2", "0123456789"), Role.USER, new UserCredentials("Test2", "Test2")));
        usersList.add(new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test3", "Test3", new Date()), new UserContact("Test3@gmail.com", "Test3", "0123456789"), Role.USER, new UserCredentials("Test3", "Test3")));

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
        String userId = (UUID.randomUUID()).toString();
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
        String userId = (UUID.randomUUID()).toString();
        // arrange
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
        lenient().doNothing().when(usersData).addUser(new UserMapper(new BCryptPasswordEncoder()).mapToModel(userDTO));

        //act
        userManager.addUser(userDTO);

        // assert
        verify(usersData, times(1)).addUser(new UserMapper(new BCryptPasswordEncoder()).mapToModel(userDTO));
    }

    @Test
    public void updateUserInfoTest(){
        //arrange
        String userId = (UUID.randomUUID()).toString();
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
        when(usersData.getUserById(userId)).thenReturn(oldUser);
        doNothing().when(usersData).updateUserInfo(new UserMapper(new BCryptPasswordEncoder()).mapToModel(userDTO));
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));
        //act
        userManager.updateUserInfo(userDTO);
        //assert
        verify(usersData,times(1)).updateUserInfo(oldUser);
        verify(usersData,times(1)).getUserById(userId);
    }
    @Test
    public void getUserByUsernameTest() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
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
        String userId = (UUID.randomUUID()).toString();
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
        String userId = (UUID.randomUUID()).toString();
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
        String userId = (UUID.randomUUID()).toString();
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
        String userId = (UUID.randomUUID()).toString();
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
    @Test()
    public void getUserByUsernameTestException() {
        // arrange
        String username = "test";
        IUsersData usersData = mock(IUsersData.class);
        UserManager userManager = new UserManager(usersData, new UserMapper(new BCryptPasswordEncoder()));

        NotExistingUserException thrown = Assertions.assertThrows(NotExistingUserException.class, () -> {
            userManager.getUser(username);
        });
        Assertions.assertEquals("This user does not exist in the system!", thrown.getMessage());
        verify(usersData, times(1)).getUser(username);
    }
}
