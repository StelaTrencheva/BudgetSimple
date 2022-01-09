package budget.simple.budgetsimple_back_end.model.walletDTOs;

import budget.simple.budgetsimple_back_end.logic.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class WalletDTO {
    @Getter @Setter private User creator;
    @Getter @Setter private double budget;
    @Getter @Setter private String title;
    @Getter @Setter private String description;
    @Getter @Setter private String currency;
}
