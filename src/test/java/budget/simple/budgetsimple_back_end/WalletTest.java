package budget.simple.budgetsimple_back_end;


import budget.simple.budgetsimple_back_end.exception.NotExistingUserException;
import budget.simple.budgetsimple_back_end.logic.user.Role;
import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.logic.wallets.Transaction;
import budget.simple.budgetsimple_back_end.logic.wallets.TransactionCategory;
import budget.simple.budgetsimple_back_end.logic.wallets.Wallet;
import budget.simple.budgetsimple_back_end.logic.wallets.WalletEntryRequest;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserContact;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserCredentials;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserPersonalInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class WalletTest {
    @Test
    public void addMemberTest() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);

        UserCredentials userCredentials1 = new UserCredentials("Test1", "Test1");
        UserContact userContact1 = new UserContact("Test1@gmail.com", "Test0", "0123456789");
        User user1 = new User(UUID.randomUUID().toString(), userPersonalInfo, userContact1, Role.USER, userCredentials1);

        // act
        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());
        wallet.addMember(user1);

        // assert
        assertEquals(wallet.getMembers().contains(user1), true);

    }

    @Test
    public void addTransactionTest() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);
        Transaction transaction = new Transaction("test", "test", TransactionCategory.Bills, 10.0, new Date(), user, new ArrayList<>());
        // act
        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());
        wallet.addTransaction(transaction);

        // assert
        assertEquals(wallet.getTransactions().contains(transaction), true);

    }
    @Test
    public void removeTransactionTest() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);
        Transaction transaction = new Transaction("test", "test", TransactionCategory.Bills, 10.0, new Date(), user, new ArrayList<>());
        // act
        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());
        wallet.addTransaction(transaction);
        wallet.removeTransaction(transaction.getId());

        // assert
        assertEquals(wallet.getTransactions().contains(transaction), false);

    }
    @Test
    public void removeMemberTest() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);
         // act
        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());
        wallet.removeMember(user);

        // assert
        assertEquals(wallet.getMembers().contains(user), false);

    }
    @Test
    public void removeEntryRequest() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);
        UserCredentials userCredentials1 = new UserCredentials("Test1", "Test1");
        UserContact userContact1 = new UserContact("Test1@gmail.com", "Test0", "0123456789");
        User user1 = new User(UUID.randomUUID().toString(), userPersonalInfo, userContact1, Role.USER, userCredentials1);

        WalletEntryRequest entryRequest = new WalletEntryRequest(user1);
        // act
        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());
        wallet.addEntryRequest(entryRequest);
        wallet.removeEntryRequest(entryRequest);

        // assert
        assertEquals(wallet.getWalletEntryRequests().contains(entryRequest), false);

    }
    @Test
    public void addEntryRequestTest() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);
        UserCredentials userCredentials1 = new UserCredentials("Test1", "Test1");
        UserContact userContact1 = new UserContact("Test1@gmail.com", "Test0", "0123456789");
        User user1 = new User(UUID.randomUUID().toString(), userPersonalInfo, userContact1, Role.USER, userCredentials1);

        WalletEntryRequest entryRequest = new WalletEntryRequest(user1);
        // act
        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());
        wallet.addEntryRequest(entryRequest);

        // assert
        assertEquals(wallet.getWalletEntryRequests().contains(entryRequest), true);
    }

    @Test
    public void addEntryRequestTestException() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);
        UserCredentials userCredentials1 = new UserCredentials("Test1", "Test1");
        UserContact userContact1 = new UserContact("Test1@gmail.com", "Test0", "0123456789");
        User user1 = new User(UUID.randomUUID().toString(), userPersonalInfo, userContact1, Role.USER, userCredentials1);

        WalletEntryRequest entryRequest = new WalletEntryRequest(user1);
        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());
        wallet.addEntryRequest(entryRequest);

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            wallet.addEntryRequest(entryRequest);
        });
        Assertions.assertEquals("This user already requested to join the wallet!", thrown.getMessage());
    }

    @Test
    public void addEntryRequestException1() {
        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);

        WalletEntryRequest entryRequest = new WalletEntryRequest(user);
        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            wallet.addEntryRequest(entryRequest);
        });
        Assertions.assertEquals("This user is already in the wallet!", thrown.getMessage());
    }

    @Test
    public void addMemberExceptionTest() {

        // arrange
        String userId = (UUID.randomUUID()).toString();
        UserCredentials userCredentials = new UserCredentials("Test0", "Test0");
        UserContact userContact = new UserContact("Test0@gmail.com", "Test0", "0123456789");
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo("Test0", "Test0", new Date());
        User user = new User(userId, userPersonalInfo, userContact, Role.USER, userCredentials);

        Wallet wallet = new Wallet(user, 10.0, "test", "test", "test", new Date());

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            wallet.addMember(user);
        });
        Assertions.assertEquals("This user already exist in the wallet", thrown.getMessage());
    }
}
