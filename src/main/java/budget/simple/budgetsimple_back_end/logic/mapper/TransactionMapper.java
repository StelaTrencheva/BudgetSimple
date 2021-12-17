package budget.simple.budgetsimple_back_end.logic.mapper;

import budget.simple.budgetsimple_back_end.logic.ImageModel;
import budget.simple.budgetsimple_back_end.logic.survey.SurveyQuestion;
import budget.simple.budgetsimple_back_end.logic.wallets.*;
import budget.simple.budgetsimple_back_end.model.walletDTOs.MemberAmountDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.TransactionDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.WalletDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionMapper {

    public Transaction mapToModel(TransactionDTO transactionDTO) throws IOException {
        List<MemberAmount> memberAmounts = new ArrayList<>();
        for (MemberAmountDTO memberAmount:transactionDTO.getMemberAmountDTO()
        ) {
            memberAmounts.add(new MemberAmount(memberAmount.getUser(), memberAmount.getAmount()));
        }

        return new Transaction(
                transactionDTO.getTitle(),
                transactionDTO.getDescription(),
                TransactionCategory.valueOf(transactionDTO.getCategory()),
                transactionDTO.getAmount(),
                transactionDTO.getDataOfCreation(),
                transactionDTO.getCreator(),
                memberAmounts
        );

    }
    public WalletDTO mapToDTO(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setBudget(wallet.getBudget());
        walletDTO.setCurrency(wallet.getCurrency());
        walletDTO.setDescription(wallet.getDescription());
        walletDTO.setTitle(wallet.getTitle());
        walletDTO.setDataOfCreation(wallet.getDataOfCreation());
        return walletDTO;
    }
}
