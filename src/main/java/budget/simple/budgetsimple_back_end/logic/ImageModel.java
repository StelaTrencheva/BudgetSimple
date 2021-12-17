package budget.simple.budgetsimple_back_end.logic;

import budget.simple.budgetsimple_back_end.logic.wallets.Transaction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "ImageModel")
@Table(
        name = "image_models",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = "id"
                )

        })
public class ImageModel {
    @Id
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "varchar(255)"
    )
    @Getter @Setter private String id;
    @Column
            (
                    name = "type",
                    nullable = false,
                    columnDefinition = "text"
            )
    @Getter @Setter private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter @Setter private Transaction transaction;

    public ImageModel(String type){
        this.id = UUID.randomUUID().toString();
        this.type = type;
    }

    protected ImageModel() {

    }
}
