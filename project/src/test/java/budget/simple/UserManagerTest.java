package budget.simple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import budget.simple.logic.User;
import budget.simple.logic.UserManager;
import budget.simple.repository.IUsersData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserManagerTest {
    @Test
    public void getUserTest()
    {

        // arrange
        int userId = 1;
        User user = new User(userId,"Test0", "Test0","Test0@gmail.com","0123456789","Test0","Test0");

        IUsersData usersData = mock(IUsersData.class);
        when(usersData.getUser(userId)).thenReturn(user);
        UserManager userManager = new UserManager(usersData);

        // act
        User foundUser = userManager.getUser(userId);

        // assert
        assertEquals(foundUser, user);
        verify(usersData, times(1)).getUser(userId);
    }
    @Test
    public void getAllUsersTest(){
        //arrange
        int idSeeker=1;
        List<User> usersList = new ArrayList<>();
        usersList.add(new User(idSeeker++,"Test0", "Test0","Test0@gmail.com","0123456789","Test0","Test0"));
        usersList.add(new User(idSeeker++,"Test1", "Test1","Test1@gmail.com","0123456789","Test1","Test1"));
        usersList.add(new User(idSeeker++,"Test2", "Test2","Test2@gmail.com","0123456789","Test2","Test2"));
        usersList.add(new User(idSeeker++,"Test3", "Test3","Test3@gmail.com","0123456789","Test3","Test3"));
        usersList.add(new User(idSeeker++,"Test4", "Test4","Test4@gmail.com","0123456789","Test4","Test4"));

        IUsersData usersData = mock(IUsersData.class);
        when(usersData.getUsers()).thenReturn(usersList);
        UserManager userManager = new UserManager(usersData);

        //act
        List<User> newUsers=userManager.getAllUsers();

        //assert
        verify(usersData,times(1)).getUsers();
        assertEquals(usersList,newUsers);

    }
    @Test
    public void addUserTest(){
        //arrange
        int userId = 1;
        User user = new User(userId,"Test0", "Test0","Test0@gmail.com","0123456789","Test0","Test0");
        IUsersData usersData = mock(IUsersData.class);
        doNothing().when(usersData).addUser(user);
        UserManager userManager = new UserManager(usersData);
        //act
        userManager.addUser(user);
        //assert
        verify(usersData,times(1)).addUser(user);
    }
    @Test
    public void updateUserInfoTest(){
        //arrange
        int userId = 1;
        User oldUser = new User(userId,"Test0", "Test0","Test0@gmail.com","0123456789","Test0","Test0");
        User user = new User(userId,"Test5", "Test5","Test5@gmail.com","0123456789","Test5","Test5");
        IUsersData usersData=mock(IUsersData.class);
        when(usersData.getUser(userId)).thenReturn(oldUser);
        doNothing().when(usersData).saveUser(user);
        UserManager userManager = new UserManager(usersData);
        //act
        userManager.updateUserInfo(user);
        //assert
        verify(usersData,times(1)).saveUser(user);
        verify(usersData,times(1)).getUser(userId);
    }
    @Test
    public void getUserIdTest(){
        // arrange
        int userId = 1;
        User user = new User(userId,"Test0", "Test0","Test0@gmail.com","0123456789","Test0","Test0");

        IUsersData usersData = mock(IUsersData.class);
        when(usersData.getUserId(user.getUsername())).thenReturn(1);
        UserManager userManager = new UserManager(usersData);

        // act
        int id = userManager.getUserId(user.getUsername());

        // assert
        assertEquals(id, 1);
        verify(usersData, times(1)).getUserId(user.getUsername());
    }
}
