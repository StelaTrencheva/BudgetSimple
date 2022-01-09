package budget.simple.budgetsimple_back_end.logic.wallets;

import budget.simple.budgetsimple_back_end.logic.mapper.TransactionMapper;
import budget.simple.budgetsimple_back_end.logic.mapper.UserMapper;
import budget.simple.budgetsimple_back_end.logic.mapper.WalletMapper;
import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.model.WebsocketMessage;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.TransactionDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.WalletDTO;
import budget.simple.budgetsimple_back_end.repository.IPersistentSharableCodeData;
import budget.simple.budgetsimple_back_end.repository.IPersistentTransactionData;
import budget.simple.budgetsimple_back_end.repository.IPersistentWalletData;
import com.google.zxing.WriterException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

@Service
public class WalletManager {
    private final ImageManager im;
    private final SimpMessagingTemplate messagingTemplate;
    private final IPersistentWalletData walletData;
    private final IPersistentTransactionData transactionData;
    private final IPersistentSharableCodeData generatedCodesData;
    private final WalletMapper walletMapper;
    private final TransactionMapper transactionMapper;

    @Autowired
    public WalletManager(SimpMessagingTemplate messagingTemplate, ImageManager im, IPersistentTransactionData transactionData, IPersistentSharableCodeData generatedCodes, IPersistentWalletData walletData, WalletMapper walletMapper, TransactionMapper transactionMapper) {
        this.transactionData = transactionData;
        this.generatedCodesData = generatedCodes;
        this.walletData = walletData;
        this.walletMapper = walletMapper;
        this.transactionMapper = transactionMapper;
        this.im = im;
        this.messagingTemplate = messagingTemplate;
    }

    public List<Wallet> getAllWallets(User user) {
        List<Wallet> wallets = walletData.findAllByMembersContains(user);
        return wallets;
    }

    public void addWallet(WalletDTO walletDTO) {
        Wallet wallet = walletMapper.mapToModel(walletDTO);
        walletData.save(wallet);
    }

    public void deleteWallet(String walletId) throws IOException {
        Wallet wallet = this.getWalletById(walletId);
        wallet.getGeneratedCode().deleteQRCodeImage();
        generatedCodesData.deleteById(wallet.getGeneratedCode().getCodeId());
        walletData.deleteById(walletId);
    }

    public void removeWalletMember(String walletId, UserDTO user) {
        Wallet wallet = this.getWalletById(walletId);
        wallet.removeMember(new UserMapper(new BCryptPasswordEncoder()).mapToModel(user));
        walletData.save(wallet);
    }

    public void changeWalletBudget(String id, double budget) {
        Wallet wallet = getWalletById(id);
        wallet.setBudget(budget);
        walletData.save(wallet);

    }

    public Wallet getWalletById(String walletId) {
        return walletData.findById(walletId).orElse(null);
    }

    public List<Transaction> getAllTransactions(String id) {
        Wallet wallet = walletData.findById(id).orElse(null);
        return wallet.getTransactions();
    }

    public List<TransactionCategory> getAllTransactionCategories() {
        List<TransactionCategory> transactionCategoriesValues = new ArrayList<TransactionCategory>(EnumSet.allOf(TransactionCategory.class));
        return transactionCategoriesValues;
    }

    public void addTransaction(String id, TransactionDTO transactionDTO) throws IOException {
        Wallet wallet = walletData.findById(id).orElse(null);
        Transaction newTransaction = transactionMapper.mapToModel(transactionDTO);
        if (transactionDTO.getImage() != null) {
            newTransaction.setImage(im.createImage(transactionDTO.getImage()));
        }
        wallet.addTransaction(newTransaction);
        walletData.save(wallet);
    }

    public Wallet getWalletByCode(String code) {
        ISharableCode generatedCode = generatedCodesData.findByLink(code);
        return walletData.findWalletByGeneratedCode(generatedCode);
    }

