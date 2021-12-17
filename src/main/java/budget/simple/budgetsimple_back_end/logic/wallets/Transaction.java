package budget.simple.budgetsimple_back_end.logic.wallets;

import budget.simple.budgetsimple_back_end.logic.ImageModel;
import budget.simple.budgetsimple_back_end.logic.user.Role;
import budget.simple.budgetsimple_back_end.logic.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Transaction")
    @Table(
            name = "transactions",
            uniqueConstraints = {
                    @UniqueConstraint(
                            name = "id",
                            columnNames = "id"
                    )

            })
    public class Transaction {
        @Id
        @Column
                (
                        name = "id",
                        updatable = false,
                        columnDefinition = "varchar(255)"
                )
        @Getter
        @Setter
        private String id;
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
                        columnDefinition = "TEXT"
                )
        @Getter @Setter private String description;
        @Column
                (
                        name = "category",
                        nullable = false,
                        columnDefinition = "enum('Groceries', 'Dining', 'Shopping', 'Bills', 'Education', 'Kids', 'Health', 'Travel', 'Other')"
                )
        @Enumerated(EnumType.STRING)
        @Getter @Setter private TransactionCategory category;
        @Column
                (
                        name = "amount",
                        nullable = false,
                        columnDefinition = "double"
                )
        @Getter @Setter private double amount;
        @Temporal(TemporalType.DATE)
        @Column
                (
                        name = "data_of_creation",
                        columnDefinition = "date"
                )
        @Getter @Setter private Date dataOfCreation;

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "creator_id", nullable = false)
        @Getter @Setter private User creator;

        @OneToMany(cascade = CascadeType.ALL)
        @Getter @Setter private List<MemberAmount> memberAmounts;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "image_id")
        @Getter @Setter private ImageModel image;

        public Transaction(String title, String description, TransactionCategory category, double amount, Date dataOfCreation, User creator, List<MemberAmount> memberAmounts){
            this.id = UUID.randomUUID().toString();
            this.category = category;
            this.title = title;
            this.description = description;
            this.amount = amount;
            this.dataOfCreation = dataOfCreation;
            this.creator = creator;
            this.memberAmounts = memberAmounts;
        }
    protected Transaction(){

        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Transaction transaction = (Transaction) o;
            return id == transaction.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
