package budget.simple.budgetsimple_back_end.logic.wallets;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface ISharableCode {
    void updateCode();
    void generateQRCodeImage(int width, int height)throws WriterException, IOException;
    String getLink();
    String getCodeId();
    String getPath();
    void deleteQRCodeImage() throws IOException;

}
