package budget.simple.budgetsimple_back_end.logic.wallets;

import budget.simple.budgetsimple_back_end.logic.mapper.TransactionMapper;
import budget.simple.budgetsimple_back_end.logic.mapper.WalletMapper;
import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.model.walletDTOs.TransactionDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.WalletDTO;
import budget.simple.budgetsimple_back_end.repository.IPersistentTransactionData;
import budget.simple.budgetsimple_back_end.repository.IPersistentWalletData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class WalletManager {
    @Autowired
    private ImageManager im;

    private final IPersistentWalletData walletData;
    private final IPersistentTransactionData transactionData;
    private final WalletMapper walletMapper;
    private final TransactionMapper transactionMapper;

    public WalletManager(IPersistentTransactionData transactionData, IPersistentWalletData walletData, WalletMapper walletMapper, TransactionMapper transactionMapper) {
        this.transactionData = transactionData;
        this.walletData = walletData;
        this.walletMapper = walletMapper;
        this.transactionMapper = transactionMapper;
    }

    public List<Wallet> getAllWallets(User user){
        List<Wallet> wallets =walletData.findAllByMembersContains(user);
        return wallets;
    }
    public void addWallet(WalletDTO walletDTO){
        Wallet wallet = walletMapper.mapToModel(walletDTO);
        walletData.save(wallet);
    }
    public void deleteWallet(String walletId){
        walletData.deleteById(walletId);
    }

    public void changeWalletBudget(String id, double budget){
        Wallet wallet = getWalletById(id);
        wallet.setBudget(budget);
        walletData.save(wallet);

    }
    public Wallet getWalletById(String walletId){
        return walletData.findById(walletId).orElse(null);
    }
    public List<Transaction> getAllTransactions(String id){
        Wallet wallet = walletData.findById(id).orElse(null);
        return wallet.getTransactions();
    }

    public List<TransactionCategory> getAllTransactionCategories(){
        List<TransactionCategory> transactionCategoriesValues = new ArrayList<TransactionCategory>(EnumSet.allOf(TransactionCategory.class));
        return transactionCategoriesValues;
    }
    public void addTransaction(String id, TransactionDTO transactionDTO) throws IOException {
        Wallet wallet = walletData.findById(id).orElse(null);
        Transaction newTransaction = transactionMapper.mapToModel(transactionDTO);
        if(transactionDTO.getImage()!=null){
            newTransaction.setImage(im.createImage(transactionDTO.getImage()));
        }
        List<Transaction> walletTransactions = wallet.getTransactions();
        walletTransactions.add(newTransaction);
        wallet.setTransactions(walletTransactions);
       // transactionData.save(newTransaction);
        walletData.save(wallet);

    }
}
