package budget.simple.budgetsimple_back_end.logic.mapper;

import budget.simple.budgetsimple_back_end.logic.ImageModel;
import budget.simple.budgetsimple_back_end.logic.wallets.MemberAmount;
import budget.simple.budgetsimple_back_end.logic.wallets.Transaction;
import budget.simple.budgetsimple_back_end.logic.wallets.Wallet;
import budget.simple.budgetsimple_back_end.model.walletDTOs.MemberAmountDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.TransactionDTO;
import budget.simple.budgetsimple_back_end.model.walletDTOs.WalletDTO;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class ImageMapper{
    private String uploadLocation = "";

    public ImageModel mapToModel(MultipartFile uploadedImage) throws IOException {
            return new ImageModel(uploadedImage.getContentType());
    }


    public WalletDTO mapToDTO(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setBudget(wallet.getBudget());
        walletDTO.setCurrency(wallet.getCurrency());
        walletDTO.setDescription(wallet.getDescription());
        walletDTO.setTitle(wallet.getTitle());
        walletDTO.setDataOfCreation(wallet.getDataOfCreation());
        return walletDTO;
    }

}