    public void addNewMemberRequest(UserDTO user, String walletId) {
        Wallet wallet = getWalletById(walletId);
        WalletEntryRequest walletEntryRequest = new WalletEntryRequest(new UserMapper(new BCryptPasswordEncoder()).mapToModel(user));
        wallet.addEntryRequest(walletEntryRequest);
        walletData.save(wallet);
    }

    public void acceptWalletEntryRequest(String walletId, WalletEntryRequest request) {
        Wallet wallet = getWalletById(walletId);
        for (User user : wallet.getMembers()) {
            this.notifyWalletMembersOfNewMember(user.getUsername(), new WebsocketMessage(String.format("New member with name: " + request.getUser().getFirstName() + " " +
                    request.getUser().getLastName() + " was added to the wallet " + wallet.getTitle())));
        }

        wallet.addMember(request.getUser());
        wallet.removeEntryRequest(request);
        walletData.save(wallet);
    }

    public void rejectWalletEntryRequest(String walletId, WalletEntryRequest request) {
        Wallet wallet = getWalletById(walletId);
        wallet.removeEntryRequest(request);
        walletData.save(wallet);
    }

    public byte[] getWalletQrCode(String wallet_id) throws IOException {
        Wallet wallet = this.getWalletById(wallet_id);
        return Base64.getEncoder().encode(readFileToByteArray(new File(wallet.getGeneratedCode().getPath() + wallet.getGeneratedCode().getCodeId() + ".png")));
    }

    public void deleteTransaction(String walletId, String transactionId) {
        Wallet wallet = this.getWalletById(walletId);
        wallet.removeTransaction(transactionId);
        walletData.save(wallet);
        transactionData.deleteById(transactionId);
    }

    public Transaction getTransactionById(String transactionId) {
        return transactionData.findById(transactionId).orElse(null);
    }

    public void notifyWalletMembersOfNewMember(String username, final WebsocketMessage message) {
        messagingTemplate.convertAndSend("/walletEntry/messages/" + username, message);
    }

    public Map<String, Double> getWalletSpendingPerMember(String id) {
        Wallet wallet = this.getWalletById(id);
        Map<String, Double> userWithSpentAmount = new HashMap<>();
        for (User member : wallet.getMembers()){
            double spentAmount = 0;
            for (Transaction transaction : wallet.getTransactions()){
                for (MemberAmount ma : transaction.getMemberAmounts()){
                    if(member.equals(ma.getMember())){
                        spentAmount += ma.getAmount();
                    }
                }
            }
            userWithSpentAmount.put(member.getFirstName() +" "+ member.getLastName(), spentAmount);
        }
        return  userWithSpentAmount;
    }

    public Map<TransactionCategory, Double> getWalletSpendingPerCategory(String id) {
        Wallet wallet = this.getWalletById(id);
        Map<TransactionCategory, Double> userWithSpentAmount = new HashMap<>();

            for (Transaction transaction : wallet.getTransactions()){
                double spentAmount = 0;
                for (TransactionCategory tc : TransactionCategory.values()){
                    if(transaction.getCategory() == tc){
                        spentAmount += transaction.getAmount();
                    }
                }
                userWithSpentAmount.put(transaction.getCategory(), spentAmount);
            }
        return  userWithSpentAmount;
    }

    public Double getWalletTotalSpend(String id) {
        Wallet wallet = this.getWalletById(id);
        double totalSpent = 0;
        for (Transaction transaction : wallet.getTransactions()){
            totalSpent += transaction.getAmount();
        }
        return totalSpent;
    }

    public Double getAllWalletsSpending(User user) {
        List<Wallet> wallets = walletData.findAllByMembersContains(user);
        double totalSpent = 0;
        for (Wallet wallet : wallets){
            for(Transaction transaction : wallet.getTransactions()){
                totalSpent += transaction.getAmount();
            }
        }
        return totalSpent;
    }
}
