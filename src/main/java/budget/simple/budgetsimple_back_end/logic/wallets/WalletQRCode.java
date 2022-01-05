package budget.simple.budgetsimple_back_end.logic.wallets;

import budget.simple.budgetsimple_back_end.logic.user.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

@Entity(name = "QRCode")
@Table(
        name = "qr_codes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "id",
                        columnNames = "id"
                )}

)
public class WalletQRCode implements ISharableCode {

    private static final String QR_CODE_IMAGE_PATH = "src/main/java/budget/simple/budgetsimple_back_end/logic/wallets/QRcodes/";
    private static final String URL = "http://localhost:3000/user/wallets/code/";

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
                    name = "path",
                    updatable = false,
                    columnDefinition = "text"
            )
    @Getter @Setter private String path;
    @Column
            (
                    name = "link",
                    updatable = false,
                    columnDefinition = "text"
            )
    @Getter @Setter private String link;


    public WalletQRCode() throws IOException, WriterException {
        this.path = QR_CODE_IMAGE_PATH;
        this.id = UUID.randomUUID().toString();
        this.link = UUID.randomUUID().toString();
    }
    @Override
    public void updateCode() {
        this.link = UUID.randomUUID().toString();
    }

    @Override
    public void generateQRCodeImage( int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(URL + this.link, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH + this.id + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    @Override
    public void deleteQRCodeImage()
            throws IOException {
        File file = new File(QR_CODE_IMAGE_PATH + this.id + ".png");
        if(file.delete()){

        }
    }
    @Override
    public String getLink() {
        return this.link;
    }
    @Override
    public String getPath() {
        return this.path;
    }
    @Override
    public String getCodeId() {
        return this.id;
    }
}
