package budget.simple.budgetsimple_back_end.controller;

import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.logic.wallets.Transaction;
import budget.simple.budgetsimple_back_end.logic.wallets.TransactionCategory;
import budget.simple.budgetsimple_back_end.logic.wallets.Wallet;
import budget.simple.budgetsimple_back_end.logic.wallets.WalletManager;
import budget.simple.budgetsimple_back_end.model.walletDTOs.TransactionDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.WalletDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/wallet")
public class WalletsController {
        @Autowired
        private WalletManager wm;

        // Get all wallets
        @PostMapping("/getAll")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public List<Wallet> getAllWallets(@RequestBody User user) {
                return wm.getAllWallets(user);
        }

        // Create wallet
        @PostMapping("/create")
        @ResponseStatus(HttpStatus.CREATED)
        public void createWallet(@RequestBody WalletDTO walletDTO) {
                wm.addWallet(walletDTO);
        }

        // Delete wallet
        @DeleteMapping("/{id}/delete")
        @ResponseStatus(HttpStatus.OK)
        public void deleteWallet(@PathVariable(value = "id") String id) {
                wm.deleteWallet(id);
        }

        // Get wallet by id
        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Wallet getWallet(@PathVariable(value = "id") String id) {
                return wm.getWalletById(id);
        }

        // Change wallet budget
        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public void changeWalletBudget(@PathVariable(value = "id") String id, @RequestParam double budget) {
                 wm.changeWalletBudget(id, budget);
        }

        // Get all transactions
        @GetMapping("/{id}/transaction/getAll")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public List<Transaction> getAllTransactions(@PathVariable(value = "id") String id) {
                return wm.getAllTransactions(id);
        }

        // Create transaction
        @PostMapping("/{id}/transaction/create")
        @ResponseStatus(HttpStatus.CREATED)
        public void createTransaction(@PathVariable(value = "id") String id, @RequestBody TransactionDTO transactionDTO) throws IOException {
                wm.addTransaction(id, transactionDTO);
        }

        // Get all transactions categories
        @GetMapping("/transaction/categories")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public List<TransactionCategory> getAllTransactionCategories() {
                return wm.getAllTransactionCategories();
        }



}

