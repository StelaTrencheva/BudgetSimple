package budget.simple.budgetsimple_back_end.controller;

import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.logic.wallets.*;
import budget.simple.budgetsimple_back_end.model.userDTOs.UserDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.TransactionDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.WalletDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/wallet")
public class WalletsController {
        private final WalletManager wm;

        @Autowired
        public WalletsController(WalletManager wm){
                this.wm = wm;
        }

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
        public void deleteWallet(@PathVariable(value = "id") String id) throws IOException {
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

        // Get transaction by id
        @GetMapping("/transaction/{transaction_id}")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Transaction getTransaction(@PathVariable(value = "transaction_id") String transaction_id) {
                return wm.getTransactionById(transaction_id);
        }

        // Get all transactions categories
        @GetMapping("/transaction/categories")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public List<TransactionCategory> getAllTransactionCategories() {
                return wm.getAllTransactionCategories();
        }

        // Delete transaction
        @DeleteMapping("/{id}/transaction/{transaction_id}/delete")
        @ResponseStatus(HttpStatus.OK)
        public void deleteTransaction(@PathVariable(value = "id") String id, @PathVariable(value = "transaction_id") String transaction_id) throws IOException {
                wm.deleteTransaction(id, transaction_id);
        }

        // Get wallet by code id
        @GetMapping("/code/{code}")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Wallet getWalletByCode(@PathVariable(value = "code") String code) {
                return wm.getWalletByCode(code);
        }


        // Get wallet qr code image
        @GetMapping("/qrCode/{id}")
        public @ResponseBody byte[] getWalletQrCodeImage(@PathVariable(value = "id") String id) throws IOException {
                return wm.getWalletQrCode(id);
        }

        // Create wallet entry request
        @PostMapping("/{id}/addWalletEntryRequest")
        @ResponseStatus(HttpStatus.CREATED)
        public void addWalletEntryRequest(@PathVariable(value = "id") String id, @RequestBody UserDTO user) {
                wm.addNewMemberRequest(
                        user,
                        id);
        }

        // Accept wallet entry request
        @PostMapping("/{id}/acceptWalletEntryRequest")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public void acceptWalletEntryRequest(@PathVariable(value = "id") String id, @RequestBody WalletEntryRequest request) {
                wm.acceptWalletEntryRequest(
                        id, request);
        }

        // Reject wallet entry request
        @PostMapping("/{id}/rejectWalletEntryRequest")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public void rejectWalletEntryRequest(@PathVariable(value = "id") String id, @RequestBody WalletEntryRequest request) {
                wm.rejectWalletEntryRequest(
                        id, request);
        }
        // Remove wallet member
        @PostMapping("/{id}/removeWalletMember")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public void removeWalletMember(@PathVariable(value = "id") String id, @RequestBody UserDTO member) {
                wm.removeWalletMember(
                        id, member);
        }

        // Get wallet spending per member
        @GetMapping("/{id}/spendingPerMember")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Map<String, Double> getWalletSpendingPerMember(@PathVariable(value = "id") String id) {
                return wm.getWalletSpendingPerMember(
                        id);
        }

        // Get wallet spending per category
        @GetMapping("/{id}/spendingPerCategory")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Map<TransactionCategory, Double> getWalletSpendingPerCategory(@PathVariable(value = "id") String id) {
                return wm.getWalletSpendingPerCategory(
                        id);
        }

        // Get wallet total spent
        @GetMapping("/{id}/totalSpent")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Double getWalletTotalSpend(@PathVariable(value = "id") String id) {
                return wm.getWalletTotalSpend(
                        id);
        }

        // Get spending of all wallets
        @GetMapping("/totalSpent")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Double getSpendingOfAllWallets(@RequestBody User user) {
                return wm.getAllWalletsSpending(
                        user);
        }
}

