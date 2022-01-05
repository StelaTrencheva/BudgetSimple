package budget.simple.budgetsimple_back_end.logic.wallets;

import budget.simple.budgetsimple_back_end.logic.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "Wallet_Entry_Requests")
@Table(
        name = "wallet_entry_request",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = "id"
                )

        })
public class WalletEntryRequest {
    @Id
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "varchar(255)"
    )
    @Getter @Setter private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter @Setter private User user;



    public WalletEntryRequest(User user){
        this.id = UUID.randomUUID().toString();
        this.user = user;
    }

    protected WalletEntryRequest() {

    }
}
