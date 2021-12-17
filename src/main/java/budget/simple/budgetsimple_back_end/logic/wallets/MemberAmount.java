package budget.simple.budgetsimple_back_end.logic.wallets;

import budget.simple.budgetsimple_back_end.logic.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "MemberAmount")
@Table(
        name = "member_amounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = "id"
                )

        })
public class MemberAmount {
    @Id
    @Column
            (
                    name = "id",
                    updatable = false,
                    columnDefinition = "varchar(255)"
            )
    @Getter @Setter private String id;
    @Column
            (
                    name = "amount",
                    nullable = false,
                    columnDefinition = "double"
            )
    @Getter @Setter private double amount;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    @Getter @Setter private User member;


    public MemberAmount(User member, double amount){
        this.id = UUID.randomUUID().toString();
        this.member = member;
        this.amount = amount;
    }

    protected MemberAmount() {

    }
}
