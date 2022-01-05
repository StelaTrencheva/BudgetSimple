package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.wallets.ISharableCode;
import budget.simple.budgetsimple_back_end.logic.wallets.WalletQRCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersistentSharableCodeData extends JpaRepository<WalletQRCode, String> {
    ISharableCode findByLink(String link);
}
