package budget.simple.budgetsimple_back_end;

import budget.simple.budgetsimple_back_end.logic.mapper.ImageMapper;
import budget.simple.budgetsimple_back_end.logic.mapper.TransactionMapper;
import budget.simple.budgetsimple_back_end.logic.mapper.UserMapper;
import budget.simple.budgetsimple_back_end.logic.mapper.WalletMapper;
import budget.simple.budgetsimple_back_end.logic.user.Role;
import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.logic.wallets.*;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserContact;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserCredentials;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserDTO;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserPersonalInfo;
import budget.simple.budgetsimple_back_end.model.walletDTOs.MemberAmountDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.TransactionDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.WalletDTO;
import budget.simple.budgetsimple_back_end.repository.*;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WalletManagerTest {
    @Test
    public void getAllWalletsTest() {
        // arrange

        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        List<Wallet> walletList = new ArrayList<>();
        walletList.add(new Wallet());
        walletList.add(new Wallet());

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findAllByMembersContains(user)).thenReturn(walletList);
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        List<Wallet> allWallets = wm.getAllWallets(user);

        // assert
        assertEquals(allWallets, walletList);
        verify(walletData, times(1)).findAllByMembersContains(user);
    }

    @Test()
    public void addWalletTest() throws IOException, WriterException {
        // arrange

        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.save(any(Wallet.class))).thenReturn(new Wallet());
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
         wm.addWallet(walletDTO);

        // assert
        verify(walletData, times(1)).save(any(Wallet.class));
    }

    @Test()
    public void removeWalletMemberTest() {
        // arrange

        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));
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

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findById("test")).thenReturn(Optional.ofNullable(w));
        when(walletData.save(any(Wallet.class))).thenReturn(new Wallet());
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        wm.removeWalletMember("test", userDTO);

        // assert
        verify(walletData, times(1)).save(any(Wallet.class));
        verify(walletData, times(1)).findById("test");

    }

    @Test()
    public void changeWalletBudgetTest() {
        // arrange

        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        // arrange
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findById(w.getId())).thenReturn(Optional.ofNullable(w));
        when(walletData.save(any(Wallet.class))).thenReturn(new Wallet());
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        wm.changeWalletBudget(w.getId(), 1);

        // assert
        verify(walletData, times(1)).save(any(Wallet.class));
        verify(walletData, times(1)).findById(w.getId());

    }

    @Test()
    public void getWalletByIdTest() {
        // arrange

        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));
        // arrange
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findById(w.getId())).thenReturn(Optional.ofNullable(w));
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        Wallet foundWallet = wm.getWalletById(w.getId());

        // assert
        assertEquals(foundWallet, w);
        verify(walletData, times(1)).findById(w.getId());

    }

    @Test
    public void getAllTransactionsTest() {

        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        // arrange
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);


        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findById(w.getId())).thenReturn(Optional.ofNullable(w));
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        List<Transaction> transactions = wm.getAllTransactions(w.getId());

        // assert
        assertEquals(transactions, w.getTransactions());
        verify(walletData, times(1)).findById(w.getId());
    }
    @Test
    public void getWalletByCodeTest() throws IOException, WriterException {

        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        // arrange
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);
        ISharableCode code = new WalletQRCode();

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(sharableCodeData.findByLink("test")).thenReturn(code);
        when(walletData.findWalletByGeneratedCode(code)).thenReturn(w);
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        Wallet foundWallet = wm.getWalletByCode("test");

        // assert
        assertEquals(foundWallet, w);
       verify(walletData, times(1)).findWalletByGeneratedCode(code);
        verify(sharableCodeData, times(1)).findByLink("test");
    }
    @Test()
    public void addWalletEntryRequest() {
        // arrange

        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));
        String userId = (UUID.randomUUID()).toString();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setFirstName("Test0");
        userDTO.setLastName("Test0");
        userDTO.setUsername("Test1");
        userDTO.setPassword("Test0");
        userDTO.setDateOfBirth(new Date());
        userDTO.setEmail("Test0@gmail.com");
        userDTO.setAddress("Test0");
        userDTO.setPhoneNum("Test0");

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findById(w.getId())).thenReturn(Optional.ofNullable(w));
        when(walletData.save(any(Wallet.class))).thenReturn(new Wallet());
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        wm.addNewMemberRequest(userDTO,w.getId());

        // assert
        verify(walletData, times(1)).findById(w.getId());
        verify(walletData, times(1)).save(any(Wallet.class));
    }
    @Test
    public void getAllWalletsSpendingTest(){
        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(w);
        wallets.add(w);
        wallets.add(w);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findAllByMembersContains(user)).thenReturn(wallets);
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        Double totalSpent = wm.getAllWalletsSpending(user);

        // assert
        assertEquals(totalSpent, 0);
        verify(walletData, times(1)).findAllByMembersContains(user);
    }
    @Test
    public void getWalletTotalSpentTest(){
        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findById(w.getId())).thenReturn(Optional.ofNullable(w));
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        Double totalSpent = wm.getWalletTotalSpend(w.getId());

        // assert
        assertEquals(totalSpent, 0);
        verify(walletData, times(1)).findById(w.getId());
    }
    @Test
    public void getWalletSpendingPerCategoryTest() throws IOException {
        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTitle("test");
        transactionDTO.setCategory("Groceries");
        transactionDTO.setImage(null);
        transactionDTO.setMemberAmountDTO(new ArrayList<>());
        transactionDTO.setAmount(10);
        transactionDTO.setDescription("test");
        transactionDTO.setCreator(user);
        w.addTransaction(new TransactionMapper().mapToModel(transactionDTO));
        Map<TransactionCategory,Double> testCategoryWithSpentAmount = new HashMap<>();
        testCategoryWithSpentAmount.put(TransactionCategory.Groceries,10.0);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findById(w.getId())).thenReturn(Optional.ofNullable(w));
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        Map<TransactionCategory, Double> categoryWithSpentAmount = wm.getWalletSpendingPerCategory(w.getId());

        // assert
        assertEquals(categoryWithSpentAmount, testCategoryWithSpentAmount);
        verify(walletData, times(1)).findById(w.getId());
    }
    @Test
    public void getWalletSpendingPerMember() throws IOException {
        User user = new User((UUID.randomUUID()).toString(), new UserPersonalInfo("Test0", "Test0", new Date()), new UserContact("Test0@gmail.com", "Test0", "0123456789"), Role.USER, new UserCredentials("Test0", "Test0"));

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setTitle("test");
        walletDTO.setDescription("test");
        walletDTO.setCurrency("test");
        walletDTO.setCreator(user);
        walletDTO.setBudget(10);
        Wallet w = new WalletMapper().mapToModel(walletDTO);
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTitle("test");
        transactionDTO.setCategory("Groceries");
        transactionDTO.setImage(null);
        List<MemberAmountDTO> mad = new ArrayList<>();
        MemberAmountDTO ma = new MemberAmountDTO();
        ma.setAmount(10.0);
        ma.setUser(user);
        mad.add(ma);
        transactionDTO.setMemberAmountDTO(mad);
        transactionDTO.setAmount(10);
        transactionDTO.setDescription("test");
        transactionDTO.setCreator(user);
        w.addTransaction(new TransactionMapper().mapToModel(transactionDTO));
        Map<String,Double> testUserWithSpentAmount = new HashMap<>();
        testUserWithSpentAmount.put(user.getFirstName() +" "+ user.getLastName(), 10.0);

        IPersistentWalletData walletData = mock(IPersistentWalletData.class);
        IImageData imageData = mock(IImageData.class);
        IPersistentTransactionData transactionData = mock(IPersistentTransactionData.class);
        IPersistentSharableCodeData sharableCodeData = mock(IPersistentSharableCodeData.class);
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);

        when(walletData.findById(w.getId())).thenReturn(Optional.ofNullable(w));
        WalletManager wm = new WalletManager(messagingTemplate, new ImageManager(imageData, new ImageMapper()), transactionData, sharableCodeData, walletData, new WalletMapper(), new TransactionMapper());

        // act
        Map<String, Double> userWithSpentAmount = wm.getWalletSpendingPerMember(w.getId());

        // assert
        assertEquals(userWithSpentAmount, testUserWithSpentAmount);
        verify(walletData, times(1)).findById(w.getId());
    }
}
