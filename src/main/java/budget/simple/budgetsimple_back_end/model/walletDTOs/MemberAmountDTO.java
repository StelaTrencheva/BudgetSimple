package budget.simple.budgetsimple_back_end.model.walletDTOs;


import budget.simple.budgetsimple_back_end.logic.user.User;
import lombok.Getter;
import lombok.Setter;

public class MemberAmountDTO {
    @Getter @Setter private User user;
    @Getter @Setter private double amount;
}
