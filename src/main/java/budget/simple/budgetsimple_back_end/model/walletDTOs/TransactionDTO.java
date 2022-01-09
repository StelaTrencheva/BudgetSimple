package budget.simple.budgetsimple_back_end.model.walletDTOs;

import budget.simple.budgetsimple_back_end.logic.user.User;
import budget.simple.budgetsimple_back_end.logic.wallets.MemberAmount;
import budget.simple.budgetsimple_back_end.logic.wallets.TransactionCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public class TransactionDTO {
    @Getter @Setter private User creator;
    @Getter @Setter private double amount;
    @Getter @Setter private String title;
    @Getter @Setter private String description;
    @Getter @Setter private String category;
    @Getter @Setter private List<MemberAmountDTO> memberAmountDTO;
    @Getter @Setter private MultipartFile image;
}
