package budget.simple.budgetsimple_back_end.logic.mapper;

import budget.simple.budgetsimple_back_end.logic.wallets.Wallet;
import budget.simple.budgetsimple_back_end.model.walletDTOs.WalletDTO;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WalletMapper {

    public WalletMapper() {
    }

    public Wallet mapToModel(WalletDTO walletDTO) {
        return new Wallet(
                walletDTO.getCreator(),
                walletDTO.getBudget(),
                walletDTO.getTitle(),
                walletDTO.getDescription(),
                walletDTO.getCurrency(),
                new Date()
        );
    }
    public WalletDTO mapToDTO(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setBudget(wallet.getBudget());
        walletDTO.setCurrency(wallet.getCurrency());
        walletDTO.setDescription(wallet.getDescription());
        walletDTO.setTitle(wallet.getTitle());
        return walletDTO;
    }
}
