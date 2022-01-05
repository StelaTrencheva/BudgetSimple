package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.logic.wallets.ISharableCode;
import budget.simple.budgetsimple_back_end.logic.wallets.Transaction;
import budget.simple.budgetsimple_back_end.logic.wallets.Wallet;
import budget.simple.budgetsimple_back_end.logic.wallets.WalletQRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPersistentWalletData extends JpaRepository<Wallet, String> {
    List<Wallet> findAllByMembersContains(User user);
    Wallet findWalletByGeneratedCode(ISharableCode code);
}
