package budget.simple.budgetsimple_back_end.logic.wallets;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "QRCode")
@Table(
        name = "qr_codes"
)
public class WalletQRCode implements ISharableCode {
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
                    name = "code",
                    updatable = false,
                    columnDefinition = "text"
            )
    @Getter @Setter private String code;
    @Column
            (
                    name = "url",
                    updatable = false,
                    columnDefinition = "text"
            )
    @Getter @Setter private String url;

    protected WalletQRCode(){
        this.id = UUID.randomUUID().toString();

    }
    @Override
    public void UpdateCode() {

    }
}
