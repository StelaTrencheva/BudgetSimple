package budget.simple.budgetsimple_back_end.logic.wallets;

import budget.simple.budgetsimple_back_end.logic.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity(name = "Wallet")
@Table(
        name = "wallets",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = "id"
                )

        })
public class Wallet{
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
                    name = "budget",
                    nullable = false,
                    columnDefinition = "double"
            )
    @Getter @Setter private double budget;
    @Column
            (
                    name = "title",
                    nullable = false,
                    columnDefinition = "TEXT"
            )
    @Getter @Setter private String title;
    @Column
            (
                    name = "description",
                    nullable = false,
                    columnDefinition = "TEXT"
            )
    @Getter @Setter private String description;
    @Column
            (
                    name = "currency",
                    columnDefinition = "TEXT"
            )
    @Getter @Setter private String currency;
    @Temporal(TemporalType.DATE)
    @Column
            (
                    name = "data_of_creation",
                    columnDefinition = "date"
            )
    @Getter @Setter private Date dataOfCreation;

    @OneToOne(cascade = CascadeType.ALL, targetEntity=WalletQRCode.class)
    @Getter @Setter private ISharableCode generatedCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    @Getter @Setter private User creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @Getter @Setter private Set<User> members;

    @OneToMany(cascade = CascadeType.ALL)
    @Getter @Setter private List<Transaction> transactions;


    public Wallet(User creator, Double budget, String title, String description, String currency, Date dataOfCreation){
        this.id = UUID.randomUUID().toString();
        this.budget = budget;
        this.title = title;
        this.description = description;
        this.currency = currency;
        this.dataOfCreation = dataOfCreation;
        this.members = new HashSet<>();
        this.creator = creator;
        this.members.add(creator);
        this.transactions = new ArrayList<>();
        generatedCode = new WalletQRCode();
    }

    protected Wallet(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return id == wallet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
